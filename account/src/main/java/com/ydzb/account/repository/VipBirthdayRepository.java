package com.ydzb.account.repository;

import com.ydzb.account.entity.WmRedPacketInterest;
import com.ydzb.account.entity.WmUser;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;


@Repository
public class VipBirthdayRepository {

    @PersistenceContext
    private EntityManager manager;


    /**
     * 查询VIP生日用户
     *
     * @param date 系统当前日期
     * @return VIP生日用户
     */
    public List<WmUser> findVipBirthdayByDate(String date) {
        String sql = "select wm from WmUser as wm where wm.status = 0 and wm.userLeve > 1 and wm.idCard like '__________" + date + "%'";
        TypedQuery<WmUser> query = manager.createQuery(sql, WmUser.class);
        if (query != null) {
            List<WmUser> lst = query.getResultList();
            if (lst != null && !lst.isEmpty()) {
                return lst;
            }
        }
        return null;
    }

    public List<WmUser> findBirthdayByDate(String date){
        String sql = "select wm from WmUser as wm where wm.status = 0  and wm.userLeve = 1 and wm.userInvestinfo.allInvest >= 100 and wm.idCard like '__________" + date + "%'";
        TypedQuery<WmUser> query = manager.createQuery(sql, WmUser.class);
        if (query != null) {
            List<WmUser> lst = query.getResultList();
            if (lst != null && !lst.isEmpty()) {
                return lst;
            }
        }
        return null;
    }

    public int sendRedpacket(Long userId, Long redpacketId, String createName) {
        StringBuffer sql = new StringBuffer();
        sql.append(" INSERT INTO wm_redpacket_users(user_id, redpacket_id," +
                " redpacket_name, product_type, redpacket_type, trigger_type," +
                " begin_time, finish_time, invest_days, use_finish_time," +
                " get_time, give_value, created, created_user)" +
                " SELECT user.id, redpacket.id, redpacket.name, redpacket.product_type, 2," +
                " redpacket.trigger_type, redpacket.activity_begin_time, redpacket.activity_finish_time," +
                " redpacket.invest_days, UNIX_TIMESTAMP(DATE_ADD(CURDATE(),INTERVAL redpacket.use_days DAY)), UNIX_TIMESTAMP(now())," +
                " redpacket.give_value, UNIX_TIMESTAMP(now()), '" + createName + "'" +
                " FROM wm_redpacket_interest AS redpacket, wm_user_users as user where  user.status = 0 and redpacket.id = " + redpacketId + " and user.id = " + userId);
        Query query = manager.createNativeQuery(sql.toString());
        return query.executeUpdate();
    }


    /**
     * 红包表 返回要发送的红包
     *
     * @return 加息券红包
     */
    public WmRedPacketInterest findWmRedPacketInterest() {
        String sql = "select wrp from WmRedPacketInterest as wrp where  wrp.triggerType = 10  and wrp.status = -1 order by wrp.id desc ";
        TypedQuery<WmRedPacketInterest> query = manager.createQuery(sql, WmRedPacketInterest.class);
        if (query != null) {
            List<WmRedPacketInterest> lst = query.getResultList();
            if (lst != null && !lst.isEmpty()) {
                return lst.get(0);
            }
        }
        return null;
    }


    public EntityManager getManager() {
        return manager;
    }

    public void setManager(EntityManager manager) {
        this.manager = manager;
    }

}
