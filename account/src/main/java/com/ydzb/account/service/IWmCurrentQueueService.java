package com.ydzb.account.service;


import com.ydzb.account.context.IDRange;
import com.ydzb.account.entity.WmProductInfo;

import java.math.BigDecimal;

public interface IWmCurrentQueueService {

    /**
     * 查询排队表 最大ID 和 最小ID
     *
     * @return
     */
    public IDRange findMaxIdAndMinId();


    /**
     * 结算活期宝队列
     *
     * @param id
     * @throws Exception
     */


    public int accountCurrentQueue(Long id, Long productId, BigDecimal interestRate) throws Exception;


    /**
     * 查询产品
     *
     * @return
     */
    public WmProductInfo findWmProductInfo();

    /**
     * 查询产品总份数
     *
     * @return
     */
    public Integer findTotalCopies();


    /**
     * 更新产品
     *
     * @param productInfo
     */
    public void updateWmProductInfo(WmProductInfo productInfo);
}
