package com.ydzb.web.platform.condition;

import com.google.common.collect.Lists;
import com.ydzb.core.entity.search.filter.SearchFilter;

import java.util.List;

/**
 * Created by sy on 2016/9/20.
 */
public class PlatformUserCondition {

    private List<SearchFilter> filters = Lists.newArrayList();

    public List<SearchFilter> getAndFilters() {


        return filters;
    }
}
