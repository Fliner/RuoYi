package com.ruoyi.process.business.general.service;

import com.ruoyi.process.business.general.domain.HistoricActivity;

import java.util.List;

public interface IProcessService {

    /**
     * 查询审批历史列表
     * @param processInstanceId
     * @param historicActivity
     * @return
     */
    List<HistoricActivity> selectHistoryList(String processInstanceId, HistoricActivity historicActivity);

}
