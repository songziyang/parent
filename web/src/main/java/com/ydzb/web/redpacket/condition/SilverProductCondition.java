package com.ydzb.web.redpacket.condition;

import com.google.common.collect.Lists;
import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.filter.SearchFilter;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;

import java.util.List;


public class SilverProductCondition {

    private List<SearchFilter> filters = Lists.newArrayList();

    public List<SearchFilter> getAndFilters() {
        filters.add(SearchFilterHelper.newCondition("status", SearchOperator.eq, 0));
        return filters;
    }

}