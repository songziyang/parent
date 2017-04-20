package com.ydzb.account.service;


public interface IWmUserWithDrawNumService {

    /**
     * 统计七天内提现次数
     *
     * @param endDate 系统当前时间
     * @throws Exception
     */
    public void accountQueryWithDraw(Long endDate) throws Exception;

}
