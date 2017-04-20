package com.ydzb.packet.service.impl;

import com.ydzb.admin.shiro.ShiroUser;
import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.core.utils.POIUtil;
import com.ydzb.packet.entity.*;
import com.ydzb.packet.repository.*;
import com.ydzb.packet.service.IActivityGuoqingExchangeService;
import com.ydzb.user.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.LockModeType;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 国庆活动记录service实现
 */
@Service
public class ActivityGuoqingExchangeServiceImpl extends BaseServiceImpl<ActivityGuoqingExchange, Long> implements IActivityGuoqingExchangeService {

    @Autowired
    private IActivityGuoqingExchangeRepository activityGuoqingExchangeRepository;
    @Autowired
    private ActivityGuoqingRepository activityGuoqingRepository;
    @Autowired
    private IActivityGuoqingRecordRepository activityGuoqingRecordRepository;
    @Autowired
    private ActivityGuoqingExchangeRepository cactivityGuoqingExchangeRepository;

    /**
     * 确认收货 和 确认发货
     *
     * @param id
     * @param status
     */
    @Override
    public void updateActivityZhongqiuExchange(Long id, Integer status) {
        Subject currentUser = SecurityUtils.getSubject();
        ShiroUser shiroUser = (ShiroUser) currentUser.getPrincipal();
        ActivityGuoqingExchange activityGuoqingExchange = activityGuoqingExchangeRepository.findOne(id);
        if (activityGuoqingExchange != null) {
            //更新信息
            activityGuoqingExchange.setStatus(status);
            activityGuoqingExchange.setOperationTime(DateUtil.getSystemTimeSeconds());
            activityGuoqingExchange.setOperationUser(shiroUser.getUsername());
            activityGuoqingExchangeRepository.save(activityGuoqingExchange);
        }
    }


    /**
     * 取消订单
     *
     * @param id
     */
    @Override
    public void auditFailure(Long id) {
        Subject currentUser = SecurityUtils.getSubject();
        ShiroUser shiroUser = (ShiroUser) currentUser.getPrincipal();
        ActivityGuoqingExchange activityGuoqingExchange = activityGuoqingExchangeRepository.findOne(id);
        if (activityGuoqingExchange != null) {
            //更新信息
            activityGuoqingExchange.setStatus(4);
            activityGuoqingExchange.setOperationTime(DateUtil.getSystemTimeSeconds());
            activityGuoqingExchange.setOperationUser(shiroUser.getUsername());
            activityGuoqingExchangeRepository.save(activityGuoqingExchange);

            User user = activityGuoqingExchange.getUser();
            if (user != null) {
                Integer fund = activityGuoqingExchange.getFund() == null ? 0 : activityGuoqingExchange.getFund();
                ActivityGuoqing activityGuoqing = activityGuoqingRepository.queryByUser(user.getId(), LockModeType.PESSIMISTIC_WRITE);
                if (activityGuoqing != null) {
                    //返还福包
                    activityGuoqing.setUsableFund(activityGuoqing.getUsableFund() == null ? fund.intValue() : (activityGuoqing.getUsableFund() + fund.intValue()));
                    activityGuoqingRepository.createOrUpdate(activityGuoqing);
                    //保存取消订单桂花记录
                    saveActivityZhongqiuRecord(user.getId(), 3, activityGuoqingExchange.getId(), fund, activityGuoqing.getUsableFund());
                }
            }
        }
    }


    /**
     * 保存桂花记录
     *
     * @param userId     用户ID
     * @param type       类型 1、获得 2、使用3、退款
     * @param linkId     外链ID 类型1定存记录ID 类型2兑换记录ID
     * @param fund       数量
     * @param usableFund 余额数量
     */
    public void saveActivityZhongqiuRecord(Long userId, Integer type, Long linkId, Integer fund, Integer usableFund) {
        ActivityGuoqingRecord activityGuoqingRecord = new ActivityGuoqingRecord();
        activityGuoqingRecord.setUserId(userId);
        activityGuoqingRecord.setType(type);
        activityGuoqingRecord.setLinkId(linkId);
        activityGuoqingRecord.setFund(fund);
        activityGuoqingRecord.setUsableFund(usableFund);
        activityGuoqingRecord.setCreated(DateUtil.getSystemTimeSeconds());
        activityGuoqingRecordRepository.save(activityGuoqingRecord);

    }


    /**
     * 查询导出数据
     *
     * @param filter
     * @return
     */
    @Override
    public List<Object[]> findExportData(Map<String, Object> filter) {
        return cactivityGuoqingExchangeRepository.findExportData(filter);
    }

    /**
     * 导出Excel
     *
     * @param exchanges
     * @param address
     * @return
     */
    @Override
    public String exportExcel(List<Object[]> exchanges, String address) {
        String fileName = "";
        try {
            List<Map<String, Object>> headInfo = createHeadInfo();
            List<Map<String, Object>> rowInfo = createRowInfo(exchanges);
            fileName = "/guoqing" + DateUtil.getSystemTimeSeconds() + ".xls";
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
        String[] titles = new String[]{"用户名", "手机号", "兑奖类型", "状态", "奖品名称", "收货人", "收货手机号", "收货地址", "兑换时间"};
        String[] keys = new String[]{"username", "mobile", "type", "status", "productName", "realname", "receiveMobile", "addr", "created"};
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
            rowMap.put("username", row[0] == null ? "" : row[0]);
            rowMap.put("mobile", row[1] == null ? "" : row[1]);
            rowMap.put("type", getTypeName((Integer) row[2]));
            rowMap.put("status", getStatus((Integer) row[3]));
            rowMap.put("productName", row[4] == null ? "" : row[4]);
            rowMap.put("realname", row[5] == null ? "" : row[5]);
            rowMap.put("receiveMobile", row[6] == null ? "" : row[6]);
            rowMap.put("addr", row[7] == null ? "" : row[7]);
            rowMap.put("created", row[8] == null ? "" : format.format(DateUtil.getSystemTimeMillisecond(((Integer) row[8]).longValue())));
            rowList.add(rowMap);
        }
        return rowList;
    }

    private String getStatus(Integer status) {
        if (status == null) {
            return "";
        }
        switch (status) {
            case 0:
                return "未兑换";
            case 1:
                return "待处理";
            case 2:
                return "已发货";
            case 3:
                return "已收货";
            case 4:
                return "已取消";
            default:
                return "";
        }
    }

    private String getTypeName(Integer type) {
        if (type == null) {
            return "";
        }
        switch (type) {
            case 1:
                return "兑换";
            case 2:
                return "中奖";
            default:
                return "";
        }
    }
}