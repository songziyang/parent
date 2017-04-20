package com.ydzb.web.redpacket.condition;

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.filter.SearchFilter;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;
import com.ydzb.core.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UserIntegralRecordCondition {

    private List<SearchFilter> filters = Lists.newArrayList();

    @Expose
    private String username;

    @Expose
    private String mobile;

    @Expose
    private Integer type;

    @Expose
    private String startDate;

    @Expose
    private String endDate;

    @Expose
    private Long linkId;
    @Expose
    private Integer linkType;

    public List<SearchFilter> getAndFilters() {

        if (!StringUtils.isEmpty(username)) {
            filters.add(SearchFilterHelper.newCondition("user.username", SearchOperator.like, username));
        }

        if (!StringUtils.isEmpty(mobile)) {
            filters.add(SearchFilterHelper.newCondition("user.mobile", SearchOperator.like, mobile));
        }

        if (type != null) {
            filters.add(SearchFilterHelper.newCondition("type", SearchOperator.eq, type));
        }

        if (StringUtils.isNotEmpty(startDate)) {
            filters.add(SearchFilterHelper.newCondition("created", SearchOperator.gte, DateUtil.getSystemTimeDay(startDate)));
        }

        if (StringUtils.isNotEmpty(endDate)) {
            filters.add(SearchFilterHelper.newCondition("created", SearchOperator.lte, DateUtil.getSystemTimeDay(endDate) + 24 * 3600));
        }

        if (linkId != null) {
            filters.add(SearchFilterHelper.newCondition("linkId", SearchOperator.eq, linkId));
        }

        if (linkType != null) {
            filters.add(SearchFilterHelper.newCondition("linkType", SearchOperator.eq, linkType));
        }

        return filters;
    }

    public Map<String, Object> getSqlFilters() {

        Map<String, Object> filter = new HashMap<String, Object>();

        if (!StringUtils.isEmpty(username)) {
            filter.put("username", username);
        }

        if (!StringUtils.isEmpty(mobile)) {
            filter.put("mobile", mobile);
        }

        if (type != null) {
            filter.put("type", type);
        }

        if (StringUtils.isNotEmpty(startDate)) {
            filter.put("startDate", DateUtil.getSystemTimeDay(startDate));
        }

        if (StringUtils.isNotEmpty(endDate)) {
            filter.put("endDate", DateUtil.getSystemTimeDay(endDate) + 24 * 3600);
        }


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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public Long getLinkId() {
        return linkId;
    }

    public void setLinkId(Long linkId) {
        this.linkId = linkId;
    }

    public Integer getLinkType() {
        return linkType;
    }

    public void setLinkType(Integer linkType) {
        this.linkType = linkType;
    }
}