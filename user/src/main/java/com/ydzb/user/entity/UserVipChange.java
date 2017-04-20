package com.ydzb.user.entity;

import com.ydzb.core.entity.BaseEntity;
import com.ydzb.core.utils.DateUtil;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户vip变化
 */
@Entity
@Table(name = "wm_user_vip_change")
@DynamicInsert
@DynamicUpdate
public class UserVipChange extends BaseEntity<Long> {


    public static final int CTYPE_LEVELUP = 1;  //改变类型-用户升级
    public static final int CTYPE_LEVELDOWN = 2;    //改变类型-用户降级
    public static final int OTYPE_INVEST = 1;   //操作类型-投资
    public static final int OTYPE_MANUAL = 2;   //操作类型-手动

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private User user;

    /**
     * 原用户等级
     */
    @ManyToOne
    @JoinColumn(name = "old_grade_id")
    private VipGrade oldGrade;

    /**
     * 现用户等级
     */
    @ManyToOne
    @JoinColumn(name = "new_grade_id")
    private VipGrade newGrade;

    @Column(name = "c_type")
    private Integer cType;  //更改类型

    @Column(name = "o_type")
    private Integer oType;  //操作类型
    private Long optime;    //操作日期

    @Transient
    private Date opdate;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getcType() {
        return cType;
    }

    public void setcType(Integer cType) {
        this.cType = cType;
    }

    public Long getOptime() {
        return optime;
    }

    public void setOptime(Long optime) {
        this.optime = optime;
    }

    public Date getOpdate() {
        return DateUtil.getSystemTimeMillisecond(optime);
    }

    public VipGrade getOldGrade() {
        return oldGrade;
    }

    public void setOldGrade(VipGrade oldGrade) {
        this.oldGrade = oldGrade;
    }

    public VipGrade getNewGrade() {
        return newGrade;
    }

    public void setNewGrade(VipGrade newGrade) {
        this.newGrade = newGrade;
    }

    public Integer getoType() {
        return oType;
    }

    public void setoType(Integer oType) {
        this.oType = oType;
    }
}