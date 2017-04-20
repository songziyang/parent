package com.ydzb.account.context;

/**
 * 链接类型
 */
public enum WmPlatformRecordLinkType {

    RECHARGE(1, "RECHARGE", "充值"),
    BUY_DEBT(2, "BUY_DEBT", "购买债权"),
    SELL_DEBT(3, "SELL_DEBT", "售出债权"),
    BUY_CURRENT(10, "BUY_CURRENT", "活期购买"),
    REDEEM_CURRENT(11, "REDEEM_CURRENT", "活期赎回"),
    BUY_RAGULAR(20, "BUY_RAGULAR", "定存购买"),
    EXPIRE_RAGULAR(21, "REDEEM_RAGULAR", "定存到期"),
    BUY_FREERAGULAR(30, "BUY_DEBT", "自由存购买"),
    EXPIRE_FREERAGULAR(31, "BUY_DEBT", "自由存到期"),
    BUY_MLJR(40, "BUY_MLJR", "美利金融购买"),
    EXPIRE_MLJR(41, "BUY_MLJR", "美利金融到期"),
    BUY_ZZZ(50, "BUY_MLJR", "转转赚购买"),
    EXPIRE_ZZZ(51, "BUY_MLJR", "转转赚到期");

    Integer value;
    String name;
    String desc;

    WmPlatformRecordLinkType(int value, String name, String desc) {
        this.value = value;
        this.name = name;
        this.desc = desc;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}