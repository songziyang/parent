package com.ydzb.web.product.condition;

import java.util.List;

import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.filter.SearchFilter;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;
import com.ydzb.product.entity.SelfRevenue;
import jersey.repackaged.com.google.common.collect.Lists;

import com.google.gson.annotations.Expose;

public class SelfRevenueCondition {

	private List<SearchFilter> filters = Lists.newArrayList();

	@Expose
	private Long fid;
	@Expose
	private Byte status = SelfRevenue.CHECKING;

	public List<SearchFilter> getAndFilters() {

		if (status != null) {
			filters.add(SearchFilterHelper.newCondition("status",
					SearchOperator.eq, status));
		}

		if (fid != null) {
			filters.add(SearchFilterHelper.newCondition("selfTradeLog.id",
					SearchOperator.eq, fid));
		}

		return filters;
	}

	public Long getFid() {
		return fid;
	}

	public void setFid(Long fid) {
		this.fid = fid;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}
}
