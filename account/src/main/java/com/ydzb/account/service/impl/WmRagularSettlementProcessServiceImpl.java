package com.ydzb.account.service.impl;

import com.ydzb.account.entity.*;
import com.ydzb.account.repository.WmRagularUserAccountRepository;
import com.ydzb.account.service.*;
import com.ydzb.core.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 定存宝产品结算流程service抽象类
 */
@Service("ragularSettlementProcessService")
public class WmRagularSettlementProcessServiceImpl implements IWmPeriodicProductSettlementProcessService {

    @Autowired
    private WmRagularUserAccountRepository ragularUserAccountRepository;
    @Autowired
    private IWmUserUsersService wmUserUsersService;
    @Autowired
    private IWmRagularTransferService ragularTransferService;
    @Autowired
    @Qualifier("ragularNoRecastService")
    private IWmPeriodicProductHandleService ragularNoRecastService;  //定存到期不复投service
    @Autowired
    @Qualifier("ragularAllRecastService")
    private IWmPeriodicProductHandleService ragularAllRecastService; //定存到期本息复投service
    @Autowired
    @Qualifier("ragularPrincipalRecastService")
    private IWmPeriodicProductHandleService ragularPrincipalRecastService;   //定存到期本金复投service
    @Autowired
    @Qualifier("ragularNotLastStageService")
    private IWmPeriodicProductHandleService ragularNotLastStageService;  //定存到期不是最后一期service
    @Autowired
    private IWmRagularRefundService ragularRefundService;


    /**
     * 执行结算
     * @param userId 用户id
     * @throws Exception
     */
    @Override
    public void executeSettlement(Long userId) throws Exception {
        //系统当前日期
        Long curDate = DateUtil.getSystemTimeDay(DateUtil.curDate());
        // 查询定存结算记录
        List<WmRagularRefund> ragularRefunds = ragularUserAccountRepository.findWmRagularRefundByIdAndDate(userId, curDate);
        if (ragularRefunds != null && !ragularRefunds.isEmpty()) {
            for (WmRagularRefund ragularRefund : ragularRefunds) {
                // 判断定存结算是否存在
                if (ragularRefund != null) {
                    //查询定存购买记录
                    WmRagularUserAccount ragularUserAccount = ragularUserAccountRepository.findWmRagularUserAccountById(ragularRefund.getAccountId());
                    //判断定存购买是否存在
                    if (ragularUserAccount != null) {
                        WmUser user = wmUserUsersService.findById(userId);
                        //存管用户更改状态，然后添加记录
                        if (user != null && user.isDepositoryUser()) {
                            handleDepositorySettlement(user, ragularUserAccount, ragularRefund);
                            return;
                        }
                        handleOldUserSettlement(ragularUserAccount, ragularRefund);
                    }
                }
            }
        }
    }

    /**
     * 处理存管用户结算
     * @param user 用户
     * @param periodicProductAccount 定存宝账户
     * @param periodicProductRefund 定存宝还息记录
     * @throws Exception
     */
    @Override
    public void handleDepositorySettlement(WmUser user, IWmPeriodicProductAccount periodicProductAccount, IWmPeriodicProductRefund periodicProductRefund) throws Exception{
        WmRagularUserAccount ragularUserAccount = (WmRagularUserAccount)periodicProductAccount;
        WmRagularRefund ragularRefund = (WmRagularRefund)periodicProductRefund;
        //取消转让
        if (ragularUserAccount.getStatus() == WmRagularUserAccount.STATUS_TRANSFERING) ragularTransferService.cancelTransfer(ragularUserAccount);
        //判断是否需要进行结算
        if (ragularUserAccount.getStatus() == WmRagularUserAccount.STATUS_NOTEXPIRE || ragularUserAccount.getStatus() == WmRagularUserAccount.STATUS_TRANSFERING) {
            //判断定存宝是否是最后一期 0、非最后一期 1、最后一期
            if (ragularUserAccount.isNoExpire()) { //非复投
                ragularNoRecastService.executeDepositoryUserPretreatment(user, ragularRefund, ragularUserAccount);
            } else if (ragularUserAccount.isPrincipalExpire()) { //本金复投
                ragularPrincipalRecastService.executeDepositoryUserPretreatment(user, ragularRefund, ragularUserAccount);
            } else if (ragularUserAccount.isPrincipalInterestExpire()) {    //本息复投
                ragularAllRecastService.executeDepositoryUserPretreatment(user, ragularRefund, ragularUserAccount);
            }
            //更新状态为已经结算
            ragularRefundService.updateStatus(ragularRefund, WmRagularRefund.STATE_REFUND);
        }
    }

    /**
     * 处理老用户结算
     * @param periodicProductAccount 定存宝账户
     * @param periodicProductRefund 定存宝还息记录
     * @throws Exception
     */
    @Override
    public void handleOldUserSettlement(IWmPeriodicProductAccount periodicProductAccount, IWmPeriodicProductRefund periodicProductRefund) throws Exception {

        WmRagularUserAccount ragularUserAccount = (WmRagularUserAccount)periodicProductAccount;
        WmRagularRefund ragularRefund = (WmRagularRefund)periodicProductRefund;

        //取消转让
        if (ragularUserAccount.getStatus() == WmRagularUserAccount.STATUS_TRANSFERING) ragularTransferService.cancelTransfer(ragularUserAccount);
        //判断是否需要进行结算
        if (ragularUserAccount.getStatus() == WmRagularUserAccount.STATUS_NOTEXPIRE || ragularUserAccount.getStatus() == WmRagularUserAccount.STATUS_TRANSFERING) {
            //判断定存宝是否是最后一期 0、非最后一期 1、最后一期
            if (ragularRefund.getIsExpire() == WmRagularRefund.EXPIRE_LAST) {
                if (ragularUserAccount.isNoExpire()) { //非复投
                    ragularNoRecastService.executeOldUserSettlement(ragularRefund, ragularUserAccount);
                } else if (ragularUserAccount.isPrincipalExpire()) { //本金复投
                    ragularPrincipalRecastService.executeOldUserSettlement(ragularRefund, ragularUserAccount);
                } else if (ragularUserAccount.isPrincipalInterestExpire()) {    //本息复投
                    ragularAllRecastService.executeOldUserSettlement(ragularRefund, ragularUserAccount);
                }
            } else {
                ragularNotLastStageService.executeOldUserSettlement(ragularRefund, ragularUserAccount);
            }
        }
    }
}
