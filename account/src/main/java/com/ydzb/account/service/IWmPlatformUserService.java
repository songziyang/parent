package com.ydzb.account.service;


import com.ydzb.account.context.WmPlatformRecordLinkType;
import com.ydzb.account.entity.WmPlatformUser;

import java.math.BigDecimal;

/**
 * 平台用户service接口
 */
public interface IWmPlatformUserService {

    /**
     * 查询最新的一条数据
     *
     * @return
     */
    Long findLastPlatformUser();

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    WmPlatformUser findPlatformUserById(Long id);


    /**
     * 用户购买债权
     *
     * @param opfund   操作金额
     * @param linkId   链接ID
     * @param linkType 链接类型
     * @param opinfo   操作说明
     */
    void purchase(BigDecimal opfund, Long linkId, WmPlatformRecordLinkType linkType, String opinfo);

    /**
     * 用户赎回债权
     *
     * @param opfund   操作金额
     * @param linkId   链接ID
     * @param linkType 链接类型
     * @param opinfo   操作说明
     */
    void redeem(BigDecimal opfund, Long linkId, WmPlatformRecordLinkType linkType, String opinfo);


}
