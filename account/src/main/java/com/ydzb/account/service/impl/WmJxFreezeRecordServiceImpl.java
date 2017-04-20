package com.ydzb.account.service.impl;

import com.ydzb.account.entity.WmJxFreezeRecord;
import com.ydzb.account.repository.WmJxFreezeRecordRepository;
import com.ydzb.account.service.IWmJxFreezeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.LockModeType;
import java.math.BigDecimal;
import java.util.List;

/**
 * 产品交易冻结记录service实现
 */
@Service
public class WmJxFreezeRecordServiceImpl implements IWmJxFreezeRecordService {

    @Autowired
    private WmJxFreezeRecordRepository wmJxFreezeRecordRepository;

    /**
     * 创建
     * @param userId 用户id
     * @param accountId 电子账户
     * @param txAmount 交易金额
     * @param txIncome 交易收益
     * @param type 类型
     * @param linkType 交易类型
     * @param linkId 交易记录ID
     * @param productId 产品ID
     * @param investRedpacketId 加息券红包ID（定存）
     * @param depositRedpacketId 定存红包IDs（定存）
     * @param expireMode 是否复投
     * @param device 设备
     * @param startDate 开始时间（随心存）
     * @param endDate 结束时间（随心存）
     * @param freeCopies 随心存各产品份数
     * @param state 状态
     * @param created 创建时间
     * @param updated 处理完成时间
     * @return
     */
    @Override
    public WmJxFreezeRecord createOne(Long userId, String accountId, BigDecimal txAmount, BigDecimal txIncome, Integer type, Integer linkType, Long linkId,
            Long productId, String investRedpacketId, String depositRedpacketId, Integer expireMode, Integer device, Long startDate, Long endDate, String freeCopies,
            Integer state, Long created, Long updated) {

        WmJxFreezeRecord record = new WmJxFreezeRecord();
        record.setUserId(userId);
        record.setAccountId(accountId);
        record.setTxAmount(txAmount);
        record.setTxIncome(txIncome);
        record.setType(type);
        record.setLinkType(linkType);
        record.setLinkId(linkId);
        record.setProductId(productId);
        record.setInvestRedpacketId(investRedpacketId);
        record.setDepositRedpacketId(depositRedpacketId);
        record.setExpireMode(expireMode);
        record.setDevice(device);
        record.setStartDate(startDate);
        record.setEndDate(endDate);
        record.setFreeCopies(freeCopies);
        record.setState(state);
        record.setCreated(created);
        record.setUpdated(updated);
        return wmJxFreezeRecordRepository.createOrUpdate(record);
    }

    /**
     * 查询id
     * @param type 类型
     * @param linkType 交易类型
     * @param expireMode 复投类型
     * @param state 状态
     * @return
     */
    @Override
    public List<Long> queryIds(Integer type, Integer linkType, Integer expireMode, Integer state) {
        return wmJxFreezeRecordRepository.queryIds(type, linkType, expireMode, state);
    }

    @Override
    public WmJxFreezeRecord queryById(Long id, LockModeType lockType) {
        return wmJxFreezeRecordRepository.queryById(id, WmJxFreezeRecord.class, lockType);
    }

    @Override
    public WmJxFreezeRecord createOrUpdate(WmJxFreezeRecord jxFreezeRecord) {
        return wmJxFreezeRecordRepository.createOrUpdate(jxFreezeRecord);
    }

    /**
     * 根据类型，交易类型，状态查询id
     * @param type 类型
     * @param linkType 交易类型
     * @param state 状态
     * @return
     */
    @Override
    public List<Long> queryIds(Integer type, Integer linkType, Integer state) {
        return wmJxFreezeRecordRepository.queryIds(type, linkType, state);
    }

    /**
     * 更新状态
     * @param jxFreezeRecord 交易冻结记录
     * @param state 目标状态
     * @return
     */
    @Override
    public WmJxFreezeRecord updateStatus(WmJxFreezeRecord jxFreezeRecord, Integer state) throws Exception {
        if (jxFreezeRecord != null) {
            jxFreezeRecord.setState(state);
            return wmJxFreezeRecordRepository.createOrUpdate(jxFreezeRecord);
        }
        return null;
    }
}