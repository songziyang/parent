package com.ydzb.withdraw.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.withdraw.entity.PayManualRecord;

/**
 * 代付后台打款service接口
 */
public interface IPayManualRecordService extends IBaseService<PayManualRecord, Long> {

    /**
     * 保存
     *
     * @return
     */
    public String savePayManualRecord(PayManualRecord payManualRecord) throws Exception;

    /**
     * 审核成功
     *
     * @param id
     */
    public String auditSuccess(Long id) throws Exception;

    /**
     * 审核失败
     *
     * @param id
     */
    public void auditFailure(Long id) throws Exception;


    /**
     * 发送请求
     *
     * @param id
     * @throws Exception
     */
    public void querysendRequest(Long id) throws Exception;
}