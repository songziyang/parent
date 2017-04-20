package com.ydzb.account.repository;

import com.ydzb.account.entity.WmStructure;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 */
@Repository
public class WmStructureRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 根据申购结束日期查询状态为申购中的
     * @param date 申购结束日期
     * @return
     */
    public List<WmStructure> findByEndDate(Long date) {
        String sql = " SELECT * FROM wm_structure WHERE end_date = " + date + " AND state = " + WmStructure.STATE_ALLOT;
        List<WmStructure> wmStructures = entityManager.createNativeQuery(sql, WmStructure.class).getResultList();
        return wmStructures == null? new ArrayList<WmStructure>(): wmStructures;
    }

    public WmStructure saveOrUpdate(WmStructure entity) {
        if (entity == null) return null;
        if (entity.getId() != null) return entityManager.merge(entity);
        entityManager.persist(entity);
        return entity;
    }

    /**
     * 根据到期日期查询基金产品
     *
     * @param closeDate 到期日期
     * @return
     */
    public List<WmStructure> findByCloseDate(Long closeDate) {
        String sql = " SELECT * FROM wm_structure WHERE close_date = " + closeDate;
        List<WmStructure> resultList  = entityManager.createNativeQuery(sql, WmStructure.class).getResultList();
        return resultList == null? new ArrayList<WmStructure>(): resultList;
    }

    public WmStructure queryById(Long id) {
        return entityManager.find(WmStructure.class, id);
    }
}
