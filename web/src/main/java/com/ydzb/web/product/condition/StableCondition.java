package com.ydzb.web.product.condition;

import java.util.List;

import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.filter.SearchFilter;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;
import jersey.repackaged.com.google.common.collect.Lists;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.annotations.Expose;

/**
 * 稳进宝分页配置
 *
 */
public class StableCondition {

	
	private List<SearchFilter> filters = Lists.newArrayList();
	
	@Expose
	private String name;	//项目名称
	
	/**
	 * 获得查询语句过滤条件
	 * @return
	 */
	public List<SearchFilter> getAndFilters() {
		//根据项目名称过滤
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
