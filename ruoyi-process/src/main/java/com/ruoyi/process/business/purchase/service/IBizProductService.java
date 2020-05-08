package com.ruoyi.process.business.purchase.service;

import java.util.List;
import com.ruoyi.process.business.purchase.domain.BizProduct;

/**
 * productService接口
 * 
 * @author fline
 * @date 2020-05-03
 */
public interface IBizProductService 
{
    /**
     * 查询product
     * 
     * @param id productID
     * @return product
     */
    public BizProduct selectBizProductById(Long id);

    /**
     * 查询product列表
     * 
     * @param bizProduct product
     * @return product集合
     */
    public List<BizProduct> selectBizProductList(BizProduct bizProduct);

    /**
     * 新增product
     * 
     * @param bizProduct product
     * @return 结果
     */
    public int insertBizProduct(BizProduct bizProduct);

    /**
     * 修改product
     * 
     * @param bizProduct product
     * @return 结果
     */
    public int updateBizProduct(BizProduct bizProduct);

    /**
     * 批量删除product
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBizProductByIds(String ids);

    /**
     * 删除product信息
     * 
     * @param id productID
     * @return 结果
     */
    public int deleteBizProductById(Long id);

    List<BizProduct> selectBizProductByIds(String[] productIds);
}
