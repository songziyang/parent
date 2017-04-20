package com.ydzb.account.service.impl;


import com.ydzb.account.context.WmPlatformRecordLinkType;
import com.ydzb.account.context.WmPlatformRecordType;
import com.ydzb.account.entity.WmPlatformUser;
import com.ydzb.account.entity.WmUser;
import com.ydzb.account.entity.WmUserInvestinfo;
import com.ydzb.account.entity.WmUserMoney;
import com.ydzb.account.repository.IWmPlatformUserRepository;
import com.ydzb.account.repository.WmPlatformUserRepository;
import com.ydzb.account.service.IWmPlatformFundRecordService;
import com.ydzb.account.service.IWmPlatformInvestRecordService;
import com.ydzb.account.service.IWmPlatformUserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 平台用户service实现
 */
@Service
public class WmPlatformUserServiceImpl implements IWmPlatformUserService {

    Logger logger = Logger.getLogger(WmPlatformUserServiceImpl.class);


    @Autowired
    private IWmPlatformUserRepository platformUserRepository;

    @Autowired
    private IWmPlatformFundRecordService platformFundRecordService;

    @Autowired
    private IWmPlatformInvestRecordService platformInvestRecordService;

    @Autowired
    private WmPlatformUserRepository wmPlatformUserRepository;


    /**
     * 查询最新的一条数据
     *
     * @return
     */
    @Override
    public Long findLastPlatformUser() {
        return platformUserRepository.findLastPlatformUser();
    }


    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @Override
    public WmPlatformUser findPlatformUserById(Long id) {
        return platformUserRepository.findPlatformUserById(id);
    }


    /**
     * 用户购买债权
     *
     * @param opfund   操作金额
     * @param linkId   链接ID
     * @param linkType 链接类型
     * @param opinfo   操作说明
     */
    @Override
    public void purchase(BigDecimal opfund, Long linkId, WmPlatformRecordLinkType linkType, String opinfo) {
        WmPlatformUser platformUser = findPlatformUser();
        WmUserInvestinfo userInvest = wmPlatformUserRepository.findWmUserInvestinfoByUserId(WmUser.PL_ID);
        WmUserMoney userMoney = wmPlatformUserRepository.findWmUserMoneyByUserId(WmUser.PL_ID);
        if (platformUser != null && userInvest != null && userMoney != null) {

            //平台账户
            if (platformUser.getUsableFund() == null) platformUser.setUsableFund(BigDecimal.ZERO);
            if (platformUser.getAllInvest() == null) platformUser.setAllInvest(BigDecimal.ZERO);

            platformUser.setUsableFund(platformUser.getUsableFund().add(opfund));
            platformUser.setAllInvest(platformUser.getAllInvest().subtract(opfund));
            platformUserRepository.save(platformUser);


            //老翟账户资金
            if (userMoney.getUsableFund() == null) userMoney.setUsableFund(BigDecimal.ZERO);
            userMoney.setUsableFund(userMoney.getUsableFund().add(opfund));
            wmPlatformUserRepository.updateWmUserMoney(userMoney);

            //老翟账户债权
            if (userInvest.getAllInvest() == null) userInvest.setAllInvest(BigDecimal.ZERO);
            if (userInvest.getAllInvestDeposit() == null) userInvest.setAllInvestDeposit(BigDecimal.ZERO);
            userInvest.setAllInvest(userInvest.getAllInvest().subtract(opfund));
            userInvest.setAllInvestDeposit(userInvest.getAllInvestDeposit().subtract(opfund));
            wmPlatformUserRepository.updateWmUserInvestinfo(userInvest);

            //平台余额记录
            platformFundRecordService.savePlatformFundRecord(WmPlatformRecordType.FUND_IN, opfund, platformUser.getUsableFund(), linkId, linkType, opinfo);
            //平台债权记录
            platformInvestRecordService.savePlatformInvestRecord(WmPlatformRecordType.FUND_OUT, opfund, platformUser.getAllInvest(), linkId, linkType, opinfo);

        } else {
            logger.error("========平台信息错误========");
        }

    }

    /**
     * 用户赎回债权
     *
     * @param opfund   操作金额
     * @param linkType 链接类型
     * @param opinfo   操作说明
     */
    @Override
    public void redeem(BigDecimal opfund, Long linkId, WmPlatformRecordLinkType linkType, String opinfo) {

        WmPlatformUser platformUser = findPlatformUser();
        WmUserInvestinfo userInvest = wmPlatformUserRepository.findWmUserInvestinfoByUserId(WmUser.PL_ID);
        WmUserMoney userMoney = wmPlatformUserRepository.findWmUserMoneyByUserId(WmUser.PL_ID);
        if (platformUser != null && userInvest != null && userMoney != null) {

            //平台账户
            if (platformUser.getUsableFund() == null) platformUser.setUsableFund(BigDecimal.ZERO);
            if (platformUser.getAllInvest() == null) platformUser.setAllInvest(BigDecimal.ZERO);

            platformUser.setUsableFund(platformUser.getUsableFund().subtract(opfund));
            platformUser.setAllInvest(platformUser.getAllInvest().add(opfund));
            platformUserRepository.save(platformUser);

            //老翟账户资金
            if (userMoney.getUsableFund() == null) userMoney.setUsableFund(BigDecimal.ZERO);
            userMoney.setUsableFund(userMoney.getUsableFund().subtract(opfund));
            wmPlatformUserRepository.updateWmUserMoney(userMoney);

            //老翟账户债权
            if (userInvest.getAllInvest() == null) userInvest.setAllInvest(BigDecimal.ZERO);
            if (userInvest.getAllInvestDeposit() == null) userInvest.setAllInvestDeposit(BigDecimal.ZERO);
            userInvest.setAllInvest(userInvest.getAllInvest().add(opfund));
            userInvest.setAllInvestDeposit(userInvest.getAllInvestDeposit().add(opfund));
            wmPlatformUserRepository.updateWmUserInvestinfo(userInvest);

            //平台余额记录
            platformFundRecordService.savePlatformFundRecord(WmPlatformRecordType.FUND_OUT, opfund, platformUser.getUsableFund(), linkId, linkType, opinfo);
            //平台债权记录
            platformInvestRecordService.savePlatformInvestRecord(WmPlatformRecordType.FUND_IN, opfund, platformUser.getAllInvest(), linkId, linkType, opinfo);

        } else {
            logger.error("========平台信息错误========");
        }
    }


    /**
     * 查询平台账户
     *
     * @return
     */
    public WmPlatformUser findPlatformUser() {
        Long platformId = findLastPlatformUser();
        if (platformId != null) {
            return findPlatformUserById(platformId);
        }
        return null;
    }


    public IWmPlatformUserRepository getPlatformUserRepository() {
        return platformUserRepository;
    }

    public void setPlatformUserRepository(IWmPlatformUserRepository platformUserRepository) {
        this.platformUserRepository = platformUserRepository;
    }

    public IWmPlatformFundRecordService getPlatformFundRecordService() {
        return platformFundRecordService;
    }

    public void setPlatformFundRecordService(IWmPlatformFundRecordService platformFundRecordService) {
        this.platformFundRecordService = platformFundRecordService;
    }

    public IWmPlatformInvestRecordService getPlatformInvestRecordService() {
        return platformInvestRecordService;
    }

    public void setPlatformInvestRecordService(IWmPlatformInvestRecordService platformInvestRecordService) {
        this.platformInvestRecordService = platformInvestRecordService;
    }

    public WmPlatformUserRepository getWmPlatformUserRepository() {
        return wmPlatformUserRepository;
    }

    public void setWmPlatformUserRepository(WmPlatformUserRepository wmPlatformUserRepository) {
        this.wmPlatformUserRepository = wmPlatformUserRepository;
    }
}