package com.ydzb.account.entity;

import com.ydzb.core.entity.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by sy on 2016/9/26.
 */
@Entity
@Table(name = "wm_activity_shiyi_winning")
@DynamicInsert
@DynamicUpdate
public class WmActivityGuoqingWinning extends BaseEntity<Long> {

    /**
     * 金福袋总
     */
    private Integer gold;

    /**
     * 金福袋剩余
     */
    @Column(name = "gold_surplus")
    private Integer goldSurplus;

    /**
     * 十一活动期间用户总投资
     */
    @Column(name = "shiyi_money")
    private Integer totalMoney;

    private Integer three;
    private Integer two;

    public Integer getGold() {
        return gold;
    }

    public void setGold(Integer gold) {
        this.gold = gold;
    }

    public Integer getGoldSurplus() {
        return goldSurplus;
    }

    public void setGoldSurplus(Integer goldSurplus) {
        this.goldSurplus = goldSurplus;
    }

    public Integer getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Integer totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Integer getThree() {
        return three;
    }

    public void setThree(Integer three) {
        this.three = three;
    }

    public Integer getTwo() {
        return two;
    }

    public void setTwo(Integer two) {
        this.two = two;
    }
}