package com.ydzb.account.service;

import com.ydzb.account.entity.WmJxFreezeRecord;

import javax.persistence.LockModeType;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 */
public interface IWmJxFreezeRecordService {

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
    WmJxFreezeRecord createOne(Long userId, String accountId, BigDecimal txAmount, BigDecimal txIncome, Integer type, Integer linkType, Long linkId,
            Long productId, String investRedpacketId, String depositRedpacketId, Integer expireMode, Integer device, Long startDate, Long endDate, String freeCopies,
            Integer state, Long created, Long updated);

    /**
     * 查询id
     * @param type 类型
     * @param linkType 交易类型
     * @param expireMode 复投类型
     * @param state 状态
     * @return
     */
    List<Long> queryIds(Integer type, Integer linkType, Integer expireMode, Integer state);

    WmJxFreezeRecord queryById(Long id, LockModeType lockType);

    WmJxFreezeRecord createOrUpdate(WmJxFreezeRecord jxFreezeRecord);

    /**
     * 根据类型，交易类型，状态查询id
     * @param type 类型
     * @param linkType 交易类型
     * @param state 状态
     * @return
     */
    List<Long> queryIds(Integer type, Integer linkType, Integer state);

    /**
     * 更新状态
     * @param jxFreezeRecord 交易冻结记录
     * @param state 目标状态
     * @return
     */
    WmJxFreezeRecord updateStatus(WmJxFreezeRecord jxFreezeRecord, Integer state) throws Exception;
}