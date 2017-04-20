package com.ydzb.web.product.condition;

import java.util.List;

import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.filter.SearchFilter;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.product.entity.SelfTradeLog;
import jersey.repackaged.com.google.common.collect.Lists;

import com.alibaba.druid.util.StringUtils;
import com.google.gson.annotations.Expose;

/**
 * 自主交易记录-查询设置
 */
public class SelfTradeLogCondition {

	private List<SearchFilter> filters = Lists.newArrayList();
	
	@Expose
	private String username;	//用户名
	@Expose
	private String mobile;		//手机号
	@Expose
	private Byte state = SelfTradeLog.STATE_TO_BE_CONFIRM;	//状态  默认为待确认
	
	/**
	 * 获得查询语句过滤条件
	 * @return
	 */
	public List<SearchFilter> getAndFilters() {
		
		if (!StringUtils.isEmpty(username)) {
			filters.add(SearchFilterHelper.newCondition("user.username",
					SearchOperator.like, username));
		}
		
		if (!StringUtils.isEmpty(mobile)) {
			filters.add(SearchFilterHelper.newCondition("user.mobile", 
					SearchOperator.like, mobile));
		}
		
		//如果状态为待确认，则只查询交易日期为当天的数据
		if (state == SelfTradeLog.STATE_TO_BE_CONFIRM) {
			filters.add(SearchFilterHelper.newCondition("tradeTime", 
					SearchOperator.eq, 
					DateUtil.getSystemTimeDay(DateUtil.getCurrentDate())));
		}
		
		filters.add(SearchFilterHelper.newCondition("state", 
					SearchOperator.eq, state));
		
		return filters;
	}
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public Byte getState() {
		return state;
	}
	
	public void setState(Byte state) {
		this.state = state;
	}
}
