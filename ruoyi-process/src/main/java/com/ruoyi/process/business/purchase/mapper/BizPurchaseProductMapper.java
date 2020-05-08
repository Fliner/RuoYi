package com.ruoyi.process.business.purchase.mapper;

import java.lang.reflect.Array;
import java.util.List;
import com.ruoyi.process.business.purchase.domain.BizPurchaseProduct;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

/**
 * 采购产品关系Mapper接口
 * 
 * @author ruoyi
 * @date 2020-05-04
 */
public interface BizPurchaseProductMapper 
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
     * 删除采购产品关系
     * 
     * @param id 采购产品关系ID
     * @return 结果
     */
    public int deleteBizPurchaseProductById(Long id);

    /**
     * 批量删除采购产品关系
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBizPurchaseProductByIds(String[] ids);

    @Select("SELECT PRODUCT_ID FROM BIZ_PURCHASE_PRODUCT WHERE PURCHASE_ID = #{purchaseId} ")
    String[] selectProductListByPurchaseId(Long purchaseId);

    @Delete("DELETE FROM BIZ_PURCHASE_PRODUCT WHERE PURCHASE_ID = #{id}")
    int deleteBizPurchaseProductByPurchaseId(Long id);
}
