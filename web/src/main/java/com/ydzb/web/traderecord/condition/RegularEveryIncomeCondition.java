package com.ydzb.web.traderecord.condition;


import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import com.ydzb.core.entity.search.filter.SearchFilter;

import java.util.List;


public class RegularEveryIncomeCondition {

    private List<SearchFilter> filters = Lists.newArrayList();

    @Expose
    private String startDate;

    @Expose
    private String endDate;

    public List<SearchFilter> getAndFilters() {

        return filters;
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
