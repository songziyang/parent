package com.ydzb.account.repository;

import com.ydzb.account.entity.WmUserWithdrawNum;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class WmUserWithDrawNumRepository {


    @PersistenceContext
    private EntityManager manager;


    /**
     * 查询七天提现统计和
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public List<Map<String, Object>> findWithDrawNum(Long startDate, Long endDate) {
        String sql = "select count(wuw.user_id) as num , wuw.user_id as uid from wm_user_withdraw as wuw  where wuw.application_time >= " + startDate + " and  wuw.application_time < " + endDate + " group by wuw.user_id having count(wuw.user_id)  > 1   ";
        Query query = manager.createNativeQuery(sql);
        List<Map<String, Object>> lst = new ArrayList();
        if (query != null) {
            List queryLst = query.getResultList();
            if (queryLst != null && !queryLst.isEmpty()) {
                for (int i = 0; i < queryLst.size(); i++) {
                    Object[] obj = (Object[]) queryLst.get(i);
                    if (obj != null && obj.length > 0) {
                        Map<String, Object> map = new HashMap();
                        BigInteger num = (BigInteger) obj[0];
                        BigInteger uid = (BigInteger) obj[1];
                        if (num != null && uid != null) {
                            map.put("num", num.intValue());
                            map.put("uid", uid.longValue());
                            lst.add(map);
                        }
                    }
                }
            }
        }
        return lst;
    }


    /**
     * 查询七天充值统计和
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public List<Map<String, Object>> findRechargeNum(Long startDate, Long endDate) {
        String sql = "select count(wufr.user_id) as num , wufr.user_id as uid from wm_user_fund_record as wufr  where wufr.fund_type = 0  and   wufr.record_time >= " + startDate + " and  wufr.record_time < " + endDate + " group by wufr.user_id having count(wufr.user_id)  > 1   ";
        Query query = manager.createNativeQuery(sql);
        List<Map<String, Object>> lst = new ArrayList();
        if (query != null) {
            List queryLst = query.getResultList();
            if (queryLst != null && !queryLst.isEmpty()) {
                for (int i = 0; i < queryLst.size(); i++) {
                    Object[] obj = (Object[]) queryLst.get(i);
                    if (obj != null && obj.length > 0) {
                        Map<String, Object> map = new HashMap();
                        BigInteger num = (BigInteger) obj[0];
                        BigInteger uid = (BigInteger) obj[1];
                        if (num != null && uid != null) {
                            map.put("num", num.intValue());
                            map.put("uid", uid.longValue());
                            lst.add(map);
                        }
                    }
                }
            }
        }
        return lst;
    }


    /**
     * 查询七天提现统计和
     *
     * @param userId
     * @return
     */
    public WmUserWithdrawNum findWmUserWithdrawNumByUserId(Long userId) {
        String sql = "select wm from WmUserWithdrawNum as wm where wm.userId = " + userId;
        TypedQuery<WmUserWithdrawNum> query = manager.createQuery(sql, WmUserWithdrawNum.class);
        if (query != null) {
            List<WmUserWithdrawNum> lst = query.getResultList();
            if (lst != null && !lst.isEmpty()) {
                return lst.get(0);
            }
        }
        return null;
    }


    /**
     * 保存用户七天提现统计和
     *
     * @param userWithdrawNum
     */
    public void saveWmUserWithdrawNum(WmUserWithdrawNum userWithdrawNum) {
        manager.persist(userWithdrawNum);
    }

    /**
     * 更新用户七天提现统计和
     *
     * @param userWithdrawNum
     */
    public void updateWmUserWithdrawNum(WmUserWithdrawNum userWithdrawNum) {
        manager.merge(userWithdrawNum);
    }


    /**
     * 清空提现和充值统计表
     */
    public void removeWmUserWithdrawNum() {
        String sql = "DELETE FROM wm_user_withdraw_num ";
        Query query = manager.createNativeQuery(sql);
        query.executeUpdate();
    }

    public EntityManager getManager() {
        return manager;
    }

    public void setManager(EntityManager manager) {
        this.manager = manager;
    }


}
