package com.ydzb.packet.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.core.utils.POIUtil;
import com.ydzb.packet.entity.ActivityOctoberRedpacket;
import com.ydzb.packet.repository.ActivityOctoberRedpacketRepository;
import com.ydzb.packet.service.IActivityOctoberRedpacketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 金秋十月活动service实现
 */
@Service
public class ActivityOctoberRedpacketServiceImpl extends BaseServiceImpl<ActivityOctoberRedpacket, Long>
        implements IActivityOctoberRedpacketService {

    @Autowired
    private ActivityOctoberRedpacketRepository activityOctoberRedpacketRepository;

    @Override
    public List<Object[]> findExportData(Map<String, Object> filters) {
        return activityOctoberRedpacketRepository.findExportData(filters);
    }


    /**
     * 导出Excel
     *
     * @param address
     * @return
     */
    @Override
    public String exportExcel(List<Object[]> datas, String address) {
        String fileName = "";
        try {
            List<Map<String, Object>> headInfo = createHeadInfo();
            List<Map<String, Object>> rowInfo = createRowInfo(datas);
            fileName = "/october" + DateUtil.getSystemTimeSeconds() + ".xls";
            String filePath = address + fileName;
            POIUtil.exportExcel2FilePath("国庆活动兑换", filePath, headInfo, rowInfo);
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
        String[] titles = new String[]{"用户名", "手机号", "购买金额", "购买天数", "购买类型", "红包金额"};
        String[] keys = new String[]{"username", "mobile", "buyFund", "days", "buyType", "redpacketFund"};
        List<Map<String, Object>> headInfoList = new ArrayList<>();
        Map<String, Object> itemMap;
        for (int i = 0; i < titles.length; i++) {
            itemMap = new HashMap<>();
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
        List<Map<String, Object>> rowList = new ArrayList<>();
        Map<String, Object> rowMap;
        for (Object[] row : statistics) {
            if (row == null) {
                continue;
            }
            rowMap = new HashMap<>();
            rowMap.put("username", row[0] == null ? "" : row[0]);
            rowMap.put("mobile", row[1] == null ? "" : row[1]);
            rowMap.put("buyFund", row[2] == null ? "" : row[2]);
            rowMap.put("days", row[3] == null? "": row[3]);
            rowMap.put("buyType", row[4] == null? "": convertBuyType((Integer)row[4]));
            rowMap.put("redpacketFund", row[5] == null? "": row[5]);
            rowList.add(rowMap);
        }
        return rowList;
    }

    /**
     * 转换成购买类型
     * @param buyType
     * @return
     */
    private String convertBuyType(Integer buyType) {
        if (buyType == null) return "";
        switch (buyType) {
            case 0: return "正常";
            case 1: return "复投";
            default: return "";
        }

    }
}