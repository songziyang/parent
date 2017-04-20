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
 * 随心存产品记录
 */
@Entity
@Table(name = "wm_free_user_account")
@DynamicInsert
@DynamicUpdate
public class FreeUserAccount extends BaseEntity<Long> {

    public static final byte NOT_EXPIRE = 0;    //未到期
    public static final byte EXPIRE = 1;    //已到期

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private ProductInfo productInfo;

    @Column(name = "buy_fund")
    private BigDecimal buyFund;

    //利息收益
    @Column(name = "interest_fund")
    private BigDecimal interestFund;

    //加息利率
    @Column(name = "grand_apr")
    private BigDecimal grandApr;

    private Integer days;

    private BigDecimal apr;

    //VIP加息利率
    @Column(name = "vip_apr")
    private BigDecimal vipApr;

    //到期日期
    @Column(name = "expire_time")
    private Long expireTime;

    //到期是否复投 0 不复投 1 复投
    @Column(name = "expire_mode")
    private Integer expireMode;

    //0未到期,1已到期,2转让中,3转让成功
    private Integer status;

    //购买时间
    @Column(name = "buy_time")
    private Long buyTime;

    //购买类型 0 、正常 1、复投
    @Column(name = "buy_type")
    private Integer buyType;

    //转让次数  0=未转让过
    @Column(name = "trans_count")
    private Integer transCount;

    //预期收益
    @Column(name = "predict_income")
    private BigDecimal predictIncome;

    private Long created;

    @Transient
    private Date applyTime;

    @Transient
    private Date expireDime;

    //满标日期
    @Transient
    private Date creatDate;

    //到期日期
    @Transient
    private Date closeDate;

    /**
     * 已还金额
     */
    private BigDecimal refundable;
//
//    @OneToMany(targetEntity = RagularRefund.class, fetch = FetchType.LAZY)
//    @JoinColumn(name = "account_id")
//    private Set<RagularRefund> ragularRefund;

    public Date getCreatDate() {
        return DateUtil.getSystemTimeMillisecond(buyTime);
    }

    public Date getCloseDate() {
        return DateUtil.getSystemTimeMillisecond(expireTime);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ProductInfo getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(ProductInfo productInfo) {
        this.productInfo = productInfo;
    }

    public BigDecimal getBuyFund() {
        return buyFund;
    }

    public void setBuyFund(BigDecimal buyFund) {
        this.buyFund = buyFund;
    }

    public BigDecimal getInterestFund() {
        return interestFund;
    }

    public void setInterestFund(BigDecimal interestFund) {
        this.interestFund = interestFund;
    }

    public BigDecimal getGrandApr() {
        return grandApr;
    }

    public void setGrandApr(BigDecimal grandApr) {
        this.grandApr = grandApr;
    }

    public BigDecimal getApr() {
        return apr;
    }

    public void setApr(BigDecimal apr) {
        this.apr = apr;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(Long buyTime) {
        this.buyTime = buyTime;
    }

    public BigDecimal getVipApr() {
        return vipApr;
    }

    public void setVipApr(BigDecimal vipApr) {
        this.vipApr = vipApr;
    }

    public Integer getExpireMode() {
        return expireMode;
    }

    public void setExpireMode(Integer expireMode) {
        this.expireMode = expireMode;
    }

    public Integer getBuyType() {
        return buyType;
    }

    public void setBuyType(Integer buyType) {
        this.buyType = buyType;
    }

    public Integer getTransCount() {
        return transCount;
    }

    public void setTransCount(Integer transCount) {
        this.transCount = transCount;
    }

    public BigDecimal getPredictIncome() {
        return predictIncome;
    }

    public void setPredictIncome(BigDecimal predictIncome) {
        this.predictIncome = predictIncome;
    }

    public Long getCreated() {
        return created;
    }

    public Date getExpireDime() {
        return DateUtil.getSystemTimeMillisecond(expireTime);
    }

    public Date getBuyDime() {
        return DateUtil.getSystemTimeMillisecond(buyTime);
    }
//
//    public Set<RagularRefund> getRagularRefund() {
//        return ragularRefund;
//    }
//
//    public void setRagularRefund(Set<RagularRefund> ragularRefund) {
//        this.ragularRefund = ragularRefund;
//    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public BigDecimal getRefundable() {
        return refundable;
    }

    public void setRefundable(BigDecimal refundable) {
        this.refundable = refundable;
    }
}