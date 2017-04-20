package com.ydzb.packet.repository;

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
public class ThirtyLotteryStatisticsRepository {

    @PersistenceContext
    private EntityManager manager;

    public List<Object[]> findExportData(Map<String, Object> filter) {

        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT today_count, statistics_date " +
                " FROM wm_thirty_lottery_statistics" +
                " WHERE 1 = 1");

        if (filter != null) {
            String startTime = (String) filter.get("startTime");
            if (StringUtils.isNotEmpty(startTime)) {
                sql.append(" AND statistics_date >= " + DateUtil.getSystemTimeDay(startTime));
            }

            String endTime = (String) filter.get("endTime");
            if (StringUtils.isNotEmpty(endTime)) {
                sql.append(" AND statistics_date < " + DateUtil.getSystemTimeDay(DateUtil.addDay(endTime)));
            }
        }
        sql.append(" ORDER BY id DESC");

        Query query = manager.createNativeQuery(sql.toString());
        if (query == null || query.getResultList() == null || query.getResultList().isEmpty()) {
            return new ArrayList<>();
        }
        return query.getResultList();
    }
}
