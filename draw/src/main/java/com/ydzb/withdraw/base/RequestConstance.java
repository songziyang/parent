package com.ydzb.withdraw.base;

public class RequestConstance {

    // 版本号
    public static final String V = "1.0.0";

    // 对于请求报文，type域值为0001
    public static final String MSGTYPE = "0001";

    // 交易渠道代号，由平台分配
    public static final String CHANNELNO = "99";

    // 商户号
    public static final String MERCHANTNO = "CF2000033391";

    //代付私钥
    //99a4ae1307973ec6fe2c04113a64c1c4
    public static final String KEY = "4fc8598c6df124ee31cd8c150f82e75a";

    //查询
    //9ca0e817ec949859a9b5fd750cad7e4d
    public static final String QUERY_KEY = "521de251220d457fea29c9180b6b6592";

    //查询的指定虚拟帐号
    public static final String ACCTNO = "00620000000010038298";

    //地址
    public static final String URL = "https://www.umbpay.cn:9380/agentCollPayPlatPre/msgProcess/acceptXmlReq.do";

}
