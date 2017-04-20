package com.ydzb.account.entity;

import com.ydzb.core.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "wm_platform_balance_remind")
public class WmPlatformBalanceRemind extends BaseEntity<Long> {

    //第三方余额
    @Column(name = "third_fund")
    private String thirdFund;

    //金额类型 1 2 3 4 5
    private Integer type;

    //记录日期
    private Long opdate;

    //记录时间
    private Long optime;


    public String getThirdFund() {
        return thirdFund;
    }

    public void setThirdFund(String thirdFund) {
        this.thirdFund = thirdFund;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getOpdate() {
        return opdate;
    }

    public void setOpdate(Long opdate) {
        this.opdate = opdate;
    }

    public Long getOptime() {
        return optime;
    }

    public void setOptime(Long optime) {
        this.optime = optime;
    }
}