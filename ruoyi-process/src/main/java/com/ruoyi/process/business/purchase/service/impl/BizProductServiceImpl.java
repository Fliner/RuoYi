package com.ruoyi.process.business.purchase.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.process.business.purchase.mapper.BizProductMapper;
import com.ruoyi.process.business.purchase.domain.BizProduct;
import com.ruoyi.process.business.purchase.service.IBizProductService;
import com.ruoyi.common.core.text.Convert;

/**
 * productService业务层处理
 * 
 * @author fline
 * @date 2020-05-03
 */
@Service
public class BizProductServiceImpl implements IBizProductService 
{
    @Autowired
    private BizProductMapper bizProductMapper;

    /**
     * 查询product
     * 
     * @param id productID
     * @return product
     */
    @Override
    public BizProduct selectBizProductById(Long id)
    {
        return bizProductMapper.selectBizProductById(id);
    }

    /**
     * 查询product列表
     * 
     * @param bizProduct product
     * @return product
     */
    @Override
    public List<BizProduct> selectBizProductList(BizProduct bizProduct)
    {
        return bizProductMapper.selectBizProductList(bizProduct);
    }

    /**
     * 新增product
     * 
     * @param bizProduct product
     * @return 结果
     */
    @Override
    public int insertBizProduct(BizProduct bizProduct)
    {
        return bizProductMapper.insertBizProduct(bizProduct);
    }

    /**
     * 修改product
     * 
     * @param bizProduct product
     * @return 结果
     */
    @Override
    public int updateBizProduct(BizProduct bizProduct)
    {
        return bizProductMapper.updateBizProduct(bizProduct);
    }

    /**
     * 删除product对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteBizProductByIds(String ids)
    {
        return bizProductMapper.deleteBizProductByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除product信息
     * 
     * @param id productID
     * @return 结果
     */
    @Override
    public int deleteBizProductById(Long id)
    {
        return bizProductMapper.deleteBizProductById(id);
    }

    @Override
    public List<BizProduct> selectBizProductByIds(String[] productIds) {
        return bizProductMapper.selectBizProductByIds(productIds);
    }
}
