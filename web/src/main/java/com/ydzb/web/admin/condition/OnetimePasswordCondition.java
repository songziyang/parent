package com.ydzb.web.admin.condition;

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.filter.SearchFilter;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 数字令牌查询设置
 */
public class OnetimePasswordCondition {

    private List<SearchFilter> filters = Lists.newArrayList();

    @Expose
    private Long otpNumber;

    @Expose
    private String authkey;

    @Expose
    private String username;

    @Expose
    private Byte status;

    public List<SearchFilter> getAndFilters() {

        if (otpNumber != null) {
            filters.add(SearchFilterHelper.newCondition("otpNumber",
                    SearchOperator.eq, otpNumber));
        }

        if (StringUtils.isNotEmpty(authkey)) {
            filters.add(SearchFilterHelper.newCondition("authkey",
                    SearchOperator.eq, authkey));
        }

        if (StringUtils.isNotEmpty(username)) {
            filters.add(SearchFilterHelper.newCondition("admin.username",
                    SearchOperator.eq, username));
        }
        return filters;
    }

    public Long getOtpNumber() {
        return otpNumber;
    }

    public void setOtpNumber(Long otpNumber) {
        this.otpNumber = otpNumber;
    }

    public String getAuthkey() {
        return authkey;
    }

    public void setAuthkey(String authkey) {
        this.authkey = authkey;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}