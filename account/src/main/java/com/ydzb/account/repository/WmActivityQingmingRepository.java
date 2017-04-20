package com.ydzb.account.repository;

import com.ydzb.account.entity.WmActivityQingming;
import com.ydzb.account.entity.WmProductInfo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * 清明节活动repository
 */
@Repository
public class WmActivityQingmingRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 根据用户获得清明节活动记录
     *
     * @param userId 用户id
     * @return
     */
    public WmActivityQingming findByUser(final Long userId) {
        String sql = " SELECT * FROM wm_activity_qingming WHERE user_id = " + userId + " order by id desc ";
        Query query = entityManager.createNativeQuery(sql, WmActivityQingming.class);
        if (query != null) {
            List<WmActivityQingming> lst = query.getResultList();
            if (lst != null && !lst.isEmpty()) {
                return entityManager.find(WmActivityQingming.class, lst.get(0).getId(), LockModeType.PESSIMISTIC_WRITE);
            }
        }
        return null;
    }

    /**
     * 新建清明节活动
     *
     * @param activityQingming 清明节活动记录
     */
    public void saveWmActivityQingming(WmActivityQingming activityQingming) {
        entityManager.persist(activityQingming);
    }

    /**
     * 更新清明节活动
     *
     * @param activityQingming 清明节活动记录
     */
    public void updateWmActivityQingming(WmActivityQingming activityQingming) {
        entityManager.merge(activityQingming);
    }
}
