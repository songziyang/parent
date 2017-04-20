package com.ydzb.web.product.condition;

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.filter.SearchFilter;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 产品信息查询设置
 * @author sy
 */
public class StructureCondition {

    private List<SearchFilter> filters = Lists.newArrayList();

    @Expose
    private String name;

    public List<SearchFilter> getAndFilters() {

        if (StringUtils.isNotEmpty(name)) {
            filters.add(SearchFilterHelper.newCondition("name",  SearchOperator.like, name));
        }
        return filters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}