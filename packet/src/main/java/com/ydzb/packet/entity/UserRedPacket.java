package com.ydzb.packet.entity;

import com.ydzb.core.entity.BaseEntity;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.user.entity.User;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户红包
 *
 * @author sy
 */
@Entity
@Table(name = "wm_redpacket_users")
public class UserRedPacket extends BaseEntity<Long> {

    public static final byte STATUS_NOUSE = 1; //未使用
    public static final byte STATUS_FROZEN = 2;    //冻结
    public static final byte STATUS_USED = 3;      //已使用
    public static final byte STATUS_OVERTIME = 4;  //已过期
    public static final byte STATUS_CASH_USE = 5;  //现金使用

    public static final byte REDPACKET_TYPE_CASH = 1;   //现金红包
    public static final byte REDPACKET_TYPE_INTEREST = 2;   //加息券

    public static final byte SHARE_UNSUCCESSFUL = 0;   //分享-未成功
    public static final byte SHARE_SUCCESSFUL = 1;   //分享-成功


    /**
     * 红包用户
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * 产品交易id
     */
    @Column(name = "product_trade_id")
    private Long productTradeId;

    /**
     * 用户红包
     */
    @Column(name = "redpacket_id")
    private Long redpacketId;

    /**
     * 红包名称
     */
    @Column(name = "redpacket_name")
    private String redpacketName;


    /**
     * 产品类型
     */
    @Column(name = "product_type")
    private Byte productType;

//    /**
//     * 产品类型
//     */
//    @ManyToOne
//    @JoinColumn(name = "product_type")
//    @NotFound(action = NotFoundAction.IGNORE)
//    private ProductType productType;

    /**
     * 类型   1-现金红包  2-加息券
     */
    @Column(name = "redpacket_type")
    private Byte redpacketType;

    /**
     * 触发类型 0-手动    1-注册    2-充值    3-投资    4-推荐
     */
    @Column(name = "trigger_type")
    private Byte triggerType;

    /**
     * 开始有效时间
     */
    @Column(name = "begin_time")
    private Long beginTime;

    /**
     * 截止有效时间
     */
    @Column(name = "finish_time")
    private Long finishTime;

    /**
     * 投资使用天数
     */
    @Column(name = "invest_days")
    private Integer investDays;

    /**
     * 投资截止时间
     */
    @Column(name = "invest_finish_time")
    private Long investFinishTime;

    /**
     * 使用截止时间
     */
    @Column(name = "use_finish_time")
    private Long useFinishTime;

    /**
     * 发放时间
     */
    @Column(name = "get_time")
    private Long getTime;

    /**
     * 用户使用时间
     */
    @Column(name = "user_use_time")
    private Long userUseTime;

    /**
     * 赠送值
     */
    @Column(name = "give_value")
    private BigDecimal giveValue;

    /**
     * 赠送人id
     */
    @Column(name = "give_user_id")
    private Long giveUserId;

    /**
     * 状态：1-未使用     2-已冻结   3-已使用   4-已过期   5-现金领取
     */
    private Byte status = STATUS_NOUSE;

    /**
     * 是否分享成功 0-未成功 1-成功
     */
    private Byte share = SHARE_UNSUCCESSFUL;

    /**
     * 创建时间
     */
    private Long created;

    /**
     * 创建人姓名
     */
    @Column(name = "created_user")
    private String createdUser;

    @Column(name = "limit_fund")
    private Long limitFund; //满足金额

    @Transient
    private Date beginDate;

    @Transient
    private Date finishDate;

    @Transient
    private Date createDate;

    @Transient
    private Date userUseDate;

    @Transient
    private Date useFinishDate; //使用截至日期

    @Transient
    private Date investFinishDate;  //投资截至日期

    @Transient
    private Date getDate;   //发放日期

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getProductTradeId() {
        return productTradeId;
    }

    public void setProductTradeId(Long productTradeId) {
        this.productTradeId = productTradeId;
    }

    public Long getRedpacketId() {
        return redpacketId;
    }

    public void setRedpacketId(Long redpacketId) {
        this.redpacketId = redpacketId;
    }

    public String getRedpacketName() {
        return redpacketName;
    }

    public void setRedpacketName(String redpacketName) {
        this.redpacketName = redpacketName;
    }

    public Byte getProductType() {
        return productType;
    }

    public void setProductType(Byte productType) {
        this.productType = productType;
    }

    public Byte getRedpacketType() {
        return redpacketType;
    }

    public void setRedpacketType(Byte redpacketType) {
        this.redpacketType = redpacketType;
    }

    public Byte getTriggerType() {
        return triggerType;
    }

    public void setTriggerType(Byte triggerType) {
        this.triggerType = triggerType;
    }

    public Long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Long beginTime) {
        this.beginTime = beginTime;
    }

    public Long getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Long finishTime) {
        this.finishTime = finishTime;
    }

    public Long getInvestFinishTime() {
        return investFinishTime;
    }

    public void setInvestFinishTime(Long investFinishTime) {
        this.investFinishTime = investFinishTime;
    }

    public Long getUseFinishTime() {
        return useFinishTime;
    }

    public void setUseFinishTime(Long useFinishTime) {
        this.useFinishTime = useFinishTime;
    }

    public Long getGetTime() {
        return getTime;
    }

    public void setGetTime(Long getTime) {
        this.getTime = getTime;
    }

    public Long getUserUseTime() {
        return userUseTime;
    }

    public void setUserUseTime(Long userUseTime) {
        this.userUseTime = userUseTime;
    }

    public BigDecimal getGiveValue() {
        return giveValue;
    }

    public void setGiveValue(BigDecimal giveValue) {
        this.giveValue = giveValue;
    }

    public Long getGiveUserId() {
        return giveUserId;
    }

    public void setGiveUserId(Long giveUserId) {
        this.giveUserId = giveUserId;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public Date getBeginDate() {
        return DateUtil.getSystemTimeMillisecond(beginTime);
    }

    public Date getFinishDate() {
        return DateUtil.getSystemTimeMillisecond(finishTime);
    }

    public Integer getInvestDays() {
        return investDays;
    }

    public void setInvestDays(Integer investDays) {
        this.investDays = investDays;
    }

    public Byte getShare() {
        return share;
    }

    public void setShare(Byte share) {
        this.share = share;
    }

    public Date getCreateDate() {
        return DateUtil.getSystemTimeMillisecond(created);
    }

    public Date getUserUseDate() {
        return DateUtil.getSystemTimeMillisecond(userUseTime);
    }

    public Date getUseFinishDate() {
        return DateUtil.getSystemTimeMillisecond(useFinishTime);
    }

    public Date getInvestFinishDate() {
        return DateUtil.getSystemTimeMillisecond(investFinishTime);
    }

    public Date getGetDate() {
        return DateUtil.getSystemTimeMillisecond(getTime);
    }

    public Long getLimitFund() {
        return limitFund;
    }

    public void setLimitFund(Long limitFund) {
        this.limitFund = limitFund;
    }
}