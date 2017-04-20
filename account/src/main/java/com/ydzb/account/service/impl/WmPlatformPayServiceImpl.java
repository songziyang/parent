package com.ydzb.account.service.impl;

import com.ydzb.account.entity.WmPlatformPay;
import com.ydzb.account.repository.WmPlatformPayRepository;
import com.ydzb.account.service.IWmPlatformPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 平台支出结算service实现类
 *
 * @author sy
 */
@Service
public class WmPlatformPayServiceImpl implements IWmPlatformPayService {

    @Autowired
    private WmPlatformPayRepository platformPayRepository;

    /**
     * 平台支出结算
     *
     * @param endDate
     * @throws Exception
     */
    @Override
    public void accountPlatformPay(Long endDate) throws Exception {
        //开始时间
        Long startDate = endDate - 86400;   //3600秒 * 24小时 = 86400秒

        //活期宝收益总额
        BigDecimal currentFund = platformPayRepository.findCurrentAllFund(startDate, endDate);
        //判断是否有收益
        if (currentFund != null && currentFund.doubleValue() > 0)
            saveWmPlatformPay(currentFund, WmPlatformPay.CURRENT, startDate);

        //定存宝收益总额
        BigDecimal regularFund = platformPayRepository.findRegularAllFund(startDate, endDate);
        //判断是否收益
        if (regularFund != null && regularFund.doubleValue() > 0)
            saveWmPlatformPay(regularFund, WmPlatformPay.REGULAR, startDate);

        //机构宝收益总额
        BigDecimal institutionFund = platformPayRepository.findInstitutionAllFund(startDate, endDate);
        //判断是否收益
        if (institutionFund != null && institutionFund.doubleValue() > 0)
            saveWmPlatformPay(institutionFund, WmPlatformPay.INSTITUTION, startDate);

        //自主投资收益总额
        BigDecimal privateOrderingFund = platformPayRepository.findPrivateOrderingAllFund(startDate, endDate);
        //判断是否收益
        if (privateOrderingFund != null && privateOrderingFund.doubleValue() > 0)
            saveWmPlatformPay(privateOrderingFund, WmPlatformPay.PRIVATE_ORDERING, startDate);

        //推荐收益返现总额
        BigDecimal refereeFund = platformPayRepository.findRefereeAllFund(startDate, endDate);
        //判断是否收益
        if (refereeFund != null && refereeFund.doubleValue() > 0)
            saveWmPlatformPay(refereeFund, WmPlatformPay.REFEREE, startDate);
    }


    /**
     * 保存平台收益统计
     *
     * @param fund
     * @param type
     * @param opDate
     */
    public void saveWmPlatformPay(BigDecimal fund, Byte type, Long opDate) {
        WmPlatformPay platformPay = new WmPlatformPay();
        platformPay.setFund(fund);
        platformPay.setType(type);
        platformPay.setOperationTime(opDate);
        platformPayRepository.save(platformPay);
    }


    public WmPlatformPayRepository getPlatformPayRepository() {
        return platformPayRepository;
    }

    public void setPlatformPayRepository(WmPlatformPayRepository platformPayRepository) {
        this.platformPayRepository = platformPayRepository;
    }
}