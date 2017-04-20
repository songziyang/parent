package com.ydzb.user.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.ydzb.admin.shiro.ShiroUser;
import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.core.utils.Exceptions;
import com.ydzb.core.utils.POIUtil;
import com.ydzb.user.entity.*;
import com.ydzb.user.repository.*;
import com.ydzb.user.service.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class UserServiceImpl extends BaseServiceImpl<User, Long> implements IUserService {

    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IUserMoneyRepository userMoneyRepository;
    @Autowired
    private IUserInfoRepository userInfoRepository;
    @Autowired
    private IUserFundInLogService userFundInLogService;
    @Autowired
    private IUserFundRecordService userFundRecordService;
    @Autowired
    private IUserManualRechargeService userManualRechargeService;
    @Autowired
    private IUserInvestinfoRepository userInvestinfoRepository;
    @Autowired
    private UserRepository cUserRepository;
    @Autowired
    private IUserReferralRepository userReferralRepository;
    @Autowired
    private IVipSetRecordService vipSetRecordService;
    @Autowired
        private IUserVipChangeService userVipChangeService;

    Logger logger = Logger.getLogger(UserServiceImpl.class);


    @Override
    public String userSettingLeve(Long userId, Long vipGradeId) throws Exception {

        String condition = checkCondition(userId, vipGradeId);
        if (StringUtils.isNotEmpty(condition)) {
            return condition;
        }
        userVipChangeService.createOne(userId, vipGradeId, UserVipChange.OTYPE_MANUAL);
        vipSetRecordService.updateStatus(userId, VipSetRecord.STATUS_NOTEXPIRE, VipSetRecord.STATUS_CANCEL);
        vipSetRecordService.createOne(userId, vipGradeId);
        updateVipGrade(userId, vipGradeId);
        return "设置成功";
    }

    /**
     * 设置电子账户类型
     * @param userId
     * @param accounttype
     * @return
     * @throws Exception
     */
    @Override
    public String userSettingAccountType(Long userId, Integer accounttype) throws Exception {
        User user = findOne(userId);
        if (user == null) {
            return "用户不存在";
        }
        user.setAccountType(accounttype);
        userRepository.save(user);
        return "设置成功";
    }

    /**
     * 判断条件
     * @param userId
     * @param vipGradeId
     * @return
     */
    private String checkCondition(Long userId, Long vipGradeId) {

        User user = findOne(userId);
        if (user == null) {
            return "不存在该用户";
        }
        VipGrade vipGrade = user.getUserLeve();
        if (vipGrade != null && vipGrade.getId().equals(vipGradeId)) {
            return "不能重复设置vip等级";
        }
        return null;
    }

    /**
     * 更新vip等级
     * @param userId
     * @param vipGradeId
     * @throws Exception
     */
    private void updateVipGrade(Long userId, Long vipGradeId) throws Exception {

        User user = findOne(userId);
        if (user == null) {
            logger.error("手动调整vip等级时,查询的用户id为" + userId + "的用户不存在");
            throw new Exception("不存在该用户;");
        }
        user.setUserLeve(new VipGrade(vipGradeId));
        user.setLeveTime(DateUtil.getSystemTimeSeconds());
        save(user);
    }

    @Override
    public boolean findUserByIdCard(String idCard) {
        List<User> users = userRepository.findUserByIdCard(idCard);
        if (users != null && !users.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * 删除用户
     *
     * @param id
     * @throws Exception
     */
    @Override
    public void deleteUser(Long id) throws Exception {
        User user = userRepository.findOne(id);
        if (user != null) {
            user.setStatus(-1);
            userRepository.save(user);
        }
    }

    /**
     * 删除用户
     *
     * @param ids
     * @throws Exception
     */
    @Override
    public void deleteUser(Long[] ids) throws Exception {
        List<User> userLst = new ArrayList<User>();
        if (ids != null && ids.length > 0) {
            for (Long id : ids) {
                User user = userRepository.findOne(id);
                if (user != null) {
                    user.setStatus(-1);
                    userLst.add(user);
                }
            }
            userRepository.save(userLst);
        }

    }


    @Override
    public void saveRecharge(Long userId, BigDecimal fund, String remark) throws Exception {
        User user = userRepository.findOne(userId);
        if (user != null && fund != null && fund.doubleValue() > 0) {
            Subject currentUser = SecurityUtils.getSubject();
            ShiroUser shiroUser = (ShiroUser) currentUser.getPrincipal();
            if (shiroUser != null) {

                //用户资金修改
                UserMoney userMoney = userMoneyRepository.findUserMoneyById(user.getUserMoney().getId());
                userMoney.setTotalFund(fund.add(userMoney.getTotalFund()));
                userMoney.setUsableFund(fund.add(userMoney.getUsableFund()));
                userMoneyRepository.save(userMoney);

                //用户充值累计
                UserInfo userInfo = userInfoRepository.findUserInfoById(user.getUserInfo().getId());
                userInfo.setAllRecharge(fund.add(userInfo.getAllRecharge()));
                userInfoRepository.save(userInfo);

                //手动充值记录
                Long manualId = userManualRechargeService.saveUserManualRecharge(user, fund, shiroUser.getId(), remark);

                //进账日志
                Long inLogId = userFundInLogService.saveUserFundInLog(userId, manualId, 14, fund, BigDecimal.ZERO, userMoney.getUsableFund(), remark);

                //资金记录
                userFundRecordService.saveUserFundRecord(userId, FundFlow.RECHARGE, fund, 1, 0, userMoney.getUsableFund(), inLogId);

            }
        }
    }


    @Override
    public BigDecimal findHqbje() {
        return userRepository.findHqbje();
    }

    @Override
    public BigDecimal findHqbrs() {
        return userRepository.findHqbrs();
    }

    @Override
    public BigDecimal findDcbje() {
        return userRepository.findDcbje();
    }

    @Override
    public BigDecimal findDcbrs() {
        return userRepository.findDcbrs();
    }

    @Override
    public BigDecimal findXsbje() {
        return userRepository.findXsbje();
    }

    @Override
    public BigDecimal findXsbrs() {
        return userRepository.findXsbrs();
    }

    @Override
    public BigDecimal findFreeUserCount() {
        return userRepository.findFreeUserCount();
    }

    @Override
    public BigDecimal findZhuanUserCount() {
        return userRepository.findZhuanUserCount();
    }

    @Override
    public BigDecimal findMljrUserCount() {
        return userRepository.findMljrUserCount();
    }

    @Override
    public BigDecimal findFreeUserInvestFund() {
        return userRepository.findFreeUserInvestFund();
    }

    @Override
    public BigDecimal findZhuanUserInvestFund() {
        return userRepository.findZhuanUserInvestFund();
    }

    @Override
    public BigDecimal findMljrUserInvestFund() {
        return userRepository.findMljrUserInvestFund();
    }


    @Override
    public BigDecimal findHqbtyj() {
        return userRepository.findHqbtyj();
    }

    @Override
    public BigDecimal findHqbrmb() {
        return userRepository.findHqbrmb();
    }

    @Override
    public BigDecimal findWjb() {
        return userRepository.findWjb();
    }

    @Override
    public BigDecimal findSrdz() {
        return userRepository.findSrdz();
    }

    @Override
    public BigDecimal findZtjrs() {
        return userRepository.findZtjrs();
    }

    @Override
    public String exportUser(List<Object[]> users, String address) {

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
            itemMap.put("title", "年龄");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "age");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "性别");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "gender");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "推荐人手机号");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "referralMobile");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "投资用户");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "investUser");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "用户类型");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "userType");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "用户等级");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "userLeve");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "电子账户类型");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "isLogin");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "是否买单侠用户");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "cardVerifyNum");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "累计积分");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "totalIntegral");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "可用积分");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "integral");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "注册时间");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "created");
            headInfoList.add(itemMap);

            List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

            Map<String, Object> dataItem = null;

            for (Object[] row : users) {
                if (row == null) {
                    continue;
                }
                dataItem = new HashMap<String, Object>();
                dataItem.put("username", row[0] == null ? "" : row[0]);
                dataItem.put("mobile", row[1] == null ? "" : row[1]);
                dataItem.put("referralMobile", row[2] == null ? "" : row[2]);

                if (row[3] != null && ((BigDecimal) row[3]).compareTo(BigDecimal.ZERO) > 0) {
                    dataItem.put("investUser", "是");
                } else {
                    dataItem.put("investUser", "否");
                }

                if (row[4] != null) {
                    if ((Integer) row[4] == 0) {
                        dataItem.put("userType", "普通用户");
                    } else if ((Integer) row[4] == 1) {
                        dataItem.put("userType", "老用户");
                    } else if ((Integer) row[4] == 2) {
                        dataItem.put("userType", "股东用户");
                    } else if ((Integer) row[4] == 3) {
                        dataItem.put("userType", "业务员用户");
                    } else if ((Integer) row[4] == 4) {
                        dataItem.put("userType", "新鲜说用户");
                    } else if ((Integer) row[4] == 5) {
                        dataItem.put("userType", "麦罗用户");
                    }
                } else {
                    dataItem.put("userType", "");
                }

                dataItem.put("userLeve", row[5] == null ? "" : row[5]);

                if (row[6] == null) {
                    dataItem.put("accountType", "");
                }
                else if ((Integer) row[6] == 1) {
                    dataItem.put("accountType", "普通用户");
                }
                else if ((Integer) row[6] == 2) {
                    dataItem.put("accountType", "债权用户");
                }
                else if ((Integer) row[6] == 3) {
                    dataItem.put("accountType", "美利用户");
                }
                else if ((Integer) row[6] == 4) {
                    dataItem.put("accountType", "红包账户");
                }
                else if ((Integer) row[6] == 5) {
                    dataItem.put("accountType", "手续费账户");
                }
                else {
                    dataItem.put("accountType", "");
                }

                dataItem.put("cardVerifyNum", (row[7] != null && (Integer) row[7] == -1) ? "是" : "否");
                dataItem.put("totalIntegral", row[8] == null ? 0 : row[8]);
                dataItem.put("integral", row[9] == null ? 0 : row[9]);
                dataItem.put("created", row[10] == null ? "" : format.format(DateUtil.getSystemTimeMillisecond(((Integer) row[10]).longValue())));
                dataItem.put("age", getAge((String) row[11]));
                dataItem.put("gender", getGender((String) row[11]));
                dataList.add(dataItem);
            }

            fileName = "/user" + DateUtil.getSystemTimeSeconds() + ".xls";
            String filePath = address + fileName;

            POIUtil.exportExcel2FilePath("用户信息", filePath, headInfoList, dataList);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileName;

    }

    private String getAge(String idCard) {
        try {
            return StringUtils.isEmpty(idCard)? null: Calendar.getInstance().get(Calendar.YEAR) - Integer.valueOf(idCard.substring(6, 10)) + "";
        } catch (Exception e) {
            return null;
        }
    }

    private String getGender(String idCard) {
        try {
            if (StringUtils.isEmpty(idCard)) return null;
            int sex = Integer.valueOf(idCard.substring(16, 17));
            if (sex % 2 == 0) {
                return "女";
            }
            return "男";
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Object[]> findExportData(Map<String, Object> filter) {
        return cUserRepository.findExportData(filter);
    }

    @Override
    public String exportUserMoney(List<Object[]> users, String address) {
        String fileName = "";
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:ss");

            List<Map<String, Object>> headInfoList = new ArrayList<Map<String, Object>>();

            Map<String, Object> itemMap = new HashMap<String, Object>();
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
            itemMap.put("title", "账户总额");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "totalFund");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "账户余额");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "usableFund");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "收益总额");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "allIncome");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "冻结金额");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "freezeFund");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "体验金总额");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "amount");
            headInfoList.add(itemMap);


            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "体验金余额");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "ableMoney");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "体验金冻结金额");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "investFreezeFund");
            headInfoList.add(itemMap);


            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "活期宝");
            itemMap.put("columnWidth", 50);
            itemMap.put("dataKey", "allInvestDayloan");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "定存宝");
            itemMap.put("columnWidth", 50);
            itemMap.put("dataKey", "allInvestDeposit");
            headInfoList.add(itemMap);

            itemMap = new HashMap<>();
            itemMap.put("title", "随心存");
            itemMap.put("columnWidth", 50);
            itemMap.put("dataKey", "allInvestFree");
            headInfoList.add(itemMap);

            itemMap = new HashMap<>();
            itemMap.put("title", "新手宝");
            itemMap.put("columnWidth", 50);
            itemMap.put("dataKey", "allInvestPrivilege");
            headInfoList.add(itemMap);

            List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

            Map<String, Object> dataItem = null;
            for (Object[] row : users) {
                if (row == null) {
                    continue;
                }
                dataItem = new HashMap<String, Object>();
                dataItem.put("username", row[0] == null ? "" : row[0]);
                dataItem.put("mobile", row[1] == null ? "" : row[1]);
                dataItem.put("totalFund", row[2] == null ? "" : row[2]);
                dataItem.put("usableFund", row[3] == null ? "" : row[3]);
                dataItem.put("allIncome", row[10] == null ? "" : row[10]);
                dataItem.put("freezeFund", row[4] == null ? "" : row[4]);
                dataItem.put("amount", row[5] == null ? "" : row[5]);
                dataItem.put("ableMoney", row[6] == null ? "" : row[6]);
                dataItem.put("investFreezeFund", row[7] == null ? "" : row[7]);
                dataItem.put("allInvestDayloan", row[8] == null ? "" : row[8]);
                dataItem.put("allInvestDeposit", row[9] == null ? "" : row[9]);
                dataItem.put("allInvestFree", row[11] == null ? "" : row[11]);
                dataItem.put("allInvestPrivilege", row[12] == null ? "" : row[12]);
                dataList.add(dataItem);
            }

            fileName = "/fund" + DateUtil.getSystemTimeSeconds() + ".xls";
            String filePath = address + fileName;

            POIUtil.exportExcel2FilePath("用户资金", filePath, headInfoList, dataList);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }

    @Override
    public List<Object[]> findMoneyExportData(Map<String, Object> filter) {
        return cUserRepository.findMoneyExportData(filter);
    }

    @Override
    public String exportRefferralUser(List<Object[]> users, String address) {
        String fileName = "";
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:ss");

            List<Map<String, Object>> headInfoList = new ArrayList<Map<String, Object>>();

            Map<String, Object> itemMap = new HashMap<String, Object>();
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
            itemMap.put("title", "用户类型");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "userType");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "注册时间");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "registerTime");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "推荐人数");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "refferralNum");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "充值总额");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "rechargeFund");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "投资总额");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "investFund");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "活期总额");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "currentFund");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "定存总额");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "regularFund");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "收益总额");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "revenueFund");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "提现总额");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "withdrawFund");
            headInfoList.add(itemMap);

            List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

            Map<String, Object> dataItem = null;
            for (Object[] row : users) {
                if (row == null) {
                    continue;
                }
                dataItem = new HashMap<String, Object>();
                dataItem.put("username", row[0] == null ? "" : row[0]);
                dataItem.put("mobile", row[1] == null ? "" : row[1]);
                dataItem.put("userType", getUserType(row[2]));
                dataItem.put("registerTime", row[3] == null ? "" :
                        new SimpleDateFormat("yyyy年MM月dd日 HH时mm分").format(DateUtil.getSystemTimeMillisecond(((Integer) row[3]).longValue())));
                dataItem.put("refferralNum", row[4] == null ? 0 : row[4]);
                dataItem.put("rechargeFund", row[5] == null ? BigDecimal.ZERO : row[5]);
                dataItem.put("investFund", row[6] == null ? BigDecimal.ZERO : row[6]);
                dataItem.put("currentFund", row[7] == null ? BigDecimal.ZERO : row[7]);
                dataItem.put("regularFund", row[8] == null ? BigDecimal.ZERO : row[8]);
                dataItem.put("revenueFund", row[9] == null ? BigDecimal.ZERO : row[9]);
                dataItem.put("withdrawFund", row[10] == null ? BigDecimal.ZERO : row[10]);
                dataList.add(dataItem);
            }

            fileName = "/refferral" + DateUtil.getSystemTimeSeconds() + ".xls";
            String filePath = address + fileName;

            POIUtil.exportExcel2FilePath("用户推荐", filePath, headInfoList, dataList);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }

    @Override
    public List<Object[]> findReferralExportData(Map<String, Object> filter) {
        return cUserRepository.findReferralExportData(filter);
    }

    /**
     * 根据用户名或手机号查找用户
     */
    @Override
    public String findByUsernameOrMobile(String username, String mobile) {
        List<User> users = userRepository.findByUsernameOrMobile(username,
                mobile);
        List<Map<String, Object>> jsonList = new ArrayList<Map<String, Object>>();
        Map<String, Object> jsonMap;
        for (User user : users) {
            jsonMap = new HashMap<String, Object>();
            jsonMap.put("id", user.getId());
            jsonMap.put("username", user.getUsername());
            jsonMap.put("mobile", user.getMobile());
            jsonList.add(jsonMap);
        }
        return JSONArray.toJSONString(jsonList);
    }

    /**
     * 根据手机号判断是否存在拥有相同手机号或用户名的用户
     *
     * @param mobile
     * @param userId
     * @return
     */
    @Override
    public String existUsernameOrMobile(String mobile, Long userId) {
        List<User> users = userRepository.findByUsernameOrMobile(mobile, mobile);
        if (users == null || users.isEmpty()) {
            User user = userRepository.findOne(userId);
            user.setMobile(mobile);
            userRepository.save(user);
            return "设置手机号成功";
        }
        //如果只包含当前用户
        if (users.size() == 1 && users.get(0).getId().equals(userId)) {
            User user = userRepository.findOne(userId);
            user.setMobile(mobile);
            userRepository.save(user);
            return "设置手机号成功";
        }
        return "设置失败，已存在该用户名或手机号";
    }

    /**
     * 设置微信号
     *
     * @param user         目标用户
     * @param wechatNumber 微信号
     * @return
     * @throws Exception
     */
    @Override
    public String setWechatNumber(User user, String wechatNumber) throws Exception {
        if (user == null) {
            return "设置失败";
        }
        user.setWechatNumber(wechatNumber);
        userRepository.save(user);
        return "设置微信号成功";
    }

    /**
     * 设置qq号
     *
     * @param user     目标用户
     * @param qqNumber qq号
     * @return
     * @throws Exception
     */
    @Override
    public String setQqNumber(User user, String qqNumber) throws Exception {
        if (user == null) {
            return "设置失败";
        }
        user.setQqNumber(qqNumber);
        userRepository.save(user);
        return "设置qq号成功";
    }


    @Override
    public String setBuyLimit(User user, Integer buyLimit) throws Exception {
        if (user == null || user.getUserInvestinfo() == null) {
            return "设置失败";
        }
        UserInvestinfo userInvestinfo = user.getUserInvestinfo();
        userInvestinfo.setBuyLimit(buyLimit);
        userInvestinfoRepository.save(userInvestinfo);
        return "设置成功";
    }

    @Override
    public String setRedeemLimit(User user, Integer redeemLimit) throws Exception {
        if (user == null || user.getUserInvestinfo() == null) {
            return "设置失败";
        }
        UserInvestinfo userInvestinfo = user.getUserInvestinfo();
        userInvestinfo.setRedeemLimit(redeemLimit);
        userInvestinfoRepository.save(userInvestinfo);
        return "设置成功";
    }

    /**
     * 设置推荐人手机号
     *
     * @param user
     * @param refferralUserId
     * @return
     */
    @Override
    public String setRefferralUser(User user, Long refferralUserId) {
        User refferralUser = findOne(refferralUserId);
        if (refferralUser == null) {
            return "设置失败，不存在该推荐人";
        }
        //设置推荐人手机号
        user.setReferralMobile(refferralUser.getMobile());
        userRepository.save(user);
        //推荐人推荐数量+1
        refferralUser.setReferralNum(refferralUser.getReferralNum() + 1);
        userRepository.save(refferralUser);
        //保存推荐关系
        UserReferral userReferral = new UserReferral();
        userReferral.setUser(refferralUser);
        userReferral.setReferralUser(user);
        userReferral.setReferralTime(DateUtil.getSystemTimeSeconds());
        userReferralRepository.save(userReferral);
        return "设置推荐人手机号成功";
    }

    /**
     * 获得用户类型
     *
     * @param type
     * @return
     */
    private String getUserType(Object type) {
        if (type == null) {
            return "普通用户";
        }
        Integer userType = (Integer) type;
        switch (userType) {
            case 0:
                return "普通用户";
            case 1:
                return "老用户";
            case 2:
                return "股东用户";
            case 3:
                return "业务员用户";
            case 4:
                return "新鲜说用户";
            case 5:
                return "麦罗用户";
            default:
                return "普通用户";
        }
    }


    @Override
    public String exportVipBirthdayUser(List<Object[]> users, String address) {
        String fileName = "";
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:ss");

            List<Map<String, Object>> headInfoList = new ArrayList<Map<String, Object>>();

            Map<String, Object> itemMap = new HashMap<String, Object>();
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
            itemMap.put("title", "出生年月");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "birthday");
            headInfoList.add(itemMap);


            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "注册时间");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "registerTime");
            headInfoList.add(itemMap);


            List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

            Map<String, Object> dataItem = null;
            for (Object[] row : users) {
                if (row == null) {
                    continue;
                }
                dataItem = new HashMap<String, Object>();
                dataItem.put("username", row[0] == null ? "" : row[0]);
                dataItem.put("mobile", row[1] == null ? "" : row[1]);
                dataItem.put("birthday", getBirthday(row[2]));
                dataItem.put("registerTime", row[3] == null ? "" :
                        new SimpleDateFormat("yyyy年MM月dd日 HH时mm分").format(DateUtil.getSystemTimeMillisecond(((Integer) row[3]).longValue())));
                dataList.add(dataItem);
            }

            fileName = "/vip" + DateUtil.getSystemTimeSeconds() + ".xls";
            String filePath = address + fileName;
            POIUtil.exportExcel2FilePath("用户推荐", filePath, headInfoList, dataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }


    private String getBirthday(Object card) {
        if (card == null) {
            return "";
        }
        String idCard = (String) card;
        if (StringUtils.isNotEmpty(idCard)) {
            return idCard.substring(6, 10) + "年" + idCard.substring(10, 12) + "月" + idCard.substring(12, 14) + "日";
        }
        return "";
    }

    @Override
    public List<Object[]> findVipBirthdayExportData(Map<String, Object> filter) {
        return cUserRepository.findVipBirthdayExportData(filter);
    }

    @Override
    public List<User> findVipBirthdayByDate(Map<String, Object> filter) {
        return cUserRepository.findVipBirthdayByDate((String) filter.get("birthdayDate"));
    }

    /**
     * 查询与银多同一天生日的用户
     *
     * @param filter
     * @return
     */
    @Override
    public List<User> findSameBirthdayUsers(Map<String, Object> filter) {
        return cUserRepository.findSameBirthdayUsers(filter);
    }

    @Override
    public List<Object[]> findSameBirthdayExportData(Map<String, Object> filter) {
        return cUserRepository.findSameBirthdayExportData(filter);
    }

    @Override
    public String exportSameBirthdayUser(List<Object[]> users, String address) {
        String fileName = "";
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:ss");

            List<Map<String, Object>> headInfoList = new ArrayList<Map<String, Object>>();

            Map<String, Object> itemMap = new HashMap<String, Object>();
            itemMap.put("title", "真实姓名");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "realName");
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
            itemMap.put("title", "推荐人手机号");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "referralMobile");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "用户类型");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "userType");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "用户等级");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "vipGrade");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "投资总额");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "allInvest");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "出生年月");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "birthday");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "注册时间");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "registerTime");
            headInfoList.add(itemMap);


            List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

            Map<String, Object> dataItem = null;
            for (Object[] row : users) {
                if (row == null) {
                    continue;
                }
                dataItem = new HashMap<String, Object>();
                dataItem.put("realName", row[0] == null ? "" : row[0]);
                dataItem.put("username", row[1] == null ? "" : row[1]);
                dataItem.put("mobile", row[2] == null ? "" : row[2]);
                dataItem.put("referralMobile", row[3] == null ? "" : row[3]);
                dataItem.put("userType", getUserType(row[4]));
                dataItem.put("vipGrade", row[5] == null ? "" : row[5]);
                dataItem.put("allInvest", row[6] == null ? "" : row[6]);
                dataItem.put("birthday", getBirthday(row[7]));
                dataItem.put("registerTime", row[8] == null ? "" :
                        new SimpleDateFormat("yyyy年MM月dd日 HH时mm分").format(DateUtil.getSystemTimeMillisecond(((Integer) row[8]).longValue())));
                dataList.add(dataItem);
            }

            fileName = "/samebirthday" + DateUtil.getSystemTimeSeconds() + ".xls";
            String filePath = address + fileName;
            POIUtil.exportExcel2FilePath("周年生日", filePath, headInfoList, dataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }

    public IUserRepository getUserRepository() {
        return userRepository;
    }

    public void setUserRepository(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public IUserMoneyRepository getUserMoneyRepository() {
        return userMoneyRepository;
    }

    public void setUserMoneyRepository(IUserMoneyRepository userMoneyRepository) {
        this.userMoneyRepository = userMoneyRepository;
    }

    public IUserInfoRepository getUserInfoRepository() {
        return userInfoRepository;
    }

    public void setUserInfoRepository(IUserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    public IUserFundInLogService getUserFundInLogService() {
        return userFundInLogService;
    }

    public void setUserFundInLogService(IUserFundInLogService userFundInLogService) {
        this.userFundInLogService = userFundInLogService;
    }

    public IUserFundRecordService getUserFundRecordService() {
        return userFundRecordService;
    }

    public void setUserFundRecordService(IUserFundRecordService userFundRecordService) {
        this.userFundRecordService = userFundRecordService;
    }

    public IUserManualRechargeService getUserManualRechargeService() {
        return userManualRechargeService;
    }

    public void setUserManualRechargeService(IUserManualRechargeService userManualRechargeService) {
        this.userManualRechargeService = userManualRechargeService;
    }

    public IUserInvestinfoRepository getUserInvestinfoRepository() {
        return userInvestinfoRepository;
    }

    public void setUserInvestinfoRepository(IUserInvestinfoRepository userInvestinfoRepository) {
        this.userInvestinfoRepository = userInvestinfoRepository;
    }

    public UserRepository getcUserRepository() {
        return cUserRepository;
    }

    public void setcUserRepository(UserRepository cUserRepository) {
        this.cUserRepository = cUserRepository;
    }
}
