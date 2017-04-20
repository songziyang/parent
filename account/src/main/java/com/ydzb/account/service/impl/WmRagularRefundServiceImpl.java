package com.ydzb.account.service.impl;

import com.ydzb.account.entity.WmRagularRefund;
import com.ydzb.account.repository.WmRagularRefundRepository;
import com.ydzb.account.service.IWmRagularRefundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.LockModeType;
import java.math.BigDecimal;

/**
 * 定存宝还息记录service实现
 */
@Service
public class WmRagularRefundServiceImpl implements IWmRagularRefundService {

    @Autowired
    private WmRagularRefundRepository ragularRefundRepository;

    /**
     * 创建
     * @param userId 用户id
     * @param stage 总期数
     * @param curStage 当前期数
     * @param refundTime 还息日期
     * @param fund 收益总金额
     * @param redpacketFund 加息收益
     * @param vipFund VIP加息收益
     * @param voucherFund 代金券收益
     * @param state 状态
     * @param isExpire 是否是最后一期状态值
     * @param organization 产品类别（定存宝、机构宝）
     * @return
     */
    @Override
    public WmRagularRefund createOne(Long userId, Integer stage, Integer curStage, Long refundTime,
            BigDecimal fund, BigDecimal redpacketFund, BigDecimal vipFund, BigDecimal voucherFund,
            Integer state, Integer isExpire, Integer organization) throws Exception {
        WmRagularRefund ragularRefund = new WmRagularRefund();
        ragularRefund.setUserId(userId);
        ragularRefund.setStage(stage);
        ragularRefund.setCurStage(curStage);
        ragularRefund.setRefundTime(refundTime);
        ragularRefund.setFund(fund);
        ragularRefund.setRedpacketFund(redpacketFund);
        ragularRefund.setVipFund(vipFund);
        ragularRefund.setVouchersFund(voucherFund);
        ragularRefund.setState(state);
        ragularRefund.setIsExpire(isExpire);
        ragularRefund.setOrganization(organization);
        return ragularRefundRepository.createOrUpdate(ragularRefund);
    }

    /**
     * 更新换新记录状态
     * @param ragularRefund 还息记录
     * @param state 状态
     * @return
     */
    @Override
    public WmRagularRefund updateStatus(WmRagularRefund ragularRefund, Integer state) throws Exception {
        if (ragularRefund != null) {
            ragularRefund.setState(state);
            return ragularRefundRepository.createOrUpdate(ragularRefund);
        }
        return null;
    }

    /**
     * 根据主键查询
     * @param id 主键id
     * @param lockType 锁类型
     * @return
     */
    public WmRagularRefund queryById(Long id, LockModeType lockType) {
        return ragularRefundRepository.queryById(id, lockType);
    }

    /**
     * 根据accountId查询
     * @param accountId 用户定存记录id
     * @param organization 机构类型/产品类型
     * @param expire 是否是最后一期
     * @return
     */
    @Override
    public WmRagularRefund queryByAccount(Long accountId, Integer organization, Integer expire) {
        return ragularRefundRepository.queryByAccount(accountId, organization, expire);
    }
}