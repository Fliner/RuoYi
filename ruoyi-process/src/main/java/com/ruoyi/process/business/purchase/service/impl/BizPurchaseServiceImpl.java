package com.ruoyi.process.business.purchase.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableSupport;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.process.business.general.service.impl.ProcessServiceImpl;
import com.ruoyi.process.business.leave.domain.BizLeaveVo;
import com.ruoyi.process.business.purchase.domain.BizPurchaseVo;
import com.ruoyi.process.business.todoitem.service.IBizTodoItemService;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.mapper.SysUserMapper;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.persistence.entity.TaskEntityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.process.business.purchase.mapper.BizPurchaseMapper;
import com.ruoyi.process.business.purchase.domain.BizPurchase;
import com.ruoyi.process.business.purchase.service.IBizPurchaseService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.util.CollectionUtils;

/**
 * 采购流程Service业务层处理
 * 
 * @author fline
 * @date 2020-05-03
 */
@Service
public class BizPurchaseServiceImpl implements IBizPurchaseService 
{
    @Autowired
    private BizPurchaseMapper bizPurchaseMapper;
    @Autowired
    private TaskService taskService;
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private IBizTodoItemService bizTodoItemService;
    @Autowired
    private ProcessServiceImpl processService;

    /**
     * 查询采购流程
     * 
     * @param id 采购流程ID
     * @return 采购流程
     */
    @Override
    public BizPurchaseVo selectBizPurchaseById(Long id)
    {
        return bizPurchaseMapper.selectBizPurchaseById(id);
    }

    /**
     * 查询采购流程列表
     * 
     * @param bizPurchase 采购流程
     * @return 采购流程
     */
    @Override
    public List<BizPurchaseVo> selectBizPurchaseList(BizPurchaseVo bizPurchase)
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();

        // PageHelper 仅对第一个 List 分页
        Page<BizPurchaseVo> list = (Page<BizPurchaseVo>) bizPurchaseMapper.selectBizPurchaseList(bizPurchase);
        Page<BizPurchaseVo> returnList = new Page<>();
        for (BizPurchaseVo purchase: list) {
            SysUser sysUser = userMapper.selectUserByLoginName(purchase.getCreateBy());
            if (sysUser != null) {
                purchase.setCreateUserName(sysUser.getUserName());
            }
            SysUser sysUser2 = userMapper.selectUserByLoginName(purchase.getApplyUser());
            if (sysUser2 != null) {
                purchase.setApplyUserName(sysUser2.getUserName());
            }
            // 当前环节
            if (StringUtils.isNotBlank(purchase.getInstanceId())) {
                List<Task> taskList = taskService.createTaskQuery()
                        .processInstanceId(purchase.getInstanceId())
//                        .singleResult();
                        .list();    // 例如请假会签，会同时拥有多个任务
                if (!CollectionUtils.isEmpty(taskList)) {
                    Task task = taskList.get(0);
                    purchase.setTaskId(task.getId());
                    purchase.setTaskName(task.getName());
                } else {
                    purchase.setTaskName("已结束");
                }
            } else {
                purchase.setTaskName("未启动");
            }
            returnList.add(purchase);
        }
        returnList.setTotal(CollectionUtils.isEmpty(list) ? 0 : list.getTotal());
        returnList.setPageNum(pageNum);
        returnList.setPageSize(pageSize);
        return returnList;
    }

    /**
     * 新增采购流程
     * 
     * @param bizPurchase 采购流程
     * @return 结果
     */
    @Override
    public int insertBizPurchase(BizPurchaseVo bizPurchase)
    {
        bizPurchase.setCreateTime(DateUtils.getNowDate());
        bizPurchase.setCreateBy(ShiroUtils.getLoginName());
        bizPurchase.setApplyUser(ShiroUtils.getLoginName());
        bizPurchase.setApplyTime(DateUtils.getNowDate());
        return bizPurchaseMapper.insertBizPurchase(bizPurchase);
    }

    /**
     * 修改采购流程
     * 
     * @param bizPurchase 采购流程
     * @return 结果
     */
    @Override
    public int updateBizPurchase(BizPurchaseVo bizPurchase)
    {
        bizPurchase.setUpdateTime(DateUtils.getNowDate());
        return bizPurchaseMapper.updateBizPurchase(bizPurchase);
    }

    /**
     * 删除采购流程对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteBizPurchaseByIds(String ids)
    {
        return bizPurchaseMapper.deleteBizPurchaseByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除采购流程信息
     * 
     * @param id 采购流程ID
     * @return 结果
     */
    @Override
    public int deleteBizPurchaseById(Long id)
    {
        return bizPurchaseMapper.deleteBizPurchaseById(id);
    }

    @Override
    public ProcessInstance submitApply(BizPurchaseVo entity, String applyUserId, String key, Map<String, Object> variables) {
        entity.setApplyUser(applyUserId);
        entity.setApplyTime(DateUtils.getNowDate());
        entity.setUpdateBy(applyUserId);
        bizPurchaseMapper.updateBizPurchase(entity);
        String businessKey = entity.getId().toString(); // 实体类 ID，作为流程的业务 key

        SysUser leader = this.findApplyUserLeader(applyUserId);
        variables.put("purchaseLeader", leader.getLoginName()); // 主管审批代理人
        variables.put("amount", entity.getAmount()); // 流程变量：合同金额
        ProcessInstance processInstance = processService.submitApply(applyUserId, businessKey, entity.getTitle(), entity.getAmount() + "", key, variables);

        String processInstanceId = processInstance.getId();
        entity.setInstanceId(processInstanceId); // 建立双向关系
        bizPurchaseMapper.updateBizPurchase(entity);

        return processInstance;
    }

    @Override
    public Page<BizPurchaseVo> findTodoTasks(BizPurchaseVo bizPurchase, String userId) {
        // 手动分页
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        Page<BizPurchaseVo> list = new Page<>();

        List<BizPurchaseVo> results = new ArrayList<>();
        List<Task> tasks = processService.findTodoTasks(userId, "purchase");
        // 根据流程的业务ID查询实体并关联
        for (Task task : tasks) {
            TaskEntityImpl taskImpl = (TaskEntityImpl) task;
            String processInstanceId = taskImpl.getProcessInstanceId();
            // 条件过滤 1
            if (StringUtils.isNotBlank(bizPurchase.getInstanceId()) && !bizPurchase.getInstanceId().equals(processInstanceId)) {
                continue;
            }
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            String businessKey = processInstance.getBusinessKey();
            BizPurchaseVo purchase2 = bizPurchaseMapper.selectBizPurchaseById(new Long(businessKey));
            // 条件过滤 2
//            if (StringUtils.isNotBlank(leave.getType()) && !leave.getType().equals(leave2.getType())) {
//                continue;
//            }
            purchase2.setTaskId(taskImpl.getId());
            if (taskImpl.getSuspensionState() == 2) {
                purchase2.setTaskName("已挂起");
            } else {
                purchase2.setTaskName(taskImpl.getName());
            }
            SysUser sysUser = userMapper.selectUserByLoginName(purchase2.getApplyUser());
            purchase2.setApplyUserName(sysUser.getUserName());
            results.add(purchase2);
        }

        List<BizPurchaseVo> tempList;
        if (pageNum != null && pageSize != null) {
            int maxRow = Math.min((pageNum - 1) * pageSize + pageSize, results.size());
            tempList = results.subList((pageNum - 1) * pageSize, maxRow);
            list.setTotal(results.size());
            list.setPageNum(pageNum);
            list.setPageSize(pageSize);
        } else {
            tempList = results;
        }

        list.addAll(tempList);

        return list;
    }

    @Override
    public List<BizPurchaseVo> findDoneTasks(BizPurchaseVo bizPurchase, String userId) {
        // 手动分页
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        Page<BizPurchaseVo> list = new Page<>();

        List<BizPurchaseVo> results = new ArrayList<>();
        List<HistoricTaskInstance> hisList = processService.findDoneTasks(userId, "purchase");
        // 根据流程的业务ID查询实体并关联
        for (HistoricTaskInstance instance : hisList) {
            String processInstanceId = instance.getProcessInstanceId();
            // 条件过滤 1
            if (StringUtils.isNotBlank(bizPurchase.getInstanceId()) && !bizPurchase.getInstanceId().equals(processInstanceId)) {
                continue;
            }
            HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            String businessKey = processInstance.getBusinessKey();
            BizPurchaseVo newPurchase = bizPurchaseMapper.selectBizPurchaseById(new Long(businessKey));
//            BizLeaveVo newLeave = new BizLeaveVo();
//            BeanUtils.copyProperties(leave2, newLeave);
//            // 条件过滤 2
//            if (StringUtils.isNotBlank(bizLeave.getType()) && !bizLeave.getType().equals(leave2.getType())) {
//                continue;
//            }
            newPurchase.setTaskId(instance.getId());
            newPurchase.setTaskName(instance.getName());
            newPurchase.setDoneTime(instance.getEndTime());
            SysUser sysUser = userMapper.selectUserByLoginName(newPurchase.getApplyUser());
            newPurchase.setApplyUserName(sysUser.getUserName());
            results.add(newPurchase);
        }

        List<BizPurchaseVo> tempList;
        if (pageNum != null && pageSize != null) {
            int maxRow = Math.min((pageNum - 1) * pageSize + pageSize, results.size());
            tempList = results.subList((pageNum - 1) * pageSize, maxRow);
            list.setTotal(results.size());
            list.setPageNum(pageNum);
            list.setPageSize(pageSize);
        } else {
            tempList = results;
        }

        list.addAll(tempList);

        return list;
    }

    /**
     * 此处模拟根据申请人 applyUser 查询主管
     * @param applyUser
     * @return
     */
    private SysUser findApplyUserLeader(String applyUser) {
        // TODO 根据 applyUser 查询具体的主管
        return userMapper.selectUserByLoginName("ry");
    }
}
