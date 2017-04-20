package com.ydzb.account.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baofoo.sdk.http.HttpUtil;
import com.baofoo.sdk.util.SecurityUtil;
import com.baofoo.sdk.util.TransConstant;
import com.ydzb.account.entity.WmPlatformBalanceRemind;
import com.ydzb.account.entity.WmPlatformReconciliation;
import com.ydzb.account.repository.WmPlatformReconciliationRepository;
import com.ydzb.account.service.IWmPlatformReconciliationService;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.sms.service.ISmsHandleService;
import com.ydzb.withdraw.entity.ManualBalance;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


@Service
public class WmPlatformReconciliationServiceImpl implements IWmPlatformReconciliationService {

    private static final double F5 = 5000000;
    private static final double F4 = 4000000;
    private static final double F3 = 3000000;
    private static final double F2 = 2000000;
    private static final double F1 = 1000000;
    private static final double F0 = 0;

    private static final String MOBILE = "15245111128";

    public Logger logger = Logger.getLogger(WmPlatformReconciliationServiceImpl.class);


    @Autowired
    private WmPlatformReconciliationRepository platformReconciliationRepository;

    @Autowired
    private ISmsHandleService smsHandleService;


    /**
     * 统计平台和第三方账户信息
     *
     * @throws Exception
     */
    @Override
    public void accountPlatformReconciliation() throws Exception {
        ManualBalance manualBalance = doManualBalance();
        if (manualBalance != null) {
            WmPlatformReconciliation wmPlatformReconciliation = new WmPlatformReconciliation();
            wmPlatformReconciliation.setCreated(DateUtil.getSystemTimeSeconds());
            BigDecimal fund = platformReconciliationRepository.findPlatformFund();
            wmPlatformReconciliation.setPlatformFund(fund == null ? "0" : String.valueOf(fund));
            BigDecimal income = platformReconciliationRepository.findPlatformIncome();
            wmPlatformReconciliation.setPlatformIncome(income == null ? "0" : String.valueOf(income));
            //1:基本账户; 2:未结算账户; 3:冻结账户; 4:保证金账户; 5:资金托管账户;
            wmPlatformReconciliation.setThirdFund(manualBalance.getBalance1());
            wmPlatformReconciliation.setThirdSettlement(manualBalance.getBalance2());
            wmPlatformReconciliation.setThirdFreeze(manualBalance.getBalance3());
            wmPlatformReconciliation.setThirdEnsure(manualBalance.getBalance4());
            platformReconciliationRepository.saveWmPlatformReconciliation(wmPlatformReconciliation);
        }
    }


    /**
     * 递归调用接口 直到有返回值
     *
     * @return
     * @throws Exception
     */
    public ManualBalance doManualBalance() throws Exception {
        ManualBalance manualBalance = sendBalanceRequest();
        if (manualBalance == null) {
            return doManualBalance();
        }
        return manualBalance;
    }


    /**
     * 发送查询余额请求
     *
     * @return
     * @throws Exception
     */
    private ManualBalance sendBalanceRequest() throws Exception {
        Map<String, String> PostParams = new HashMap();
        //	商户号
        PostParams.put("member_id", TransConstant.member_id);
        //	终端号
        PostParams.put("terminal_id", TransConstant.query_terminal_id);
        //	返回报文数据类型xml 或json
        PostParams.put("return_type", TransConstant.data_type_json);
        //	交易码
        PostParams.put("trans_code", "BF0001");
        //版本号
        PostParams.put("version", TransConstant.version);
        //帐户类型--0:全部、1:基本账户、2:未结算账户、3:冻结账户、4:保证金账户、5:资金托管账户；
        PostParams.put("account_type", "0");
        //分隔符
        String MAK = "&";
        String KeyString = TransConstant.KeyString;
        String Md5AddString = "member_id=" + PostParams.get("member_id") + MAK + "terminal_id=" + PostParams.get("terminal_id") + MAK + "return_type=" + PostParams.get("return_type") + MAK + "trans_code=" + PostParams.get("trans_code") + MAK + "version=" + PostParams.get("version") + MAK + "account_type=" + PostParams.get("account_type") + MAK + "key=" + KeyString;
        logger.info("Md5拼接字串:" + Md5AddString);
        String Md5Sing = SecurityUtil.MD5(Md5AddString).toUpperCase();//必须为大写
        PostParams.put("sign", Md5Sing);
        String jsonStr = HttpUtil.RequestForm(TransConstant.Bl_URL, PostParams);
        logger.info("jsonStr:" + jsonStr);
        return jsonToManualBalance(jsonStr);
    }

    /**
     * 解析余额返回json
     *
     * @param jsonStr
     * @return
     */
    private ManualBalance jsonToManualBalance(String jsonStr) {
        ManualBalance manualBalance = new ManualBalance();
        if (jsonStr != null && jsonStr.contains("trans_content")) {
            JSONObject jsonObj = JSON.parseObject(jsonStr);
            String return_code = jsonObj.getJSONObject("trans_content").getJSONObject("trans_head").getString("return_code");
            if ("0000".equals(return_code)) {
                JSONArray trans_reqDatas = jsonObj.getJSONObject("trans_content").getJSONObject("trans_reqDatas").getJSONArray("trans_reqData");
                for (int i = 0; i < trans_reqDatas.size(); i++) {
                    int account_type = trans_reqDatas.getJSONObject(i).getInteger("account_type");
                    String balance = trans_reqDatas.getJSONObject(i).getString("balance");
                    switch (account_type) {
                        case 1:
                            manualBalance.setBalance1(balance);
                            break;
                        case 2:
                            manualBalance.setBalance2(balance);
                            break;
                        case 3:
                            manualBalance.setBalance3(balance);
                            break;
                        case 4:
                            manualBalance.setBalance4(balance);
                            break;
                        case 5:
                            manualBalance.setBalance5(balance);
                            break;

                    }

                }
            }

        }
        return manualBalance;
    }


    /**
     * 余额提醒
     *
     * @throws Exception
     */
    @Override
    public void balanceRemind() throws Exception {

        //查询第三方余额
        ManualBalance manualBalance = doManualBalance();

        if (manualBalance != null) {
            double fund = Double.valueOf(manualBalance.getBalance1());
            //400W-500W
            if (fund <= F5 && fund > F4) {
                //发送提醒短信
                sendRemindMsg(5, manualBalance.getBalance1());
            }

            //300W-400W
            if (fund <= F4 && fund > F3) {
                //发送提醒短信
                sendRemindMsg(4, manualBalance.getBalance1());
            }

            //200W-300W
            if (fund <= F3 && fund > F2) {
                //发送提醒短信
                sendRemindMsg(3, manualBalance.getBalance1());
            }

            //100W-200W
            if (fund <= F2 && fund > F1) {
                //发送提醒短信
                sendRemindMsg(2, manualBalance.getBalance1());
            }

            //100W-200W
            if (fund <= F1 && fund > F0) {
                //发送提醒短信
                sendRemindMsg(1, manualBalance.getBalance1());
            }
        }
    }


    public void sendRemindMsg(Integer type, String thirdFund) {

        //系统当前日期
        Long curDate = DateUtil.getSystemTimeDay(DateUtil.curDate());

        //查询是否已有提醒记录
        WmPlatformBalanceRemind platformBalanceRemind = platformReconciliationRepository.findWmPlatformBalanceRemind(type, curDate);
        if (platformBalanceRemind != null && platformBalanceRemind.getId() != null) {
            return;
        }

        //记录提醒
        saveWmPlatformBalanceRemind(type, thirdFund);

        //发送短信
        StringBuffer sb = new StringBuffer();
        sb.append("pvalue:" + thirdFund);
        smsHandleService.sendUserSms("balance_remind", MOBILE, sb.toString());
    }


    /**
     * 保存
     *
     * @param type
     * @param thirdFund
     */
    public void saveWmPlatformBalanceRemind(Integer type, String thirdFund) {
        WmPlatformBalanceRemind platformBalanceRemind = new WmPlatformBalanceRemind();
        platformBalanceRemind.setType(type);
        platformBalanceRemind.setThirdFund(thirdFund);
        platformBalanceRemind.setOpdate(DateUtil.getSystemTimeDay(DateUtil.curDate()));
        platformBalanceRemind.setOptime(DateUtil.getSystemTimeSeconds());
        platformReconciliationRepository.saveWmPlatformBalanceRemind(platformBalanceRemind);
    }

    public WmPlatformReconciliationRepository getPlatformReconciliationRepository() {
        return platformReconciliationRepository;
    }

    public void setPlatformReconciliationRepository(WmPlatformReconciliationRepository platformReconciliationRepository) {
        this.platformReconciliationRepository = platformReconciliationRepository;
    }

    public ISmsHandleService getSmsHandleService() {
        return smsHandleService;
    }

    public void setSmsHandleService(ISmsHandleService smsHandleService) {
        this.smsHandleService = smsHandleService;
    }
}
