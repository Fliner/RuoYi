package com.ruoyi.process.web.controller.purchase;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ruoyi.common.core.text.Convert;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.process.business.general.service.IProcessService;
import com.ruoyi.process.business.leave.domain.BizLeaveVo;
import com.ruoyi.process.business.purchase.domain.BizProduct;
import com.ruoyi.process.business.purchase.domain.BizPurchaseProduct;
import com.ruoyi.process.business.purchase.domain.BizPurchaseVo;
import com.ruoyi.process.business.purchase.service.IBizProductService;
import com.ruoyi.process.business.purchase.service.IBizPurchaseProductService;
import com.ruoyi.system.domain.SysUser;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.process.business.purchase.domain.BizPurchase;
import com.ruoyi.process.business.purchase.service.IBizPurchaseService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 采购流程Controller
 * 
 * @author fline
 * @date 2020-05-03
 */
@Controller
@RequestMapping("/process/business/purchase")
public class BizPurchaseController extends BaseController
{
    private String prefix = "process/business/purchase";

    @Autowired
    private IBizPurchaseService bizPurchaseService;
    @Autowired
    private IBizPurchaseProductService purchaseProductService;
    @Autowired
    private IBizProductService productService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private IProcessService processService;

    @RequiresPermissions("purchase:purchase:view")
    @GetMapping()
    public String purchase(ModelMap mmap)
    {
        mmap.put("currentUser", ShiroUtils.getSysUser());
        return prefix + "/purchase";
    }

    /**
     * 查询采购流程列表
     */
    @RequiresPermissions("purchase:purchase:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(BizPurchaseVo bizPurchase)
    {
        if (!SysUser.isAdmin(ShiroUtils.getUserId())) {
            bizPurchase.setCreateBy(ShiroUtils.getLoginName());
        }
        startPage();
        List<BizPurchaseVo> list = bizPurchaseService.selectBizPurchaseList(bizPurchase);
        return getDataTable(list);
    }

    /**
     * 导出采购流程列表
     */
    @RequiresPermissions("purchase:purchase:export")
    @Log(title = "采购流程", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(BizPurchaseVo bizPurchase)
    {
        List<BizPurchaseVo> list = bizPurchaseService.selectBizPurchaseList(bizPurchase);
        ExcelUtil<BizPurchaseVo> util = new ExcelUtil<BizPurchaseVo>(BizPurchaseVo.class);
        return util.exportExcel(list, "purchase");
    }

    /**
     * 新增采购流程
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存采购流程
     */
    @RequiresPermissions("purchase:purchase:add")
    @Log(title = "采购流程", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(BizPurchaseVo bizPurchase, Long[] productIds)
    {
        Long userId = ShiroUtils.getUserId();
        if (SysUser.isAdmin(userId)) {
            return error("提交申请失败：不允许管理员提交申请！");
        }
        int result = bizPurchaseService.insertBizPurchase(bizPurchase);
        if (productIds != null) {
            for (Long productId : productIds) {
                BizPurchaseProduct purchaseProduct = new BizPurchaseProduct();
                purchaseProduct.setProductId(productId);
                purchaseProduct.setPurchaseId(bizPurchase.getId());
                purchaseProductService.insertBizPurchaseProduct(purchaseProduct);
            }
        }
        return toAjax(result);
    }

    /**
     * 修改采购流程
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        BizPurchaseVo bizPurchase = bizPurchaseService.selectBizPurchaseById(id);
        mmap.put("bizPurchase", bizPurchase);
        return prefix + "/edit";
    }

    /**
     * 修改保存采购流程
     */
    @RequiresPermissions("purchase:purchase:edit")
    @Log(title = "采购流程", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(BizPurchaseVo bizPurchase, Long[] productIds)
    {
        int result = bizPurchaseService.updateBizPurchase(bizPurchase);
        if (productIds != null) {
            purchaseProductService.deleteBizPurchaseProductByPurchaseId(bizPurchase.getId());
            for (Long productId : productIds) {
                BizPurchaseProduct purchaseProduct = new BizPurchaseProduct();
                purchaseProduct.setProductId(productId);
                purchaseProduct.setPurchaseId(bizPurchase.getId());
                purchaseProductService.insertBizPurchaseProduct(purchaseProduct);
            }
        }
        return toAjax(result);
    }

    /**
     * 删除采购流程
     */
    @RequiresPermissions("purchase:purchase:remove")
    @Log(title = "采购流程", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        int result = bizPurchaseService.deleteBizPurchaseByIds(ids);
        for (String id : Convert.toStrArray(ids)) {
            purchaseProductService.deleteBizPurchaseProductByPurchaseId(Long.valueOf(id));
        }
        return toAjax(result);
    }

    @RequiresPermissions("purchase:purchase:list")
    @PostMapping("/purchaseProductList")
    @ResponseBody
    public TableDataInfo purchaseProductList(Long purchaseId)
    {
        startPage();
        String[] productIds = purchaseProductService.selectProductListByPurchaseId(purchaseId);
        List<BizProduct> bizProductList = productService.selectBizProductByIds(productIds);
        return getDataTable(bizProductList);
    }

    /**
     * 提交申请
     */
    @PostMapping( "/submitApply")
    @ResponseBody
    public AjaxResult submitApply(Long id) {
        BizPurchaseVo purchase = bizPurchaseService.selectBizPurchaseById(id);
        String applyUserId = ShiroUtils.getLoginName();
        Map<String, Object> variables = new HashMap<>();
        variables.put("applyUserId", applyUserId);
        bizPurchaseService.submitApply(purchase, applyUserId, "purchase", variables);
        return success();
    }

    @GetMapping("/purchaseTodo")
    public String todoView() {
        return prefix + "/purchaseTodo";
    }

    /**
     * 我的待办列表
     * @return
     */
    @PostMapping("/taskList")
    @ResponseBody
    public TableDataInfo taskList(BizPurchaseVo bizPurchase) {
        List<BizPurchaseVo> list = bizPurchaseService.findTodoTasks(bizPurchase, ShiroUtils.getLoginName());
        return getDataTable(list);
    }

    /**
     * 加载审批弹窗
     * @param taskId
     * @param mmap
     * @return
     */
    @GetMapping("/showVerifyDialog/{taskId}")
    public String showVerifyDialog(@PathVariable("taskId") String taskId, ModelMap mmap) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        BizPurchaseVo bizPurchase = bizPurchaseService.selectBizPurchaseById(new Long(processInstance.getBusinessKey()));
        mmap.put("bizPurchase", bizPurchase);
        mmap.put("taskId", taskId);
        String verifyName = task.getTaskDefinitionKey().substring(0, 1).toUpperCase() + task.getTaskDefinitionKey().substring(1);
        return prefix + "/task" + verifyName;
    }

    /**
     * 完成任务
     *
     * @return
     */
    @RequestMapping(value = "/complete/{taskId}", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public AjaxResult complete(@PathVariable("taskId") String taskId, @RequestParam(value = "saveEntity", required = false) String saveEntity,
                               @ModelAttribute("preloadPurchase") BizPurchaseVo purchase, HttpServletRequest request) {
        boolean saveEntityBoolean = BooleanUtils.toBoolean(saveEntity);
        processService.complete(taskId, purchase.getInstanceId(), purchase.getTitle(), purchase.getAmount() + "", "purchase", new HashMap<String, Object>(), request);
        if (saveEntityBoolean) {
            bizPurchaseService.updateBizPurchase(purchase);
        }
        return success("任务已完成");
    }

    /**
     * 申请详情
     * @param instanceId
     * @param mmap
     * @return
     */
    @GetMapping("/showFormDialog/{instanceId}")
    public String showFormDialog(@PathVariable("instanceId") String instanceId, ModelMap mmap) {
        String businessKey = processService.findBusinessKeyByInstanceId(instanceId);
        BizPurchaseVo bizPurchase = bizPurchaseService.selectBizPurchaseById(new Long(businessKey));
        mmap.put("bizLeave", bizPurchase);
        return prefix + "/view";
    }

    /**
     * 自动绑定页面字段
     */
    @ModelAttribute("preloadPurchase")
    public BizPurchaseVo getPurchase(@RequestParam(value = "id", required = false) Long id, HttpSession session) {
        if (id != null) {
            return bizPurchaseService.selectBizPurchaseById(id);
        }
        return new BizPurchaseVo();
    }

    @GetMapping("/purchaseDone")
    public String doneView() {
        return prefix + "/purchaseDone";
    }

    /**
     * 我的已办列表
     * @param bizPurchase
     * @return
     */
    @PostMapping("/taskDoneList")
    @ResponseBody
    public TableDataInfo taskDoneList(BizPurchaseVo bizPurchase) {
        List<BizPurchaseVo> list = bizPurchaseService.findDoneTasks(bizPurchase, ShiroUtils.getLoginName());
        return getDataTable(list);
    }
}
