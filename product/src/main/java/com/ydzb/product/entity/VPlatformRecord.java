package com.ydzb.product.entity;

import com.ydzb.core.entity.BaseEntity;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.user.entity.User;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 平台资金记录
 */
@Entity
@Table(name = "vi_platform_record")
public class VPlatformRecord extends BaseEntity<String> {

    private Integer type;   //类型

    private BigDecimal fund;    //操作金额

    private String opinfo;  //操作说明

    private Long optime;    //操作时间

    @Column(name = "link_type")
    private Integer linkType;


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

    public Integer getLinkType() {
        return linkType;
    }

    public void setLinkType(Integer linkType) {
        this.linkType = linkType;
    }

    public Date getOpdate() {
        return DateUtil.getSystemTimeMillisecond(optime);
    }
}