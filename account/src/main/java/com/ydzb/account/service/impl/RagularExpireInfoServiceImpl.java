package com.ydzb.account.service.impl;

import com.ydzb.account.entity.WmProductInfo;
import com.ydzb.account.entity.WmRagularUserAccount;
import com.ydzb.account.entity.WmUser;
import com.ydzb.account.repository.RagularExpireInfoRepository;
import com.ydzb.account.repository.WmRagularUserAccountRepository;
import com.ydzb.account.service.IRagularExpireInfoService;
import com.ydzb.account.service.IWmInfoSmsTimerService;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.sms.service.ISmsHandleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;


@Service
public class RagularExpireInfoServiceImpl implements IRagularExpireInfoService {

    @Autowired
    private RagularExpireInfoRepository ragularExpireInfoRepository;

    @Autowired
    private WmRagularUserAccountRepository ragularUserAccountRepository;

    @Autowired
    private ISmsHandleService smsHandleService;

    @Autowired
    private IWmInfoSmsTimerService infoSmsTimerService;


    /**
     * 定存复投提示
     *
     * @throws Exception
     */
    @Override
    public void accountRagularExpireInfo() throws Exception {
        //系统当前日期 + 1天
        Long currentDate = DateUtil.getSystemTimeDay(DateUtil.curDate()) + 24 * 3600;
        //即将到期的定存
        List<WmRagularUserAccount> wmRagularUserAccounts = ragularExpireInfoRepository.findWmRagularUserAccounts(currentDate);
        //循环遍历
        if (wmRagularUserAccounts != null && !wmRagularUserAccounts.isEmpty()) {
            for (WmRagularUserAccount wmRagularUserAccount : wmRagularUserAccounts) {
                //发送短信和站内信
                sendSiteContent(wmRagularUserAccount.getUserId(), wmRagularUserAccount.getBuyFund(), wmRagularUserAccount.getProductId());
            }
        }


    }


    /**
     * 发送站内信 和 短信
     *
     * @param userId
     * @param fund
     * @param productId
     */
    public void sendSiteContent(Long userId, BigDecimal fund, Long productId) {
        try {
            WmUser user = ragularUserAccountRepository.findWmUserById(userId);
            WmProductInfo productInfo = ragularUserAccountRepository.findWmProductInfoById(productId);
            Long currentDate = DateUtil.getSystemTimeDay(DateUtil.curDate()) + 24 * 3600;
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String date = df.format(DateUtil.getSystemTimeMillisecond(currentDate));
            if (user != null && productInfo != null) {
                String pname = productInfo.getName();
                smsHandleService.addSiteContent("deposit_mode_info", userId, pname + "复投提示", "name:" + user.getUsername() + ",value:" + fund.intValue() + ",ptype:" + pname + ",date:" + date, 0);
                infoSmsTimerService.sendWmInfoSmsTimer("deposit_mode_info", user.getMobile(), "name:" + user.getUsername() + ",value:" + fund.intValue() + ",ptype:" + pname + ",date:" + date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public RagularExpireInfoRepository getRagularExpireInfoRepository() {
        return ragularExpireInfoRepository;
    }

    public void setRagularExpireInfoRepository(RagularExpireInfoRepository ragularExpireInfoRepository) {
        this.ragularExpireInfoRepository = ragularExpireInfoRepository;
    }

    public WmRagularUserAccountRepository getRagularUserAccountRepository() {
        return ragularUserAccountRepository;
    }

    public void setRagularUserAccountRepository(WmRagularUserAccountRepository ragularUserAccountRepository) {
        this.ragularUserAccountRepository = ragularUserAccountRepository;
    }

    public ISmsHandleService getSmsHandleService() {
        return smsHandleService;
    }

    public void setSmsHandleService(ISmsHandleService smsHandleService) {
        this.smsHandleService = smsHandleService;
    }

    public IWmInfoSmsTimerService getInfoSmsTimerService() {
        return infoSmsTimerService;
    }

    public void setInfoSmsTimerService(IWmInfoSmsTimerService infoSmsTimerService) {
        this.infoSmsTimerService = infoSmsTimerService;
    }
}
