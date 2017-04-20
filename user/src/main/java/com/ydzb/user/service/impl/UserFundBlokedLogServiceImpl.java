package com.ydzb.user.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.user.entity.UserFundBlokedLog;
import com.ydzb.user.repository.IUserFundBlokedLogRepository;
import com.ydzb.user.service.IUserFundBlokedLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
public class UserFundBlokedLogServiceImpl extends BaseServiceImpl<UserFundBlokedLog, Long> implements IUserFundBlokedLogService {

    @Autowired
    private IUserFundBlokedLogRepository userFundBlokedLogRepository;


    @Override
    public Long saveUserFundBlokedLog(Long userId, Integer type, Integer linkType, Long linkId, BigDecimal fund, BigDecimal usableFund) {
        UserFundBlokedLog userFundBlokedLog = new UserFundBlokedLog();
        userFundBlokedLog.setUserId(userId);
        userFundBlokedLog.setType(type);
        userFundBlokedLog.setLinkType(linkType);
        userFundBlokedLog.setLinkId(linkId);
        userFundBlokedLog.setFund(fund);
        userFundBlokedLog.setUsableFund(usableFund);
        userFundBlokedLog.setOperationTime(DateUtil.getSystemTimeSeconds());
        userFundBlokedLogRepository.save(userFundBlokedLog);

        return userFundBlokedLog.getId();
    }


    /**
     * 查询冻结的资金
     *
     * @return
     */
    @Override
    public List<UserFundBlokedLog> findUserFundBlokedLogByType(Long userId) {
        return userFundBlokedLogRepository.findUserFundBlokedLogByType(userId);
    }

    /**
     *
     * @param userId
     * @return
     */
    @Override
    public List<UserFundBlokedLog> findExUserFundBlokedLogByType(Long userId) {
        return userFundBlokedLogRepository.findExUserFundBlokedLogByType(userId);
    }

    public IUserFundBlokedLogRepository getUserFundBlokedLogRepository() {
        return userFundBlokedLogRepository;
    }

    public void setUserFundBlokedLogRepository(IUserFundBlokedLogRepository userFundBlokedLogRepository) {
        this.userFundBlokedLogRepository = userFundBlokedLogRepository;
    }
}
