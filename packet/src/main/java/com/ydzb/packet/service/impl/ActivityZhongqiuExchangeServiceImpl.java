package com.ydzb.packet.service.impl;

import com.ydzb.admin.shiro.ShiroUser;
import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.core.utils.POIUtil;
import com.ydzb.packet.entity.ActivityZhongqiu;
import com.ydzb.packet.entity.ActivityZhongqiuExchange;
import com.ydzb.packet.entity.ActivityZhongqiuRecord;
import com.ydzb.packet.repository.ActivityZhongqiuExchangeRepository;
import com.ydzb.packet.repository.IActivityZhongqiuExchangeRepository;
import com.ydzb.packet.service.IActivityZhongqiuExchangeService;
import com.ydzb.user.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 中秋活动
 */
@Service
public class ActivityZhongqiuExchangeServiceImpl extends BaseServiceImpl<ActivityZhongqiuExchange, Long> implements IActivityZhongqiuExchangeService {

    @Autowired
    private IActivityZhongqiuExchangeRepository activityZhongqiuExchangeRepository;

    @Autowired
    private ActivityZhongqiuExchangeRepository zhongqiuExchangeRepository;

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
        ActivityZhongqiuExchange activityZhongqiuExchange = activityZhongqiuExchangeRepository.findOne(id);
        if (activityZhongqiuExchange != null) {
            //更新信息
            activityZhongqiuExchange.setStatus(status);
            activityZhongqiuExchange.setOperationTime(DateUtil.getSystemTimeSeconds());
            activityZhongqiuExchange.setOperationUser(shiroUser.getUsername());
            activityZhongqiuExchangeRepository.save(activityZhongqiuExchange);
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
        ActivityZhongqiuExchange activityZhongqiuExchange = activityZhongqiuExchangeRepository.findOne(id);
        if (activityZhongqiuExchange != null) {
            //更新信息
            activityZhongqiuExchange.setStatus(4);
            activityZhongqiuExchange.setOperationTime(DateUtil.getSystemTimeSeconds());
            activityZhongqiuExchange.setOperationUser(shiroUser.getUsername());
            activityZhongqiuExchangeRepository.save(activityZhongqiuExchange);

            User user = activityZhongqiuExchange.getUser();
            if (user != null) {
                Integer fund = activityZhongqiuExchange.getFund() == null ? 0 : activityZhongqiuExchange.getFund();
                ActivityZhongqiu activityZhongqiu = zhongqiuExchangeRepository.findWmActivityZhongqiuByUserId(user.getId());
                if (activityZhongqiu != null) {
                    //更新桂花余额
                    activityZhongqiu.setUsableFund(activityZhongqiu.getUsableFund() == null ? fund.intValue() : (activityZhongqiu.getUsableFund() + fund.intValue()));
                    zhongqiuExchangeRepository.saveActivityZhongqiu(activityZhongqiu);
                    //保存取消订单桂花记录
                    saveActivityZhongqiuRecord(user.getId(), 3, activityZhongqiuExchange.getId(), fund, activityZhongqiu.getUsableFund());
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
        ActivityZhongqiuRecord activityZhongqiuRecord = new ActivityZhongqiuRecord();
        activityZhongqiuRecord.setUserId(userId);
        activityZhongqiuRecord.setType(type);
        activityZhongqiuRecord.setLinkId(linkId);
        activityZhongqiuRecord.setFund(fund);
        activityZhongqiuRecord.setUsableFund(usableFund);
        activityZhongqiuRecord.setCreated(DateUtil.getSystemTimeSeconds());
        zhongqiuExchangeRepository.saveActivityZhongqiuRecord(activityZhongqiuRecord);

    }


    /**
     * 查询导出数据
     *
     * @param filter
     * @return
     */
    @Override
    public List<Object[]> findExportData(Map<String, Object> filter) {
        return zhongqiuExchangeRepository.findExportData(filter);
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
            fileName = "/zhongqiu" + DateUtil.getSystemTimeSeconds() + ".xls";
            String filePath = address + fileName;
            POIUtil.exportExcel2FilePath("中秋活动兑换", filePath, headInfo, rowInfo);
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
        String[] titles = new String[]{"用户名", "手机号", "类型", "状态", "产品名称", "收货人", "收货手机号", "收货地址", "兑换时间"};
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


    public IActivityZhongqiuExchangeRepository getActivityZhongqiuExchangeRepository() {
        return activityZhongqiuExchangeRepository;
    }

    public void setActivityZhongqiuExchangeRepository(IActivityZhongqiuExchangeRepository activityZhongqiuExchangeRepository) {
        this.activityZhongqiuExchangeRepository = activityZhongqiuExchangeRepository;
    }

    public ActivityZhongqiuExchangeRepository getZhongqiuExchangeRepository() {
        return zhongqiuExchangeRepository;
    }

    public void setZhongqiuExchangeRepository(ActivityZhongqiuExchangeRepository zhongqiuExchangeRepository) {
        this.zhongqiuExchangeRepository = zhongqiuExchangeRepository;
    }
}
