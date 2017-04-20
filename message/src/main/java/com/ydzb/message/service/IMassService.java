package com.ydzb.message.service;


import com.ydzb.core.service.IBaseService;
import com.ydzb.sms.entity.Mass;

/**
 * 群发短信模板服务接口
 *
 */
public interface IMassService extends IBaseService<Mass, Long> {

	/**
	 * 保存模板
	 * @param mass 模板实体
	 * @return
	 * @throws Exception
	 */
	public String saveMass(Mass mass) throws Exception;
	
	/**
	 * 保存模板发送短信状态
	 * @param mass 模板实体
	 * @param result 发送短信状态 
	 * @return
	 * @throws Exception
	 */
	public void saveMass(Mass mass, String result) throws Exception;
}