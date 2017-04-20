package com.ydzb.account.service.impl;

import com.ydzb.account.entity.WmActivityOctoberRedpacket;
import com.ydzb.account.entity.WmRagularUserAccount;
import com.ydzb.account.entity.WmRedpacketUser;
import com.ydzb.account.entity.WmRedpacketVoucher;
import com.ydzb.account.repository.WmActivityOctoberRedpacketRepository;
import com.ydzb.account.service.IWmActivityOctoberRedpacketService;
import com.ydzb.account.service.IWmRagularUserAccountService;
import com.ydzb.account.service.IWmRedpacketUserService;
import com.ydzb.account.service.IWmRedpacketVoucherService;
import com.ydzb.core.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 十月红包活动service实现
 */
@Service
public class WmActivityOctoberRedpacketServiceImpl implements IWmActivityOctoberRedpacketService {

    @Autowired
    private IWmRagularUserAccountService wmRagularUserAccountService;
    @Autowired
    private IWmRedpacketVoucherService wmRedpacketVoucherService;
    @Autowired
    private IWmRedpacketUserService wmRedpacketUserService;
    @Autowired
    private WmActivityOctoberRedpacketRepository wmActivityOctoberRedpacketRepository;

    public static final BigDecimal REDPACKET_LIMIT = new BigDecimal(10000);    //每1万一个红包

    /**
     * 创建
     * @param userId
     * @param buyFund
     * @param days
     * @param buyType
     * @param redpacketFund
     * @param redpacketUserId
     * @param accountId
     * @return
     */
    @Override
    public WmActivityOctoberRedpacket createOne(Long userId, BigDecimal buyFund, Integer days,
            Integer buyType, BigDecimal redpacketFund, Long redpacketUserId, Long accountId) {
        WmActivityOctoberRedpacket redpacket = new WmActivityOctoberRedpacket();
        redpacket.setUserId(userId);
        redpacket.setBuyFund(buyFund);
        redpacket.setDays(days);
        redpacket.setBuyType(buyType);
        redpacket.setRedpacketFund(redpacketFund);
        redpacket.setRedpacketUserId(redpacketUserId);
        redpacket.setAccountId(accountId);
        redpacket.setCreated(DateUtil.getSystemTimeSeconds());
        return wmActivityOctoberRedpacketRepository.createOrUpdate(redpacket);
    }

    /**
     * 创建
     * @param userId
     * @param account
     * @param redpacketFund
     * @param redpacketUserId
     * @return
     */
    @Override
    public WmActivityOctoberRedpacket createOne(Long userId, WmRagularUserAccount account, BigDecimal redpacketFund, Long redpacketUserId) {
        BigDecimal buyFund = account == null? null: account.getBuyFund();
        Integer days = account == null? null: account.getDays();
        Integer buyType = account == null? null: account.getBuyType();
        Long accountId = account == null? null: account.getId();
        return createOne(userId, buyFund, days, buyType, redpacketFund, redpacketUserId, accountId);
    }

    /**
     * 发放红包
     * @param userId 用户id
     * @param buyFund 购买金额
     * @param days
     * @param accountId
     * @param voucher
     */
    @Override
    public void sendRedpacket(Long userId, BigDecimal buyFund, Integer days, Long accountId, WmRedpacketVoucher voucher) {

        //红包数量
        BigDecimal redpacketCount = buyFund.divideAndRemainder(REDPACKET_LIMIT)[0];
        if (redpacketCount == null || redpacketCount.compareTo(BigDecimal.ZERO) <= 0) {
            return;
        }

        WmRagularUserAccount account = wmRagularUserAccountService.queryById(accountId);
        BigDecimal redpacketFund;
        //发放红包
        for (int i = 0; i < redpacketCount.intValue(); i ++) {
            try {
                redpacketFund = new BigDecimal(getRedpacketFund(days));
                WmRedpacketUser redpacketUser = wmRedpacketUserService.sendVoucher(userId, redpacketFund, voucher, WmRedpacketVoucher.TRIGGERTYPE_OCTREDPACKET, 10000);
                createOne(userId, account, redpacketFund, redpacketUser == null? null: redpacketUser.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Integer getRedpacketFund(Integer days) {
        if (days == null) {
            return 20;
        }
        switch (days) {
            case 30: return 20;
            case 90: return 30;
            case 180: return 40;
            case 360:
            case 365: return 50;
            default: return 20;
        }
    }
}