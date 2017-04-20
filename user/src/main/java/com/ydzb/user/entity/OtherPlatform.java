package com.ydzb.user.entity;

import com.ydzb.core.entity.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 其他平台用户
 */
@Entity
@Table(name = "wm_otherplatform")
@DynamicInsert
@DynamicUpdate
public class OtherPlatform extends BaseEntity<Long> {

    public OtherPlatform() {

    }

    public OtherPlatform(Long id) {
        setId(id);
    }

    private String name;    //名称

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}