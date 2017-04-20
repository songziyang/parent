package com.ydzb.withdraw.service.impl;

import com.ydzb.admin.shiro.ShiroUser;
import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.core.utils.POIUtil;
import com.ydzb.sms.service.ISmsHandleService;
import com.ydzb.user.entity.FundFlow;
import com.ydzb.user.entity.User;
import com.ydzb.user.entity.UserInfo;
import com.ydzb.user.entity.UserMoney;
import com.ydzb.user.repository.IUserInfoRepository;
import com.ydzb.user.repository.IUserMoneyRepository;
import com.ydzb.user.service.*;
import com.ydzb.withdraw.base.Banks;
import com.ydzb.withdraw.base.Constance;
import com.ydzb.withdraw.entity.UserWithDraw;
import com.ydzb.withdraw.repository.IUserWithDrawRepository;
import com.ydzb.withdraw.repository.UserWithDrawRepository;
import com.ydzb.withdraw.repository.UserWithHugeRepository;
import com.ydzb.withdraw.service.IPayManualRequestService;
import com.ydzb.withdraw.service.IUserWithDrawService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class UserWithDrawServiceImpl extends BaseServiceImpl<UserWithDraw, Long> implements IUserWithDrawService {


    @Autowired
    private ISmsHandleService smsHandleService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IUserWithDrawRepository userWithDrawRepository;
    @Autowired
    private IUserMoneyRepository userMoneyRepository;
    @Autowired
    private IUserInfoRepository userInfoRepository;
    @Autowired
    private IUserFundInLogService userFundInLogService;
    @Autowired
    private IUserFundBlokedLogService userFundBlokedLogService;
    @Autowired
    private IUserFundRecordService userFundRecordService;
    @Autowired
    private IUserFundOutLogService userFundOutLogService;
    @Autowired
    private IPayManualRequestService payManualRequestService;
    @Autowired
    private UserWithDrawRepository cUserWithDrawRepository;
    @Autowired
    private UserWithHugeRepository cUserWithHugeRepository;

    /**
     * 审核失败新
     * 删除指定大额提现记录
     *
     * @param id
     * @param description
     * @param status
     * @param advanceMoney
     * @throws Exception
     */
    @Override
    public void updateWithDrawFailNew(Long id, String description, int status, BigDecimal advanceMoney) throws Exception {
        updateWithDrawFail(id, description, status, advanceMoney);
        deleteWithHuge(id);
    }

    private void deleteWithHuge(Long id) throws Exception {
        cUserWithHugeRepository.deleteWithHuge(id);
    }

    /**
     * 审核失败
     *
     * @param id
     * @param description
     * @param status
     * @param advanceMoney
     */
    @Override
    public void updateWithDrawFail(Long id, String description, int status, BigDecimal advanceMoney) throws Exception {
        UserWithDraw withdraw = userWithDrawRepository.findUserWithDrawById(id);
        if (withdraw != null && StringUtils.isEmpty(withdraw.getProbleDescription())) {

            // 更新用户资金
            User user = userService.findOne(withdraw.getUser().getId());
            if (user != null && user.getUserMoney() != null && user.getUserInfo() != null) {

                UserMoney userMoney = userMoneyRepository.findUserMoneyById(user.getUserMoney().getId());
                if (withdraw.getSucAmt() == null) withdraw.setSucAmt(BigDecimal.ZERO);
                userMoney.setUsableFund(userMoney.getUsableFund().add(withdraw.getWithdrawMoney()).subtract(withdraw.getSucAmt()));
                //冻结金额 = 冻结金额 - 提现金额
                userMoney.setFreezeFund(userMoney.getFreezeFund().subtract(withdraw.getWithdrawMoney().subtract(withdraw.getSucAmt())));
                userMoneyRepository.save(userMoney);

                UserInfo userInfo = userInfoRepository.findUserInfoById(user.getUserInfo().getId());
                //减去提现次数
                dealWithdrawTimes(withdraw,userInfo);
                userInfoRepository.save(userInfo);

                //解冻日志
                Long userFundBlokedId = userFundBlokedLogService.saveUserFundBlokedLog(user.getId(), 2, 1, withdraw.getId(), withdraw.getWithdrawMoney().subtract(withdraw.getSucAmt()), userMoney.getUsableFund());

                //解冻资金流水
                userFundRecordService.saveUserFundRecord(user.getId(), FundFlow.DRAW_FAIL, withdraw.getWithdrawMoney().subtract(withdraw.getSucAmt()), 3, 1, userMoney.getUsableFund(), userFundBlokedId);

                StringBuffer sbs = new StringBuffer();
                sbs.append("content:" + description);
                smsHandleService.addSiteContent("withdraws_fail", user.getId(), FundFlow.DRAW_FAIL, sbs.toString(), 0);


                Subject currentUser = SecurityUtils.getSubject();
                ShiroUser shiroUser = (ShiroUser) currentUser.getPrincipal();
                withdraw.setAuditUser(shiroUser.getUsername());
                withdraw.setAdvanceMoney(advanceMoney);
                withdraw.setAuditTime(DateUtil.getSystemTimeSeconds());
                withdraw.setStatus(status);
                withdraw.setProbleDescription(description);
                this.update(withdraw);

            }
        }
    }


    /**
     * 处理提现次数
     * @param withdraw
     * @param userInfo
     */
    public void dealWithdrawTimes(UserWithDraw withdraw, UserInfo userInfo) {
        //减去提现次数
        if (withdraw.getTimesSorurce() != null) {
            //免费次数
            if (withdraw.getTimesSorurce() == 1) {
                if (userInfo.getWithdrawFrees() != null) {
                    userInfo.setWithdrawFrees(userInfo.getWithdrawFrees() - 1);
                }
            }
            //3元次数
            if (withdraw.getTimesSorurce() == 2) {
                if (userInfo.getWithdrawTimes() != null) {
                    userInfo.setWithdrawTimes(userInfo.getWithdrawTimes() - 1);
                }
            }
        }
    }


    /**
     * 审核成功
     *
     * @param id
     * @param advanceMoney
     */
    @Override
    public void updateWithDrawSuccess(Long id, BigDecimal advanceMoney) throws Exception {
        UserWithDraw withdraw = userWithDrawRepository.findUserWithDrawById(id);
        if (withdraw != null && withdraw.getStatus() == 0) {
            Subject currentUser = SecurityUtils.getSubject();
            ShiroUser shiroUser = (ShiroUser) currentUser.getPrincipal();
            withdraw.setAuditUser(shiroUser.getUsername());
            withdraw.setStatus(Constance.PAYBACK);
            withdraw.setAuditTime(DateUtil.getSystemTimeSeconds());
            withdraw.setAdvanceMoney(advanceMoney);
            this.update(withdraw);
        }

    }


    /**
     * 代付放款
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public String updateLoanMoney(Long id, String userName) throws Exception {
        UserWithDraw withdraw = userWithDrawRepository.findUserWithDrawById(id);

        //自动放款时非VIP提现
        if (withdraw != null) {
            if ((withdraw.getAuditTime() == null || withdraw.getAuditTime() == 0) && withdraw.getStatus() == 0) {
                withdraw.setStatus(Constance.PAYBACK);
            }
        }

        if (withdraw != null && withdraw.getStatus() == 3) {

            //银行民称处理
            withdraw.setBankName(Banks.dealWithBankName(withdraw.getBankName()));

//            if (!WeekUtil.isWeekTime()) {
//                if (!Banks.AB.contains(withdraw.getBankName())) {
//                    return withdraw.getBankName() + "非工作时间不能代付";
//                }
//            }

            // 更新用户资金
            User user = userService.findOne(withdraw.getUser().getId());
            if (user != null && user.getUserMoney() != null && user.getUserInfo() != null) {

                BigDecimal fee = withdraw.getWithdrawMoney().subtract(withdraw.getAdvanceMoney());

                UserMoney userMoney = userMoneyRepository.findUserMoneyById(user.getUserMoney().getId());
                //用户总额
                userMoney.setTotalFund(userMoney.getTotalFund().subtract(withdraw.getWithdrawMoney()));
                //冻结金额
                userMoney.setFreezeFund(userMoney.getFreezeFund().subtract(withdraw.getWithdrawMoney()));
                userMoneyRepository.save(userMoney);

                UserInfo userInfo = userInfoRepository.findUserInfoById(user.getUserInfo().getId());
                //累计提现手续费 + 手续费
                userInfo.setMangeFee(userInfo.getMangeFee().add(fee));
                //累计提现
                userInfo.setAlliWthdraw(userInfo.getAlliWthdraw().add(withdraw.getWithdrawMoney()));
                userInfoRepository.save(userInfo);

                //解冻日志
                Long userFundBlokedId = userFundBlokedLogService.saveUserFundBlokedLog(user.getId(), 2, 1, withdraw.getId(), withdraw.getWithdrawMoney(), userMoney.getUsableFund().add(withdraw.getWithdrawMoney()));
                //解冻资金流水
                userFundRecordService.saveUserFundRecord(user.getId(), FundFlow.DRAW_UNFREE, withdraw.getWithdrawMoney(), 3, 1, userMoney.getUsableFund().add(withdraw.getWithdrawMoney()), userFundBlokedId);
                //出账日志
                Long fundOutLogId = userFundOutLogService.saveUserFundOutLog(user.getId(), withdraw.getId(), 1, withdraw.getAdvanceMoney(), userMoney.getUsableFund().add(fee), null);
                //出账资金流水
                userFundRecordService.saveUserFundRecord(user.getId(), FundFlow.DRAW_SUCCESS, withdraw.getAdvanceMoney(), 0, 1, userMoney.getUsableFund().add(fee), fundOutLogId);

                if (fee.doubleValue() > 0) {
                    //手续费出账日志
                    Long userFundOutLogId = userFundOutLogService.saveUserFundOutLog(user.getId(), withdraw.getId(), 5, fee, userMoney.getUsableFund(), null);
                    //手续费资金流水
                    userFundRecordService.saveUserFundRecord(user.getId(), FundFlow.DRAW_FEE, fee, 0, 5, userMoney.getUsableFund(), userFundOutLogId);

                }
            }

            //自动放款 如果无审核信息 自动审核
            if (StringUtils.isEmpty(withdraw.getAuditUser())) withdraw.setAuditUser(userName);
            if ((withdraw.getAuditTime() == null || withdraw.getAuditTime() == 0)) {
                withdraw.setDrawAuto(1);
                withdraw.setAuditTime(DateUtil.getSystemTimeSeconds());
            }

            withdraw.setTransferUser(userName);
            withdraw.setStatus(Constance.PAYBACK_MINSHENG);
            withdraw.setPayTime(DateUtil.getSystemTimeSeconds());
            this.update(withdraw);

        } else {
            return "记录已操作";
        }

        return null;
    }

    @Override
    public String updatesdloanMoney(Long id) throws Exception {
        UserWithDraw withdraw = userWithDrawRepository.findUserWithDrawById(id);
        if (withdraw != null && withdraw.getStatus() == 3) {
            // 更新用户资金
            User user = userService.findOne(withdraw.getUser().getId());
            if (user != null && user.getUserMoney() != null && user.getUserInfo() != null) {

                BigDecimal fee = withdraw.getWithdrawMoney().subtract(withdraw.getAdvanceMoney());

                UserMoney userMoney = userMoneyRepository.findUserMoneyById(user.getUserMoney().getId());
                //用户总额
                userMoney.setTotalFund(userMoney.getTotalFund().subtract(withdraw.getWithdrawMoney()));
                //冻结金额
                userMoney.setFreezeFund(userMoney.getFreezeFund().subtract(withdraw.getWithdrawMoney()));
                userMoneyRepository.save(userMoney);

                UserInfo userInfo = userInfoRepository.findUserInfoById(user.getUserInfo().getId());
                //累计提现手续费 + 手续费
                userInfo.setMangeFee(userInfo.getMangeFee().add(fee));
                //累计提现
                userInfo.setAlliWthdraw(userInfo.getAlliWthdraw().add(withdraw.getWithdrawMoney()));
                userInfoRepository.save(userInfo);

                //解冻日志
                Long userFundBlokedId = userFundBlokedLogService.saveUserFundBlokedLog(user.getId(), 2, 1, withdraw.getId(), withdraw.getWithdrawMoney(), userMoney.getUsableFund().add(withdraw.getWithdrawMoney()));
                //解冻资金流水
                userFundRecordService.saveUserFundRecord(user.getId(), FundFlow.DRAW_UNFREE, withdraw.getWithdrawMoney(), 3, 1, userMoney.getUsableFund().add(withdraw.getWithdrawMoney()), userFundBlokedId);
                //出账日志
                Long fundOutLogId = userFundOutLogService.saveUserFundOutLog(user.getId(), withdraw.getId(), 1, withdraw.getAdvanceMoney(), userMoney.getUsableFund().add(fee), null);
                //出账资金流水
                userFundRecordService.saveUserFundRecord(user.getId(), FundFlow.DRAW_SUCCESS, withdraw.getAdvanceMoney(), 0, 1, userMoney.getUsableFund().add(fee), fundOutLogId);

                if (fee.doubleValue() > 0) {
                    //手续费出账日志
                    Long userFundOutLogId = userFundOutLogService.saveUserFundOutLog(user.getId(), withdraw.getId(), 5, fee, userMoney.getUsableFund(), null);
                    //手续费资金流水
                    userFundRecordService.saveUserFundRecord(user.getId(), FundFlow.DRAW_FEE, fee, 0, 5, userMoney.getUsableFund(), userFundOutLogId);

                }
            }

            Subject currentUser = SecurityUtils.getSubject();
            ShiroUser shiroUser = (ShiroUser) currentUser.getPrincipal();
            withdraw.setTransferUser(shiroUser.getUsername());
            withdraw.setTransferTime(DateUtil.getSystemTimeSeconds());
            withdraw.setStatus(Constance.PAYBACK_SUCCESS);
            withdraw.setPayTime(DateUtil.getSystemTimeSeconds());
            withdraw.setSucAmt(withdraw.getAdvanceMoney());
            this.update(withdraw);

        } else {
            return "记录已操作";
        }

        return null;
    }

    /**
     * 发送代付请求
     *
     * @param id
     * @throws Exception
     */
    @Override
    public void querysendRequest(Long id) throws Exception {
        UserWithDraw withdraw = userWithDrawRepository.findUserWithDrawById(id);
        if (withdraw != null) {
            // 根据用户ID 查询用户
            User user = userService.findOne(withdraw.getUser().getId());
            if (user != null) {
                if (withdraw.getSucAmt() == null) withdraw.setSucAmt(BigDecimal.ZERO);

                //银行民称处理
                withdraw.setBankName(Banks.dealWithBankName(withdraw.getBankName()));

                //50W限额 7 * 24
                if (Banks.AB.contains(withdraw.getBankName())) {
                    querysendRequest(withdraw, Banks.F_FUND);
                } else {
                    //49990限额限额 工作日
                    querysendRequest(withdraw, Banks.O_FUND);
                }
            }

        }
    }


    /**
     * 发送放款短信
     *
     * @param id
     */
    @Override
    public void sendMessage(Long id) {
        UserWithDraw withdraw = userWithDrawRepository.findUserWithDrawById(id);
        if (withdraw != null) {
            // 根据用户ID 查询用户
            User user = userService.findOne(withdraw.getUser().getId());
            if (user != null) {
                DecimalFormat df = new DecimalFormat("#.00");
                // 放款成功进行发送短信
                StringBuffer sb = new StringBuffer();
                sb.append("name:" + user.getUsername());
                sb.append(",value:" + df.format(withdraw.getAdvanceMoney()));
                smsHandleService.sendUserSms("withdraws_success", user.getMobile(), sb.toString());

                StringBuffer sbs = new StringBuffer();
                sbs.append("name:" + user.getUsername());
                sbs.append(",value:" + withdraw.getAdvanceMoney());
                smsHandleService.addSiteContent("withdraws_success", withdraw.getUser().getId(), "提现成功", sbs.toString(), 0);
            }
        }
    }


    /**
     * 转账拆分
     *
     * @param withdraw
     * @param basefund
     * @throws Exception
     */
    private void querysendRequest(UserWithDraw withdraw, BigDecimal basefund) throws Exception {
        //判断转账金额是否大于基础金额
        if (withdraw.getAdvanceMoney().subtract(withdraw.getSucAmt()).compareTo(basefund) > 0) {
            BigDecimal fund = withdraw.getAdvanceMoney().subtract(withdraw.getSucAmt());
            //拆分成多笔转账
            while (true) {
                if (fund.compareTo(basefund) <= 0) {
                    payManualRequestService.querysendPayXml(withdraw.getUser().getId(), withdraw.getId(), 1, withdraw.getBankName(), withdraw.getBankCardNumber(), withdraw.getRealname(), fund, withdraw.getIdCard(), withdraw.getTransMobile());
                    break;
                } else {
                    payManualRequestService.querysendPayXml(withdraw.getUser().getId(), withdraw.getId(), 1, withdraw.getBankName(), withdraw.getBankCardNumber(), withdraw.getRealname(), basefund, withdraw.getIdCard(), withdraw.getTransMobile());
                }
                fund = fund.subtract(basefund);
            }
        } else {
            //转账金额小于基础金额 直接转账
            payManualRequestService.querysendPayXml(withdraw.getUser().getId(), withdraw.getId(), 1, withdraw.getBankName(), withdraw.getBankCardNumber(), withdraw.getRealname(), withdraw.getAdvanceMoney().subtract(withdraw.getSucAmt()), withdraw.getIdCard(), withdraw.getTransMobile());
        }
    }


    /**
     * 代付手动成功
     *
     * @param id
     * @throws Exception
     */
    @Override
    public String minSubmitWithDraw(Long id) throws Exception {
        UserWithDraw withdraw = userWithDrawRepository.findUserWithDrawById(id);
        if (withdraw != null && (withdraw.getStatus() == 7 || withdraw.getStatus() == 8)) {
            Subject currentUser = SecurityUtils.getSubject();
            ShiroUser shiroUser = (ShiroUser) currentUser.getPrincipal();
            withdraw.setTransferUser(shiroUser.getUsername());
            withdraw.setTransferTime(DateUtil.getSystemTimeSeconds());
            withdraw.setSucAmt(withdraw.getAdvanceMoney());
            withdraw.setStatus(Constance.PAYBACK_SUCCESS);
            this.update(withdraw);
        } else {
            return "记录已操作";
        }
        return null;
    }


    /**
     * 代付手动失败
     *
     * @param id
     * @param description
     * @param status
     * @param advanceMoney
     * @return
     */
    @Override
    public String minFailPayBack(Long id, String description, int status, BigDecimal advanceMoney) {
        UserWithDraw withdraw = userWithDrawRepository.findUserWithDrawById(id);
        if (withdraw != null && StringUtils.isEmpty(withdraw.getProbleDescription())) {

            Subject currentUser = SecurityUtils.getSubject();
            ShiroUser shiroUser = (ShiroUser) currentUser.getPrincipal();

            withdraw.setAuditUser(shiroUser.getUsername());
            withdraw.setAdvanceMoney(advanceMoney);
            withdraw.setAuditTime(DateUtil.getSystemTimeSeconds());
            withdraw.setStatus(status);
            withdraw.setProbleDescription(description);
            this.update(withdraw);


            User user = userService.findOne(withdraw.getUser().getId());
            if (user != null && user.getUserMoney() != null && user.getUserInfo() != null) {

                UserMoney userMoney = userMoneyRepository.findUserMoneyById(user.getUserMoney().getId());
                if (withdraw.getSucAmt() == null) withdraw.setSucAmt(BigDecimal.ZERO);
                //用户总额
                userMoney.setTotalFund(userMoney.getTotalFund().add(withdraw.getWithdrawMoney()).subtract(withdraw.getSucAmt()));
                //用户余额
                userMoney.setUsableFund(userMoney.getUsableFund().add(withdraw.getWithdrawMoney()).subtract(withdraw.getSucAmt()));
                userMoneyRepository.save(userMoney);

                BigDecimal fee = withdraw.getWithdrawMoney().subtract(withdraw.getAdvanceMoney());
                UserInfo userInfo = userInfoRepository.findUserInfoById(user.getUserInfo().getId());
                //累计提现手续费 -  手续费
                userInfo.setMangeFee(userInfo.getMangeFee().subtract(fee));
                //累计提现
                userInfo.setAlliWthdraw(userInfo.getAlliWthdraw().subtract(withdraw.getWithdrawMoney()).add(withdraw.getSucAmt()));

                //减去提现次数
                dealWithdrawTimes(withdraw,userInfo);
                userInfoRepository.save(userInfo);

                //用户进账日志
                Long linkId = userFundInLogService.saveUserFundInLog(user.getId(), withdraw.getId(), 12, withdraw.getWithdrawMoney().subtract(withdraw.getSucAmt()), BigDecimal.ZERO, userMoney.getUsableFund(), null);

                //用户提现资金流水
                userFundRecordService.saveUserFundRecord(user.getId(), "提现失败", withdraw.getWithdrawMoney().subtract(withdraw.getSucAmt()), 1, 1, userMoney.getUsableFund(), linkId);

                StringBuffer sbs = new StringBuffer();
                sbs.append("name:" + user.getUsername());
                sbs.append(",msg:" + description);
                smsHandleService.addSiteContent("withdraws_fail_msg", user.getId(), FundFlow.DRAW_FAIL, sbs.toString(), 0);
                smsHandleService.sendUserSms("withdraws_fail_msg", user.getMobile(), sbs.toString());

            }

        }

        return null;
    }


    /**
     * 查询总提现金额以及打款金额
     *
     * @param filters
     * @return
     */
    @Override
    public Object[] findSumMoney(Map<String, Object> filters) {
        return cUserWithDrawRepository.findSumMoney(filters);
    }


    @Override
    public String exportExcele(List<UserWithDraw> userWithDraws, String address) {
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
            itemMap.put("title", "申请时间");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "applicationTime");
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
            itemMap.put("title", "状态");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "status");
            headInfoList.add(itemMap);

            List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

            Map<String, Object> dataItem = null;

            for (UserWithDraw userWithDraw : userWithDraws) {
                dataItem = new HashMap<String, Object>();
                dataItem.put("realname", userWithDraw.getRealname());
                dataItem.put("username", userWithDraw.getUser().getUsername());
                dataItem.put("mobile", userWithDraw.getUser().getMobile());
                dataItem.put("withdrawMoney", userWithDraw.getWithdrawMoney());
                dataItem.put("advanceMoney", userWithDraw.getAdvanceMoney());
                dataItem.put("applicationTime", format.format(userWithDraw.getSqDate()));


                switch (userWithDraw.getStatus()) {
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
                }
                dataList.add(dataItem);
            }

            fileName = "/withdraw" + DateUtil.getSystemTimeSeconds() + ".xls";
            String filePath = address + fileName;

            POIUtil.exportExcel2FilePath("提现记录", filePath, headInfoList,
                    dataList);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileName;
    }


    public ISmsHandleService getSmsHandleService() {
        return smsHandleService;
    }

    public void setSmsHandleService(ISmsHandleService smsHandleService) {
        this.smsHandleService = smsHandleService;
    }

    public IUserService getUserService() {
        return userService;
    }

    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    public IUserWithDrawRepository getUserWithDrawRepository() {
        return userWithDrawRepository;
    }

    public void setUserWithDrawRepository(IUserWithDrawRepository userWithDrawRepository) {
        this.userWithDrawRepository = userWithDrawRepository;
    }

    public IUserMoneyRepository getUserMoneyRepository() {
        return userMoneyRepository;
    }

    public void setUserMoneyRepository(IUserMoneyRepository userMoneyRepository) {
        this.userMoneyRepository = userMoneyRepository;
    }

    public UserWithDrawRepository getcUserWithDrawRepository() {
        return cUserWithDrawRepository;
    }

    public void setcUserWithDrawRepository(UserWithDrawRepository cUserWithDrawRepository) {
        this.cUserWithDrawRepository = cUserWithDrawRepository;
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

    public IUserFundBlokedLogService getUserFundBlokedLogService() {
        return userFundBlokedLogService;
    }

    public void setUserFundBlokedLogService(IUserFundBlokedLogService userFundBlokedLogService) {
        this.userFundBlokedLogService = userFundBlokedLogService;
    }

    public IUserFundRecordService getUserFundRecordService() {
        return userFundRecordService;
    }

    public void setUserFundRecordService(IUserFundRecordService userFundRecordService) {
        this.userFundRecordService = userFundRecordService;
    }

    public IUserFundOutLogService getUserFundOutLogService() {
        return userFundOutLogService;
    }

    public void setUserFundOutLogService(IUserFundOutLogService userFundOutLogService) {
        this.userFundOutLogService = userFundOutLogService;
    }

    public IPayManualRequestService getPayManualRequestService() {
        return payManualRequestService;
    }

    public void setPayManualRequestService(IPayManualRequestService payManualRequestService) {
        this.payManualRequestService = payManualRequestService;
    }


}
