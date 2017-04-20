package com.baofoo.sdk.demo;

import com.baofoo.sdk.demo.base.TransContent;
import com.baofoo.sdk.demo.base.request.TransReqBF0040001;
import com.baofoo.sdk.demo.base.response.TransRespBF0040001;
import com.baofoo.sdk.domain.RequestParams;
import com.baofoo.sdk.http.SimpleHttpResponse;
import com.baofoo.sdk.rsa.RsaCodingUtil;
import com.baofoo.sdk.util.BaofooClient;
import com.baofoo.sdk.util.TransConstant;
import org.apache.commons.codec.binary.Base64;

import java.util.ArrayList;
import java.util.List;

public class BF0040001SDKDemo {

    /**
     * @param args
     * @throws Exception
     */
    @SuppressWarnings("static-access")
    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub

        String dataType = TransConstant.data_type_xml; // 数据类型 xml/json

        TransContent<TransReqBF0040001> transContent = new TransContent<TransReqBF0040001>(dataType);

        List<TransReqBF0040001> trans_reqDatas = new ArrayList<TransReqBF0040001>();

        TransReqBF0040001 transReqData = new TransReqBF0040001();
        transReqData.setTrans_no("9ABCDEFG17");
        transReqData.setTrans_money("1");
        transReqData.setTo_acc_name("测试账号");
        transReqData.setTo_acc_no("666666666");
        transReqData.setTo_bank_name("中国工商银行");
        transReqData.setTo_pro_name("上海市");
        transReqData.setTo_city_name("上海市");
        transReqData.setTo_acc_dept("支行");
        transReqData.setTrans_summary("备注1");
        trans_reqDatas.add(transReqData);
        transContent.setTrans_reqDatas(trans_reqDatas);

        String bean2XmlString = transContent.obj2Str(transContent);
        System.out.println("报文：" + bean2XmlString);

        String origData = bean2XmlString;
        origData = new String(new Base64().encodeBase64(origData.getBytes()));
        String encryptData = RsaCodingUtil.encryptByPriPfxFile(origData, TransConstant.keyStorePath, TransConstant.keyStorePassword);

        System.out.println("----------->【私钥加密-结果】" + encryptData);

        // 发送请求
        String requestUrl = "http://paytest.baofoo.com/baofoo-fopay/pay/BF0040001.do";
        String memberId = "100000178"; // 商户号
        String terminalId = "100000859"; // 终端号

        RequestParams params = new RequestParams();
        params.setMemberId(Integer.parseInt(memberId));
        params.setTerminalId(Integer.parseInt(terminalId));
        params.setDataType(dataType);
        params.setDataContent(encryptData);// 加密后数据
        params.setVersion("4.0.0");
        params.setRequestUrl(requestUrl);
        SimpleHttpResponse response = BaofooClient.doRequest(params);

        System.out.println("宝付请求返回结果：" + response.getEntityString());

        TransContent<TransRespBF0040001> str2Obj = new TransContent<TransRespBF0040001>( dataType);

        String reslut = response.getEntityString();
        reslut = RsaCodingUtil.decryptByPubCerFile(reslut, TransConstant.pub_key);
        reslut = new String(new Base64().decode(reslut));
        System.out.println(reslut);
        if (reslut.contains("trans_content")) {
            // 我报文错误处理
            str2Obj = (TransContent<TransRespBF0040001>) str2Obj.str2Obj(reslut, TransRespBF0040001.class);
            // 业务逻辑判断
        } else {
            reslut = RsaCodingUtil.decryptByPubCerFile(reslut, TransConstant.pub_key);
            str2Obj = (TransContent<TransRespBF0040001>) str2Obj.str2Obj( reslut, TransRespBF0040001.class);
            // 业务逻辑判断
        }
    }

}
