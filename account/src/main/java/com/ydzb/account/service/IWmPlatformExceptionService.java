package com.ydzb.account.service;


import com.ydzb.account.entity.WmUserFundBlokedLog;
import com.ydzb.account.entity.WmUserFundInLog;
import com.ydzb.account.entity.WmUserFundOutLog;

import java.util.List;

/**
 * 平台交易记录异常检测
 */
public interface IWmPlatformExceptionService {

    /**
     * 异常记录保存
     *
     * @param userId  用户ID
     * @param linkId  异常记录ID
     * @param type    异常记录类型
     * @param created 记录时间
     * @throws Exception
     */
    public void account(Long userId, Long linkId, String type, Long created) throws Exception;

    /**
     * 查询入账异常记录
     *
     * @param type 入账类型
     * @return
     */
    public List<WmUserFundInLog> findWmUserFundInLogByType(Integer type);

    /**
     * 查询出账异常记录
     *
     * @param type 出账类型
     * @return
     */
    public List<WmUserFundOutLog> findWmUserFundOutLogByType(Integer type);

    /**
     * 查询冻结异常记录
     *
     * @param type 冻结类型
     * @return
     */
    public List<WmUserFundBlokedLog> findWmUserFundBlokedLogByType(Integer type);

}
