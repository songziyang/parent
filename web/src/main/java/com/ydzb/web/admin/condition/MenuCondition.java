package com.ydzb.web.admin.condition;

import java.util.List;

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.filter.SearchFilter;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;

public class MenuCondition {

	private List<SearchFilter> filters = Lists.newArrayList();

	@Expose
	private String name;
	@Expose
	private Long pid;

	public List<SearchFilter> getAndFilters() {

		if (name != null && !"".equals(name)) {
			filters.add(SearchFilterHelper.newCondition("name",
					SearchOperator.like, name));
		}

		if (pid != null && !"".equals(pid)) {
			filters.add(SearchFilterHelper.newCondition("parentMenu.id",
					SearchOperator.eq, pid));
		}
		return filters;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

}
