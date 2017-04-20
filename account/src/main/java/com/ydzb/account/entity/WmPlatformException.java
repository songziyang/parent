package com.ydzb.account.entity;

import com.ydzb.core.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "wm_platform_exception")
public class WmPlatformException extends BaseEntity<Long> {

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Long userId;

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


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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