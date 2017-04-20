package com.ydzb.account.service;

import com.ydzb.account.entity.WmRagularRefund;

import javax.persistence.LockModeType;
import java.math.BigDecimal;

/**
 * 定存还息service接口
 */
public interface IWmRagularRefundService {

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
    WmRagularRefund createOne(Long userId, Integer stage, Integer curStage, Long refundTime,
            BigDecimal fund, BigDecimal redpacketFund, BigDecimal vipFund, BigDecimal voucherFund,
            Integer state, Integer isExpire, Integer organization) throws Exception;

    /**
     * 更新换新记录状态
     * @param ragularRefund 还息记录
     * @param state 状态
     * @return
     */
    WmRagularRefund updateStatus(WmRagularRefund ragularRefund, Integer state) throws Exception;

    /**
     * 根据主键查询
     * @param id 主键id
     * @param lockType 锁类型
     * @return
     */
    WmRagularRefund queryById(Long id, LockModeType lockType);

    /**
     * 根据accountId查询
     * @param accountId 用户定存记录id
     * @param organization 机构类型/产品类型
     * @param expire 是否是最后一期
     * @return
     */
    WmRagularRefund queryByAccount(Long accountId, Integer organization, Integer expire);
}
