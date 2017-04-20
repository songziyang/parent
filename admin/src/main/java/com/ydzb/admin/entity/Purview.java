package com.ydzb.admin.entity;

import com.ydzb.core.entity.BaseEntity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "wm_sys_purview")
public class Purview extends BaseEntity<Long> {

	private static final long serialVersionUID = 8686925862286039254L;

	private String name;

	private String flag;

	private String remark;

	private Long created;

	@Transient
	private Date createDate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Long getCreated() {
		return created;
	}

	public void setCreated(Long created) {
		this.created = created;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateDate() {
		if (created != null && created > 0) {
			return new Date(created);
		}
		return new Date();
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
