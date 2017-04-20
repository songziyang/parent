package com.ydzb.product.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.core.utils.POIUtil;
import com.ydzb.product.entity.PlatformReconciliation;
import com.ydzb.product.repository.IPlatformReconciliationRepository;
import com.ydzb.product.repository.PlatformReconciliationRepository;
import com.ydzb.product.service.IPlatformReconciliationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class PlatformReconciliationServiceImpl extends BaseServiceImpl<PlatformReconciliation, Long> implements IPlatformReconciliationService {

    @Autowired
    private IPlatformReconciliationRepository platformReconciliationRepository;

    @Autowired
    private PlatformReconciliationRepository reconciliationRepository;

    /**
     * 导出excel
     */
    @Override
    public String exportExcel(List<Object[]> statistics, String address) {
        String fileName = "";
        try {
            List<Map<String, Object>> headInfo = createHeadInfo();
            List<Map<String, Object>> rowInfo = createRowInfo(statistics);
            fileName = "/reconciliation" + DateUtil.getSystemTimeSeconds() + ".xls";
            String filePath = address + fileName;
            POIUtil.exportExcel2FilePath("平台对账管理", filePath, headInfo, rowInfo);
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
        String[] titles = new String[]{"统计时间", "平台总资金(含收益)", "平台总收益", "第三方余额","第三方未结算金额","第三方冻结金额","第三方保证金额"};
        String[] keys = new String[]{"totalDate", "platformFund", "platformIncome", "thirdFund","thirdSettlement","thirdFreeze","thirdEnsure"};
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
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        List<Map<String, Object>> rowList = new ArrayList<Map<String, Object>>();
        Map<String, Object> rowMap = null;
        for (Object[] row : statistics) {
            if (row == null) {
                continue;
            }
            rowMap = new HashMap<String, Object>();
            rowMap.put("totalDate", row[0] == null ? "" : format.format(DateUtil.getSystemTimeMillisecond(((Integer) row[0]).longValue())));
            rowMap.put("platformFund", row[1] == null ? 0.00 : row[1]);
            rowMap.put("platformIncome", row[2] == null ? 0.00 : row[2]);
            rowMap.put("thirdFund", row[3] == null ? 0.00 : row[3]);
            rowMap.put("thirdSettlement", row[4] == null ? 0.00 : row[4]);
            rowMap.put("thirdFreeze", row[5] == null ? 0.00 : row[5]);
            rowMap.put("thirdEnsure", row[6] == null ? 0.00 : row[6]);
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
        return reconciliationRepository.findExportData(filter);
    }


    public IPlatformReconciliationRepository getPlatformReconciliationRepository() {
        return platformReconciliationRepository;
    }

    public void setPlatformReconciliationRepository(IPlatformReconciliationRepository platformReconciliationRepository) {
        this.platformReconciliationRepository = platformReconciliationRepository;
    }

    public PlatformReconciliationRepository getReconciliationRepository() {
        return reconciliationRepository;
    }

    public void setReconciliationRepository(PlatformReconciliationRepository reconciliationRepository) {
        this.reconciliationRepository = reconciliationRepository;
    }
}