package com.ydzb.product.entity;

import com.ydzb.core.entity.BaseEntity;
import com.ydzb.core.utils.DateUtil;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name = "wm_new_standard")
public class NewStandard extends BaseEntity<Long> {

    private Integer funds;

    private Integer surplus;

    private Integer status;

    private Long created;

    //创建时间
    @Transient
    private Date createDate;

    public Date getCreateDate() {
        return DateUtil.getSystemTimeMillisecond(created);
    }


    public Integer getFunds() {
        return funds;
    }

    public void setFunds(Integer funds) {
        this.funds = funds;
    }

    public Integer getSurplus() {
        return surplus;
    }

    public void setSurplus(Integer surplus) {
        this.surplus = surplus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }
}
