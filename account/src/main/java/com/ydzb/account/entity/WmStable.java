package com.ydzb.account.entity;

import com.ydzb.core.entity.BaseEntity;
import com.ydzb.core.utils.DateUtil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 稳进宝实体
 */
@Entity
@Table(name = "wm_stable")
public class WmStable extends BaseEntity<Long> implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final byte STATE_APPLYING = 1;	//申购中状态
	public static final byte STATE_FULLSTANDARD = 2;	//已满标状态
	public static final byte STATE_END = 3;		//已结束状态
	
	//项目名称
	@Column
	private String name;
	
	//开放份数
	@Column
	private Integer copies;
	
	//剩余份数
	@Column(name = "remaining_copies")
	private Integer remainingCopies;
	
	//最小份额
	@Column(name = "min_copies")
	private Integer minCopies;
	
	//最大份额
	@Column(name = "max_copies")
	private Integer maxCopies;
	
	//申购开始日期毫秒数
	@Column(name = "start_date")
	private Long startTime;
	
	//申购结束日期毫秒数
	@Column(name = "end_date")
	private Long endTime;
	
	//利润百分比
	@Column
	private BigDecimal apr;
	
	//状态 1、申购中；2、已满标；3、已结束
	@Column
	private Byte state;
	
	//满标日期毫秒数
	@Column(name = "full_date")
	private Long fullTime;
		
	//到期日期毫秒数
	@Column(name = "close_date")
	private Long closeTime;
	
	//创建时间
	@Column
	private Long created;
	
	//创建人
	@Column(name = "create_user")
	private Long createUser;
	
	//最后修改人
	@Column
	private Long updateuser;

	private Integer type;

	private Integer days;
	
	//申购开始日期
	@Transient
	private Date startDate;
	
	//申购结束日期
	@Transient
	private Date endDate;
	
	//满标日期
	@Transient
	private Date fullDate;
	
	//到期日期
	@Transient
	private Date closeDate;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCopies() {
		return copies;
	}

	public void setCopies(Integer copies) {
		this.copies = copies;
	}

	public Integer getRemainingCopies() {
		return remainingCopies;
	}

	public void setRemainingCopies(Integer remainingCopies) {
		this.remainingCopies = remainingCopies;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public BigDecimal getApr() {
		return apr;
	}

	public void setApr(BigDecimal apr) {
		this.apr = apr;
	}

	public Byte getState() {
		return state;
	}

	public void setState(Byte state) {
		this.state = state;
	}

	public Long getCreated() {
		return created;
	}

	public void setCreated(Long created) {
		this.created = created;
	}

	public Long getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}

	public Long getUpdateuser() {
		return updateuser;
	}

	public void setUpdateuser(Long updateuser) {
		this.updateuser = updateuser;
	}

	public Date getStartDate() {
		return DateUtil.getSystemTimeMillisecond(startTime);
	}

	public Date getEndDate() {
		return DateUtil.getSystemTimeMillisecond(endTime);
	}

	public Long getFullTime() {
		return fullTime;
	}

	public void setFullTime(Long fullTime) {
		this.fullTime = fullTime;
	}

	public Long getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(Long closeTime) {
		this.closeTime = closeTime;
	}

	public Date getFullDate() {
		return DateUtil.getSystemTimeMillisecond(fullTime);
	}

	public Date getCloseDate() {
		return DateUtil.getSystemTimeMillisecond(closeTime);
	}

	public Integer getMinCopies() {
		return minCopies;
	}

	public void setMinCopies(Integer minCopies) {
		this.minCopies = minCopies;
	}

	public Integer getMaxCopies() {
		return maxCopies;
	}

	public void setMaxCopies(Integer maxCopies) {
		this.maxCopies = maxCopies;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}
}