package com.ydzb.product.enumeration;

/**
 * Created by sy on 2016/9/21.
 */
public enum PlatformRecordType {

    FUND_IN(1, "FUND_IN", "入账"),
    FUND_OUT(2, "FUND_OUT", "出账");

    PlatformRecordType(int value, String name, String desc) {
        this.value = value;
        this.name = name;
        this.desc = desc;
    }

    Integer value;
    String name;
    String desc;

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
