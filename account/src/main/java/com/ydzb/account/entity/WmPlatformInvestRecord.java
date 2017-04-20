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
 * 平台债权记录
 */
@Entity
@Table(name = "wm_platform_invest_record")
@DynamicInsert
@DynamicUpdate
public class WmPlatformInvestRecord extends BaseEntity<Long> {

    private Integer type;   //类型
    private BigDecimal invest;    //操作债权
    private String opinfo;  //操作说明
    private Long optime;    //操作时间

    /**
     * 债权总额
     */
    @Column(name = "all_invest")
    private BigDecimal allInvest;
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

    public BigDecimal getInvest() {
        return invest;
    }

    public void setInvest(BigDecimal invest) {
        this.invest = invest;
    }

    public BigDecimal getAllInvest() {
        return allInvest;
    }

    public void setAllInvest(BigDecimal allInvest) {
        this.allInvest = allInvest;
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