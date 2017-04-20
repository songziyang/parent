package com.ydzb.withdraw.service.impl;

import com.ydzb.admin.shiro.ShiroUser;
import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.withdraw.base.Banks;
import com.ydzb.withdraw.entity.PayManualRecord;
import com.ydzb.withdraw.repository.IPayManualRecordRepository;
import com.ydzb.withdraw.service.IPayManualRecordService;
import com.ydzb.withdraw.service.IPayManualRequestService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 代付手动打款service实现
 */
@Service
public class PayManualRecordServiceImpl extends BaseServiceImpl<PayManualRecord, Long> implements IPayManualRecordService {

    @Autowired
    private IPayManualRecordRepository payManualRecordRepository;

    @Autowired
    private IPayManualRequestService payManualRequestService;

    /**
     * 保存
     *
     * @return
     */
    @Override
    public String savePayManualRecord(PayManualRecord payManualRecord) {
        //只有新建
        ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        payManualRecord.setCreateUser(user.getUsername());
        payManualRecordRepository.save(payManualRecord);
        return null;
    }

    /**
     * 审核成功
     *
     * @param id
     */
    @Override
    public String auditSuccess(Long id) throws Exception {
        PayManualRecord payManualRecord = payManualRecordRepository.findOne(id);
        if (payManualRecord != null && payManualRecord.getStatus() == 1) {

            //银行名称处理
            payManualRecord.setBankName(Banks.dealWithBankName(payManualRecord.getBankName()));

//            if (!WeekUtil.isWeekTime()) {
//                if (!Banks.AB.contains(payManualRecord.getBankName())) {
//                    return payManualRecord.getBankName() + "非工作时间不能代付";
//                }
//            }

            Subject currentUser = SecurityUtils.getSubject();
            ShiroUser shiroUser = (ShiroUser) currentUser.getPrincipal();
            payManualRecord.setAuditUser(shiroUser.getUsername());
            payManualRecord.setAuditTime(DateUtil.getSystemTimeSeconds());
            payManualRecord.setStatus(PayManualRecord.AUDIT_SUCCESS);
            this.update(payManualRecord);
        } else {
            return "该记录已操作";
        }
        return null;
    }


    @Override
    public void querysendRequest(Long id) throws Exception {
        PayManualRecord payManualRecord = payManualRecordRepository.findOne(id);
        if (payManualRecord != null) {

            //银行名称处理
            payManualRecord.setBankName(Banks.dealWithBankName(payManualRecord.getBankName()));

            //50W限额 7 * 24
            if (Banks.AB.contains(payManualRecord.getBankName())) {
                querysendRequest(payManualRecord, Banks.F_FUND);
            } else {
                //49990限额限额 工作日
                querysendRequest(payManualRecord, Banks.O_FUND);
            }
        }

    }


    /**
     * 转账拆分
     *
     * @param payManualRecord
     * @param basefund
     * @throws Exception
     */
    public void querysendRequest(PayManualRecord payManualRecord, BigDecimal basefund) throws Exception {
        //判断转账金额是否大于基础金额
        if (payManualRecord.getTranAmt().compareTo(basefund) > 0) {
            BigDecimal fund = payManualRecord.getTranAmt();
            //拆分成多笔转账
            while (true) {
                if (fund.compareTo(basefund) <= 0) {
                    payManualRequestService.querysendPayXml(null, payManualRecord.getId(), 2, payManualRecord.getBankName(), payManualRecord.getAccountNo(), payManualRecord.getAccountName(), fund, payManualRecord.getTransCardId(), payManualRecord.getTransMobile());
                    break;
                } else {
                    payManualRequestService.querysendPayXml(null, payManualRecord.getId(), 2, payManualRecord.getBankName(), payManualRecord.getAccountNo(), payManualRecord.getAccountName(), basefund, payManualRecord.getTransCardId(), payManualRecord.getTransMobile());
                }
                fund = fund.subtract(basefund);
            }
        } else {
            //转账金额小于基础金额 直接转账
            payManualRequestService.querysendPayXml(null, payManualRecord.getId(), 2, payManualRecord.getBankName(), payManualRecord.getAccountNo(), payManualRecord.getAccountName(), payManualRecord.getTranAmt(), payManualRecord.getTransCardId(), payManualRecord.getTransMobile());
        }
    }


    /**
     * 审核失败
     *
     * @param id
     */
    @Override
    public void auditFailure(Long id) {
        PayManualRecord payManualRecord = payManualRecordRepository.findOne(id);
        if (payManualRecord != null) {
            Subject currentUser = SecurityUtils.getSubject();
            ShiroUser shiroUser = (ShiroUser) currentUser.getPrincipal();
            payManualRecord.setAuditUser(shiroUser.getUsername());
            payManualRecord.setAuditTime(DateUtil.getSystemTimeSeconds());
            payManualRecord.setStatus(PayManualRecord.AUDIT_FAILURE);
            this.update(payManualRecord);
        }
    }


    public IPayManualRecordRepository getPayManualRecordRepository() {
        return payManualRecordRepository;
    }

    public void setPayManualRecordRepository(IPayManualRecordRepository payManualRecordRepository) {
        this.payManualRecordRepository = payManualRecordRepository;
    }

    public IPayManualRequestService getPayManualRequestService() {
        return payManualRequestService;
    }

    public void setPayManualRequestService(IPayManualRequestService payManualRequestService) {
        this.payManualRequestService = payManualRequestService;
    }


}