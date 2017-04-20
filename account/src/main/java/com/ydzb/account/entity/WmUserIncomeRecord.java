package com.ydzb.account.entity;

import com.ydzb.core.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;


@Entity
@Table(name = "wm_user_income_record")
public class WmUserIncomeRecord extends BaseEntity<Long> {

    public static final int CURRENT = 1;
    public static final int RAGULAR = 2;
    public static final int REDPACKET_CASH = 3;
    public static final int FUNDATION_PRODUCT = 6;  //基金产品
    public static final int PTYPE_FREE = 11;    //随心存
    public static final int PTYPE_PRIVILEGE = 12;   //新手特权


    @Column(name = "user_id")
    private Long userId;

    private String name;

    private BigDecimal income;

    private Integer ptype;

    private Long  optime;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public Integer getPtype() {
        return ptype;
    }

    public void setPtype(Integer ptype) {
        this.ptype = ptype;
    }

    public Long getOptime() {
        return optime;
    }

    public void setOptime(Long optime) {
        this.optime = optime;
    }
}
