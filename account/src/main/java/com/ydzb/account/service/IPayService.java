package com.ydzb.account.service;


import com.ydzb.account.context.IDRange;
import com.ydzb.account.entity.WmPayManuaStatus;

import java.util.List;

public interface IPayService {

    /**
     * 查询受理成功的
     *
     * @return
     */
    public IDRange findMaxIdAndMinId();


    /**
     * 根据受理成功 查询民生处理状态
     *
     * @param id
     * @throws Exception
     */
    public void accountQueryPay(Long id) throws Exception;


    /**
     * 查询运行状态
     *
     * @return
     * @throws Exception
     */
    public WmPayManuaStatus findStatus();

    /**
     * 更新运行状态
     *
     * @param payManuaStatus
     * @throws Exception
     */
    public void updateWmPayManuaStatus(WmPayManuaStatus payManuaStatus);


    /**
     * 查询自动放款记录
     *
     * @return
     */
    public List<Object[]> findAutoUserWithDraw();


    /**
     * 查询待放款记录
     *
     * @return
     */
    public List<Object> findUserWithDrawStatus();

}
