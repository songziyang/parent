package com.ydzb.account.service;

import com.ydzb.account.entity.WmUser;

/**
 * 新手宝产品结算流程service
 */
public interface IWmPrivilegeSettlementProcessService extends IWmDailyProductSettlementProcessService {

    /**
     * 处理老用户到期
     * @param user 用户
     * @throws Exception
     */
    void handleOldUserExpire(WmUser user) throws Exception;

    /**
     * 处理存管用户到期
     * @param user
     * @return 是否到期
     * @throws Exception
     */
    boolean handleDepositoryExpire(WmUser user) throws Exception;

    /**
     * 处理管存用户预处理成功
     * @param jxFreezeRecordId 即信交易冻结记录id
     */
    void handleDepositoryPretreatmentSuccess(Long jxFreezeRecordId) throws Exception;
}