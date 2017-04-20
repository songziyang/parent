package com.ydzb.withdraw.base;

/**
 * 用户还款状态常量
 *
 * @author benhang
 */
public class Constance {

    public static final int VERIFY = 0;             // 审核中

    //public static final int VERIFY_SUCCESS = 1;   // 审核成功

    public static final int VERIFY_FAIL = 2;        // 审核失败

    public static final int PAYBACK = 3;            // 还款中

    public static final int PAYBACK_SUCCESS = 4;    // 还款成功

    public static final int PAYBACK_FAIL = 5;       // 还款失败

    public static final int PAYBACK_MINSHENG = 6;   // 民生受理中

    public static final int PAYBACK_MSSB = 7;       // 民生受理失败

}
