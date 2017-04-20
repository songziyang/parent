package com.ydzb.user.entity;


import com.ydzb.core.entity.BaseEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "wm_user_referral")
public class UserReferral extends BaseEntity<Long> {

	private static final long serialVersionUID = -8358888218477984298L;

	//用户
	@ManyToOne
	@JoinColumn(name = "user_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private User user;


	//被推荐用户
	@OneToOne
	@JoinColumn(name = "referral_user")
	@NotFound(action = NotFoundAction.IGNORE)
	private  User  referralUser;

	@Column(name = "referral_time")
	private Long referralTime;

	public UserReferral() {
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getReferralUser() {
		return referralUser;
	}

	public void setReferralUser(User referralUser) {
		this.referralUser = referralUser;
	}

	public Long getReferralTime() {
		return referralTime;
	}

	public void setReferralTime(Long referralTime) {
		this.referralTime = referralTime;
	}


}
