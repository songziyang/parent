package com.ydzb.withdraw.entity;

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
 * 用户大额提现记录
 */
@Entity
@Table(name = "wm_user_withhuge")
@DynamicInsert
@DynamicUpdate
public class UserWithHuge extends BaseEntity<Long> {

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private User user;

    /**
     * 提现金额
     */
    @Column(name = "with_money")
    private BigDecimal withMoney;

    /**
     * 可用金额
     */
    @Column(name = "able_money")
    private BigDecimal ableMoney;

    private Long optime;

    @Column(name = "link_id")
    private Long linkId;
    private Integer status;

    @Transient
    private Date opdate;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getWithMoney() {
        return withMoney;
    }

    public void setWithMoney(BigDecimal withMoney) {
        this.withMoney = withMoney;
    }

    public BigDecimal getAbleMoney() {
        return ableMoney;
    }

    public void setAbleMoney(BigDecimal ableMoney) {
        this.ableMoney = ableMoney;
    }

    public Long getOptime() {
        return optime;
    }

    public void setOptime(Long optime) {
        this.optime = optime;
    }

    public Long getLinkId() {
        return linkId;
    }

    public void setLinkId(Long linkId) {
        this.linkId = linkId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getOpdate() {
        return DateUtil.getSystemTimeMillisecond(optime);
    }
}