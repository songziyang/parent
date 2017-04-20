package com.ydzb.web.user.condition;

import com.google.common.collect.Lists;
import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.filter.SearchFilter;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;

import java.util.List;

public class ChannelManagerCondition {

    private List<SearchFilter> filters = Lists.newArrayList();

    public List<SearchFilter> getAndFilters() {

        filters.add(SearchFilterHelper.newCondition("status", SearchOperator.eq, 1));

        return filters;
    }


}
