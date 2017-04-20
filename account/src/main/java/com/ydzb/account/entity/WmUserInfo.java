package com.ydzb.account.entity;


import com.ydzb.core.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "wm_user_info")
public class WmUserInfo extends BaseEntity<Long> {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "is_send_vip")
    private Integer sendVip;

    public WmUserInfo() {

    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getSendVip() {
        return sendVip;
    }

    public void setSendVip(Integer sendVip) {
        this.sendVip = sendVip;
    }
}
