package com.ydzb.user.repository;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * 用户体验金repository
 * @author sy
 */
@Repository
public class UserExpMoneyRepository {

    @PersistenceContext
    private EntityManager manager;

    /**
     * 更新用户体验金
     * @param userIds
     * @param fund
     * @return
     */
    public String updateUserMoney(List<BigInteger> userIds, BigDecimal fund) {
        if(userIds == null || userIds.isEmpty() || fund == null) {
            return null;
        }
        StringBuffer sql = new StringBuffer();
        sql.append(" UPDATE wm_user_expmoney" +
                " SET amount = amount + " + fund + "," +
                " able_money = able_money + " + fund +
                " WHERE user_id IN (");
        for (int i = 0; i < userIds.size(); i ++) {
            if (i == userIds.size() - 1) {
                sql.append(userIds.get(i));
            } else {
                sql.append(userIds.get(i) + ",");
            }
        }
        sql.append(")");
        Query query = manager.createNativeQuery(sql.toString());
        query.executeUpdate();
        return "ok";
    }
}
