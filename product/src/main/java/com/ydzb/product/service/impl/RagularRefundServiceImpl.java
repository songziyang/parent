package com.ydzb.product.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.product.entity.RagularRefund;
import com.ydzb.product.repository.IRagularRefundRepository;
import com.ydzb.product.repository.RagularRefundRepository;
import com.ydzb.product.service.IRagularRefundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 定存宝还息
 * @author sy
 */
@Service
public class RagularRefundServiceImpl extends BaseServiceImpl<RagularRefund, Long> implements IRagularRefundService {

    @Autowired
    private RagularRefundRepository ragularRefundRepository;
    @Autowired
    private IRagularRefundRepository iRagularRefundRepositroy;

    /**
     * 根据还息状态(state)以及对应产品的类型(type)计算收益
     * @param type
     * @param state
     * @return
     */
    @Override
    public BigDecimal getTotalRevenue(long[] type, byte state) {
        return ragularRefundRepository.getTotalRevenue(type, state);
    }

    /**
     * 根据还息状态(state)以及对应产品的类型(type)计算收益
     * @param type
     * @param state
     * @return
     */
    @Override
    public BigDecimal getTotalRevenue(Long type, byte state) {
        return ragularRefundRepository.getTotalRevenue(type, state);
    }

    /**
     * 根据定存产品类型(type)、结算状态(state)、结算起始日期查询到期收益
     * @param type
     * @param state
     * @param startTime
     * @param endTime
     * @return
     */
    public BigDecimal getClosingRevenue(long[] type, Byte state, Long startTime, Long endTime, boolean mustExistTime) {
        return ragularRefundRepository.getClosingRevenue(type, state, startTime, endTime, mustExistTime);
    }
    /**
     * 根据定存产品类型(type)、结算状态(state)、结算起始日期查询到期收益
     * @param type
     * @param state
     * @param startTime
     * @param endTime
     * @return
     */
    public BigDecimal getClosingRevenue(Long type, Byte state, Long startTime, Long endTime, boolean mustExistTime) {
        return ragularRefundRepository.getClosingRevenue(type, state, startTime, endTime, mustExistTime);
    }
}
