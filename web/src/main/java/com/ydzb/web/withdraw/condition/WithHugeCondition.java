package com.ydzb.web.withdraw.condition;

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

public class WithHugeCondition {

    private List<SearchFilter> filters = Lists.newArrayList();

    @Expose
    private String username;
    @Expose
    private String mobile;
    @Expose
    private String startTime; // 开始时间
    @Expose
    private String endTime;// 结束时间

    public List<SearchFilter> getAndFilters() {

        filters.add(SearchFilterHelper.newCondition("status", SearchOperator.eq, 0));

        if (StringUtils.isNotEmpty(mobile)) {
            filters.add(SearchFilterHelper.newCondition("user.mobile", SearchOperator.like, mobile));
        }

        if (StringUtils.isNotEmpty(username)) {
            filters.add(SearchFilterHelper.newCondition("user.username", SearchOperator.like, username));
        }

        if (StringUtils.isNotEmpty(startTime)) {
            filters.add(SearchFilterHelper.newCondition("optime", SearchOperator.gte,
                    DateUtil.getSystemTimeDay(this.startTime)));
        }

        if (StringUtils.isNotEmpty(endTime)) {
            filters.add(SearchFilterHelper.newCondition("optime", SearchOperator.lt,
                    (DateUtil.getSystemTimeDay(this.endTime) + 24 * 3600)));
        }

        return filters;
    }

    public Map<String, Object> getSqlFilters() {
        Map<String, Object> filters = new HashMap<>();
        filters.put("status", 0);
        filters.put("mobile", mobile);
        filters.put("username", username);
        filters.put("startTime", startTime);
        filters.put("endTime", endTime);
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
