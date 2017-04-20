package com.ydzb.account.repository;

import com.ydzb.account.entity.WmUser;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户repository
 */
@Repository
public class WmUserUsersRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 查询所有推荐人id以及手机号
     * 根据用户推荐人数>0进行判断
     * @return
     */
    public List<Object[]> findReferralUserIdAndMobile() {
        String sql = " SELECT id, mobile FROM wm_user_users WHERE referral_num > 0 AND status = 0 ";
        Query query = entityManager.createNativeQuery(sql);
        if (query != null) {
            return query.getResultList();
        }
        return new ArrayList<>();
    }

    /**
     * 查询所有推荐人id以及手机号
     * 根据推荐人手机号进行判断
     * @return
     */
    public List<Object[]> findReferralUserIdAndMobile2() {
        String sql = " SELECT DISTINCT(referUsers.id), referUsers.mobile FROM wm_user_users AS referUsers " +
                " JOIN wm_user_users AS users ON users.referral_mobile = referUsers.mobile AND referUsers.status = 0";
        Query query = entityManager.createNativeQuery(sql);
        if (query != null) {
            return query.getResultList();
        }
        return new ArrayList<>();
    }

    /**
     * 根据推荐人手机号以及注册时间段查找用户id
     *
     * @param referralUserMobile 推荐人手机号
     * @param startTime          注册时间开始
     * @param endTime            注册时间结束
     * @return
     */
    public List<BigInteger> findUsersIdByReferralUserAndRegisterTime(String referralUserMobile, Long startTime, Long endTime) {
        String sql = " SELECT id FROM wm_user_users WHERE referral_mobile = " + referralUserMobile +
                " AND created >= " + startTime + " AND created < " + endTime + " AND status = 0";
        Query query = entityManager.createNativeQuery(sql);
        if (query != null) {
            return query.getResultList();
        }
        return new ArrayList<>();
    }

    /**
     * 根据推荐人手机号以及注册时间开始查找用户id
     *
     * @param referralUserMobile 推荐人手机号
     * @param startTime          注册时间开始
     * @return
     */
    public List<BigInteger> findUsersIdByReferralUserAndRegisterTime(String referralUserMobile, Long startTime) {
        String sql = " SELECT id FROM wm_user_users WHERE referral_mobile = " + referralUserMobile +
                " AND created >= " + startTime + " AND status = 0";
        Query query = entityManager.createNativeQuery(sql);
        if (query != null) {
            return query.getResultList();
        }
        return new ArrayList<>();
    }

    /**
     * 查询用户注册时间
     * @param userId
     * @return
     */
    public Long findCreatedByUser(Long userId) {
        String sql = " SELECT created FROM wm_user_users WHERE id = " + userId;
        Query query = entityManager.createNativeQuery(sql);
        List<Integer> resultSet = query.getResultList();
        if (query != null && resultSet != null && !resultSet.isEmpty()) {
            Integer integerId = resultSet.get(0);
            if (integerId != null) {
                return new Long(integerId);
            }
        }
        return null;
    }

    public WmUser findByUserId(Long userId) {
        return entityManager.find(WmUser.class, userId);
    }
}