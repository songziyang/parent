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
public class UserRedPacketRepository  {

    @PersistenceContext
    private EntityManager manager;

    /**
     * 查询导出excel数据
     * @param filter
     * @return
     */
    public List<Object[]> findExportData(Map<String, Object> filter) {
        String sql = " SELECT user.username, user.mobile, redpacket.give_value, redpacket.redpacket_type," +
                " redpacket.product_type, redpacket.status, redpacket.get_time, redpacket.user_use_time," +
                " redpacket.use_finish_time, redpacket.invest_finish_time" +
                " FROM wm_redpacket_users AS redpacket" +
                " LEFT JOIN wm_user_users AS user ON redpacket.user_id = user.id" +
                " WHERE 1 = 1";
        if (filter != null) {

            String username = (String) filter.get("username");
            if (StringUtils.isNotEmpty(username)) {
                sql += " AND user.username = '" + username + "'";
            }

            String mobile = (String) filter.get("mobile");
            if (StringUtils.isNotEmpty(mobile)) {
                sql += " AND user.mobile = '" + mobile + "'";
            }

            Byte triggerType = (Byte) filter.get("triggerType");
            if (triggerType != null) {
                sql += " AND redpacket.trigger_type = " + triggerType;
            }

            Byte redpacketType = (Byte) filter.get("redpacketType");
            if (redpacketType != null) {
                sql += " AND redpacket.redpacket_type = " + redpacketType;
            }

            Byte status = (Byte) filter.get("status");
            if (status != null) {
                sql += " AND redpacket.status = " + status;
            }

            String startTime = (String) filter.get("startTime");
            if (StringUtils.isNotEmpty(startTime)) {
                sql += " AND redpacket.get_time >= " + DateUtil.getSystemTimeDay(startTime);
            }

            String endTime = (String) filter.get("endTime");
            if (StringUtils.isNotEmpty(endTime)) {
                sql += " AND redpacket.get_time < " + DateUtil.getSystemTimeDay(DateUtil.addDay(endTime));
            }


            String startUseTime = (String) filter.get("startUseTime");
            if (StringUtils.isNotEmpty(startUseTime)) {
                sql += " AND redpacket.user_use_time >= " + DateUtil.getSystemTimeDay(startUseTime);
            }

            String endUseTime = (String) filter.get("endUseTime");
            if (StringUtils.isNotEmpty(endUseTime)) {
                sql += " AND redpacket.user_use_time < " + DateUtil.getSystemTimeDay(DateUtil.addDay(endUseTime));
            }


            String startUseFinishTime = (String) filter.get("startUseFinishTime");
            if (StringUtils.isNotEmpty(startUseFinishTime)) {
                sql += " AND redpacket.use_finish_time >= " + DateUtil.getSystemTimeDay(startUseFinishTime);
            }

            String endUseFinishTime = (String) filter.get("endUseFinishTime");
            if (StringUtils.isNotEmpty(endUseFinishTime)) {
                sql += " AND redpacket.use_finish_time < " + DateUtil.getSystemTimeDay(DateUtil.addDay(endUseFinishTime));
            }

        }

        sql += " ORDER BY redpacket.created DESC";
        Query query = manager.createNativeQuery(sql);
        if (query == null || query.getResultList() == null) {
            return new ArrayList<>();
        }
        return query.getResultList();
    }

    public EntityManager getManager() {
        return manager;
    }

    public void setManager(EntityManager manager) {
        this.manager = manager;
    }
}