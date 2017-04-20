package com.ydzb.web.traderecord.condition;

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.filter.SearchFilter;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;
import com.ydzb.core.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FundTransferRecordCondition {

    private List<SearchFilter> filters = Lists.newArrayList();

    @Expose
    private String fromMobile;
    @Expose
    private String toMobile;
    @Expose
    private String fromTime;
    @Expose
    private String toTime;

    public List<SearchFilter> getAndFilters() {

        if (StringUtils.isNotEmpty(fromMobile)) {
            filters.add(SearchFilterHelper.newCondition("fromUser.mobile", SearchOperator.eq, fromMobile));
        }

        if (StringUtils.isNotEmpty(toMobile)) {
            filters.add(SearchFilterHelper.newCondition("toUser.mobile", SearchOperator.eq, toMobile));
        }

        if (StringUtils.isNotEmpty(fromTime)) {
            filters.add(SearchFilterHelper.newCondition("optime", SearchOperator.gte, DateUtil.getSystemTimeDay(fromTime)));
        }

        if (StringUtils.isNotEmpty(toTime)) {
            filters.add(SearchFilterHelper.newCondition("optime", SearchOperator.lt, DateUtil.getSystemTimeDay(DateUtil.addDay(toTime))));
        }

        return filters;
    }

    public Map<String, Object> getSqlFilters() {

        final Map<String, Object> filter = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(fromMobile)) {
            filter.put("fromMobile", fromMobile);
        }

        if (StringUtils.isNotEmpty(toMobile)) {
            filter.put("toMobile", toMobile);
        }

        if (StringUtils.isNotEmpty(fromTime)) {
            filter.put("fromTime", fromTime);
        }

        if (StringUtils.isNotEmpty(toTime)) {
            filter.put("toTime", toTime);
        }

        return filter;
    }

    public String getFromMobile() {
        return fromMobile;
    }

    public void setFromMobile(String fromMobile) {
        this.fromMobile = fromMobile;
    }

    public String getToMobile() {
        return toMobile;
    }

    public void setToMobile(String toMobile) {
        this.toMobile = toMobile;
    }

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }
}