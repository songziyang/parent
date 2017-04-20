package com.ydzb.account.service.impl;

import com.ydzb.account.entity.WmRagularTransfer;
import com.ydzb.account.entity.WmRagularUserAccount;
import com.ydzb.account.entity.WmUser;
import com.ydzb.account.repository.WmRagularTransferRepository;
import com.ydzb.account.repository.WmRagularUserAccountRepository;
import com.ydzb.account.service.IWmInfoSmsTimerService;
import com.ydzb.account.service.IWmRagularTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 定存转让service实现
 */
@Service
public class WmRagularTransferServiceImpl implements IWmRagularTransferService {

    @Autowired
    private WmRagularTransferRepository ragularTransferRepository;
    @Autowired
    private WmRagularUserAccountRepository ragularUserAccountRepository;
    @Autowired
    private IWmInfoSmsTimerService infoSmsTimerService;

    /**
     * 设置为失败
     * @return
     */
    @Override
    public WmRagularTransfer transferFail(WmRagularTransfer ragularTransfer) {
        if (ragularTransfer != null) {
            ragularTransfer.setStatus(WmRagularTransfer.STATUS_TRANSFER_FAIL);
            return ragularTransferRepository.createOrUpdate(ragularTransfer);
        }
        return null;
    }

    /**
     * 设置为转让失败
     * @param ragularTransfers
     * @return
     */
    @Override
    public WmRagularTransfer transferFail(List<WmRagularTransfer> ragularTransfers) {
        if (ragularTransfers != null) {
            for (WmRagularTransfer ragularTransfer: ragularTransfers) {
                transferFail(ragularTransfer);
            }
        }
        return null;
    }

    /**
     * 取消债券装让
     * @param userAccount 定存账户
     * @throws Exception
     */
    @Override
    public void cancelTransfer(WmRagularUserAccount userAccount) throws Exception {
        if (userAccount != null) {
            //将定存转让设置为转让失败
            List<WmRagularTransfer> ragularTransfers = ragularTransferRepository.queryOnes(WmRagularTransfer.STATUS_TRANSFERING, userAccount.getUserId(), userAccount.getId());
            transferFail(ragularTransfers);

            //重置定存时间与状态
            userAccount.setApplyTransferTime(null);
            userAccount.setApplyOptionTime(null);
            userAccount.setStatus(0);
            ragularUserAccountRepository.updateWmRagularUserAccount(userAccount);

            //发送取消转让站内信和短信
            try {
                WmUser user = ragularUserAccountRepository.findWmUserById(userAccount.getUserId());
                if (user != null) {
                    infoSmsTimerService.sendWmInfoSmsTimer("transfer_cancel", user.getMobile(), "name:" + user.getUsername() + ",value:" + userAccount.getBuyFund().intValue());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}