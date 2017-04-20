package com.ydzb.account.service.impl;

import com.ydzb.account.entity.*;
import com.ydzb.account.repository.WmFreeUserAccountRepository;
import com.ydzb.account.service.*;
import com.ydzb.core.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.persistence.LockModeType;
import java.util.List;

/**
 * 定存宝产品结算流程service抽象类
 */
@Service("freeSettlementProcessService")
public class WmFreeSettlementProcessServiceImpl implements IWmPeriodicProductSettlementProcessService {

    @Autowired
    private IWmUserUsersService wmUserUsersService;
    @Autowired
    private WmFreeUserAccountRepository freeUserAccountRepository;
    @Autowired
    @Qualifier("freeNoRecastService")
    private IWmPeriodicProductHandleService freeNoRecastService;
    @Autowired
    @Qualifier("freeNotLatestStageService")
    private IWmPeriodicProductHandleService freeNotLatestStageService;
    @Autowired
    private IWmJxFreezeRecordService jxFreezeRecordService;
    @Autowired
    private IWmCmsRedeemQueueService wmCmsRedeemQueueService;

    /**
     * 执行结算
     * @param userId 用户id
     * @throws Exception
     */
    @Override
    public void executeSettlement(Long userId) throws Exception {
        //系统当前日期
        Long curDate = DateUtil.getSystemTimeDay(DateUtil.curDate());
        // 查询自由定存结算记录
        List<WmFreeRefund> freeRefunds = freeUserAccountRepository.queryOnes(userId, curDate, WmFreeRefund.STATE_NOTEXPIRE);
        if (freeRefunds != null) {
            for (WmFreeRefund freeRefund: freeRefunds) {
                //判断自由定存结算记录是否存在
                if (freeRefund != null) {
                    //查询自由定存结算记录
                    WmFreeUserAccount freeUserAccount = freeUserAccountRepository.queryById(freeRefund.getAccountId(), LockModeType.PESSIMISTIC_WRITE);
                    WmUser user = wmUserUsersService.findById(userId);
                    if (user != null && user.isDepositoryUser()) {
                        handleDepositorySettlement(user, freeUserAccount, freeRefund);
                        return;
                    }
                    handleOldUserSettlement(freeUserAccount, freeRefund);
                }
            }
        }
    }

    @Override
    public void handleDepositorySettlement(WmUser user, IWmPeriodicProductAccount periodicProductAccount, IWmPeriodicProductRefund periodicProductRefund) throws Exception {

        WmFreeUserAccount freeUserAccount = (WmFreeUserAccount)periodicProductAccount;
        WmFreeRefund freeRefund = (WmFreeRefund)periodicProductRefund;
        //处理不复投
        if (user != null && freeUserAccount != null && freeUserAccount.getExpireMode() == 0 && freeUserAccount.getIncomeMode() == 0) {
            //更改状态
            freeUserAccount.setStatus(WmFreeUserAccount.STATUS_EXPIRE_REFUNDING);
            freeUserAccountRepository.createOrUpdate(freeUserAccount);
            //新增交易冻结记录
            WmJxFreezeRecord record = jxFreezeRecordService.createOne(user.getId(), user.getAccountId(), freeUserAccount.getBuyFund(), freeRefund.getFund(), WmJxFreezeRecord.TYPE_EXPIRE,
                    WmJxFreezeRecord.LINKTYPE_FREE, freeUserAccount.getId(), freeUserAccount.getProductId(), null,
                    null, 0, 5,
                    freeUserAccount.getBuyTime() == null? null: DateUtil.convertToDay(freeUserAccount.getBuyTime()),
                    freeUserAccount.getExpireTime(), null,
                    WmJxFreezeRecord.STATE_UNDERHANDLE, DateUtil.getSystemTimeSeconds(),
                    null);
            //新增赎回队列列表
            wmCmsRedeemQueueService.createOne(user.getId(), null, freeUserAccount.getBuyFund(),
                    WmCmsRedeemQueue.PRODUCTTYPE_FREERAGULAR, freeRefund.getFund(), WmCmsRedeemQueue.TYPE_EXPIRE, WmCmsRedeemQueue.STATUS_UNDERHANDLE,
                    null, record == null? null: record.getId(), null);
        }

    }

    /**
     * 处理老用户结算
     * @param periodicProductAccount 随心存账户
     * @param periodicProductRefund 随心存还息记录
     * @throws Exception
     */
    @Override
    public void handleOldUserSettlement(IWmPeriodicProductAccount periodicProductAccount, IWmPeriodicProductRefund periodicProductRefund) throws Exception {

        WmFreeUserAccount freeUserAccount = (WmFreeUserAccount)periodicProductAccount;
        WmFreeRefund freeRefund = (WmFreeRefund)periodicProductRefund;
        if (freeUserAccount != null && freeRefund != null) {
            //判断自由定存是否到期 0 未到期 1已到期 2转让中 3转让成功
            if (freeUserAccount.getStatus() == WmFreeUserAccount.STATUS_NOTEXPIRE) {
                if (freeRefund.getIsExpire() == WmFreeRefund.EXPIRE_LAST) {
                    freeNoRecastService.executeOldUserSettlement(freeRefund, freeUserAccount);
                } else {
                    freeNotLatestStageService.executeOldUserSettlement(freeRefund, freeUserAccount);
                }
            }
        }
    }
}
