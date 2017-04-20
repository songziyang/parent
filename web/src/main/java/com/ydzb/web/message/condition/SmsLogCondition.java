package com.ydzb.web.message.condition;

import java.util.List;

import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.filter.SearchFilter;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;

public class SmsLogCondition {

	private List<SearchFilter> filters = Lists.newArrayList();

	@Expose
	private String phone;

	public List<SearchFilter> getAndFilters() {

		if (!StringUtils.isEmpty(phone)) {
			filters.add(SearchFilterHelper.newCondition("phone", SearchOperator.like, phone));
		}

		return filters;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
