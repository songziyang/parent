package com.ydzb.product.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.core.utils.ExcelUtil;
import com.ydzb.core.utils.POIUtil;
import com.ydzb.product.entity.CurrentTradeRecored;
import com.ydzb.product.entity.PlatformTrading;
import com.ydzb.product.repository.IPlatformTradingRepository;
import com.ydzb.product.repository.PlatformTradingRepository;
import com.ydzb.product.service.IPlatformTradingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 平台交易统计service实现类
 * @author sy
 */
@Service
public class PlatformTradingServiceImpl extends BaseServiceImpl<PlatformTrading, Long> implements IPlatformTradingService {

    @Autowired
    private IPlatformTradingRepository platformTradingRepository;
    @Autowired
    private PlatformTradingRepository tradingRepository;

    /**
     * 查询图标所需的七天数据
     * @return
     */
    @Override
    public Map<String, String[]> queryDates() {
        Map<String, String[]> data = new HashMap<String, String[]>();
        String[] sevenDays = getSevenDays();
        Long[] sevenDaysLong = getSevenDaysLong(sevenDays);

        String[] recharges = getRecharges(sevenDaysLong);
        String[] withdraws = getWithdraws(sevenDaysLong);
        String[] currents = getCurrents(sevenDaysLong);
        String[] regulars = getRegulars(sevenDaysLong);
        String[] institutions = getInstitutions(sevenDaysLong);
        String[] privateOrderings = getPrivateOrderings(sevenDaysLong);
        String[] frees = getFrees(sevenDaysLong);

        data.put("dates", sevenDays);
        data.put("recharges", recharges);
        data.put("withdraws", withdraws);
        data.put("currents", currents);
        data.put("regulars", regulars);
        data.put("institutions", institutions);
        data.put("privateOrderings", privateOrderings);
        data.put("frees", frees);
        return data;
    }


    @Override
    public String exportExcel(List<Object[]> tradings, String address) {
        String fileName = "";
        try {
            String[] names = {"金额", "类型", "操作日期"};
            String[] keys = {"fund", "type", "opdate"};
            List<Map<String, Object>> headInfoList = ExcelUtil.setHeadInfo(names, keys);
            List<Map<String, Object>> dataList = setData(keys, tradings);
            fileName = "/platformtrading" + DateUtil.getSystemTimeSeconds() + ".xls";
            String filePath = address + fileName;
            POIUtil.exportExcel2FilePath("活期宝交易", filePath, headInfoList, dataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }

    @Override
    public List<Object[]> findExportData(Map<String, Object> filter) {
        return tradingRepository.findExportData(filter);
    }

    /**
     * 设置数据
     * @param keys
     * @param tradings
     * @return
     */
    private List<Map<String, Object>> setData(String[] keys, List<Object[]> tradings) {
        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
        Map<String, Object> dataItem = null;
        for (Object[] row: tradings) {
            if (row == null) {
                continue;
            }
            dataItem = new HashMap<String, Object>();
            dataItem.put(keys[0], row[0] == null? BigDecimal.ZERO: row[0]);
            dataItem.put(keys[1], row[1] == null? "": getType((Byte) row[1]));
            dataItem.put(keys[2], row[2] == null? "": new SimpleDateFormat("yyyy-MM-dd").format(DateUtil.getSystemTimeMillisecond(((Integer)row[2]).longValue())));
            dataList.add(dataItem);
        }
        return dataList;
    }

    /**
     * 获得类型
     * @param type
     * @return
     */
    private String getType(Byte type) {
        if (type == null) {
            return "";
        }
        switch (type) {
            case 1: return "充值";
            case 2: return "提现";
            case 3: return "活期宝";
            case 4: return "定存宝";
            case 5: return "机构宝";
            case 6: return "私人订制";
            case 7: return "随心存";
        }
        return "";
    }



    /**
     * 根据平台交易记录类型、操作起始时间获得总金额
     * @param type
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public BigDecimal findSumFund(Byte type, String startDate, String endDate) {
        return tradingRepository.findSumFund(type, startDate, endDate);
    }


    /**
     * 获得前七天时间
     * @return
     */
    private String[] getSevenDays() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate  = dateFormat.format(new Date());
        String[] sevenDays = new String[7];
        //不包含今天的前7天
        for (int i = 0; i < 7; i++) {
            Long currentLong = DateUtil.getSystemTimeDay(currentDate) - ((i + 1) * 24 * 3600);
            sevenDays[i] = dateFormat.format(DateUtil.getSystemTimeMillisecond(currentLong));
        }
        Arrays.sort(sevenDays);
        return sevenDays;
    }

    /**
     * 获得前七天时间毫秒数
     * @param sevenDays
     * @return
     */
    private Long[] getSevenDaysLong(String[] sevenDays) {
        Long[] sevenDaysLong = new Long[7];
        for (int i = 0; i < sevenDays.length; i ++) {
            sevenDaysLong[i] = DateUtil.getSystemTimeDay(sevenDays[i]);
        }
        return sevenDaysLong;
    }

    /**
     * 充值
     * @param sevenDaysLong
     * @return
     */
    private String[] getRecharges(Long[] sevenDaysLong) {
        DecimalFormat df = new DecimalFormat("#.00");
        String[] rechargeStrs = new String[sevenDaysLong.length];
        List<PlatformTrading> recharges = platformTradingRepository.findOnes(PlatformTrading.RECHARGE,
                sevenDaysLong[0], sevenDaysLong[sevenDaysLong.length - 1]);
        for (int i = 0; i < rechargeStrs.length; i ++) {
            rechargeStrs[i] = df.format(BigDecimal.ZERO);
            for (PlatformTrading trading: recharges) {
                if (sevenDaysLong[i].equals(trading.getOperationTime())) {
                    //金额累加
                    rechargeStrs[i] = df.format(trading.getFund().add(new BigDecimal(rechargeStrs[i])));
                }
            }
        }
        return rechargeStrs;
    }

    /**
     * 提现
     * @param sevenDaysLong
     * @return
     */
    private String[] getWithdraws(Long[] sevenDaysLong) {
        DecimalFormat df = new DecimalFormat("#.00");
        String[] withdrawStrs = new String[sevenDaysLong.length];
        List<PlatformTrading> withdraws = platformTradingRepository.findOnes(PlatformTrading.WITHDRAW,
                sevenDaysLong[0], sevenDaysLong[sevenDaysLong.length - 1]);
        for (int i = 0; i < withdrawStrs.length; i ++) {
            withdrawStrs[i] = df.format(BigDecimal.ZERO);
            for (PlatformTrading trading: withdraws) {
                if (sevenDaysLong[i].equals(trading.getOperationTime())) {
                    //金额累加
                    withdrawStrs[i] = df.format(trading.getFund().add(new BigDecimal(withdrawStrs[i])));
                }
            }
        }
        return withdrawStrs;
    }

    /**
     * 活期宝
     * @param sevenDaysLong
     * @return
     */
    private String[] getCurrents(Long[] sevenDaysLong) {
        DecimalFormat df = new DecimalFormat("#.00");
        String[] currentStrs = new String[sevenDaysLong.length];
        List<PlatformTrading> currents = platformTradingRepository.findOnes(PlatformTrading.CURRENT,
                sevenDaysLong[0], sevenDaysLong[sevenDaysLong.length - 1]);
        for (int i = 0; i < currentStrs.length; i ++) {
            currentStrs[i] = df.format(BigDecimal.ZERO);
            for (PlatformTrading trading: currents) {
                if (sevenDaysLong[i].equals(trading.getOperationTime())) {
                    //金额累加
                    currentStrs[i] = df.format(trading.getFund().add(new BigDecimal(currentStrs[i])));
                }
            }
        }
        return currentStrs;
    }

    /**
     * 定存宝
     * @param sevenDaysLong
     * @return
     */
    private String[] getRegulars(Long[] sevenDaysLong) {
        DecimalFormat df = new DecimalFormat("#.00");
        String[] regularStrs = new String[sevenDaysLong.length];
        List<PlatformTrading> regulars = platformTradingRepository.findOnes(PlatformTrading.REGULAR,
                sevenDaysLong[0], sevenDaysLong[sevenDaysLong.length - 1]);
        for (int i = 0; i < regularStrs.length; i ++) {
            regularStrs[i] = df.format(BigDecimal.ZERO);
            for (PlatformTrading trading: regulars) {
                if (sevenDaysLong[i].equals(trading.getOperationTime())) {
                    //金额累加
                    regularStrs[i] = df.format(trading.getFund().add(new BigDecimal(regularStrs[i])));
                }
            }
        }
        return regularStrs;
    }

    /**
     * 机构宝
     * @param sevenDaysLong
     * @return
     */
    private String[] getInstitutions(Long[] sevenDaysLong) {
        DecimalFormat df = new DecimalFormat("#.00");
        String[] insitutionStrs = new String[sevenDaysLong.length];
        List<PlatformTrading> institutions = platformTradingRepository.findOnes(PlatformTrading.INSTITUTION,
                sevenDaysLong[0], sevenDaysLong[sevenDaysLong.length - 1]);
        for (int i = 0; i < insitutionStrs.length; i ++) {
            insitutionStrs[i] = df.format(BigDecimal.ZERO);
            for (PlatformTrading trading: institutions) {
                if (sevenDaysLong[i].equals(trading.getOperationTime())) {
                    //金额累加
                    insitutionStrs[i] = df.format(trading.getFund().add(new BigDecimal(insitutionStrs[i])));
                }
            }
        }
        return insitutionStrs;
    }

    /**
     * 私人订制
     * @param sevenDaysLong
     * @return
     */
    private String[] getPrivateOrderings(Long[] sevenDaysLong) {
        DecimalFormat df = new DecimalFormat("#.00");
        String[] privateOrderingStrs = new String[sevenDaysLong.length];
        List<PlatformTrading> privateOerdings = platformTradingRepository.findOnes(PlatformTrading.PRIVATE_ORDERING,
                sevenDaysLong[0], sevenDaysLong[sevenDaysLong.length - 1]);
        for (int i = 0; i < privateOrderingStrs.length; i ++) {
            privateOrderingStrs[i] = df.format(BigDecimal.ZERO);
            for (PlatformTrading trading: privateOerdings) {
                if (sevenDaysLong[i].equals(trading.getOperationTime())) {
                    //金额累加
                    privateOrderingStrs[i] = df.format(trading.getFund().add(new BigDecimal(privateOrderingStrs[i])));
                }
            }
        }
        return privateOrderingStrs;
    }

    /**
     * 随心存
     * @param sevenDaysLong
     * @return
     */
    private String[] getFrees(Long[] sevenDaysLong) {
        DecimalFormat df = new DecimalFormat("#.00");
        String[] freeStrs = new String[sevenDaysLong.length];
        List<PlatformTrading> frees = platformTradingRepository.findOnes(PlatformTrading.FREE,
                sevenDaysLong[0], sevenDaysLong[sevenDaysLong.length - 1]);
        for (int i = 0; i < freeStrs.length; i ++) {
            freeStrs[i] = df.format(BigDecimal.ZERO);
            for (PlatformTrading trading: frees) {
                if (sevenDaysLong[i].equals(trading.getOperationTime())) {
                    //金额累加
                    freeStrs[i] = df.format(trading.getFund().add(new BigDecimal(freeStrs[i])));
                }
            }
        }
        return freeStrs;
    }


}