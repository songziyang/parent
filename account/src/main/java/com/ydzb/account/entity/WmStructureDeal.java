package com.ydzb.account.entity;

import com.ydzb.core.entity.BaseEntity;
import com.ydzb.core.utils.DateUtil;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 转转赚交易记录
 */
@Entity
@Table(name = "wm_structure_deal")
@DynamicInsert
@DynamicUpdate
public class WmStructureDeal extends BaseEntity<Long> {

    private static final long serialVersionUID = 2474401648514579648L;

    private Integer copies;
    private Long btime;
    private Integer state;
    private BigDecimal income;  //收益
    private BigDecimal apr; //结算利率

    /**
     * 到期日期
     */
    @Column(name = "close_date")
    private Long closeDate;

    @Column(name = "user_id")
    private Long userId;

    /**
     * 转转赚产品
     */
    @Column(name = "structure_id")
    private Long structureId;

    /**
     * 加息利率
     */
    @Column(name = "add_apr")
    private BigDecimal addApr;

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


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public BigDecimal getApr() {
        return apr;
    }

    public void setApr(BigDecimal apr) {
        this.apr = apr;
    }

    public Long getStructureId() {
        return structureId;
    }

    public void setStructureId(Long structureId) {
        this.structureId = structureId;
    }

    public BigDecimal getAddApr() {
        return addApr;
    }

    public void setAddApr(BigDecimal addApr) {
        this.addApr = addApr;
    }

    public Long getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Long closeDate) {
        this.closeDate = closeDate;
    }
}