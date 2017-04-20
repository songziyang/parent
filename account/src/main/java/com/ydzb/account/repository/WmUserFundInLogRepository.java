package com.ydzb.account.repository;

import com.ydzb.account.entity.WmUserFundInLog;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 */
@Repository
public class WmUserFundInLogRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 保存或更新
     * @param wmUserFundInLog
     * @return
     */
    public WmUserFundInLog saveOrUpdate(WmUserFundInLog wmUserFundInLog) {

        if (wmUserFundInLog == null) return null;
        if (wmUserFundInLog.getId() != null) return entityManager.merge(wmUserFundInLog);
        entityManager.persist(wmUserFundInLog);
        entityManager.refresh(wmUserFundInLog);
        return wmUserFundInLog;
    }
}