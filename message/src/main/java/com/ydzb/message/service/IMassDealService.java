package com.ydzb.message.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.sms.entity.MassDeal;
import jxl.Sheet;



/**
 * 短信群发记录服务接口
 */
public interface IMassDealService extends IBaseService<MassDeal, Long> {

	/**
	 * 保存短信群发记录
	 * @param massId 群发模板id
	 * @param mobile 电话号码
	 * @return
	 * @throws Exception
	 */
	public String saveMassDeal(Long massId, String mobile) throws Exception;

	/**
	 * 读取excel，将数据添加到wm_info_mass_deal表中，存储手机号
	 * @param sheet 
	 * @param column 列名
	 * @param massId 群发模板id
	 * @return
	 */
	public String sheetProcess(Sheet sheet, String column, Long massId) throws Exception;
	
	/**
	 * 根据群发模板id，查询对应群发记录的手机号
	 * @param massId 群发模板id
	 * @return
	 * @throws Exception
	 */
	public String[] findMobile(Long massId) throws Exception;
	
	/**
	 * 根据群发模板idy以及手机号，查询群发记录
	 * @param massId 群发模板id
	 * @param mobile 手机号
	 * @return
	 * @throws Exception
	 */
	public MassDeal findByMobile(Long massId, String mobile) throws Exception;
}