package com.ydzb.withdraw.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.core.utils.ExcelUtil;
import com.ydzb.core.utils.POIUtil;
import com.ydzb.user.entity.UserWithDrawNum;
import com.ydzb.user.repository.IUserRechargeRepository;
import com.ydzb.user.repository.UserWithDrawNumRepository;
import com.ydzb.withdraw.repository.IUserWithDrawRepository;
import com.ydzb.withdraw.repository.UserWithDrawRepository;
import com.ydzb.withdraw.service.IUserWithDrawNumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户提现次数service实现
 */
@Service
public class UserWithDrawNumServiceImpl extends BaseServiceImpl<UserWithDrawNum, Long> implements IUserWithDrawNumService {

    @Autowired
    private UserWithDrawRepository userWithDrawRepository;
    @Autowired
    private UserWithDrawNumRepository userWithDrawNumRepository;
    @Autowired
    private IUserWithDrawRepository withDrawRepository;
    @Autowired
    private IUserRechargeRepository userRechargeRepository;

    @Override
    public String exportExcel(List<Object[]> withDrawNums, String address) {
        String fileName = "";
        try {
            String[] names = {"用户名", "手机号", "真实姓名", "七天提现次数", "最近一次提现时间", "七天充值次数", "最近一次充值时间"};
            String[] keys = {"username", "mobile", "realName", "withdrawNum", "withdrawDate", "rechargeNum", "rechargeDate"};
            List<Map<String, Object>> headInfoList = ExcelUtil.setHeadInfo(names, keys);
            List<Map<String, Object>> dataList = setData(keys, withDrawNums);
            fileName = "/withdrawnum" + DateUtil.getSystemTimeSeconds() + ".xls";
            String filePath = address + fileName;
            POIUtil.exportExcel2FilePath("用户提现统计", filePath, headInfoList, dataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }


    /**
     * 导出体现excel
     *
     * @param userWithDraws
     * @param address
     * @return
     */
    @Override
    public String exportWithDrawExcel(List<Object[]> userWithDraws, String address) {
        String fileName = "";
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            List<Map<String, Object>> headInfoList = new ArrayList<Map<String, Object>>();
            Map<String, Object> itemMap = new HashMap<String, Object>();
            itemMap.put("title", "真实姓名");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "realname");
            headInfoList.add(itemMap);

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
            itemMap.put("title", "提现金额");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "withdrawMoney");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "打款金额");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "advanceMoney");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "到账金额");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "realMoney");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "申请时间");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "applicationTime");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "审核时间");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "auditTime");
            headInfoList.add(itemMap);

//            itemMap = new HashMap<String, Object>();
//            itemMap.put("title", "转账时间");
//            itemMap.put("columnWidth", 30);
//            itemMap.put("dataKey", "transferTime");
//            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "放款时间");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "payTime");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "状态");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "status");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "处理类型");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "drawAuto");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "提现类型");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "withdrawtype");
            headInfoList.add(itemMap);

            List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

            Map<String, Object> dataItem = null;

            for (Object[] row : userWithDraws) {
                dataItem = new HashMap<String, Object>();
                if (row == null) {
                    continue;
                }
                dataItem.put("realname", row[0] == null ? "" : row[0]);
                dataItem.put("username", row[1] == null ? "" : row[1]);
                dataItem.put("mobile", row[2] == null ? "" : row[2]);
                dataItem.put("withdrawMoney", row[3] == null ? "" : row[3]);
                dataItem.put("advanceMoney", row[4] == null ? "" : row[4]);
                dataItem.put("realMoney", row[5] == null ? "" : row[5]);
                dataItem.put("applicationTime", row[6] == null ? "" : format.format(DateUtil.getSystemTimeMillisecond(((BigInteger) row[6]).longValue())));
                dataItem.put("auditTime", row[7] == null ? "" : format.format(DateUtil.getSystemTimeMillisecond(((Integer) row[7]).longValue())));
                //dataItem.put("transferTime", row[8] == null ? "" : format.format(DateUtil.getSystemTimeMillisecond(((Integer) row[8]).longValue())));
                dataItem.put("payTime", row[9] == null ? "" : format.format(DateUtil.getSystemTimeMillisecond(((Integer) row[9]).longValue())));

                Byte status = row[10] == null ? null : ((Integer) row[10]).byteValue();
                if (status == null) {
                    dataItem.put("status", "");
                } else {
                    switch (status) {
                        case 0:
                            dataItem.put("status", "审核中");
                            break;
                        case 2:
                            dataItem.put("status", "审核失败");
                            break;
                        case 3:
                            dataItem.put("status", "打款中");
                            break;
                        case 4:
                            dataItem.put("status", "打款成功");
                            break;
                        case 5:
                            dataItem.put("status", "打款失败");
                            break;
                        case 6:
                            dataItem.put("status", "民生打款中");
                            break;
                        case 7:
                            dataItem.put("status", "民生受理失败");
                            break;
                        case 8:
                            dataItem.put("status", "分批受理失败");
                            break;
                    }
                }

                Integer drawAuto = row[11] == null ? null : ((Integer) row[11]);
                if (drawAuto == null) {
                    dataItem.put("drawAuto", "");
                } else {
                    switch (drawAuto) {
                        case 0:
                            dataItem.put("drawAuto", "手动处理");
                            break;
                        case 1:
                            dataItem.put("drawAuto", "自动处理");
                            break;
                    }
                }

                Integer withdrawtype = row[12] == null ? null : ((Integer) row[12]);
                if (withdrawtype == null) {
                    dataItem.put("withdrawtype", "银多账户提现");
                } else {
                    switch (withdrawtype) {
                        case 0:
                            dataItem.put("withdrawtype", "银多账户提现");
                            break;
                        case 1:
                            dataItem.put("withdrawtype", "电子账户提现");
                            break;
                    }
                }

                dataList.add(dataItem);
            }

            fileName = "/payback" + DateUtil.getSystemTimeSeconds() + ".xls";
            String filePath = address + fileName;

            POIUtil.exportExcel2FilePath("放款操作", filePath, headInfoList,
                    dataList);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileName;
    }

    /**
     * 设置数据
     *
     * @param keys
     * @param withDrawNums
     * @return
     */
    private List<Map<String, Object>> setData(String[] keys, List<Object[]> withDrawNums) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
        Map<String, Object> dataItem = null;
        for (Object[] row : withDrawNums) {
            if (row == null) {
                continue;
            }
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            // {"username", "mobile", "realName", "withdrawNum", "withdrawDate", "rechargeNum", "rechargeDate"};
            dataItem = new HashMap<String, Object>();
            dataItem.put(keys[0], row[0] == null ? "" : row[0]);
            dataItem.put(keys[1], row[1] == null ? "" : row[1]);
            dataItem.put(keys[2], row[2] == null ? "" : row[2]);
            dataItem.put(keys[3], row[3] == null ? 0 : row[3]);
            dataItem.put(keys[4], row[4] == null ? "" : formatter.format(DateUtil.getSystemTimeMillisecond(((BigInteger) row[4]).longValue())));
            dataItem.put(keys[5], row[5] == null ? 0 : row[5]);
            dataItem.put(keys[6], row[6] == null ? "" : formatter.format(DateUtil.getSystemTimeMillisecond(((Integer) row[6]).longValue())));
            dataList.add(dataItem);
        }
        return dataList;
    }

    /**
     * 查询提现导出excel数据
     *
     * @param filters
     * @return
     */
    @Override
    public List<Object[]> findWithdrawExportData(Map<String, Object> filters) {
        return userWithDrawRepository.findExportData(filters);
    }

    @Override
    public List<Object[]> findExportData(Map<String, Object> filter) {
        return userWithDrawNumRepository.findExportData(filter);
    }


    public UserWithDrawRepository getUserWithDrawRepository() {
        return userWithDrawRepository;
    }

    public void setUserWithDrawRepository(UserWithDrawRepository userWithDrawRepository) {
        this.userWithDrawRepository = userWithDrawRepository;
    }

    public UserWithDrawNumRepository getUserWithDrawNumRepository() {
        return userWithDrawNumRepository;
    }

    public void setUserWithDrawNumRepository(UserWithDrawNumRepository userWithDrawNumRepository) {
        this.userWithDrawNumRepository = userWithDrawNumRepository;
    }

    public IUserWithDrawRepository getWithDrawRepository() {
        return withDrawRepository;
    }

    public void setWithDrawRepository(IUserWithDrawRepository withDrawRepository) {
        this.withDrawRepository = withDrawRepository;
    }

    public IUserRechargeRepository getUserRechargeRepository() {
        return userRechargeRepository;
    }

    public void setUserRechargeRepository(IUserRechargeRepository userRechargeRepository) {
        this.userRechargeRepository = userRechargeRepository;
    }
}
