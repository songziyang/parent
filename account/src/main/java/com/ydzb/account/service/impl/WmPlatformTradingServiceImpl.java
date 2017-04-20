package com.ydzb.account.service.impl;

import com.ydzb.account.entity.WmPlatformTrading;
import com.ydzb.account.entity.WmPlatformTradingDeal;
import com.ydzb.account.entity.WmProductInfo;
import com.ydzb.account.entity.WmRagularUserAccount;
import com.ydzb.account.repository.WmPlatformTradingRepository;
import com.ydzb.account.service.IWmPlatformTradingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
public class WmPlatformTradingServiceImpl implements IWmPlatformTradingService {

    @Autowired
    private WmPlatformTradingRepository platformTradingRepository;

    /**
     * 平台交易统计
     *
     * @param endDate 系统当前日期
     *
     * @throws Exception
     */
    @Override
    public void accountPlatformTrading(Long endDate) throws Exception {
        //开始时间
        Long startDate = endDate - 86400;

        //查询充值之和
        BigDecimal rechargeFund = platformTradingRepository.findRechargeAllFund(startDate, endDate);
        //保存充值记录
        if (rechargeFund != null && rechargeFund.doubleValue() > 0)
            saveWmPlatformTrading(rechargeFund, 1, startDate);

        //查询提现之和
        BigDecimal withdrawFund = platformTradingRepository.findWithDrawAllFund(startDate, endDate);
        if (withdrawFund != null && withdrawFund.doubleValue() > 0)
            //查询提现之和
            saveWmPlatformTrading(withdrawFund, 2, startDate);

        //活期宝之和
        BigDecimal currentFund = platformTradingRepository.findCurrentAllFund(startDate, endDate);
        if (currentFund != null && currentFund.doubleValue() > 0) {
            //活期宝
            Long currentTradeId = saveWmPlatformTrading(currentFund, 3, startDate);
            //活期宝记录
            List<WmProductInfo> currentWmProductInfos = platformTradingRepository.findCurrentWmProductInfo(startDate, endDate);
            if (currentWmProductInfos != null && currentWmProductInfos.size() > 0) {
                for (WmProductInfo productInfo : currentWmProductInfos) {
                    if (productInfo.getFunds() - productInfo.getSurplus() > 0) {
                        saveWmPlatformTradingDeal(currentTradeId, productInfo.getName(), new BigDecimal(productInfo.getFunds() - productInfo.getSurplus()), startDate);
                    }
                }
            }
        }

        //定存宝之和
        BigDecimal ragularFund = platformTradingRepository.findRagularAllFund(startDate, endDate);
        if (ragularFund != null && ragularFund.doubleValue() > 0) {
            //定存宝
            Long ragularTradeId = saveWmPlatformTrading(ragularFund, 4, startDate);
            //定存宝记录
            List<Object[]> wmRagularUserAccounts = platformTradingRepository.findRagularWmProductInfo(startDate, endDate);
            if (wmRagularUserAccounts != null && wmRagularUserAccounts.size() > 0) {
                for (Object[] row: wmRagularUserAccounts) {
                    BigDecimal buyFund = row[0] == null? BigDecimal.ZERO: (BigDecimal) row[0];
                    String productName = row[1] == null? "": (String) row[1];
                    if (buyFund.compareTo(BigDecimal.ZERO) > 0) {
                        saveWmPlatformTradingDeal(ragularTradeId, productName, buyFund, startDate);
                    }
                }
            }
        }


        //机构宝之和
        BigDecimal institutionFund = platformTradingRepository.findInstitutionAllFund(startDate, endDate);
        if (institutionFund != null && institutionFund.doubleValue() > 0) {
            //定存宝
            Long institutionFundTradeId = saveWmPlatformTrading(institutionFund, 5, startDate);
            List<Object[]> wmRagularUserAccounts = platformTradingRepository.findInstitutionWmProductInfo(startDate, endDate);
            if (wmRagularUserAccounts != null && wmRagularUserAccounts.size() > 0) {
                for (Object[] row: wmRagularUserAccounts) {
                    BigDecimal buyFund = row[0] == null? BigDecimal.ZERO: (BigDecimal) row[0];
                    String productName = row[1] == null? "": (String) row[1];
                    if (buyFund.compareTo(BigDecimal.ZERO) > 0) {
                        saveWmPlatformTradingDeal(institutionFundTradeId, productName, buyFund, startDate);
                    }
                }
            }
        }

        //私人定制
        BigDecimal selfFund = platformTradingRepository.findSelfAllFund(startDate, endDate);
        if (selfFund != null && selfFund.doubleValue() > 0)
            saveWmPlatformTrading(selfFund, 6, startDate);

        try {
            //随心存之和
            BigDecimal freeFund = platformTradingRepository.findFreeAllFund(startDate, endDate);
            if (freeFund != null && freeFund.doubleValue() > 0) {
                //随心存
                Long freeTradeId = saveWmPlatformTrading(freeFund, 7, startDate);
                //随心存记录
                List<Object[]> wmFreeUserAccounts = platformTradingRepository.findFreeWmProductInfo(startDate, endDate);
                if (wmFreeUserAccounts != null && wmFreeUserAccounts.size() > 0) {
                    for (Object[] row: wmFreeUserAccounts) {
                        if (row != null) {
                            BigDecimal buyFund = row[0] == null? BigDecimal.ZERO: (BigDecimal) row[0];
                            String productName = row[1] == null? "": (String) row[1];
                            Integer days = row[2] == null? 0: (Integer)row[2];
                            if (buyFund.compareTo(BigDecimal.ZERO) == 1) {
                                saveWmPlatformTradingDeal(freeTradeId, productName + days + "天", buyFund, startDate);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 平台交易统计
     *
     * @param fund   金额
     * @param type   1、充值 2、提现 3、活期宝 4、定存宝 5、机构宝 6、私人定制
     * @param opDate 操作日期
     * @return
     */
    public Long saveWmPlatformTrading(BigDecimal fund, Integer type, Long opDate) {
        WmPlatformTrading platformTrading = new WmPlatformTrading();
        platformTrading.setFund(fund);
        platformTrading.setType(type);
        platformTrading.setOpdate(opDate);
        return platformTradingRepository.saveWmPlatformTrading(platformTrading);
    }

    /**
     * 平台交易统计详细
     *
     * @param tradeId
     * @param name
     * @param fund
     * @param opDate
     */
    public void saveWmPlatformTradingDeal(Long tradeId, String name, BigDecimal fund, Long opDate) {
        WmPlatformTradingDeal platformTradingDeal = new WmPlatformTradingDeal();
        platformTradingDeal.setTradeId(tradeId);
        platformTradingDeal.setName(name);
        platformTradingDeal.setFund(fund);
        platformTradingDeal.setOpdate(opDate);
        platformTradingRepository.saveWmPlatformTradingDeal(platformTradingDeal);

    }

    public WmPlatformTradingRepository getPlatformTradingRepository() {
        return platformTradingRepository;
    }

    public void setPlatformTradingRepository(WmPlatformTradingRepository platformTradingRepository) {
        this.platformTradingRepository = platformTradingRepository;
    }


}
