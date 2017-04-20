package com.ydzb.withdraw.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.withdraw.entity.UserWithDraw;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


public interface IUserWithDrawService extends IBaseService<UserWithDraw, Long> {

    /**
     * @param userWithDraws
     * @param address
     * @return
     */
    public String exportExcele(List<UserWithDraw> userWithDraws, String address);

    public void updateWithDrawFailNew(Long id, String description, int status, BigDecimal advanceMoney) throws Exception;

    /**
     * 审核失败
     *
     * @param id
     * @param description
     * @param status
     * @param advanceMoney
     */
    public void updateWithDrawFail(Long id, String description, int status, BigDecimal advanceMoney) throws Exception;

    /**
     * 审核成功
     *
     * @param id
     * @param advanceMoney
     */
    public void updateWithDrawSuccess(Long id, BigDecimal advanceMoney) throws Exception;


    /**
     * 代付放款
     *
     * @param id
     * @return
     * @throws Exception
     */
    public String updateLoanMoney(Long id, String userName) throws Exception;

    /**
     * 代付手动成功
     *
     * @param id
     * @throws Exception
     */
    public String minSubmitWithDraw(Long id) throws Exception;


    /**
     * 代付手动失败
     *
     * @param id
     * @param description
     * @param status
     * @param advanceMoney
     * @return
     */
    public String minFailPayBack(Long id, String description, int status, BigDecimal advanceMoney);


    /**
     * 查询总提现金额以及打款金额
     *
     * @param filters
     * @return
     */
    public Object[] findSumMoney(Map<String, Object> filters);


    /**
     * 发送代付请求
     *
     * @param id
     * @throws Exception
     */
    public void querysendRequest(Long id) throws Exception;


    /**
     * 发送放款短信
     *
     * @param id
     */
    public void sendMessage(Long id);


    /**
     * 手动放款
     *
     * @param id
     * @return
     * @throws Exception
     */
    public String updatesdloanMoney(Long id) throws Exception;

}
