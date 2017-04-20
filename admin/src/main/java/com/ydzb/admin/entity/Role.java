package com.ydzb.admin.entity;

import com.ydzb.core.entity.BaseEntity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;


//角色表 实体
@Entity
@Table(name = "wm_sys_role")
public class Role extends BaseEntity<Long> {

	private static final long serialVersionUID = -4834016524687657290L;

	private String name;

	private String remark;

	private Long created;
	
	private String purviews;
	
	@Transient
	private Date createDate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getCreated() {
		return created;
	}

	public void setCreated(Long created) {
		this.created = created;
	}

	public Date getCreateDate() {
		if (created !=null && created >0) {
			return new Date(created);
		}
		return new Date();
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getPurviews() {
		return purviews;
	}

	public void setPurviews(String purviews) {
		this.purviews = purviews;
	}

}
