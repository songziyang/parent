package com.ydzb.account.repository;

import com.ydzb.account.entity.WmStructureDeal;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sy on 2016/7/25.
 */
@Repository
public class WmStructureDealRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 查询用户到期转转赚产品产品
     * @param userId   用户ID
     * @param structureId 基金产品id
     * @return
     */
    public List<WmStructureDeal> findWmStructureDeal(Long userId, Long structureId) {
        String sql = " SELECT * FROM wm_structure_deal WHERE user_id = " + userId + " AND structure_id = " + structureId + " AND state = 0 ";
        List<WmStructureDeal> resultList = entityManager.createNativeQuery(sql, WmStructureDeal.class).getResultList();
        return resultList == null? new ArrayList<WmStructureDeal>(): resultList;
    }

    public WmStructureDeal saveOrUpdate(WmStructureDeal entity) {
        if (entity == null) return null;
        if (entity.getId() != null) return entityManager.merge(entity);
        entityManager.persist(entity);
        return entity;
    }

    public List<WmStructureDeal> queryByUserAndCloseDate(Long userId, Long closeDate) {
        String jpql = " FROM WmStructureDeal WHERE userId = " + userId + " AND closeDate = " + closeDate;
        List<WmStructureDeal> resultList = entityManager.createQuery(jpql, WmStructureDeal.class).getResultList();
        return resultList == null? new ArrayList<WmStructureDeal>(): resultList;
    }
}