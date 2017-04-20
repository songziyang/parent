package com.ydzb.web.activity.condition;

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.filter.SearchFilter;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;
import com.ydzb.packet.entity.SilverExchangeThirty;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SilverExchangeThirtyCondition {

    private List<SearchFilter> filters = Lists.newArrayList();


    @Expose
    private String username;

    @Expose
    private String mobile;

    @Expose
    private String type;

    @Expose
    private Integer status = 1;

    @Expose
    private Integer category = 1;   //种类 1-手动发放 2-自动发放

    public List<SearchFilter> getAndFilters() {

        if (category.equals(1)) {
            filters.add(SearchFilterHelper.or(SearchFilterHelper.newCondition("level", SearchOperator.isNull, null),
                new SearchFilter[]{
                    SearchFilterHelper.and(SearchFilterHelper.newCondition("level", SearchOperator.gte, SilverExchangeThirty.LEVEL_GOODSMIN),
                            new SearchFilter[]{ SearchFilterHelper.newCondition("level", SearchOperator.lte, SilverExchangeThirty.LEVEL_GOODSMAX)}      )

                }
            ));
            filters.add(SearchFilterHelper.newCondition("status", SearchOperator.eq, status));
            if (StringUtils.isNotEmpty(type)) {
                filters.add(SearchFilterHelper.newCondition("type", SearchOperator.eq, type));
            }
        } else if (category.equals(2)) {
            filters.add(SearchFilterHelper.or(SearchFilterHelper.newCondition("level", SearchOperator.lt, SilverExchangeThirty.LEVEL_GOODSMIN),
                    new SearchFilter[]{
                            SearchFilterHelper.newCondition("level", SearchOperator.gt, SilverExchangeThirty.LEVEL_GOODSMAX)
                    }
            ));
        }

        if (!StringUtils.isEmpty(username)) {
            filters.add(SearchFilterHelper.newCondition("user.username", SearchOperator.like, username));
        }

        if (!StringUtils.isEmpty(mobile)) {
            filters.add(SearchFilterHelper.newCondition("user.mobile", SearchOperator.like, mobile));
        }

        return filters;
    }

    public Map<String, Object> getSqlFilters() {

        Map<String, Object> filter = new HashMap<>();

        if (!StringUtils.isEmpty(username)) {
            filter.put("username", username);
        }

        if (!StringUtils.isEmpty(mobile)) {
            filter.put("mobile", mobile);
        }

        if (status != null) {
            filter.put("status", status);
        } else {
            filter.put("status", 1);
        }

        filter.put("type", type);
        filter.put("category", category);
        return filter;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }
}