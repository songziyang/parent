package com.ydzb.withdraw.base;


import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class Banks {


    //所有7*24小时银行
    public static final List<String> AB = Arrays.asList("建设银行", "工商银行", "招商银行", "农业银行", "兴业银行", "中国银行", "民生银行", "中信银行", "光大银行", "交通银行", "平安银行", "浦发银行", "邮政储蓄银行", "广发银行", "华夏银行");

    //public static final List<String> F = Arrays.asList("建设银行", "工商银行", "招商银行", "农业银行", "兴业银行");

    //public static final List<String> S = Arrays.asList("中国银行", "民生银行", "中信银行", "光大银行", "交通银行", "平安银行", "浦发银行");

    public static final BigDecimal F_FUND = new BigDecimal(500000);

    //public static final BigDecimal S_FUND = new BigDecimal(100000);

    public static final BigDecimal O_FUND = new BigDecimal(49990);

    /**
     * 银行民称处理
     *
     * @param bankName 银行民称
     * @return 银行民称
     */
    public static String dealWithBankName(String bankName) {

        if (bankName != null) {

            if (bankName.contains("工商")) {
                return "工商银行";
            }

            if (bankName.contains("农业")) {
                return "农业银行";
            }

            if (bankName.contains("建设")) {
                return "建设银行";
            }

            if (bankName.contains("中国银行")) {
                return "中国银行";
            }

            if (bankName.contains("交通")) {
                return "交通银行";
            }

            if (bankName.contains("兴业")) {
                return "兴业银行";
            }

            if (bankName.contains("中信")) {
                return "中信银行";
            }

            if (bankName.contains("光大")) {
                return "光大银行";
            }

            if (bankName.contains("平安")) {
                return "平安银行";
            }

            if (bankName.contains("邮政")) {
                return "邮政储蓄银行";
            }

            if (bankName.contains("上海银行")) {
                return "上海银行";
            }

            if (bankName.contains("浦发") || bankName.contains("浦东发展")) {
                return "浦发银行";
            }

            if (bankName.contains("上海农商银行")) {
                return "上海农商银行";
            }

            if (bankName.contains("北京银行")) {
                return "北京银行";
            }

            if (bankName.contains("华夏银行")) {
                return "华夏银行";
            }

            if (bankName.contains("民生银行")) {
                return "民生银行";
            }

            if (bankName.contains("广发银行") || bankName.contains("广东发展")) {
                return "广发银行";
            }

            if (bankName.contains("招商")) {
                return "招商银行";
            }

            return bankName;
        }

        return bankName;
    }


}
