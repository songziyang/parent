package com.ydzb.web.product.condition;

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.filter.SearchFilter;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;
import com.ydzb.core.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.List;


public class NewStandardCondition {

    private List<SearchFilter> filters = Lists.newArrayList();

    @Expose
    private String createdStart;

    @Expose
    private String createdEnd;


    public List<SearchFilter> getAndFilters() {

        if (!StringUtils.isEmpty(createdStart)) {
            filters.add(SearchFilterHelper.newCondition("created", SearchOperator.gte, DateUtil.getSystemTimeDay(createdStart)));
        }

        if (!StringUtils.isEmpty(createdEnd)) {
            filters.add(SearchFilterHelper.newCondition("created", SearchOperator.lte, DateUtil.getSystemTimeDay(createdEnd) + 24 * 3600));
        }

        return filters;
    }

    public String getCreatedEnd() {
        return createdEnd;
    }

    public void setCreatedEnd(String createdEnd) {
        this.createdEnd = createdEnd;
    }

    public String getCreatedStart() {
        return createdStart;
    }

    public void setCreatedStart(String createdStart) {
        this.createdStart = createdStart;
    }
}