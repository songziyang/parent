package com.ydzb.product.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.core.utils.POIUtil;
import com.ydzb.product.entity.PlatformStatistics;
import com.ydzb.product.repository.PlatformStatisticsRepository;
import com.ydzb.product.service.IPlatformStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 平台统计service实现类
 */
@Service
public class PlatformStatisticsServiceImpl extends BaseServiceImpl<PlatformStatistics, Long> implements IPlatformStatisticsService {

    @Autowired
    private PlatformStatisticsRepository platformStatisticsRepository;

    /**
     * 导出excel
     */
    @Override
    public String exportExcel(List<Object[]> statistics, String address) {
        String fileName = "";
        try {
            List<Map<String, Object>> headInfo = createHeadInfo();
            List<Map<String, Object>> rowInfo = createRowInfo(statistics);

            fileName = "/statistics" + DateUtil.getSystemTimeSeconds() + ".xls";
            String filePath = address + fileName;
            POIUtil.exportExcel2FilePath("平台统计", filePath, headInfo, rowInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }

    /**
     * 创建头信息
     *
     * @return
     */
    private List<Map<String, Object>> createHeadInfo() {
        String[] titles = new String[]{"日期", "累计交易金额", "累计收益", "活期累计交易金额", "定期累计交易金额",
                "活期年化利率", "债权数量", "债权金额", "平台数量", "投资人数", "充值人数", "活期宝当日人数", "定存宝当日人数","活期宝总人数","定存宝总人数","总人数"};
        String[] keys = new String[]{"totalDate", "totalFund", "totalRevenue", "totalDayloan", "totalDeposit",
                "dayloanApr", "investNum", "investFund", "platformNum", "investPersons", "rechargeNum", "currentPersons", "ragularPersons","currents","ragulars","totalPersons"};
        List<Map<String, Object>> headInfoList = new ArrayList<Map<String, Object>>();
        Map<String, Object> itemMap = null;
        for (int i = 0; i < titles.length; i++) {
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
     *
     * @param statistics
     * @return
     */
    private List<Map<String, Object>> createRowInfo(List<Object[]> statistics) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        List<Map<String, Object>> rowList = new ArrayList<Map<String, Object>>();
        Map<String, Object> rowMap = null;
        for (Object[] row : statistics) {
            if (row == null) {
                continue;
            }
            rowMap = new HashMap<String, Object>();
            rowMap.put("totalDate", row[0] == null ? "" : format.format(DateUtil.getSystemTimeMillisecond(((Integer) row[0]).longValue())));
            rowMap.put("totalFund", row[1] == null ? 0L : row[1]);
            rowMap.put("totalRevenue", row[2] == null ? 0.00 : row[2]);
            rowMap.put("totalDayloan", row[3] == null ? 0L : row[3]);
            rowMap.put("totalDeposit", row[4] == null ? 0L : row[4]);
            rowMap.put("dayloanApr", row[5] == null ? "0.00%" : row[5] + "%");
            rowMap.put("investNum", row[6] == null ? 0L : row[6]);
            rowMap.put("investFund", row[7] == null ? 0L : row[7]);
            rowMap.put("platformNum", row[8] == null ? 0L : row[8]);
            rowMap.put("investPersons", row[9] == null ? 0L : row[9]);
            rowMap.put("rechargeNum", row[10] == null ? 0L : row[10]);
            rowMap.put("currentPersons", row[11] == null ? 0L : row[11]);
            rowMap.put("ragularPersons", row[12] == null ? 0L : row[12]);
            rowMap.put("currents", row[13] == null ? 0L : row[13]);
            rowMap.put("ragulars", row[14] == null ? 0L : row[14]);
            rowMap.put("totalPersons", row[15] == null ? 0L : row[15]);
            rowList.add(rowMap);
        }
        return rowList;
    }

    /**
     * 查询导出excel数据
     *
     * @return
     */
    @Override
    public List<Object[]> findExportData(Map<String, Object> filter) {
        return platformStatisticsRepository.findExportData(filter);
    }
}