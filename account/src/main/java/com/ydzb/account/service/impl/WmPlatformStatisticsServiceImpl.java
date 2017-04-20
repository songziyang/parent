package com.ydzb.account.service.impl;

import com.google.gson.Gson;
import com.ydzb.account.entity.WmPlatformStatistics;
import com.ydzb.account.repository.WmPlatformStatisticsRepository;
import com.ydzb.account.service.IWmPlatformStatisticsService;
import com.ydzb.core.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;


@Service
public class WmPlatformStatisticsServiceImpl implements IWmPlatformStatisticsService {

    private static final String URL = "http://218.244.133.47:15521/Member/Www/tongji";

    @Autowired
    private WmPlatformStatisticsRepository statisticsRepository;



    /**
     * 统计数据 调用接口
     *
     * @throws Exception
     */
    @Override
    public void accountPlatformStatistics() throws Exception {

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(URL);
        String jsonstr = target.request().get().readEntity(String.class);
        Map<String, Object> data = new Gson().fromJson(jsonstr, Map.class);

        WmPlatformStatistics wmPlatformStatistics = new WmPlatformStatistics();
        //累计交易金额
        wmPlatformStatistics.setTotalFund(Long.valueOf((String) data.get("money_total")));
        //累计收益
        BigDecimal b = new BigDecimal((String) data.get("revenue_total"));
        wmPlatformStatistics.setTotalRevenue(b.setScale(2, BigDecimal.ROUND_HALF_UP));
        //活期累计交易金额
        wmPlatformStatistics.setTotalDayloan(Long.valueOf((String) data.get("dayloan_total")));
        //定存累计交易金额
        wmPlatformStatistics.setTotalDeposit(Long.valueOf((String) data.get("deposit_total")));
        //活期年化收益率
        wmPlatformStatistics.setDayloanApr(new BigDecimal((String) data.get("dayloan_apr")));
        //债权金额
        wmPlatformStatistics.setInvestFund(Long.valueOf((String) data.get("droit_total_money")));
        //债权数量
        wmPlatformStatistics.setInvestNum(Long.valueOf((String) data.get("droit_total_invest")));
        //平台数
        wmPlatformStatistics.setPlatformNum(Long.valueOf((String) data.get("droit_total_platform")));
        //投资人数
        wmPlatformStatistics.setInvestPersons(Long.valueOf((String) data.get("people_total")));
        //充值人数
        wmPlatformStatistics.setRechargeNum(Long.valueOf((String) data.get("recharge_count")));

        // 系统当前日期
        Long endDate = DateUtil.getSystemTimeDay(DateUtil.curDate());
        //统计日期
        wmPlatformStatistics.setTotalTime(endDate - 86400);
        //创建时间
        wmPlatformStatistics.setCreated(DateUtil.getSystemTimeSeconds());
        statisticsRepository.saveWmPlatformStatistics(wmPlatformStatistics);
    }


    /**
     * 统计活期宝/定存宝购买人数
     *
     * @throws Exception
     */
    @Override
    public void accountBuyUserCount() throws Exception {
        Long endTime = DateUtil.getSystemTimeDay(DateUtil.getCurrentDate());
        Long startTime = DateUtil.getSystemTimeDay(DateUtil.subDay(DateUtil.getCurrentDate()));
        BigInteger currentPersons = statisticsRepository.findCurrentDailyBuyUsersCount(startTime, endTime);
        BigInteger ragularPersons = statisticsRepository.findRagularDailyBuyUsersCount(startTime, endTime);

        BigInteger currents = statisticsRepository.findCurrents();
        BigInteger ragulars = statisticsRepository.findagulars();
        BigInteger totalPersons = statisticsRepository.findZtjrs();


        List<WmPlatformStatistics> wmPlatformStatisticses = statisticsRepository.findByTotalDate(startTime);
        if (wmPlatformStatisticses == null || wmPlatformStatisticses.isEmpty()) {
            return;

        }

        WmPlatformStatistics wmPlatformStatistics = wmPlatformStatisticses.get(0);
        wmPlatformStatistics.setCurrentPersons(currentPersons == null ? BigInteger.ZERO.longValue() : currentPersons.longValue());
        wmPlatformStatistics.setRagularPersons(ragularPersons == null ? BigInteger.ZERO.longValue() : ragularPersons.longValue());
        wmPlatformStatistics.setCurrents(currents == null ? BigInteger.ZERO.longValue() : currents.longValue());
        wmPlatformStatistics.setRagulars(ragulars == null ? BigInteger.ZERO.longValue() : ragulars.longValue());
        wmPlatformStatistics.setTotalPersons(totalPersons == null ? BigInteger.ZERO.longValue() : totalPersons.longValue());

        statisticsRepository.saveWmPlatformStatistics(wmPlatformStatistics);
    }


    public WmPlatformStatisticsRepository getStatisticsRepository() {
        return statisticsRepository;
    }

    public void setStatisticsRepository(WmPlatformStatisticsRepository statisticsRepository) {
        this.statisticsRepository = statisticsRepository;
    }
}
