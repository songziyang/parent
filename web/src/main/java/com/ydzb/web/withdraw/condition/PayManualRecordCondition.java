package com.ydzb.web.withdraw.condition;

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.filter.SearchFilter;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;
import com.ydzb.core.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 代付手动打款查询设置
 */
public class PayManualRecordCondition {

    private List<SearchFilter> filters = Lists.newArrayList();

    @Expose
    private Byte status;
    @Expose
    private String accountType;
    @Expose
    private String accountName;
    @Expose
    private String accountNo;
    @Expose
    private String startTime;
    @Expose
    private String endTime;

    public List<SearchFilter> getAndFilters() {

        if (status != null) {
            filters.add(SearchFilterHelper.newCondition("status", SearchOperator.eq, status));
        }

        if (StringUtils.isNotEmpty(accountType)) {
            filters.add(SearchFilterHelper.newCondition("accountType", SearchOperator.eq, accountType));
        }

        if (StringUtils.isNotEmpty(accountName)) {
            filters.add(SearchFilterHelper.newCondition("accountName", SearchOperator.like, accountName));
        }

        if (StringUtils.isNotEmpty(accountNo)) {
            filters.add(SearchFilterHelper.newCondition("accountNo", SearchOperator.like, accountNo));
        }

        if (StringUtils.isNotEmpty(startTime)) {
            filters.add(SearchFilterHelper.newCondition("created", SearchOperator.gte, DateUtil.getSystemTimeDay(startTime)));
        }

        if (StringUtils.isNotEmpty(endTime)) {
            filters.add(SearchFilterHelper.newCondition("created", SearchOperator.lt, DateUtil.getSystemTimeDay(DateUtil.addDay(endTime))));
        }

        return filters;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
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
