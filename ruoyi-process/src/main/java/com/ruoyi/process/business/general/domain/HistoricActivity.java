package com.ruoyi.process.business.general.domain;

import lombok.Data;
import org.activiti.engine.impl.persistence.entity.HistoricActivityInstanceEntityImpl;
@Data
public class HistoricActivity extends HistoricActivityInstanceEntityImpl {

    /** 审批批注 */
    private String comment;

    /** 办理人姓名 */
    private String assigneeName;
}
