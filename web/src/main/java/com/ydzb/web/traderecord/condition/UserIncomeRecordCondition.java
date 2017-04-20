package com.ydzb.web.traderecord.condition;

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

/**
 * 用户收益记录查询设置
 */
public class UserIncomeRecordCondition {

    private List<SearchFilter> filters = Lists.newArrayList();

    @Expose
    private String username;

    @Expose
    private String mobile;

    @Expose
    private Byte ptype;

    @Expose
    private String startDate;

    @Expose
    private String endDate;

    public List<SearchFilter> getAndFilters() {

        filters.add(SearchFilterHelper.newCondition("user.status", SearchOperator.eq, 0));

        if (StringUtils.isNotEmpty(username)) {
            filters.add(SearchFilterHelper.newCondition("user.username", SearchOperator.eq, username));
        }

        if (StringUtils.isNotEmpty(mobile)) {
            filters.add(SearchFilterHelper.newCondition("user.mobile", SearchOperator.eq, mobile));
        }
        if (ptype != null && !"".equals(ptype)) {
            filters.add(SearchFilterHelper.newCondition("ptype", SearchOperator.eq, ptype));
        }
        if (StringUtils.isNotEmpty(startDate)) {
            filters.add(SearchFilterHelper.newCondition("optime", SearchOperator.gte,
                    DateUtil.getSystemTimeDay(startDate)));
        }
        if (StringUtils.isNotEmpty(endDate)) {
            filters.add(SearchFilterHelper.newCondition("optime", SearchOperator.lte,
                    DateUtil.getSystemTimeDay(DateUtil.addDay(endDate))));
        }

        return filters;
    }

    public Map<String, Object> getSqlFilters() {

        Map<String, Object> filter = new HashMap<String, Object>();

        if (StringUtils.isNotEmpty(username)) {
            filter.put("username", username);
        }

        if (StringUtils.isNotEmpty(mobile)) {
            filter.put("mobile", mobile);
        }

        if (ptype != null && !"".equals(ptype)) {
           filter.put("ptype", ptype);
        }

        if (StringUtils.isNotEmpty(startDate)) {
            filter.put("startDate", DateUtil.getSystemTimeDay(startDate));
        }

        if (StringUtils.isNotEmpty(endDate)) {
            filter.put("endDate", DateUtil.getSystemTimeDay(DateUtil.addDay(endDate)));
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

    public Byte getPtype() {
        return ptype;
    }

    public void setPtype(Byte ptype) {
        this.ptype = ptype;
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
}
