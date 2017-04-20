package com.ydzb.packet.service.impl;

import com.ydzb.admin.shiro.ShiroUser;
import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.packet.entity.RedPacketInterest;
import com.ydzb.packet.repository.IRedPacketInterestRepository;
import com.ydzb.packet.service.IRedPacketInterestService;
import com.ydzb.product.entity.ProductType;
import com.ydzb.product.repository.IProductTypeRepository;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 红包-加息券 service实现类
 * @author sy
 */
@Service
public class RedPacketInterestServiceImpl extends BaseServiceImpl<RedPacketInterest, Long>
        implements IRedPacketInterestService {

    @Autowired
    private IRedPacketInterestRepository interestRepository;
    @Autowired
    private IProductTypeRepository productTypeRepository;

    /**
     * 保存
     * @param interest
     * @param aBeginTime 活动开始时间字符串
     * @param aFinishTime 活动结束时间字符串
     * @return
     */
    @Override
    public String saveOne(RedPacketInterest interest, String aBeginTime, String aFinishTime) {
        if (interest == null) {
            return null;
        }
        Long id = interest.getId();
        Long currentTime = DateUtil.getSystemTimeSeconds();
        ShiroUser curUser = (ShiroUser)SecurityUtils.getSubject().getPrincipal();
        //如果是新建
        if (id == null) {
            interest.setStatus(RedPacketInterest.STATE_INUSE);
            interest.setCreated(currentTime);
            interest.setCreatedUser(curUser.getUsername());
            setProductType(interest);
        }
        //如果是编辑
        else {
            RedPacketInterest sourceInterest = interestRepository.findOne(id);
            sourceInterest.setName(interest.getName());
            sourceInterest.setUseDays(interest.getUseDays());
            sourceInterest.setInvestDays(interest.getInvestDays());
            sourceInterest.setActivityBeginTime(interest.getActivityBeginTime());
            sourceInterest.setActivityFinishTime(interest.getActivityFinishTime());
            sourceInterest.setGiveValue(interest.getGiveValue());
            sourceInterest.setRemark(interest.getRemark());
            sourceInterest.setUpdated(currentTime);
            sourceInterest.setUpdatedUser(curUser.getUsername());
            interest = sourceInterest;
        }
        interest.setActivityBeginTime(DateUtil.getSystemTimeDay(aBeginTime));
        interest.setActivityFinishTime(DateUtil.getSystemTimeDay(aFinishTime));
        interestRepository.save(interest);
        return null;
    }

    /**
     * 停用
     * @param id
     */
    @Override
    public void disable(Long id) {
        updateStatus(id, RedPacketInterest.STATE_DISABLED);
    }

    /**
     * 启用
     * @param id
     */
    @Override
    public void enable(Long id) {
        updateStatus(id, RedPacketInterest.STATE_INUSE);
    }

    /**
     * 根据id改变该加息券的状态值
     * @param id
     * @param status
     */
    @Override
    public void updateStatus(Long id, byte status) {
        interestRepository.updateStatus(id, status);
    }

    /**
     * 根据使用状态查询加息券
     * @param status
     * @return
     */
    @Override
    public List<RedPacketInterest> findAll(byte status) {
        return interestRepository.findAll(status);
    }

    /**
     * 设置产品类型
     * @param interest
     */
    private void setProductType(RedPacketInterest interest) {
        ProductType productType = interest.getProductType();
        if (productType == null) {
            return;
        }
        //拿到持久化状态的ProductType
        Long productTypeId = interest.getProductType().getId();
        productType = productTypeRepository.findOne(productTypeId);
        interest.setProductType(productType);
    }
}