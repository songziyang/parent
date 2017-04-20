package com.ydzb.withdraw.entity;

public class ManualBalance {


    /**
     * 1:基本账户; 2:未结算账户; 3:冻结账户; 4:保证金账户; 5:资金托管账户;
     */
    private String balance1;

    private String balance2;

    private String balance3;

    private String balance4;

    private String balance5;

    public String getBalance1() {
        return balance1;
    }

    public void setBalance1(String balance1) {
        this.balance1 = balance1;
    }

    public String getBalance2() {
        return balance2;
    }

    public void setBalance2(String balance2) {
        this.balance2 = balance2;
    }

    public String getBalance3() {
        return balance3;
    }

    public void setBalance3(String balance3) {
        this.balance3 = balance3;
    }

    public String getBalance4() {
        return balance4;
    }

    public void setBalance4(String balance4) {
        this.balance4 = balance4;
    }

    public String getBalance5() {
        return balance5;
    }

    public void setBalance5(String balance5) {
        this.balance5 = balance5;
    }
}
