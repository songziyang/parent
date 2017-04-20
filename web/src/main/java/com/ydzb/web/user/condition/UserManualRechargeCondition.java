package com.ydzb.web.user.condition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.filter.SearchFilter;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;


public class UserManualRechargeCondition {

	private List<SearchFilter> filters = Lists.newArrayList();

	@Expose
	private String username;
	@Expose
	private String mobile;

	public List<SearchFilter> getAndFilters() {

		if (!StringUtils.isEmpty(username)) {
			filters.add(SearchFilterHelper.newCondition("user.username",
					SearchOperator.like, username));
		}

		if (!StringUtils.isEmpty(mobile)) {
			filters.add(SearchFilterHelper.newCondition("user.mobile",
					SearchOperator.like, mobile));
		}

		return filters;
	}

	public Map<String, Object> getSqlFilters() {

		Map<String, Object> filter = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(username)) {
			filter.put("username", username);
		}

		if (!StringUtils.isEmpty(mobile)) {
			filter.put("mobile", mobile);
		}

		return filter;
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

}
