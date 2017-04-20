package com.ydzb.product.service.impl;

import com.ydzb.admin.shiro.ShiroUser;
import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.product.entity.Structure;
import com.ydzb.product.repository.IStructureRepository;
import com.ydzb.product.service.IStructureService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 */
@Service
public class StructureServiceImpl extends BaseServiceImpl<Structure, Long> implements IStructureService {

    @Autowired
    private IStructureRepository structureRepository;

    @Override
    public void saveOne(Structure structure, String startDate, String endDate) {
        if (structure != null) {
            if (structure.getId() == null) {
                execCreate(structure, startDate, endDate);
            } else {
                execEdit(structure, startDate, endDate);
            }
        }
    }

    private void execEdit(Structure structure, String startDate, String endDate) {
        if (structure != null) {
            structure.setStartDate(DateUtil.getSystemTimeDay(startDate));
            structure.setEndDate(DateUtil.getSystemTimeDay(endDate));
            structure.setUpdateUser(((ShiroUser)SecurityUtils.getSubject().getPrincipal()).getUsername());
            structureRepository.save(structure);
        }
    }

    private void execCreate(Structure structure, String startDate, String endDate) {
        if (structure != null) {
            structure.setRemainingCopies(structure.getCopies());
            structure.setApr((structure.getApr() == null || structure.getApr().compareTo(BigDecimal.ZERO) == 0)?
                    Structure.APR_DEFAULT: structure.getApr());
            structure.setHelpMaxApr((structure.getHelpMaxApr() == null || structure.getHelpMaxApr().compareTo(BigDecimal.ZERO) == 0)?
                    Structure.HELP_MAX_APR_DEFAULT: structure.getHelpMaxApr());
            structure.setDays(structure.getDays() == null? Structure.DAYS_DEFAULT: structure.getDays());
            structure.setMinCopies(structure.getMinCopies() == null? Structure.MIN_COPIES_DEFAULT: structure.getMinCopies());
            structure.setMaxCopies(structure.getMaxCopies() == null? Structure.MAX_COPIES_DEFAULT: structure.getMaxCopies());
            structure.setVipMaxCopies(structure.getVipMaxCopies() == null? Structure.VIP_MAX_COPIES_DEFAULT: structure.getVipMaxCopies());
            structure.setState(Structure.STATE_ALLOT);
            structure.setCreated(DateUtil.getSystemTimeSeconds());
            structure.setCreateUser(((ShiroUser)SecurityUtils.getSubject().getPrincipal()).getUsername());
            structure.setStartDate(DateUtil.getSystemTimeDay(startDate));
            structure.setEndDate(DateUtil.getSystemTimeDay(endDate));
            structureRepository.save(structure);
        }
    }
}