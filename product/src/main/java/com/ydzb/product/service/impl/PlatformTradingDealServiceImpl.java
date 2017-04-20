package com.ydzb.product.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.core.utils.POIUtil;
import com.ydzb.product.entity.PlatformTradingDeal;
import com.ydzb.product.repository.IPlatformTradingDealRepository;
import com.ydzb.product.repository.PlatformTradingDealRepository;
import com.ydzb.product.service.IPlatformTradingDealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 平台交易统计详细service实现类
 * @author sy
 */
@Service
public class PlatformTradingDealServiceImpl extends BaseServiceImpl<PlatformTradingDeal, Long> implements IPlatformTradingDealService {

    @Autowired
    private IPlatformTradingDealRepository iPlatformTradingDealRepository;
    @Autowired
    private PlatformTradingDealRepository platformTradingDealRepository;

    /**
     * 根据平台交易记录类型、操作日期起始时间获得总金额
     * @param type
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public BigDecimal findSumFund(Byte type, String startDate, String endDate) {
        return platformTradingDealRepository.findSumFund(type, startDate, endDate);
    }


    /**
     * 导出excel
     */
    @Override
    public String exportExcel(List<PlatformTradingDeal> tradingDeals, String address) {
        String fileName = "";
        try {
            List<Map<String, Object>> headInfo = createHeadInfo();
            List<Map<String, Object>> rowInfo = createRowInfo(tradingDeals);

            fileName = "/platform_tradingdeal_" + DateUtil.getSystemTimeSeconds() + ".xls";
            String filePath = address + fileName;
            POIUtil.exportExcel2FilePath("交易统计", filePath, headInfo, rowInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }

    /**
     * 创建头信息
     * @return
     */
    private List<Map<String, Object>> createHeadInfo() {
        String[] titles = {"产品名称", "操作金额", "操作类型", "操作日期"};
        String[] keys = {"name", "fund", "type", "optime"};
        List<Map<String, Object>> headInfoList = new ArrayList<Map<String, Object>>();
        Map<String, Object> itemMap = null;
        for (int i = 0; i < titles.length; i ++) {
            itemMap = new HashMap<String, Object>();
            itemMap.put("title", titles[i]);
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", keys[i]);
            headInfoList.add(itemMap);
        }
        return headInfoList;
    }

    /**
     * 创建行信息
     * @param tradingDeals
     * @return
     */
    private List<Map<String, Object>> createRowInfo(List<PlatformTradingDeal> tradingDeals) {
        List<Map<String, Object>> rowList = new ArrayList<Map<String, Object>>();
        Map<String, Object> rowMap = null;
        for (PlatformTradingDeal tradingDeal: tradingDeals) {
            rowMap = new HashMap<String, Object>();
            rowMap.put("name", tradingDeal.getName());
            rowMap.put("fund", tradingDeal.getFund() == null? "0": tradingDeal.getFund());
            rowMap.put("type", getTypeName(tradingDeal));
            rowMap.put("optime", new SimpleDateFormat("yyyy-MM-dd").format(tradingDeal.getOperationDate()));
            rowList.add(rowMap);
        }
        return rowList;
    }

    /**
     * 获得类型名称
     * @param tradingDeal
     * @return
     */
    private String getTypeName(PlatformTradingDeal tradingDeal) {
        String typeName = null;
        Byte type = tradingDeal.getPlatformTrading().getType();
        switch(type) {
            case 1: return "充值";
            case 2: return "提现";
            case 3: return "活期宝";
            case 4: return "定存宝";
            case 5: return "机构宝";
            case 6: return "私人订制";
            default: return "";
        }
    }
}