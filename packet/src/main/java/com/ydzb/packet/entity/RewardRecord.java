package com.ydzb.packet.entity;

import com.ydzb.core.entity.BaseEntity;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.user.entity.User;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "wm_reward_records")
public class RewardRecord extends BaseEntity<Long> {

    public static final byte NO_EXCHANGED = 0;  //未兑换
    public static final byte ALREADY_EXCHANGED = 1; //已兑换

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private User user;

    /**
     * 奖品金额
     */
    @Column(name = "reward_val")
    private BigDecimal rewardVal;

    /**
     * 奖品类型
     */
    @Column(name = "reward_type")
    private Byte rewardType;

    /**
     * 奖品名称
     */
    @Column(name = "reward_name")
    private String rewardName;

    /**
     * 中奖时间
     */
    @Column(name = "reward_time")
    private Long rewardTime;

    /**
     * 兑换记录 0-未兑换   1-已兑换
     */
    @Column(name = "exchage_type")
    private Byte exchageType;

    /**
     * 兑换时间
     */
    @Column(name = "exchage_time")
    private Long exchageTime;

    /**
     * 收货人姓名
     */
    @Column(name = "real_name")
    private String realName;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 收货地址
     */
    private String address;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 创建时间
     */
    private Long created;

    @Transient
    private Date rewardDate;

    @Transient
    private Date exchageDate;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getRewardVal() {
        return rewardVal;
    }

    public void setRewardVal(BigDecimal rewardVal) {
        this.rewardVal = rewardVal;
    }

    public Byte getRewardType() {
        return rewardType;
    }

    public void setRewardType(Byte rewardType) {
        this.rewardType = rewardType;
    }

    public String getRewardName() {
        return rewardName;
    }

    public void setRewardName(String rewardName) {
        this.rewardName = rewardName;
    }

    public Long getRewardTime() {
        return rewardTime;
    }

    public void setRewardTime(Long rewardTime) {
        this.rewardTime = rewardTime;
    }

    public Byte getExchageType() {
        return exchageType;
    }

    public void setExchageType(Byte exchageType) {
        this.exchageType = exchageType;
    }

    public Long getExchageTime() {
        return exchageTime;
    }

    public void setExchageTime(Long exchageTime) {
        this.exchageTime = exchageTime;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Date getRewardDate() {
        return DateUtil.getSystemTimeMillisecond(rewardTime);
    }

    public Date getExchageDate() {
        return DateUtil.getSystemTimeMillisecond(exchageTime);
    }
}