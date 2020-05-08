package com.ruoyi.process.business.purchase.mapper;

import java.util.List;
import com.ruoyi.process.business.purchase.domain.BizProduct;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;

/**
 * productMapper接口
 * 
 * @author fline
 * @date 2020-05-03
 */
public interface BizProductMapper 
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
     * 删除product
     * 
     * @param id productID
     * @return 结果
     */
    public int deleteBizProductById(Long id);

    /**
     * 批量删除product
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBizProductByIds(String[] ids);

    List<BizProduct> selectBizProductByIds(String[] ids);
}
