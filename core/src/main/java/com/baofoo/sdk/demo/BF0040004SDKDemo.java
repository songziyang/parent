package com.baofoo.sdk.demo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;

import com.baofoo.sdk.demo.base.TransContent;
import com.baofoo.sdk.demo.base.TransHead;
import com.baofoo.sdk.demo.base.request.TransReqBF0040004;
import com.baofoo.sdk.demo.base.response.TransRespBF0040004;
import com.baofoo.sdk.domain.RequestParams;
import com.baofoo.sdk.http.SimpleHttpResponse;
import com.baofoo.sdk.rsa.RsaCodingUtil;
import com.baofoo.sdk.util.BaofooClient;
import com.baofoo.sdk.util.TransConstant;

public class BF0040004SDKDemo {

	/**
	 * @param args
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String dataType = TransConstant.data_type_xml; // 数据类型 xml/json
		TransContent<TransReqBF0040004> transContent = new TransContent<TransReqBF0040004>(dataType);

		List<TransReqBF0040004> trans_reqDatas = new ArrayList<TransReqBF0040004>();

		TransHead trans_head = new TransHead();
		trans_head.setTrans_count("2");
		trans_head.setTrans_totalMoney("101");
		transContent.setTrans_head(trans_head);

		TransReqBF0040004 transReqData = new TransReqBF0040004();
		transReqData.setTrans_no("3ABCDEFG17");
		transReqData.setTrans_money("1");
		transReqData.setTo_acc_name("测试账号");
		transReqData.setTo_acc_no("666666666");
		transReqData.setTo_bank_name("中国工商银行");
		transReqData.setTo_pro_name("上海市");
		transReqData.setTo_city_name("上海市");
		transReqData.setTo_acc_dept("支行");
		trans_reqDatas.add(transReqData);

		TransReqBF0040004 transReqData2 = new TransReqBF0040004();
		transReqData2.setTrans_no("3ABCDEGF27");
		transReqData2.setTrans_money("100");
		transReqData2.setTo_acc_name("测试账号");
		transReqData2.setTo_acc_no("666666666");
		transReqData2.setTo_bank_name("中国工商银行");
		transReqData2.setTo_pro_name("上海市");
		transReqData2.setTo_city_name("上海市");
		transReqData2.setTo_acc_dept("支行");
		trans_reqDatas.add(transReqData2);

		transContent.setTrans_reqDatas(trans_reqDatas);

		String bean2XmlString = transContent.obj2Str(transContent);
		System.out.println("报文：" + bean2XmlString);

		String keyStorePath = "d:\\m_pri.pfx";
		String keyStorePassword = "123456";
		String pub_key = "d:\\baofoo_pub.cer";
		String origData = bean2XmlString;
		origData =  new String(new Base64().encodeBase64(origData.getBytes()));//Base64.encode(origData);
		String encryptData = RsaCodingUtil.encryptByPriPfxFile(origData,
				keyStorePath, keyStorePassword);

		System.out.println("----------->【私钥加密-结果】" + encryptData);

		// 发送请求
		String requestUrl = "http://10.0.20.171:28019/baofoo-fopay/pay/BF0040004.do";
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

		TransContent<TransRespBF0040004> str2Obj = new TransContent<TransRespBF0040004>(dataType);

		String reslut = response.getEntityString();

		if (reslut.contains("trans_content")) {
			// 我报文错误处理
			str2Obj = (TransContent<TransRespBF0040004>) str2Obj
					.str2Obj(reslut,TransRespBF0040004.class);
			//业务逻辑判断
		} else {
			reslut = RsaCodingUtil.decryptByPubCerFile(reslut, pub_key);

			str2Obj = (TransContent<TransRespBF0040004>) str2Obj
					.str2Obj(reslut,TransRespBF0040004.class);
			//业务逻辑判断
		}
		System.out.println(str2Obj);
	}

}
