package com.ydzb.user.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.core.utils.POIUtil;
import com.ydzb.user.entity.FundTransferRecord;
import com.ydzb.user.repository.FundTransferRecordRepository;
import com.ydzb.user.service.IFundTransferRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 资金转账记录service实现
 * @author sy
 */
@Service
public class FundTransferRecordServiceImpl extends BaseServiceImpl<FundTransferRecord, Long>
        implements IFundTransferRecordService {

    @Autowired
    private FundTransferRecordRepository fundTransferRecordRepository;

    /**
     * 查找导出excel所需数据
     * @param filter
     * @return
     */
    @Override
    public List<Object[]> findExportData(Map<String, Object> filter) {
        return fundTransferRecordRepository.findExportData(filter);
    }

    /**
     * 导出excel
     * @param data
     * @param address
     * @return
     */
    @Override
    public String exportExcel(List<Object[]> data, String address) {
        String fileName = "";
        try {
            List<Map<String, Object>> headInfo = createHeadInfo();
            List<Map<String, Object>> rowInfo = createRowInfo(data);
            fileName = "/fund_transfer" + DateUtil.getSystemTimeSeconds() + ".xls";
            String filePath = address + fileName;
            POIUtil.exportExcel2FilePath("资金转账记录", filePath, headInfo, rowInfo);
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
        final String[] titles = {"转出人用户名", "转出人手机号", "转入人用户名", "转入人手机号", "转账金额", "操作时间"};
        final String[] keys = {"fromUsername", "fromMobile", "toUsername", "toMobile", "fund", "optime"};
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
     * @param data
     * @return
     */
    private List<Map<String, Object>> createRowInfo(List<Object[]> data) {
        List<Map<String, Object>> rowList = new ArrayList<Map<String, Object>>();
        Map<String, Object> rowMap = null;
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        for (Object[] row: data) {
            if (row == null) {
                continue;
            }
            rowMap = new HashMap<String, Object>();
            rowMap.put("fromUsername", row[0] == null? "": row[0]);
            rowMap.put("fromMobile", row[1] == null? "": row[1]);
            rowMap.put("toUsername", row[2] == null? "": row[2]);
            rowMap.put("toMobile", row[3] == null? "": row[3]);
            rowMap.put("fund", row[4] == null? "": row[4]);
            rowMap.put("optime", row[5] == null? "": dateFormater.format(DateUtil.getSystemTimeMillisecond(((Integer)row[5]).longValue())));
            rowList.add(rowMap);
        }
        return rowList;
    }
}
