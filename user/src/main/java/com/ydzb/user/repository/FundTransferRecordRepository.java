package com.ydzb.user.repository;

import com.ydzb.core.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class FundTransferRecordRepository {

    @PersistenceContext
    private EntityManager manager;

    /**
     * 查询导出excel所需数据
     * @param filter
     * @return
     */
    public List<Object[]> findExportData(Map<String, Object> filter) {
        List<Object[]> data = new ArrayList<Object[]>();

        String sql = " SELECT from_user.username as from_username, from_user.mobile as from_mobile," +
                " to_user.username as to_username, to_user.mobile as to_mobile, record.fund, record.optime" +
                " FROM wm_fund_transfer_record AS record" +
                " JOIN wm_user_users AS from_user ON from_user.id = record.out_user_id";
        if (filter != null) {
            final String fromMobile = (String) filter.get("fromMobile");
            if (StringUtils.isNotEmpty(fromMobile)) {
                //不加在where条件中是因为查询速度比where快
                sql += " AND from_user.mobile = '" + fromMobile + "'";
            }
        }
        sql += " JOIN wm_user_users AS to_user ON to_user.id = record.into_user_id";
        if (filter != null) {
            final String toMobile = (String) filter.get("toMobile");
            if (StringUtils.isNotEmpty(toMobile)) {
                //不加在where条件中是因为查询速度比where快
                sql += " AND to_user.mobile = '" + toMobile + "'";
            }
        }
        sql += " WHERE 1 = 1";
        if (filter != null) {
            final String fromTime = (String) filter.get("fromTime");
            if (StringUtils.isNotEmpty(fromTime)) {
                sql += " AND record.optime >= " + DateUtil.getSystemTimeDay(fromTime);
            }
            final String toTime = (String) filter.get("toTime");
            if (StringUtils.isNotEmpty(toTime)) {
                sql += " AND record.optime < " + DateUtil.getSystemTimeDay(DateUtil.addDay(toTime));
            }
        }
        sql += " ORDER BY record.optime DESC";
        Query query = manager.createNativeQuery(sql);
        if (query != null && query.getResultList() != null) {
            data = query.getResultList();
        }
        return data;
    }
}