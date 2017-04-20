package com.ydzb.account.service.impl;

import com.ydzb.account.context.IDRange;
import com.ydzb.account.entity.WmActivityZhongqiu;
import com.ydzb.account.entity.WmActivityZhongqiuRecord;
import com.ydzb.account.repository.WmActivityZhongqiuRepository;
import com.ydzb.account.service.IWmActivityZhongqiuService;
import com.ydzb.account.service.IWmUserIntegralService;
import com.ydzb.core.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
public class WmActivityZhongqiuServiceImpl implements IWmActivityZhongqiuService {

    //基础金额
    private static final int B_FUND = 10000;
    //一个月
    private static final int ONE = 100;
    //三个月
    private static final int THREE = 125;
    //六个月
    private static final int SIX = 150;
    //十二个月
    private static final int TWELVE = 200;

    //1桂花 = 5 积分
    private static final int B_ZH = 5;

    @Autowired
    private WmActivityZhongqiuRepository activityZhongqiuRepository;

    @Autowired
    private IWmUserIntegralService userIntegralService;

    /**
     * 活动桂花
     *
     * @param userId 用户ID
     * @param days   天数
     * @param fund   金额
     * @param linkId 链接ID
     */
    @Override
    public void investObtainFund(Long userId, Integer days, BigDecimal fund, Long linkId) {
        try {
            if (userId != null && fund != null) {

                //查询是否有桂花
                WmActivityZhongqiu activityZhongqiu = activityZhongqiuRepository.findWmActivityZhongqiuByUserId(userId);

                int obtainFund = 0;

                if (activityZhongqiu != null) {
                    if (activityZhongqiu.getTotalFund() == null) activityZhongqiu.setTotalFund(0);
                    if (activityZhongqiu.getUsableFund() == null) activityZhongqiu.setUsableFund(0);
                    if (activityZhongqiu.getOne() == null) activityZhongqiu.setOne(0);
                    if (activityZhongqiu.getThree() == null) activityZhongqiu.setThree(0);
                    if (activityZhongqiu.getSix() == null) activityZhongqiu.setSix(0);
                    if (activityZhongqiu.getTwelve() == null) activityZhongqiu.setTwelve(0);

                    //一个月产品
                    if (days == 30) {
                        obtainFund = ((fund.intValue() + activityZhongqiu.getOne()) / B_FUND) * ONE;
                        activityZhongqiu.setTotalFund(activityZhongqiu.getTotalFund() + obtainFund);
                        activityZhongqiu.setUsableFund(activityZhongqiu.getUsableFund() + obtainFund);
                        activityZhongqiu.setOne((fund.intValue() + activityZhongqiu.getOne()) % B_FUND);
                    }

                    //三个月产品
                    if (days == 90) {
                        obtainFund = ((fund.intValue() + activityZhongqiu.getThree()) / B_FUND) * THREE;
                        activityZhongqiu.setTotalFund(activityZhongqiu.getTotalFund() + obtainFund);
                        activityZhongqiu.setUsableFund(activityZhongqiu.getUsableFund() + obtainFund);
                        activityZhongqiu.setThree((fund.intValue() + activityZhongqiu.getThree()) % B_FUND);
                    }

                    //六个月产品
                    if (days == 180) {
                        obtainFund = ((fund.intValue() + activityZhongqiu.getSix()) / B_FUND) * SIX;
                        activityZhongqiu.setTotalFund(activityZhongqiu.getTotalFund() + obtainFund);
                        activityZhongqiu.setUsableFund(activityZhongqiu.getUsableFund() + obtainFund);
                        activityZhongqiu.setSix((fund.intValue() + activityZhongqiu.getSix()) % B_FUND);
                    }

                    //十二个月产品
                    if (days >= 360) {
                        obtainFund = ((fund.intValue() + activityZhongqiu.getTwelve()) / B_FUND) * TWELVE;
                        activityZhongqiu.setTotalFund(activityZhongqiu.getTotalFund() + obtainFund);
                        activityZhongqiu.setUsableFund(activityZhongqiu.getUsableFund() + obtainFund);
                        activityZhongqiu.setTwelve((fund.intValue() + activityZhongqiu.getTwelve()) % B_FUND);
                    }

                    activityZhongqiuRepository.updateWmActivityZhongqiu(activityZhongqiu);
                } else {

                    activityZhongqiu = new WmActivityZhongqiu();
                    activityZhongqiu.setUserId(userId);
                    activityZhongqiu.setTotalFund(0);
                    activityZhongqiu.setUsableFund(0);
                    activityZhongqiu.setCreated(DateUtil.getSystemTimeSeconds());
                    activityZhongqiu.setOne(0);
                    activityZhongqiu.setThree(0);
                    activityZhongqiu.setSix(0);
                    activityZhongqiu.setTwelve(0);

                    //一个月产品
                    if (days == 30) {
                        obtainFund = ((fund.intValue() + activityZhongqiu.getOne()) / B_FUND) * ONE;
                        activityZhongqiu.setTotalFund(activityZhongqiu.getTotalFund() + obtainFund);
                        activityZhongqiu.setUsableFund(activityZhongqiu.getUsableFund() + obtainFund);
                        activityZhongqiu.setOne((fund.intValue() + activityZhongqiu.getOne()) % B_FUND);
                    }

                    //三个月产品
                    if (days == 90) {
                        obtainFund = ((fund.intValue() + activityZhongqiu.getThree()) / B_FUND) * THREE;
                        activityZhongqiu.setTotalFund(activityZhongqiu.getTotalFund() + obtainFund);
                        activityZhongqiu.setUsableFund(activityZhongqiu.getUsableFund() + obtainFund);
                        activityZhongqiu.setThree((fund.intValue() + activityZhongqiu.getThree()) % B_FUND);
                    }

                    //六个月产品
                    if (days == 180) {
                        obtainFund = ((fund.intValue() + activityZhongqiu.getSix()) / B_FUND) * SIX;
                        activityZhongqiu.setTotalFund(activityZhongqiu.getTotalFund() + obtainFund);
                        activityZhongqiu.setUsableFund(activityZhongqiu.getUsableFund() + obtainFund);
                        activityZhongqiu.setSix((fund.intValue() + activityZhongqiu.getSix()) % B_FUND);
                    }

                    //十二个月产品
                    if (days >= 360) {
                        obtainFund = ((fund.intValue() + activityZhongqiu.getTwelve()) / B_FUND) * TWELVE;
                        activityZhongqiu.setTotalFund(activityZhongqiu.getTotalFund() + obtainFund);
                        activityZhongqiu.setUsableFund(activityZhongqiu.getUsableFund() + obtainFund);
                        activityZhongqiu.setTwelve((fund.intValue() + activityZhongqiu.getTwelve()) % B_FUND);
                    }

                    activityZhongqiuRepository.saveWmActivityZhongqiu(activityZhongqiu);
                }

                //获得桂花记录
                if (obtainFund > 0) {
                    saveWmActivityZhongqiuRecord(userId, 1, linkId, obtainFund, activityZhongqiu.getUsableFund());
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 保存桂花记录
     *
     * @param userId     用户ID
     * @param type       类型 1、获得 2、使用3、退款
     * @param linkId     外链ID 类型1定存记录ID 类型2兑换记录ID
     * @param fund       数量
     * @param usableFund 余额数量
     */
    public void saveWmActivityZhongqiuRecord(Long userId, Integer type, Long linkId, Integer fund, Integer usableFund) {
        WmActivityZhongqiuRecord activityZhongqiuRecord = new WmActivityZhongqiuRecord();
        activityZhongqiuRecord.setUserId(userId);
        activityZhongqiuRecord.setType(type);
        activityZhongqiuRecord.setLinkId(linkId);
        activityZhongqiuRecord.setFund(fund);
        activityZhongqiuRecord.setUsableFund(usableFund);
        activityZhongqiuRecord.setCreated(DateUtil.getSystemTimeSeconds());
        activityZhongqiuRepository.saveWmActivityZhongqiuRecord(activityZhongqiuRecord);

    }


    /**
     * 桂花活动最大ID和最小ID
     *
     * @return
     */
    @Override
    public IDRange findMaxIdAndMinId() {
        return activityZhongqiuRepository.findMaxIdAndMinId();
    }


    /**
     * 桂花兑换积分
     *
     * @param id 桂花
     * @throws Exception
     */
    @Override
    public void sendIntegral(Long id) throws Exception {
        if (id != null) {
            WmActivityZhongqiu activityZhongqiu = activityZhongqiuRepository.findWmActivityZhongqiuById(id);
            if (activityZhongqiu != null) {
                if (activityZhongqiu.getUsableFund() != null) {
                    //兑换积分
                    BigDecimal integral = new BigDecimal(activityZhongqiu.getUsableFund() * B_ZH);

                    if (integral.intValue() > 0) {

                        //兑换积分记录
                        saveWmActivityZhongqiuRecord(activityZhongqiu.getUserId(), 4, null, activityZhongqiu.getUsableFund(), 0);

                        //积分记录
                        userIntegralService.zhongqiuObtainIntegral(activityZhongqiu.getUserId(), integral, activityZhongqiu.getId());

                        //清空
                        activityZhongqiu.setUsableFund(0);
                        activityZhongqiuRepository.updateWmActivityZhongqiu(activityZhongqiu);
                    }

                }
            }
        }
    }

    public WmActivityZhongqiuRepository getActivityZhongqiuRepository() {
        return activityZhongqiuRepository;
    }

    public void setActivityZhongqiuRepository(WmActivityZhongqiuRepository activityZhongqiuRepository) {
        this.activityZhongqiuRepository = activityZhongqiuRepository;
    }

    public IWmUserIntegralService getUserIntegralService() {
        return userIntegralService;
    }

    public void setUserIntegralService(IWmUserIntegralService userIntegralService) {
        this.userIntegralService = userIntegralService;
    }
}
