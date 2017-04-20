package com.ydzb.account.entity;

import com.ydzb.core.entity.BaseEntity;
import com.ydzb.user.entity.User;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 清明节活动
 */
@Entity
@Table(name = "wm_activity_qingming")
@DynamicInsert
@DynamicUpdate
public class WmActivityQingming extends BaseEntity<Long> {

    public static final int NEWUSER = 1;    //新用户
    public static final int OLDUSER = 2;    //老用户

    public static final long ACITVITY_STARTTIME = 1459990800L;  //活动开始时间-2016年4月7日09:00
    public static final long ACTIVITY_ENDTIME = 1462615200L;    //活动结束时间-2016年5月7日18:00

    /**
     * 用户
     */
    @Column(name = "user_id")
    private Long userId;

    private Integer sign = 0;   //抽奖次数

    /**
     * 剩余抽奖次数
     */
    @Column(name = "surplus_sign")
    private Integer surplusSign = 0;

    /**
     * 最后一次抽奖时间
     */
    @Column(name = "last_sign")
    private Long lastSignTime;

    /**
     * 活动期间体验金收益
     */
    @Column(name = "exp_money_income")
    private BigDecimal expMoneyIncome = BigDecimal.ZERO;

    /**
     * 活动期间被推荐人活期收益
     */
    @Column(name = "dayloan_income")
    private BigDecimal dayloanIncome = BigDecimal.ZERO;

    /**
     * 最后一次增加时间
     */
    @Column(name = "last_add_sign")
    private Long lastAddSign;

    /**
     * 活动期间获得体验金总额
     */
    @Column(name = "total_expmoney")
    private BigDecimal totalExpmoney = BigDecimal.ZERO;

    /**
     * 是否是新用户 1-新用户 2-老用户
     */
    @Column(name = "new_user")
    private Integer isNewUser = OLDUSER;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getSign() {
        return sign;
    }

    public void setSign(Integer sign) {
        this.sign = sign;
    }

    public Integer getSurplusSign() {
        return surplusSign;
    }

    public void setSurplusSign(Integer surplusSign) {
        this.surplusSign = surplusSign;
    }

    public Long getLastSignTime() {
        return lastSignTime;
    }

    public void setLastSignTime(Long lastSignTime) {
        this.lastSignTime = lastSignTime;
    }

    public BigDecimal getExpMoneyIncome() {
        return expMoneyIncome;
    }

    public void setExpMoneyIncome(BigDecimal expMoneyIncome) {
        this.expMoneyIncome = expMoneyIncome;
    }

    public BigDecimal getDayloanIncome() {
        return dayloanIncome;
    }

    public void setDayloanIncome(BigDecimal dayloanIncome) {
        this.dayloanIncome = dayloanIncome;
    }

    public Long getLastAddSign() {
        return lastAddSign;
    }

    public void setLastAddSign(Long lastAddSign) {
        this.lastAddSign = lastAddSign;
    }

    public BigDecimal getTotalExpmoney() {
        return totalExpmoney;
    }

    public void setTotalExpmoney(BigDecimal totalExpmoney) {
        this.totalExpmoney = totalExpmoney;
    }

    public Integer getIsNewUser() {
        return isNewUser;
    }

    public void setIsNewUser(Integer isNewUser) {
        this.isNewUser = isNewUser;
    }
}