package com.ydzb.product.entity;

import com.ydzb.core.entity.BaseEntity;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.user.entity.User;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "wm_platform_exception")
public class PlatformException extends BaseEntity<Long> {

    /**
     * 用户ID
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * 异常的外链ID
     */
    @Column(name = "link_id")
    private Long linkId;

    /**
     * 类型 i 入账 o 出账
     */
    private String type;

    //创建时间
    private Long created;

    //统计日期
    @Transient
    private Date totalDate;

    public Date getTotalDate() {
        return DateUtil.getSystemTimeMillisecond(created);
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getLinkId() {
        return linkId;
    }

    public void setLinkId(Long linkId) {
        this.linkId = linkId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }
}