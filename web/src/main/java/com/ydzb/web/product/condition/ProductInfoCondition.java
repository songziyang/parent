package com.ydzb.web.product.condition;

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.filter.SearchFilter;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;
import com.ydzb.product.entity.ProductInfo;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 产品信息查询设置
 * @author sy
 */
public class ProductInfoCondition {

    private List<SearchFilter> filters = Lists.newArrayList();

    private static final Byte ALL_STATUS = -1;

    @Expose
    private String name;
    @Expose
    private Byte status = ProductInfo.STATUS_INUSE;

    public List<SearchFilter> getAndFilters() {

        if (ALL_STATUS.equals(status)) {
            filters.add(SearchFilterHelper.newCondition("status",
                    SearchOperator.in, new byte[]{ProductInfo.STATUS_INUSE, ProductInfo.STATUS_NOTUSE}));
        } else {
            filters.add(SearchFilterHelper.newCondition("status",
                    SearchOperator.eq, status));
        }

        if (StringUtils.isNotEmpty(name)) {
            filters.add(SearchFilterHelper.newCondition("name",
                    SearchOperator.like, name));
        }
        return filters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}