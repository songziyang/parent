package com.ydzb.user.repository;

import com.ydzb.core.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 小额充值repository
 */
@Repository
public class ViDivestmentUserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 查询导出excel数据
     *
     * @param filter
     * @return
     */
    public List<Object[]> findExportData(Map<String, Object> filter) {

        StringBuffer sql = new StringBuffer();
        sql.append("select vdu.user_id , wuu.username , wuu.mobile , vdu.all_recharge , vdu.total_fund , vdu.application_time  from wm_user_users as wuu , vi_divestment_user as vdu where wuu.id = vdu.user_id  ");

        if (filter != null) {

            String username = (String) filter.get("username");
            if (StringUtils.isNotEmpty(username)) {
                sql.append(" AND wuu.username = '" + username + "'");
            }

            String mobile = (String) filter.get("mobile");
            if (StringUtils.isNotEmpty(mobile)) {
                sql.append(" AND wuu.mobile = '" + mobile + "'");
            }

            String startDate = (String) filter.get("startDate");
            if (StringUtils.isNotEmpty(startDate)) {
                sql.append(" AND vdu.application_time >= " + DateUtil.getSystemTimeDay(startDate));
            }

            String endDate = (String) filter.get("endDate");
            if (StringUtils.isNotEmpty(endDate)) {
                sql.append(" AND vdu.application_time < " + DateUtil.getSystemTimeDay(DateUtil.addDay(endDate)));
            }


        }
        sql.append(" ORDER BY vdu.id DESC");

        List<Object[]> resultList = entityManager.createNativeQuery(sql.toString()).getResultList();
        if (resultList == null || resultList.isEmpty()) {
            return new ArrayList<>();
        }
        return resultList;
    }
}