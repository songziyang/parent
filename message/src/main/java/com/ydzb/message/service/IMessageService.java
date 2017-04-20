package com.ydzb.message.service;


import com.ydzb.core.service.IBaseService;
import com.ydzb.sms.entity.Message;

public interface IMessageService extends IBaseService<Message, Long> {

	/**
	 * 保存站内信
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public String saveMessage(Message message) throws Exception;


}
