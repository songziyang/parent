package com.ydzb.withdraw.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.withdraw.entity.ManualBalance;
import com.ydzb.withdraw.entity.PayManualRequest;

import java.math.BigDecimal;

public interface IPayManualRequestService extends IBaseService<PayManualRequest, Long> {


    /**
     * 代付请求
     *
     * @param userId
     * @param linkId
     * @param type
     * @param bankName
     * @param accountNo
     * @param accountName
     * @param fund
     * @param transCardId
     * @param transMobile
     * @throws Exception
     */
    public void querysendPayXml(Long userId, Long linkId, Integer type, String bankName, String accountNo, String accountName, BigDecimal fund, String transCardId, String transMobile) throws Exception;

    /**
     * 平台余额查询
     *
     * @return
     * @throws Exception
     */
    public ManualBalance queryAccountBalance() throws Exception;


    /**
     * 根据请求流水号查询请求记录
     *
     * @param bussflowno 请求流水号
     * @return
     */
    public PayManualRequest findPayManualRequestByBussflowno(String bussflowno);

}
