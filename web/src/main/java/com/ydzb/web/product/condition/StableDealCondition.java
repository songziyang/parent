package com.ydzb.web.product.condition;

import com.google.gson.annotations.Expose;
import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.filter.SearchFilter;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;
import jersey.repackaged.com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 稳进宝我交易记录分页配置
 */
public class StableDealCondition {

    private List<SearchFilter> filters = Lists.newArrayList();

    @Expose
    private Long stableId;        //稳进宝id
    @Expose
    private String username;    //用户名
    @Expose
    private String mobile;        //用户手机号

    /**
     * 获得查询语句过滤条件
     *
     * @return
     */
    public List<SearchFilter> getAndFilters() {

        //根据稳进宝id过滤
        if (stableId != null && !"".equals(stableId)) {
            filters.add(SearchFilterHelper.newCondition("stable.id", SearchOperator.eq, stableId));
        }

        //根据用户名过滤
        if (!StringUtils.isEmpty(username)) {
            filters.add(SearchFilterHelper.newCondition("user.username", SearchOperator.like, username));
        }

        //根据手机号
        if (!StringUtils.isEmpty(mobile)) {
            filters.add(SearchFilterHelper.newCondition("user.mobile", SearchOperator.like, mobile));
        }

        return filters;
    }

    public Long getStableId() {
        return stableId;
    }

    public void setStableId(Long stableId) {
        this.stableId = stableId;
    }

    public List<SearchFilter> getFilters() {
        return filters;
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
}