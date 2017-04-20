package com.ydzb.user.entity;

import com.ydzb.core.entity.BaseEntity;
import com.ydzb.core.utils.DateUtil;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户收益记录
 * @author sy
 */
@Entity
@Table(name = "wm_user_income_record")
public class UserIncomeRecord extends BaseEntity<Long> {

    public static final byte CURRENT = 1;   //活期宝类型
    public static final byte REGULAR = 2;   //定存宝类型
    public static final byte CASH_REDPACKET = 3;    //现金红包类型

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * 来源名称
     */
    private String name;

    /**
     * 收益
     */
    private BigDecimal income;

    /**
     * 产品类型 1-活期宝   2-定存宝   3-现金红包
     */
    private Byte ptype;

    /**
     * 操作时间
     */
    private Long optime;

    @Transient
    private Date opdate;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Byte getPtype() {
        return ptype;
    }

    public void setPtype(Byte ptype) {
        this.ptype = ptype;
    }

    public Long getOptime() {
        return optime;
    }

    public void setOptime(Long optime) {
        this.optime = optime;
    }

    public Date getOpdate() {
        return DateUtil.getSystemTimeMillisecond(optime);
    }
}