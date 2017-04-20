package com.ydzb.account.repository;

import com.ydzb.account.entity.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;


@Repository
public class WmUserRedPacketRepository {

    @PersistenceContext
    private EntityManager manager;


    /**
     * 查询未投资的红包
     *
     * @param userId  用户ID
     * @param curDate 系统当前日期
     * @return
     */
    public List<WmUserRedPacket> findWmUserRedPacketUsed(Long userId, Long curDate) {
        String sql = "select wurp from WmUserRedPacket as wurp where wurp.status = 1 and wurp.userId = " + userId + " and wurp.useFinishTime = " + curDate;
        TypedQuery<WmUserRedPacket> query = manager.createQuery(sql, WmUserRedPacket.class);
        if (query != null) {
            List<WmUserRedPacket> lst = query.getResultList();
            if (lst != null && !lst.isEmpty()) {
                return lst;
            }
        }
        return null;
    }


    /**
     * 保存红包日志
     *
     * @param userRedPacketLog
     */
    public void saveWmUserRedPacketLog(WmUserRedPacketLog userRedPacketLog) {
        manager.persist(userRedPacketLog);
    }


    /**
     * 保存用户红包
     *
     * @param userRedPacket
     */
    public WmUserRedPacket saveWmUserRedPacket(WmUserRedPacket userRedPacket) {
        manager.persist(userRedPacket);
        return userRedPacket;
    }

    /**
     * 更新用户红包
     *
     * @param userRedPacket
     */
    public void updateWmUserRedPacket(WmUserRedPacket userRedPacket) {
        manager.merge(userRedPacket);
    }


    /**
     * 结算投资的红包
     *
     * @param userId  用户ID
     * @param curDate 系统当前日期
     * @return
     */
    public List<WmUserRedPacket> findWmUserRedPacketInvested(Long userId, Long curDate) {
        String sql = "select wurp from WmUserRedPacket as wurp where wurp.status = 3 and wurp.userId = " + userId + " and wurp.investFinishTime = " + curDate;
        TypedQuery<WmUserRedPacket> query = manager.createQuery(sql, WmUserRedPacket.class);
        if (query != null) {
            List<WmUserRedPacket> lst = query.getResultList();
            if (lst != null && !lst.isEmpty()) {
                return lst;
            }
        }
        return null;
    }



    /**
     * 查询定存产品记录
     *
     * @param id
     * @return
     */
    public WmRagularUserAccount findWmRagularUserAccountById(Long id) {
        return manager.find(WmRagularUserAccount.class, id);
    }


    /**
     * 查询红包使用记录
     *
     * @param userId
     * @return
     */
    public WmRedpacketUsed findWmRedpacketUsed(Long userId, Long redpacketUsersId) {
        String sql = "select wm from WmRedpacketUsed as wm where  wm.userId = " + userId + " and  wm.redpacketUsersId = " + redpacketUsersId;
        TypedQuery<WmRedpacketUsed> query = manager.createQuery(sql, WmRedpacketUsed.class);
        if (query != null && query.getResultList() != null && query.getResultList().size() > 0) {
            return query.getSingleResult();
        }
        return null;
    }


    /**
     * 删除红包使用记录
     *
     * @param redpacketUsed
     */
    public void removeWmRedpacketUsed(WmRedpacketUsed redpacketUsed) {
        manager.remove(redpacketUsed);
    }


    /**
     * 查询用户基础信息
     *
     * @param id
     * @return
     */
    public WmUser findWmUserById(Long id) {
        return manager.find(WmUser.class, id);
    }

    /**
     * 根据使用到期时间查询红包信息
     * 未使用的
     * @param finishTime
     * @return
     */
    public List<Object[]> queryRedpacketInfoByFinishTime(Long finishTime) {
        String sql = " SELECT users.id, users.mobile, redpacket.redpacket_type" +
                " FROM wm_redpacket_users AS redpacket" +
                " JOIN wm_user_users AS users ON users.id = redpacket.user_id" +
                " WHERE redpacket.status = 1 AND use_finish_time = " + finishTime +
                " GROUP BY user_id, redpacket_type" ;
        return manager.createNativeQuery(sql).getResultList();
    }


    public EntityManager getManager() {
        return manager;
    }

    public void setManager(EntityManager manager) {
        this.manager = manager;
    }

}
