package com.ydzb.packet.repository;

import com.ydzb.core.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class DepositTypeExchangeRepository {

    @PersistenceContext
    private EntityManager manager;

    /**
     * 查询导出excel数据
     * @param filter
     * @return
     */
    public List<Object[]> findExportData(Map<String, Object> filter) {
        String sql = "select wuu.username , wuu.mobile ,  wdte.actype , wdte.status ,  wdte.ptype, wdte.type ,wdte.product_name , wdte.fund , wdte.number , " +
                "wdte.realname , wdte.mobile as mob , wdte.address , wdte.remark ,wdte.created " +
                "from  wm_deposit_type_exchange  as wdte  left join  wm_user_users as wuu  ON wdte.user_id = wuu.id  where 1 = 1 ";

        if (filter != null) {

            String username = (String) filter.get("username");
            if (StringUtils.isNotEmpty(username)) {
                sql += " AND wuu.username = '" + username + "'";
            }

            String mobile = (String) filter.get("mobile");
            if (StringUtils.isNotEmpty(mobile)) {
                sql += " AND wuu.mobile = '" + mobile + "'";
            }

            Integer status = (Integer) filter.get("status");
            if (status != null) {
                sql += " AND wdte.status = " + status;
            }

            Integer actype = (Integer) filter.get("actype");
            if (actype != null) {
                sql += " AND wdte.actype = " + actype;
            }

            Integer ptype = (Integer) filter.get("ptype");
            if (ptype != null) {
                sql += " AND wdte.ptype = " + ptype;
            }

            Integer type = (Integer) filter.get("type");
            if (type != null) {
                sql += " AND wdte.type = " + type;
            }

            String startDate = (String) filter.get("startDate");
            if (StringUtils.isNotEmpty(startDate)) {
                sql += " AND wdte.created >= " + DateUtil.getSystemTimeDay(startDate);
            }

            String endDate = (String) filter.get("endDate");
            if (StringUtils.isNotEmpty(endDate)) {
                sql += " AND wdte.created < " + DateUtil.getSystemTimeDay(DateUtil.addDay(endDate));
            }

            BigDecimal startFund = (BigDecimal) filter.get("startFund");
            if (startFund != null) {
                sql += " AND wdte.fund >= " + startFund;
            }

            BigDecimal endFund = (BigDecimal) filter.get("endFund");
            if (endFund != null) {
                sql += " AND wdte.fund <= " + endFund;
            }

        }

        sql += " ORDER BY wdte.created DESC";

        System.out.println(sql);

        Query query = manager.createNativeQuery(sql);
        if (query == null || query.getResultList() == null) {
            return new ArrayList<>();
        }
        return query.getResultList();
    }
}