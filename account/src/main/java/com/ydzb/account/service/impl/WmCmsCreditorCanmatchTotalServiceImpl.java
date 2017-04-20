package com.ydzb.account.service.impl;

import com.ydzb.account.entity.WmCmsCreditorCanmatchTotal;
import com.ydzb.account.repository.WmCmsCreditorCanmatchTotalRepository;
import com.ydzb.account.service.IWmCmsCreditorCanmatchTotalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.LockModeType;
import java.math.BigDecimal;

/**
 * 债券总数service实现
 */
@Service
public class WmCmsCreditorCanmatchTotalServiceImpl implements IWmCmsCreditorCanmatchTotalService {

    @Autowired
    private WmCmsCreditorCanmatchTotalRepository cmsCreditorCanmatchTotalRepository;

    /**
     * 增加总债券购买
     * @param fund 金额
     * @return
     */
    @Override
    public WmCmsCreditorCanmatchTotal increaseBuy(BigDecimal fund) throws Exception {
        WmCmsCreditorCanmatchTotal matchTotal = cmsCreditorCanmatchTotalRepository.queryLatestOne(LockModeType.PESSIMISTIC_WRITE);
        if (matchTotal != null) {
            matchTotal.setBuy(matchTotal.getBuy() == null? fund: matchTotal.getBuy().add(fund));
            return (WmCmsCreditorCanmatchTotal) cmsCreditorCanmatchTotalRepository.createOrUpdate(matchTotal);
        }
        return null;
    }

    /**
     * 增加总债券赎回
     * @param fund 金额
     * @return
     */
    @Override
    public WmCmsCreditorCanmatchTotal increaseRedeem(BigDecimal fund) throws Exception {
        WmCmsCreditorCanmatchTotal matchTotal = cmsCreditorCanmatchTotalRepository.queryLatestOne(LockModeType.PESSIMISTIC_WRITE);
        if (matchTotal != null) {
            matchTotal.setRedeem(matchTotal.getRedeem() == null? fund: matchTotal.getRedeem().add(fund));
            return (WmCmsCreditorCanmatchTotal) cmsCreditorCanmatchTotalRepository.createOrUpdate(matchTotal);
        }
        return null;
    }
}