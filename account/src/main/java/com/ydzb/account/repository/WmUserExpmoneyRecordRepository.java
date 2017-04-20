package com.ydzb.account.repository;

import com.ydzb.account.entity.WmUserExpMoneyRecord;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by sy on 2016/6/28.
 */
@Repository
public class WmUserExpmoneyRecordRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 保存或更新
     * @param wmUserExpMoneyRecord
     * @return
     */
    public WmUserExpMoneyRecord saveOrUpdate(WmUserExpMoneyRecord wmUserExpMoneyRecord) {

        if (wmUserExpMoneyRecord == null) return null;
        if (wmUserExpMoneyRecord.getId() != null) return entityManager.merge(wmUserExpMoneyRecord);
        entityManager.persist(wmUserExpMoneyRecord);
        entityManager.refresh(wmUserExpMoneyRecord);
        return wmUserExpMoneyRecord;
    }
}