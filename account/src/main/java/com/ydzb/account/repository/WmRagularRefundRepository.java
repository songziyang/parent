package com.ydzb.account.repository;

import com.ydzb.account.entity.WmRagularRefund;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;

/**
 * 定存还息记录repository
 */
@Repository
public class WmRagularRefundRepository extends WmBaseRepository<WmRagularRefund> {

    /**
     * 根据accountId查询
     * @param accountId 用户定存记录id
     * @param organization 机构类型/产品类型
     * @param expire 是否是最后一期
     * @return
     */
    public WmRagularRefund queryByAccount(Long accountId, Integer organization, Integer expire) {
        String ql = " FROM WmRagularRefund WHERE accountId = " + accountId + " AND organization = " + organization + " AND isExpire = " + expire;
        return entityManager.createQuery(ql, WmRagularRefund.class).getSingleResult();
    }

    /**
     * 根据主键查询实体
     * @param id 主键
     * @param lockType 锁类型
     * @return
     */
    public WmRagularRefund queryById(Long id, LockModeType lockType) {
        return super.queryById(id, WmRagularRefund.class, lockType);
    }
}