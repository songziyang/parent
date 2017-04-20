package com.ydzb.account.repository;

import com.ydzb.account.entity.WmPrivilegeSettlement;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * 新手特权每日结算repository
 */
@Repository
public class WmPrivilegeSettlementRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 创建或更新
     * @param privilegeSettlement
     * @return
     */
    public WmPrivilegeSettlement createOrUpdate(WmPrivilegeSettlement privilegeSettlement) {
        if (privilegeSettlement == null) return null;
        if (privilegeSettlement.getId() != null) return entityManager.merge(privilegeSettlement);
        entityManager.merge(privilegeSettlement);
        return privilegeSettlement;
    }
}