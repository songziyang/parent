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
 * 红包记录查询设置
 *
 * @author sy
 */
public class RedPacketRecordCondition {

    private List<SearchFilter> filters = Lists.newArrayList();

    @Expose
    private Byte productType;
    @Expose
    private String username;
    @Expose
    private String mobile;
    @Expose
    private Byte triggerType;
    @Expose
    private Byte redpacketType;
    @Expose
    private Byte status;
    @Expose
    private String startTime;
    @Expose
    private String endTime;
    @Expose
    private String startUseTime;
    @Expose
    private String endUseTime;
    @Expose
    private String startUseFinishTime;
    @Expose
    private String endUseFinishTime;

    public List<SearchFilter> getAndFilters() {

        if (productType != null) {
            filters.add(SearchFilterHelper.newCondition("productType", SearchOperator.eq, productType));
        }

        if (!StringUtils.isEmpty(username)) {
            filters.add(SearchFilterHelper.newCondition("user.username", SearchOperator.eq, username));
        }

        if (!StringUtils.isEmpty(mobile)) {
            filters.add(SearchFilterHelper.newCondition("user.mobile", SearchOperator.eq, mobile));
        }

        if (triggerType != null) {
            filters.add(SearchFilterHelper.newCondition("triggerType", SearchOperator.eq, triggerType));
        }

        if (redpacketType != null) {
            filters.add(SearchFilterHelper.newCondition("redpacketType", SearchOperator.eq, redpacketType));
        }

        if (status != null) {
            filters.add(SearchFilterHelper.newCondition("status", SearchOperator.eq, status));
        }

        if (StringUtils.isNotEmpty(startTime)) {
            filters.add(SearchFilterHelper.newCondition("getTime", SearchOperator.gte, DateUtil.getSystemTimeDay(startTime)));
        }

        if (StringUtils.isNotEmpty(endTime)) {
            filters.add(SearchFilterHelper.newCondition("getTime", SearchOperator.lt, DateUtil.getSystemTimeDay(DateUtil.addDay(endTime))));
        }


        if (StringUtils.isNotEmpty(startUseTime)) {
            filters.add(SearchFilterHelper.newCondition("userUseTime", SearchOperator.gte, DateUtil.getSystemTimeDay(startUseTime)));
        }

        if (StringUtils.isNotEmpty(endUseTime)) {
            filters.add(SearchFilterHelper.newCondition("userUseTime", SearchOperator.lt, DateUtil.getSystemTimeDay(DateUtil.addDay(endUseTime))));
        }


        if (StringUtils.isNotEmpty(startUseFinishTime)) {
            filters.add(SearchFilterHelper.newCondition("useFinishTime", SearchOperator.gte, DateUtil.getSystemTimeDay(startUseFinishTime)));
        }

        if (StringUtils.isNotEmpty(endUseFinishTime)) {
            filters.add(SearchFilterHelper.newCondition("useFinishTime", SearchOperator.lt, DateUtil.getSystemTimeDay(DateUtil.addDay(endUseFinishTime))));
        }


        return filters;
    }

    public Map<String, Object> getSqlFilter() {

        Map<String, Object> filter = new HashMap<String, Object>();

        if (productType != null) {
            filter.put("productType", productType);
        }

        if (!StringUtils.isEmpty(username)) {
            filter.put("username", username);
        }

        if (!StringUtils.isEmpty(mobile)) {
            filter.put("mobile", mobile);
        }

        if (triggerType != null) {
            filter.put("triggerType", triggerType);
        }

        if (redpacketType != null) {
            filter.put("redpacketType", redpacketType);
        }

        if (status != null) {
            filter.put("status", status);
        }

        if (StringUtils.isNotEmpty(startTime)) {
            filter.put("startTime", startTime);
        }

        if (StringUtils.isNotEmpty(endTime)) {
            filter.put("endTime", endTime);
        }

        if (StringUtils.isNotEmpty(startUseTime)) {
            filter.put("startUseTime", startUseTime);
        }

        if (StringUtils.isNotEmpty(endUseTime)) {
            filter.put("endUseTime", endUseTime);
        }

        if (StringUtils.isNotEmpty(startUseFinishTime)) {
            filter.put("startUseFinishTime", startUseFinishTime);
        }

        if (StringUtils.isNotEmpty(endUseFinishTime)) {
            filter.put("endUseFinishTime", endUseFinishTime );
        }

        return filter;
    }

    public Byte getProductType() {
        return productType;
    }

    public void setProductType(Byte productType) {
        this.productType = productType;
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


    public Byte getRedpacketType() {
        return redpacketType;
    }

    public void setRedpacketType(Byte redpacketType) {
        this.redpacketType = redpacketType;
    }

    public Byte getTriggerType() {
        return triggerType;
    }

    public void setTriggerType(Byte triggerType) {
        this.triggerType = triggerType;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
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

    public String getStartUseTime() {
        return startUseTime;
    }

    public void setStartUseTime(String startUseTime) {
        this.startUseTime = startUseTime;
    }

    public String getEndUseTime() {
        return endUseTime;
    }

    public void setEndUseTime(String endUseTime) {
        this.endUseTime = endUseTime;
    }


    public String getStartUseFinishTime() {
        return startUseFinishTime;
    }

    public void setStartUseFinishTime(String startUseFinishTime) {
        this.startUseFinishTime = startUseFinishTime;
    }

    public String getEndUseFinishTime() {
        return endUseFinishTime;
    }

    public void setEndUseFinishTime(String endUseFinishTime) {
        this.endUseFinishTime = endUseFinishTime;
    }
}