package com.ydzb.account.service.impl;

import com.ydzb.account.repository.PlatformTradingRemindRepository;
import com.ydzb.account.service.IPlatformTradingRemindService;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.sms.service.ISmsHandleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
public class PlatformTradingRemindServiceImpl implements IPlatformTradingRemindService {

    private static final String MOBILE = "15245111128";

    @Autowired
    private ISmsHandleService smsHandleService;

    @Autowired
    private PlatformTradingRemindRepository platformTradingRemindRepository;


    /**
     * 充值和提现提醒
     *
     * @throws Exception
     */
    @Override
    public void tradingRemind() throws Exception {
        //开始时间
        Long startDate = DateUtil.getSystemTimeDay(DateUtil.curDate());
        //结束时间
        Long endDate = DateUtil.getSystemTimeSeconds(DateUtil.curDate() + " 18:00:00");
        //充值金额
        BigDecimal rechargeFund = platformTradingRemindRepository.findRechargeAllFund(startDate, endDate);
        //提现金额
        BigDecimal withdrawFund = platformTradingRemindRepository.findWithDrawAllFund(startDate, endDate);

        //发送短信
        StringBuffer sb = new StringBuffer();
        sb.append("date:" + DateUtil.curDate());
        sb.append(",");
        sb.append("rvalue:" + rechargeFund);
        sb.append(",");
        sb.append("tvalue:" + withdrawFund);
        smsHandleService.sendUserSms("trading_remind", MOBILE, sb.toString());

    }

    public ISmsHandleService getSmsHandleService() {
        return smsHandleService;
    }

    public void setSmsHandleService(ISmsHandleService smsHandleService) {
        this.smsHandleService = smsHandleService;
    }

    public PlatformTradingRemindRepository getPlatformTradingRemindRepository() {
        return platformTradingRemindRepository;
    }

    public void setPlatformTradingRemindRepository(PlatformTradingRemindRepository platformTradingRemindRepository) {
        this.platformTradingRemindRepository = platformTradingRemindRepository;
    }
}
