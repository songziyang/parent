package com.ydzb.web.activity.condition;

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.filter.SearchFilter;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RewardRecordCondition {

    private List<SearchFilter> filters = Lists.newArrayList();


    @Expose
    private Byte rewardType;
    @Expose
    private Byte exchageType;
    @Expose
    private String realName;
    @Expose
    private String mobile;

    public List<SearchFilter> getAndFilters() {

        //默认查询1-5等奖
        if (rewardType != null && rewardType != 0) {
            filters.add(SearchFilterHelper.newCondition("rewardType",
                    SearchOperator.eq, rewardType));
        } else {
            filters.add(SearchFilterHelper.newCondition("rewardType",
                    SearchOperator.lte, 5));
        }

        if (exchageType != null && exchageType != -1) {
            filters.add(SearchFilterHelper.newCondition("exchageType",
                    SearchOperator.eq, exchageType));
        }

        if (StringUtils.isNotEmpty(realName)) {
            filters.add(SearchFilterHelper.newCondition("user.realName",
                    SearchOperator.eq, realName));
        }

        if (StringUtils.isNotEmpty(mobile)) {
            filters.add(SearchFilterHelper.newCondition("user.mobile",
                    SearchOperator.eq, mobile));
        }
        return filters;
    }

    public Map<String, Object> getSqlFilter() {

        Map<String, Object> filter = new HashMap<String, Object>();

        filter.put("rewardType", rewardType);

        filter.put("exchageType", exchageType);

        filter.put("realName", realName);

        filter.put("mobile", mobile);
        return filter;
    }

    public Byte getRewardType() {
        return rewardType;
    }

    public void setRewardType(Byte rewardType) {
        this.rewardType = rewardType;
    }

    public Byte getExchageType() {
        return exchageType;
    }

    public void setExchageType(Byte exchageType) {
        this.exchageType = exchageType;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
