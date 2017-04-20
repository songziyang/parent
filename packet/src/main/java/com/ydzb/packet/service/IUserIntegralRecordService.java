package com.ydzb.packet.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.packet.entity.UserIntegralRecord;
import com.ydzb.user.entity.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 积分记录
 */
public interface IUserIntegralRecordService extends IBaseService<UserIntegralRecord, Long> {


    public List<Object[]> findExportData(Map<String, Object> filter);

    public String exportExcel(List<Object[]> exchanges, String address);

    public void authentication(Long userId);

    /**
     * 生成积分记录
     * @param user
     * @param fundFlow
     * @param integral
     * @param balance
     * @param type
     * @param linkType
     * @return
     */
    public UserIntegralRecord generateIntegralRecord(User user, String fundFlow, BigDecimal integral,
            BigDecimal balance, Integer type, Integer linkType, Long linkId);
}
