package com.ydzb.account.repository;

import com.ydzb.account.entity.WmRedPacketCash;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by sy on 2016/6/4.
 */
@Repository
public class WmRedpacketCashRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public WmRedPacketCash findByTriggerType(Integer triggerType) {
        String sql = " SELECT * FROM wm_redpack_cash WHERE trigger_type = " + triggerType + " AND status = 0 ORDER BY created DESC ";
        Query query = entityManager.createNativeQuery(sql, WmRedPacketCash.class);
        if (query != null) {
            List<WmRedPacketCash> resultlist = query.getResultList();
            if (resultlist != null && !resultlist.isEmpty()) {
                return resultlist.get(0);
            }
        }
        return null;
    }

    public WmRedPacketCash createOrUpdate(WmRedPacketCash redPacketCash) {
        if (redPacketCash != null) {
            if (redPacketCash.getId() == null) {
                entityManager.persist(redPacketCash);
                return redPacketCash;
            }
            return entityManager.merge(redPacketCash);
        }
        return null;
    }
}