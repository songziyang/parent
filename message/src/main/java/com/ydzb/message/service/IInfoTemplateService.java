package com.ydzb.message.service;


import com.ydzb.core.service.IBaseService;
import com.ydzb.sms.entity.InfoTemplate;

public interface IInfoTemplateService extends IBaseService<InfoTemplate, Long> {
	/**
	 * 保存短信模板
	 * @param infoTemplate
	 * @return 
	 * @throws Exception
	 */
	public String saveInfoTemplate(InfoTemplate infoTemplate) throws Exception;
	
	
	public void deleteInfoTemplate(Long id) throws Exception;
	
	public void deleteInfoTemplate(Long[] ids) throws Exception;
}

