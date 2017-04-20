package com.ydzb.product.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.product.entity.PlatformUser;
import com.ydzb.product.enumeration.PlatformRecordLinkType;
import com.ydzb.product.enumeration.PlatformRecordType;
import com.ydzb.product.repository.IPlatformUserRepository;
import com.ydzb.product.repository.PlatformUserNewRepository;
import com.ydzb.product.service.IPlatformFundRecordService;
import com.ydzb.product.service.IPlatformInvestRecordService;
import com.ydzb.product.service.IPlatformUserService;
import com.ydzb.user.entity.UserInvestinfo;
import com.ydzb.user.entity.UserMoney;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 平台用户service实现
 */
@Service
public class PlatformUserServiceImpl extends BaseServiceImpl<PlatformUser, Long> implements IPlatformUserService {

    public static final long PL_ID = 84033;


    @Autowired
    private IPlatformUserRepository platformUserRepository;
    @Autowired
    private IPlatformFundRecordService platformFundRecordService;
    @Autowired
    private IPlatformInvestRecordService platformInvestRecordService;
    @Autowired
    private PlatformUserNewRepository userRepository;

    /**
     * 查询最新的一条数据
     *
     * @return
     */
    @Override
    public PlatformUser queryLastOne() {
        return platformUserRepository.queryLastOne();
    }

    /**
     * 充值
     *
     * @param platformUserId 平台用户id
     * @param fund           充值金额
     * @return
     */
    @Override
    public String recharge(Long platformUserId, BigDecimal fund, String desc) {
        if (platformUserId == null) {
            return "充值失败,不存在该平台用户记录";
        }
        if (fund == null || fund.compareTo(BigDecimal.ZERO) <= 0) {
            return "充值失败,请填写正确充值金额";
        }
        PlatformUser platformUser = platformUserRepository.queryByIdWithPessimisticWriteLock(platformUserId);
        if (platformUser == null) {
            return "充值失败,不存在该记录";
        }


        UserMoney userMoney = userRepository.findWmUserMoneyByUserId(PL_ID);
        //老翟账户资金
        if (userMoney.getUsableFund() == null) userMoney.setUsableFund(BigDecimal.ZERO);
        if (userMoney.getTotalFund() == null) userMoney.setTotalFund(BigDecimal.ZERO);
        userMoney.setUsableFund(userMoney.getUsableFund().add(fund));
        userMoney.setTotalFund(userMoney.getTotalFund().add(fund));
        userRepository.updateUserMoney(userMoney);

        BigDecimal usableFund = platformUser.getUsableFund() == null ? fund : platformUser.getUsableFund().add(fund);
        platformUser.setUsableFund(usableFund);
        platformUserRepository.save(platformUser);
        platformFundRecordService.createOne(PlatformRecordType.FUND_IN, fund, usableFund, null, PlatformRecordLinkType.RECHARGE, desc);


        return "success";
    }

    /**
     * 购买债权
     *
     * @param platformUserId 平台用户id
     * @param fund           债权购买金额
     * @param desc           说明
     * @return
     */
    @Override
    public String buyDebt(Long platformUserId, BigDecimal fund, String desc) {

        if (platformUserId == null) {
            return "债权购买失败,不存在该平台用户记录";
        }

        if (fund == null || fund.compareTo(BigDecimal.ZERO) <= 0) {
            return "债权购买失败,请填写正确购买金额";
        }

        PlatformUser platformUser = platformUserRepository.queryByIdWithPessimisticWriteLock(platformUserId);
        if (platformUser == null) {
            return "债权购买失败,不存在该记录";
        }

        BigDecimal allInvest = platformUser.getAllInvest() == null ? BigDecimal.ZERO : platformUser.getAllInvest();
        allInvest = allInvest.add(fund);
        platformUser.setAllInvest(allInvest);
        platformUserRepository.save(platformUser);

        UserInvestinfo userInvest = userRepository.findWmUserInvestinfoByUserId(PL_ID);

        //老翟账户债权
        if (userInvest.getAllInvest() == null) userInvest.setAllInvest(BigDecimal.ZERO);
        if (userInvest.getAllInvestDeposit() == null) userInvest.setAllInvestDeposit(BigDecimal.ZERO);
        userInvest.setAllInvest(userInvest.getAllInvest().add(fund));
        userInvest.setAllInvestDeposit(userInvest.getAllInvestDeposit().add(fund));
        userRepository.updateUserInvestinfo(userInvest);

        //老翟用户资金
        UserMoney userMoney = userRepository.findWmUserMoneyByUserId(PL_ID);
        if (userMoney.getTotalFund() == null) userMoney.setTotalFund(BigDecimal.ZERO);
        userMoney.setTotalFund(userMoney.getTotalFund().add(fund));
        userRepository.updateUserMoney(userMoney);

        platformInvestRecordService.createOne(PlatformRecordType.FUND_IN, fund, allInvest, null, PlatformRecordLinkType.BUY_DEBT, desc);

        return "success";
    }

    /**
     * 售出债权
     *
     * @param platformUserId 平台用户id
     * @param fund           债权售出金额
     * @param desc           说明
     * @return
     */
    @Override
    public String sellDebt(Long platformUserId, BigDecimal fund, String desc) {
        if (platformUserId == null) {
            return "债权售出失败,不存在该平台用户记录";
        }
        if (fund == null || fund.compareTo(BigDecimal.ZERO) <= 0) {
            return "债权售出买失败,请填写正确售出份额";
        }
        PlatformUser platformUser = platformUserRepository.queryByIdWithPessimisticWriteLock(platformUserId);
        if (platformUser == null) {
            return "债权售出失败,不存在该记录";
        }

        BigDecimal allInvest = platformUser.getAllInvest() == null ? BigDecimal.ZERO : platformUser.getAllInvest() ;
        allInvest = allInvest.subtract(fund);
        platformUser.setAllInvest(allInvest);
        platformUserRepository.save(platformUser);

        UserInvestinfo userInvest = userRepository.findWmUserInvestinfoByUserId(PL_ID);
        //老翟账户债权
        if (userInvest.getAllInvest() == null) userInvest.setAllInvest(BigDecimal.ZERO);
        if (userInvest.getAllInvestDeposit() == null) userInvest.setAllInvestDeposit(BigDecimal.ZERO);
        userInvest.setAllInvest(userInvest.getAllInvest().subtract(fund));
        userInvest.setAllInvestDeposit(userInvest.getAllInvestDeposit().subtract(fund));
        userRepository.updateUserInvestinfo(userInvest);

        //老翟用户资金
        UserMoney userMoney = userRepository.findWmUserMoneyByUserId(PL_ID);
        if (userMoney.getTotalFund() == null) userMoney.setTotalFund(BigDecimal.ZERO);
        userMoney.setTotalFund(userMoney.getTotalFund().subtract(fund));
        userRepository.updateUserMoney(userMoney);


        platformInvestRecordService.createOne(PlatformRecordType.FUND_OUT, fund, allInvest, platformUserId, PlatformRecordLinkType.SELL_DEBT, desc);

        return "success";
    }


    public IPlatformUserRepository getPlatformUserRepository() {
        return platformUserRepository;
    }

    public void setPlatformUserRepository(IPlatformUserRepository platformUserRepository) {
        this.platformUserRepository = platformUserRepository;
    }

    public IPlatformFundRecordService getPlatformFundRecordService() {
        return platformFundRecordService;
    }

    public void setPlatformFundRecordService(IPlatformFundRecordService platformFundRecordService) {
        this.platformFundRecordService = platformFundRecordService;
    }

    public IPlatformInvestRecordService getPlatformInvestRecordService() {
        return platformInvestRecordService;
    }

    public void setPlatformInvestRecordService(IPlatformInvestRecordService platformInvestRecordService) {
        this.platformInvestRecordService = platformInvestRecordService;
    }

    public PlatformUserNewRepository getUserRepository() {
        return userRepository;
    }

    public void setUserRepository(PlatformUserNewRepository userRepository) {
        this.userRepository = userRepository;
    }
}