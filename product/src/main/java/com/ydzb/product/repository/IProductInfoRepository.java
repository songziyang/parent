package com.ydzb.product.repository;

import com.ydzb.core.repository.IBaseRepository;
import com.ydzb.product.entity.ProductInfo;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;

/**
 * 产品信息repository层
 *
 * @author sy
 */
public interface IProductInfoRepository extends IBaseRepository<ProductInfo, Long> {

    /**
     * 通过产品名称查找产品信息
     *
     * @param name
     * @return
     */
    @Query(" FROM ProductInfo WHERE name = :name ")
    public ProductInfo queryOne(@Param("name") String name);

    /**
     * 根据产品类型id(id)、产品类别(productClas)以及天数(cylcleDays)查询最大期数
     *
     * @return
     */
    @Query(" SELECT MAX(stage) FROM ProductInfo" +
            " WHERE type.id = :id" +
            " AND productClas = :productClas" +
            " AND cylcleDays = :cylcleDays ")
    public Integer queryMaxStage(@Param("id") Long id, @Param("productClas") Byte productClas,
                                 @Param("cylcleDays") Integer cylcleDays);

    /**
     * 根据产品类型(type)、产品类别(productClas)、天数(cylcleDays)以及原使用状态(fromStatus),来修改符合条件的使用状态(toStatus)
     *
     * @param toStatus
     * @param fromStatus
     * @param id
     * @param productClas
     * @param cylcleDays
     */
    @Modifying
    @Query(" UPDATE ProductInfo SET status = :toStatus" +
            " WHERE status = :fromStatus" +
            " AND type.id = :id" +
            " AND productClas = :productClas" +
            " AND cylcleDays = :cylcleDays ")
    public void updateStatus(@Param("toStatus") Byte toStatus, @Param("fromStatus") Byte fromStatus, @Param("id") Long id,
                             @Param("productClas") Byte productClas, @Param("cylcleDays") Integer cylcleDays);

    /**
     * 根据产品类型id、创建起始时间查找产品数量
     *
     * @param productTypeId
     * @param startTime
     * @param endTime
     * @return
     */
    @Query(" SELECT COUNT(product) FROM ProductInfo AS product WHERE product.type.id = :productTypeId" +
            " AND product.created BETWEEN :startTime AND :endTime ")
    public Integer getProductCount(@Param("productTypeId") Long productTypeId, @Param("startTime") Long startTime,
                                   @Param("endTime") Long endTime);


    /**
     * 根据id查询产品
     *
     * @param id
     * @return
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(" FROM ProductInfo WHERE id = :id ")
    public ProductInfo queryByIdWithPessimisticWriteLock(@Param("id") Long id);


    /**
     * @param typeId
     * @param cylcleDays
     * @return
     */
    @Query("select pi FROM ProductInfo  AS pi  WHERE  pi.type.id = :typeId  AND pi.productClas = 1  AND pi.cylcleDays = :cylcleDays and pi.status = 0   ")
    ProductInfo findProductInfoByParam(@Param("typeId") Long typeId, @Param("cylcleDays") Integer cylcleDays);


    /**
     * 查找往期产品剩余份数
     *
     * @param typeId     类型ID
     * @param cylcleDays 产品周期
     * @param startTime  开始时间
     * @return
     */
    @Query("select sum(pi.surplus) FROM ProductInfo  AS pi  WHERE  pi.type.id = :typeId  AND pi.productClas = 1  AND pi.cylcleDays = :cylcleDays and pi.status <> 0 and pi.created > :startTime ")
    Integer findProductInfoAllSurplus(@Param("typeId") Long typeId, @Param("cylcleDays") Integer cylcleDays, @Param("startTime") Long startTime);


    /**
     * 更新往期产品剩余份数
     *
     * @param typeId     类型ID
     * @param cylcleDays 产品周期
     * @param startTime  开始时间
     */
    @Modifying
    @Query("update ProductInfo  AS pi set pi.surplus = 0  WHERE  pi.type.id = :typeId  AND pi.productClas = 1  AND pi.cylcleDays = :cylcleDays and pi.status <> 0 and pi.created > :startTime ")
    void updateProductInfoAllSurplus(@Param("typeId") Long typeId, @Param("cylcleDays") Integer cylcleDays, @Param("startTime") Long startTime);

}