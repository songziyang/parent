package com.ydzb.product.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.product.entity.Stable;

import java.math.BigDecimal;
import java.util.List;


/**
 * 稳进宝服务接口
 *
 */
public interface IStableService extends IBaseService<Stable, Long> {

	/**
	 * 删除某个稳进宝
	 * @param id
	 * @throws Exception
	 */
	public void deleteStable(Long id) throws Exception;
	
	/**
	 * 保存稳进宝
	 * @param stable 稳进宝实体
	 * @param startDate 申购开始日期
	 * @param endDate 申购结束日期
	 * @return
	 * @throws Exception
	 */
	public String saveStable(Stable stable, String startDate, String endDate) throws Exception;
	
	/**
	 * 查询所有稳进宝
	 * @return
	 */
	public List<Stable> findAllStable();
	
	/**
	 * 根据用户ID 查询用户稳进宝投资总额
	 * 
	 * @param userId
	 * @return
	 */
	public BigDecimal findSumCopiesFromStableDealByUserId(Long userId);
}