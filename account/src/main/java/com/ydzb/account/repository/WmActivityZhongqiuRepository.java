package com.ydzb.account.repository;

import com.ydzb.account.context.IDRange;
import com.ydzb.account.entity.WmActivityZhongqiu;
import com.ydzb.account.entity.WmActivityZhongqiuRecord;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.List;

/**
 * 清明节活动repository
 */
@Repository
public class WmActivityZhongqiuRepository {

    @PersistenceContext
    private EntityManager entityManager;


    /**
     * 根据用户ID 查询用户桂花
     *
     * @param userId 用户ID
     * @return 用户桂花
     */
    public WmActivityZhongqiu findWmActivityZhongqiuByUserId(Long userId) {
        String sql = "select wui from WmActivityZhongqiu as wui where wui.userId = " + userId;
        TypedQuery<WmActivityZhongqiu> query = entityManager.createQuery(sql, WmActivityZhongqiu.class);
        if (query != null) {
            List<WmActivityZhongqiu> lst = query.getResultList();
            if (lst != null && !lst.isEmpty()) {
                return entityManager.find(WmActivityZhongqiu.class, lst.get(0).getId(), LockModeType.PESSIMISTIC_WRITE);
            }
        }
        return null;
    }


    /**
     * 保存桂花记录
     *
     * @param activityZhongqiuRecord
     */
    public void saveWmActivityZhongqiuRecord(WmActivityZhongqiuRecord activityZhongqiuRecord) {
        entityManager.persist(activityZhongqiuRecord);
    }


    /**
     * 保存桂花
     *
     * @param wmActivityZhongqiu
     */
    public void saveWmActivityZhongqiu(WmActivityZhongqiu wmActivityZhongqiu) {
        entityManager.persist(wmActivityZhongqiu);
    }


    /**
     * 更新桂花
     *
     * @param wmActivityZhongqiu
     */
    public void updateWmActivityZhongqiu(WmActivityZhongqiu wmActivityZhongqiu) {
        entityManager.merge(wmActivityZhongqiu);
    }


    /**
     * 桂花活动最大ID和最小ID
     *
     * @return
     */
    public IDRange findMaxIdAndMinId() {
        String sql = "select max(wist.id) as maxId, min(wist.id) as minId from wm_activity_zhongqiu as wist ";
        Query query = entityManager.createNativeQuery(sql);
        if (query != null) {
            Object[] obj = (Object[]) query.getSingleResult();
            if (obj != null && obj.length > 0) {
                IDRange idRange = new IDRange((BigInteger) obj[0], (BigInteger) obj[1]);
                return idRange;
            }
        }
        return null;
    }


    /**
     * 查询中秋桂花
     *
     * @param id
     * @return
     */
    public WmActivityZhongqiu findWmActivityZhongqiuById(Long id) {
        return entityManager.find(WmActivityZhongqiu.class, id, LockModeType.PESSIMISTIC_WRITE);
    }


    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
