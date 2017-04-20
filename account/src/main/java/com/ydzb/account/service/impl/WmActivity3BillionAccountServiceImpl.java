package com.ydzb.account.service.impl;

import com.ydzb.account.entity.WmProductInfo;
import com.ydzb.account.entity.WmSilverTradeRecordThirty;
import com.ydzb.account.entity.WmUserSilverThirty;
import com.ydzb.account.service.IWmActivity3BillionAccountService;
import com.ydzb.account.service.IWmProductInfoService;
import com.ydzb.account.service.IWmSilverTradeRecordThirtyService;
import com.ydzb.account.service.IWmUserSilverThirtyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.LockModeType;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 30亿活动结算service实现
 */
@Service
public class WmActivity3BillionAccountServiceImpl implements IWmActivity3BillionAccountService {

    @Autowired
    private IWmUserSilverThirtyService wmUserSilverThirtyService;
    @Autowired
    private IWmSilverTradeRecordThirtyService wmSilverTradeRecordThirtyService;
    @Autowired
    private IWmProductInfoService wmProductInfoService;

    private static Map<Integer, Integer> MONEY_STAGE = new HashMap<>();

    static {
        MONEY_STAGE.put(30, 3000); //一个月定存,3000元一个银多币,同理其他
        MONEY_STAGE.put(90, 2000);
        MONEY_STAGE.put(180, 1000);
        MONEY_STAGE.put(360, 800);
        MONEY_STAGE.put(365, 800);
    }

    /**
     * 结算
     * 根据复投月份以及钱数计算赠送的银多币数量
     * 更新用户30亿
     * @param ragularInfo
     */
    @Override
    public void account(Object[] ragularInfo) {

        if (ragularInfo != null && ragularInfo.length >= 4) {

            Long userId = ragularInfo[0] == null? Long.valueOf(0L): (Long) ragularInfo[0];
            BigDecimal buyFund = ragularInfo[1] == null? BigDecimal.ZERO: (BigDecimal) ragularInfo[1];
            Integer days = ragularInfo[2] == null? 30: (Integer) ragularInfo[2];
            Long ragularAccountId = (Long) ragularInfo[3];

            if (buyFund.compareTo(BigDecimal.ZERO) == 1) {

                Integer coinCount = culculateCoinCount(days, buyFund);
                if (coinCount.compareTo(0) == 1) {
                    //更新用户银多币数量
                    WmUserSilverThirty wmUserSilverThirty = wmUserSilverThirtyService.queryByUser(userId, LockModeType.PESSIMISTIC_WRITE);
                    if (wmUserSilverThirty == null) {
                        wmUserSilverThirty = wmUserSilverThirtyService.createOne(userId);
                    }
                    wmUserSilverThirtyService.updateCoins(wmUserSilverThirty, coinCount);
                    //生成银多币交易记录
                    wmSilverTradeRecordThirtyService.createOne(wmUserSilverThirty, ragularAccountId, coinCount, WmSilverTradeRecordThirty.TYPE_GET);
                }
            }
        }
    }

    /**
     * 计算银多币数量
     * @param days
     * @param buyFund
     * @return
     */
    private Integer culculateCoinCount(Integer days, BigDecimal buyFund) {
        days = days == null? 30: days;
        buyFund = buyFund == null? BigDecimal.ZERO: buyFund;
        Integer fundPerCoin = MONEY_STAGE.get(days) == null? 3000: MONEY_STAGE.get(days);
        return buyFund.intValue() / fundPerCoin;
    }
}

