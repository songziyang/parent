package com.ydzb.user.repository;

import org.springframework.stereotype.Repository;

import java.math.BigDecimal;


/**
 * 用户资金进账日志repository实体类
 * @author sy
 */
@Repository
public class UserFundInLogRepository {

    public void addUserFundInLog(Long userId, Byte type, BigDecimal fund, BigDecimal incomeInterest, BigDecimal usableFund, String remark, Long linkId) {
        StringBuffer sql = new StringBuffer();
        sql.append(" INSERT INTO wm_user_fund_inlog(user_id, type, receipts_time, fund, income_interest, usable_fund, remark, link_id)" +
                " SELECT " + userId + ", " + type + ", UNIX_TIMESTAMP(now()), " + fund + ", " + incomeInterest + ", money.usable_fund, " + remark + ", " + linkId +
                " FROM ");
    }

}
