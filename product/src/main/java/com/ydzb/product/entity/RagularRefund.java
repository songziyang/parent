package com.ydzb.product.entity;

import com.ydzb.core.entity.BaseEntity;
import com.ydzb.user.entity.User;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 定存宝还息
 * @author sy
 */
@Entity
@Table(name = "wm_ragular_refund")
public class RagularRefund extends BaseEntity<Long> {

    public static final byte NOT_REPAYMENT = 0;     //未还
    public static final byte REPAYMENT = 1;     //已还
    public static final byte ISNOT_LAST_STAGE = 0;  //非最后一期
    public static final byte IS_LAST_STAGE = 1; //最后一期

    @ManyToOne
    @JoinColumn(name = "account_id")
    private RagularUserAccount ragularUserAccount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * 总期数
     */
    private Byte stage;

    /**
     * 当前期数
     */
    @Column(name = "cur_stage")
    private Byte curStage;

    /**
     * 还息日期
     */
    @Column(name = "refund_time")
    private Long refundTime;

    /**
     * 收益总金额
     */
    private BigDecimal fund;

    /**
     * 加息收益
     */
    @Column(name = "redpacket_fund")
    private BigDecimal redpacketFund;

    /**
     * vip加息收益
     */
    @Column(name = "vip_fund")
    private BigDecimal vipFund;

    /**
     * 状态：0-未还  1-已还
     */
    private Byte state = NOT_REPAYMENT;

    /**
     * 最后一期状态：0-非最后一期   1-最后一期
     */
    @Column(name = "is_expire")
    private Byte isExpire = ISNOT_LAST_STAGE;


    public RagularUserAccount getRagularUserAccount() {
        return ragularUserAccount;
    }

    public void setRagularUserAccount(RagularUserAccount ragularUserAccount) {
        this.ragularUserAccount = ragularUserAccount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Byte getStage() {
        return stage;
    }

    public void setStage(Byte stage) {
        this.stage = stage;
    }

    public Byte getCurStage() {
        return curStage;
    }

    public void setCurStage(Byte curStage) {
        this.curStage = curStage;
    }

    public Long getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(Long refundTime) {
        this.refundTime = refundTime;
    }

    public BigDecimal getFund() {
        return fund;
    }

    public void setFund(BigDecimal fund) {
        this.fund = fund;
    }

    public BigDecimal getRedpacketFund() {
        return redpacketFund;
    }

    public void setRedpacketFund(BigDecimal redpacketFund) {
        this.redpacketFund = redpacketFund;
    }

    public BigDecimal getVipFund() {
        return vipFund;
    }

    public void setVipFund(BigDecimal vipFund) {
        this.vipFund = vipFund;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public Byte getIsExpire() {
        return isExpire;
    }

    public void setIsExpire(Byte isExpire) {
        this.isExpire = isExpire;
    }
}