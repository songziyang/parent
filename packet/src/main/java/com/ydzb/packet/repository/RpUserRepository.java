package com.ydzb.packet.repository;

import com.ydzb.core.utils.DateUtil;
import com.ydzb.packet.entity.RpUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * 红包/体验金用户repository
 */
@Repository
public class RpUserRepository {

    @PersistenceContext
    private EntityManager manager;

    /**
     * 查询发送用户数量
     * @param rpUser
     * @return
     */
    public BigInteger querySendUserCount(RpUser rpUser) {
        if (rpUser == null) {
            return BigInteger.ZERO;
        }
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT COUNT(DISTINCT user.id) FROM wm_user_users AS user" +
                " JOIN wm_user_money AS money ON user.id = money.user_id" +
                " JOIN wm_user_investinfo AS invest ON user.id = invest.user_id" +
                " WHERE user.status = 0");
        addCondition(rpUser, sql);
        Query query = manager.createNativeQuery(sql.toString());
        if (query == null || query.getResultList() == null) {
            return BigInteger.ZERO;
        }
        return (BigInteger)query.getSingleResult();
    }

    /**
     * 查询发送用户id
     * @param rpUser
     * @return
     */
    public List<BigInteger> querySendUserIds(RpUser rpUser) {
        if (rpUser == null) {
            return new ArrayList<BigInteger>();
        }
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT DISTINCT user.id FROM wm_user_users AS user" +
                " JOIN wm_user_money AS money ON user.id = money.user_id" +
                " JOIN wm_user_investinfo AS invest ON user.id = invest.user_id" +
                " WHERE user.status = 0");
        addCondition(rpUser, sql);
        Query query = manager.createNativeQuery(sql.toString());
        if (query == null || query.getResultList() == null) {
            return new ArrayList<BigInteger>();
        }
       return (List<BigInteger>)query.getResultList();
    }

    /**
     * 发送加息券红包
     * @param rpUser 红包用户条件
     * @param redpacketId 红包id
     * @param username 当前登录用户姓名
     * @return
     */
    public String sendRedpacketInterest(RpUser rpUser, Long redpacketId, String username) {
        StringBuffer sql = new StringBuffer();
        sql.append(" INSERT INTO wm_redpacket_users(user_id, redpacket_id," +
                        " redpacket_name, product_type, redpacket_type, trigger_type," +
                        " begin_time, finish_time, invest_days, use_finish_time," +
                        " get_time, give_value, created, created_user)" +
                " SELECT user.id, redpacket.id, redpacket.name, redpacket.product_type, 2," +
                        " redpacket.trigger_type, redpacket.activity_begin_time, redpacket.activity_finish_time," +
                        " redpacket.invest_days, UNIX_TIMESTAMP(DATE_ADD(CURDATE(),INTERVAL redpacket.use_days DAY)), UNIX_TIMESTAMP(now())," +
                        " redpacket.give_value, UNIX_TIMESTAMP(now()), '" + username + "'" +
                " FROM wm_redpacket_interest AS redpacket, wm_user_money AS money, wm_user_investinfo AS invest, wm_user_users AS user" +
                " WHERE money.user_id = user.id AND invest.user_id = user.id AND user.status = 0" +
                " AND redpacket.id = " + redpacketId);
        addCondition(rpUser, sql);
        Query query = manager.createNativeQuery(sql.toString());
        query.executeUpdate();
        return "ok";
    }

    /**
     * 发送现金红包
     * @param rpUser 红包用户条件
     * @param redpacketId 红包id
     * @param username 当前登录用户姓名
     * @return
     */
    public String sendRedpacketCash(RpUser rpUser, Long redpacketId, String username) {
        StringBuffer sql = new StringBuffer();
        sql.append(" INSERT INTO wm_redpacket_users(user_id, redpacket_id, redpacket_name, " +
                " product_type, redpacket_type, trigger_type," +
                " begin_time, finish_time, use_finish_time," +
                " get_time, give_value, created, created_user)" +
                " SELECT user.id, redpacket.id, redpacket.name, 0, 1," +
                " redpacket.trigger_type, redpacket.begin_date, redpacket.end_date," +
                " UNIX_TIMESTAMP(DATE_ADD(CURDATE(),INTERVAL redpacket.use_days DAY)), UNIX_TIMESTAMP(now())," +
                " redpacket.fund, UNIX_TIMESTAMP(now()), '" + username + "'" +
                " FROM wm_redpack_cash AS redpacket, wm_user_money AS money, wm_user_investinfo AS invest, wm_user_users AS user" +
                " WHERE money.user_id = user.id AND invest.user_id = user.id AND user.status = 0" +
                " AND redpacket.id = " + redpacketId);
        addCondition(rpUser, sql);
        Query query = manager.createNativeQuery(sql.toString());
        query.executeUpdate();
        return "ok";
    }

    /**
     * 发送代金券红包
     * @param rpUser 红包用户条件
     * @param redpacketId 红包id
     * @param username 当前登录用户姓名
     * @return
     */
    public String sendRedpacketVoucher(RpUser rpUser, Long redpacketId, String username) {
        StringBuffer sql = new StringBuffer();
        sql.append(" INSERT INTO wm_redpacket_users(user_id, redpacket_id," +
                " redpacket_name, product_type, redpacket_type, trigger_type," +
                " begin_time, finish_time, use_finish_time," +
                " get_time, give_value, limit_fund, created, created_user)" +
                " SELECT user.id, redpacket.id, redpacket.name, 2, 3," +
                " redpacket.trigger_type, redpacket.begin_date, redpacket.end_date," +
                " UNIX_TIMESTAMP(DATE_ADD(CURDATE(),INTERVAL redpacket.use_days DAY)), UNIX_TIMESTAMP(now())," +
                " redpacket.fund, redpacket.limit_fund, UNIX_TIMESTAMP(now()), '" + username + "'" +
                " FROM wm_redpack_vouchers AS redpacket, wm_user_money AS money, wm_user_investinfo AS invest, wm_user_users AS user" +
                " WHERE money.user_id = user.id AND invest.user_id = user.id AND user.status = 0" +
                " AND redpacket.id = " + redpacketId);
        addCondition(rpUser, sql);
        Query query = manager.createNativeQuery(sql.toString());
        query.executeUpdate();
        return "ok";
    }

    /**
     * 发送用户积分
     * @return
     */
    public String sendUserIntegral(RpUser rpUser, Long linkId) {
        StringBuffer sql = new StringBuffer();
        sql.append(" INSERT INTO wm_user_integral_record(user_id, fundflow, integral, balance, type, link_type, link_id, created)" +
                " SELECT user.id, integral.fundflow, integral.integral," +
                " (CASE WHEN u_integral.integral IS NOT NULL THEN u_integral.integral + integral.integral ELSE integral.integral END)," +
                " 1, 24, integral.id, UNIX_TIMESTAMP(now())" +
                " FROM wm_user_users AS user" +
                " LEFT JOIN wm_user_integral AS u_integral ON user.id = u_integral.user_id" +
                " LEFT JOIN wm_integral AS integral ON integral.id = " + linkId +
                " WHERE 1 = 1");
        addCondition(rpUser, sql);
        Query query = manager.createNativeQuery(sql.toString());
        query.executeUpdate();
        return "ok";
    }


  /*  *//**
     * 发送体验金
     * @param rpUser 体验金用户条件
     * @param expMoneyId 体验金id
     * @return
     *//*
    public String sendExpMoney(RpUser rpUser, Long expMoneyId) {
        StringBuffer sql = new StringBuffer();
        sql.append(" INSERT INTO wm_expmoney_deal(user_id, fund, created, close_date, type)" +
                " SELECT user.id, expmoney.copies, UNIX_TIMESTAMP(now())," +
                " UNIX_TIMESTAMP(DATE_ADD(CURDATE(),INTERVAL expmoney.days DAY)), 7" +
                " FROM wm_expmoney_send AS expmoney, wm_user_money AS money, wm_user_investinfo AS invest, wm_user_users AS user" +
                " WHERE money.user_id = user.id AND invest.user_id = user.id AND user.status = 0" +
                " AND expmoney.id = " + expMoneyId);
        addCondition(rpUser, sql);
        Query query = manager.createNativeQuery(sql.toString());
        query.executeUpdate();
        return "ok";
    }*/

    /**
     * 发送体验金
     * @param rpUser 体验金用户条件
     * @param expMoneyId 体验金id
     * @return
     */
    public String sendExpmoney(RpUser rpUser, Long expMoneyId) {
        StringBuffer sql = new StringBuffer();
        sql.append(" INSERT INTO wm_expmoney_deal(user_id, fund, created, close_date, type)" +
                " SELECT user.id, expmoney.copies, UNIX_TIMESTAMP(now())," +
                " UNIX_TIMESTAMP(DATE_ADD(CURDATE(),INTERVAL expmoney.days DAY)), 7" +
                " FROM wm_expmoney_send AS expmoney, wm_user_money AS money, wm_user_investinfo AS invest, wm_user_users AS user" +
                " WHERE money.user_id = user.id AND invest.user_id = user.id AND user.status = 0" +
                " AND expmoney.id = " + expMoneyId);
        addCondition(rpUser, sql);
        Query query = manager.createNativeQuery(sql.toString());
        query.executeUpdate();
        return "ok";
    }



    /**
     * 查询用户手机号
     * @param rpUser
     * @return
     */
    public String[] queryUserMoblie(RpUser rpUser){
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT user.mobile FROM wm_user_users AS user" +
                " JOIN wm_user_money AS money ON money.user_id = user.id" +
                " JOIN wm_user_info AS info ON info.user_id = user.id" +
                " JOIN wm_user_investinfo AS invest ON invest.user_id = user.id" +
                " WHERE user.status = 0");
        addCondition(rpUser, sql);
        Query query = manager.createNativeQuery(sql.toString());
        List<String> mobiles = query.getResultList();
        if(null != mobiles && !mobiles.isEmpty()) {
            return mobiles.toArray(new String[mobiles.size()]);
        }
        return null;
    }

    /**
     * 查询用户手机号
     * @param rpUser
     * @return
     */
    public List<BigInteger> queryUserIds(RpUser rpUser){
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT user.id FROM wm_user_users AS user" +
                " JOIN wm_user_money AS money ON money.user_id = user.id" +
                " JOIN wm_user_info AS info ON info.user_id = user.id" +
                " JOIN wm_user_investinfo AS invest ON invest.user_id = user.id" +
                " WHERE user.status = 0");
        addCondition(rpUser, sql);
        Query query = manager.createNativeQuery(sql.toString());
        if (query != null) {
            return query.getResultList();
        }
        return new ArrayList<>();
    }


    /**
     * 添加查询条件
     * @param rpUser
     * @param sql
     */
    private void addCondition(RpUser rpUser, StringBuffer sql) {
        addUserTypeCondition(rpUser, sql);
        addUserLevelCondition(rpUser, sql);
        addUsernameCondition(rpUser, sql);
        addRegisterDateCondition(rpUser, sql);
        addAccountCondition(rpUser, sql);
        addInvestCondition(rpUser, sql);
        addRefNumCondition(rpUser, sql);
    }

    /**
     * 添加用户类型查询条件
     * @param rpUser
     * @param sql
     */
    private void addUserTypeCondition(RpUser rpUser, StringBuffer sql) {
        if (rpUser == null) {
            return;
        }
        Byte userType = rpUser.getUserType();
        if (userType != null && userType >= 0) {
            sql.append(" AND user.user_type = " + userType);
        }
    }

    /**
     * 添加用户等级查询条件
     * @param rpUser
     * @param sql
     */
    private void addUserLevelCondition(RpUser rpUser, StringBuffer sql) {
        if (rpUser == null) {
            return;
        }
        Byte userLevel = rpUser.getUserLevel();
        if (userLevel != null && userLevel >= 0) {
            sql.append(" AND user.user_leve = " + userLevel);
        }
    }

    /**
     * 添加手机号码查询条件
     * @param rpUser
     * @param sql
     */
    private void addUsernameCondition(RpUser rpUser, StringBuffer sql) {
        if (rpUser == null) {
            return;
        }
        String mobile = rpUser.getMobile();
        if (StringUtils.isNotEmpty(mobile)) {
            sql.append(" AND user.mobile = '" + mobile + "'");
        }
    }

    /**
     * 添加注册时间查询条件
     * @param rpUser
     * @param sql
     */
    private void addRegisterDateCondition(RpUser rpUser, StringBuffer sql) {
        if (rpUser == null) {
            return;
        }

        String registerStartDate = rpUser.getRegisterStartDate();
        String registerEndDate = rpUser.getRegisterEndDate();

        if (StringUtils.isNotEmpty(registerStartDate)) {
            sql.append(" AND user.created >= " + DateUtil.getSystemTimeDay(registerStartDate));
        }

        if (StringUtils.isNotEmpty(registerEndDate)) {
            sql.append(" AND user.created <= " + DateUtil.getSystemTimeDay(DateUtil.addDay(registerEndDate)));
        }
    }

    /**
     * 添加账户金额查询条件
     * @param rpUser
     * @param sql
     */
    private void addAccountCondition(RpUser rpUser, StringBuffer sql) {
        if (rpUser == null) {
            return;
        }

        BigDecimal startAccount = rpUser.getStartAccount();
        BigDecimal endAccount = rpUser.getEndAccount();

        if (startAccount != null) {
            sql.append(" AND money.total_fund >= " + startAccount);
        }

        if (endAccount != null) {
            sql.append(" AND money.total_fund <= " + endAccount);
        }
    }

    /**
     * 添加投资金额查询条件
     * @param rpUser
     * @param sql
     */
    private void addInvestCondition(RpUser rpUser, StringBuffer sql) {
        if (rpUser == null) {
            return;
        }

        BigDecimal startInvest = rpUser.getStartInvest();
        BigDecimal endInvest = rpUser.getEndInvest();

        if (startInvest != null) {
            sql.append(" AND invest.all_invest >= " + startInvest);
        }

        if (endInvest != null) {
            sql.append(" AND invest.all_invest <= " + endInvest);
        }
    }

    /**
     * 添加推荐人数查询条件
     * @param rpUser
     * @param sql
     */
    private void addRefNumCondition(RpUser rpUser, StringBuffer sql) {
        if (rpUser == null) {
            return;
        }

        Integer refStartNum = rpUser.getRefStartNum();
        Integer refEndNum = rpUser.getRefEndNum();

        if (refStartNum != null) {
            sql.append(" AND user.referral_num >= " + refStartNum);
        }

        if (refStartNum != null) {
            sql.append(" AND user.referral_num <= " + refEndNum);
        }
    }

    public EntityManager getManager() {
        return manager;
    }

    public void setManager(EntityManager manager) {
        this.manager = manager;
    }
}