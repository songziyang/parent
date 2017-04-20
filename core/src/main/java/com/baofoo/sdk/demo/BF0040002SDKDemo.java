package com.baofoo.sdk.demo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;

import com.baofoo.sdk.demo.base.TransContent;
import com.baofoo.sdk.demo.base.request.TransReqBF0040002;
import com.baofoo.sdk.demo.base.response.TransRespBF0040002;
import com.baofoo.sdk.domain.RequestParams;
import com.baofoo.sdk.http.SimpleHttpResponse;
import com.baofoo.sdk.rsa.RsaCodingUtil;
import com.baofoo.sdk.util.BaofooClient;
import com.baofoo.sdk.util.TransConstant;

public class BF0040002SDKDemo {

	/**
	 * @param args
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		TransContent<TransReqBF0040002> transContent = new TransContent<TransReqBF0040002>(
				TransConstant.data_type_xml);

		List<TransReqBF0040002> trans_reqDatas = new ArrayList<TransReqBF0040002>();

		TransReqBF0040002 transReqData = new TransReqBF0040002();
		transReqData.setTrans_batchid("20218703");
		transReqData.setTrans_no("1ABCDEF34");

		trans_reqDatas.add(transReqData);

		TransReqBF0040002 transReqData2 = new TransReqBF0040002();

		transReqData2.setTrans_batchid("");
		transReqData2.setTrans_no("8ABCDEFG12");
		trans_reqDatas.add(transReqData2);

		transContent.setTrans_reqDatas(trans_reqDatas);

		String bean2XmlString = transContent.obj2Str(transContent);
		System.out.println("报文：" + bean2XmlString);

		String keyStorePath = "d:\\m_pri.pfx";
		String keyStorePassword = "123456";
		String pub_key = "d:\\baofoo_pub.cer";
		String origData = bean2XmlString;
		//origData = Base64.encode(origData);
		origData =  new String(new Base64().encodeBase64(origData.getBytes()));//Base64.encode(origData);
		String encryptData = RsaCodingUtil.encryptByPriPfxFile(origData,
				keyStorePath, keyStorePassword);

		System.out.println("----------->【私钥加密-结果】" + encryptData);

		// 发送请求
		String requestUrl = "http://10.0.20.19:8080/baofoo-fopay/pay/BF0040002.do";
		String memberId = "100000178"; // 商户号
		String terminalId = "100000859"; // 终端号
		String dataType = TransConstant.data_type_xml; // 数据类型 xml/json

		RequestParams params = new RequestParams();
		params.setMemberId(Integer.parseInt(memberId));
		params.setTerminalId(Integer.parseInt(terminalId));
		params.setDataType(dataType);
		params.setDataContent(encryptData);// 加密后数据
		params.setVersion("4.0.0");
		params.setRequestUrl(requestUrl);
		SimpleHttpResponse response = BaofooClient.doRequest(params);

		System.out.println("宝付请求返回结果：" + response.getEntityString());

		TransContent<TransRespBF0040002> str2Obj = new TransContent<TransRespBF0040002>(dataType);

		String reslut = response.getEntityString();
		
		reslut = RsaCodingUtil.decryptByPubCerFile(reslut, pub_key);
		//reslut =  Base64.decode(reslut);
		reslut = new String(new Base64().decode(reslut));
		System.out.println(reslut);
		str2Obj = (TransContent<TransRespBF0040002>) str2Obj
				.str2Obj(reslut,TransRespBF0040002.class);

//		if (reslut.contains("trans_content")) {
//			// 我报文错误处理
//			str2Obj = (TransContent<TransRespBF0040002>) str2Obj
//					.str2Obj(reslut,TransRespBF0040002.class);
//			//业务逻辑判断
//		} else {
//			reslut = RsaCodingUtil.decryptByPubCerFile(reslut, pub_key);
//
//			str2Obj = (TransContent<TransRespBF0040002>) str2Obj
//					.str2Obj(reslut,TransRespBF0040002.class);
//			//业务逻辑判断
//		}
	}

}
