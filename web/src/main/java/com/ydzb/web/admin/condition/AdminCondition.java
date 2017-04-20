package com.ydzb.web.admin.condition;

import java.util.List;

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.filter.SearchFilter;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;


public class AdminCondition {

	private List<SearchFilter> filters = Lists.newArrayList();

	@Expose
	private String username;
	@Expose
	private String realname;
	@Expose
	private String roleId;

	public List<SearchFilter> getAndFilters() {
		// 过滤后台超级用户
		filters.add(SearchFilterHelper.newCondition("username",
				SearchOperator.ne, "kim"));
		// 过滤不可用用户
		filters.add(SearchFilterHelper.newCondition("status",
				SearchOperator.eq, 1));

		if (username != null && !"".equals(username)) {
			filters.add(SearchFilterHelper.newCondition("username",
					SearchOperator.like, username));
		}

		if (realname != null && !"".equals(realname)) {
			filters.add(SearchFilterHelper.newCondition("realname",
					SearchOperator.like, realname));
		}

		if (roleId != null && !"".equals(roleId)) {
			filters.add(SearchFilterHelper.newCondition("role.id",
					SearchOperator.eq, roleId));
		}

		return filters;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

}
