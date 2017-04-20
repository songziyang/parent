package com.ydzb.packet.entity;


import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

/**
 * 红包方法用户dto
 */
public class RpUser implements java.io.Serializable {

    /**
     * 用户类型
     */
    private Byte userType;

    /**
     * 用户等级
     */
    private Byte userLevel;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 注册时间开始
     */
    private String registerStartDate;

    /**
     * 注册时间结束
     */
    private String registerEndDate;

    /**
     * 账户总额开始
     */
    private BigDecimal startAccount;

    /**
     * 账户总额结束
     */
    private BigDecimal endAccount;

    /**
     * 投资总额开始
     */
    private BigDecimal startInvest;

    /**
     * 投资总额结束
     */
    private BigDecimal endInvest;

    /**
     * 推荐人数开始
     */
    private Integer refStartNum;

    /**
     * 推荐人数结束
     */
    private Integer refEndNum;

    /**
     * 判断是否有查询条件
     * @return
     */
    public boolean haveCondition() {
        if (userType != null && userType >= 0) {
            return true;
        }

        if (userLevel != null && userLevel >= 0) {
            return true;
        }

        if (StringUtils.isNotEmpty(mobile)) {
            return true;
        }
        if (StringUtils.isNotEmpty(registerStartDate)) {
            return true;
        }
        if (StringUtils.isNotEmpty(registerEndDate)) {
            return true;
        }
        if (startAccount != null) {
            return true;
        }
        if (endAccount != null) {
            return true;
        }
        if (startInvest != null) {
            return true;
        }
        if (endInvest != null) {
            return true;
        }
        if (refStartNum != null) {
            return true;
        }
        if (refEndNum != null) {
            return true;
        }
        return false;
    }

    public Byte getUserType() {
        return userType;
    }

    public void setUserType(Byte userType) {
        this.userType = userType;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRegisterStartDate() {
        return registerStartDate;
    }

    public void setRegisterStartDate(String registerStartDate) {
        this.registerStartDate = registerStartDate;
    }

    public String getRegisterEndDate() {
        return registerEndDate;
    }

    public void setRegisterEndDate(String registerEndDate) {
        this.registerEndDate = registerEndDate;
    }

    public BigDecimal getStartAccount() {
        return startAccount;
    }

    public void setStartAccount(BigDecimal startAccount) {
        this.startAccount = startAccount;
    }

    public BigDecimal getEndAccount() {
        return endAccount;
    }

    public void setEndAccount(BigDecimal endAccount) {
        this.endAccount = endAccount;
    }

    public BigDecimal getStartInvest() {
        return startInvest;
    }

    public void setStartInvest(BigDecimal startInvest) {
        this.startInvest = startInvest;
    }

    public BigDecimal getEndInvest() {
        return endInvest;
    }

    public void setEndInvest(BigDecimal endInvest) {
        this.endInvest = endInvest;
    }

    public Integer getRefStartNum() {
        return refStartNum;
    }

    public void setRefStartNum(Integer refStartNum) {
        this.refStartNum = refStartNum;
    }

    public Integer getRefEndNum() {
        return refEndNum;
    }

    public void setRefEndNum(Integer refEndNum) {
        this.refEndNum = refEndNum;
    }

    public Byte getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(Byte userLevel) {
        this.userLevel = userLevel;
    }
}