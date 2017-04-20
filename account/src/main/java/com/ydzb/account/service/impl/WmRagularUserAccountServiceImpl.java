package com.ydzb.account.service.impl;

import com.ydzb.account.context.WmPlatformRecordLinkType;
import com.ydzb.account.entity.*;
import com.ydzb.account.repository.*;
import com.ydzb.account.service.*;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.sms.service.ISmsHandleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


@Service
public class WmRagularUserAccountServiceImpl implements IWmRagularUserAccountService {


    @Autowired
    private WmRagularUserAccountRepository ragularUserAccountRepository;
    @Autowired
    private ISmsHandleService smsHandleService;
    @Autowired
    private IWmInfoSmsTimerService infoSmsTimerService;
    @Autowired
    private IWmUserIntegralService userIntegralService;
    @Autowired
    private WmEntityRepository wmEntityRepository;
    @Autowired
    private WmCurrentUserAccountRepository currentUserAccountRepository;
    @Autowired
    private WmProductInfoRepository wmProductInfoRepository;
    @Autowired
    private IWmActivityZhongqiuService activityZhongqiuService;
    @Autowired
    private IWmPlatformUserService wmPlatformUserService;
    @Autowired
    private IWmJxFreezeRecordService jxFreezeRecordService;
    @Autowired
    private WmRagularRefundRepository wmRagularRefundRepository;


    private static String addMonth(String strdate) {
        String dateresult = null; // 返回的日期字符串
        try {
            Date date = new Date(); // 构造一个日期型中间变量
            // 创建格式化格式
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            // 加减日期所用
            GregorianCalendar gc = new GregorianCalendar();
            date = df.parse(strdate); // 将字符串格式化为日期型
            gc.setTime(date); // 得到gc格式的时间
            gc.add(2, 1); // 2表示月的加减，年代表1依次类推(周,天。。)
            // 把运算完的时间从新赋进对象
            gc.set(gc.get(gc.YEAR), gc.get(gc.MONTH), gc.get(gc.DATE));
            // 在格式化回字符串时间
            dateresult = df.format(gc.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateresult;
    }


    /**
     * 定存宝转让结算
     *
     * @param userId 用户ID
     * @throws Exception
     */
    @Override
    public void accountRagularTransfer(Long userId) throws Exception {

        //系统七天前日期
        Long curDate = DateUtil.getSystemTimeDay(DateUtil.curDate()) - 7 * 24 * 3600;

        //查询定存转让记录
        List<WmRagularUserAccount> ragularUserAccounts = ragularUserAccountRepository.findWmRagularUserAccount(userId, curDate);
        if (ragularUserAccounts != null && !ragularUserAccounts.isEmpty()) {
            for (WmRagularUserAccount userAccount : ragularUserAccounts) {
                //判断转让记录是否为空
                if (userAccount != null) {
                    //转让取消
                    transferCancel(userAccount);
                }
            }
        }
    }

    /**
     * 定存宝转让取消
     *
     * @param userAccount
     * @throws Exception
     */
    private void transferCancel(WmRagularUserAccount userAccount) throws Exception {
        if (userAccount != null) {
            //定存转让记录
            List<WmRagularTransfer> ragularTransferLst = ragularUserAccountRepository.findWmRagularTransfer(userAccount.getUserId(), userAccount.getId());
            if (ragularTransferLst != null && ragularTransferLst.size() > 0) {
                for (WmRagularTransfer ragularTransfer : ragularTransferLst) {
                    if (ragularTransfer != null) {
                        ragularTransfer.setStatus(3);
                        ragularUserAccountRepository.updateWmRagularTransfer(ragularTransfer);
                    }
                }
            }

            userAccount.setApplyTransferTime(null);
            userAccount.setApplyOptionTime(null);
            userAccount.setStatus(0);
            ragularUserAccountRepository.updateWmRagularUserAccount(userAccount);

            //取消转让站内信和短信
            sendTransferCancel(userAccount.getUserId(), userAccount.getBuyFund());
        }
    }


    /**
     * 通过ragularAccountId
     * @throws Exception
     */
    @Override
    public void accountRagularUserAccountByFreeRecord(WmJxFreezeRecord freezeRecord) throws Exception {
        if (freezeRecord != null) {
            WmRagularUserAccount ragularUserAccount = ragularUserAccountRepository.queryById(freezeRecord.getLinkId());
            if (ragularUserAccount != null) {
                WmRagularRefund ragularRefund = wmRagularRefundRepository.queryByAccount(ragularUserAccount.getId(), WmRagularRefund.ORG_RAGULAR, WmRagularRefund.EXPIRE_LAST);
                if (ragularRefund != null) {
                    handleRefundBusiness(ragularUserAccount, ragularRefund);
//                    freezeRecord.setStatus(WmJxFreezeRecord.STATE_HANDLE_FINISH);
                    jxFreezeRecordService.createOrUpdate(freezeRecord);
                }
            }
        }
    }

    /**
     * 更新状态
     * @param ragularUserAccount 定存宝账户
     * @param status 目标状态
     * @return
     */
    @Override
    public WmRagularUserAccount updateStatus(WmRagularUserAccount ragularUserAccount, Integer status) throws Exception {
        if (ragularUserAccount != null) {
            ragularUserAccount.setStatus(status);
            return ragularUserAccountRepository.createOrUpdate(ragularUserAccount);
        }
        return null;
    }
    /**
     * 处理退款业务
     * @param ragularUserAccount
     * @param ragularRefund
     * @throws Exception
     */
    private void handleRefundBusiness(WmRagularUserAccount ragularUserAccount, WmRagularRefund ragularRefund) throws Exception {
        //取消转让
        if (ragularUserAccount.getStatus() == 2) {
            transferCancel(ragularUserAccount);
        }

        //查询产品
        WmProductInfo productInfo = ragularUserAccountRepository.findWmProductInfoByProductId(ragularUserAccount.getProductId());
        if (productInfo == null) {
            productInfo = new WmProductInfo();
        }

        //判断定存宝是否到期 0 未到期 1已到期 2转让中 3转让成功
        if (ragularUserAccount.getStatus() == 0 || ragularUserAccount.getStatus() == 2) {

            //收益名称
            String productInfoNames = "";
            if (ragularRefund.getStage() == 1) {
                productInfoNames = productInfo.getName() + FundFlow.INCOME_;
            } else {
                productInfoNames = productInfo.getName() + FundFlow.INCOME;
            }

            //判断定存宝是否是最后一期 0、非最后一期 1、最后一期
            if (ragularRefund.getIsExpire() == 1) {

                //非复投
                if (ragularUserAccount.getExpireMode() == 0) {

                    //更新用户收益
                    updateUserIncome(ragularRefund.getUserId(), ragularRefund.getFund(), ragularRefund.getRedpacketFund(), ragularRefund.getVipFund(), ragularRefund.getVouchersFund());

                    //用户收益记录
                    saveWmUserIncomeRecord(ragularRefund.getUserId(), ragularRefund.getFund(), productInfoNames, 2);

                    //更新用户投资
                    updateUserInvestinfo(ragularRefund.getUserId(), ragularUserAccount.getBuyFund());

                    //更新用户定存购买交易
                    updateWmRagularUserAccount(ragularUserAccount, ragularUserAccount.getBuyFund(), ragularRefund.getFund(), 1);

                    //保存定存宝到期日志
                    Long logId = saveWmRagularOverLog(ragularUserAccount.getUserId(), ragularUserAccount.getId(), ragularUserAccount.getBuyFund(), ragularUserAccount.getGrandFund());

                    //保存定存宝交易记录
                    saveWmRagularTradeRecored(ragularUserAccount.getUserId(), ragularUserAccount.getProductId(), ragularUserAccount.getBuyFund(), logId, 2, ragularUserAccount.getDays(), "到期");

                    //更新用户资金-收益
                    updateUserMoney(ragularRefund.getUserId(), BigDecimal.ZERO, ragularRefund.getFund());

                    //定存收益进账日志
                    Long incomeLogId = saveWmUserFundInLog(ragularUserAccount.getUserId(), ragularUserAccount.getId(), 5, BigDecimal.ZERO, ragularRefund.getFund());

                    //定存收益记录
                    saveWmUserFundRecord(ragularUserAccount.getUserId(), productInfoNames, ragularRefund.getFund(), 1, 3, incomeLogId);

                    //更新用户资金-本金
                    updateUserMoney(ragularRefund.getUserId(), ragularUserAccount.getBuyFund(), BigDecimal.ZERO);

                    //定存本金进账日志
                    Long fundLogId = saveWmUserFundInLog(ragularUserAccount.getUserId(), ragularUserAccount.getId(), 3, ragularUserAccount.getBuyFund(), BigDecimal.ZERO);

                    //定存资金记录
                    saveWmUserFundRecord(ragularUserAccount.getUserId(), FundFlow.R_OVER, ragularUserAccount.getBuyFund(), 1, 2, fundLogId);

                    //发送站内信
                    sendSiteContent(2, ragularUserAccount.getUserId(), ragularUserAccount.getBuyFund(), ragularRefund.getFund(), ragularUserAccount.getProductId());

                    //定存到期
                    wmPlatformUserService.redeem(ragularUserAccount.getBuyFund(), ragularUserAccount.getUserId(), WmPlatformRecordLinkType.EXPIRE_RAGULAR, WmPlatformRecordLinkType.EXPIRE_RAGULAR.getDesc());

                } else {

                    //本金复投
                    if (ragularUserAccount.getExpireMode() == 1) {

                        //更新用户资金
                        updateUserMoney(ragularRefund.getUserId(), BigDecimal.ZERO, ragularRefund.getFund());

                        //更新用户收益
                        updateUserIncome(ragularRefund.getUserId(), ragularRefund.getFund(), ragularRefund.getRedpacketFund(), ragularRefund.getVipFund(), ragularRefund.getVouchersFund());

                        //用户收益记录
                        saveWmUserIncomeRecord(ragularRefund.getUserId(), ragularRefund.getFund(), productInfoNames, 2);

                        //更新用户定存购买交易
                        updateWmRagularUserAccount(ragularUserAccount, ragularUserAccount.getBuyFund(), ragularRefund.getFund(), 1);

                        //保存定存宝到期日志
                        Long logId = saveWmRagularOverLog(ragularUserAccount.getUserId(), ragularUserAccount.getId(), ragularUserAccount.getBuyFund(), ragularUserAccount.getGrandFund());

                        //保存定存宝到期交易记录
                        saveWmRagularTradeRecored(ragularUserAccount.getUserId(), ragularUserAccount.getProductId(), ragularUserAccount.getBuyFund(), logId, 2, ragularUserAccount.getDays(), "到期");

                        //定存宝收益进账日志
                        Long inLogId = saveWmUserFundInLog(ragularUserAccount.getUserId(), ragularUserAccount.getId(), 5, BigDecimal.ZERO, ragularRefund.getFund());

                        //定存收益记录
                        saveWmUserFundRecord(ragularUserAccount.getUserId(), productInfoNames, ragularRefund.getFund(), 1, 3, inLogId);

                        //发送站内信
                        sendSiteContent(1, ragularUserAccount.getUserId(), ragularUserAccount.getBuyFund(), ragularRefund.getFund(), ragularUserAccount.getProductId());

                        //保存定存购买交易记录
                        saveWmRagularUserAccount(ragularUserAccount.getUserId(), ragularUserAccount.getBuyFund(), ragularUserAccount.getProductId(), ragularRefund.getOrganization(), ragularUserAccount.getExpireNum());

                        //发送站内信
                        sendSiteContent(3, ragularUserAccount.getUserId(), ragularUserAccount.getBuyFund(), BigDecimal.ZERO, ragularUserAccount.getProductId());

                    }

                    //本息复投
                    if (ragularUserAccount.getExpireMode() == 2) {

                        //用户收益取整舍掉小数部分
                        BigDecimal intIncome = ragularRefund.getFund().setScale(0, BigDecimal.ROUND_DOWN);

                        //用户收益小数部分
                        BigDecimal dobleIncome = ragularRefund.getFund().subtract(intIncome);

                        //更新用户资金
                        updateUserMoneyByExpire(ragularRefund.getUserId(), ragularRefund.getFund(), dobleIncome);

                        //更新用户投资
                        updateUserInvestinfoByExpire(ragularRefund.getUserId(),intIncome);

                        //更新用户收益
                        updateUserIncome(ragularRefund.getUserId(), ragularRefund.getFund(), ragularRefund.getRedpacketFund(), ragularRefund.getVipFund(), ragularRefund.getVouchersFund());

                        //用户收益记录
                        saveWmUserIncomeRecord(ragularRefund.getUserId(), ragularRefund.getFund(), productInfoNames, 2);

                        //更新用户定存购买交易
                        updateWmRagularUserAccount(ragularUserAccount, ragularUserAccount.getBuyFund(), ragularRefund.getFund(), 1);

                        //保存定存宝到期日志
                        Long logId = saveWmRagularOverLog(ragularUserAccount.getUserId(), ragularUserAccount.getId(), ragularUserAccount.getBuyFund(), ragularUserAccount.getGrandFund());

                        //保存定存宝到期交易记录
                        saveWmRagularTradeRecored(ragularUserAccount.getUserId(), ragularUserAccount.getProductId(), ragularUserAccount.getBuyFund(), logId, 2, ragularUserAccount.getDays(), "到期");

                        //定存宝收益进账日志
                        Long inLogId = saveWmUserFundInLog(ragularUserAccount.getUserId(), ragularUserAccount.getId(), 5, BigDecimal.ZERO, dobleIncome);

                        //定存收益记录
                        saveWmUserFundRecord(ragularUserAccount.getUserId(), productInfoNames, dobleIncome, 1, 3, inLogId);

                        //发送站内信
                        sendSiteContent(1, ragularUserAccount.getUserId(), ragularUserAccount.getBuyFund(), dobleIncome, ragularUserAccount.getProductId());

                        //保存定存购买交易记录
                        saveWmRagularUserAccount(ragularUserAccount.getUserId(), ragularUserAccount.getBuyFund().add(intIncome), ragularUserAccount.getProductId(), ragularRefund.getOrganization(), ragularUserAccount.getExpireNum());

                        //发送站内信
                        sendSiteContent(3, ragularUserAccount.getUserId(), ragularUserAccount.getBuyFund().add(intIncome), BigDecimal.ZERO, ragularUserAccount.getProductId());


                    }


                }

            } else {

                //更新用户资金
                updateUserMoney(ragularRefund.getUserId(), BigDecimal.ZERO, ragularRefund.getFund());

                //更新用户收益
                updateUserIncome(ragularRefund.getUserId(), ragularRefund.getFund(), ragularRefund.getRedpacketFund(), ragularRefund.getVipFund(), ragularRefund.getVouchersFund());

                //用户收益记录
                saveWmUserIncomeRecord(ragularRefund.getUserId(), ragularRefund.getFund(), productInfoNames, 2);

                //更新用户定存购买交易
                updateWmRagularUserAccount(ragularUserAccount, BigDecimal.ZERO, ragularRefund.getFund(), 0);

                //定存收益进账日志
                Long logId = saveWmUserFundInLog(ragularUserAccount.getUserId(), ragularUserAccount.getId(), 5, BigDecimal.ZERO, ragularRefund.getFund());

                //定存资金记录
                saveWmUserFundRecord(ragularUserAccount.getUserId(), productInfoNames, ragularRefund.getFund(), 1, 3, logId);

                //发送站内信
                sendSiteContent(1, ragularUserAccount.getUserId(), ragularUserAccount.getBuyFund(), ragularRefund.getFund(), ragularUserAccount.getProductId());

            }

            //加息券收益
            saveWmUserFuncGrandProfitRecord(ragularRefund.getUserId(), ragularUserAccount.getProductId(), ragularRefund.getRedpacketFund(), 1);

            //VIP加息收益
            saveWmUserFuncGrandProfitRecord(ragularRefund.getUserId(), ragularUserAccount.getProductId(), ragularRefund.getVipFund(), 3);

            //代金券收益
            saveWmUserFuncGrandProfitRecord(ragularRefund.getUserId(), ragularUserAccount.getProductId(), ragularRefund.getVouchersFund(), 4);

            //更新状态为已经结算
            ragularRefund.setState(1);
            ragularUserAccountRepository.updateWmRagularRefund(ragularRefund);

        }
    }

    /**
     * 保存收益记录
     *
     * @param userId
     * @param income
     * @param name
     * @param ptype
     */
    public void saveWmUserIncomeRecord(Long userId, BigDecimal income, String name, Integer ptype) {
        WmUserIncomeRecord userIncomeRecord = new WmUserIncomeRecord();
        userIncomeRecord.setUserId(userId);
        userIncomeRecord.setIncome(income);
        userIncomeRecord.setName(name);
        userIncomeRecord.setOptime(DateUtil.getSystemTimeSeconds());
        userIncomeRecord.setPtype(ptype);
        ragularUserAccountRepository.saveWmUserIncomeRecord(userIncomeRecord);
    }

    /**
     * 收益复投
     *
     * @param userId
     * @param income
     */
    public void saveIncomeMode(Long userId, BigDecimal income) {
        //用户收益取整 舍掉小数位
        BigDecimal intIncome = income.setScale(0, BigDecimal.ROUND_DOWN);
        //WmUser user, BigDecimal income, WmProductInfo productInfo, WmCurrentUserAccount currentUserAccount
        WmProductInfo productInfo = wmProductInfoRepository.findWmProductInfo();
        buyCurrent(userId, productInfo, intIncome, 4, 1);
    }

    /**
     * 保存用户赠予（红包）收益记录
     *
     * @param userId
     * @param productId
     * @param fund
     */
    public void saveWmUserFuncGrandProfitRecord(Long userId, Long productId, BigDecimal fund, Integer type) {
        if (fund != null && fund.doubleValue() > 0) {
            WmUserFuncGrandProfitRecord profitRecord = new WmUserFuncGrandProfitRecord();
            profitRecord.setProductId(productId);
            profitRecord.setUserId(userId);
            profitRecord.setType(type);
            profitRecord.setPtype(2);
            profitRecord.setFund(fund);
            profitRecord.setRecordDate(DateUtil.getSystemTimeSeconds());
            ragularUserAccountRepository.saveWmUserFuncGrandProfitRecord(profitRecord);
        }
    }




    /**
     * 保存定存复投交易记录
     *
     * @param userId       用户ID
     * @param fund         金额
     * @param productId    产品ID
     * @param organization 组织
     * @param expireNum    复投次数
     */
    public void saveWmRagularUserAccount(Long userId, BigDecimal fund, Long productId, Integer organization, Integer expireNum) {

        //购买时产品
        WmProductInfo productInfo = ragularUserAccountRepository.findWmProductInfoById(productId);

        //系统明天日期
        Long currentDate = DateUtil.getSystemTimeDay(DateUtil.curDate()) + 24 * 3600;


        //VIP加息收益查询用户
        WmUser user = ragularUserAccountRepository.findWmUserById(userId);
        //VIP利率
        BigDecimal vipApr = BigDecimal.ZERO;
        //判断用户
        if (user != null) {
            //用户等级
            WmVipGrade vipGrade = ragularUserAccountRepository.findWmVipGradeById(user.getUserLeve());
            if (vipGrade != null) {
                vipApr = vipGrade.getDepositApr();
            }
        }

        if (productInfo != null) {

            //收益
            BigDecimal income = BigDecimal.ZERO;

            BigDecimal vipIncome = BigDecimal.ZERO;

            //首次复投为空
            if (expireNum == null) expireNum = 0;

            //活动利率
            if (productInfo.getActivityRate() == null) productInfo.setActivityRate(BigDecimal.ZERO);
            //产品利率 =  发布利率 - 活动利率
            BigDecimal interestRate = productInfo.getInterestRate().subtract(productInfo.getActivityRate());

            //当期产品
            WmProductInfo currentProductInfo = ragularUserAccountRepository.findWmProductInfo(productInfo.getCylcleDays());
            if (currentProductInfo != null) {
                if (currentProductInfo.getActivityRate() == null) currentProductInfo.setActivityRate(BigDecimal.ZERO);
                //原始产品利率+当前产品活动利率
                interestRate = interestRate.add(currentProductInfo.getActivityRate());
            }

            if (productInfo.getCylcleDays() == 365) {
                vipIncome = fund.multiply(vipApr).divide(new BigDecimal(100), 6, BigDecimal.ROUND_HALF_DOWN);
                income = fund.multiply(interestRate.add(vipApr)).divide(new BigDecimal(100), 6, BigDecimal.ROUND_HALF_DOWN);
            } else {
                vipIncome = fund.multiply(vipApr).multiply(new BigDecimal(productInfo.getCylcleDays())).divide(new BigDecimal(36000), 6, BigDecimal.ROUND_HALF_DOWN);
                income = fund.multiply(interestRate.add(vipApr)).multiply(new BigDecimal(productInfo.getCylcleDays())).divide(new BigDecimal(36000), 6, BigDecimal.ROUND_HALF_DOWN);
            }

            //定存交易记录
            WmRagularUserAccount ragularUserAccount = new WmRagularUserAccount();
            ragularUserAccount.setUserId(userId);
            ragularUserAccount.setProductId(productInfo.getId());
            ragularUserAccount.setAllFund(fund.add(income));
            ragularUserAccount.setBuyTime(DateUtil.getSystemTimeSeconds());
            ragularUserAccount.setBuyFund(fund);
            ragularUserAccount.setGrandFund(BigDecimal.ZERO);
            ragularUserAccount.setInterestFund(income);
            ragularUserAccount.setBuyCopies(fund.intValue());
            ragularUserAccount.setGrandApr(BigDecimal.ZERO);
            ragularUserAccount.setVipApr(vipApr);
            ragularUserAccount.setApr(interestRate);
            ragularUserAccount.setExpireTime(currentDate + productInfo.getCylcleDays() * 24 * 3600);
            ragularUserAccount.setExpireMode(0);
            ragularUserAccount.setIncomeMode(0);
            ragularUserAccount.setBuyType(1);
            ragularUserAccount.setDays(productInfo.getCylcleDays());
            ragularUserAccount.setStatus(0);
            ragularUserAccount.setTransCount(0);
            ragularUserAccount.setRefundCount(0);
            ragularUserAccount.setRefundable(BigDecimal.ZERO);
            ragularUserAccount.setPredictIncome(income);
            ragularUserAccount.setExpireNum(expireNum + 1);
            ragularUserAccount.setCurProductId(currentProductInfo.getId());
            //保存定存交易记录
            Long accountId = ragularUserAccountRepository.saveWmRagularUserAccount(ragularUserAccount);

            //复投获得积分
            userIntegralService.investObtainIntegralNew(userId, fund, accountId);

            //定存还息记录
            WmRagularRefund ragularRefund = new WmRagularRefund();
            if (productInfo.getCylcleDays() == 365) {
                DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String addMonth = sdf.format(DateUtil.getSystemTimeMillisecond(currentDate));
                for (int i = 1; i <= 12; i++) {
                    addMonth = addMonth(addMonth);
                }
                ragularRefund.setUserId(userId);
                ragularRefund.setAccountId(accountId);
                ragularRefund.setStage(1);
                ragularRefund.setCurStage(1);
                ragularRefund.setRefundTime(DateUtil.getSystemTimeDay(addMonth));
                ragularRefund.setFund(income);
                ragularRefund.setRedpacketFund(BigDecimal.ZERO);
                ragularRefund.setState(0);
                ragularRefund.setIsExpire(1);
                ragularRefund.setOrganization(organization);
                ragularRefund.setVipFund(vipIncome);
                ragularRefund.setVouchersFund(BigDecimal.ZERO);
            } else {
                ragularRefund.setUserId(userId);
                ragularRefund.setAccountId(accountId);
                ragularRefund.setStage(1);
                ragularRefund.setCurStage(1);
                ragularRefund.setRefundTime(currentDate + productInfo.getCylcleDays() * 24 * 3600);
                ragularRefund.setFund(income);
                ragularRefund.setRedpacketFund(BigDecimal.ZERO);
                ragularRefund.setState(0);
                ragularRefund.setIsExpire(1);
                ragularRefund.setOrganization(organization);
                ragularRefund.setVipFund(vipIncome);
                ragularRefund.setVouchersFund(BigDecimal.ZERO);
            }
            //保存还息记录
            ragularUserAccountRepository.saveWmRagularRefund(ragularRefund);

            //更新定存到期记录
            ragularUserAccount.setExpireTime(ragularRefund.getRefundTime());
            ragularUserAccountRepository.updateWmRagularUserAccount(ragularUserAccount);

            //定存购买日志
            WmRagularBuyLog ragularBuyLog = new WmRagularBuyLog();
            ragularBuyLog.setProductId(productInfo.getId());
            ragularBuyLog.setAccountId(accountId);
            ragularBuyLog.setUserId(userId);
            ragularBuyLog.setBuyFund(fund);
            ragularBuyLog.setBuyCopies(fund.intValue());
            ragularBuyLog.setApr(interestRate);
            ragularBuyLog.setGrandApr(BigDecimal.ZERO);
            ragularBuyLog.setBuyTime(DateUtil.getSystemTimeSeconds());
            ragularBuyLog.setVipApr(vipApr);
            ragularBuyLog.setDevice(4);
            Long buyLogId = ragularUserAccountRepository.saveWmRagularBuyLog(ragularBuyLog);

            //保存定存宝交易记录
            saveWmRagularTradeRecored(ragularUserAccount.getUserId(), ragularUserAccount.getProductId(), ragularUserAccount.getBuyFund(), buyLogId, 3, ragularUserAccount.getDays(), "复投");

            WmUserIncome userIncome = ragularUserAccountRepository.findWmUserIncomeByUserId(userId);
            if (userIncome != null) {
                //预期收益
                userIncome.setPredictIncome(userIncome.getPredictIncome().add(income));
                //更新用户收益
                ragularUserAccountRepository.updateWmUserIncome(userIncome);
            }


        }

    }

    /**
     * 保存进账日志
     *
     * @param userId
     * @param linkId
     * @param type
     * @param incomeFund
     * @param incomeInterest
     * @return
     */
    public Long saveWmUserFundInLog(Long userId, Long linkId, Integer type, BigDecimal incomeFund, BigDecimal incomeInterest) {
        WmUserMoney userMoney = ragularUserAccountRepository.findWmUserMoneyByUserId(userId);
        if (userMoney != null) {
            // 进账日志
            WmUserFundInLog log = new WmUserFundInLog();
            log.setUserId(userId);
            log.setLinkId(linkId);
            log.setType(type);
            log.setReceiptsTime(DateUtil.getSystemTimeSeconds());
            log.setIncomeFund(incomeFund);
            log.setIncomeInterest(incomeInterest);
            log.setUsableFund(userMoney.getUsableFund());
            log.setRemark(null);
            ragularUserAccountRepository.saveWmUserFundInLog(log);
            return log.getId();
        }
        return null;
    }

    /**
     * 保存资金记录
     *
     * @param userId
     * @param fundflow
     * @param fund
     * @param type
     * @param fundType
     * @param logId
     */
    public void saveWmUserFundRecord(Long userId, String fundflow, BigDecimal fund, Integer type, Integer fundType, Long logId) {
        WmUserMoney userMoney = ragularUserAccountRepository.findWmUserMoneyByUserId(userId);
        if (userMoney != null) {
            WmUserFundRecord userFundRecord = new WmUserFundRecord();
            userFundRecord.setUserId(userId);
            userFundRecord.setFundflow(fundflow);
            userFundRecord.setFund(fund);
            userFundRecord.setType(type);
            userFundRecord.setFundType(fundType);
            userFundRecord.setBalance(userMoney.getUsableFund());
            userFundRecord.setRecordTime(DateUtil.getSystemTimeSeconds());
            userFundRecord.setLogId(logId);
            ragularUserAccountRepository.saveWmUserFundRecord(userFundRecord);
        }
    }

    /**
     * 冻结日志
     *
     * @param userId
     * @param type
     * @param linkType
     * @param linkId
     * @param fund
     * @param usableFund
     * @return
     */
    public Long saveWmUserFundBlokedLog(Long userId, Integer type, Integer linkType, Long linkId, BigDecimal fund, BigDecimal usableFund) {
        //资金解冻日志
        WmUserFundBlokedLog userFundBlokedLog = new WmUserFundBlokedLog();
        userFundBlokedLog.setUserId(userId);
        userFundBlokedLog.setType(type);
        userFundBlokedLog.setLinkType(linkType);
        userFundBlokedLog.setLinkId(linkId);
        userFundBlokedLog.setFund(fund);
        userFundBlokedLog.setUsableFund(usableFund);
        userFundBlokedLog.setOperationTime(DateUtil.getSystemTimeSeconds());
        return ragularUserAccountRepository.saveWmUserFundBlokedLog(userFundBlokedLog);
    }

    public Long saveWmCurrentPrepay(Long userId, BigDecimal fund) {
        WmCurrentPrepay currentPrepay = new WmCurrentPrepay();
        currentPrepay.setUserId(userId);
        currentPrepay.setBuyFund(fund);
        currentPrepay.setBuyCopies(fund.intValue());
        currentPrepay.setType(0);
        currentPrepay.setStatus(0);
        currentPrepay.setPrepayTime(DateUtil.getSystemTimeSeconds());
        Long prepayId = ragularUserAccountRepository.saveWmCurrentPrepay(currentPrepay);

        WmCurrentQueue currentQueue = new WmCurrentQueue();
        currentQueue.setUserId(userId);
        currentQueue.setDayloanPrepayId(prepayId);
        currentQueue.setPriority((byte) 0);
        currentQueue.setType(0);
        currentQueue.setPrepayCopies(fund);
        currentQueue.setEnqueueTime(DateUtil.getSystemTimeSeconds());
        ragularUserAccountRepository.saveWmCurrentQueue(currentQueue);

        return prepayId;


    }

    /**
     * 保存定存宝交易记录
     *
     * @param userId
     * @param productId
     * @param fund
     * @param logId
     * @param type
     * @param days
     */
    public void saveWmRagularTradeRecored(Long userId, Long productId, BigDecimal fund, Long logId, Integer type, Integer days, String name) {

        WmProductInfo productInfo = ragularUserAccountRepository.findWmProductInfoById(productId);

        WmRagularTradeRecored ragularTradeRecored = new WmRagularTradeRecored();
        //用户ID
        ragularTradeRecored.setUserId(userId);

        //来源去向
        if (productInfo != null)
            ragularTradeRecored.setNames(productInfo.getName() + name);
        //资金
        ragularTradeRecored.setFund(fund);
        //到期日志ID
        ragularTradeRecored.setLogId(logId);
        //1、购买2、到期
        ragularTradeRecored.setType(type);
        //
        ragularTradeRecored.setBuyTime(DateUtil.getSystemTimeSeconds());
        //0-资金余额
        ragularTradeRecored.setFundSource(0);
        //天数
        ragularTradeRecored.setDays(days);

        ragularUserAccountRepository.saveWmRagularTradeRecored(ragularTradeRecored);
    }

    /**
     * 定存到期日志
     *
     * @param userId
     * @param accountId
     * @param fund
     * @param grandFund
     */
    public Long saveWmRagularOverLog(Long userId, Long accountId, BigDecimal fund, BigDecimal grandFund) {
        WmRagularOverLog ragularOverLog = new WmRagularOverLog();
        ragularOverLog.setUserId(userId);
        ragularOverLog.setAccountId(accountId);
        ragularOverLog.setFund(fund);
        ragularOverLog.setGrandFund(grandFund);
        ragularOverLog.setType(0);
        ragularOverLog.setLogTime(DateUtil.getSystemTimeSeconds());
        return ragularUserAccountRepository.saveWmRagularOverLog(ragularOverLog);

    }

    /**
     * 减少定存宝结算账户金额
     * @param ragularUserAccount 定存宝账户
     * @param fund 金额
     * @param income 收益
     * @param status 状态
     */
    @Override
    public void decreaseRagularSettlementAccount(WmRagularUserAccount ragularUserAccount, BigDecimal fund, BigDecimal income, Integer status) {
        if (ragularUserAccount != null) {
            BigDecimal predictIncome = ragularUserAccount.getPredictIncome() == null? BigDecimal.ZERO: ragularUserAccount.getPredictIncome().subtract(income);
            if (predictIncome.doubleValue() < 0) {
                //用户预期收益
                ragularUserAccount.setPredictIncome(BigDecimal.ZERO);
            } else {
                ragularUserAccount.setPredictIncome(predictIncome);
            }
            //用户已还期数
            ragularUserAccount.setRefundCount(ragularUserAccount.getRefundCount() + 1);
            //已还款金额
            ragularUserAccount.setRefundable(ragularUserAccount.getRefundable() == null? fund.add(income): ragularUserAccount.getRefundable().add(fund).add(income));
            //定存状态 0未到期 1已到期
            ragularUserAccount.setStatus(status);
            //更新用户交易
            ragularUserAccountRepository.updateWmRagularUserAccount(ragularUserAccount);
        }
    }


    /**
     * 更新用户投资
     *
     * @param userId 用户ID
     * @param fund   用户本金
     */
    public void updateUserInvestinfo(Long userId, BigDecimal fund) {
        WmUserInvestinfo userInvestinfo = ragularUserAccountRepository.findWmUserInvestinfoByUserId(userId);
        if (userInvestinfo != null) {
            //投资总额
            userInvestinfo.setAllInvest(userInvestinfo.getAllInvest().subtract(fund));
            //定存投资总额
            userInvestinfo.setAllInvestDeposit(userInvestinfo.getAllInvestDeposit().subtract(fund));
            //更新用户投资
            ragularUserAccountRepository.updateWmUserInvestinfo(userInvestinfo);
        }
    }

    /**
     * 更新用户定存交易
     *
     * @param ragularUserAccount
     * @param fund
     * @param income
     * @param status             0未到期,1已到期
     */
    public void updateWmRagularUserAccount(WmRagularUserAccount ragularUserAccount, BigDecimal fund, BigDecimal income, Integer status) {

        BigDecimal predictIncome = ragularUserAccount.getPredictIncome().subtract(income);
        if (predictIncome.doubleValue() < 0) {
            //用户预期收益
            ragularUserAccount.setPredictIncome(BigDecimal.ZERO);
        } else {
            ragularUserAccount.setPredictIncome(predictIncome);
        }
        //用户已还期数
        ragularUserAccount.setRefundCount(ragularUserAccount.getRefundCount() + 1);
        //已还款金额
        ragularUserAccount.setRefundable(ragularUserAccount.getRefundable().add(fund).add(income));
        //定存状态 0未到期 1已到期
        ragularUserAccount.setStatus(status);
        //更新用户交易
        ragularUserAccountRepository.updateWmRagularUserAccount(ragularUserAccount);
    }


    /**
     * 更新用户总投资
     *
     * @param userId    用户ID
     * @param intIncome 整数收益
     */
    public void updateUserInvestinfoByExpire(Long userId, BigDecimal intIncome) {
        WmUserInvestinfo userInvestinfo = ragularUserAccountRepository.findWmUserInvestinfoByUserId(userId);
        if (userInvestinfo != null) {
            //投资总额
            userInvestinfo.setAllInvest(userInvestinfo.getAllInvest().add(intIncome));
            //定存投资总额
            userInvestinfo.setAllInvestDeposit(userInvestinfo.getAllInvestDeposit().add(intIncome));
            //更新用户投资
            ragularUserAccountRepository.updateWmUserInvestinfo(userInvestinfo);
        }
    }

    /**
     * 更新用户资金
     *
     * @param userId 用户ID
     * @param fund   本金
     * @param income 收益
     */
    public void updateUserMoney(Long userId, BigDecimal fund, BigDecimal income) {
        WmUserMoney userMoney = ragularUserAccountRepository.findWmUserMoneyByUserId(userId);
        if (userMoney != null) {
            //用户资金总额 收益
            userMoney.setTotalFund(userMoney.getTotalFund().add(income));
            //用户资金余额 本金 收益
            userMoney.setUsableFund(userMoney.getUsableFund().add(fund).add(income));
            //更新用户资金总额
            ragularUserAccountRepository.updateWmUserMoney(userMoney);
        }

    }

    /**
     * 更新用户资金
     *
     * @param userId       用户ID
     * @param totalIncome  总收益
     * @param doubleIncome 小数部位收益
     */
    public void updateUserMoneyByExpire(Long userId, BigDecimal totalIncome, BigDecimal doubleIncome) {
        WmUserMoney userMoney = ragularUserAccountRepository.findWmUserMoneyByUserId(userId);
        if (userMoney != null) {
            //用户资金总额 收益
            userMoney.setTotalFund(userMoney.getTotalFund().add(totalIncome));
            //用户资金余额 本金 收益
            userMoney.setUsableFund(userMoney.getUsableFund().add(doubleIncome));
            //更新用户资金总额
            ragularUserAccountRepository.updateWmUserMoney(userMoney);
        }

    }


    /**
     * 更新用户收益
     *
     * @param userId
     * @param income
     * @param interestIncome
     */
    public void updateUserIncome(Long userId, BigDecimal income, BigDecimal interestIncome, BigDecimal vipIncome, BigDecimal vouchersIncome) {
        WmUserIncome userIncome = ragularUserAccountRepository.findWmUserIncomeByUserId(userId);
        if (userIncome != null) {
            //昨日收益
            userIncome.setYesterdayIncome(userIncome.getYesterdayIncome().add(income));
            //累计收益总额
            userIncome.setAllIncome(userIncome.getAllIncome().add(income));
            //定存收益
            userIncome.setAllIncomeDeposit(userIncome.getAllIncomeDeposit().add(income));
            //加息收益
            userIncome.setAllIncomeInterest(userIncome.getAllIncomeInterest().add(interestIncome));
            //VIP收益
            userIncome.setAllIncomeVip(userIncome.getAllIncomeVip().add(vipIncome));

            //预期收益
            BigDecimal predictIncome = userIncome.getPredictIncome().subtract(income);
            if (predictIncome.doubleValue() < 0) {
                userIncome.setPredictIncome(BigDecimal.ZERO);
            } else {
                userIncome.setPredictIncome(predictIncome);
            }

            //代金券收益
            if (userIncome.getAllIncomeVouchers() == null) userIncome.setAllIncomeVouchers(BigDecimal.ZERO);
            if (vouchersIncome == null) vouchersIncome = BigDecimal.ZERO;
            userIncome.setAllIncomeVouchers(userIncome.getAllIncomeVouchers().add(vouchersIncome));

            //更新用户收益
            ragularUserAccountRepository.updateWmUserIncome(userIncome);
        }

    }

    /**
     * 发送站内信
     *
     * @param type   1 收益 2 到期 3 复投
     * @param userId
     * @param fund
     * @param income
     */
    public void sendSiteContent(Integer type, Long userId, BigDecimal fund, BigDecimal income, Long productId) {
        try {
            WmUser user = ragularUserAccountRepository.findWmUserById(userId);
            WmProductInfo productInfo = ragularUserAccountRepository.findWmProductInfoById(productId);
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

                if (type == 3) {
//                    smsHandleService.addSiteContent("deposit_mode", userId, pname + "复投", "name:" + user.getUsername() + ",value:" + fund.intValue() + ",ptype:" + pname, 0);
                    infoSmsTimerService.sendWmInfoSmsTimer("deposit_mode", user.getMobile(), "name:" + user.getUsername() + ",value:" + fund.intValue() + ",ptype:" + pname);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送站内信
     *
     * @param userId
     * @param fund
     */
    public void sendTransferCancel(Long userId, BigDecimal fund) {
        try {
            WmUser user = ragularUserAccountRepository.findWmUserById(userId);
            if (user != null) {
//                smsHandleService.addSiteContent("transfer_cancel", userId, "债权转让取消", "name:" + user.getUsername() + ",value:" + fund.intValue(), 0);
                infoSmsTimerService.sendWmInfoSmsTimer("transfer_cancel", user.getMobile(), "name:" + user.getUsername() + ",value:" + fund.intValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据其实时间查询用户id以及购买资金
     *
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public List<Object[]> queryRagularInfoBetweenTime(Long startTime, Long endTime) {
        return ragularUserAccountRepository.queryRagularInfoBetweenTime(startTime, endTime);
    }

    /**
     * 查询未使用代金券并且不是债权转让的定存信息
     *
     * @param startTime 购买开始时间
     * @param endTime   购买结束时间
     * @return
     */
    @Override
    public List<Object[]> queryWithoutVoucherAndNotTransfer(Long startTime, Long endTime) {
        return ragularUserAccountRepository.queryWithoutVoucherAndNotTransfer(startTime, endTime);
    }

    /**
     * 查询复投
     *
     * @param startTime 购买开始时间
     * @param endTime   购买结束时间
     * @return
     */
    @Override
    public List<Object[]> queryRebuy(Long startTime, Long endTime) {
        return ragularUserAccountRepository.queryRebuy(startTime, endTime);
    }

    private void buyCurrent(Long userId, WmProductInfo productInfo, BigDecimal decimalBuyCopies, Integer device, Integer buySource) {

        //生成活期宝购买日志
        WmCurrentBuylog currentBuylog = generateCurrentBuylog(userId, productInfo,
                decimalBuyCopies, device, buySource);
        //生成活期宝交易记录
        WmCurrentTradeRecored currentTradeRecord = generateFundTraderecord(userId, decimalBuyCopies,
                currentBuylog, buySource);
        //更新用户活期宝账户
        updateCurrentUserAccount(userId, decimalBuyCopies, buySource);
        //更新用户资金
        WmUserMoney userMoney = updateUserMoneyInBuyingCurrent(userId, decimalBuyCopies, buySource);
        //更新用户投资
        updateUserInvest(userId, decimalBuyCopies, buySource);
        //生成用户出账日志
        WmUserFundOutLog userFundOutlog = generateUserFundOutlog(userId, currentTradeRecord, decimalBuyCopies, userMoney);
        //生成用户资金记录
        generateUserFundRecord(userId, decimalBuyCopies, userFundOutlog, userMoney);
        //发站内信
        sendPrivateMessage(userId, decimalBuyCopies, userMoney);
    }


    private WmCurrentBuylog generateCurrentBuylog(Long userId, WmProductInfo productInfo,
                                                  BigDecimal buyCopies, Integer device, int buySource) {
        buyCopies = buyCopies == null ? BigDecimal.ZERO : buyCopies;
        WmCurrentBuylog buylog = new WmCurrentBuylog();
        buylog.setProductId(productInfo == null ? null : productInfo.getId());
        buylog.setUserId(userId);
        buylog.setApr(productInfo == null ? BigDecimal.ZERO : productInfo.getInterestRate());
        buylog.setGrandApr(BigDecimal.ZERO);
        buylog.setBuyTime(DateUtil.getSystemTimeSeconds());
        buylog.setDevice(device);
        if (buySource == 1) {
            buylog.setBuyFund(buyCopies);
            buylog.setExpFund(BigDecimal.ZERO);
        } else if (buySource == 2) {
            buylog.setExpFund(buyCopies);
            buylog.setBuyFund(BigDecimal.ZERO);
        }
        buylog.setBuyCopies(buyCopies.intValue());
        return wmEntityRepository.save(buylog);
    }

    private WmCurrentTradeRecored generateFundTraderecord(Long userId, BigDecimal buyCopies,
                                                          WmCurrentBuylog currentBuylog, int buySource) {
        Integer fundSource = null;
        if (buySource == 1) {
            fundSource = 0;
        } else if (buySource == 2) {
            fundSource = 1;
        }
        Long logId = currentBuylog == null ? null : currentBuylog.getId();
        return generateCurrentTradeRecord(userId, "活期宝投资", buyCopies, 1, fundSource, logId);
    }

    private WmCurrentTradeRecored generateCurrentTradeRecord(Long userId, String names, BigDecimal fund, Integer type, Integer fundSource, Long logId) {
        WmCurrentTradeRecored tradeRecord = new WmCurrentTradeRecored();
        tradeRecord.setUserId(userId);
        tradeRecord.setFund(fund);
        tradeRecord.setNames(names);
        tradeRecord.setBuyTime(DateUtil.getSystemTimeSeconds());
        tradeRecord.setType(type);
        tradeRecord.setFundSource(fundSource);
        tradeRecord.setLogId(logId);
        return wmEntityRepository.save(tradeRecord);
    }

    private WmCurrentUserAccount updateCurrentUserAccount(Long userId, BigDecimal buyCopies, int buySource) {
        buyCopies = buyCopies == null ? BigDecimal.ZERO : buyCopies;
        WmCurrentUserAccount currentUserAccount = currentUserAccountRepository.findWmCurrentUserAccountByUserId(userId);
        if (currentUserAccount != null) {
            if (buySource == 1) {
                BigDecimal allFund = currentUserAccount.getAllFund();
                currentUserAccount.setAllFund(allFund == null ? buyCopies : allFund.add(buyCopies));
            } else if (buySource == 2) {
                BigDecimal expFund = currentUserAccount.getExpFund();
                currentUserAccount.setExpFund(expFund == null ? buyCopies : expFund.add(buyCopies));
            }
            return wmEntityRepository.update(currentUserAccount);
        }
        //如果用户没有账户,则需要创建一个
        currentUserAccount = new WmCurrentUserAccount();
        currentUserAccount.setDlLastSettlementDate(0l);
        currentUserAccount.setProfit(BigDecimal.ZERO);
        currentUserAccount.setUserId(userId);
        if (buySource == 1) {
            currentUserAccount.setAllFund(buyCopies);
            currentUserAccount.setExpFund(BigDecimal.ZERO);
        } else if (buySource == 2) {
            currentUserAccount.setExpFund(buyCopies);
            currentUserAccount.setAllFund(BigDecimal.ZERO);
        }
        return wmEntityRepository.save(currentUserAccount);
    }

    private WmUserMoney updateUserMoneyInBuyingCurrent(Long userId, BigDecimal buyFund, int buySource) {
        WmUserMoney userMoney = currentUserAccountRepository.findWmUserMoneyByUserId(userId);
        if (userMoney != null && userMoney.getUsableFund() != null
                && buySource == 1) {
            //余额 = 余额 - 购买资金
            userMoney.setUsableFund(userMoney.getUsableFund().subtract(buyFund));
            return wmEntityRepository.update(userMoney);
        }
        return userMoney;
    }

    private void updateUserInvest(Long userId, BigDecimal decimalBuyCopies, int buySource) {
        decimalBuyCopies = decimalBuyCopies == null ? BigDecimal.ZERO : decimalBuyCopies;
        //用主键进行查询,只锁行,不锁表
        WmUserInvestinfo userInvest = currentUserAccountRepository.findWmUserInvestinfoByUserId(userId);
        if (userInvest != null) {
            //投资总额 = 投资总额 + 购买金额
            userInvest.setAllInvest(userInvest.getAllInvest() == null ? decimalBuyCopies :
                    userInvest.getAllInvest().add(decimalBuyCopies));
            //活期宝投资总额 = 活期宝投资总额 + 购买金额
            userInvest.setAllInvestDayloan(userInvest.getAllInvestDayloan() == null ? decimalBuyCopies :
                    userInvest.getAllInvestDayloan().add(decimalBuyCopies));
            //体验金投资总额 = 体验金投资总额 + 体验金购买金额
            if (buySource == 2) {
                userInvest.setAllInvestInvest(userInvest.getAllInvestInvest() == null ? decimalBuyCopies :
                        userInvest.getAllInvestInvest().add(decimalBuyCopies));
            }
            //新手投资总额 = 新手投资总额 - 投资金额
            BigDecimal newHand = userInvest.getAllNewHand() == null ? BigDecimal.ZERO : userInvest.getAllNewHand();
            newHand = newHand.subtract(decimalBuyCopies).compareTo(BigDecimal.ZERO) >= 0 ? newHand.subtract(decimalBuyCopies) : BigDecimal.ZERO;
            userInvest.setAllNewHand(newHand);
            wmEntityRepository.update(userInvest);
        }
    }

    private WmUserFundOutLog generateUserFundOutlog(Long userId, WmCurrentTradeRecored currentTradeRecord,
                                                    BigDecimal buyFund, WmUserMoney userMoney) {
        Long linkId = currentTradeRecord == null ? null : currentTradeRecord.getId();
        BigDecimal usableFund = userMoney == null ? null : userMoney.getUsableFund();
        return generateFundOutlog(userId, linkId, 2, buyFund, usableFund, null);
    }

    private WmUserFundOutLog generateFundOutlog(Long userId, Long linkId,
                                                Integer type, BigDecimal fund, BigDecimal usableFund, String remark) {
        WmUserFundOutLog fundOutlog = new WmUserFundOutLog();
        fundOutlog.setUserId(userId);
        fundOutlog.setLinkId(linkId);
        fundOutlog.setType(type);
        fundOutlog.setOutTime(DateUtil.getSystemTimeSeconds());
        fundOutlog.setOutFund(fund);
        fundOutlog.setBalance(usableFund);
        fundOutlog.setRemark(remark);
        return wmEntityRepository.save(fundOutlog);
    }

    private void generateUserFundRecord(Long userId, BigDecimal buyFund, WmUserFundOutLog userFundOutlog, WmUserMoney userMoney) {
        Long logId = userFundOutlog == null ? null : userFundOutlog.getId();
        BigDecimal usableFund = userMoney == null ? null : userMoney.getUsableFund();
        generateUserFundRecord(userId, "活期宝投资", buyFund, logId, 0, 6, usableFund);
    }

    private WmUserFundRecord generateUserFundRecord(Long userId, String fundflow, BigDecimal fund,
                                                    Long logId, Integer type, Integer fundType, BigDecimal usableFund) {
        WmUserFundRecord record = new WmUserFundRecord();
        record.setUserId(userId);
        record.setFundflow(fundflow);
        record.setFund(fund);
        record.setLogId(logId);
        record.setType(type);
        record.setFundType(fundType);
        record.setBalance(usableFund);
        record.setRecordTime(DateUtil.getSystemTimeSeconds());
        return wmEntityRepository.save(record);
    }

    private void sendPrivateMessage(Long userId, BigDecimal buyCopies, WmUserMoney userMoney) {
        WmUser user = wmEntityRepository.queryById(userId, WmUser.class);
        String usable = "";
        if (userMoney != null && userMoney.getUsableFund() != null) {
            usable = userMoney.getUsableFund().setScale(2, BigDecimal.ROUND_DOWN).toString();
        }
        //用户余额(保留小数点后2位)
        smsHandleService.addSiteContent("buy_dayloan", userId, "活期宝投资", "name:" + user.getUsername() +
                ",money:" + buyCopies + ",usable:" + usable, 0);
    }

    /**
     * 创建
     * @param userId userId
     * @param productId 产品id
     * @param allFund 到期本息总额
     * @param buyTime 购买时间
     * @param buyFund 购买资金
     * @param grandFund 各种方式赠与的资金
     * @param interestFund 利息收益总额
     * @param buyCopies 购买份数
     * @param grandApr 加息利率
     * @param vipApr vip加息利率
     * @param apr 年利率
     * @param expireTime 到期日期
     * @param expireMode 复投模式
     * @param incomeMode 利息是否复投
     * @param buyType 购买类型
     * @param days 产品天数
     * @param status 状态
     * @param predictIncome 预期收益
     * @param expireNum 转让次数
     * @param curProductId 当前产品id
     * @return
     */
    @Override
    public WmRagularUserAccount createOne(Long userId, Long productId, BigDecimal allFund, Long buyTime,
            BigDecimal buyFund, BigDecimal grandFund, BigDecimal interestFund, Integer buyCopies,
            BigDecimal grandApr, BigDecimal vipApr, BigDecimal apr, Long expireTime, Integer expireMode,
            Integer incomeMode, Integer buyType, Integer days, Integer status, BigDecimal predictIncome,
            Integer expireNum, Long curProductId) throws Exception{

        WmRagularUserAccount userAccount = new WmRagularUserAccount();
        userAccount.setUserId(userId);
        userAccount.setProductId(productId);
        userAccount.setAllFund(allFund);
        userAccount.setBuyTime(buyTime);
        userAccount.setBuyFund(buyFund);
        userAccount.setGrandFund(grandFund);
        userAccount.setInterestFund(interestFund);
        userAccount.setBuyCopies(buyCopies);
        userAccount.setGrandApr(grandApr);
        userAccount.setVipApr(vipApr);
        userAccount.setApr(apr);
        userAccount.setExpireTime(expireTime);
        userAccount.setExpireMode(expireMode);
        userAccount.setIncomeMode(incomeMode);
        userAccount.setBuyType(buyType);
        userAccount.setDays(days);
        userAccount.setStatus(status);
        userAccount.setPredictIncome(predictIncome);
        userAccount.setExpireNum(expireNum);
        userAccount.setCurProductId(curProductId);
        return ragularUserAccountRepository.createOrUpdate(userAccount);
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    public WmRagularUserAccount queryById(Long id) {
        return ragularUserAccountRepository.queryById(id);
    }

    public WmRagularUserAccountRepository getRagularUserAccountRepository() {
        return ragularUserAccountRepository;
    }

    public void setRagularUserAccountRepository(WmRagularUserAccountRepository ragularUserAccountRepository) {
        this.ragularUserAccountRepository = ragularUserAccountRepository;
    }


    public ISmsHandleService getSmsHandleService() {
        return smsHandleService;
    }

    public void setSmsHandleService(ISmsHandleService smsHandleService) {
        this.smsHandleService = smsHandleService;
    }


    public IWmInfoSmsTimerService getInfoSmsTimerService() {
        return infoSmsTimerService;
    }

    public void setInfoSmsTimerService(IWmInfoSmsTimerService infoSmsTimerService) {
        this.infoSmsTimerService = infoSmsTimerService;
    }

    public IWmUserIntegralService getUserIntegralService() {
        return userIntegralService;
    }

    public void setUserIntegralService(IWmUserIntegralService userIntegralService) {
        this.userIntegralService = userIntegralService;
    }

    public WmEntityRepository getWmEntityRepository() {
        return wmEntityRepository;
    }

    public void setWmEntityRepository(WmEntityRepository wmEntityRepository) {
        this.wmEntityRepository = wmEntityRepository;
    }

    public WmCurrentUserAccountRepository getCurrentUserAccountRepository() {
        return currentUserAccountRepository;
    }

    public void setCurrentUserAccountRepository(WmCurrentUserAccountRepository currentUserAccountRepository) {
        this.currentUserAccountRepository = currentUserAccountRepository;
    }

    public WmProductInfoRepository getWmProductInfoRepository() {
        return wmProductInfoRepository;
    }

    public void setWmProductInfoRepository(WmProductInfoRepository wmProductInfoRepository) {
        this.wmProductInfoRepository = wmProductInfoRepository;
    }

    public IWmActivityZhongqiuService getActivityZhongqiuService() {
        return activityZhongqiuService;
    }

    public void setActivityZhongqiuService(IWmActivityZhongqiuService activityZhongqiuService) {
        this.activityZhongqiuService = activityZhongqiuService;
    }
}
