package com.ydzb.web.user.condition;

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.filter.SearchFilter;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;
import com.ydzb.core.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserCondition {

    private List<SearchFilter> filters = Lists.newArrayList();


    //用户名
    @Expose
    private String username;
    //手机号
    @Expose
    private String mobile;
    //身份证号
    @Expose
    private String idCard;
    //推荐人手机号
    @Expose
    private String referralMobile;
    //注册日期始
    @Expose
    private String startRegiest;
    //注册日期止
    @Expose
    private String endRegiest;
    //是否投资用户
    @Expose
    private Integer isInvUser;
    //锁定状态
    @Expose
    private Integer isLogin;
    //身份证验证次数
    @Expose
    private Integer cardVerifyNum;
    //用户类型
    @Expose
    private Integer userType;
    //用户等级
    @Expose
    private Integer userLeve;
    //是否红名
    @Expose
    private Integer status;
    //累计积分始
    @Expose
    private BigDecimal startTotalIntegral;
    //累计积分止
    @Expose
    private BigDecimal endTotalIntegral;
    //剩余积分始
    @Expose
    private BigDecimal startIntegral;
    //剩余积分止
    @Expose
    private BigDecimal endIntegral;
    /**
     * ADD BY CRF
     * 2017.03.06
     */
    //是否开通电子账户
    @Expose
    private Integer hasAccountId;
    //电子账户类型
    @Expose
    private Integer accountType;
    //是否买单侠用户
    @Expose
    private Integer userSource;
    /**
     * END
     */

    @Expose
    private String realName;


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

        if (!StringUtils.isEmpty(startRegiest)) {
            Long lg = DateUtil.getSystemTimeDay(startRegiest);
            filters.add(SearchFilterHelper.newCondition("created", SearchOperator.gte, lg));
        }

        if (!StringUtils.isEmpty(endRegiest)) {
            Long lt = DateUtil.getSystemTimeDay(endRegiest) + 24 * 3600;
            filters.add(SearchFilterHelper.newCondition("created", SearchOperator.lte, lt));
        }

        if (isLogin != null) {
            filters.add(SearchFilterHelper.newCondition("isLogin", SearchOperator.eq, isLogin));
        }

        if (cardVerifyNum != null ) {
            filters.add(SearchFilterHelper.newCondition("cardVerifyNum", SearchOperator.eq, cardVerifyNum));
        }

        if (isInvUser != null) {
            if (isInvUser == 1 ) {
                filters.add(SearchFilterHelper.newCondition(
                        "userInvestinfo.allInvest", SearchOperator.gt, 0));
            }
            if (isInvUser == 2) {
                filters.add(SearchFilterHelper.newCondition(
                        "userInvestinfo.allInvest", SearchOperator.lte, 0));
            }
        }

        if (userType != null ) {
            filters.add(SearchFilterHelper.newCondition("userType", SearchOperator.eq, userType));
        }

        if (userLeve != null ) {
            filters.add(SearchFilterHelper.newCondition("userLeve.id", SearchOperator.eq, userLeve));
        }


        if (!StringUtils.isEmpty(idCard)) {
            filters.add(SearchFilterHelper.newCondition("idCard", SearchOperator.like, idCard));
        }

        if (status != null) {
            if (status == 1) {
                filters.add(SearchFilterHelper.newCondition("remark", SearchOperator.isNotNull, ""));
            }
            if (status == 2) {
                filters.add(SearchFilterHelper.newCondition("remark", SearchOperator.isNull, ""));
            }
        }

        if (startTotalIntegral != null) {
            filters.add(SearchFilterHelper.newCondition("userIntegral.totalIntegral", SearchOperator.gte, startTotalIntegral));
        }

        if (endTotalIntegral != null) {
            filters.add(SearchFilterHelper.newCondition("userIntegral.totalIntegral", SearchOperator.lt, endTotalIntegral));
        }

        if (startIntegral != null) {
            filters.add(SearchFilterHelper.newCondition("userIntegral.integral", SearchOperator.gte, startIntegral));
        }

        if (endIntegral != null) {
            filters.add(SearchFilterHelper.newCondition("userIntegral.integral", SearchOperator.lt, endIntegral));
        }

        //是否开通电子账户
        if (hasAccountId != null) {
            if (hasAccountId == 0) {
                filters.add(SearchFilterHelper.newCondition("accountid", SearchOperator.isNull, ""));
            }
            if (hasAccountId == 1) {
                filters.add(SearchFilterHelper.newCondition("accountid", SearchOperator.isNotNull, ""));
            }
        }
        //电子账户类型
        if (accountType != null) {
            filters.add(SearchFilterHelper.newCondition("accountType", SearchOperator.eq, accountType));
        }
        //是否买单侠用户
        if (userSource != null) {
            if (userSource == 0) {
                SearchFilter usersourceFilter1 = SearchFilterHelper.newCondition("userSource", SearchOperator.ne, -1);
                SearchFilter usersourceFilter2 = SearchFilterHelper.newCondition("userSource", SearchOperator.isNull, "");
                filters.add(SearchFilterHelper.or(usersourceFilter1, usersourceFilter2));
            }
            if (userSource == 1) {
                filters.add(SearchFilterHelper.newCondition("userSource", SearchOperator.eq, -1));
            }
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


        if (!StringUtils.isEmpty(startRegiest)) {
            filter.put("startRegiest", DateUtil.getSystemTimeDay(startRegiest));
        }

        if (!StringUtils.isEmpty(endRegiest)) {
            filter.put("endRegiest", DateUtil.getSystemTimeDay(endRegiest) + 24 * 3600);
        }

        if (StringUtils.isNotEmpty(realName)) {
            filter.put("realName", realName);
        }

        if (isLogin != null) {
            filter.put("isLogin", isLogin);
        }

        if (cardVerifyNum != null ) {
            filter.put("cardVerifyNum", cardVerifyNum);
        }

        if (isInvUser != null) {
            filter.put("isInvUser", isInvUser);
        }

        if (userType != null ) {
            filter.put("userType", userType);
        }

        if (userLeve != null ) {
            filter.put("userLeve", userLeve);
        }

        if (status != null ) {
            filter.put("status", status);
        }

        if (startTotalIntegral != null ) {
            filter.put("startTotalIntegral", startTotalIntegral);
        }
        if (endTotalIntegral != null ) {
            filter.put("endTotalIntegral", endTotalIntegral);
        }
        if (startIntegral != null ) {
            filter.put("startIntegral", startIntegral);
        }
        if (endIntegral != null ) {
            filter.put("endIntegral", endIntegral);
        }

        if (hasAccountId != null) {
            filter.put("accountid", hasAccountId);
        }
        if (accountType != null) {
            filter.put("accountType", accountType);
        }
        if (userSource != null) {
            filter.put("userSource", userSource);
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

    public String getReferralMobile() {
        return referralMobile;
    }

    public void setReferralMobile(String referralMobile) {
        this.referralMobile = referralMobile;
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

    public Integer getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(Integer isLogin) {
        this.isLogin = isLogin;
    }

    public Integer getCardVerifyNum() {
        return cardVerifyNum;
    }

    public void setCardVerifyNum(Integer cardVerifyNum) {
        this.cardVerifyNum = cardVerifyNum;
    }

    public Integer getIsInvUser() {
        return isInvUser;
    }

    public void setIsInvUser(Integer isInvUser) {
        this.isInvUser = isInvUser;
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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getStartTotalIntegral() {
        return startTotalIntegral;
    }

    public void setStartTotalIntegral(BigDecimal startTotalIntegral) {
        this.startTotalIntegral = startTotalIntegral;
    }

    public BigDecimal getEndTotalIntegral() {
        return endTotalIntegral;
    }

    public void setEndTotalIntegral(BigDecimal endTotalIntegral) {
        this.endTotalIntegral = endTotalIntegral;
    }

    public BigDecimal getStartIntegral() {
        return startIntegral;
    }

    public void setStartIntegral(BigDecimal startIntegral) {
        this.startIntegral = startIntegral;
    }

    public BigDecimal getEndIntegral() {
        return endIntegral;
    }

    public void setEndIntegral(BigDecimal endIntegral) {
        this.endIntegral = endIntegral;
    }

    public Integer getHasAccountId() {
        return hasAccountId;
    }

    public void setHasAccountId(Integer hasAccountId) {
        this.hasAccountId = hasAccountId;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public Integer getUserSource() {
        return userSource;
    }

    public void setUserSource(Integer userSource) {
        this.userSource = userSource;
    }

}
