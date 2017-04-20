package com.ydzb.user.entity;

import com.ydzb.core.entity.BaseEntity;
import com.ydzb.core.utils.DateUtil;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户资金转账记录
 */
@Entity
@Table(name = "wm_fund_transfer_record")
public class FundTransferRecord extends BaseEntity<Long> {

    /**
     * 转出人id
     */
    @ManyToOne
    @JoinColumn(name = "out_user_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private User fromUser;

    /**
     * 转入人id
     */
    @ManyToOne
    @JoinColumn(name = "into_user_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private User toUser;

    /**
     * 操作时间
     */
    private Long optime;

    @Transient
    private Date opDate;

    /**
     * 转账金额
     */
    private BigDecimal fund;

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

    public Long getOptime() {
        return optime;
    }

    public void setOptime(Long optime) {
        this.optime = optime;
    }

    public BigDecimal getFund() {
        return fund;
    }

    public void setFund(BigDecimal fund) {
        this.fund = fund;
    }

    public Date getOpDate() {
        return DateUtil.getSystemTimeMillisecond(optime);
    }

    public void setOpDate(Date opDate) {
        this.opDate = opDate;
    }
}