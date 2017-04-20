package com.ydzb.web.product.condition;

import com.google.gson.annotations.Expose;
import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.filter.SearchFilter;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;
import com.ydzb.core.utils.DateUtil;
import jersey.repackaged.com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 平台交易统计查询设置
 */
public class PlatformTradingCondition {

	private List<SearchFilter> filters = Lists.newArrayList();

	@Expose
	private Byte type;
	@Expose
	private String startDate;
	@Expose
	private String endDate;

	public List<SearchFilter> getAndFilters() {

		if (type != null && type > 0) {
			filters.add(SearchFilterHelper.newCondition("type",
					SearchOperator.eq, type));
		}

		if (StringUtils.isNotEmpty(startDate)) {
			filters.add(SearchFilterHelper.newCondition("operationTime",
					SearchOperator.gte, DateUtil.getSystemTimeDay(startDate)));
		}

		if (StringUtils.isNotEmpty(endDate)) {
			filters.add(SearchFilterHelper.newCondition("operationTime",
					SearchOperator.lt, DateUtil.getSystemTimeDay(DateUtil.addDay(endDate))));
		}
		return filters;
	}

	public Map<String, Object> getSqlFilters() {

		Map<String, Object> filter = new HashMap<String, Object>();
		if (type != null && type > 0) {
			filter.put("type", type);
		}

		if (StringUtils.isNotEmpty(startDate)) {
			filter.put("startDate", DateUtil.getSystemTimeDay(startDate));
		}

		if (StringUtils.isNotEmpty(endDate)) {
			filter.put("endDate", DateUtil.getSystemTimeDay(DateUtil.addDay(endDate)));
		}
		return filter;
	}

	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}