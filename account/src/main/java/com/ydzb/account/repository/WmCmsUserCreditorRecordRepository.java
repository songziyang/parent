package com.ydzb.account.repository;

import com.ydzb.account.entity.WmCmsUserCreditorRecord;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

/**
 * 用户所持债权表repository
 */
@Repository
public class WmCmsUserCreditorRecordRepository extends WmBaseRepository<WmCmsUserCreditorRecord> {


    /**
     * 根据原交易id,用户ID和产品类型来更改新的交易id
     * @param dealId 新的交易id
     * @param sourceDealId 原交易id
     * @param userId 用户id
     * @param productType 产品类别
     */
    public void updateDealId(Long dealId, Long sourceDealId, Long userId, Integer productType) throws Exception {
        String ql = " UPDATE WmCmsUserCreditorRecord SET dealId = :dealId WHERE dealId = :sourceDealId AND userId = :userId AND productType = :productType";
        Query query = entityManager.createQuery(ql);
        query.setParameter("dealId", dealId).setParameter("sourceDealId", sourceDealId)
                .setParameter("userId", userId).setParameter("productType", productType);
        query.executeUpdate();
    }
}