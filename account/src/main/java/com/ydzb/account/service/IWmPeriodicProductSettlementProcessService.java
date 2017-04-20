package com.ydzb.account.service;

import com.ydzb.account.entity.*;
import org.springframework.stereotype.Service;

/**
 * 周期产品结算流程service抽象类
 */
@Service
public interface IWmPeriodicProductSettlementProcessService extends IWmProductSettlementProcessService {

    /**
     * 处理存管用户结算
     * @param user 用户
     * @param periodicProductAccount 周期产品账户
     * @param periodicProductRefund 周期产品还息记录
     * @throws Exception
     */
    void handleDepositorySettlement(WmUser user, IWmPeriodicProductAccount periodicProductAccount, IWmPeriodicProductRefund periodicProductRefund) throws Exception;

    /**
     * 处理老用户结算
     * @param periodicProductAccount 周期产品账户
     * @param periodicProductRefund 周期产品还息记录
     * @throws Exception
     */
     void handleOldUserSettlement(IWmPeriodicProductAccount periodicProductAccount, IWmPeriodicProductRefund periodicProductRefund) throws Exception;
}
