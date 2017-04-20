package com.ydzb.packet.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.core.utils.POIUtil;
import com.ydzb.packet.entity.SilverExchangeThirty;
import com.ydzb.packet.entity.SilverUserThirty;
import com.ydzb.packet.repository.ISilverExchangeThirtyRepository;
import com.ydzb.packet.repository.SilverExchangeThirtyRepository;
import com.ydzb.packet.service.ISilverExchangeThirtyService;
import com.ydzb.packet.service.ISilverTraceRecordThirtyService;
import com.ydzb.packet.service.ISilverUserThirtyService;
import com.ydzb.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.LockModeType;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 银币活动
 */
@Service
public class SilverExchangeThirtyServiceImpl extends BaseServiceImpl<SilverExchangeThirty, Long> implements ISilverExchangeThirtyService {


    @Autowired
    private ISilverExchangeThirtyRepository silverExchangeThirtyRepository;
    @Autowired
    private SilverExchangeThirtyRepository cSilverExchangeRepository;
    @Autowired
    private ISilverUserThirtyService silverUserThirtyService;
    @Autowired
    private ISilverTraceRecordThirtyService silverTraceRecordThirtyService;


    @Override
    public void updateSilverExchange(Long id, Integer status) {
        SilverExchangeThirty silverExchange = silverExchangeThirtyRepository.findOne(id);
        if (silverExchange != null) {
            silverExchange.setStatus(status);
            silverExchange.setSended(DateUtil.getSystemTimeSeconds());
            update(silverExchange);
        }
    }


    @Override
    public void auditFailure(Long id) {
        
        SilverExchangeThirty silverExchange = silverExchangeThirtyRepository.findOne(id);
        if (silverExchange != null) {
            silverExchange.setStatus(4);
            update(silverExchange);
            User user = silverExchange.getUser();
            if (user != null) {
                BigDecimal fund = silverExchange.getFund() == null? BigDecimal.ZERO: silverExchange.getFund();
                SilverUserThirty silverUser = silverUserThirtyService.queryByUser(user.getId(), LockModeType.PESSIMISTIC_WRITE);
                if (silverUser == null) {
                    silverUser = silverUserThirtyService.createOne(user.getId(), fund.intValue(), 0);
                }
                silverUser.setUsableFund(silverUser.getUsableFund() == null? fund.intValue(): (silverUser.getUsableFund() + fund.intValue()));
                silverUserThirtyService.save(silverUser);

                silverTraceRecordThirtyService.createOne(user.getId(), 1, silverExchange.getId(), fund.intValue(), silverUser.getUsableFund());
            }
        }
    }

    @Override
    public List<Object[]> findExportData(Map<String, Object> filter) {
        return cSilverExchangeRepository.findExportData(filter);
    }

    @Override
    public SilverUserThirty querySilverUserByUserId(Long userId) {
        return silverUserThirtyService.queryByUser(userId, LockModeType.NONE);
    }

    @Override
    public String exportExcel(List<Object[]> exchanges, String address) {
        String fileName = "";
        try {
            List<Map<String, Object>> headInfo = createHeadInfo();
            List<Map<String, Object>> rowInfo = createRowInfo(exchanges);

            fileName = "/silverexchangethirty" + DateUtil.getSystemTimeSeconds() + ".xls";
            String filePath = address + fileName;
            POIUtil.exportExcel2FilePath("30亿活动兑换", filePath, headInfo, rowInfo);
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
        String[] titles = new String[]{"用户名", "手机号", "兑换类型", "状态", "奖品名称", "收货人", "收货手机号", "收货地址", "备注", "兑换时间", "受理时间"};
        String[] keys = new String[]{"username", "mobile", "type", "status", "awardName", "receiveUser", "receiveMobile", "receiveAddress", "remark", "time", "optime"};
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
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
        List<Map<String, Object>> rowList = new ArrayList<>();
        Map<String, Object> rowMap;
        for (Object[] row : statistics) {
            if (row == null) {
                continue;
            }
            rowMap = new HashMap<>();
            rowMap.put("username", row[0] == null? "": row[0]);
            rowMap.put("mobile", row[1] == null? "": row[1]);
            rowMap.put("type", getTypeName((Integer)row[2]));
            rowMap.put("status", getStatus((Integer)row[3]));
            rowMap.put("awardName", row[4] == null? "": row[4]);
            rowMap.put("receiveUser", row[5] == null? "": row[5]);
            rowMap.put("receiveMobile", row[6] == null? "": row[6]);
            rowMap.put("receiveAddress", row[7] == null? "": row[7]);
            rowMap.put("remark", row[8] == null? "": row[8]);
            rowMap.put("time", row[9] == null? "": format.format(DateUtil.getSystemTimeMillisecond(((Integer)row[9]).longValue())));
            rowMap.put("optime", row[10] == null? "": format.format(DateUtil.getSystemTimeMillisecond(((Integer)row[10]).longValue())));
            rowList.add(rowMap);
        }
        return rowList;
    }

    private String getStatus(Integer status) {
        if (status == null) {
            return "";
        }
        switch (status) {
            case 1: return "待处理";
            case 2: return "已发货";
            case 3: return "已收货";
            case 4: return "已取消";
            default: return "";
        }
    }

    private String getTypeName(Integer type) {
        if (type == null) {
            return "";
        }
        switch (type) {
            case 0: return "抽奖兑换";
            case 1: return "银多币兑换";
            default: return "";
        }
    }
}