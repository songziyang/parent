package com.ydzb.account.entity;



import com.ydzb.core.entity.BaseEntity;
import com.ydzb.core.utils.DateUtil;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 平台资金记录
 */
@Entity
@Table(name = "wm_platform_fund_record")
@DynamicInsert
@DynamicUpdate
public class WmPlatformFundRecord extends BaseEntity<Long> {

    private Integer type;   //类型
    private BigDecimal fund;    //操作金额
    private String opinfo;  //操作说明
    private Long optime;    //操作时间

    /**
     * 可用余额
     */
    @Column(name = "usable_fund")
    private BigDecimal usableFund;
    /**
     * 链接类型
     */
    @Column(name = "link_type")
    private Integer linkType;

    /**
     * 链接id
     */
    @Column(name = "link_id")
    private Long linkId;

    @Transient
    private Date opdate;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public BigDecimal getFund() {
        return fund;
    }

    public void setFund(BigDecimal fund) {
        this.fund = fund;
    }

    public BigDecimal getUsableFund() {
        return usableFund;
    }

    public void setUsableFund(BigDecimal usableFund) {
        this.usableFund = usableFund;
    }

    public Integer getLinkType() {
        return linkType;
    }

    public void setLinkType(Integer linkType) {
        this.linkType = linkType;
    }

    public Long getLinkId() {
        return linkId;
    }

    public void setLinkId(Long linkId) {
        this.linkId = linkId;
    }

    public String getOpinfo() {
        return opinfo;
    }

    public void setOpinfo(String opinfo) {
        this.opinfo = opinfo;
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