package com.ydzb.account.service.impl;

import com.ydzb.account.entity.WmUserInvestinfo;
import com.ydzb.account.repository.WmUserInvestInfoRepository;
import com.ydzb.account.service.IWmUserInvestInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.LockModeType;
import java.math.BigDecimal;

/**
 * 用户投资service实现
 */
@Service
public class WmUserInvestInfoServiceImpl implements IWmUserInvestInfoService {

    @Autowired
    private WmUserInvestInfoRepository wmUserInvestInfoRepository;

    @Override
    public WmUserInvestinfo findByUser(Long userId) {
        return wmUserInvestInfoRepository.findByUser(userId);
    }

    @Override
    public WmUserInvestinfo findByUser(Long userId, LockModeType lockType) {
        return wmUserInvestInfoRepository.findByUser(userId, lockType);

    }
    @Override
    public WmUserInvestinfo saveOrUpdate(WmUserInvestinfo investinfo) throws Exception{
        return wmUserInvestInfoRepository.saveOrUpdate(investinfo);
    }

    /**
     * 增加活期宝投资
     * @param userId 用户ID
     * @param fund 金额
     */
    @Override
    public WmUserInvestinfo increaseCurrentInvest(Long userId, BigDecimal fund) throws Exception {
        WmUserInvestinfo userInvestinfo = wmUserInvestInfoRepository.findByUser(userId, LockModeType.PESSIMISTIC_WRITE);
        if (userInvestinfo != null) {
            //投资总额
            userInvestinfo.setAllInvest(userInvestinfo.getAllInvest() == null ? fund : userInvestinfo.getAllInvest().add(fund));
            //活期投资总额
            userInvestinfo.setAllInvestDayloan(userInvestinfo.getAllInvestDayloan() == null ? fund : userInvestinfo.getAllInvestDayloan().add(fund));
            //更新用户投资
            wmUserInvestInfoRepository.createOrUpdate(userInvestinfo);
        }
        return userInvestinfo;
    }

    /**
     * 增加新手宝投资
     * @param userId 用户ID
     * @param fund 金额
     */
    @Override
    public WmUserInvestinfo increasePrivilegeInvest(Long userId, BigDecimal fund) throws Exception {
        WmUserInvestinfo userInvestinfo = wmUserInvestInfoRepository.findByUser(userId, LockModeType.PESSIMISTIC_WRITE);
        if (userInvestinfo != null) {
            //投资总额
            userInvestinfo.setAllInvest(userInvestinfo.getAllInvest() == null? fund: userInvestinfo.getAllInvest().add(fund));
            //新手特权投资总额
            userInvestinfo.setAllInvestPrivilege(userInvestinfo.getAllInvestPrivilege() == null? fund: userInvestinfo.getAllInvestPrivilege().add(fund));
            //更新用户投资
            wmUserInvestInfoRepository.saveOrUpdate(userInvestinfo);
        }
        return userInvestinfo;
    }



    /**
     * 减少定存宝投资
     * @param userId 用户ID
     * @param fund 金额
     */
    @Override
    public WmUserInvestinfo decreaseRagularInvest(Long userId, BigDecimal fund) throws Exception{
        WmUserInvestinfo userInvestinfo = wmUserInvestInfoRepository.findByUser(userId, LockModeType.PESSIMISTIC_WRITE);
        if (userInvestinfo != null) {
            //投资总额
            userInvestinfo.setAllInvest(userInvestinfo.getAllInvest() == null? BigDecimal.ZERO: userInvestinfo.getAllInvest().subtract(fund));
            //定存投资总额
            userInvestinfo.setAllInvestDeposit(userInvestinfo.getAllInvestDeposit() == null? BigDecimal.ZERO: userInvestinfo.getAllInvestDeposit().subtract(fund));
            //更新用户投资
            wmUserInvestInfoRepository.createOrUpdate(userInvestinfo);
        }
        return userInvestinfo;
    }

    /**
     * 减少随心存投资
     * @param userId 用户ID
     * @param fund 金额
     */
    @Override
    public WmUserInvestinfo decreaseFreeInvest(Long userId, BigDecimal fund) throws Exception {
        WmUserInvestinfo userInvestinfo = wmUserInvestInfoRepository.findByUser(userId, LockModeType.PESSIMISTIC_WRITE);
        if (userInvestinfo != null) {
            //投资总额
            userInvestinfo.setAllInvest(userInvestinfo.getAllInvest() == null? BigDecimal.ZERO: userInvestinfo.getAllInvest().subtract(fund));
            //随心存投资总额
            userInvestinfo.setAllInvestDeposit(userInvestinfo.getAllInvestFree() == null? BigDecimal.ZERO: userInvestinfo.getAllInvestFree().subtract(fund));
            //更新用户投资
            wmUserInvestInfoRepository.createOrUpdate(userInvestinfo);
        }
        return userInvestinfo;
    }

    /**
     * 增加定存宝投资
     * @param userId 用户ID
     * @param fund 金额
     */
    @Override
    public WmUserInvestinfo increaseRagularInvest(Long userId, BigDecimal fund) throws Exception{
        WmUserInvestinfo userInvestinfo = wmUserInvestInfoRepository.findByUser(userId, LockModeType.PESSIMISTIC_WRITE);
        if (userInvestinfo != null) {
            //投资总额
            userInvestinfo.setAllInvest(userInvestinfo.getAllInvest() == null? fund: userInvestinfo.getAllInvest().add(fund));
            //定存投资总额
            userInvestinfo.setAllInvestDeposit(userInvestinfo.getAllInvestDeposit() == null? fund: userInvestinfo.getAllInvestDeposit().add(fund));
            //更新用户投资
            wmUserInvestInfoRepository.createOrUpdate(userInvestinfo);
        }
        return userInvestinfo;
    }

    /**
     * 增加定存类赎回冻结金额
     * @param userId 用户id
     * @param fund 金额
     * @return
     * @throws Exception
     */
    @Override
    public WmUserInvestinfo increaseAllRedeemFreeze(Long userId, BigDecimal fund) throws Exception {
        WmUserInvestinfo userInvestinfo = wmUserInvestInfoRepository.findByUser(userId, LockModeType.PESSIMISTIC_WRITE);
        if (userInvestinfo != null) {
            userInvestinfo.setAllRedeemFreeze(userInvestinfo.getAllRedeemFreeze() == null? fund: userInvestinfo.getAllRedeemFreeze().add(fund));
            wmUserInvestInfoRepository.createOrUpdate(userInvestinfo);
        }
        return userInvestinfo;
    }


    /**
     * 增加定存类购买冻结金额
     * @param userId 用户id
     * @param fund 金额
     * @return
     * @throws Exception
     */
    @Override
    public WmUserInvestinfo increaseAllBuyFreeze(Long userId, BigDecimal fund) throws Exception {
        WmUserInvestinfo userInvestinfo = wmUserInvestInfoRepository.findByUser(userId, LockModeType.PESSIMISTIC_WRITE);
        if (userInvestinfo != null) {
            userInvestinfo.setAllBuyFreeze(userInvestinfo.getAllBuyFreeze() == null? fund: userInvestinfo.getAllBuyFreeze().add(fund));
            wmUserInvestInfoRepository.createOrUpdate(userInvestinfo);
        }
        return userInvestinfo;
    }

    /**
     * 将新手宝投资转换为活期宝投资
     * @param userId 用户id
     * @param fund 金额
     * @return
     * @throws Exception
     */
    @Override
    public WmUserInvestinfo convertPrvilegeToCurrent(Long userId, BigDecimal fund) throws Exception {
        WmUserInvestinfo userInvestinfo = wmUserInvestInfoRepository.findByUser(userId, LockModeType.PESSIMISTIC_WRITE);
        if (userInvestinfo != null) {
            userInvestinfo.setAllInvestPrivilege(userInvestinfo.getAllInvestPrivilege() == null? BigDecimal.ZERO: userInvestinfo.getAllInvestPrivilege().subtract(fund));
            userInvestinfo.setAllInvestDayloan(userInvestinfo.getAllInvestDayloan() == null? fund: userInvestinfo.getAllInvestDayloan().add(fund));
            wmUserInvestInfoRepository.createOrUpdate(userInvestinfo);
        }
        return userInvestinfo;
    }
}