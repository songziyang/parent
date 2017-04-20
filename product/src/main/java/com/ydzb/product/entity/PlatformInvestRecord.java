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
 * 平台债权记录
 */
@Entity
@Table(name = "wm_platform_invest_record")
@DynamicInsert
@DynamicUpdate
public class PlatformInvestRecord extends BaseEntity<Long> {

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

    @ManyToOne
    @JoinColumn(name = "link_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private User user;


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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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