package com.ydzb.account.service.impl;

import com.ydzb.account.entity.*;
import com.ydzb.account.repository.WmFreeUserAccountRepository;
import com.ydzb.account.repository.WmProductInfoRepository;
import com.ydzb.account.repository.WmUserUsersRepository;
import com.ydzb.account.service.*;
import com.ydzb.core.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.LockModeType;
import java.math.BigDecimal;

/**
 * 随心存不是最后一期service实现
 */
@Service("freeNotLatestStageService")
public class WmFreeNotLatestStageServiceImpl implements IWmPeriodicProductHandleService {

    @Autowired
    private IWmUserFuncGrandProfitRecordService userFuncGrandProfitRecordService;
    @Autowired
    private WmFreeUserAccountRepository freeUserAccountRepository;
    @Autowired
    private IWmFreeUserAccountService freeUserAccountService;
    @Autowired
    private IWmUserIncomeRecordService userIncomeRecordService;
    @Autowired
    private IWmUserFundInLogService userFundInLogService;
    @Autowired
    private IWmUserMoneyService userMoneyService;
    @Autowired
    private IWmUserFundRecordService userFundRecordService;
    @Autowired
    private IWmUserIncomeService userIncomeService;
    @Autowired
    private IWmJxFreezeRecordService jxFreezeRecordService;
    @Autowired
    private IWmCmsRedeemQueueService wmCmsRedeemQueueService;
    @Autowired
    private WmUserUsersRepository userUsersRepository;
    @Autowired
    private IWmInfoSmsTimerService infoSmsTimerService;
    @Autowired
    private IWmFreeRefundService freeRefundService;
    @Autowired
    private WmProductInfoRepository productInfoRepository;

    /**
     * 执行老用户结算
     * @param periodicProductRefund 周期产品还息记录
     * @param periodicProductAccount 周期产品账户
     * @throws Exception
     */
    @Override
    public IWmPeriodicProductAccount executeOldUserSettlement(IWmPeriodicProductRefund periodicProductRefund, IWmPeriodicProductAccount periodicProductAccount) throws Exception{

        WmFreeUserAccount freeUserAccount = (WmFreeUserAccount)periodicProductAccount;
        WmFreeRefund freeRefund = (WmFreeRefund)periodicProductRefund;
        if (freeUserAccount != null && freeRefund != null) {
            WmUserMoney userMoney = userMoneyService.queryOne(freeUserAccount.getUserId(), LockModeType.PESSIMISTIC_WRITE);
            //查询产品
            WmProductInfo productInfo = productInfoRepository.queryById(freeUserAccount.getProductId(), LockModeType.NONE);
            if (productInfo == null) productInfo = new WmProductInfo();
            //更新用户资金
            userMoneyService.increaseTotalAndUsableFund(freeUserAccount.getUserId(), freeRefund.getFund());
            //更新用户收益
            userIncomeService.increaseFreeExpire(freeUserAccount.getUserId(), freeRefund.getFund(), freeRefund.getVipFund());
            //创建用户收益记录
            userIncomeRecordService.createOne(freeUserAccount.getUserId(), productInfo.getName() + FundFlow.INCOME_,
                    freeRefund.getFund(), WmUserIncomeRecord.PTYPE_FREE, DateUtil.getSystemTimeSeconds());
            //更新随心存账户
            freeUserAccountService.handleNotLatestStage(freeUserAccount, freeRefund.getFund());
            //创建收益进账日志
            WmUserFundInLog userFundInLog = userFundInLogService.createOne(freeUserAccount.getUserId(), WmUserFundInLog.TYPE_FREE_MONTHINCOME, DateUtil.getSystemTimeSeconds(), BigDecimal.ZERO,
                    freeRefund.getFund(), userMoney.getUsableFund(), null, freeUserAccount.getId());
            //创建资金记录(随心存收益，进账）
            userFundRecordService.createOne(freeUserAccount.getUserId(), productInfo.getName() + FundFlow.INCOME_, freeRefund.getFund(), userFundInLog == null? null: userFundInLog.getId(),
                    WmUserFundRecord.TYPE_INFUND, WmUserFundRecord.FUNDTYPE_INCOME, userMoney.getUsableFund(), DateUtil.getSystemTimeSeconds());
            //发送站内信
            sendSiteContent(1, freeUserAccount.getUserId(), freeUserAccount.getBuyFund(), freeRefund.getFund(), productInfo);
            //更新还息记录
            freeRefundService.updateState(freeRefund, WmFreeRefund.STATE_EXPIRE);
            //VIP加息记录
            //创建用户赠予（红包）体验金收益记录
            userFuncGrandProfitRecordService.createOne(freeUserAccount.getUserId(), productInfo.getId(), freeRefund.getVipFund(), WmUserFuncGrandProfitRecord.PTYPE_FREE, WmUserFuncGrandProfitRecord.TYPE_VIP, DateUtil.getSystemTimeSeconds());
        }
        return null;
    }

    /**
     * 执行存管用户预处理
     * @param user 用户
     * @param periodicProductRefund 周期产品还息记录
     * @param periodicProductAccount 周期产品账户
     * @throws Exception
     */
    @Override
    public void executeDepositoryUserPretreatment(WmUser user, IWmPeriodicProductRefund periodicProductRefund, IWmPeriodicProductAccount periodicProductAccount) throws Exception {

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

    @Override
    public void executeDepositoryUserPretreatmentSuccess(Long jxFreezeRecordId) throws Exception {
        // TODO: 2017/5/12 完成代码
    }

    /**
     * 发送站内信
     * @param userId 用户id
     * @param fund 金额
     * @param income 收益
     * @param productInfo 产品
     */
    private void sendSiteContent(Integer type, Long userId, BigDecimal fund, BigDecimal income, WmProductInfo productInfo) {

        try {
            WmUser user = userUsersRepository.findByUserId(userId);
            if (user != null && productInfo != null) {
                String pname = productInfo.getName();
                if (type == 1) {
//                    smsHandleService.addSiteContent("deposit_settlement", userId, pname + "收益", "name:" + user.getUsername() + ",value:" + fund.intValue() + ",ptype:" + pname + ",money:" + income.setScale(2, BigDecimal.ROUND_DOWN), 0);
                    infoSmsTimerService.sendWmInfoSmsTimer("deposit_settlement", user.getMobile(), "name:" + user.getUsername() + ",value:" + fund.intValue() + ",ptype:" + pname + ",money:" + income.setScale(2, BigDecimal.ROUND_DOWN));
                }

                if (type == 2) {
//                    smsHandleService.addSiteContent("deposit_expire", userId, pname + "到期", "name:" + user.getUsername() + ",value:" + fund.intValue() + ",ptype:" + pname + ",fund:" + fund.intValue() + ",income:" + income.setScale(2, BigDecimal.ROUND_DOWN), 0);
                    infoSmsTimerService.sendWmInfoSmsTimer("deposit_expire", user.getMobile(), "name:" + user.getUsername() + ",value:" + fund.intValue() + ",ptype:" + pname + ",fund:" + fund.intValue() + ",income:" + income.setScale(2, BigDecimal.ROUND_DOWN));
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}