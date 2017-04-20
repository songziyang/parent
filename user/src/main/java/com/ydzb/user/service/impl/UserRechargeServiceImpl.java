package com.ydzb.user.service.impl;

import com.baofoo.sdk.lang.string.StringUtil;
import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.core.utils.ExcelUtil;
import com.ydzb.core.utils.POIUtil;
import com.ydzb.user.entity.UserRecharge;
import com.ydzb.user.repository.IUserRechargeRepository;
import com.ydzb.user.repository.UserRechargeRepository;
import com.ydzb.user.service.IUserRechargeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class UserRechargeServiceImpl extends BaseServiceImpl<UserRecharge, Long> implements IUserRechargeService {


    @Autowired
    private IUserRechargeRepository userRechargeRepository;
    @Autowired
    private UserRechargeRepository cUserRechargeRepository;


    @Override
    public BigDecimal findSumRecharge(String startTime, String endTime) {

        if (StringUtils.isEmpty(startTime) || StringUtils.isEmpty(endTime)) {
            Long lg = DateUtil.getSystemTimeDay(DateUtil.getCurrentDate());
            Long lt = DateUtil.getSystemTimeDay(DateUtil.getCurrentDate()) + 24 * 3600;
            return userRechargeRepository.findSumRecharge(lg, lt);
        } else {
            Long lg = DateUtil.getSystemTimeSeconds(startTime + ":00");
            Long lt = DateUtil.getSystemTimeSeconds(endTime + ":00");
            System.out.println("lg = [" + lg + "], lt = [" + lt + "]");
            return userRechargeRepository.findSumRecharge(lg, lt);
        }

    }

    /**
     * 查询导出excel所需的数据
     *
     * @return
     */
    @Override
    public List<Object> findExportData(Map<String, Object> filters) {
        return cUserRechargeRepository.findExportData(filters);
    }

    @Override
    public String exportExcele(List<Object> userRecharges, String address) {
        String fileName = "";
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            List<Map<String, Object>> headInfoList = new ArrayList<Map<String, Object>>();

            Map<String, Object> itemMap = new HashMap<String, Object>();

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "用户名");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "username");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "手机号");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "mobile");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "请求流水");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "orderNumber");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "充值金额");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "money");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "充值时间");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "rechargeTime");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "充值类型");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "rechargetype");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "充值渠道");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "onlines");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "交易订单号");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "serialNumber");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "充值成功时间");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "rechargeSucceedTime");
            headInfoList.add(itemMap);

            List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

            Map<String, Object> dataItem = null;

            for (Object userRecharge : userRecharges) {
                Object[] cells = (Object[]) userRecharge;
                dataItem = new HashMap<String, Object>();
                dataItem.put("username", cells[0] == null ? "" : cells[0]);
                dataItem.put("mobile", cells[1] == null ? "" : cells[1]);
                dataItem.put("orderNumber", cells[2] == null ? "" : cells[2]);
                dataItem.put("money", cells[3] == null ? "" : cells[3]);
                dataItem.put("rechargeTime", cells[4] == null ? "" : format.format(DateUtil.getSystemTimeMillisecond(((Integer) cells[4]).longValue())));
                dataItem.put("serialNumber", cells[5] == null ? "" : cells[5]);
                dataItem.put("rechargeSucceedTime", cells[6] == null ? "" : format.format(DateUtil.getSystemTimeMillisecond(((Integer) cells[6]).longValue())));
                dataItem.put("rechargetype", cells[7] == null ? "" : getRechargetype(cells[7]));
                dataItem.put("onlines", cells[8] == null ? "" : getOnlines(cells[8]));
                dataList.add(dataItem);
            }

            fileName = "/rechargeday" + DateUtil.getSystemTimeSeconds() + ".xls";
            String filePath = address + fileName;

            POIUtil.exportExcel2FilePath("充值记录", filePath, headInfoList, dataList);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileName;
    }

    /**
     * 导出充值状态
     *
     * @param userRecharges
     * @param address
     * @return
     */
    @Override
    public String exportRechargeState(List<Object[]> userRecharges, String address) {
        String fileName = "";
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            List<Map<String, Object>> headInfoList = new ArrayList<Map<String, Object>>();

            Map<String, Object> itemMap = new HashMap<String, Object>();

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "用户名");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "username");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "手机号");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "mobile");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "真实姓名");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "realname");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "请求流水");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "requestJournal");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "请求时间");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "requestTime");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "充值状态");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "rechargeState");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "充值金额");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "rechargeFund");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "充值类型");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "rechargetype");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "充值渠道");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "onlines");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "响应时间");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "responseTime");
            headInfoList.add(itemMap);

            List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

            Map<String, Object> dataItem = null;

            for (Object[] row : userRecharges) {
                if (row == null) {
                    continue;
                }
                dataItem = new HashMap<String, Object>();
                dataItem.put("username", row[0] == null ? "" : row[0]);
                dataItem.put("mobile", row[1] == null ? "" : row[1]);
                dataItem.put("requestJournal", row[2] == null ? "" : row[2]);
                dataItem.put("requestTime", row[3] == null ? "" : format.format(DateUtil.getSystemTimeMillisecond(((Integer) row[3]).longValue())));
                dataItem.put("rechargeState", getRechargeState((String) row[4]));
                dataItem.put("rechargeFund", getRechargeFund((String) row[4], row[5] == null ? null : (BigDecimal) row[5]));
                dataItem.put("rechargetype", getRechargetype(row[7]));
                dataItem.put("onlines", getOnlines(row[8]));
                dataItem.put("responseTime", getRechargeSuccessDate((String) row[4], row[6] == null ? null : ((Integer) row[6]).longValue()));
                dataItem.put("realname", row[9] == null ? "" : row[9].toString());
                dataList.add(dataItem);
            }

            fileName = "/rechargestate" + DateUtil.getSystemTimeSeconds() + ".xls";
            String filePath = address + fileName;

            POIUtil.exportExcel2FilePath("充值状态", filePath, headInfoList, dataList);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileName;
    }

    @Override
    public String exportDetail(List<Object[]> userInfos, String address) {
        String fileName = "";
        try {
            String[] names = {"用户名", "手机号", "充值总金额", "提现总金额"};
            String[] keys = {"username", "mobile", "allRecharge", "allWithdraw"};
            List<Map<String, Object>> headInfoList = ExcelUtil.setHeadInfo(names, keys);
            List<Map<String, Object>> dataList = setData(keys, userInfos);
            fileName = "/rechargedetail_" + DateUtil.getSystemTimeSeconds() + ".xls";
            String filePath = address + fileName;
            POIUtil.exportExcel2FilePath("充值详细", filePath, headInfoList, dataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }

    /**
     * 设置数据
     *
     * @param keys
     * @param userInfos
     * @return
     */
    private List<Map<String, Object>> setData(String[] keys, List<Object[]> userInfos) {
        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
        Map<String, Object> dataItem = null;
        for (Object[] row : userInfos) {
            if (row == null) {
                continue;
            }
            dataItem = new HashMap<String, Object>();
            dataItem.put(keys[0], row[0] == null ? "" : row[0]);
            dataItem.put(keys[1], row[1] == null ? "" : row[1]);
            dataItem.put(keys[2], row[2] == null ? "" : row[2]);
            dataItem.put(keys[3], row[3] == null ? "" : row[3]);
            dataList.add(dataItem);
        }
        return dataList;
    }

    /**
     * 获得充值状态
     *
     * @param state
     * @return
     */
    private String getRechargeState(String state) {
        if (StringUtils.isEmpty(state)) {
            return "无响应";
        }
        switch (state) {
            case "0":
                return "充值失败";
            case "1":
                return "充值成功";
            default:
                return "";
        }
    }

    /**
     * 获得充值金额
     *
     * @param state
     * @param money
     * @return
     */
    private String getRechargeFund(String state, BigDecimal money) {
        if ("0".equals(state) || StringUtils.isEmpty(state)) {
            return "无响应";
        } else if ("1".equals(state)) {
            if (money == null) {
                return "";
            }
            return money.toString();
        }
        return "";
    }

    /**
     * 获得充值响应时间
     *
     * @param state
     * @param rechargeSuccessDate
     * @return
     */
    private String getRechargeSuccessDate(String state, Long rechargeSuccessDate) {
        if ("0".equals(state) || StringUtils.isEmpty(state)) {
            return "无响应";
        } else if ("1".equals(state)) {
            if (rechargeSuccessDate == null) {
                return "";
            }
            return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(DateUtil.getSystemTimeMillisecond(rechargeSuccessDate));
        }
        return "";
    }

    /**
     * 获取充值类型
     * @return
     */
    private String getRechargetype(Object rechargetype) {
        if (null != rechargetype) {
            if ("1".equals(rechargetype.toString())) {
                return "电子账户充值";
            }
        }
        return  "银多账户充值";
    }

    /**
     * 获取充值渠道
     * @param onlines
     * @return
     */
    private String getOnlines(Object onlines) {
        if (null != onlines) {
            if ("1".equals(onlines.toString())) {
                return "线上充值";
            }
        }
        return "线下充值";
    }

    /**
     * 查询充值详细导出excel所需的数据
     *
     * @param filters
     * @return
     */
    public List<Object[]> findExportDetailData(Map<String, Object> filters) {
        return cUserRechargeRepository.findExportDetailData(filters);
    }

    /**
     * 查询充值状态导出excel所需的数据
     *
     * @param filter
     * @return
     */
    @Override
    public List<Object[]> findExportResponseData(Map<String, Object> filter) {
        return cUserRechargeRepository.findExportResponseData(filter);
    }

    public IUserRechargeRepository getUserRechargeRepository() {
        return userRechargeRepository;
    }

    public void setUserRechargeRepository(IUserRechargeRepository userRechargeRepository) {
        this.userRechargeRepository = userRechargeRepository;
    }
}
