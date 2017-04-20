package com.ydzb.admin.entity;

import com.ydzb.core.entity.BaseEntity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "wm_sys_menu")
public class Menu extends BaseEntity<Long> {

	private static final long serialVersionUID = 2657549040034273781L;

	@ManyToOne
	@JoinColumn(name = "pid")
	private Menu parentMenu;

	private String name;

	private String url;

	private String purflag;

	private Long created;

	@Transient
	private Date createDate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPurflag() {
		return purflag;
	}

	public void setPurflag(String purflag) {
		this.purflag = purflag;
	}

	public Long getCreated() {
		return created;
	}

	public void setCreated(Long created) {
		this.created = created;
	}

	public Menu getParentMenu() {
		return parentMenu;
	}

	public void setParentMenu(Menu parentMenu) {
		this.parentMenu = parentMenu;
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
