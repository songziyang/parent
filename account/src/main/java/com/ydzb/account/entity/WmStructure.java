package com.ydzb.account.entity;

import com.ydzb.core.entity.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * Created by sy on 2016/10/23.
 */
@Entity
@Table(name = "wm_structure")
@DynamicInsert
@DynamicUpdate
public class WmStructure extends BaseEntity<Long> {

    public static final int MAX_COPIES_DEFAULT = 50000; //默认普通用户购买最高额度
    public static final int VIP_MAX_COPIES_DEFAULT = 100000;    //默认vip用户购买最高额度
    public static final int MIN_COPIES_DEFAULT = 1000;  //默认最低购买额度
    public static final int DAYS_DEFAULT = 30;  //默认封闭日
    public static final BigDecimal APR_DEFAULT = new BigDecimal(8);    //默认利率
    public static final BigDecimal HELP_MAX_APR_DEFAULT = new BigDecimal(2);   //默认助力最高利率

    public static final int STATE_ALLOT = 1;	//申购中状态
    public static final int STATE_FULLSTANDARD = 2;	//已满标状态
    public static final int STATE_END = 3;		//已结束状态

    private String name;    //产品名称
    private Integer issue;  //产品期数
    private Integer copies; //开放份数
    private Integer days;   //锁定期
    private BigDecimal apr; //利率
    private Integer state;  //1-申购中 2-已满标 3-已结束
    private Long created;

    /**
     * 剩余份数
     */
    @Column(name = "remaining_copies")
    private Integer remainingCopies;
    /**
     * 申购开始日期
     */
    @Column(name = "start_date")
    private Long startDate;
    /**
     * 申购结束日期
     */
    @Column(name = "end_date")
    private Long endDate;
    /**
     * 助力加息限制
     */
    @Column(name = "help_max_apr")
    private BigDecimal helpMaxApr;
    /**
     * 最小份额
     */
    @Column(name = "min_copies")
    private Integer minCopies;
    /**
     * 普通用户最大限额
     */
    @Column(name = "max_copies")
    private Integer maxCopies;
    /**
     * VIP最大限额
     */
    @Column(name = "vip_max_copies")
    private Integer vipMaxCopies;
    /**
     * 满标日期
     */
    @Column(name = "full_date")
    private Long fullDate;
    /**
     * 到期日期
     */
    @Column(name = "close_date")
    private Long closeDate;
    /**
     * 创建人
     */
    @Column(name = "create_user")
    private String createUser;
    /**
     * 更新人
     */
    @Column(name = "updateuser")
    private String updateUser;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIssue() {
        return issue;
    }

    public void setIssue(Integer issue) {
        this.issue = issue;
    }

    public Integer getCopies() {
        return copies;
    }

    public void setCopies(Integer copies) {
        this.copies = copies;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public BigDecimal getApr() {
        return apr;
    }

    public void setApr(BigDecimal apr) {
        this.apr = apr;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Integer getRemainingCopies() {
        return remainingCopies;
    }

    public void setRemainingCopies(Integer remainingCopies) {
        this.remainingCopies = remainingCopies;
    }

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getHelpMaxApr() {
        return helpMaxApr;
    }

    public void setHelpMaxApr(BigDecimal helpMaxApr) {
        this.helpMaxApr = helpMaxApr;
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

    public Integer getVipMaxCopies() {
        return vipMaxCopies;
    }

    public void setVipMaxCopies(Integer vipMaxCopies) {
        this.vipMaxCopies = vipMaxCopies;
    }

    public Long getFullDate() {
        return fullDate;
    }

    public void setFullDate(Long fullDate) {
        this.fullDate = fullDate;
    }

    public Long getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Long closeDate) {
        this.closeDate = closeDate;
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
