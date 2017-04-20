package com.ydzb.user.entity;


import com.ydzb.core.entity.BaseEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "wm_minus_amount")
public class MinusAmount extends BaseEntity<Long> {

	private Integer fund;

	private Long created;


	@Column(name = "create_user")
	private String createUser;

	@Column(name = "update_user")
	private String updateUser;


	public Integer getFund() {
		return fund;
	}

	public void setFund(Integer fund) {
		this.fund = fund;
	}

	public Long getCreated() {
		return created;
	}

	public void setCreated(Long created) {
		this.created = created;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
}
