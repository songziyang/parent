package com.ydzb.web.product.condition;

import java.util.List;

import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.filter.SearchFilter;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.product.entity.ProductType;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;

/**
 * 机构查询条件设置
 */
public class InstitutionCondition {

	private List<SearchFilter> filters = Lists.newArrayList();

	private static final byte STATUS_NOTSETTLED  = 0; 
	private static final byte STATUS_SETTLED  = 1; 

	@Expose
	private String name;	//产品名称
	@Expose
	private long[] type = ProductType.MAILUO_PRODUCTS;
	@Expose
	private String startTime;	//起始时间
	@Expose
	private String endTime;	//结束时间
	@Expose 
	private String closingStartTime;	//到期起始时间
	@Expose
	private String closingEndTime;	//到期结束时间
	@Expose
	private Byte status;	//到期状态
	
	public List<SearchFilter> getAndFilters() {

		filters.add(SearchFilterHelper.newCondition("productInfo.type.id",
				SearchOperator.in, type));
		
		if (!StringUtils.isEmpty(name)) {
			filters.add(SearchFilterHelper.newCondition("productInfo.name",
					SearchOperator.like, name));
		}
		
		if (!StringUtils.isEmpty(startTime)) {
			filters.add(SearchFilterHelper.newCondition("buyTime",
					SearchOperator.gte, DateUtil.getSystemTimeDay(startTime)));
		}
		
		if (!StringUtils.isEmpty(endTime)) {
			filters.add(SearchFilterHelper.newCondition("buyTime",
					SearchOperator.lte, DateUtil.getSystemTimeDay(DateUtil.addDay(endTime))));
		}
		
		if (!StringUtils.isEmpty(closingStartTime)) {
			filters.add(SearchFilterHelper.newCondition("expireTime",
					SearchOperator.gte, DateUtil.getSystemTimeDay(closingStartTime)));
		}
		
		if (!StringUtils.isEmpty(closingEndTime)) {
			filters.add(SearchFilterHelper.newCondition("expireTime",
					SearchOperator.lte, DateUtil.getSystemTimeDay(closingEndTime) + 24 * 3600));
		}
		
		if (status != null && (status == STATUS_NOTSETTLED || status == STATUS_SETTLED)) {
			filters.add(SearchFilterHelper.newCondition("status",
					SearchOperator.eq, status));
		}
		
		return filters;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getClosingStartTime() {
		return closingStartTime;
	}

	public void setClosingStartTime(String closingStartTime) {
		this.closingStartTime = closingStartTime;
	}

	public String getClosingEndTime() {
		return closingEndTime;
	}

	public void setClosingEndTime(String closingEndTime) {
		this.closingEndTime = closingEndTime;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}
}