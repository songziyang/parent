package com.ydzb.product.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.product.entity.SelfTradeLog;

import java.math.BigDecimal;


/**
 * 自主交易记录-service接口
 * @author sy
 */
public interface ISelfTradeLogService extends IBaseService<SelfTradeLog, Long> {
	
	/**
	 * 保存
	 * @param tradeLog
	 * @param dTradeDate 交易日期
	 * @return
	 */
	public String saveOne(SelfTradeLog tradeLog, String dTradeDate);
	
	
	/**
	 * 交易失败
	 * 
	 * @param id
	 */
	public void buyFail(Long id);
	
	/**
	 * 交易成功
	 * 
	 * @param fid
	 * @param tradeMoney
	 * @param investDate
	 * @return
	 */
	public String saveOption(Long fid, BigDecimal tradeMoney, String investDate);
	
	/**
	 * 交易还款
	 * @param fid
	 * @param state
	 * @param endDate
	 * @param remark
	 * @param revenue
	 */
	public void saveSales(Long fid, Byte state, String endDate, String remark, BigDecimal revenue);
}
