package com.ydzb.account.quartz;

import com.ydzb.account.entity.WmJxFreezeRecord;
import com.ydzb.account.service.IWmJxFreezeRecordService;
import com.ydzb.account.service.IWmPeriodicProductHandleService;
import com.ydzb.account.service.IWmPrivilegeSettlementProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

/**
 * 预处理成功定时任务
 */
public class AccountPretreatementSuccessQuartz {

    @Autowired
    private IWmJxFreezeRecordService jxFreezeRecordService;
    @Autowired
    @Qualifier("ragularNoRecastService")
    private IWmPeriodicProductHandleService ragularNoRecastService;
    @Autowired
    @Qualifier("ragularAllRecastService")
    private IWmPeriodicProductHandleService ragularAllRecastService;
    @Autowired
    @Qualifier("ragularPrincipalRecastService")
    private IWmPeriodicProductHandleService ragularPrincipalRecastService;
    @Autowired
    @Qualifier("freeNoRecastService")
    private IWmPeriodicProductHandleService freeNoRecastService;
    @Autowired
    @Qualifier("privilegeSettlementProcessService")
    private IWmPrivilegeSettlementProcessService privilegeSettlementProcessService;

    /**
     * 处理预处理成功
     */
    public void handlePretreatmentSuccess() {
        //定存宝不复投
        handleRagularNoRecastPretreatmentSuccess();
        //定存宝本息复投
        handleRagularAllRecastPretreatmentSuccess();
        //定存宝本金复投
        handleRagularPrincipalRecastPretreatmentSuccess();
        //随心存不复投
        handleFreeNoRecastPretreatmentSuccess();
        //新手宝到期转活期宝
        handlePrivilegePretreatmentSuccess();
    }

    /**
     * 处理定存不复投预处理成功
     */
    private void handleRagularNoRecastPretreatmentSuccess() {
        try {
            List<Long> freezeIds = jxFreezeRecordService.queryIds(WmJxFreezeRecord.TYPE_EXPIRE, WmJxFreezeRecord.LINKTYPE_RAGULAR, WmJxFreezeRecord.STATE_MATCHFINISH);
            if (freezeIds != null) {
                for (Long freezeId: freezeIds) {
                    try {
                        ragularNoRecastService.executeDepositoryUserPretreatmentSuccess(freezeId);
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
     * 处理本息复投预处理成功
     */
    private void handleRagularAllRecastPretreatmentSuccess() {
        try {
            List<Long> freezeIds = jxFreezeRecordService.queryIds(WmJxFreezeRecord.TYPE_PROFIT, WmJxFreezeRecord.LINKTYPE_RAGULAR, WmJxFreezeRecord.STATE_MATCHFINISH);
            if (freezeIds != null) {
                for (Long freezeId: freezeIds) {
                    try {
                        ragularAllRecastService.executeDepositoryUserPretreatmentSuccess(freezeId);
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
     * 处理本金复投预处理成功
     */
    private void handleRagularPrincipalRecastPretreatmentSuccess() {
        try {
            List<Long> freezeIds = jxFreezeRecordService.queryIds(WmJxFreezeRecord.TYPE_PRINCIPAL_RECAST, WmJxFreezeRecord.LINKTYPE_RAGULAR, WmJxFreezeRecord.STATE_MATCHFINISH);
            if (freezeIds != null) {
                for (Long freezeId: freezeIds) {
                    try {
                        ragularPrincipalRecastService.executeDepositoryUserPretreatmentSuccess(freezeId);
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
     * 处理随心存不复投预处理成功
     */
    private void handleFreeNoRecastPretreatmentSuccess() {
        try {
            List<Long> freezeIds = jxFreezeRecordService.queryIds(WmJxFreezeRecord.TYPE_EXPIRE, WmJxFreezeRecord.LINKTYPE_FREE, WmJxFreezeRecord.STATE_MATCHFINISH);
            if (freezeIds != null) {
                for (Long freezeId: freezeIds) {
                    try {
                        freeNoRecastService.executeDepositoryUserPretreatmentSuccess(freezeId);
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
     * 处理随心存不复投预处理成功
     */
    private void handlePrivilegePretreatmentSuccess() {
        try {
            List<Long> freezeIds = jxFreezeRecordService.queryIds(WmJxFreezeRecord.TYPE_EXPIRE, WmJxFreezeRecord.LINKTYPE_PRIVILEGE, WmJxFreezeRecord.STATE_MATCHFINISH);
            if (freezeIds != null) {
                for (Long freezeId: freezeIds) {
                    try {
                        privilegeSettlementProcessService.handleDepositoryPretreatmentSuccess(freezeId);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}