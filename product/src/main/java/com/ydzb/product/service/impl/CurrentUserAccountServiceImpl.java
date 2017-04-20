package com.ydzb.product.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.core.utils.ExcelUtil;
import com.ydzb.core.utils.POIUtil;
import com.ydzb.product.entity.CurrentTradeRecored;
import com.ydzb.product.entity.CurrentUserAccount;
import com.ydzb.product.repository.CurrentUserAccountRepository;
import com.ydzb.product.repository.ICurrentUserAccountRepository;
import com.ydzb.product.service.ICurrentUserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class CurrentUserAccountServiceImpl extends BaseServiceImpl<CurrentUserAccount, Long> implements ICurrentUserAccountService {

    @Autowired
    private ICurrentUserAccountRepository currentUserAccountRepository;
    @Autowired
    private CurrentUserAccountRepository cCurrentUserAccountRepository;


    @Override
    public String exportExcel(List<Object[]> userAccounts, String address) {
        String fileName = "";
        try {
            String[] names = {"用户名", "手机号", "资金总额", "体验金总额", "收益复利"};
            String[] keys = {"username", "mobile", "totalFund", "expmoneyFund", "fundType", "tradeFund", "revenue"};
            List<Map<String, Object>> headInfoList = ExcelUtil.setHeadInfo(names, keys);
            List<Map<String, Object>> dataList = setData(keys, userAccounts);
            fileName = "/currentuseraccount" + DateUtil.getSystemTimeSeconds() + ".xls";
            String filePath = address + fileName;
            POIUtil.exportExcel2FilePath("活期宝已购", filePath, headInfoList, dataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }

    /**
     * 查询导出excel的数据
     * @param filters
     * @return
     */
    @Override
    public List<Object[]> findExportData(Map<String, Object> filters) {
        return cCurrentUserAccountRepository.findExportData(filters);
    }

    /**
     * 设置数据
     * @param keys
     * @param userAccounts
     * @return
     */
    private List<Map<String, Object>> setData(String[] keys, List<Object[]> userAccounts) {
        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
        Map<String, Object> dataItem = null;
        for (Object[] row: userAccounts) {
            if (row == null) {
                continue;
            }
            dataItem = new HashMap<String, Object>();
            dataItem.put(keys[0], row[0] == null? "": row[0]);
            dataItem.put(keys[1], row[1] == null? "": row[1]);
            dataItem.put(keys[2], row[2] == null? BigDecimal.ZERO: row[2]);
            dataItem.put(keys[3], row[3] == null? BigDecimal.ZERO: row[3]);
            dataItem.put(keys[4], row[4] == null? BigDecimal.ZERO: row[4]);
            dataList.add(dataItem);
        }
        return dataList;
    }


    public ICurrentUserAccountRepository getCurrentUserAccountRepository() {
        return currentUserAccountRepository;
    }

    public void setCurrentUserAccountRepository(ICurrentUserAccountRepository currentUserAccountRepository) {
        this.currentUserAccountRepository = currentUserAccountRepository;
    }
}