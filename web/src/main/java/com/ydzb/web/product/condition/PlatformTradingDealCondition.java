package com.ydzb.web.product.condition;

import com.google.gson.annotations.Expose;
import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.filter.SearchFilter;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;
import com.ydzb.core.utils.DateUtil;
import jersey.repackaged.com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 平台交易统计详细查询设置
 */
public class PlatformTradingDealCondition {

	private List<SearchFilter> filters = Lists.newArrayList();

	@Expose
	private Long tradingId;

	public List<SearchFilter> getAndFilters() {

		filters.add(SearchFilterHelper.newCondition("platformTrading.id",
				SearchOperator.eq, tradingId));

		return filters;
	}

	public Long getTradingId() {
		return tradingId;
	}

	public void setTradingId(Long tradingId) {
		this.tradingId = tradingId;
	}
}