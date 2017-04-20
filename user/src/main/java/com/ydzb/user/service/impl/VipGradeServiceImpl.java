package com.ydzb.user.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.user.entity.VipGrade;
import com.ydzb.user.repository.IVipGradeRepository;
import com.ydzb.user.service.IVipGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * vip等级service实现类
 * @author sy
 */
@Service
public class VipGradeServiceImpl extends BaseServiceImpl<VipGrade, Long> implements IVipGradeService {

    @Autowired
    private IVipGradeRepository vipGradeRepository;

    /**
     * 根据用户查询
     * @param userId
     * @return
     */
    @Override
    public VipGrade queryByUser(Long userId) {
        return vipGradeRepository.queryByUser(userId);
    }
}