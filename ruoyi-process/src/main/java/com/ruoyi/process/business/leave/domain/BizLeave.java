package com.ruoyi.process.business.leave.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

@Data
public class BizLeave extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 请假类型 */
    @Excel(name = "请假类型")
    private String type;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /** 原因 */
    @Excel(name = "原因")
    private String reason;

    /** 开始时间 */
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /** 结束时间 */
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /** 请假时长，单位秒 */
    @Excel(name = "请假时长，单位秒")
    private Long totalTime;

    /** 流程实例ID */
    @Excel(name = "流程实例ID")
    private String instanceId;

    /** 申请人 */
    @Excel(name = "申请人")
    private String applyUser;

    /** 申请时间 */
    @Excel(name = "申请时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date applyTime;

    /** 实际开始时间 */
    private Date realityStartTime;

    /** 实际结束时间 */
    private Date realityEndTime;

}
