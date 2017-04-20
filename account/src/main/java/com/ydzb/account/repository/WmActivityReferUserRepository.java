package com.ydzb.account.repository;

import com.ydzb.account.entity.WmActivityQingming;
import com.ydzb.account.entity.WmActivityReferUser;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by sy on 2016/6/3.
 */
@Repository
public class WmActivityReferUserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 新建推荐用户活动
     *
     * @param activityReferUser 推荐用户活动
     */
    public void saveWmActivityReferUser(WmActivityReferUser activityReferUser) {
        entityManager.persist(activityReferUser);
    }

    /**
     * 更新推荐用户活动
     *
     * @param activityReferUser 推荐用户活动
     */
    public void updateWmActivityReferUser(WmActivityReferUser activityReferUser) {
        entityManager.merge(activityReferUser);
    }
}