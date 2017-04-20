package com.ydzb.web.admin.condition;

import java.util.List;

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.filter.SearchFilter;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;


public class RoleCondition {

	private List<SearchFilter> filters = Lists.newArrayList();

	@Expose
	private String name;

	public List<SearchFilter> getAndFilters() {
		if (name != null && !"".equals(name)) {
			filters.add(SearchFilterHelper.newCondition("name",
					SearchOperator.like, name));
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
