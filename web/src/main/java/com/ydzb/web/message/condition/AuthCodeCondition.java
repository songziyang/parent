package com.ydzb.web.message.condition;

import java.util.List;

import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.filter.SearchFilter;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;

public class AuthCodeCondition {

	private List<SearchFilter> filters = Lists.newArrayList();

	@Expose
	private String name;

	public List<SearchFilter> getAndFilters() {

		if (!StringUtils.isEmpty(name)) {
			filters.add(SearchFilterHelper.newCondition("name", SearchOperator.like, name));
		}

		return filters;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
