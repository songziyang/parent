package com.ydzb.account.repository;

import com.ydzb.account.entity.WmRedpacketVoucher;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * 定存红包repository
 */
@Repository
public class WmRedpacketVoucherRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public WmRedpacketVoucher createOrUpdate(WmRedpacketVoucher entity) {
        if (entity == null) return null;
        if (entity.getId() != null) return entityManager.merge(entity);
        entityManager.persist(entity);
        return entity;
    }

    /**
     * 根据触发类型查询定存红包
     * @param triggerType 触发类型
     * @return
     */
    public WmRedpacketVoucher queryByTriggerType(Integer triggerType) {
        String ql = " FROM WmRedpacketVoucher WHERE triggerType = " + triggerType + " AND status = 0 ORDER BY created DESC";
        List<WmRedpacketVoucher> resultList = entityManager.createQuery(ql, WmRedpacketVoucher.class).getResultList();
        return (resultList != null && !resultList.isEmpty())? resultList.get(0): null;
    }
}
