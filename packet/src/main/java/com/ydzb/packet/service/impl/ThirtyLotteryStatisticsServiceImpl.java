package com.ydzb.packet.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.core.utils.POIUtil;
import com.ydzb.packet.entity.ThirtyLotteryStatistics;
import com.ydzb.packet.repository.ThirtyExchangeStatisticsRepository;
import com.ydzb.packet.repository.ThirtyLotteryStatisticsRepository;
import com.ydzb.packet.service.IThirtyLotteryStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sy on 2016/8/25.
 */
@Service
public class ThirtyLotteryStatisticsServiceImpl extends BaseServiceImpl<ThirtyLotteryStatistics, Long> implements IThirtyLotteryStatisticsService {

    @Autowired
    private ThirtyLotteryStatisticsRepository thirtyLotteryStatisticsRepository;

    @Override
    public List<Object[]> findExportData(Map<String, Object> filter) {
        return thirtyLotteryStatisticsRepository.findExportData(filter);
    }


    @Override
    public String exportExcel(List<Object[]> exchanges, String address) {

        String fileName = "";
        try {
            List<Map<String, Object>> headInfo = createHeadInfo();
            List<Map<String, Object>> rowInfo = createRowInfo(exchanges);

            fileName = "/thirtyLotteryStatistics" + DateUtil.getSystemTimeSeconds() + ".xls";
            String filePath = address + fileName;
            POIUtil.exportExcel2FilePath("30亿活动抽奖统计", filePath, headInfo, rowInfo);
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
        String[] titles = new String[]{"抽奖数", "统计日期"};
        String[] keys = new String[]{"allCount", "statisticsDate"};
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
        List<Map<String, Object>> rowList = new ArrayList<Map<String, Object>>();
        Map<String, Object> rowMap = null;
        for (Object[] row : statistics) {
            if (row == null) {
                continue;
            }
            rowMap = new HashMap<String, Object>();
            rowMap.put("allCount", row[0] == null ? 0 : row[0]);
            rowMap.put("statisticsDate", row[1] == null? "": new SimpleDateFormat("yyyy-MM-dd").format(DateUtil.getSystemTimeMillisecond(((Integer) row[1]).longValue())));
            rowList.add(rowMap);
        }
        return rowList;
    }

}
