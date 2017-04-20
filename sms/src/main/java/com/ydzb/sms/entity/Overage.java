package com.ydzb.sms.entity;

public class Overage {

    private int overage;          //余额
    private int sendTotal;        //发送总数
    private int useatio;            //比例（页面空间不够，只能取整数）
    private int surplusatio;


    public Overage() {
        super();
    }

    public int getOverage() {
        return overage;
    }

    public void setOverage(int overage) {
        this.overage = overage;
    }

    public int getSendTotal() {
        return sendTotal;
    }

    public void setSendTotal(int sendTotal) {
        this.sendTotal = sendTotal;
    }

    public int getUseatio() {
      return 100 - (int)((float)overage / (float)sendTotal*100);
    }

    public void setUseatio(int useatio) {
        this.useatio = useatio;
    }

    public int getSurplusatio() {
        return (int)((float)overage / (float)sendTotal*100);
    }

    public void setSurplusatio(int surplusatio) {
        this.surplusatio = surplusatio;
    }
}
