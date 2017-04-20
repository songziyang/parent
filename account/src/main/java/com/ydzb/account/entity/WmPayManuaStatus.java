package com.ydzb.account.entity;

import com.ydzb.core.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "pay_manua_status")
public class WmPayManuaStatus extends BaseEntity<Long> {

    private static final long serialVersionUID = -1212649080087662412L;

    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
