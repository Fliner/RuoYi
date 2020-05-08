package com.ruoyi.process.business.purchase.service;

import java.lang.reflect.Array;
import java.util.List;
import com.ruoyi.process.business.purchase.domain.BizPurchaseProduct;

/**
 * 采购产品关系Service接口
 * 
 * @author ruoyi
 * @date 2020-05-04
 */
public interface IBizPurchaseProductService 
{
    /**
     * 查询采购产品关系
     * 
     * @param id 采购产品关系ID
     * @return 采购产品关系
     */
    public BizPurchaseProduct selectBizPurchaseProductById(Long id);

    /**
     * 查询采购产品关系列表
     * 
     * @param bizPurchaseProduct 采购产品关系
     * @return 采购产品关系集合
     */
    public List<BizPurchaseProduct> selectBizPurchaseProductList(BizPurchaseProduct bizPurchaseProduct);

    /**
     * 新增采购产品关系
     * 
     * @param bizPurchaseProduct 采购产品关系
     * @return 结果
     */
    public int insertBizPurchaseProduct(BizPurchaseProduct bizPurchaseProduct);

    /**
     * 修改采购产品关系
     * 
     * @param bizPurchaseProduct 采购产品关系
     * @return 结果
     */
    public int updateBizPurchaseProduct(BizPurchaseProduct bizPurchaseProduct);

    /**
     * 批量删除采购产品关系
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBizPurchaseProductByIds(String ids);

    /**
     * 删除采购产品关系信息
     * 
     * @param id 采购产品关系ID
     * @return 结果
     */
    public int deleteBizPurchaseProductById(Long id);

    String[] selectProductListByPurchaseId(Long purchaseId);

    int deleteBizPurchaseProductByPurchaseId(Long id);
}
