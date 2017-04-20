package com.ydzb.account.context;

import java.math.BigInteger;

public class IDRange {

    private BigInteger maxId;

    private BigInteger minId;

    public IDRange() {
    }

    public IDRange(BigInteger maxId, BigInteger minId) {
        super();
        this.maxId = maxId;
        this.minId = minId;
    }

    public long getMaxId() {
        if (maxId != null) {
            return maxId.longValue();
        }
        return 0;
    }

    public void setMaxId(BigInteger maxId) {
        this.maxId = maxId;
    }

    public long getMinId() {
        if (minId != null) {
            return minId.longValue();
        }
        return 0;
    }

    public void setMinId(BigInteger minId) {
        this.minId = minId;
    }

}
