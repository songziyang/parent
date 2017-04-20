package com.ydzb.user.repository;

import com.ydzb.user.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepository {

    @PersistenceContext
    private EntityManager manager;

    /**
     * 查询导出用户信息数据
     *
     * @param filter
     * @return
     */
    public List<Object[]> findExportData(Map<String, Object> filter) {

        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT user.username, user.mobile, user.referral_mobile, invest.all_invest, user.user_type," +
                " grade.grade_name, user.account_type, user.user_source, integral.total_integral," +
                " integral.integral, user.created, user.id_card" +
                " FROM wm_user_users AS user" +
                " JOIN wm_user_money AS money ON money.user_id = user.id" +
                " JOIN wm_user_investinfo AS invest ON invest.user_id = user.id" +
                " JOIN wm_vip_grade AS grade ON user.user_leve = grade.id" +
                " LEFT JOIN wm_user_integral AS integral ON integral.user_id = user.id " +
                " WHERE user.status <> -1");

        if (filter != null) {
            String username = (String) filter.get("username");
            if (StringUtils.isNotEmpty(username)) {
                sql.append(" AND user.username LIKE '%" + username + "%'");
            }

            String mobile = (String) filter.get("mobile");
            if (StringUtils.isNotEmpty(mobile)) {
                sql.append(" AND user.mobile LIKE '%" + mobile + "%'");
            }

            String referralMobile = (String) filter.get("referralMobile");
            if (StringUtils.isNotEmpty(referralMobile)) {
                sql.append(" AND user.referral_mobile LIKE '%" + referralMobile + "%'");
            }

            Long startRegiest = (Long) filter.get("startRegiest");
            if (startRegiest != null) {
                sql.append(" AND user.created >= " + startRegiest);
            }

            Long endRegiest = (Long) filter.get("endRegiest");
            if (endRegiest != null) {
                sql.append(" AND user.created <= " + endRegiest);
            }

            Integer isLogin = (Integer) filter.get("isLogin");
            if (isLogin != null) {
                sql.append(" AND user.is_login = " + isLogin);
            }

            Integer cardVerifyNum = (Integer) filter.get("cardVerifyNum");
            if (cardVerifyNum != null) {
                sql.append(" AND user.card_verify_num = " + cardVerifyNum);
            }

            Integer isInvUser = (Integer) filter.get("isInvUser");
            if (isInvUser != null) {
                if (isInvUser == 1) {
                    sql.append(" AND invest.all_invest > 0");
                }
                if (isInvUser == 2) {
                    sql.append(" AND invest.all_invest <= 0");
                }
            }

            Integer userType = (Integer) filter.get("userType");
            if (userType != null) {
                sql.append(" AND user.user_type = " + userType);
            }

            Integer userLeve = (Integer) filter.get("userLeve");
            if (userLeve != null) {
                sql.append(" AND grade.id = " + userLeve);
            }

            final Integer status = (Integer) filter.get("status");
            if (status != null) {
                if (status == 1) {
                    sql.append(" AND user.remark is not null ");
                }

                if (status == 2) {
                    sql.append(" AND user.remark is null ");
                }
            }

            BigDecimal startTotalIntegral = (BigDecimal) filter.get("startTotalIntegral");
            if (startTotalIntegral != null) {
                sql.append(" AND integral.total_integral >= " + startTotalIntegral);
            }
            BigDecimal endTotalIntegral = (BigDecimal) filter.get("endTotalIntegral");
            if (endTotalIntegral != null) {
                sql.append(" AND integral.total_integral < " + endTotalIntegral);
            }
            BigDecimal startIntegral = (BigDecimal) filter.get("startIntegral");
            if (startIntegral != null) {
                sql.append(" AND integral.integral >= " + startIntegral);
            }
            BigDecimal endIntegral = (BigDecimal) filter.get("endIntegral");
            if (endIntegral != null) {
                sql.append(" AND integral.integral < " + endIntegral);
            }

            Integer hasAccountId = (Integer) filter.get("hasAccountId");
            if (hasAccountId != null) {
                if (hasAccountId == 0) {
                    sql.append(" AND user.accountid is null");
                }
                if (hasAccountId == 1) {
                    sql.append(" AND user.accountid is not null");
                }
            }

            Integer accountType = (Integer) filter.get("accountType");
            if (accountType != null) {
                sql.append(" AND user.account_type =" + accountType);
            }

            Integer userSource = (Integer) filter.get("userSource");
            if (userSource != null) {
                if (userSource == 0) {
                    sql.append(" AND (user.user_source is null or user.user_source <> -1)");
                }
                if (userSource == 1) {
                    sql.append(" AND user.user_source = -1");
                }
            }

        }

        sql.append(" ORDER BY user.created DESC");
        Query query = manager.createNativeQuery(sql.toString());
        if (query == null || query.getResultList() == null || query.getResultList().isEmpty()) {
            return new ArrayList<Object[]>();
        }
        return query.getResultList();
    }

    /**
     * 查询导出用户资金数据
     *
     * @param filter
     * @return
     */
    public List<Object[]> findMoneyExportData(Map<String, Object> filter) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT user.username, user.mobile, money.total_fund, money.usable_fund," +
                " money.freeze_fund, expmoney.amount, expmoney.able_money, expmoney.blocked_money," +
                " invest.all_invest_dayloan, invest.all_invest_deposit, income.all_income, invest.all_invest_free,invest.all_invest_privilege" +
                " FROM wm_user_users AS user" +
                " LEFT JOIN wm_user_money AS money ON money.user_id = user.id" +
                " LEFT JOIN wm_user_expmoney AS expmoney ON expmoney.user_id = user.id" +
                " LEFT JOIN wm_user_investinfo AS invest ON invest.user_id = user.id" +
                " LEFT JOIN wm_vip_grade AS grade ON user.user_leve = grade.id" +
                " LEFT JOIN wm_user_income AS income ON income.user_id = user.id " +
                " WHERE user.status <> -1");
        if (filter != null) {

            String username = (String) filter.get("username");
            if (StringUtils.isNotEmpty(username)) {
                sql.append(" AND user.username LIKE '%" + username + "%'");
            }

            String mobile = (String) filter.get("mobile");
            if (StringUtils.isNotEmpty(mobile)) {
                sql.append(" AND user.mobile LIKE '%" + mobile + "%'");
            }

            String referralMobile = (String) filter.get("referralMobile");
            if (StringUtils.isNotEmpty(referralMobile)) {
                sql.append(" AND user.referral_mobile LIKE '%" + referralMobile + "%'");
            }

            Long userId = (Long) filter.get("userId");
            if (userId != null) {
                sql.append(" AND user.id = " + userId);
            }

            Integer userType = (Integer) filter.get("userType");
            if (userType != null) {
                sql.append(" AND user.user_type = " + userType);
            }

            Integer userLeve = (Integer) filter.get("userLeve");
            if (userLeve != null) {
                sql.append(" AND grade.id = " + userLeve);
            }

            String wechatNumber = (String) filter.get("wechatNumber");
            if (StringUtils.isNotEmpty(wechatNumber)) {
                sql.append(" AND user.wechat_number LIKE '%" + wechatNumber + "%'");
            }

            String qqNumber = (String) filter.get("qqNumber");
            if (StringUtils.isNotEmpty(qqNumber)) {
                sql.append(" AND user.qq_number LIKE '%" + qqNumber + "%'");
            }

            final Integer startAllIncome = (Integer) filter.get("startAllIncome");
            if (startAllIncome != null) {
                sql.append(" AND income.all_income >= " + startAllIncome);
            }

            final Integer endAllIncome = (Integer) filter.get("endAllIncome");
            if (endAllIncome != null) {
                sql.append(" AND income.all_income <= " + endAllIncome);
            }

            final Integer startFreezeMoney = (Integer) filter.get("startFreezeMoney");
            if (startFreezeMoney != null) {
                sql.append(" AND money.freeze_fund >= " + startFreezeMoney);
            }

            final Integer endFreezeMoney = (Integer) filter.get("endFreezeMoney");
            if (startFreezeMoney != null) {
                sql.append(" AND money.freeze_fund <= " + endFreezeMoney);
            }

            final Integer startExpmoney = (Integer) filter.get("startExpmoney");
            if (startExpmoney != null) {
                sql.append(" AND expmoney.amount >= " + startExpmoney);
            }

            final Integer endExpmoney = (Integer) filter.get("endExpmoney");
            if (startExpmoney != null) {
                sql.append(" AND expmoney.amount <= " + endExpmoney);
            }

            final Integer startAbledExpmoney = (Integer) filter.get("startAbledExpmoney");
            if (startAbledExpmoney != null) {
                sql.append(" AND expmoney.able_money >= " + startAbledExpmoney);
            }

            final Integer endAbledExpmoney = (Integer) filter.get("endAbledExpmoney");
            if (endAbledExpmoney != null) {
                sql.append(" AND expmoney.able_money <= " + endAbledExpmoney);
            }

            final Integer startCurrent = (Integer) filter.get("startCurrent");
            if (startCurrent != null) {
                sql.append(" AND invest.all_invest_dayloan >= " + startCurrent);
            }

            final Integer endCurrent = (Integer) filter.get("endCurrent");
            if (endCurrent != null) {
                sql.append(" AND invest.all_invest_dayloan <= " + endCurrent);
            }

            final Integer startRagular = (Integer) filter.get("startRagular");
            if (startRagular != null) {
                sql.append(" AND invest.all_invest_deposit >= " + startRagular);
            }

            final Integer endRagular = (Integer) filter.get("endRagular");
            if (endRagular != null) {
                sql.append(" AND invest.all_invest_deposit <= " + endRagular);
            }

            final Integer status = (Integer) filter.get("status");
            if (status != null) {

                if (status == 1) {
                    sql.append(" AND user.remark is not null ");
                }

                if (status == 2) {
                    sql.append(" AND user.remark is null ");
                }
            }

            Long startRegiest = (Long) filter.get("startRegiest");
            if (startRegiest != null) {
                sql.append(" AND user.created >= " + startRegiest);
            }

            Long endRegiest = (Long) filter.get("endRegiest");
            if (endRegiest != null) {
                sql.append(" AND user.created <= " + endRegiest);
            }

            Integer startAbleMoney = (Integer) filter.get("startAbleMoney");
            if (startAbleMoney != null) {
                sql.append(" AND money.usable_fund >= " + startAbleMoney);
            }

            Integer endAbleMoney = (Integer) filter.get("endAbleMoney");
            if (endAbleMoney != null) {
                sql.append(" AND money.usable_fund <= " + endAbleMoney);
            }

            Integer startTotalMoney = (Integer) filter.get("startTotalMoney");
            if (startTotalMoney != null) {
                sql.append(" AND money.total_fund >= " + startTotalMoney);
            }

            Integer endTotalMoney = (Integer) filter.get("endTotalMoney");
            if (endTotalMoney != null) {
                sql.append(" AND money.total_fund <= " + endTotalMoney);
            }

            Integer startFree = (Integer) filter.get("startFree");
            if (startFree != null) {
                sql.append(" AND invest.all_invest_free >= " + startFree);
            }

            Integer endFree = (Integer) filter.get("endFree");
            if (endFree != null) {
                sql.append(" AND invest.all_invest_free <= " + endFree);
            }

            Integer startPrivilege = (Integer) filter.get("startPrivilege");
            if (startPrivilege != null) {
                sql.append(" AND invest.all_invest_privilege >= " + startPrivilege);
            }

            Integer endPrivilege = (Integer) filter.get("endPrivilege");
            if (endPrivilege != null) {
                sql.append(" AND invest.all_invest_privilege <= " + endPrivilege);
            }

            Integer orderNumber = (Integer) filter.get("orderNumber");
            String orderSort = (String) filter.get("orderSort");

            if (orderNumber != null && StringUtils.isNotEmpty(orderSort)) {
                switch (orderNumber) {
                    //资金总额
                    case 1:
                        if ("desc".equals(orderSort)) {
                            sql.append(" ORDER BY money.total_fund DESC");
                        } else {
                            sql.append(" ORDER BY money.total_fund ASC");
                        }
                        break;
                    //资金余额
                    case 2:
                        if ("desc".equals(orderSort)) {
                            sql.append(" ORDER BY money.usable_fund DESC");
                        } else {
                            sql.append(" ORDER BY money.usable_fund ASC");
                        }
                        break;
                    //活期宝投资总额
                    case 3:
                        if ("desc".equals(orderSort)) {
                            sql.append(" ORDER BY invest.all_invest_dayloan DESC");
                        } else {
                            sql.append(" ORDER BY invest.all_invest_dayloan ASC");
                        }
                        break;
                    //定存宝投资总额
                    case 4:
                        if ("desc".equals(orderSort)) {
                            sql.append(" ORDER BY invest.all_invest_deposit DESC");
                        } else {
                            sql.append(" ORDER BY invest.all_invest_deposit ASC");
                        }
                        break;
                    //定存转让金额
                    case 5:
                        if ("desc".equals(orderSort)) {
                            sql.append(" ORDER BY invest.all_invest_transfer DESC");
                        } else {
                            sql.append(" ORDER BY invest.all_invest_transfer ASC");
                        }
                        break;
                    //稳进宝金额
                    case 6:
                        if ("desc".equals(orderSort)) {
                            sql.append(" ORDER BY invest.all_invest_wjb DESC");
                        } else {
                            sql.append(" ORDER BY invest.all_invest_wjb ASC");
                        }
                        break;
                    //体验金总额
                    case 7:
                        if ("desc".equals(orderSort)) {
                            sql.append(" ORDER BY expmoney.amount DESC");
                        } else {
                            sql.append(" ORDER BY expmoney.amount ASC");
                        }
                        break;
                    //体验金余额
                    case 8:
                        if ("desc".equals(orderSort)) {
                            sql.append(" ORDER BY expmoney.able_money DESC");
                        } else {
                            sql.append(" ORDER BY expmoney.able_money ASC");
                        }
                        break;
                    //资金冻结金额
                    case 9:
                        if ("desc".equals(orderSort)) {
                            sql.append(" ORDER BY money.freeze_fund DESC");
                        } else {
                            sql.append(" ORDER BY money.freeze_fund ASC");
                        }
                        break;
                    case 10:
                        if ("desc".equals(orderSort)) {
                            sql.append(" ORDER BY expmoney.blocked_money DESC");
                        } else {
                            sql.append(" ORDER BY expmoney.blocked_money ASC");
                        }
                        break;
                    //用户总收益
                    case 11:
                        if ("desc".equals(orderSort)) {
                            sql.append(" ORDER BY income.all_income DESC");
                        } else {
                            sql.append(" ORDER BY income.all_income ASC");
                        }
                        break;
                    //自由定存投资
                    case 12:
                        if ("desc".equals(orderSort)) {
                            sql.append(" ORDER BY invest.all_invest_free DESC");
                        } else {
                            sql.append(" ORDER BY invest.all_invest_free ASC");
                        }
                        break;
                    case 13:
                        if ("desc".equals(orderSort)) {
                            sql.append(" ORDER BY invest.all_invest_privilege DESC");
                        } else {
                            sql.append(" ORDER BY invest.all_invest_privilege ASC");
                        }
                        break;
                }
            } else {
                sql.append(" ORDER BY money.total_fund DESC");
            }
        }

        Query query = manager.createNativeQuery(sql.toString());
        if (query == null || query.getResultList() == null || query.getResultList().isEmpty()) {
            return new ArrayList<Object[]>();
        }
        return query.getResultList();
    }

    /**
     * 查询导出推荐用户数据
     *
     * @param filter
     * @return
     */
    public List<Object[]> findReferralExportData(Map<String, Object> filter) {

        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT referUser.username, referUser.mobile, referUser.user_type, referUser.created, referUser.referral_num," +
                " SUM(info.all_recharge), SUM(invest.all_invest), SUM(invest.all_invest_dayloan), SUM(invest.all_invest_deposit)," +
                " SUM(income.all_income), SUM(info.all_withdraw)" +
                " FROM wm_user_users AS referUser" +
                " LEFT JOIN wm_user_referral AS ref ON ref.user_id = referUser.id" +
                " LEFT JOIN wm_user_users AS user ON ref.referral_user = user.id" +
                " LEFT JOIN wm_user_info AS info ON info.user_id = user.id" +
                " LEFT JOIN wm_user_investinfo AS invest ON invest.user_id = user.id" +
                " LEFT JOIN wm_user_income AS income ON income.user_id = user.id" +
                " WHERE referUser.status <> -1" +
                " AND referUser.referral_num > 0 ");

        if (filter != null) {
            String username = (String) filter.get("username");
            if (StringUtils.isNotEmpty(username)) {
                sql.append(" AND referUser.username LIKE '%" + username + "%'");
            }

            String mobile = (String) filter.get("mobile");
            if (StringUtils.isNotEmpty(mobile)) {
                sql.append(" AND referUser.mobile LIKE '%" + mobile + "%'");
            }

            Integer userType = (Integer) filter.get("userType");
            if (userType != null) {
                sql.append(" AND referUser.user_type = " + userType);
            }
        }

        sql.append(" GROUP BY referUser.id");
        sql.append(" ORDER BY referUser.referral_num DESC");
        Query query = manager.createNativeQuery(sql.toString());
        if (query == null || query.getResultList() == null || query.getResultList().isEmpty()) {
            return new ArrayList<Object[]>();
        }
        return query.getResultList();
    }

    /**
     * 查询导出vip生日所需数据
     *
     * @param filter
     * @return
     */
    public List<Object[]> findVipBirthdayExportData(Map<String, Object> filter) {

        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT user.username, user.mobile, user.id_card, user.created" +
                " FROM wm_user_users AS user" +
                " JOIN wm_vip_grade AS grade ON grade.id = user.user_leve" +
                " WHERE user.status <> -1" +
                " AND grade.grade_no > 0");

        if (filter != null) {
            String birthdayDate = (String) filter.get("birthdayDate");
            if (StringUtils.isNotEmpty(birthdayDate)) {
                sql.append(" AND user.id_card LIKE '__________" + birthdayDate + "%'");
            }
        }

        sql.append(" ORDER BY user.id DESC");
        Query query = manager.createNativeQuery(sql.toString());
        if (query == null || query.getResultList() == null || query.getResultList().isEmpty()) {
            return new ArrayList<Object[]>();
        }
        return query.getResultList();
    }


    public List<User> findVipBirthdayByDate(String date) {
        String sql = "select wm from User as wm where wm.status = 0 and wm.userLeve > 1 and wm.idCard like '__________" + date + "%'";
        TypedQuery<User> query = manager.createQuery(sql, User.class);
        if (query != null && query.getResultList() != null && query.getResultList().size() > 0) {
            return query.getResultList();
        }
        return new ArrayList();
    }

    /**
     * 查询与银多同一天生日的用户
     *
     * @param filter
     * @return
     */
    public List<User> findSameBirthdayUsers(Map<String, Object> filter) {
        String sql = " FROM User WHERE status = 0 AND idCard LIKE '__________0115%'" +
                " AND userInvestinfo.allInvest > 0 ";

        String realName = (String) filter.get("realName");
        if (StringUtils.isNotEmpty(realName)) {
            sql += " AND realName = '" + realName + "'";
        }

        String username = (String) filter.get("username");
        if (StringUtils.isNotEmpty(username)) {
            sql += " AND username = '" + username + "'";
        }

        String mobile = (String) filter.get("mobile");
        if (StringUtils.isNotEmpty(mobile)) {
            sql += " AND mobile = '" + mobile + "'";
        }

        String referralMobile = (String) filter.get("referralMobile");
        if (StringUtils.isNotEmpty(referralMobile)) {
            sql += " AND referralMobile = '" + referralMobile + "'";
        }

        TypedQuery<User> query = manager.createQuery(sql, User.class);
        if (query == null || query.getResultList() == null || query.getResultList().isEmpty()) {
            return new ArrayList<User>();
        }
        return query.getResultList();
    }

    /**
     * @param filter
     * @return
     */
    public List<Object[]> findSameBirthdayExportData(Map<String, Object> filter) {

        String sql = " SELECT user.real_name, user.username, user.mobile, user.referral_mobile, user.user_type," +
                " grade.grade_name, invest.all_invest, user.id_card, user.created" +
                " FROM wm_user_users AS user" +
                " LEFT JOIN wm_vip_grade AS grade ON grade.id = user.user_leve" +
                " LEFT JOIN wm_user_investinfo AS invest ON invest.user_id = user.id " +
                " WHERE user.status = 0" +
                " AND user.id_card LIKE '__________0115%'" +
                " AND invest.all_invest > 0";

        String realName = (String) filter.get("realName");
        if (StringUtils.isNotEmpty(realName)) {
            sql += " AND user.real_name = '" + realName + "'";
        }

        String username = (String) filter.get("username");
        if (StringUtils.isNotEmpty(username)) {
            sql += " AND user.username = '" + username + "'";
        }

        String mobile = (String) filter.get("mobile");
        if (StringUtils.isNotEmpty(mobile)) {
            sql += " AND user.mobile = '" + mobile + "'";
        }

        String referralMobile = (String) filter.get("referralMobile");
        if (StringUtils.isNotEmpty(referralMobile)) {
            sql += " AND user.referral_mobile = '" + referralMobile + "'";
        }

        Query query = manager.createNativeQuery(sql);
        if (query == null || query.getResultList() == null || query.getResultList().isEmpty()) {
            return new ArrayList<Object[]>();
        }
        return query.getResultList();
    }
}