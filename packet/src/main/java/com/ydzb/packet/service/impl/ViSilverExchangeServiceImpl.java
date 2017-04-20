package com.ydzb.packet.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.core.utils.POIUtil;
import com.ydzb.packet.entity.SilverTraceRecord;
import com.ydzb.packet.entity.SilverUser;
import com.ydzb.packet.entity.ViSilverExchange;
import com.ydzb.packet.repository.*;
import com.ydzb.packet.service.IViSilverExchangeService;
import com.ydzb.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/12/8.
 */
@Service
public class ViSilverExchangeServiceImpl extends BaseServiceImpl<ViSilverExchange, Long> implements IViSilverExchangeService {

    @Autowired
    private IViSilverExchangeRepository viSilverExchangeRepository;
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
        ViSilverExchange silverExchange = viSilverExchangeRepository.findOne(id);
        if (silverExchange != null) {
            //如果充话费，直接收货
            if (silverExchange.getType() == ViSilverExchange.MOBILE_RECHARGE) {
                if (silverExchange.getUser() != null && silverExchange.getUser().getId() != null) {
                    silverExchangeRepository.updateStatus(silverExchange.getUser().getId(), 3, DateUtil.getSystemTimeSeconds(), 1, ViSilverExchange.MOBILE_RECHARGE);
                    return;
                }
            }
        }
        silverExchangeRepository.updateStatus(id, status, DateUtil.getSystemTimeSeconds());
    }


    @Override
    public void auditFailure(Long id) {
        ViSilverExchange silverExchange = viSilverExchangeRepository.findOne(id);
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
        String[] titles = new String[]{"用户名", "手机号", "状态", "奖品名称", "收货人", "收货手机号", "收货地址", "活动类型", "金额", "备注", "受理时间"};
        String[] keys = new String[]{"username", "mobile", "status", "awardName", "receiveUser", "receiveMobile", "receiveAddress", "type", "fund", "remark", "time"};
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
            rowMap.put("username", row[0] == null? "": row[0]);
            rowMap.put("mobile", row[1] == null? "": row[1]);
            rowMap.put("status", getStatus((Integer)row[2]));
            rowMap.put("awardName", row[3] == null? "": row[3]);
            rowMap.put("receiveUser", row[4] == null? "": row[4]);
            rowMap.put("receiveMobile", row[5] == null? "": row[5] + "%");
            rowMap.put("receiveAddress", row[6] == null? "": row[6]);
            rowMap.put("type", row[7] == null? "": getType((Integer) row[7]));
            rowMap.put("fund", row[8] == null? "": getFund((BigDecimal)row[8], (Integer)row[7]));
            rowMap.put("remark", row[9] == null? "": row[9]);
            rowMap.put("time", row[10] == null? "": format.format(DateUtil.getSystemTimeMillisecond(((Integer)row[10]).longValue())));
            rowList.add(rowMap);
        }
        return rowList;
    }

    private String getType(Integer type) {
        if (type == null) {
            return "";
        }
        switch (type) {
            case ViSilverExchange.SILVER: return "银币活动兑换";
            case ViSilverExchange.GOLD_EGG: return "双十二砸金蛋";
            case 2: return "话费充值";
        }
        return "";
    }

    private String getFund(BigDecimal fund, Integer type) {
        if (type == null || type == ViSilverExchange.SILVER || type == ViSilverExchange.GOLD_EGG) {
            return "-";
        }
        return fund.toString();
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

    public IViSilverExchangeRepository getSilverExchangeRepository() {
        return viSilverExchangeRepository;
    }

    public void setSilverExchangeRepository(IViSilverExchangeRepository viSilverExchangeRepository) {
        this.viSilverExchangeRepository = viSilverExchangeRepository;
    }

    public SilverExchangeRepository getcSilverExchangeRepository() {
        return cSilverExchangeRepository;
    }

    public void setcSilverExchangeRepository(SilverExchangeRepository cSilverExchangeRepository) {
        this.cSilverExchangeRepository = cSilverExchangeRepository;
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
