package com.ydzb.web.user.condition;

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.filter.SearchFilter;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;
import com.ydzb.core.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class OtherPlatformUserCondition {

    private List<SearchFilter> filters = Lists.newArrayList();


    //是否注册
    @Expose
    private String regFlag;
    @Expose
    private String realname;
    @Expose
    private String mobile;
    @Expose
    private String platformId;
    @Expose
    private String startDate;//注册开始日期
    @Expose
    private String endDate;//注册结束日期

    @Expose
    private String importstartDate;//导入开始日期
    @Expose
    private String importendDate;//导入结束日期


    public List<SearchFilter> getAndFilters() {

        if (StringUtils.isNotEmpty(regFlag)) {
            filters.add(SearchFilterHelper.newCondition("regFlag", SearchOperator.eq, regFlag));
        }
        if (StringUtils.isNotEmpty(realname)) {
            filters.add(SearchFilterHelper.newCondition("realname", SearchOperator.eq, realname));
        }
        if (StringUtils.isNotEmpty(mobile)) {
            filters.add(SearchFilterHelper.newCondition("mobile", SearchOperator.eq, mobile));
        }

        if (!StringUtils.isEmpty(startDate)) {
            filters.add(SearchFilterHelper.newCondition("regTime", SearchOperator.gte, DateUtil.getSystemTimeDay(startDate)));
        }

        if (!StringUtils.isEmpty(endDate)) {
            filters.add(SearchFilterHelper.newCondition("regTime", SearchOperator.lt, DateUtil.getSystemTimeDay(endDate) + 24 * 3600));
        }

        if (!StringUtils.isEmpty(startDate)) {
            filters.add(SearchFilterHelper.newCondition("regTime", SearchOperator.gte, DateUtil.getSystemTimeDay(startDate)));
        }

        if (!StringUtils.isEmpty(endDate)) {
            filters.add(SearchFilterHelper.newCondition("regTime", SearchOperator.lt, DateUtil.getSystemTimeDay(endDate) + 24 * 3600));
        }
        if (StringUtils.isNotEmpty(platformId)) {
            filters.add(SearchFilterHelper.newCondition("otherPlatform.id", SearchOperator.eq, platformId));
        }
        return filters;
    }

    public String getRegFlag() {
        return regFlag;
    }

    public void setRegFlag(String regFlag) {
        this.regFlag = regFlag;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
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

    public String getImportstartDate() {
        return importstartDate;
    }

    public void setImportstartDate(String importstartDate) {
        this.importstartDate = importstartDate;
    }

    public String getImportendDate() {
        return importendDate;
    }

    public void setImportendDate(String importendDate) {
        this.importendDate = importendDate;
    }
}
