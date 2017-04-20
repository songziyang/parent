package com.ydzb.web.message.condition;

import java.util.List;

import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.filter.SearchFilter;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;

public class MessageCondition {

	private List<SearchFilter> filters = Lists.newArrayList();

	@Expose
	private String username;
	@Expose
	private String title;

	public List<SearchFilter> getAndFilters() {
		
		filters.add(SearchFilterHelper.newCondition("deleted", SearchOperator.eq, 0));
		
		
		if (!StringUtils.isEmpty(username)) {
			filters.add(SearchFilterHelper.newCondition("user.username",SearchOperator.like, username));
		}
		
		if (!StringUtils.isEmpty(title)) {
			filters.add(SearchFilterHelper.newCondition("title",SearchOperator.like, title));
		}

		return filters;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	

}
