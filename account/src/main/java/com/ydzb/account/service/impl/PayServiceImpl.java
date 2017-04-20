package com.ydzb.account.service.impl;

import com.baofoo.sdk.demo.base.TransContent;
import com.baofoo.sdk.demo.base.request.TransReqBF0040002;
import com.baofoo.sdk.demo.base.response.TransRespBF0040002;
import com.baofoo.sdk.domain.RequestParams;
import com.baofoo.sdk.http.SimpleHttpResponse;
import com.baofoo.sdk.rsa.RsaCodingUtil;
import com.baofoo.sdk.util.BaofooClient;
import com.baofoo.sdk.util.TransConstant;
import com.ydzb.account.context.IDRange;
import com.ydzb.account.entity.WmPayManuaStatus;
import com.ydzb.account.entity.WmPayManualRequest;
import com.ydzb.account.repository.PayRepository;
import com.ydzb.account.service.IPayService;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.withdraw.entity.PayManualRecord;
import com.ydzb.withdraw.entity.UserWithDraw;
import com.ydzb.withdraw.service.IPayManualRecordService;
import com.ydzb.withdraw.service.IUserWithDrawService;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Service
public class PayServiceImpl implements IPayService {

    public Logger logger = Logger.getLogger(PayServiceImpl.class);

    @Autowired
    private PayRepository payRepository;
    @Autowired
    private IUserWithDrawService userWithDrawService;
    @Autowired
    private IPayManualRecordService payManualRecordService;


    /**
     * 查询运行状态
     *
     * @return
     * @throws Exception
     */
    @Override
    public WmPayManuaStatus findStatus() {
        return payRepository.findStatus();
    }

    /**
     * 更新运行状态
     *
     * @param payManuaStatus
     * @throws Exception
     */
    @Override
    public void updateWmPayManuaStatus(WmPayManuaStatus payManuaStatus) {
        payRepository.updateWmPayManuaStatus(payManuaStatus);
    }


    /**
     * @return
     */
    @Override
    public IDRange findMaxIdAndMinId() {
        return payRepository.findMaxIdAndMinId();
    }

    /**
     * @param id
     * @throws Exception
     */
    @Override
    public void accountQueryPay(Long id) throws Exception {
        //打款请求记录
        WmPayManualRequest payManualRequest = payRepository.findWmPayManualRequestById(id);
        if (payManualRequest != null) {
            sendPayXml(payManualRequest);
        }
    }


    public void sendPayXml(WmPayManualRequest payManualRequest) throws Exception {

        TransContent<TransReqBF0040002> transContent = new TransContent<>(TransConstant.data_type_xml);
        List<TransReqBF0040002> trans_reqDatas = new ArrayList<TransReqBF0040002>();
        TransReqBF0040002 transReqData = new TransReqBF0040002();
        transReqData.setTrans_batchid(payManualRequest.getTransBatchid());
        transReqData.setTrans_no(payManualRequest.getBussflowno());
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
        params.setRequestUrl(TransConstant.BF0040002);
        SimpleHttpResponse response = BaofooClient.doRequest(params);
        logger.info("----------->【宝付请求返回结果】" + response.getEntityString());

        TransContent<TransRespBF0040002> str2Obj = new TransContent<>(TransConstant.data_type_xml);
        String reslut = response.getEntityString();
        if (reslut != null) {
            reslut = RsaCodingUtil.decryptByPubCerFile(reslut, TransConstant.pub_key);
            reslut = new String(new Base64().decode(reslut));
            logger.info("----------->【reslut】" + reslut);
            if (reslut.contains("trans_content")) {
                str2Obj = (TransContent<TransRespBF0040002>) str2Obj.str2Obj(reslut, TransRespBF0040002.class);
                if ("0000".equals(str2Obj.getTrans_head().getReturn_code()) || "200".equals(str2Obj.getTrans_head().getReturn_code())) {
                    payManualRequest.setRespmsg(str2Obj.getTrans_reqDatas().get(0).getTrans_remark());
                    String tranState = str2Obj.getTrans_reqDatas().get(0).getState();
                    //处理中
                    if ("0".equals(tranState)) {
                        payManualRequest.setTranState("02");
                    }
                    //代付成功
                    if ("1".equals(tranState)) {
                        payManualRequest.setTranState("01");
                    }
                    //代付失败
                    if ("-1".equals(tranState)) {
                        payManualRequest.setTranState("03");
                    }
                    //代付失败
                    if ("2".equals(tranState)) {
                        payManualRequest.setTranState("03");
                    }

                    //代付成功
                    if ("01".equals(payManualRequest.getTranState())) {
                        payManualRequest.setAccState(1);
                        dealWithResultSuc(payManualRequest);
                    }

                    //代付失败
                    if ("03".equals(payManualRequest.getTranState())) {
                        payManualRequest.setAccState(1);
                        dealWithResultFail(payManualRequest);
                    }

                    payRepository.updateWmPayManualRequest(payManualRequest);

                }else if("0401".equals(str2Obj.getTrans_head().getReturn_code())){

                    payManualRequest.setRespmsg("代付订单不存在");
                    payManualRequest.setTranState("03");
                    //代付失败
                    if ("03".equals(payManualRequest.getTranState())) {
                        payManualRequest.setAccState(1);
                        dealWithResultFail(payManualRequest);
                    }
                    payRepository.updateWmPayManualRequest(payManualRequest);

                }
            }
        }


    }


    /**
     * 成功
     *
     * @param payManualRequest
     * @throws Exception
     */
    public void dealWithResultSuc(WmPayManualRequest payManualRequest) throws Exception {

        if (payManualRequest.getType() == 1) {
            dealWithUserWithDrawSuc(payManualRequest);
        }

        if (payManualRequest.getType() == 2) {
            dealWithPayManualRecordSuc(payManualRequest);
        }

    }

    /**
     * 用户提现处理成功
     *
     * @param payManualRequest
     */
    public void dealWithUserWithDrawSuc(WmPayManualRequest payManualRequest) throws Exception {
        UserWithDraw userWithDraw = userWithDrawService.findOne(payManualRequest.getLinkId());
        if (userWithDraw != null) {
            //统计是否分笔处理
            if (payManualRequest.getTranamt().compareTo(userWithDraw.getAdvanceMoney()) == 0) {
                userWithDraw.setStatus(4);
                userWithDraw.setSucAmt(payManualRequest.getTranamt());
                userWithDraw.setTransferTime(DateUtil.getSystemTimeSeconds());
                userWithDrawService.update(userWithDraw);
            } else {
                if (userWithDraw.getSucAmt() == null) userWithDraw.setSucAmt(BigDecimal.ZERO);
                if (payManualRequest.getTranamt().add(userWithDraw.getSucAmt()).compareTo(userWithDraw.getAdvanceMoney()) == 0) {
                    userWithDraw.setStatus(4);
                    userWithDraw.setTransferTime(DateUtil.getSystemTimeSeconds());
                }
                userWithDraw.setSucAmt(userWithDraw.getSucAmt().add(payManualRequest.getTranamt()));
                userWithDrawService.update(userWithDraw);
            }
        }
    }

    /**
     * 手动打款民生处理成功
     *
     * @param payManualRequest
     */
    public void dealWithPayManualRecordSuc(WmPayManualRequest payManualRequest) throws Exception {
        PayManualRecord payManualRecord = payManualRecordService.findOne(payManualRequest.getLinkId());
        if (payManualRecord != null) {
            //统计是否分笔处理
            if (payManualRequest.getTranamt().compareTo(payManualRecord.getTranAmt()) == 0) {
                payManualRecord.setStatus((byte) 4);
                payManualRecord.setSucAmt(payManualRequest.getTranamt());
                payManualRecordService.update(payManualRecord);
            } else {
                if (payManualRecord.getSucAmt() == null) payManualRecord.setSucAmt(BigDecimal.ZERO);
                if (payManualRequest.getTranamt().add(payManualRecord.getSucAmt()).compareTo(payManualRecord.getTranAmt()) == 0) {
                    payManualRecord.setStatus((byte) 4);
                }
                payManualRecord.setSucAmt(payManualRecord.getSucAmt().add(payManualRequest.getTranamt()));
                payManualRecordService.update(payManualRecord);
            }
        }
    }

    /**
     * 失败
     *
     * @param payManualRequest
     * @throws Exception
     */
    public void dealWithResultFail(WmPayManualRequest payManualRequest) throws Exception {
        if (payManualRequest.getType() == 1) {
            dealWithUserWithDrawRecordFail(payManualRequest);
        }

        if (payManualRequest.getType() == 2) {
            dealWithPayManualRecordFail(payManualRequest);
        }

    }

    /**
     * 用户提现处理失败
     *
     * @param payManualRequest
     */
    public void dealWithUserWithDrawRecordFail(WmPayManualRequest payManualRequest) throws Exception {
        UserWithDraw userWithDraw = userWithDrawService.findOne(payManualRequest.getLinkId());
        if (userWithDraw != null) {
            //统计是否分笔处理
            if (payManualRequest.getTranamt().compareTo(userWithDraw.getAdvanceMoney()) == 0) {
                userWithDraw.setTransferTime(DateUtil.getSystemTimeSeconds());
                userWithDraw.setStatus(7);
                userWithDraw.setSucAmt(BigDecimal.ZERO);
                userWithDrawService.update(userWithDraw);
            } else {
                userWithDraw.setTransferTime(DateUtil.getSystemTimeSeconds());
                userWithDraw.setStatus(8);
                if (userWithDraw.getSucAmt() == null) userWithDraw.setSucAmt(BigDecimal.ZERO);
                userWithDrawService.update(userWithDraw);
            }
        }
    }

    /**
     * 手动打款民生处理失败
     *
     * @param payManualRequest
     */
    public void dealWithPayManualRecordFail(WmPayManualRequest payManualRequest) throws Exception {
        PayManualRecord payManualRecord = payManualRecordService.findOne(payManualRequest.getLinkId());
        if (payManualRecord != null) {
            //统计是否分笔处理
            if (payManualRequest.getTranamt().compareTo(payManualRecord.getTranAmt()) == 0) {
                payManualRecord.setStatus((byte) 5);
                payManualRecord.setSucAmt(BigDecimal.ZERO);
                payManualRecordService.update(payManualRecord);
            } else {
                payManualRecord.setStatus((byte) 6);
                if (payManualRecord.getSucAmt() == null) payManualRecord.setSucAmt(BigDecimal.ZERO);
                payManualRecordService.update(payManualRecord);
            }
        }
    }


    /**
     * 查询自动放款记录
     *
     * @return
     */
    @Override
    public List<Object[]> findAutoUserWithDraw() {
        return payRepository.findAutoUserWithDraw();
    }


    /**
     * 查询待放款记录
     *
     * @return
     */
    @Override
    public List<Object> findUserWithDrawStatus() {
        return payRepository.findUserWithDrawStatus();
    }

    public PayRepository getPayRepository() {
        return payRepository;
    }

    public void setPayRepository(PayRepository payRepository) {
        this.payRepository = payRepository;
    }

    public IUserWithDrawService getUserWithDrawService() {
        return userWithDrawService;
    }

    public void setUserWithDrawService(IUserWithDrawService userWithDrawService) {
        this.userWithDrawService = userWithDrawService;
    }

    public IPayManualRecordService getPayManualRecordService() {
        return payManualRecordService;
    }

    public void setPayManualRecordService(IPayManualRecordService payManualRecordService) {
        this.payManualRecordService = payManualRecordService;
    }
}
