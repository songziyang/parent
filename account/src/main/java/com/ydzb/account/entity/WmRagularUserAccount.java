package com.ydzb.account.entity;


import com.ydzb.core.entity.BaseEntity;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "wm_ragular_user_account")
@DynamicUpdate
public class WmRagularUserAccount extends BaseEntity<Long> implements IWmPeriodicProductAccount {

    public static final int STATUS_NOTEXPIRE = 0; //状态-未到期
    public static final int STATUS_TRANSFERING = 2; //状态-转让中
    public static final byte TRANSFER_SUCCESS = 3;  //转让成功
    public static final int STATUS_EXPIRE_REFUNDING = 4;  //到期还款中
    public static final int STATUS_PRINCIPAL_EXPIRING = 6;  //本金复投中
    public static final int STATUS_PRINCIAPAL_INTEREST_EXPIRING = 7;  //本息复投中
    public static final int STATUS_EXPIRE_EXPIRE = 1;  //已到期
    private static final long serialVersionUID = 1L;

    public static final int EXPIREMODE_NONE = 0;    //复投模式-不复投
    public static final int EXPIREMODE_PRINCIPAL = 1;   //复投模式-本金
    public static final int EXPIREMODE_PRINCIPAL_INTEREST = 2;  //复投模式-本息
    public static final int INCOMEMODE_NONE = 0;    //利息复投模式-不复投
    public static final int INCOMEMODE_EXPIRE = 1;  //利息复投模式-复投
    public static final int BUYTYPE_NORMAL = 0; //购买模式-正常购买
    public static final int BUYTYPE_EXPIRE = 1; //购买模式-复投

    //用户id
    @Column(name = "user_id")
    private Long userId;

    //产品ID
    @Column(name = "product_id")
    private Long productId;

    //到期本息总额
    @Column(name = "all_fund")
    private BigDecimal allFund;

    //购买时间
    @Column(name = "buy_time")
    private Long buyTime;

    //购买资金
    @Column(name = "buy_fund")
    private BigDecimal buyFund;

    //各种方式赠予的资金
    @Column(name = "grand_fund")
    private BigDecimal grandFund;

    //利息收益总金额
    @Column(name = "interest_fund")
    private BigDecimal interestFund;

    //购买份数
    @Column(name = "buy_copies")
    private Integer buyCopies;

    //加息利率
    @Column(name = "grand_apr")
    private BigDecimal grandApr;

    //年利率
    private BigDecimal apr;

    @Column(name = "vip_apr")
    private BigDecimal vipApr;

    //到期日期yyyy-mm-dd
    @Column(name = "expire_time")
    private Long expireTime;

    //0未到期,1已到期,2转让中,3转让成功
    private Integer status;


    //转让操作时间
    @Column(name = "apply_option_time")
    private Long applyOptionTime;

    //已还金额
    private BigDecimal refundable;

    //已还期数
    @Column(name = "refund_count")
    private Integer refundCount;

    //转让次数
    @Column(name = "trans_count")
    private Integer transCount;

    //预期收益
    @Column(name = "predict_income")
    private BigDecimal predictIncome;

    //到期是否复投
    @Column(name = "expire_mode")
    private Integer expireMode;

    //利息是否复投
    @Column(name = "income_mode")
    private Integer incomeMode;

    //购买类型
    @Column(name = "buy_type")
    private Integer buyType;

    private Integer days;

    //转让发起日期
    @Column(name = "apply_transfer_time")
    private Long applyTransferTime;

    @Column(name = "expire_num")
    private Integer expireNum;

    //复投购买当期产品ID
    @Column(name = "cur_product_id")
    private Long curProductId;

    public WmRagularUserAccount() {

    }

    /**
     * 是否是非复投复投模式
     * @return
     */
    public boolean isNoExpire() {
        return expireMode == EXPIREMODE_NONE;
    }

    /**
     * 是否是本金复投模式
     * @return
     */
    public boolean isPrincipalExpire() {
        return expireMode == EXPIREMODE_PRINCIPAL;
    }

    /**
     * 是否是本息复投模式
     * @return
     */
    public boolean isPrincipalInterestExpire() {
        return expireMode == EXPIREMODE_PRINCIPAL_INTEREST;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public BigDecimal getAllFund() {
        return allFund;
    }

    public void setAllFund(BigDecimal allFund) {
        this.allFund = allFund;
    }

    public Long getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(Long buyTime) {
        this.buyTime = buyTime;
    }

    public BigDecimal getBuyFund() {
        return buyFund;
    }

    public void setBuyFund(BigDecimal buyFund) {
        this.buyFund = buyFund;
    }

    public BigDecimal getGrandFund() {
        return grandFund;
    }

    public void setGrandFund(BigDecimal grandFund) {
        this.grandFund = grandFund;
    }

    public BigDecimal getInterestFund() {
        return interestFund;
    }

    public void setInterestFund(BigDecimal interestFund) {
        this.interestFund = interestFund;
    }

    public Integer getBuyCopies() {
        return buyCopies;
    }

    public void setBuyCopies(Integer buyCopies) {
        this.buyCopies = buyCopies;
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

    public BigDecimal getVipApr() {
        return vipApr;
    }

    public void setVipApr(BigDecimal vipApr) {
        this.vipApr = vipApr;
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

    public Long getApplyOptionTime() {
        return applyOptionTime;
    }

    public void setApplyOptionTime(Long applyOptionTime) {
        this.applyOptionTime = applyOptionTime;
    }

    public BigDecimal getRefundable() {
        return refundable;
    }

    public void setRefundable(BigDecimal refundable) {
        this.refundable = refundable;
    }

    public Integer getRefundCount() {
        return refundCount;
    }

    public void setRefundCount(Integer refundCount) {
        this.refundCount = refundCount;
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

    public Integer getExpireMode() {
        return expireMode;
    }

    public void setExpireMode(Integer expireMode) {
        this.expireMode = expireMode;
    }

    public Integer getIncomeMode() {
        return incomeMode;
    }

    public void setIncomeMode(Integer incomeMode) {
        this.incomeMode = incomeMode;
    }

    public Integer getBuyType() {
        return buyType;
    }

    public void setBuyType(Integer buyType) {
        this.buyType = buyType;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }


    public Long getApplyTransferTime() {
        return applyTransferTime;
    }

    public void setApplyTransferTime(Long applyTransferTime) {
        this.applyTransferTime = applyTransferTime;
    }

    public Integer getExpireNum() {
        return expireNum;
    }

    public void setExpireNum(Integer expireNum) {
        this.expireNum = expireNum;
    }

    public Long getCurProductId() {
        return curProductId;
    }

    public void setCurProductId(Long curProductId) {
        this.curProductId = curProductId;
    }
}