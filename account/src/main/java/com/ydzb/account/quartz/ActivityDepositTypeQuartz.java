package com.ydzb.account.quartz;

import com.ydzb.account.service.IWmRagularUserAccountService;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.core.utils.HttpClientUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 定存类活动
 */
public class ActivityDepositTypeQuartz {

    //签名
    private static final String SIGN = "kekechao";
    Logger logger = Logger.getLogger(ActivityDepositTypeQuartz.class);
    //php接口地址
    @Value("${futo.url}")
    private String url;
    //活动开始时间
    @Value("${futo.starttime}")
    private long startTime;
    //活动结束时间
    @Value("${futo.endtime}")
    private long endTime;
    @Autowired
    private IWmRagularUserAccountService wmRagularUserAccountService;

    public void accountActivity() {
        try {


            //判断当前时间是否在活动期间
            long currentTime = DateUtil.getSystemTimeSeconds();
            if (currentTime < startTime || currentTime > endTime) {
                logger.info("------活动期间外-------");
                return;
            }

            logger.info("------活动期间内-------");

            //获得今日复投定存记录
            List<Object[]> ragularTrades = wmRagularUserAccountService.queryRagularInfoBetweenTime(DateUtil.getSystemTimeDay(DateUtil.getCurrentDate()), DateUtil.getSystemTimeSeconds());
            if (ragularTrades != null) {
                for (Object[] ragularTrade : ragularTrades) {
                    if (ragularTrade != null && ragularTrade.length >= 4) {
                        try {

                            //定存复投信息
                            Long user_id = (Long) ragularTrade[0];                      //用户id
                            BigDecimal buy_copies = (BigDecimal) ragularTrade[1];       //购买金额
                            Integer cylcle_days = (Integer) ragularTrade[2];            //购买金额
                            Long account_id = (Long) ragularTrade[3];                   //定存id

                            logger.info("user_id = " + user_id + " = buy_copies = " + buy_copies + " = cylcle_days = " + cylcle_days + " = account_id = " + account_id);

                            //发送请求
                            Map<String, String> params = new HashMap<>();
                            params.put("user_id", String.valueOf(user_id));
                            params.put("buy_copies", String.valueOf(buy_copies));
                            params.put("cylcle_days", String.valueOf(cylcle_days));
                            params.put("account_id", String.valueOf(account_id));
                            params.put("sign", SIGN);
                            String result = HttpClientUtil.POST(url + "/Activity/futo", params);

                            logger.info("user_id = " + user_id + " = buy_copies = " + buy_copies + " = cylcle_days = " + cylcle_days + " = account_id = " + account_id + "= result = " + result);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
}
