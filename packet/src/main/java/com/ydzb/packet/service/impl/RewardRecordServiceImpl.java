package com.ydzb.packet.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.core.utils.POIUtil;
import com.ydzb.packet.entity.RewardRecord;
import com.ydzb.packet.repository.RewardRecordRepository;
import com.ydzb.packet.service.IRewardRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class RewardRecordServiceImpl extends BaseServiceImpl<RewardRecord, Long> implements IRewardRecordService {

    @Autowired
    private RewardRecordRepository rewardRecordRepository;

    @Override
    public List<Object[]> findExportData(Map<String, Object> filter) {
        return rewardRecordRepository.findExportData(filter);
    }

    /**
     * 导出excel
     */
    @Override
    public String exportExcel(List<Object[]> pays, String address) {
        String fileName = "";
        try {
            List<Map<String, Object>> headInfo = createHeadInfo();
            List<Map<String, Object>> rowInfo = createRowInfo(pays);

            fileName = "/reward_record" + DateUtil.getSystemTimeSeconds() + ".xls";
            String filePath = address + fileName;
            POIUtil.exportExcel2FilePath("周年中奖记录", filePath, headInfo, rowInfo);
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
        String[] titles = {"用户姓名", "手机号", "奖品名称", "奖品类型", "奖品金额", "中奖时间",
                "兑换记录", "兑换时间", "收货人姓名", "收货人手机号", "收货地址", "备注信息"};
        String[] keys = {"realName", "mobile", "rewardName", "rewardType", "rewardVal", "rewardTime", "exchangeType",
                "exchangeTime", "receiveRealName", "receiveMobile", "address", "remark"};
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
     * @param record
     * @return
     */
    private List<Map<String, Object>> createRowInfo(List<Object[]> record) {
        List<Map<String, Object>> rowList = new ArrayList<Map<String, Object>>();
        Map<String, Object> rowMap = null;
        for (Object[] row: record) {
            if (row == null) {
                continue;
            }
            rowMap = new HashMap<String, Object>();
            rowMap.put("realName", row[0] == null? "": row[0]);
            rowMap.put("mobile", row[1] == null? "": row[1]);
            rowMap.put("rewardName", row[2] == null? "": row[2]);
            rowMap.put("rewardType", getRewardType(row[3]));
            rowMap.put("rewardVal", row[4] == null? "": row[4]);
            rowMap.put("rewardTime", getTime(row[5]));
            rowMap.put("exchangeType", getExchangeType(row[6]));
            rowMap.put("exchangeTime", getTime(row[7]));
            rowMap.put("receiveRealName", row[8] == null? "": row[8]);
            rowMap.put("receiveMobile", row[9] == null? "": row[9]);
            rowMap.put("address", row[10] == null? "": row[10]);
            rowMap.put("remark", row[11] == null? "": row[11]);

            rowList.add(rowMap);
        }
        return rowList;
    }

    private String getRewardType(Object obj) {
        if (obj == null) {
            return "";
        }
        switch ((Byte)obj) {
            case 1: return "一等奖";
            case 2: return "二等奖";
            case 3: return "三等奖";
            case 4: return "四等奖";
            case 5: return "五等奖";
            case 6: return "现金红包";
            case 7: return "鼓励奖";
            default: return "";
        }
    }

    private String getTime(Object obj) {
        if (obj == null) {
            return "";
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(((Integer)obj).longValue() * 1000));
    }

    private String getExchangeType(Object obj) {
        if (obj == null) {
            return "";
        }
        switch ((Byte)obj) {
            case 0: return "未兑换";
            case 1: return "已兑换";
            default: return "";
        }
    }
}
