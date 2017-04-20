package com.ydzb.account.service.impl;

import com.ydzb.account.entity.WmActivityGuoqing;
import com.ydzb.account.entity.WmActivityGuoqingRecord;
import com.ydzb.account.entity.WmActivityGuoqingWinning;
import com.ydzb.account.entity.WmUserIntegral;
import com.ydzb.account.repository.WmActivityGuoqingRepository;
import com.ydzb.account.repository.WmActivityGuoqingWinningRepository;
import com.ydzb.account.service.*;
import com.ydzb.core.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.LockModeType;
import java.math.BigDecimal;
import java.util.List;

/**
 * 国庆活动service实现
 */
@Service
public class WmActivityGuoqingServiceImpl implements IWmActivityGuoqingService {

    @Autowired
    private WmActivityGuoqingRepository wmActivityGuoqingRepository;
    @Autowired
    private IWmActivityGuoqingRecordService wmActivityGuoqingRecordService;
    @Autowired
    private WmActivityGuoqingWinningRepository wmActivityGuoqingWinningRepository;
    @Autowired
    private IWmActivityguoqingWinningService wmActivityguoqingWinningService;
    @Autowired
    private IWmUserIntegralService wmUserIntegralService;
    @Autowired
    private IWmUserIntegralRecordService wmUserIntegralRecordService;


    private BigDecimal LUCKBAG_LIMIT = new BigDecimal(5000);

    /**
     * 赠送福袋
     * @param userId 用户id
     * @param fund 复投定存金额
     * @param ragularAccountId 定存产品记录id
     */
    @Override
    public void grantLuckBag(Long userId, BigDecimal fund, Long ragularAccountId) throws Exception {

        if (userId != null && fund != null && fund.compareTo(BigDecimal.ZERO) == 1) {
            WmActivityGuoqingWinning activityGuoqingWinning = wmActivityGuoqingWinningRepository.queryFirstOne(LockModeType.PESSIMISTIC_WRITE);
            //累加总和,计算总金福包总数以及剩余金福包数量
            if (activityGuoqingWinning == null) activityGuoqingWinning = wmActivityguoqingWinningService.init();
            wmActivityguoqingWinningService.cumulateInvestMoney(activityGuoqingWinning, fund);
            //复投只发普通福包,不发金福包
            grantNormalLuckBag(userId, fund, ragularAccountId);
        }
    }

    /**
     * 查询有福包的用户国庆信息
     * @return
     */
    @Override
    public List<Object[]> queryOnesHavingLuckbag() {
        return wmActivityGuoqingRepository.queryOnesHavingLuckbag();
    }

    /**
     * 转换为积分
     * @param zhongqiuId 中秋活动id
     * @param userId 用户id
     * @param luckbagCount 福包数量
     */
    @Override
    public void convertToIntegral(Long zhongqiuId, Long userId, Integer luckbagCount) {

        if (userId != null && luckbagCount != null && luckbagCount > 0) {
            //清空用户国庆信息的福包数量
            WmActivityGuoqing activityGuoqing = wmActivityGuoqingRepository.queryById(zhongqiuId, LockModeType.PESSIMISTIC_WRITE);
            if (activityGuoqing != null) {
                activityGuoqing.setUsableFund(0);
                wmActivityGuoqingRepository.createOrUpdate(activityGuoqing);
            }
            //创建国庆福包流水
            wmActivityGuoqingRecordService.createOne(userId, 4, null, luckbagCount, 0);
            //增加用户积分账户
            BigDecimal convertIntegral = new BigDecimal(luckbagCount * 100);
            WmUserIntegral userIntegral = wmUserIntegralService.queryByUser(userId, LockModeType.PESSIMISTIC_WRITE);
            if (userIntegral != null) {
                BigDecimal totalIntegral = userIntegral.getTotalIntegral() == null? convertIntegral: convertIntegral.add(userIntegral.getTotalIntegral());
                BigDecimal integral = userIntegral.getIntegral() == null? convertIntegral: convertIntegral.add(userIntegral.getIntegral());
                wmUserIntegralService.resetIntegral(userIntegral, totalIntegral, integral);
            } else {
                userIntegral = wmUserIntegralService.init(userId, convertIntegral, convertIntegral);
            }
            //创建用户积分流水
            wmUserIntegralRecordService.createOne(userId, "十一活动福包兑换", convertIntegral, userIntegral.getIntegral(), 1, 44, zhongqiuId);
        }
    }

    /**
     * 发放普通福包
     * @param userId 用户id
     * @param fund 购买基恩
     * @param ragularAccountId 定存记录id
     */
    private void grantNormalLuckBag(Long userId, BigDecimal fund, Long ragularAccountId) {
        WmActivityGuoqing guoqingInfo = queryByUser(userId, LockModeType.PESSIMISTIC_WRITE);
        LuckBag luckBag;
        if (guoqingInfo == null) {
            luckBag = createGuoqingInfo(userId, fund);
        } else {
            luckBag = updateGuoqingInfo(guoqingInfo, fund);
        }
        if (luckBag != null && luckBag.getNum() > 0) {
            wmActivityGuoqingRecordService.createOne(userId, WmActivityGuoqingRecord.TYPE_GET, ragularAccountId, luckBag.getNum(), luckBag.getUsableNum());
        }
    }

    /**
     * 更新国庆活动信息
     * @param wmActivityGuoqing 国庆活动信息
     * @param fund 复投定存金额
     * @return
     */
    private LuckBag updateGuoqingInfo(WmActivityGuoqing wmActivityGuoqing, BigDecimal fund) {

        if (wmActivityGuoqing != null && fund != null) {

            int sourceSurplusFund = wmActivityGuoqing.getOne();
            fund = fund.add(new BigDecimal(sourceSurplusFund)); //福袋计算金额 = 复投定存金额 + 原多余金额

            BigDecimal[] results = fund.divideAndRemainder(LUCKBAG_LIMIT);
            BigDecimal bagCount = results[0] == null? BigDecimal.ZERO: results[0];   //福袋数量
            BigDecimal surplusFund = results[1] == null? BigDecimal.ZERO: results[1];    //现多余金额
            int intBagCount = bagCount.intValue();

            boolean increaseBag = intBagCount > 0;  //是否需要发放福袋

            if (increaseBag) {
                wmActivityGuoqing.setTotalFund(wmActivityGuoqing.getTotalFund() == null? intBagCount: wmActivityGuoqing.getTotalFund() + intBagCount);
                wmActivityGuoqing.setUsableFund(wmActivityGuoqing.getUsableFund() == null? intBagCount: wmActivityGuoqing.getUsableFund() + intBagCount);
            }
            wmActivityGuoqing.setOne(surplusFund.intValue());
            wmActivityGuoqingRepository.createOrUpdate(wmActivityGuoqing);
            return new LuckBag(intBagCount, wmActivityGuoqing.getUsableFund());
        }
        return null;
    }

    /**
     * 创建国庆活动信息
     * @param userId
     * @param fund
     * @return
     */
    private LuckBag createGuoqingInfo(Long userId, BigDecimal fund) {

        if (userId != null && fund != null) {
            WmActivityGuoqing wmActivityGuoqing = new WmActivityGuoqing();
            BigDecimal[] results = fund.divideAndRemainder(LUCKBAG_LIMIT);
            BigDecimal bagCount = results[0] == null? BigDecimal.ZERO: results[0];   //福袋数量
            BigDecimal surplusFund = results[1] == null? BigDecimal.ZERO: results[1];    //现多余金额
            int intBagCount = bagCount.intValue();

            wmActivityGuoqing.setUserId(userId);
            wmActivityGuoqing.setTotalFund(intBagCount);
            wmActivityGuoqing.setUsableFund(intBagCount);
            wmActivityGuoqing.setGoldFund(0);
            wmActivityGuoqing.setCreated(DateUtil.getSystemTimeSeconds());
            wmActivityGuoqing.setOne(surplusFund.intValue());
            wmActivityGuoqingRepository.createOrUpdate(wmActivityGuoqing);
            return new LuckBag(bagCount.intValue(), bagCount.intValue());
        }
        return null;
    }

    /**
     * 根据用户查询国庆活动信息
     * @param userId 用户id
     * @param lockType 锁类型
     * @return
     */
    @Override
    public WmActivityGuoqing queryByUser(Long userId, LockModeType lockType) {
        return wmActivityGuoqingRepository.queryByUser(userId, lockType);
    }

    /**
     * 福袋
     */
    class LuckBag {

        public LuckBag(int num, int usableNum) {
            this.num = num;
            this.usableNum = usableNum;
        }

        private Integer num;
        private Integer usableNum;

        public Integer getNum() {
            return num;
        }

        public void setNum(Integer num) {
            this.num = num;
        }

        public Integer getUsableNum() {
            return usableNum;
        }

        public void setUsableNum(Integer usableNum) {
            this.usableNum = usableNum;
        }
    }
}