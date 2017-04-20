package com.ydzb.product.entity;

import com.ydzb.core.entity.BaseEntity;
import com.ydzb.core.utils.DateUtil;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 定存宝每日支付收益
 */
@Entity
@Table(name = "wm_deposit_type_expire")
public class DepositTypeExpire extends BaseEntity<Long> {

    //产品类型 1、定存宝 2、自由定存 3、转转赚 4、美利金融 5、买单侠
    private Integer type;

    //到期总额
    private BigDecimal opfund;

    //到期日期
    private Long optime;

    @Transient
    private Date opDate;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public BigDecimal getOpfund() {
        return opfund;
    }

    public void setOpfund(BigDecimal opfund) {
        this.opfund = opfund;
    }

    public Long getOptime() {
        return optime;
    }

    public void setOptime(Long optime) {
        this.optime = optime;
    }

    public Date getOpDate() {
        return DateUtil.getSystemTimeMillisecond(optime);
    }


}
