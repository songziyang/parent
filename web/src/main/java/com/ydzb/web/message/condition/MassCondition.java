package com.ydzb.web.message.condition;

import java.util.List;

import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.filter.SearchFilter;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;
import org.apache.commons.lang3.StringUtils;

import jersey.repackaged.com.google.common.collect.Lists;

import com.google.gson.annotations.Expose;

/**
 * 群发短信模板-查询/分页设置
 */
public class MassCondition {

	private List<SearchFilter> filters = Lists.newArrayList();
	
	@Expose
	private String name;	//查询的模板名称
	
	/**
	 * 获得过滤条件
	 * @return
	 */
	public List<SearchFilter> getAndFilters() {
		//根据名称模糊查询
		if (!StringUtils.isEmpty(name)) {
			filters.add(SearchFilterHelper.newCondition("name",SearchOperator.like, name));
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