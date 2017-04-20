package com.baofoo.sdk.util;

public class TransConstant {

    public final static String data_type_json = "json";

    public final static String data_type_xml = "xml";

    /**
     * 商户号
     * 1、测试 100000178
     * 2、正式 739237
     */
    public final static String member_id = "739237";

    /**
     * 终端号
     * 1、测试 100000859
     * 2、正式 28531
     */
    public final static String terminal_id = "28531";

    /**
     * 版本
     */
    public final static String version = "4.0.0";

    /**
     * 私钥
     * 1、测试私钥 m_pri
     * 2、正式私钥 ydzb_pri
     */
    public final static String keyStorePath = "/alidata/server/web-tomcat/mycert/ydzb_pri.pfx";

    /**
     * 密码
     * 1、测试 123456
     * 2、正式 yinduowang
     */
    public final static String keyStorePassword = "yinduowang";

    /**
     * 公钥
     * 1、测试公钥 baofoo_pub
     * 2、正式公钥 ydzb_daifu
     */
    public final static String pub_key = "/alidata/server/web-tomcat/mycert/ydzb_daifu.cer";

    /**
     * 余额查询秘钥
     */
    public final static String KeyString = "w7rl7hbewdcclvfp";

    /**
     * 终端号
     */
    public final static String query_terminal_id = "28482";

    /**
     * 余额查询地址
     *
     * 1、测试 https://paytest.baofoo.com/open-service/query/service.do
     * 2、正式 https://public.baofoo.com/open-service/query/service.do
     */
    public final static String Bl_URL = "https://public.baofoo.com/open-service/query/service.do";

    /**
     * 代付接口
     * 1、测试 http://paytest.baofoo.com/baofoo-fopay/pay/BF0040001.do
     * 2、正式 https://public.baofoo.com/baofoo-fopay/pay/BF0040001.do
     */
    public final static String BF0040001 = "https://public-new.baofoo.com/baofoo-fopay/pay/BF0040001.do";


    /**
     * 代付查询接口
     * 1、测试 http://paytest.baofoo.com/baofoo-fopay/pay/BF0040002.do
     * 2、正式 https://public.baofoo.com/baofoo-fopay/pay/BF0040002.do
     */
    public final static String BF0040002 = "https://public-new.baofoo.com/baofoo-fopay/pay/BF0040002.do";

}
