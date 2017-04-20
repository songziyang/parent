package com.ydzb.account.service.impl;

import com.ydzb.account.entity.WmPrivilegeBuyLog;
import com.ydzb.account.repository.WmPrivilegeBuyLogRepository;
import com.ydzb.account.service.IWmPrivilegeBuyLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 新手特权购买日志service实现
 */
@Service
public class WmPrivilegeBuyLogServiceImpl implements IWmPrivilegeBuyLogService {

    @Autowired
    private WmPrivilegeBuyLogRepository privilegeBuyLogRepository;

    /**
     * 创建
     * @param productId 产品id
     * @param userId 用户id
     * @param buyFund 购买金额
     * @param buyCopies 购买份数
     * @param apr 利率
     * @param buyTime 购买时间
     * @param device
     * @return
     */
    @Override
    public WmPrivilegeBuyLog createOne(Long productId, Long userId, BigDecimal buyFund, Integer buyCopies, BigDecimal apr, Long buyTime, Integer device) {
        WmPrivilegeBuyLog privilegeBuyLog = new WmPrivilegeBuyLog();
        privilegeBuyLog.setProductId(productId);
        privilegeBuyLog.setUserId(userId);
        privilegeBuyLog.setBuyFund(buyFund);
        privilegeBuyLog.setBuyCopies(buyCopies);
        privilegeBuyLog.setApr(apr);
        privilegeBuyLog.setBuyTime(buyTime);
        privilegeBuyLog.setDevice(device);
        return privilegeBuyLogRepository.createOrUpdate(privilegeBuyLog);
    }
}