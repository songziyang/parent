package com.ydzb.web.user.condition;

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


public class UserMoneyCondition {

    private List<SearchFilter> filters = Lists.newArrayList();

    @Expose
    private String username;

    @Expose
    private String mobile;

    //推荐人手机号
    @Expose
    private String referralMobile;

    @Expose
    private Long userId;

    @Expose
    private Integer userType;

    @Expose
    private Integer userLeve;

    @Expose
    private Integer orderNumber;

    @Expose
    private String orderSort;

    @Expose
    private String wechatNumber;

    @Expose
    private String qqNumber;

    @Expose
    private Integer startAllIncome;
    @Expose
    private Integer endAllIncome;
    @Expose
    private Integer startFreezeMoney;
    @Expose
    private Integer endFreezeMoney;

    @Expose
    private Integer startExpmoney;
    @Expose
    private Integer endExpmoney;

    @Expose
    private Integer startAbledExpmoney;
    @Expose
    private Integer endAbledExpmoney;

    @Expose
    private Integer startCurrent;
    @Expose
    private Integer endCurrent;
    @Expose
    private Integer startRagular;
    @Expose
    private Integer endRagular;
    
    @Expose
    private Integer status;

    @Expose
    private String startRegiest;
    @Expose
    private String endRegiest;

    @Expose
    private Integer startAbleMoney;
    @Expose
    private Integer endAbleMoney;
    @Expose
    private Integer startTotalMoney;
    @Expose
    private Integer endTotalMoney;
    @Expose
    private Integer startFree;
    @Expose
    private Integer endFree;
    //新手宝金额范围查询条件
    @Expose
    private Integer startPrivilege;
    @Expose
    private Integer endPrivilege;
    public List<SearchFilter> getAndFilters() {

        filters.add(SearchFilterHelper.newCondition("status", SearchOperator.ne, -1));

        if (!StringUtils.isEmpty(username)) {
            filters.add(SearchFilterHelper.newCondition("username", SearchOperator.like, username));
        }

        if (!StringUtils.isEmpty(mobile)) {
            filters.add(SearchFilterHelper.newCondition("mobile", SearchOperator.like, mobile));
        }

        if (!StringUtils.isEmpty(referralMobile)) {
            filters.add(SearchFilterHelper.newCondition("referralMobile", SearchOperator.like, referralMobile));
        }

        if (userId != null) {
            filters.add(SearchFilterHelper.newCondition("id", SearchOperator.eq, userId));
        }


        if (userType != null) {
            filters.add(SearchFilterHelper.newCondition("userType", SearchOperator.eq, userType));
        }

        if (userLeve != null) {
            filters.add(SearchFilterHelper.newCondition("userLeve.id", SearchOperator.eq, userLeve));
        }

        if (StringUtils.isNotEmpty(wechatNumber)) {
            filters.add(SearchFilterHelper.newCondition("wechatNumber", SearchOperator.like, wechatNumber));
        }

        if (StringUtils.isNotEmpty(qqNumber)) {
            filters.add(SearchFilterHelper.newCondition("qqNumber", SearchOperator.like, qqNumber));
        }

        if (startAllIncome != null) {
            filters.add(SearchFilterHelper.newCondition("userIncome.allIncome", SearchOperator.gte, startAllIncome));
        }

        if (endAllIncome != null) {
            filters.add(SearchFilterHelper.newCondition("userIncome.allIncome", SearchOperator.lte, endAllIncome));
        }

        if (startFreezeMoney != null) {
            filters.add(SearchFilterHelper.newCondition("userMoney.freezeFund", SearchOperator.gte, startFreezeMoney));
        }

        if (endFreezeMoney != null) {
            filters.add(SearchFilterHelper.newCondition("userMoney.freezeFund", SearchOperator.lte, endFreezeMoney));
        }

        if (startExpmoney != null) {
            filters.add(SearchFilterHelper.newCondition("userExMoney.amount", SearchOperator.gte, startExpmoney));
        }

        if (endExpmoney != null) {
            filters.add(SearchFilterHelper.newCondition("userExMoney.amount", SearchOperator.lte, endExpmoney));
        }

        if (startAbledExpmoney != null) {
            filters.add(SearchFilterHelper.newCondition("userExMoney.ableMoney", SearchOperator.gte, startAbledExpmoney));
        }

        if (endAbledExpmoney != null) {
            filters.add(SearchFilterHelper.newCondition("userExMoney.ableMoney", SearchOperator.lte, endAbledExpmoney));
        }

        if (startCurrent != null) {
            filters.add(SearchFilterHelper.newCondition("userInvestinfo.allInvestDayloan", SearchOperator.gte, startCurrent));
        }

        if (endCurrent != null) {
            filters.add(SearchFilterHelper.newCondition("userInvestinfo.allInvestDayloan", SearchOperator.lte, endCurrent));
        }

        if (startRagular != null) {
            filters.add(SearchFilterHelper.newCondition("userInvestinfo.allInvestDeposit", SearchOperator.gte, startRagular));
        }

        if (endRagular != null) {
            filters.add(SearchFilterHelper.newCondition("userInvestinfo.allInvestDeposit", SearchOperator.lte, endRagular));
        }

        if (status != null) {
            if (status == 1) {
                filters.add(SearchFilterHelper.newCondition("remark", SearchOperator.isNotNull, ""));
            }

            if (status == 2) {
                filters.add(SearchFilterHelper.newCondition("remark", SearchOperator.isNull, ""));
            }
        }

        if (!StringUtils.isEmpty(startRegiest)) {
            Long lg = DateUtil.getSystemTimeDay(startRegiest);
            filters.add(SearchFilterHelper.newCondition("created", SearchOperator.gte, lg));
        }

        if (!StringUtils.isEmpty(endRegiest)) {
            Long lt = DateUtil.getSystemTimeDay(endRegiest) + 24 * 3600;
            filters.add(SearchFilterHelper.newCondition("created", SearchOperator.lte, lt));
        }

        if (startAbleMoney != null) {
            filters.add(SearchFilterHelper.newCondition("userMoney.usableFund", SearchOperator.gte, startAbleMoney));
        }

        if (endAbleMoney != null) {
            filters.add(SearchFilterHelper.newCondition("userMoney.usableFund", SearchOperator.lte, endAbleMoney));
        }


        if (startTotalMoney != null) {
            filters.add(SearchFilterHelper.newCondition("userMoney.totalFund", SearchOperator.gte, startTotalMoney));
        }

        if (endTotalMoney != null) {
            filters.add(SearchFilterHelper.newCondition("userMoney.totalFund", SearchOperator.lte, endTotalMoney));
        }

        if (startFree != null) {
            filters.add(SearchFilterHelper.newCondition("userInvestinfo.allInvestFree", SearchOperator.gte, startFree));
        }

        if (endFree != null) {
            filters.add(SearchFilterHelper.newCondition("userInvestinfo.allInvestFree", SearchOperator.lte, endFree));
        }

        if (startPrivilege != null) {
            filters.add(SearchFilterHelper.newCondition("userInvestinfo.allInvestPrivilege", SearchOperator.gte, startPrivilege));
        }

        if (endPrivilege != null) {
            filters.add(SearchFilterHelper.newCondition("userInvestinfo.allInvestPrivilege", SearchOperator.lte, endPrivilege));
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

        if (!StringUtils.isEmpty(referralMobile)) {
            filter.put("referralMobile", referralMobile);
        }

        if (userId != null) {
            filter.put("userId", userId);
        }

        if (userType != null) {
            filter.put("userType", userType);
        }

        if (userLeve != null) {
            filter.put("userLeve", userLeve);
        }

        if (StringUtils.isNotEmpty(wechatNumber)) {
            filter.put("wechatNumber", wechatNumber);
        }

        if (StringUtils.isNotEmpty(qqNumber)) {
            filter.put("qqNumber", qqNumber);
        }

        if (startAllIncome != null) {
            filter.put("startAllIncome", startAllIncome);
        }

        if (endAllIncome != null) {
            filter.put("endAllIncome", endAllIncome);
        }

        if (startFreezeMoney != null) {
            filter.put("startFreezeMoney", startFreezeMoney);
        }

        if (endFreezeMoney != null) {
            filter.put("endFreezeMoney", endFreezeMoney);
        }

        if (startExpmoney != null) {
            filter.put("startExpmoney", startExpmoney);
        }

        if (endExpmoney != null) {
            filter.put("endExpmoney", endExpmoney);
        }

        if (startAbledExpmoney != null) {
            filter.put("startAbledExpmoney", startAbledExpmoney);
        }

        if (endAbledExpmoney != null) {
            filter.put("endAbledExpmoney", endAbledExpmoney);
        }

        if (startCurrent != null) {
            filter.put("startCurrent", startCurrent);
        }

        if (endCurrent != null) {
            filter.put("endCurrent", endCurrent);
        }

        if (startRagular != null) {
            filter.put("startRagular", startRagular);
        }

        if (endRagular != null) {
            filter.put("endRagular", endRagular);
        }

        if (status != null) {
            filter.put("status", status);
        }

        if (!StringUtils.isEmpty(startRegiest)) {
            filter.put("startRegiest", DateUtil.getSystemTimeDay(startRegiest));
        }

        if (!StringUtils.isEmpty(endRegiest)) {
            filter.put("endRegiest", DateUtil.getSystemTimeDay(endRegiest) + 24 * 3600);
        }

        if (startAbleMoney != null) {
            filter.put("startAbleMoney", startAbleMoney);
        }

        if (endAbleMoney != null) {
            filter.put("endAbleMoney", endAbleMoney);
        }

        if (startTotalMoney != null) {
            filter.put("startTotalMoney", startTotalMoney);
        }

        if (endTotalMoney != null) {
            filter.put("endTotalMoney", endTotalMoney);
        }

        if (startFree != null) {
            filter.put("startFree", startFree);
        }

        if (endFree != null) {
            filter.put("endFree", endFree);
        }

        if (startPrivilege != null) {
            filter.put("startPrivilege", startPrivilege);
        }

        if (endPrivilege != null) {
            filter.put("endPrivilege", endPrivilege);
        }

        filter.put("orderNumber", orderNumber);
        filter.put("orderSort", orderSort);

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

    public String getReferralMobile() {
        return referralMobile;
    }

    public void setReferralMobile(String referralMobile) {
        this.referralMobile = referralMobile;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getUserLeve() {
        return userLeve;
    }

    public void setUserLeve(Integer userLeve) {
        this.userLeve = userLeve;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderSort() {
        return orderSort;
    }

    public void setOrderSort(String orderSort) {
        this.orderSort = orderSort;
    }

    public String getWechatNumber() {
        return wechatNumber;
    }

    public void setWechatNumber(String wechatNumber) {
        this.wechatNumber = wechatNumber;
    }

    public String getQqNumber() {
        return qqNumber;
    }

    public void setQqNumber(String qqNumber) {
        this.qqNumber = qqNumber;
    }

    public Integer getStartAllIncome() {
        return startAllIncome;
    }

    public void setStartAllIncome(Integer startAllIncome) {
        this.startAllIncome = startAllIncome;
    }

    public Integer getEndAllIncome() {
        return endAllIncome;
    }

    public void setEndAllIncome(Integer endAllIncome) {
        this.endAllIncome = endAllIncome;
    }

    public Integer getStartFreezeMoney() {
        return startFreezeMoney;
    }

    public void setStartFreezeMoney(Integer startFreezeMoney) {
        this.startFreezeMoney = startFreezeMoney;
    }

    public Integer getEndFreezeMoney() {
        return endFreezeMoney;
    }

    public void setEndFreezeMoney(Integer endFreezeMoney) {
        this.endFreezeMoney = endFreezeMoney;
    }

    public Integer getStartExpmoney() {
        return startExpmoney;
    }

    public void setStartExpmoney(Integer startExpmoney) {
        this.startExpmoney = startExpmoney;
    }

    public Integer getEndExpmoney() {
        return endExpmoney;
    }

    public void setEndExpmoney(Integer endExpmoney) {
        this.endExpmoney = endExpmoney;
    }

    public Integer getStartAbledExpmoney() {
        return startAbledExpmoney;
    }

    public void setStartAbledExpmoney(Integer startAbledExpmoney) {
        this.startAbledExpmoney = startAbledExpmoney;
    }

    public Integer getEndAbledExpmoney() {
        return endAbledExpmoney;
    }

    public void setEndAbledExpmoney(Integer endAbledExpmoney) {
        this.endAbledExpmoney = endAbledExpmoney;
    }

    public Integer getStartCurrent() {
        return startCurrent;
    }

    public void setStartCurrent(Integer startCurrent) {
        this.startCurrent = startCurrent;
    }

    public Integer getEndCurrent() {
        return endCurrent;
    }

    public void setEndCurrent(Integer endCurrent) {
        this.endCurrent = endCurrent;
    }

    public Integer getStartRagular() {
        return startRagular;
    }

    public void setStartRagular(Integer startRagular) {
        this.startRagular = startRagular;
    }

    public Integer getEndRagular() {
        return endRagular;
    }

    public void setEndRagular(Integer endRagular) {
        this.endRagular = endRagular;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStartRegiest() {
        return startRegiest;
    }

    public void setStartRegiest(String startRegiest) {
        this.startRegiest = startRegiest;
    }

    public String getEndRegiest() {
        return endRegiest;
    }

    public void setEndRegiest(String endRegiest) {
        this.endRegiest = endRegiest;
    }

    public Integer getStartAbleMoney() {
        return startAbleMoney;
    }

    public void setStartAbleMoney(Integer startAbleMoney) {
        this.startAbleMoney = startAbleMoney;
    }

    public Integer getEndAbleMoney() {
        return endAbleMoney;
    }

    public void setEndAbleMoney(Integer endAbleMoney) {
        this.endAbleMoney = endAbleMoney;
    }

    public Integer getStartTotalMoney() {
        return startTotalMoney;
    }

    public void setStartTotalMoney(Integer startTotalMoney) {
        this.startTotalMoney = startTotalMoney;
    }

    public Integer getEndTotalMoney() {
        return endTotalMoney;
    }

    public void setEndTotalMoney(Integer endTotalMoney) {
        this.endTotalMoney = endTotalMoney;
    }

    public void setStartFree(Integer startFree) {
        this.startFree = startFree;
    }

    public void setEndFree(Integer endFree) {
        this.endFree = endFree;
    }

    public Integer getStartFree() {
        return startFree;
    }

    public Integer getEndFree() {
        return endFree;
    }

    public Integer getStartPrivilege() {
        return startPrivilege;
    }

    public void setStartPrivilege(Integer startPrivilege) {
        this.startPrivilege = startPrivilege;
    }

    public Integer getEndPrivilege() {
        return endPrivilege;
    }

    public void setEndPrivilege(Integer endPrivilege) {
        this.endPrivilege = endPrivilege;
    }
}
