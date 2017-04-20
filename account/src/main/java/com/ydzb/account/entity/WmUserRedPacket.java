package com.ydzb.account.entity;

import com.ydzb.core.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;


@Entity
@Table(name = "wm_redpacket_users")
public class WmUserRedPacket extends BaseEntity<Long> {

    public static final Integer STATUS_NOUSE = 1; //未使用
    public static final Integer STATUS_USED = 3;      //已使用
    public static final Integer STATUS_OVERTIME = 4;  //已过期


    /**
     * 红包用户
     */
    @Column(name = "user_id")
    private Long userId;

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
    private Integer productType;

    /**
     * 类型   1-现金红包  2-加息券
     */
    @Column(name = "redpacket_type")
    private Integer redpacketType;

    /**
     * 触发类型 0-手动    1-注册    2-充值    3-投资    4-推荐
     */
    @Column(name = "trigger_type")
    private Integer triggerType;

    @Column(name = "begin_time")
    private Long beginTime;

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
     * 用户投资使用时间
     */
    @Column(name = "user_use_time")
    private Long userUseTime;


    /**
     * 赠送值
     */
    @Column(name = "give_value")
    private BigDecimal giveValue;


    /**
     * 状态：1-未使用     2-已冻结   3-已使用   4-已过期   5-现金领取
     */
    private Integer status;

    private Long created;

    public WmUserRedPacket() {

    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public Integer getRedpacketType() {
        return redpacketType;
    }

    public void setRedpacketType(Integer redpacketType) {
        this.redpacketType = redpacketType;
    }

    public Integer getTriggerType() {
        return triggerType;
    }

    public void setTriggerType(Integer triggerType) {
        this.triggerType = triggerType;
    }

    public Integer getInvestDays() {
        return investDays;
    }

    public void setInvestDays(Integer investDays) {
        this.investDays = investDays;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }
}