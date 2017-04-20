package com.ydzb.account.repository;

import com.ydzb.account.entity.WmRedPacketInterest;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * 加息红包repository
 */
@Repository
public class WmRedpacketInterestRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public WmRedPacketInterest saveWmRedPacketInterest(WmRedPacketInterest redPacketInterest) {
        if (redPacketInterest == null) return null;
        if (redPacketInterest.getId() != null) return entityManager.merge(redPacketInterest);
        entityManager.persist(redPacketInterest);
        return redPacketInterest;
    }

    /**
     * 根据触发类型查询
     * @param triggerType 触发类型
     * @return
     */
    public WmRedPacketInterest queryByTriggerType(Integer triggerType) {
        String ql = " FROM WmRedPacketInterest WHERE triggerType = " + triggerType + " AND status = 0 ORDER BY created DESC ";
        List<WmRedPacketInterest> resultList = entityManager.createQuery(ql, WmRedPacketInterest.class).getResultList();
        return (resultList != null && !resultList.isEmpty())? resultList.get(0): null;
    }
}