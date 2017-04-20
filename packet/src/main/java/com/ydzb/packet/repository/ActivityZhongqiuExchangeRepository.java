package com.ydzb.packet.repository;

import com.ydzb.packet.entity.ActivityZhongqiu;
import com.ydzb.packet.entity.ActivityZhongqiuRecord;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 30亿活动兑换repository
 */
@Repository
public class ActivityZhongqiuExchangeRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 查询导出excel数据
     *
     * @param filter
     * @return
     */
    public List<Object[]> findExportData(Map<String, Object> filter) {

        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT user.username, user.mobile AS u_mobile, exchange.type, exchange.status,  exchange.product_name , exchange.realname, " +
                " exchange.mobile, exchange.addr,  exchange.created" +
                " FROM wm_activity_zhongqiu_exchange AS exchange" +
                " JOIN wm_user_users AS user ON exchange.user_id = user.id" +
                " WHERE 1 = 1");

        if (filter != null) {

            String username = (String) filter.get("username");
            if (StringUtils.isNotEmpty(username)) {
                sql.append(" AND user.username LIKE '%" + username + "%'");
            }

            String mobile = (String) filter.get("mobile");
            if (StringUtils.isNotEmpty(mobile)) {
                sql.append(" AND user.mobile LIKE '%" + mobile + "%'");
            }

            Integer type = (Integer) filter.get("type");
            if (type != null) {
                sql.append(" AND exchange.type = " + type);
            }

            Integer status = (Integer) filter.get("status");
            if (status == null) {
                status = 1;
            }
            sql.append(" AND exchange.status = " + status);
        }
        sql.append(" ORDER BY exchange.created DESC");

        List<Object[]> resultList = entityManager.createNativeQuery(sql.toString()).getResultList();
        if (resultList == null || resultList.isEmpty()) {
            return new ArrayList<>();
        }
        return resultList;
    }


    /**
     * 根据用户ID 查询用户桂花
     *
     * @param userId 用户ID
     * @return 用户桂花
     */
    public ActivityZhongqiu findWmActivityZhongqiuByUserId(Long userId) {
        String sql = "select wui from ActivityZhongqiu as wui where wui.userId = " + userId;
        TypedQuery<ActivityZhongqiu> query = entityManager.createQuery(sql, ActivityZhongqiu.class);
        if (query != null) {
            List<ActivityZhongqiu> lst = query.getResultList();
            if (lst != null && !lst.isEmpty()) {
                return entityManager.find(ActivityZhongqiu.class, lst.get(0).getId(), LockModeType.PESSIMISTIC_WRITE);
            }
        }
        return null;
    }


    /**
     * 保存桂花
     *
     * @param activityZhongqiu
     */
    public void saveActivityZhongqiu(ActivityZhongqiu activityZhongqiu) {
        entityManager.persist(activityZhongqiu);
    }


    /**
     * 保存桂花记录
     *
     * @param activityZhongqiuRecord
     */
    public void saveActivityZhongqiuRecord(ActivityZhongqiuRecord activityZhongqiuRecord) {
        entityManager.persist(activityZhongqiuRecord);
    }


}
