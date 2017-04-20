package com.ydzb.packet.repository;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CRF on 2017/3/10 0010.
 */
@Repository
public class IMRepository {
    @PersistenceContext
    private EntityManager manager;

    public EntityManager getManager() {
        return manager;
    }

    public void setManager(EntityManager manager) {
        this.manager = manager;
    }

    /**
     * 字段类型标记
     */
    static class SQLTag {
        static final String DETAIL = "txdate, txtime, seqno, order_id, retcode, retmsg, rettime";
        static final String COUNT = "count(*)";
    }

    /**
     * 获取即信异常信息统计结果
     * @return
     */
    public List<Object> getIMErrorCount() {
        StringBuilder sql = new StringBuilder("");
        sql.append("select t.* from (")
                .append(" (").append(createSQLbyTableName("jx_debt_register", SQLTag.COUNT)).append(") ")
                .append(" UNION ALL ")
                .append(" (").append(createSQLbyTableName("jx_bid_apply", SQLTag.COUNT)).append(") ")
                .append(" UNION ALL ")
                .append(" (").append(createSQLbyTableName("jx_lend_pay", SQLTag.COUNT)).append(") ")
                .append(" UNION ALL ")
                .append(" (").append(createSQLbyTableName("jx_credit_invest", SQLTag.COUNT)).append(") ")
                .append(" UNION ALL ")
                .append(" (").append(createSQLbyTableName("jx_repay", SQLTag.COUNT)).append(") ")
                .append(" UNION ALL ")
                .append(" (").append(createSQLbyTableName("jx_credit_end", SQLTag.COUNT)).append(") ")
            .append(") as t");

        Query query = manager.createNativeQuery(sql.toString());
        if(query != null) {
            return query.getResultList();
        }
        return null;
    }

    /**
     * 根据表名拼装sql语句
     * @param tableName
     * @return
     */
    private String createSQLbyTableName(String tableName, String sqlTag) {
        StringBuilder s = new StringBuilder();
        s.append("select ").append(sqlTag).append(" from ").append(tableName).append(" where retcode <> '00000000' and retcode <> '-1' order by id desc");
        return s.toString();
    }


    /**
     * 根据条件获取详细异常信息
     * @param tableName
     * @return
     */
    public List<Object[]> getIMErrorDetailList(String tableName) {
        String sql = createSQLbyTableName(tableName, SQLTag.DETAIL);
        // 数据库字段不统一，特殊处理
        if ("jx_debt_register".equals(tableName)) {
            sql = sql.replace("order_id", "product_id");
        }
        Query query = manager.createNativeQuery(sql);
        if (query != null) {
            return query.getResultList();
        }
        return new ArrayList<>();
    }
}
