package com.ydzb.account.service.impl;

import com.ydzb.account.entity.WmRagularBuyLog;
import com.ydzb.account.repository.WmRagularBuyLogRepository;
import com.ydzb.account.service.IWmRagularBuyLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 定存购买日志service接口实现
 */
@Service
public class WmRagularBuyLogServiceImpl implements IWmRagularBuyLogService {

    @Autowired
    private WmRagularBuyLogRepository ragularBuyLogRepository;

    /**
     * 创建
     * @param productId 产品id
     * @param accountId 定存宝账户id
     * @param userId 用户id
     * @param buyFund 购买金额
     * @param buyCopies 购买份数
     * @param apr 利率
     * @param grandApr 加息利率
     * @param vipApr vip利率
     * @param buyTime 购买时间
     * @param device 设备
     * @return
     */
    @Override
    public WmRagularBuyLog createOne(Long productId, Long accountId, Long userId, BigDecimal buyFund, Integer buyCopies,
            BigDecimal apr, BigDecimal grandApr, BigDecimal vipApr, Long buyTime, Integer device) {
        WmRagularBuyLog buyLog = new WmRagularBuyLog();
        buyLog.setProductId(productId);
        buyLog.setAccountId(accountId);
        buyLog.setUserId(userId);
        buyLog.setBuyFund(buyFund);
        buyLog.setBuyCopies(buyCopies);
        buyLog.setApr(apr);
        buyLog.setGrandApr(grandApr);
        buyLog.setVipApr(vipApr);
        buyLog.setBuyTime(buyTime);
        buyLog.setDevice(device);
        return ragularBuyLogRepository.createOrUpdate(buyLog);
    }
}