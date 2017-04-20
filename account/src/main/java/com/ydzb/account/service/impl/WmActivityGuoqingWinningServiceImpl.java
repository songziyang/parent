package com.ydzb.account.service.impl;

import com.ydzb.account.entity.WmActivityGuoqingWinning;
import com.ydzb.account.repository.WmActivityGuoqingWinningRepository;
import com.ydzb.account.service.IWmActivityguoqingWinningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 国庆活动统计service实现
 */
@Service
public class WmActivityGuoqingWinningServiceImpl implements IWmActivityguoqingWinningService {

    @Autowired
    private WmActivityGuoqingWinningRepository wmActivityGuoqingWinningRepository;

    public static final int LUCKBAG_LIMIT = 8000000;    //每800W一个金福包

    /**
     * 累加投资金额
     * @param activityGuoqingWinning 国庆活动统计
     * @param fund 复投金额
     * @return
     */
    @Override
    public WmActivityGuoqingWinning cumulateInvestMoney(WmActivityGuoqingWinning activityGuoqingWinning, BigDecimal fund) {

        if (activityGuoqingWinning != null && fund != null && fund.compareTo(BigDecimal.ZERO) == 1) {

            int sourceGoldBag = activityGuoqingWinning.getGold() == null? 0: activityGuoqingWinning.getGold();  //原金福包数
            int intFund = fund.intValue();
            int totalMoney = activityGuoqingWinning.getTotalMoney() == null? intFund: intFund + activityGuoqingWinning.getTotalMoney(); //总活动投资金额
            int goldBag = totalMoney / LUCKBAG_LIMIT;   //现'金福包数
            if (goldBag > sourceGoldBag) {
                activityGuoqingWinning.setGold(goldBag);
                //现金福包数 = 原金福包数 + 新增加的金福包数
                activityGuoqingWinning.setGoldSurplus(activityGuoqingWinning.getGoldSurplus() == null? goldBag - sourceGoldBag: (goldBag - sourceGoldBag) + activityGuoqingWinning.getGoldSurplus());
            }
            activityGuoqingWinning.setTotalMoney(totalMoney);
            return wmActivityGuoqingWinningRepository.createOrUpdate(activityGuoqingWinning);
        }
        return null;
    }

    /**
     * 初始化
     * @return
     */
    @Override
    public WmActivityGuoqingWinning init() {
        WmActivityGuoqingWinning activityGuoqingWinning = new WmActivityGuoqingWinning();
        activityGuoqingWinning.setThree(0);
        activityGuoqingWinning.setTwo(0);
        activityGuoqingWinning.setGold(0);
        activityGuoqingWinning.setTotalMoney(0);
        activityGuoqingWinning.setGoldSurplus(0);
        return wmActivityGuoqingWinningRepository.createOrUpdate(activityGuoqingWinning);
    }
}