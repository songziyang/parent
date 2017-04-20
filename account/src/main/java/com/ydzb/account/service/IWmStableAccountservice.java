package com.ydzb.account.service;

import com.ydzb.account.context.IDRange;
import com.ydzb.account.entity.WmStable;

import java.util.List;

/**
 * 基金产品结算service接口
 */
public interface IWmStableAccountservice {

    /**
     * 根据申购结束日期更新满标状态
     * 将申购结束日期为指定日期并且状态为申购的基金产品状态置为满标状态
     * @param stable 基金产品
     */
    public void updateFullStandardState(final WmStable stable) throws Exception;

    /**
     * 结算收益
     * @param stable 基金产品
     * @param userId 用户ID
     * @throws Exception
     */
    public void accountStableRevenue(WmStable stable, Long userId) throws Exception;

    /**
     * 根据到期日期查询基金产品
     * @param closeDate 到期日期
     * @return
     */
    public List<WmStable> findByCloseDateAndNoApr(Long closeDate);

    /**
     * 根据申购结束日期查询状态为申购中的
     * @param date 申购结束日期
     * @return
     */
    public List<WmStable> findByEndDate(final Long date);

    /**
     * 根据到期日期查询基金产品
     * @param closeDate 到期日期
     * @return
     */
    public List<WmStable> findByCloseDate(final Long closeDate);

    /**
     * 更新基金产品信息
     * @param stable 基金产品
     */
    public void updateStable(final WmStable stable) throws Exception;

}