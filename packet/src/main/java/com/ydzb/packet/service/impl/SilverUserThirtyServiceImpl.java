package com.ydzb.packet.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.packet.entity.SilverUserThirty;
import com.ydzb.packet.repository.ISilverUserThirtyRepository;
import com.ydzb.packet.repository.SilverUserThirtyRepository;
import com.ydzb.packet.service.ISilverUserThirtyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.LockModeType;

/**
 * Created by sy on 2016/7/3.
 */
@Service
public class SilverUserThirtyServiceImpl extends BaseServiceImpl<SilverUserThirty, Long> implements ISilverUserThirtyService {

    @Autowired
    private ISilverUserThirtyRepository silverUserThirtyRepository;
    @Autowired
    private SilverUserThirtyRepository cSilverUserThirtyRepository;

    /**
     * 创建
     * @param userId
     * @return
     */
    @Override
    public SilverUserThirty createOne(Long userId, Integer totalFund, Integer usableFund) {
        SilverUserThirty silverUserThirty = new SilverUserThirty();
        silverUserThirty.setUserId(userId);
        silverUserThirty.setTotalFund(totalFund);
        silverUserThirty.setUsableFund(usableFund);
        silverUserThirty.setCreated(DateUtil.getSystemTimeSeconds());
        return silverUserThirtyRepository.save(silverUserThirty);
    }

    /**
     * 根据用户查询
     * @param userId
     * @return
     */
    @Override
    public SilverUserThirty queryByUser(Long userId, LockModeType lockType) {

        if (lockType == null || lockType == LockModeType.NONE) {
            return silverUserThirtyRepository.queryByUser(userId);
        }
        Long id = silverUserThirtyRepository.queryIdByUser(userId);
        return cSilverUserThirtyRepository.queryById(id, lockType);
    }
}
