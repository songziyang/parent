package com.ydzb.web.product.condition;

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.filter.SearchFilter;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 产品类别查询设置
 * @author sy
 */
public class ProductTypeCondition {

    private List<SearchFilter> filters = Lists.newArrayList();

    @Expose
    private String type;

    /**
     * 拼查询sql
     * @return
     */
    public List<SearchFilter> getAndFilters() {

        if (StringUtils.isNotEmpty(type)) {
            filters.add(SearchFilterHelper.newCondition("type",
                    SearchOperator.like, type));
        }

        return filters;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}