package com.ruoyi.process.business.purchase.mapper;

import java.util.List;
import com.ruoyi.process.business.purchase.domain.BizPurchase;
import com.ruoyi.process.business.purchase.domain.BizPurchaseVo;

/**
 * 采购流程Mapper接口
 * 
 * @author fline
 * @date 2020-05-03
 */
public interface BizPurchaseMapper 
{
    /**
     * 查询采购流程
     * 
     * @param id 采购流程ID
     * @return 采购流程
     */
    public BizPurchaseVo selectBizPurchaseById(Long id);

    /**
     * 查询采购流程列表
     * 
     * @param bizPurchase 采购流程
     * @return 采购流程集合
     */
    public List<BizPurchaseVo> selectBizPurchaseList(BizPurchase bizPurchase);

    /**
     * 新增采购流程
     * 
     * @param bizPurchase 采购流程
     * @return 结果
     */
    public int insertBizPurchase(BizPurchaseVo bizPurchase);

    /**
     * 修改采购流程
     * 
     * @param bizPurchase 采购流程
     * @return 结果
     */
    public int updateBizPurchase(BizPurchaseVo bizPurchase);

    /**
     * 删除采购流程
     * 
     * @param id 采购流程ID
     * @return 结果
     */
    public int deleteBizPurchaseById(Long id);

    /**
     * 批量删除采购流程
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBizPurchaseByIds(String[] ids);
}
