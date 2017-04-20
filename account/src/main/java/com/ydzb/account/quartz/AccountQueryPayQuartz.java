package com.ydzb.account.quartz;


import com.ydzb.account.context.IDRange;
import com.ydzb.account.entity.WmPayManuaStatus;
import com.ydzb.account.service.IPayService;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 第三方代付结果查询
 */
public class AccountQueryPayQuartz {

    @Autowired
    private IPayService payService;

    public void accountJob() {

        //查询民生打款定时运行状态
        WmPayManuaStatus wmPayManuaStatus = null;

        //民生打款查询
        try {
            wmPayManuaStatus = payService.findStatus();
            if (wmPayManuaStatus != null && wmPayManuaStatus.getStatus().intValue() == 1) {

                //锁定表 状态修改
                wmPayManuaStatus.setStatus(2);
                payService.updateWmPayManuaStatus(wmPayManuaStatus);

                //查询代付状态
                accountQueryPay();

                //锁定表 状态修改
                wmPayManuaStatus.setStatus(1);
                payService.updateWmPayManuaStatus(wmPayManuaStatus);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (wmPayManuaStatus != null) {
                //锁定表 状态修改
                wmPayManuaStatus.setStatus(1);
                payService.updateWmPayManuaStatus(wmPayManuaStatus);
            }
        }

    }


    /**
     * 查询代付状态
     *
     * @throws Exception
     */
    private void accountQueryPay() throws Exception {
        IDRange idRange = payService.findMaxIdAndMinId();
        if (idRange != null && idRange.getMaxId() > 0 && idRange.getMinId() > 0) {
            for (long id = idRange.getMinId(); id <= idRange.getMaxId(); id++) {
                try {
                    payService.accountQueryPay(id);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public IPayService getPayService() {
        return payService;
    }

    public void setPayService(IPayService payService) {
        this.payService = payService;
    }
}
