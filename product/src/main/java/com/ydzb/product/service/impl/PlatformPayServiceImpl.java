package com.ydzb.product.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.core.utils.POIUtil;
import com.ydzb.product.entity.PlatformPay;
import com.ydzb.product.entity.PlatformTradingDeal;
import com.ydzb.product.repository.IPlatformPayRepository;
import com.ydzb.product.repository.PlatformPayRepository;
import com.ydzb.product.service.IPlatformPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 平台支出统计service实现类
 * @author sy
 */
@Service
public class PlatformPayServiceImpl extends BaseServiceImpl<PlatformPay, Long> implements IPlatformPayService {

    @Autowired
    private PlatformPayRepository platformPayRepository;

    /**
     * 导出excel
     */
    @Override
    public String exportExcel(List<Object[]> pays, String address) {
        String fileName = "";
        try {
            List<Map<String, Object>> headInfo = createHeadInfo();
            List<Map<String, Object>> rowInfo = createRowInfo(pays);

            fileName = "/platform_pay_" + DateUtil.getSystemTimeSeconds() + ".xls";
            String filePath = address + fileName;
            POIUtil.exportExcel2FilePath("支出统计", filePath, headInfo, rowInfo);
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
        String[] titles = {"金额", "类型", "操作日期"};
        String[] keys = {"fund", "type", "optime"};
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
     * @param pays
     * @return
     */
    private List<Map<String, Object>> createRowInfo(List<Object[]> pays) {
        List<Map<String, Object>> rowList = new ArrayList<Map<String, Object>>();
        Map<String, Object> rowMap = null;
        for (Object[] row: pays) {
            if (row == null) {
                continue;
            }
            rowMap = new HashMap<String, Object>();
            if (row[0] == null) {
                rowMap.put("fund", 0);
            } else {
                if ((Byte)row[1] != null && (Byte)row[1] == 5) {
                    rowMap.put("fund", ((BigDecimal)row[0]).divide(BigDecimal.TEN, 2, BigDecimal.ROUND_DOWN));
                } else {
                    rowMap.put("fund", ((BigDecimal)row[0]).setScale(2,BigDecimal.ROUND_DOWN ));
                }
            }
            rowMap.put("type", row[1] == null? "": getTypeName((Byte) row[1]));
            rowMap.put("optime", row[2] == null ? "": new SimpleDateFormat("yyyy-MM-dd").format(DateUtil.getSystemTimeMillisecond(((Integer) row[2]).longValue())));
            rowList.add(rowMap);
        }
        return rowList;
    }

    /**
     * 获得类型名称
     * @param type
     * @return
     */
    private String getTypeName(Byte type) {
        if (type == null) {
            return "";
        }
        switch(type) {
            case 1: return "活期宝收益";
            case 2: return "定存宝收益";
            case 3: return "机构宝收益";
            case 4: return "私人订制收益";
            case 5: return "推荐收益返现";
            default: return "";
        }
    }

    /**
     * 获得导出excel数据
     * @param filters
     * @return
     */
    @Override
    public List<Object[]> findExportData(Map<String, Object> filters) {
        return platformPayRepository.findExportData(filters);
    }
}