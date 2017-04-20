package com.ydzb.packet.repository;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class RewardRecordRepository {

    @PersistenceContext
    private EntityManager manager;

    /**
     * 查询导出excel数据
     * @param filters
     * @return
     */
    public List<Object[]> findExportData(Map<String, Object> filters) {

        String sql = " SELECT user.real_name AS userRealName, user.mobile AS userMobile, reward.reward_name, reward.reward_type, reward.reward_val," +
                " reward.reward_time, reward.exchage_type, reward.exchage_time, reward.real_name AS rewardRealName," +
                " reward.mobile AS rewardMobile, reward.address, reward.remark FROM wm_reward_records AS reward" +
                " JOIN wm_user_users AS user ON user.id = reward.user_id WHERE 1 = 1";

        if (filters != null) {
            Byte rewardType = (Byte) filters.get("rewardType");
            if (rewardType == null) {
                sql += " AND reward.reward_type <= 5";
            } else if (rewardType != 0) {
                sql += " AND reward.reward_type = " + rewardType;
            }

            Byte exchageType = (Byte) filters.get("exchageType");
            if (exchageType != null && exchageType != -1) {
                sql += " AND reward.exchage_type = " + exchageType;
            }

            String realName = (String) filters.get("realName");
            if (StringUtils.isNotEmpty(realName)) {
                sql += " AND user.real_name = '" + realName + "'";
            }

            String mobile = (String) filters.get("mobile");
            if (StringUtils.isNotEmpty(mobile)) {
                sql += " AND user.mobile = '" + mobile + "'";
            }
        }

        sql += " ORDER BY reward.created DESC";
        Query query = manager.createNativeQuery(sql);
        if (query == null || query.getResultList() == null || query.getResultList().isEmpty()) {
            return new ArrayList<Object[]>();
        }
        return query.getResultList();
    }
}
