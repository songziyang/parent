package com.ydzb.packet.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.core.utils.POIUtil;
import com.ydzb.packet.entity.SilverExchange;
import com.ydzb.packet.entity.SilverTraceRecord;
import com.ydzb.packet.entity.SilverUser;
import com.ydzb.packet.repository.ISilverExchangeRepository;
import com.ydzb.packet.repository.ISilverTraceRecordRepository;
import com.ydzb.packet.repository.ISilverUserRepository;
import com.ydzb.packet.repository.SilverExchangeRepository;
import com.ydzb.packet.service.ISilverExchangeService;
import com.ydzb.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 银币活动
 */
@Service
public class SilverExchangeServiceImpl extends BaseServiceImpl<SilverExchange, Long> implements ISilverExchangeService {


    @Autowired
    private ISilverExchangeRepository silverExchangeRepository;

    @Autowired
    private ISilverUserRepository silverUserRepository;

    @Autowired
    private ISilverTraceRecordRepository silverTraceRecordRepository;

    @Autowired
    private SilverExchangeRepository cSilverExchangeRepository;


    @Override
    public void updateSilverExchange(Long id, Integer status) {
        SilverExchange silverExchange = silverExchangeRepository.findOne(id);
        if (silverExchange != null) {
            silverExchange.setStatus(status);
            silverExchange.setSended(DateUtil.getSystemTimeSeconds());
            this.update(silverExchange);
        }
    }


    @Override
    public void auditFailure(Long id) {
        SilverExchange silverExchange = silverExchangeRepository.findOne(id);
        if (silverExchange != null) {
            silverExchange.setStatus(4);
            this.update(silverExchange);
            User user = silverExchange.getUser();
            if (user != null) {
                SilverUser silverUser = silverUserRepository.querySilverUserByUserId(user.getId());
                if (silverUser != null) {
                    silverUser.setUsableFund(silverUser.getUsableFund() + silverExchange.getSilverProduct().getExchange());
                    silverUserRepository.save(silverUser);

                    SilverTraceRecord silverTraceRecord = new SilverTraceRecord();
                    silverTraceRecord.setUserId(silverUser.getUserId());
                    silverTraceRecord.setUsableFund(silverUser.getUsableFund());
                    silverTraceRecord.setFund(silverExchange.getSilverProduct().getExchange());
                    silverTraceRecord.setType(3);
                    silverTraceRecord.setCreated(DateUtil.getSystemTimeSeconds());
                    silverTraceRecord.setLinkId(silverExchange.getId());
                    silverTraceRecordRepository.save(silverTraceRecord);

                }

            }
        }
    }

    @Override
    public List<Object[]> findExportData(Map<String, Object> filter) {
        return cSilverExchangeRepository.findExportData(filter);
    }

    @Override
    public SilverUser querySilverUserByUserId(Long userId) {
        return silverUserRepository.querySilverUserByUserId(userId);
    }

    @Override
    public String exportExcel(List<Object[]> exchanges, String address) {
        String fileName = "";
        try {
            List<Map<String, Object>> headInfo = createHeadInfo();
            List<Map<String, Object>> rowInfo = createRowInfo(exchanges);

            fileName = "/silverexchange" + DateUtil.getSystemTimeSeconds() + ".xls";
            String filePath = address + fileName;
            POIUtil.exportExcel2FilePath("活动兑换", filePath, headInfo, rowInfo);
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
        String[] titles = new String[]{"用户名", "手机号", "状态", "奖品名称", "收货人", "收货手机号", "收货地址", "备注", "受理时间"};
        String[] keys = new String[]{"username", "mobile", "status", "awardName", "receiveUser", "receiveMobile", "receiveAddress", "remark", "time"};
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
            //"username", "mobile", "status", "awardName", "receiveUser", "receiveMobile", "receiveAddress", "remark", "time"
            rowMap.put("username", row[0] == null? "": row[0]);
            rowMap.put("mobile", row[1] == null? "": row[1]);
            rowMap.put("status", getStatus((Integer)row[2]));
            rowMap.put("awardName", row[3] == null? "": row[3]);
            rowMap.put("receiveUser", row[4] == null? "": row[4]);
            rowMap.put("receiveMobile", row[5] == null? "": row[5] + "%");
            rowMap.put("receiveAddress", row[6] == null? "": row[6]);
            rowMap.put("remark", row[7] == null? "": row[7]);
            rowMap.put("time", row[8] == null? "": format.format(DateUtil.getSystemTimeMillisecond(((Integer)row[8]).longValue())));
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

    public ISilverExchangeRepository getSilverExchangeRepository() {
        return silverExchangeRepository;
    }

    public void setSilverExchangeRepository(ISilverExchangeRepository silverExchangeRepository) {
        this.silverExchangeRepository = silverExchangeRepository;
    }

    public ISilverUserRepository getSilverUserRepository() {
        return silverUserRepository;
    }

    public void setSilverUserRepository(ISilverUserRepository silverUserRepository) {
        this.silverUserRepository = silverUserRepository;
    }

    public ISilverTraceRecordRepository getSilverTraceRecordRepository() {
        return silverTraceRecordRepository;
    }

    public void setSilverTraceRecordRepository(ISilverTraceRecordRepository silverTraceRecordRepository) {
        this.silverTraceRecordRepository = silverTraceRecordRepository;
    }
}
