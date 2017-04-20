package com.ydzb.account.repository;

import com.ydzb.account.entity.WmActivityGuoqingWinning;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by sy on 2016/9/26.
 */
@Repository
public class WmActivityGuoqingWinningRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public WmActivityGuoqingWinning queryFirstOne(LockModeType lockType) {

        if (lockType == null || lockType == LockModeType.NONE) {
            String ql = " FROM WmActivityGuoqingWinning ORDER BY id ASC ";
            List<WmActivityGuoqingWinning> guoqingWinnings = entityManager.createQuery(ql).getResultList();
            return (guoqingWinnings != null && !guoqingWinnings.isEmpty())? guoqingWinnings.get(0): null;
        }
        String ql = " SELECT id FROM WmActivityGuoqingWinning ORDER BY id ASC ";
        List<Long> ids = entityManager.createQuery(ql).getResultList();
        if (ids != null && !ids.isEmpty()) {
            return entityManager.find(WmActivityGuoqingWinning.class, ids.get(0), lockType);
        }
        return null;
    }

    /**
     * 创建或保存
     * @param entity
     * @return
     */
    public WmActivityGuoqingWinning createOrUpdate(WmActivityGuoqingWinning entity) {
        if (entity == null) return null;
        if (entity.getId() != null) return entityManager.merge(entity);
        entityManager.persist(entity);
        return entity;
    }

}