package com.ydzb.account.service.impl;

import com.ydzb.account.entity.WmPlatformException;
import com.ydzb.account.entity.WmUserFundBlokedLog;
import com.ydzb.account.entity.WmUserFundInLog;
import com.ydzb.account.entity.WmUserFundOutLog;
import com.ydzb.account.repository.WmPlatformExceptionRepository;
import com.ydzb.account.service.IWmPlatformExceptionService;
import com.ydzb.core.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class WmPlatformExceptionServiceImpl implements IWmPlatformExceptionService {

    @Autowired
    private WmPlatformExceptionRepository platformExceptionRepository;


    /**
     * 异常记录保存
     *
     * @param userId  用户ID
     * @param linkId  异常记录ID
     * @param type    异常记录类型
     * @param created 记录时间
     * @throws Exception
     */
    @Override
    public void account(Long userId, Long linkId, String type, Long created) throws Exception {
        //查询该异常记录是否记录
        List<WmPlatformException> platformExceptionList = platformExceptionRepository.findWmPlatformException(userId, linkId, type);
        if (platformExceptionList != null && !platformExceptionList.isEmpty()) {
            return;
        }
        //保存异常记录
        saveWmPlatformException(userId, linkId, type, created);
    }


    /**
     * 保存异常记录
     *
     * @param userId  用户ID
     * @param linkId  异常记录ID
     * @param type    异常记录类型
     * @param created 记录时间
     */
    public void saveWmPlatformException(Long userId, Long linkId, String type, Long created) {
        WmPlatformException platformException = new WmPlatformException();
        platformException.setUserId(userId);
        platformException.setLinkId(linkId);
        platformException.setType(type);
        platformException.setCreated(DateUtil.getSystemTimeSeconds());
        platformExceptionRepository.saveWmPlatformException(platformException);
    }


    /**
     * 查询入账异常记录
     *
     * @param type 入账类型
     * @return
     */
    @Override
    public List<WmUserFundInLog> findWmUserFundInLogByType(Integer type) {
        return platformExceptionRepository.findWmUserFundInLogByType(type);
    }

    /**
     * 查询出账异常记录
     *
     * @param type 出账类型
     * @return
     */
    @Override
    public List<WmUserFundOutLog> findWmUserFundOutLogByType(Integer type) {
        return platformExceptionRepository.findWmUserFundOutLogByType(type);
    }

    /**
     * 查询冻结异常记录
     *
     * @param type 冻结类型
     * @return
     */
    @Override
    public List<WmUserFundBlokedLog> findWmUserFundBlokedLogByType(Integer type) {
        return platformExceptionRepository.findWmUserFundBlokedLogByType(type);
    }

    public WmPlatformExceptionRepository getPlatformExceptionRepository() {
        return platformExceptionRepository;
    }

    public void setPlatformExceptionRepository(WmPlatformExceptionRepository platformExceptionRepository) {
        this.platformExceptionRepository = platformExceptionRepository;
    }
}
