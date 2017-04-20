package com.ydzb.account.entity;

import com.ydzb.core.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;


@Entity
@Table(name = "wm_vip_set_record")
public class WmVipSetGrade extends BaseEntity<Long> {

    //用户id
    @Column(name = "user_id")
    private Long userId;

    //vip等级id
    @Column(name = "vip_id")
    private Long vipGradeId;

    //到期时间
    @Column(name = "expire_time")
    private Long expireTime;

    //状态 1-未到期 2-已到期 3-已作废
    private Integer status;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getVipGradeId() {
        return vipGradeId;
    }

    public void setVipGradeId(Long vipGradeId) {
        this.vipGradeId = vipGradeId;
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
}
