package com.ruoyi.process.business.purchase.domain;

import lombok.Data;

import java.util.Date;

@Data
public class BizPurchaseVo extends BizPurchase {

    /** 申请人姓名 */
    private String applyUserName;

    /** 任务ID */
    private String taskId;

    /** 任务名称 */
    private String taskName;

    /** 办理时间 */
    private Date doneTime;

    /** 创建人 */
    private String createUserName;

    /** 流程实例状态 1 激活 2 挂起 */
    private String suspendState;
}
