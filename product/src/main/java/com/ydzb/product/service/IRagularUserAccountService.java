package com.ydzb.product.service;


import com.ydzb.core.service.IBaseService;
import com.ydzb.product.entity.RagularUserAccount;
import com.ydzb.product.entity.TopRagularUser;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public interface IRagularUserAccountService extends IBaseService<RagularUserAccount, Long> {


    public BigDecimal findTotalFund(Long startDate,Long endDate);



    public String exportExcele(List<Object[]> ragularUserAccounts, String address);

    /**
     * 根据定存产品类型(type)、到期状态(status)以及购买起始时间获得总购买金额
     * @param type
     * @param status
     * @param startTime
     * @param endTime
     * @param mustExistTime 是否必须存在起始时间
     * @return
     */
    public BigDecimal findTotalBuyFund(long[] type, Byte status, Long startTime, Long endTime, boolean mustExistTime);

    /**
     * 根据定存产品类型(type)、到期状态(status)以及购买起始时间获得总购买金额
     * @param type
     * @param status
     * @param startTime
     * @param endTime
     * @param mustExistTime 是否必须存在起始时间
     * @return
     */
    public BigDecimal findTotalBuyFund(long type, Byte status, Long startTime, Long endTime, boolean mustExistTime);

    /**
     * 查询某一个类型的定存交易记录累计购买人数
     * @param type
     * @return
     */
    public BigDecimal findTotalBuyFund(long[] type);

    /**
     * 查询某一个类型的定存交易记录累计购买人数
     * @param type
     * @return
     */
    public BigDecimal findTotalBuyFund(Long type);

    /**
     * 根据产品类型(type)查询累计交易笔数
     * @param type
     * @return
     */
    public BigInteger getTotalTradeNum(long[] type);

    /**
     * 根据产品类型(type)查询累计交易笔数
     * @param type
     * @return
     */
    public BigInteger getTotalTradeNum(Long type);

    /**
     * 根据定存类型(type)、到期状态(status)以及到期起始时间获得总到期(购买)金额
     * @param type
     * @param status
     * @param startTime
     * @param endTime
     * @param mustExistTime 是否必须存在起始时间
     * @return
     */
    public BigDecimal getClosingFund(long[] type, Byte status, Long startTime, Long endTime, boolean mustExistTime);

    /**
     * 根据定存类型(type)、到期状态(status)以及到期起始时间获得总到期(购买)金额
     * @param type
     * @param status
     * @param startTime
     * @param endTime
     * @param mustExistTime 是否必须存在起始时间
     * @return
     */
    public BigDecimal getClosingFund(Long type, Byte status, Long startTime, Long endTime, boolean mustExistTime);

    public List<Object[]> findExportData(Map<String, Object> filter);

    /**
     * 查询在指定时间段内，购买定存（不含债权转让）最多的Top N用户
     * @return
     */
    public List<TopRagularUser> findTopRagularUser();
}
