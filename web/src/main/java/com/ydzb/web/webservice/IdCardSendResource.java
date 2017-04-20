package com.ydzb.web.webservice;

import java.net.URLDecoder;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ydzb.sms.service.ISmsHandleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;


@Path("idCard")
@Scope("prototype")
@Produces(value = MediaType.APPLICATION_JSON)
public class IdCardSendResource {
	
	@Autowired
	private ISmsHandleService smsHandleService;

	/**
	 * 身份验证发送
	 * @param userid 用户ID
	 * @param realName 真实姓名
	 * @param idCard 身份证号
	 * @return ok:验证通过,error:验证失败
	 */
	@GET
	@Path("sendIdCard/{userid}/{realName}/{idCard}")
	public String sendIdCard(@PathParam("userid") Long userid,@PathParam("realName") String realName,@PathParam("idCard") String idCard) {
		URLDecoder.decode(realName);
		return smsHandleService.idAuthen(userid, realName, idCard);
	}

	public ISmsHandleService getSmsHandleService() {
		return smsHandleService;
	}

	public void setSmsHandleService(ISmsHandleService smsHandleService) {
		this.smsHandleService = smsHandleService;
	}

}
