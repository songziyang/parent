package com.ydzb.product.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.core.utils.POIUtil;
import com.ydzb.product.entity.RagularUserAccount;
import com.ydzb.product.entity.TopRagularUser;
import com.ydzb.product.repository.IRagularUserAccountRepository;
import com.ydzb.product.repository.RagularUserAccountRepository;
import com.ydzb.product.service.IRagularUserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class RagularUserAccountServiceImpl extends BaseServiceImpl<RagularUserAccount, Long> implements IRagularUserAccountService {

    @Autowired
    private IRagularUserAccountRepository ragularUserAccountRepository;
    @Autowired
    private RagularUserAccountRepository ragularAccountRepository;


    @Override
    public BigDecimal findTotalFund(Long startDate, Long endDate) {
        return ragularUserAccountRepository.findTotalFund(startDate, endDate);
    }

    @Override
    public String exportExcele(List<Object[]> ragularUserAccounts, String address) {
        String fileName = "";
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            List<Map<String, Object>> headInfoList = new ArrayList<Map<String, Object>>();

            Map<String, Object> itemMap = new HashMap<String, Object>();
            itemMap.put("title", "用户名");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "username");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "手机号");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "mobile");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "产品名称");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "productName");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "产品天数");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "productDays");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "购买份数");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "buyCopies");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "购买利率");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "apr");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "加息利率");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "grandApr");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "VIP利率");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "vipApr");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "总收益");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "revenue");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "未还收益");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "predictIncome");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "复投模式");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "expireMode");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "购买类型");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "buyType");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "交易状态");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "status");
            headInfoList.add(itemMap);


            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "购买时间");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "created");
            headInfoList.add(itemMap);


            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "到期日期");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "closingDate");
            headInfoList.add(itemMap);

            List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

            Map<String, Object> dataItem = null;
            for (Object[] row : ragularUserAccounts) {
                if (row == null) {
                    continue;
                }
                dataItem = new HashMap<String, Object>();
                dataItem.put("username", row[0] == null? "": row[0]);
                dataItem.put("mobile", row[1] == null? "": row[1]);
                dataItem.put("productName", row[2] == null? "": row[2]);
                dataItem.put("productDays", row[3] == null? "": row[3]);
                dataItem.put("buyCopies", row[4] == null? "": row[4]);
                dataItem.put("apr", row[5] == null? "": row[5]);
                dataItem.put("grandApr", row[6] == null? "": row[6]);
                dataItem.put("revenue", row[7] == null? "": row[7]);
                dataItem.put("status", getStatus((Integer)row[8]));
                dataItem.put("created", row[9] == null ? "" : format.format(DateUtil.getSystemTimeMillisecond(((Integer)row[9]).longValue())));
                dataItem.put("closingDate", row[10] == null? "": df.format(DateUtil.getSystemTimeMillisecond(((Integer)row[10]).longValue())));
                dataItem.put("vipApr", row[11] == null? "": row[11]);
                dataItem.put("predictIncome", row[12] == null? "": row[12]);
                dataItem.put("expireMode", row[13] == null? "": getExpireMode((Integer) row[13]));
                dataItem.put("buyType", row[14] == null? "": getBuyType((Integer) row[14]));


                dataList.add(dataItem);
            }

            fileName = "/ragular" + DateUtil.getSystemTimeSeconds() + ".xls";
            String filePath = address + fileName;

            POIUtil.exportExcel2FilePath("定存宝记录", filePath, headInfoList, dataList);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileName;
    }

    private String getBuyType(Integer buyType) {
        if (buyType == null) {
            return "";
        }
        if (buyType == 0) {
            return "正常";
        } else if (buyType == 1) {
            return "复投";
        }
        return "";
    }

    private String getExpireMode(Integer expireMode) {
        if (expireMode == null) {
            return "";
        }
        if (expireMode == 0) {
            return "不复投";
        } else if (expireMode == 1) {
            return "本金复投";
        }else if(expireMode == 2){
            return "本息复投";
        }
        return "";
    }

    private String getStatus(Integer status) {
        if (status == null) {
            return "";
        }
        switch (status) {
            case 0: return "未到期";
            case 1: return "已到期";
            case 2: return "转让中";
            case 3: return "转让成功";
            default: return "";
        }
    }

    /**
     * 根据定存产品类型(type)、到期状态(status)以及购买起始时间获得总购买金额
     * @param type
     * @param status
     * @param startTime
     * @param endTime
     * @return
     */
    public BigDecimal findTotalBuyFund(long[] type, Byte status, Long startTime, Long endTime, boolean mustExistTime) {
        return ragularAccountRepository.getBuyFund(type, status, startTime, endTime, mustExistTime);
    }

    /**
     * 根据定存产品类型(type)、到期状态(status)以及购买起始时间获得总购买金额
     * @param type
     * @param status
     * @param startTime
     * @param endTime
     * @return
     */
    public BigDecimal findTotalBuyFund(long type, Byte status, Long startTime, Long endTime, boolean mustExistTime) {
        return ragularAccountRepository.getBuyFund(type, status, startTime, endTime, mustExistTime);
    }

    /**
     * 查询某一个类型的定存交易记录累计购买金额
     * @param type
     * @return
     */
    @Override
    public BigDecimal findTotalBuyFund(long[] type) {
        return ragularAccountRepository.findTotalBuyFund(type);
    }

    /**
     * 查询某一个类型的定存交易记录累计购买金额
     * @param type
     * @return
     */
    @Override
    public BigDecimal findTotalBuyFund(Long type) {
        return ragularAccountRepository.findTotalBuyFund(type);
    }

    /**
     * 查询某一个类型(type)产品的交易笔数
     * @param type
     * @return
     */
    @Override
    public BigInteger getTotalTradeNum(long[] type) {
        return ragularAccountRepository.getTotalTradeNum(type);
    }

    /**
     * 查询某一个类型(type)产品的交易笔数
     * @param type
     * @return
     */
    @Override
    public BigInteger getTotalTradeNum(Long type) {
        return ragularAccountRepository.getTotalTradeNum(type);
    }

    /**
     * 根据定存类型(type)、到期状态(status)以及到期起始时间获得总到期(购买)金额
     * @param type
     * @param status
     * @param startTime
     * @param endTime
     * @param mustExistTime 是否必须存在起始时间
     * @return
     */
    @Override
    public BigDecimal getClosingFund(long[] type, Byte status, Long startTime, Long endTime, boolean mustExistTime) {
        return ragularAccountRepository.getClosingFund(type, status, startTime, endTime, mustExistTime);
    }

    /**
     * 根据定存类型(type)、到期状态(status)以及到期起始时间获得总到期(购买)金额
     * @param type
     * @param status
     * @param startTime
     * @param endTime
     * @param mustExistTime 是否必须存在起始时间
     * @return
     */
    @Override
    public BigDecimal getClosingFund(Long type, Byte status, Long startTime, Long endTime, boolean mustExistTime) {
        return ragularAccountRepository.getClosingFund(type, status, startTime, endTime, mustExistTime);
    }

    @Override
    public List<Object[]> findExportData(Map<String, Object> filter) {
        return ragularAccountRepository.findExportData(filter);
    }

    /**
     * 查询在指定时间段内，购买定存（不含债权转让）最多的Top N用户
     * @return
     */
    @Override
    public List<TopRagularUser> findTopRagularUser() {
        //2016年1月15日00:00-2016年1月29日23:59
        Long startTime = DateUtil.getSystemTimeDay("2016-01-15");
        Long endTime = DateUtil.getSystemTimeDay("2016-01-30");
        return ragularAccountRepository.findTopRagularUser(startTime, endTime, 15);
    }

    public IRagularUserAccountRepository getRagularUserAccountRepository() {
        return ragularUserAccountRepository;
    }

    public void setRagularUserAccountRepository(IRagularUserAccountRepository ragularUserAccountRepository) {
        this.ragularUserAccountRepository = ragularUserAccountRepository;
    }
}
