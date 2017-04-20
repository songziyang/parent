package com.ydzb.product.entity;

import com.ydzb.core.entity.BaseEntity;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.user.entity.User;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 定存宝购买日志
 * @author sy
 */
@Entity
@Table(name = "wm_ragular_buylog")
public class RegularBuyLog extends BaseEntity<Long> {

    public static final byte MOBILE = 1;    //移动
    public static final byte WEBSITE = 2;   //网站
    public static final byte WECHAT = 3;    //微信
    public static final byte SYSTEM = 4;    //系统

    /**
     * 产品
     */
    @ManyToOne
    @JoinColumn(name = "product_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private ProductInfo productInfo;

    /**
     *
     */
    @ManyToOne
    @JoinColumn(name = "account_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private RagularUserAccount account;

    /**
     * 用户
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private User user;

    /**
     * 购买金额
     */
    @Column(name = "buy_fund")
    private BigDecimal buyFund;

    /**
     * 购买份数
     */
    @Column(name = "buy_copies")
    private Integer buyCopies;

    /**
     * 利率
     */
    private BigDecimal apr;

    /**
     * 加息利率
     */
    @Column(name = "grand_apr")
    private BigDecimal grandApr;

    /**
     * vip加息利率
     */
    @Column(name = "vip_apr")
    private BigDecimal vipApr;

    /**
     * 购买时间
     */
    @Column(name = "buy_time")
    private Long buyTime;

    /**
     * 设备类型 1-移动    2-网站    3-微信    4-系统
     */
    private Byte device;

    @Transient
    private Date buyDate;

    public ProductInfo getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(ProductInfo productInfo) {
        this.productInfo = productInfo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getBuyFund() {
        return buyFund;
    }

    public void setBuyFund(BigDecimal buyFund) {
        this.buyFund = buyFund;
    }

    public Integer getBuyCopies() {
        return buyCopies;
    }

    public void setBuyCopies(Integer buyCopies) {
        this.buyCopies = buyCopies;
    }

    public BigDecimal getApr() {
        return apr;
    }

    public void setApr(BigDecimal apr) {
        this.apr = apr;
    }

    public BigDecimal getGrandApr() {
        return grandApr;
    }

    public void setGrandApr(BigDecimal grandApr) {
        this.grandApr = grandApr;
    }

    public BigDecimal getVipApr() {
        return vipApr;
    }

    public void setVipApr(BigDecimal vipApr) {
        this.vipApr = vipApr;
    }

    public Long getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(Long buyTime) {
        this.buyTime = buyTime;
    }

    public Byte getDevice() {
        return device;
    }

    public void setDevice(Byte device) {
        this.device = device;
    }

    public Date getBuyDate() {
        return DateUtil.getSystemTimeMillisecond(buyTime);
    }

    public RagularUserAccount getAccount() {
        return account;
    }

    public void setAccount(RagularUserAccount account) {
        this.account = account;
    }
}
