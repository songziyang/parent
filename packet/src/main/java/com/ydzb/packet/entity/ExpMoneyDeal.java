package com.ydzb.packet.entity;

import com.ydzb.core.entity.BaseEntity;
import com.ydzb.user.entity.User;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 用户体验金记录
 * @author sy
 */
@Entity
@Table(name = "wm_expmoney_deal")
public class ExpMoneyDeal extends BaseEntity<Long> {

    public static final byte STATUS_NOT_SETTLED = 0;   //未结算
    public static final byte STATUS_SETTLED = 1;   //已结算

    /**
     * 用户
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * 金额
     */
    private Integer fund;

    /**
     * 创建时间
     */
    private Long created;

    /**
     * 到期日期
     */
    private Long closeDate;

    /**
     * 结算状态 0-未结算   1-已结算
     */
    private Byte status = STATUS_NOT_SETTLED;

    /**
     * 类型   1-注册    2-推荐    3-签到    4-老用户   5-茶馆活动  6-推荐收益  7-后台手动
     */
    private Byte type;

    /**
     * 构造函数
     */
    public ExpMoneyDeal() {

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getFund() {
        return fund;
    }

    public void setFund(Integer fund) {
        this.fund = fund;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Long getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Long closeDate) {
        this.closeDate = closeDate;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }
}