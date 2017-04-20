package com.ydzb.product.entity;

import com.ydzb.core.entity.BaseEntity;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.user.entity.User;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


@Entity
@Table(name = "wm_structure_deal")
public class StructureDeal extends BaseEntity<Long> {

    private static final long serialVersionUID = 2474401648514579648L;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "structure_id")
    private Structure structure;

    private Integer copies;

    private Long btime;

    private Integer state;

    private BigDecimal income;  //收益

    private BigDecimal apr; //利率

    /**
     * 到期日期
     */
    @Column(name = "close_date")
    private Long closeDate;

    @Transient
    private Date buyDate;

    @Transient
    private Date cDate;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getCopies() {
        return copies;
    }

    public void setCopies(Integer copies) {
        this.copies = copies;
    }

    public Long getBtime() {
        return btime;
    }

    public void setBtime(Long btime) {
        this.btime = btime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getBuyDate() {
        return DateUtil.getSystemTimeMillisecond(btime);
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public Structure getStructure() {
        return structure;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
    }

    public BigDecimal getApr() {
        return apr;
    }

    public void setApr(BigDecimal apr) {
        this.apr = apr;
    }

    public Long getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Long closeDate) {
        this.closeDate = closeDate;
    }

    public Date getcDate() {
        return DateUtil.getSystemTimeMillisecond(closeDate);
    }
}