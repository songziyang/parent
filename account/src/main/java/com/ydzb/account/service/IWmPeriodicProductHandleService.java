package com.ydzb.account.service;

import com.ydzb.account.entity.IWmPeriodicProductAccount;
import com.ydzb.account.entity.IWmPeriodicProductRefund;
import com.ydzb.account.entity.WmRagularUserAccount;
import com.ydzb.account.entity.WmUser;

/**
 * 周期产品处理service接口
 */
public interface IWmPeriodicProductHandleService {

    /**
     * 执行老用户结算
     * @param periodicProductRefund
     * @param periodicProductAccount
     * @return 复投生称的新ragularUserAccount，如果为随心存或者（定存不复投或者非最后一期）则返回null
     * @throws Exception
     */
    IWmPeriodicProductAccount executeOldUserSettlement(IWmPeriodicProductRefund periodicProductRefund, IWmPeriodicProductAccount periodicProductAccount) throws Exception;

    /**
     * 执行存管用户预处理
     * @param user 用户
     * @param periodicProductRefund 周期产品还息记录
     * @param periodicProductAccount 周期产品账户
     * @throws Exception
     */
    void executeDepositoryUserPretreatment(WmUser user, IWmPeriodicProductRefund periodicProductRefund, IWmPeriodicProductAccount periodicProductAccount) throws Exception;

    /**
     * 执行存管用户预处理成功
     * @param jxFreezeRecordId 即信交易冻结记录
     * @throws Exception
     */
    void executeDepositoryUserPretreatmentSuccess(Long jxFreezeRecordId) throws Exception;
}