package com.ruoyi.process.business.purchase.service.impl;

import java.lang.reflect.Array;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.process.business.purchase.mapper.BizPurchaseProductMapper;
import com.ruoyi.process.business.purchase.domain.BizPurchaseProduct;
import com.ruoyi.process.business.purchase.service.IBizPurchaseProductService;
import com.ruoyi.common.core.text.Convert;

/**
 * 采购产品关系Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-05-04
 */
@Service
public class BizPurchaseProductServiceImpl implements IBizPurchaseProductService 
{
    @Autowired
    private BizPurchaseProductMapper bizPurchaseProductMapper;

    /**
     * 查询采购产品关系
     * 
     * @param id 采购产品关系ID
     * @return 采购产品关系
     */
    @Override
    public BizPurchaseProduct selectBizPurchaseProductById(Long id)
    {
        return bizPurchaseProductMapper.selectBizPurchaseProductById(id);
    }

    /**
     * 查询采购产品关系列表
     * 
     * @param bizPurchaseProduct 采购产品关系
     * @return 采购产品关系
     */
    @Override
    public List<BizPurchaseProduct> selectBizPurchaseProductList(BizPurchaseProduct bizPurchaseProduct)
    {
        return bizPurchaseProductMapper.selectBizPurchaseProductList(bizPurchaseProduct);
    }

    /**
     * 新增采购产品关系
     * 
     * @param bizPurchaseProduct 采购产品关系
     * @return 结果
     */
    @Override
    public int insertBizPurchaseProduct(BizPurchaseProduct bizPurchaseProduct)
    {
        return bizPurchaseProductMapper.insertBizPurchaseProduct(bizPurchaseProduct);
    }

    /**
     * 修改采购产品关系
     * 
     * @param bizPurchaseProduct 采购产品关系
     * @return 结果
     */
    @Override
    public int updateBizPurchaseProduct(BizPurchaseProduct bizPurchaseProduct)
    {
        return bizPurchaseProductMapper.updateBizPurchaseProduct(bizPurchaseProduct);
    }

    /**
     * 删除采购产品关系对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteBizPurchaseProductByIds(String ids)
    {
        return bizPurchaseProductMapper.deleteBizPurchaseProductByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除采购产品关系信息
     * 
     * @param id 采购产品关系ID
     * @return 结果
     */
    @Override
    public int deleteBizPurchaseProductById(Long id)
    {
        return bizPurchaseProductMapper.deleteBizPurchaseProductById(id);
    }

    @Override
    public String[] selectProductListByPurchaseId(Long purchaseId) {
        return bizPurchaseProductMapper.selectProductListByPurchaseId(purchaseId);
    }

    @Override
    public int deleteBizPurchaseProductByPurchaseId(Long id) {
        return bizPurchaseProductMapper.deleteBizPurchaseProductByPurchaseId(id);
    }
}
