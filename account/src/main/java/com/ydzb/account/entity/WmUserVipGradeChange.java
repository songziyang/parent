package com.ydzb.account.entity;

import com.ydzb.core.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "wm_user_vip_change")
public class WmUserVipGradeChange extends BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    //用户ID
    @Column(name = "user_id")
    private Long userId;

    //原用户等级ID
    @Column(name = "old_grade_id")
    private Long oldGradeId;

    //新用户等级id
    @Column(name = "new_grade_id")
    private Long newGradeId;

    //1、用户升级2、用户降级
    @Column(name = "c_type")
    private Integer cType;

    //操作类型 1、投资 2、手动修改
    @Column(name = "o_type")
    private Integer oType;

    //操作日期
    private Long optime;


    public WmUserVipGradeChange() {

    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOldGradeId() {
        return oldGradeId;
    }

    public void setOldGradeId(Long oldGradeId) {
        this.oldGradeId = oldGradeId;
    }

    public Long getNewGradeId() {
        return newGradeId;
    }

    public void setNewGradeId(Long newGradeId) {
        this.newGradeId = newGradeId;
    }

    public Integer getcType() {
        return cType;
    }

    public void setcType(Integer cType) {
        this.cType = cType;
    }

    public Integer getoType() {
        return oType;
    }

    public void setoType(Integer oType) {
        this.oType = oType;
    }

    public Long getOptime() {
        return optime;
    }

    public void setOptime(Long optime) {
        this.optime = optime;
    }
}
