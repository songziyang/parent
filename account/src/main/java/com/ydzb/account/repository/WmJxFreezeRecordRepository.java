package com.ydzb.account.repository;

import com.ydzb.account.entity.WmJxFreezeRecord;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * 产品交易冻结记录repository
 */
@Repository
public class WmJxFreezeRecordRepository extends WmBaseRepository<WmJxFreezeRecord> {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 查询id
     * @param type 类型
     * @param linkType 交易类型
     * @param expireMode 复投类型
     * @param state 状态
     * @return
     */
    public List<Long> queryIds(Integer type, Integer linkType, Integer expireMode, Integer state) {
        String ql = " SELECT id FROM WmJxFreezeRecord WHERE type = " + type + " AND linkType = " + linkType + " AND expireMode = " + expireMode + " AND state = " + state;
        return entityManager.createQuery(ql).getResultList();
    }

    /**
     * 根据类型，交易类型，状态查询id
     * @param type 类型
     * @param linkType 交易类型
     * @param state 状态
     * @return
     */
    public List<Long> queryIds(Integer type, Integer linkType, Integer state) {
        String ql = " SELECT id FROM WmJxFreezeRecord WHERE type = :type AND linkType = :linkType AND state = :state";
        Query query = entityManager.createQuery(ql);
        query.setParameter("type", type).setParameter("linkType", linkType).setParameter("state", state);
        return query.getResultList();
    }
}
