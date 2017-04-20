package com.ydzb.account.service.impl;

import com.ydzb.account.entity.WmCurrentBuylog;
import com.ydzb.account.repository.WmCurrentBuylogRepository;
import com.ydzb.account.service.IWmCurrentBuylogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 活期宝购买日志service实现
 */
@Service
public class WmCurrentBuylogServiceImpl implements IWmCurrentBuylogService {

    @Autowired
    private WmCurrentBuylogRepository currentBuylogRepository;

    /**
     * 创建
     * @param productId 产品id
     * @param userId 用户id
     * @param buyFund 购买资金
     * @param expFund 体验金
     * @param buyCopies 购买份数
     * @param apr 利率
     * @param grandApr 加息利率
     * @param buyTime 购买时间
     * @param device 类型
     * @return
     */
    @Override
    public WmCurrentBuylog createOne(Long productId, Long userId, BigDecimal buyFund, BigDecimal expFund,
            Integer buyCopies, BigDecimal apr, BigDecimal grandApr, Long buyTime, Integer device) {
        WmCurrentBuylog currentBuylog = new WmCurrentBuylog();
        currentBuylog.setProductId(productId);
        currentBuylog.setUserId(userId);
        currentBuylog.setBuyFund(buyFund);
        currentBuylog.setExpFund(expFund);
        currentBuylog.setBuyCopies(buyCopies);
        currentBuylog.setApr(apr);
        currentBuylog.setGrandApr(grandApr);
        currentBuylog.setBuyTime(buyTime);
        currentBuylog.setDevice(device);
        return (WmCurrentBuylog) currentBuylogRepository.createOrUpdate(currentBuylog);
    }
}
