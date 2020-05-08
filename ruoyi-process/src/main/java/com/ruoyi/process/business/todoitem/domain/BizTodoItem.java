package com.ruoyi.process.business.todoitem.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 待办事项对象 biz_todo_item
 *
 * @author Xianlu Tech
 * @date 2019-11-08
 */
@Data
public class BizTodoItem extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 主键 ID */
    private Long id;

    /** 事项标题 */
    @Excel(name = "事项标题")
    private String itemName;

    /** 事项内容 */
    @Excel(name = "事项内容")
    private String itemContent;

    /** 模块名称 (必须以 uri 一致) */
    @Excel(name = "模块名称")
    private String module;

    /** 任务 ID */
    @Excel(name = "任务 ID")
    private String taskId;

    /** 流程实例 ID */
    @Excel(name = "流程实例 ID")
    private String instanceId;

    /** 任务名称 (必须以表单页面名称一致) */
    @Excel(name = "任务名称")
    private String taskName;

    /** 节点名称 */
    @Excel(name = "节点名称")
    private String nodeName;

    /** 是否查看 default 0 (0 否 1 是) */
    @Excel(name = "是否查看")
    private String isView;

    /** 是否处理 default 0 (0 否 1 是) */
    @Excel(name = "是否处理")
    private String isHandle;

    /** 待办人 ID */
    @Excel(name = "待办人 ID")
    private String todoUserId;

    /** 待办人名称 */
    @Excel(name = "待办人名称")
    private String todoUserName;

    /** 处理人 ID */
    @Excel(name = "处理人 ID")
    private String handleUserId;

    /** 处理人名称 */
    @Excel(name = "处理人名称")
    private String handleUserName;

    /** 通知时间 */
    @Excel(name = "通知时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date todoTime;

    /** 处理时间 */
    @Excel(name = "处理时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date handleTime;

}
