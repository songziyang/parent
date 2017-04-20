package com.ydzb.account.service;

import com.ydzb.account.entity.WmRagularTransfer;
import com.ydzb.account.entity.WmRagularUserAccount;

import java.util.List;

/**
 * 定存转让service接口
 */
public interface IWmRagularTransferService {

    /**
     * 设置为失败
     * @return
     */
    WmRagularTransfer transferFail(WmRagularTransfer ragularTransfer);

    /**
     * 设置为转让失败
     * @param ragularTransfers
     * @return
     */
    WmRagularTransfer transferFail(List<WmRagularTransfer> ragularTransfers);

    /**
     * 取消债券装让
     * @param userAccount 定存账户
     * @throws Exception
     */
    void cancelTransfer(WmRagularUserAccount userAccount) throws Exception;
}