package com.ydzb.withdraw.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baofoo.sdk.demo.base.TransContent;
import com.baofoo.sdk.demo.base.request.TransReqBF0040001;
import com.baofoo.sdk.demo.base.response.TransRespBF0040001;
import com.baofoo.sdk.domain.RequestParams;
import com.baofoo.sdk.http.HttpUtil;
import com.baofoo.sdk.http.SimpleHttpResponse;
import com.baofoo.sdk.rsa.RsaCodingUtil;
import com.baofoo.sdk.util.BaofooClient;
import com.baofoo.sdk.util.SecurityUtil;
import com.baofoo.sdk.util.TransConstant;
import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.withdraw.entity.ManualBalance;
import com.ydzb.withdraw.entity.PayManualRequest;
import com.ydzb.withdraw.repository.IPayManualBalanceRequestRepository;
import com.ydzb.withdraw.repository.IUserWithDrawRepository;
import com.ydzb.withdraw.service.IPayManualRequestService;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class PayManualRequestServiceImpl extends BaseServiceImpl<PayManualRequest, Long> implements IPayManualRequestService {

    public Logger logger = Logger.getLogger(PayManualRequestServiceImpl.class);

    @Autowired
    private IPayManualBalanceRequestRepository payManualBalanceRequestRepository;

    @Autowired
    private IUserWithDrawRepository userWithDrawRepository;


//    /**
//     * @param bankName
//     * @param accountNo
//     * @param accountName
//     * @param province
//     * @param city
//     * @param openName
//     * @param fund
//     * @return
//     */
//    public static BodyRequest doBodyRequest(String bankName, String accountNo, String accountName, String province, String city, String openName, Double fund) {
//        BodyRequest bodyRequest = new BodyRequest();
//        bodyRequest.setBankName(bankName);          //中国工商银行
//        bodyRequest.setAccountNo(accountNo);        //6222023500028543251
//        bodyRequest.setAccountName(accountName);    //刘超
//        bodyRequest.setOpenProvince(province);      //黑龙江
//        bodyRequest.setOpenCity(city);              //哈尔滨
//        bodyRequest.setOpenName(openName);          //铁路局支行
//        BigDecimal bb = new BigDecimal(fund);
//        bodyRequest.setTranAmt(bb.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
//        bodyRequest.setAccountType("00");
//        bodyRequest.setCurType("CNY");
//        bodyRequest.setBsnType("09400");
//        return bodyRequest;
//    }


    public String doBussflowno() {
        DateFormat dtf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Date date = new Date();
        Random r = new Random();
        int radom = r.nextInt(899) + 100;
        String bussflowno = TransConstant.member_id + dtf.format(date) + radom;
        PayManualRequest oldManualRequest = findPayManualRequestByBussflowno(bussflowno);
        if (oldManualRequest != null) {
            return doBussflowno();
        }
        return bussflowno;
    }


    /**
     * 代付请求
     *
     * @param userId      用户ID
     * @param linkId      外链ID
     * @param type        打款类型：1、账户打款2、手动打款
     * @param bankName    银行名称
     * @param accountNo   账号
     * @param accountName 账户名称
     * @param fund        交易金额
     * @param trandate    交易日期
     * @param trantime    交易时间
     * @param bussflowno  交易流水号
     * @throws Exception
     */
    public void savePayManualRequest(Long userId, Long linkId, Integer type, String bankName, String accountNo, String accountName, BigDecimal fund, String trandate, String trantime, String bussflowno, String transCardId, String transMobile) throws Exception {
        PayManualRequest manualRequest = new PayManualRequest();
        manualRequest.setUserId(userId);
        manualRequest.setLinkId(linkId);
        manualRequest.setType(type);
        manualRequest.setBankName(bankName);
        manualRequest.setAccountNo(accountNo);
        manualRequest.setAccountName(accountName);
        manualRequest.setTranamt(fund);
        manualRequest.setTrandate(trandate);
        manualRequest.setTrantime(trantime);
        manualRequest.setBussflowno(bussflowno);
        manualRequest.setCreated(DateUtil.getSystemTimeSeconds());
        manualRequest.setAccState(0);
        manualRequest.setRespcode("C000000000");
        manualRequest.setTranState("00");
        manualRequest.setTransCardId(transCardId);
        manualRequest.setTransMobile(transMobile);
        payManualBalanceRequestRepository.save(manualRequest);

    }

    /**
     * 代付处理
     *
     * @param userId
     * @param linkId
     * @param bankName
     * @param accountNo
     * @param accountName
     * @param fund
     * @return
     * @throws Exception
     */
    @Override
    public void querysendPayXml(Long userId, Long linkId, Integer type, String bankName, String accountNo, String accountName, BigDecimal fund, String transCardId, String transMobile) {

        try {

            DateFormat df = new SimpleDateFormat("yyyyMMdd");
            DateFormat tf = new SimpleDateFormat("HHmmss");
            Date date = new Date();
            String trandate = df.format(date);
            String trantime = tf.format(date);
            String bussflowno = doBussflowno();
            DecimalFormat decimalFormat = new DecimalFormat("#.00");
            //保存请求信息
            savePayManualRequest(userId, linkId, type, bankName, accountNo, accountName, new BigDecimal(decimalFormat.format(fund)), trandate, trantime, bussflowno, transCardId, transMobile);

            TransContent<TransReqBF0040001> transContent = new TransContent(TransConstant.data_type_xml);

            //请求信息
            List<TransReqBF0040001> trans_reqDatas = new ArrayList<TransReqBF0040001>();
            TransReqBF0040001 transReqData = new TransReqBF0040001();
            transReqData.setTrans_no(bussflowno);
            transReqData.setTrans_money(decimalFormat.format(fund));
            transReqData.setTo_acc_name(accountName);
            transReqData.setTo_acc_no(accountNo);
            transReqData.setTo_bank_name(bankName);
            transReqData.setTo_pro_name("");
            transReqData.setTo_city_name("");
            transReqData.setTo_acc_dept("");
            transReqData.setTrans_summary("");
            transReqData.setTrans_card_id(transCardId);
            transReqData.setTrans_mobile(transMobile);
            trans_reqDatas.add(transReqData);
            transContent.setTrans_reqDatas(trans_reqDatas);
            String bean2XmlString = transContent.obj2Str(transContent);
            logger.info("----------->【报文】" + bean2XmlString);
            String origData = bean2XmlString;
            origData = new String(new Base64().encodeBase64(origData.getBytes()));
            String encryptData = RsaCodingUtil.encryptByPriPfxFile(origData, TransConstant.keyStorePath, TransConstant.keyStorePassword);
            logger.info("----------->【私钥加密-结果】" + encryptData);

            RequestParams params = new RequestParams();
            params.setMemberId(Integer.parseInt(TransConstant.member_id));
            params.setTerminalId(Integer.parseInt(TransConstant.terminal_id));
            params.setDataType(TransConstant.data_type_xml);
            params.setDataContent(encryptData);// 加密后数据
            params.setVersion(TransConstant.version);
            params.setRequestUrl(TransConstant.BF0040001);
            SimpleHttpResponse response = BaofooClient.doRequest(params);
            logger.info("----------->【宝付请求返回结果】" + response.getEntityString());
            TransContent<TransRespBF0040001> str2Obj = new TransContent<>(TransConstant.data_type_xml);
            String reslut = response.getEntityString();
            if (reslut != null) {
                reslut = RsaCodingUtil.decryptByPubCerFile(reslut, TransConstant.pub_key);
                reslut = new String(new Base64().decode(reslut));
                logger.info("----------->【reslut】" + reslut);
                if (reslut.contains("trans_content")) {
                    str2Obj = (TransContent<TransRespBF0040001>) str2Obj.str2Obj(reslut, TransRespBF0040001.class);
                    if ("0000".equals(str2Obj.getTrans_head().getReturn_code()) || "200".equals(str2Obj.getTrans_head().getReturn_code())) {
                        PayManualRequest payManualRequest = findPayManualRequestByBussflowno(str2Obj.getTrans_reqDatas().get(0).getTrans_no());
                        if (payManualRequest != null) {
                            payManualRequest.setTransOrderid(str2Obj.getTrans_reqDatas().get(0).getTrans_orderid());
                            payManualRequest.setTransBatchid(str2Obj.getTrans_reqDatas().get(0).getTrans_batchid());
                            update(payManualRequest);
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 平台余额查询
     *
     * @return
     * @throws Exception
     */
    @Override
    public ManualBalance queryAccountBalance() throws Exception {
        return sendBalanceRequest();
    }

    /**
     * 发送查询余额请求
     *
     * @return
     * @throws Exception
     */
    public ManualBalance sendBalanceRequest() throws Exception {
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
        //String jsonStr = "{\"trans_content\":{ \"trans_head\":{\"return_code\":\"0000\", \"return_msg\":\"请求交易成功\" },\"trans_reqDatas\":{ \"trans_reqData\":[{\"account_type\":1,\"currency\":\"CNY\",\"balance\":900018.07}, {\"account_type\":2,\"currency\":\"CNY\",\"balance\":49290.77}, {\"account_type\":3,\"currency\":\"CNY\",\"balance\":682.72}, {\"account_type\":4,\"currency\":\"CNY\",\"balance\":\"20000.00\"}, {\"account_type\":5,\"currency\":\"CNY\",\"balance\":\"0.00\"}] }} }";
        logger.info("jsonStr:" + jsonStr);
        return jsonToManualBalance(jsonStr);
    }

    /**
     * 解析余额返回json
     *
     * @param jsonStr
     * @return
     */
    public ManualBalance jsonToManualBalance(String jsonStr) {
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

    @Override
    public PayManualRequest findPayManualRequestByBussflowno(String bussflowno) {
        return payManualBalanceRequestRepository.findPayManualRequestByBussflowno(bussflowno);
    }

    public IPayManualBalanceRequestRepository getPayManualBalanceRequestRepository() {
        return payManualBalanceRequestRepository;
    }

    public void setPayManualBalanceRequestRepository(IPayManualBalanceRequestRepository payManualBalanceRequestRepository) {
        this.payManualBalanceRequestRepository = payManualBalanceRequestRepository;
    }

    public IUserWithDrawRepository getUserWithDrawRepository() {
        return userWithDrawRepository;
    }

    public void setUserWithDrawRepository(IUserWithDrawRepository userWithDrawRepository) {
        this.userWithDrawRepository = userWithDrawRepository;
    }
}
