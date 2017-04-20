package com.ydzb.product.entity;

import com.ydzb.core.entity.BaseEntity;
import com.ydzb.core.utils.DateUtil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name = "wm_platform_reconciliation")
public class PlatformReconciliation extends BaseEntity<Long> {

    //平台总资金（含收益）
    @Column(name = "platform_fund")
    private String platformFund;

    //平台总收益
    @Column(name = "platform_income")
    private String platformIncome;

    //第三方余额
    @Column(name = "third_fund")
    private String thirdFund;

    //未结算金额
    @Column(name = "third_settlement")
    private String thirdSettlement;

    //冻结金额
    @Column(name = "third_freeze")
    private String thirdFreeze;

    //第三方保证金额
    @Column(name = "third_ensure")
    private String thirdEnsure;

    //创建时间
    private Long created;

    //统计日期
    @Transient
    private Date totalDate;


    public Date getTotalDate() {
        return DateUtil.getSystemTimeMillisecond(created);
    }


    public String getPlatformFund() {
        return platformFund;
    }

    public void setPlatformFund(String platformFund) {
        this.platformFund = platformFund;
    }

    public String getPlatformIncome() {
        return platformIncome;
    }

    public void setPlatformIncome(String platformIncome) {
        this.platformIncome = platformIncome;
    }

    public String getThirdFund() {
        return thirdFund;
    }

    public void setThirdFund(String thirdFund) {
        this.thirdFund = thirdFund;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public String getThirdSettlement() {
        return thirdSettlement;
    }

    public void setThirdSettlement(String thirdSettlement) {
        this.thirdSettlement = thirdSettlement;
    }

    public String getThirdFreeze() {
        return thirdFreeze;
    }

    public void setThirdFreeze(String thirdFreeze) {
        this.thirdFreeze = thirdFreeze;
    }

    public String getThirdEnsure() {
        return thirdEnsure;
    }

    public void setThirdEnsure(String thirdEnsure) {
        this.thirdEnsure = thirdEnsure;
    }
}