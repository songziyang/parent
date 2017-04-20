package com.ydzb.account.service.impl;

import com.ydzb.account.entity.WmPlatformApr;
import com.ydzb.account.repository.WmPlatformAprRepository;
import com.ydzb.account.repository.WmPlatformStatisticsRepository;
import com.ydzb.account.service.IWmPlatformAprService;
import com.ydzb.core.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 平台综合年化
 */
@Service
public class WmPlatformAprServiceImpl implements IWmPlatformAprService {

    @Autowired
    private WmPlatformAprRepository platformAprRepository;
    @Autowired
    private WmPlatformStatisticsRepository statisticsRepository;

    public static BigDecimal getBigDecimalByObj(Object obj) {
        if ("".equals(getStringByObj(obj))) return BigDecimal.ZERO;
        return StringUtils.isEmpty(getStringByObj(obj).trim()) ? BigDecimal.ZERO : new BigDecimal(getStringByObj(obj));
    }

    public static String getStringByObj(Object obj) {
        if (obj == null) return null;
        return String.valueOf(obj);
    }

    @Override
    public void accountPlatformApr() throws Exception {
        WmPlatformApr platformApr = new WmPlatformApr();

        //系统当前日期
        Long curDate = DateUtil.getSystemTimeDay(DateUtil.getCurrentDate());

        //活期宝
        Object[] dayloanAprObj = platformAprRepository.findDayloanApr(curDate);
        if (dayloanAprObj != null && dayloanAprObj.length > 0) {
            platformApr.setAllDayloanIncome(getBigDecimalByObj(dayloanAprObj[0]));
            platformApr.setAllDayloanFund(getBigDecimalByObj(dayloanAprObj[1]));
            platformApr.setDayloanApr(getBigDecimalByObj(dayloanAprObj[2]));
        }

        //定存宝
        Object[] depositAprObj = platformAprRepository.findDepositApr(curDate);
        if (depositAprObj != null && depositAprObj.length > 0) {
            platformApr.setAllDepositFund(getBigDecimalByObj(depositAprObj[0]));
            platformApr.setAllDepositIncome(getBigDecimalByObj(depositAprObj[1]));
            platformApr.setDepositApr(getBigDecimalByObj(depositAprObj[2]));
        }

        //综合年化
        if (dayloanAprObj != null && dayloanAprObj.length > 0 && depositAprObj != null && depositAprObj.length > 0) {
            platformApr.setApr(platformApr.getAllDayloanIncome().add(platformApr.getAllDepositIncome()).multiply(new BigDecimal(36500)).divide(platformApr.getAllDayloanFund().add(platformApr.getAllDepositFund()), 2, BigDecimal.ROUND_HALF_DOWN));
        }

        //总投资人数
        BigInteger totalPersons = statisticsRepository.findZtjrs();
        platformApr.setTotalPersons(totalPersons == null? 0L: totalPersons.longValue());

        platformApr.setCreated(DateUtil.getSystemTimeSeconds());
        platformAprRepository.saveWmPlatformApr(platformApr);
    }

    public WmPlatformAprRepository getPlatformAprRepository() {
        return platformAprRepository;
    }

    public void setPlatformAprRepository(WmPlatformAprRepository platformAprRepository) {
        this.platformAprRepository = platformAprRepository;
    }
}