package com.ydzb.account.repository;

import com.ydzb.account.entity.WmRagularTransfer;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 定存转让repository
 */
@Repository
public class WmRagularTransferRepository extends WmBaseRepository<WmRagularTransfer> {

    /**
     * 根据状态，用户id，定存账户查询定存转让记录
     * @param status
     * @param userId
     * @param accountId
     * @return
     */
    public List<WmRagularTransfer> queryOnes(Integer status, Long userId, Long accountId) {
        String ql = " FROM WmRagularTransfer WHERE status " + status + " AND accountId = " + accountId + " AND applyUserId = " + userId;
        return entityManager.createQuery(ql, WmRagularTransfer.class).getResultList();
    }
}