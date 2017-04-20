package com.ydzb.user.entity;


import com.ydzb.core.entity.BaseEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "wm_user_money")
public class UserMoney extends BaseEntity<Long> {

	private static final long serialVersionUID = -8358888218477984298L;

	//用户
	@OneToOne
	@JoinColumn(name = "user_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private User user;

	//账号总额
	@Column(name = "total_fund")
	private BigDecimal totalFund;

	//账号余额
	@Column(name = "usable_fund")
	private BigDecimal usableFund;

	//冻结金额
	@Column(name = "freeze_fund")
	private BigDecimal freezeFund;

	public UserMoney() {
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public BigDecimal getTotalFund() {
		return totalFund;
	}

	public void setTotalFund(BigDecimal totalFund) {
		this.totalFund = totalFund;
	}

	public BigDecimal getUsableFund() {
		return usableFund;
	}

	public void setUsableFund(BigDecimal usableFund) {
		this.usableFund = usableFund;
	}

	public BigDecimal getFreezeFund() {
		return freezeFund;
	}

	public void setFreezeFund(BigDecimal freezeFund) {
		this.freezeFund = freezeFund;
	}
}
