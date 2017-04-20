package com.ydzb.account.service.impl;

import com.ydzb.account.entity.WmUserIntegral;
import com.ydzb.account.entity.WmUserIntegralRecord;
import com.ydzb.account.repository.WmUserIntegralRepository;
import com.ydzb.account.service.IWmUserIntegralService;
import com.ydzb.core.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.LockModeType;
import java.math.BigDecimal;


@Service
public class WmUserIntegralServiceImpl implements IWmUserIntegralService {

    public static final BigDecimal B = new BigDecimal(100);

    public static final BigDecimal VIP = new BigDecimal(500);

    public static final String F1 = "定存宝复投";

    public static final String F2 = "用户VIP升级";

    @Autowired
    private WmUserIntegralRepository userIntegralRepository;

    /**
     * 用户投资定存宝获得积分
     *
     * @param userId 用户ID
     * @param days   产品天数
     * @param fund   投资金额
     * @param linkId 外链ID
     */
    @Override
    public void investObtainIntegral(Long userId, Integer days, BigDecimal fund, Long linkId) {
        //非空判断
        if (userId != null && days != null && fund != null) {

            BigDecimal integral = BigDecimal.ZERO;
            //判断是否是一年定存
            if (days >= 360) {
                //月份直接X12
                integral = fund.multiply(new BigDecimal(12)).divide(B, 1, BigDecimal.ROUND_DOWN);
            } else {
                //月份days/30
                integral = fund.multiply(new BigDecimal(days / 30)).divide(B, 1, BigDecimal.ROUND_DOWN);
            }

            WmUserIntegral wmUserIntegral = userIntegralRepository.findWmUserIntegralByUserId(userId);
            //判断用户积分是否存在
            if (wmUserIntegral != null) {
                //增加积分
                if (wmUserIntegral.getIntegral() == null) wmUserIntegral.setIntegral(BigDecimal.ZERO);
                wmUserIntegral.setIntegral(wmUserIntegral.getIntegral().add(integral));
                //增加累计积分
                if (wmUserIntegral.getTotalIntegral() == null) wmUserIntegral.setTotalIntegral(BigDecimal.ZERO);
                wmUserIntegral.setTotalIntegral(wmUserIntegral.getTotalIntegral().add(integral));
                //更新积分
                userIntegralRepository.updateWmUserIntegral(wmUserIntegral);
            } else {
                wmUserIntegral = new WmUserIntegral();
                wmUserIntegral.setUserId(userId);
                wmUserIntegral.setVip(0);
                wmUserIntegral.setIntegral(integral);
                wmUserIntegral.setTotalIntegral(integral);
                userIntegralRepository.saveWmUserIntegral(wmUserIntegral);
            }

            //如果积分大于0 添加积分记录
            if (integral.compareTo(BigDecimal.ZERO) > 0) {
                //保存积分记录
                saveWmUserIntegralRecord(userId, integral, wmUserIntegral.getIntegral(), 3, linkId, F1);
            }

        }

    }

    /**
     * 用户投资定存宝获得积分
     *
     * @param userId 用户ID
     * @param fund   投资金额
     * @param linkId 外链ID
     */
    @Override
    public void investObtainIntegralNew(Long userId, BigDecimal fund, Long linkId) {

        if (userId != null && fund != null) {
            //积分规则 投资金额 ÷ 100
            BigDecimal integral = fund.divide(new BigDecimal(100), 1, BigDecimal.ROUND_DOWN);
            WmUserIntegral wmUserIntegral = userIntegralRepository.findWmUserIntegralByUserId(userId);
            if (wmUserIntegral != null) {
                //增加积分
                wmUserIntegral.setIntegral(wmUserIntegral.getIntegral() == null ? integral : wmUserIntegral.getIntegral().add(integral));
                //增加累计积分
                wmUserIntegral.setTotalIntegral(wmUserIntegral.getTotalIntegral() == null ? integral : wmUserIntegral.getTotalIntegral().add(integral));
                //更新积分
                userIntegralRepository.updateWmUserIntegral(wmUserIntegral);
            } else {
                wmUserIntegral = new WmUserIntegral(userId, 0, integral, integral);
                userIntegralRepository.saveWmUserIntegral(wmUserIntegral);
            }

            //如果积分大于0 添加积分记录
            if (integral.compareTo(BigDecimal.ZERO) > 0) {
                saveWmUserIntegralRecord(userId, integral, wmUserIntegral.getIntegral(), 3, linkId, F1);
            }
        }
    }


    /**
     * 桂花转换为积分
     *
     * @param userId   用户ID
     * @param integral 积分
     * @param linkId   外链ID
     */
    @Override
    public void zhongqiuObtainIntegral(Long userId, BigDecimal integral, Long linkId) {

        if (userId != null && integral != null) {

            WmUserIntegral wmUserIntegral = userIntegralRepository.findWmUserIntegralByUserId(userId);

            if (wmUserIntegral != null) {
                //增加积分
                wmUserIntegral.setIntegral(wmUserIntegral.getIntegral() == null ? integral : wmUserIntegral.getIntegral().add(integral));
                //增加累计积分
                wmUserIntegral.setTotalIntegral(wmUserIntegral.getTotalIntegral() == null ? integral : wmUserIntegral.getTotalIntegral().add(integral));
                //更新积分
                userIntegralRepository.updateWmUserIntegral(wmUserIntegral);
            } else {
                //新建用户积分
                wmUserIntegral = new WmUserIntegral(userId, 0, integral, integral);
                userIntegralRepository.saveWmUserIntegral(wmUserIntegral);
            }

            //如果积分大于0 添加积分记录
            if (integral.compareTo(BigDecimal.ZERO) > 0) {
                saveWmUserIntegralRecord(userId, integral, wmUserIntegral.getIntegral(), 42, linkId, "桂花兑换");
            }
        }

    }

    /**
     * 成为VIP获得积分
     *
     * @param userId 用户ID
     */
    @Override
    public void becomeVip(Long userId) {
        if (userId != null) {
            WmUserIntegral wmUserIntegral = userIntegralRepository.findWmUserIntegralByUserId(userId);
            //判断用户积分是否存在
            if (wmUserIntegral != null) {
                if (wmUserIntegral.getVip() == 0) {
                    //增加积分
                    if (wmUserIntegral.getIntegral() == null) wmUserIntegral.setIntegral(BigDecimal.ZERO);
                    wmUserIntegral.setIntegral(wmUserIntegral.getIntegral().add(VIP));
                    //增加累计积分
                    if (wmUserIntegral.getTotalIntegral() == null) wmUserIntegral.setTotalIntegral(BigDecimal.ZERO);
                    wmUserIntegral.setTotalIntegral(wmUserIntegral.getTotalIntegral().add(VIP));
                    //更新VIP已给状态
                    wmUserIntegral.setVip(1);
                    //更新积分
                    userIntegralRepository.updateWmUserIntegral(wmUserIntegral);
                    //保存积分记录
                    saveWmUserIntegralRecord(userId, VIP, wmUserIntegral.getIntegral(), 4, null, F2);
                }
            } else {
                wmUserIntegral = new WmUserIntegral();
                wmUserIntegral.setUserId(userId);
                wmUserIntegral.setVip(1);
                wmUserIntegral.setIntegral(VIP);
                wmUserIntegral.setTotalIntegral(VIP);
                userIntegralRepository.saveWmUserIntegral(wmUserIntegral);
                //保存积分记录
                saveWmUserIntegralRecord(userId, VIP, wmUserIntegral.getIntegral(), 4, null, F2);
            }

        }
    }

    /**
     * 保存积分记录
     *
     * @param userId   用户ID
     * @param integral 积分
     * @param balance  积分余额
     * @param linkId   外链ID
     */
    public void saveWmUserIntegralRecord(Long userId, BigDecimal integral, BigDecimal balance, Integer linkType, Long linkId, String fundFlow) {
        WmUserIntegralRecord wmUserIntegralRecord = new WmUserIntegralRecord();
        wmUserIntegralRecord.setUserId(userId);
        wmUserIntegralRecord.setFundflow(fundFlow);
        wmUserIntegralRecord.setIntegral(integral);
        wmUserIntegralRecord.setBalance(balance);
        wmUserIntegralRecord.setType(1);
        wmUserIntegralRecord.setLinkType(linkType);
        wmUserIntegralRecord.setLinkId(linkId);
        wmUserIntegralRecord.setCreated(DateUtil.getSystemTimeSeconds());
        userIntegralRepository.saveWmUserIntegralRecord(wmUserIntegralRecord);
    }


    /**
     * 更新用户积分
     */
    @Override
    public void emptyUserIntegral() throws Exception {
        userIntegralRepository.emptyUserIntegral();
    }

    /**
     * 根据用户查询用户积分
     * @param userId
     * @param lockType
     * @return
     */
    public WmUserIntegral queryByUser(Long userId, LockModeType lockType) {
        return userIntegralRepository.queryByUser(userId, lockType);
    }

    /**
     * 重置用户积分
     * @param wmUserIntegral
     * @param totalIntegral
     * @param integral
     * @return
     */
    @Override
    public WmUserIntegral resetIntegral(WmUserIntegral wmUserIntegral, BigDecimal totalIntegral, BigDecimal integral) {
        if (wmUserIntegral != null) {
            wmUserIntegral.setTotalIntegral(totalIntegral);
            wmUserIntegral.setIntegral(integral);
            return userIntegralRepository.createOrUpdate(wmUserIntegral);
        }
        return null;
    }

    /**
     * 初始化
     * @param userId
     * @param totalIntegral
     * @param integral
     * @return
     */
    @Override
    public WmUserIntegral init(Long userId, BigDecimal totalIntegral, BigDecimal integral) {
        WmUserIntegral userIntegral = new WmUserIntegral();
        userIntegral.setUserId(userId);
        userIntegral.setTotalIntegral(totalIntegral);
        userIntegral.setIntegral(integral);
        userIntegral.setVip(0);
        return userIntegralRepository.createOrUpdate(userIntegral);
    }

    public WmUserIntegralRepository getUserIntegralRepository() {
        return userIntegralRepository;
    }

    public void setUserIntegralRepository(WmUserIntegralRepository userIntegralRepository) {
        this.userIntegralRepository = userIntegralRepository;
    }
}
