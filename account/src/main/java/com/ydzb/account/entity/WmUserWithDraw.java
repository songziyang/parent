package com.ydzb.account.entity;

import com.ydzb.core.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "wm_user_withdraw")
public class WmUserWithDraw extends BaseEntity<Long> {

    //用户ID
    @Column(name = "user_id")
    private Long userId;

    //状态 0审核中,2审核失败,3待打款,4打款成功,5打款失败6、民生打款中 7、民生受理失败8、分批处理失败
    private Integer status;

    //0、手动处理 1、自动处理
    @Column(name = "draw_auto")
    private Integer drawAuto;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDrawAuto() {
        return drawAuto;
    }

    public void setDrawAuto(Integer drawAuto) {
        this.drawAuto = drawAuto;
    }
}
