package com.ydzb.user.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.user.entity.UserFundBlokedLog;

import java.math.BigDecimal;
import java.util.List;

public interface IUserFundBlokedLogService extends IBaseService<UserFundBlokedLog, Long> {

    /**
     *
     * @param userId
     * @param type
     * @param linkType
     * @param linkId
     * @param fund
     * @param usableFund
     */
    public Long saveUserFundBlokedLog(Long userId,Integer type,Integer linkType,Long linkId,BigDecimal fund,BigDecimal usableFund);


    /**
     *
     * 查询冻结的资金
     *
     * @return
     */
    public List<UserFundBlokedLog> findUserFundBlokedLogByType(Long userId);


    /**
     *
     * 查询冻结的资金
     *
     * @return
     */
    public List<UserFundBlokedLog> findExUserFundBlokedLogByType(Long userId);

}
