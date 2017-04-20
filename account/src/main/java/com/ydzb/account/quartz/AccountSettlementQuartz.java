package com.ydzb.account.quartz;

import com.ydzb.account.context.IDRange;
import com.ydzb.account.entity.WmStable;
import com.ydzb.account.entity.WmStructure;
import com.ydzb.account.entity.WmStructureDeal;
import com.ydzb.account.entity.WmUser;
import com.ydzb.account.service.*;
import com.ydzb.core.utils.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountSettlementQuartz {

    /**
     * 拆分线程数
     */
    private static final int TH_NUM = 20;
    //线程1
    private static final int JOB0 = 0;
    //线程2
    private static final int JOB1 = 1;
    //线程3
    private static final int JOB2 = 2;
    //线程4
    private static final int JOB3 = 3;
    //线程5
    private static final int JOB4 = 4;
    //线程6
    private static final int JOB5 = 5;
    //线程7
    private static final int JOB6 = 6;
    //线程8
    private static final int JOB7 = 7;
    //线程9
    private static final int JOB8 = 8;
    //线程10
    private static final int JOB9 = 9;
    //线程11
    private static final int JOB10 = 10;
    //线程12
    private static final int JOB11 = 11;
    //线程13
    private static final int JOB12 = 12;
    //线程14
    private static final int JOB13 = 13;
    //线程15
    private static final int JOB14 = 14;
    //线程16
    private static final int JOB15 = 15;
    //线程17
    private static final int JOB16 = 16;
    //线程18
    private static final int JOB17 = 17;
    //线程19
    private static final int JOB18 = 18;
    //线程20
    private static final int JOB19 = 19;


    public Logger logger = Logger.getLogger(AccountSettlementQuartz.class);

    //总结算
    @Autowired
    private IAccountSettlementService settlementService;

    //活期宝
    @Autowired
    @Qualifier("currentSettlementProcessService")
    private IWmDailyProductSettlementProcessService currentSettlementProcessService;

    //新手宝
    @Autowired
    @Qualifier("privilegeSettlementProcessServiceImpl")
    private IWmDailyProductSettlementProcessService privilegeSettlementProcessServiceImpl;


    //基金类产品
    @Autowired
    private IWmStableAccountservice stableAccountservice;

    //定存宝
    @Autowired
    private IWmPeriodicProductSettlementProcessService ragularSettlementProcessService;
    @Autowired
    private IWmRagularUserAccountService ragularUserAccountService;

    //VIP等级
    @Autowired
    private IWmVipGradeService vipGradeService;

    //红包
    @Autowired
    private IWmUserRedPacketService userRedPacketService;

    //体验金
    @Autowired
    private IWmExpmoneyDealService expmoneyDealService;

    @Autowired
    private IWmStructureAccountService structureAccountService;

    @Autowired
    private IWmStructureDealService structureDealService;

    @Autowired
    private IWmStructureService structureService;

    @Autowired
    @Qualifier("freeSettlementProcessService")
    private IWmPeriodicProductSettlementProcessService freeSettlementProcessService;

    @Autowired
    private IWmPrivilegeUserAccountService privilegeUserAccountService;

    /**
     * 拆分线程1 方法名 + 用户ID对拆分数求余
     */
    public void accountSettlementJob0() {
        logger.info("=========线程1执行开始==========");
        //用户ID区间
        IDRange idRange = settlementService.findWmUserMaxIdAndMinId();
        if (idRange != null && idRange.getMaxId() > 0 && idRange.getMinId() > 0) {
            //根据用户ID区间遍历
            for (long userId = idRange.getMinId(); userId <= idRange.getMaxId(); userId++) {
                //线程1
                if (userId % TH_NUM == JOB0) {
                    try {
                        accountSettlementJob(userId);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        logger.info("=========线程1执行结束==========");
    }

    /**
     * 拆分线程2 方法名 + 用户ID对拆分数求余
     */
    public void accountSettlementJob1() {
        logger.info("=========线程2执行开始==========");
        //用户ID区间
        IDRange idRange = settlementService.findWmUserMaxIdAndMinId();
        if (idRange != null && idRange.getMaxId() > 0 && idRange.getMinId() > 0) {
            //根据用户ID区间遍历
            for (long userId = idRange.getMinId(); userId <= idRange.getMaxId(); userId++) {
                //线程2
                if (userId % TH_NUM == JOB1) {
                    try {
                        accountSettlementJob(userId);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        logger.info("=========线程2执行结束==========");
    }

    /**
     * 拆分线程3 方法名 + 用户ID对拆分数求余
     */
    public void accountSettlementJob2() {
        logger.info("=========线程3执行开始==========");
        //用户ID区间
        IDRange idRange = settlementService.findWmUserMaxIdAndMinId();
        if (idRange != null && idRange.getMaxId() > 0 && idRange.getMinId() > 0) {
            //根据用户ID区间遍历
            for (long userId = idRange.getMinId(); userId <= idRange.getMaxId(); userId++) {
                //线程3
                if (userId % TH_NUM == JOB2) {
                    try {
                        accountSettlementJob(userId);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        logger.info("=========线程3执行结束==========");
    }

    /**
     * 拆分线程4 方法名 + 用户ID对拆分数求余
     */
    public void accountSettlementJob3() {
        logger.info("=========线程4执行开始==========");
        //用户ID区间
        IDRange idRange = settlementService.findWmUserMaxIdAndMinId();
        if (idRange != null && idRange.getMaxId() > 0 && idRange.getMinId() > 0) {
            //根据用户ID区间遍历
            for (long userId = idRange.getMinId(); userId <= idRange.getMaxId(); userId++) {
                //线程4
                if (userId % TH_NUM == JOB3) {
                    try {
                        accountSettlementJob(userId);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        logger.info("=========线程4执行结束==========");
    }

    /**
     * 拆分线程5 方法名 + 用户ID对拆分数求余
     */
    public void accountSettlementJob4() {
        logger.info("=========线程5执行开始==========");
        //用户ID区间
        IDRange idRange = settlementService.findWmUserMaxIdAndMinId();
        if (idRange != null && idRange.getMaxId() > 0 && idRange.getMinId() > 0) {
            //根据用户ID区间遍历
            for (long userId = idRange.getMinId(); userId <= idRange.getMaxId(); userId++) {
                //线程5
                if (userId % TH_NUM == JOB4) {
                    try {
                        accountSettlementJob(userId);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        logger.info("=========线程5执行结束==========");
    }

    /**
     * 拆分线程6 方法名 + 用户ID对拆分数求余
     */
    public void accountSettlementJob5() {
        logger.info("=========线程6执行开始==========");
        //用户ID区间
        IDRange idRange = settlementService.findWmUserMaxIdAndMinId();
        if (idRange != null && idRange.getMaxId() > 0 && idRange.getMinId() > 0) {
            //根据用户ID区间遍历
            for (long userId = idRange.getMinId(); userId <= idRange.getMaxId(); userId++) {
                //线程6
                if (userId % TH_NUM == JOB5) {
                    try {
                        accountSettlementJob(userId);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        logger.info("=========线程6执行结束==========");
    }


    /**
     * 拆分线程7 方法名 + 用户ID对拆分数求余
     */
    public void accountSettlementJob6() {
        logger.info("=========线程7执行开始==========");
        //用户ID区间
        IDRange idRange = settlementService.findWmUserMaxIdAndMinId();
        if (idRange != null && idRange.getMaxId() > 0 && idRange.getMinId() > 0) {
            //根据用户ID区间遍历
            for (long userId = idRange.getMinId(); userId <= idRange.getMaxId(); userId++) {
                //线程6
                if (userId % TH_NUM == JOB6) {
                    try {
                        accountSettlementJob(userId);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        logger.info("=========线程7执行结束==========");
    }


    /**
     * 拆分线程8 方法名 + 用户ID对拆分数求余
     */
    public void accountSettlementJob7() {
        logger.info("=========线程8执行开始==========");
        //用户ID区间
        IDRange idRange = settlementService.findWmUserMaxIdAndMinId();
        if (idRange != null && idRange.getMaxId() > 0 && idRange.getMinId() > 0) {
            //根据用户ID区间遍历
            for (long userId = idRange.getMinId(); userId <= idRange.getMaxId(); userId++) {
                //线程6
                if (userId % TH_NUM == JOB7) {
                    try {
                        accountSettlementJob(userId);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        logger.info("=========线程8执行结束==========");
    }


    /**
     * 拆分线程9 方法名 + 用户ID对拆分数求余
     */
    public void accountSettlementJob8() {
        logger.info("=========线程9执行开始==========");
        //用户ID区间
        IDRange idRange = settlementService.findWmUserMaxIdAndMinId();
        if (idRange != null && idRange.getMaxId() > 0 && idRange.getMinId() > 0) {
            //根据用户ID区间遍历
            for (long userId = idRange.getMinId(); userId <= idRange.getMaxId(); userId++) {
                //线程6
                if (userId % TH_NUM == JOB8) {
                    try {
                        accountSettlementJob(userId);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        logger.info("=========线程9执行结束==========");
    }


    /**
     * 拆分线程10 方法名 + 用户ID对拆分数求余
     */
    public void accountSettlementJob9() {
        logger.info("=========线程10执行开始==========");
        //用户ID区间
        IDRange idRange = settlementService.findWmUserMaxIdAndMinId();
        if (idRange != null && idRange.getMaxId() > 0 && idRange.getMinId() > 0) {
            //根据用户ID区间遍历
            for (long userId = idRange.getMinId(); userId <= idRange.getMaxId(); userId++) {
                //线程6
                if (userId % TH_NUM == JOB9) {
                    try {
                        accountSettlementJob(userId);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        logger.info("=========线程10执行结束==========");
    }


    /**
     * 拆分线程11 方法名 + 用户ID对拆分数求余
     */
    public void accountSettlementJob10() {
        logger.info("=========线程11执行开始==========");
        //用户ID区间
        IDRange idRange = settlementService.findWmUserMaxIdAndMinId();
        if (idRange != null && idRange.getMaxId() > 0 && idRange.getMinId() > 0) {
            //根据用户ID区间遍历
            for (long userId = idRange.getMinId(); userId <= idRange.getMaxId(); userId++) {
                //线程6
                if (userId % TH_NUM == JOB10) {
                    try {
                        accountSettlementJob(userId);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        logger.info("=========线程11执行结束==========");
    }


    /**
     * 拆分线程12 方法名 + 用户ID对拆分数求余
     */
    public void accountSettlementJob11() {
        logger.info("=========线程12执行开始==========");
        //用户ID区间
        IDRange idRange = settlementService.findWmUserMaxIdAndMinId();
        if (idRange != null && idRange.getMaxId() > 0 && idRange.getMinId() > 0) {
            //根据用户ID区间遍历
            for (long userId = idRange.getMinId(); userId <= idRange.getMaxId(); userId++) {
                //线程6
                if (userId % TH_NUM == JOB11) {
                    try {
                        accountSettlementJob(userId);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        logger.info("=========线程12执行结束==========");
    }

    /**
     * 拆分线程13 方法名 + 用户ID对拆分数求余
     */
    public void accountSettlementJob12() {
        logger.info("=========线程13执行开始==========");
        //用户ID区间
        IDRange idRange = settlementService.findWmUserMaxIdAndMinId();
        if (idRange != null && idRange.getMaxId() > 0 && idRange.getMinId() > 0) {
            //根据用户ID区间遍历
            for (long userId = idRange.getMinId(); userId <= idRange.getMaxId(); userId++) {
                //线程6
                if (userId % TH_NUM == JOB12) {
                    try {
                        accountSettlementJob(userId);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        logger.info("=========线程13执行结束==========");
    }
    /**
     * 拆分线程14 方法名 + 用户ID对拆分数求余
     */
    public void accountSettlementJob13() {
        logger.info("=========线程14执行开始==========");
        //用户ID区间
        IDRange idRange = settlementService.findWmUserMaxIdAndMinId();
        if (idRange != null && idRange.getMaxId() > 0 && idRange.getMinId() > 0) {
            //根据用户ID区间遍历
            for (long userId = idRange.getMinId(); userId <= idRange.getMaxId(); userId++) {
                //线程6
                if (userId % TH_NUM == JOB13) {
                    try {
                        accountSettlementJob(userId);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        logger.info("=========线程14执行结束==========");
    }
    /**
     * 拆分线程15 方法名 + 用户ID对拆分数求余
     */
    public void accountSettlementJob14() {
        logger.info("=========线程15执行开始==========");
        //用户ID区间
        IDRange idRange = settlementService.findWmUserMaxIdAndMinId();
        if (idRange != null && idRange.getMaxId() > 0 && idRange.getMinId() > 0) {
            //根据用户ID区间遍历
            for (long userId = idRange.getMinId(); userId <= idRange.getMaxId(); userId++) {
                //线程6
                if (userId % TH_NUM == JOB14) {
                    try {
                        accountSettlementJob(userId);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        logger.info("=========线程15执行结束==========");
    }
    /**
     * 拆分线程16 方法名 + 用户ID对拆分数求余
     */
    public void accountSettlementJob15() {
        logger.info("=========线程16执行开始==========");
        //用户ID区间
        IDRange idRange = settlementService.findWmUserMaxIdAndMinId();
        if (idRange != null && idRange.getMaxId() > 0 && idRange.getMinId() > 0) {
            //根据用户ID区间遍历
            for (long userId = idRange.getMinId(); userId <= idRange.getMaxId(); userId++) {
                //线程6
                if (userId % TH_NUM == JOB15) {
                    try {
                        accountSettlementJob(userId);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        logger.info("=========线程16执行结束==========");
    }
    /**
     * 拆分线程17 方法名 + 用户ID对拆分数求余
     */
    public void accountSettlementJob16() {
        logger.info("=========线程17执行开始==========");
        //用户ID区间
        IDRange idRange = settlementService.findWmUserMaxIdAndMinId();
        if (idRange != null && idRange.getMaxId() > 0 && idRange.getMinId() > 0) {
            //根据用户ID区间遍历
            for (long userId = idRange.getMinId(); userId <= idRange.getMaxId(); userId++) {
                //线程6
                if (userId % TH_NUM == JOB16) {
                    try {
                        accountSettlementJob(userId);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        logger.info("=========线程17执行结束==========");
    }
    /**
     * 拆分线程18 方法名 + 用户ID对拆分数求余
     */
    public void accountSettlementJob17() {
        logger.info("=========线程18执行开始==========");
        //用户ID区间
        IDRange idRange = settlementService.findWmUserMaxIdAndMinId();
        if (idRange != null && idRange.getMaxId() > 0 && idRange.getMinId() > 0) {
            //根据用户ID区间遍历
            for (long userId = idRange.getMinId(); userId <= idRange.getMaxId(); userId++) {
                //线程6
                if (userId % TH_NUM == JOB17) {
                    try {
                        accountSettlementJob(userId);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        logger.info("=========线程18执行结束==========");
    }
    /**
     * 拆分线程19 方法名 + 用户ID对拆分数求余
     */
    public void accountSettlementJob18() {
        logger.info("=========线程19执行开始==========");
        //用户ID区间
        IDRange idRange = settlementService.findWmUserMaxIdAndMinId();
        if (idRange != null && idRange.getMaxId() > 0 && idRange.getMinId() > 0) {
            //根据用户ID区间遍历
            for (long userId = idRange.getMinId(); userId <= idRange.getMaxId(); userId++) {
                //线程6
                if (userId % TH_NUM == JOB18) {
                    try {
                        accountSettlementJob(userId);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        logger.info("=========线程19执行结束==========");
    }
    /**
     * 拆分线程20 方法名 + 用户ID对拆分数求余
     */
    public void accountSettlementJob19() {
        logger.info("=========线程20执行开始==========");
        //用户ID区间
        IDRange idRange = settlementService.findWmUserMaxIdAndMinId();
        if (idRange != null && idRange.getMaxId() > 0 && idRange.getMinId() > 0) {
            //根据用户ID区间遍历
            for (long userId = idRange.getMinId(); userId <= idRange.getMaxId(); userId++) {
                //线程6
                if (userId % TH_NUM == JOB19) {
                    try {
                        accountSettlementJob(userId);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        logger.info("=========线程20执行结束==========");
    }


    /**
     * 根据用户处理需结算业务
     *
     * @param userId 用户ID
     */
    private void accountSettlementJob(Long userId) throws Exception {
        //判断用户是否存在
        WmUser wmUser = settlementService.findWmUserUserById(userId);
        if (wmUser != null) {

            //活期宝结算
            accountCurrentUserAccount(userId);

            //基金类产品结算
            accountStableRevenue(userId);

            //转转赚产品结算
            accountStructureRevenue(userId);

            //取消债权转让
            accountRagularTransfer(userId);

            //定存宝结算
            accountRagularUserAccount(userId);

            //自由定存结算
            accountFreeUserAccount(userId);

            //用户VIP等级结算
            accountUserVipGrade(userId);

            //红包结算
            accountUserRedPacket(userId);

            //结算体验金
            accountExpmoneyDeal(userId);

            //结算新手特权
            accountPrivilegeUserAccount(userId);
        }
    }


    /**
     * 活期宝每日结算
     *
     * @param userId 用户ID
     */
    public void accountCurrentUserAccount(Long userId) {
        try {
            //活期宝每日结算
            currentSettlementProcessService.executeSettlement(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 新手特权每日结算
     * @param userId 用户ID
     */
    public void accountPrivilegeUserAccount(Long userId) {
        try {
            //新手特权结算
            privilegeSettlementProcessServiceImpl.executeSettlement(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 结算基金类产品
     *
     * @param userId 用户ID
     */
    public void accountStableRevenue(Long userId) {
        try {
            //系统当前日期
            Long curDate = DateUtil.getSystemTimeDay(DateUtil.getCurrentDate());
            List<WmStable> stables = stableAccountservice.findByCloseDate(curDate);
            if (stables != null && !stables.isEmpty()) {
                for (WmStable stable : stables) {
                    if (stable != null && stable.getApr() != null) {
                        try {
                            stableAccountservice.accountStableRevenue(stable, userId);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 结算转转赚产品
     *
     * @param userId 用户ID
     */
    public void accountStructureRevenue(Long userId) {
        try {
            Map<Long, WmStructure> structureMap = new HashMap<>();  //转转赚产品map,避免多次查询同一产品,节省效率
            Long curDate = DateUtil.getSystemTimeDay(DateUtil.getCurrentDate());
            List<WmStructureDeal> structureDeals = structureDealService.queryByUserAndCloseDate(userId, curDate);
            if (structureDeals != null) {
                Long structureId;
                WmStructure structure;
                for (WmStructureDeal structureDeal : structureDeals) {
                    structureId = structureDeal.getStructureId();
                    structure = structureMap.get(structureId);
                    if (structure == null) {
                        structure = structureService.queryById(structureId);
                        structureMap.put(structureId, structure);
                    }
                    try {
                        structureAccountService.accountStructureRevenueNew(structure, structureDeal, userId);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 定存宝转让结算
     *
     * @param userId 用户ID
     */
    public void accountRagularTransfer(Long userId) {
        try {
            ragularUserAccountService.accountRagularTransfer(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 定存宝结算
     *
     * @param userId 用户ID
     */
    public void accountRagularUserAccount(Long userId) {
        try {
            ragularSettlementProcessService.executeSettlement(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 自由定存结算
     *
     * @param userId 用户ID
     */
    public void accountFreeUserAccount(Long userId) {
        try {
            freeSettlementProcessService.executeSettlement(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 用户VIP等级结算
     *
     * @param userId 用户ID
     */
    public void accountUserVipGrade(Long userId) {
        try {
            vipGradeService.accountUserVipGrade(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 结算红包
     *
     * @param userId 用户ID
     */
    public void accountUserRedPacket(Long userId) {

        //未投资红包
        try {
            userRedPacketService.accountUsedUserRedPacket(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //已投资红包
        try {
            userRedPacketService.accountInvestedUserRedPacket(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 结算体验金
     *
     * @param userId 用户ID
     */
    public void accountExpmoneyDeal(Long userId) {
        try {
            expmoneyDealService.accountExpmoneyDeal(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public IAccountSettlementService getSettlementService() {
        return settlementService;
    }

    public void setSettlementService(IAccountSettlementService settlementService) {
        this.settlementService = settlementService;
    }

    public IWmExpmoneyDealService getExpmoneyDealService() {
        return expmoneyDealService;
    }

    public void setExpmoneyDealService(IWmExpmoneyDealService expmoneyDealService) {
        this.expmoneyDealService = expmoneyDealService;
    }

    public IWmUserRedPacketService getUserRedPacketService() {
        return userRedPacketService;
    }

    public void setUserRedPacketService(IWmUserRedPacketService userRedPacketService) {
        this.userRedPacketService = userRedPacketService;
    }

    public IWmStableAccountservice getStableAccountservice() {
        return stableAccountservice;
    }

    public void setStableAccountservice(IWmStableAccountservice stableAccountservice) {
        this.stableAccountservice = stableAccountservice;
    }

    public IWmRagularUserAccountService getRagularUserAccountService() {
        return ragularUserAccountService;
    }

    public void setRagularUserAccountService(IWmRagularUserAccountService ragularUserAccountService) {
        this.ragularUserAccountService = ragularUserAccountService;
    }

    public IWmVipGradeService getVipGradeService() {
        return vipGradeService;
    }

    public void setVipGradeService(IWmVipGradeService vipGradeService) {
        this.vipGradeService = vipGradeService;
    }

    public IWmStructureAccountService getStructureAccountService() {
        return structureAccountService;
    }

    public void setStructureAccountService(IWmStructureAccountService structureAccountService) {
        this.structureAccountService = structureAccountService;
    }

    public IWmStructureDealService getStructureDealService() {
        return structureDealService;
    }

    public void setStructureDealService(IWmStructureDealService structureDealService) {
        this.structureDealService = structureDealService;
    }

    public IWmStructureService getStructureService() {
        return structureService;
    }

    public void setStructureService(IWmStructureService structureService) {
        this.structureService = structureService;
    }
}
