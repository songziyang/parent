package com.ydzb.packet.service.impl;

import com.ydzb.admin.shiro.ShiroUser;
import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.packet.entity.RedPacketInterest;
import com.ydzb.packet.entity.RedPacketVoucher;
import com.ydzb.packet.repository.IRedPacketVoucherRepository;
import com.ydzb.packet.service.IRedpacketVoucherService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 红包-代金券service实现
 */
@Service
public class RedPacketVoucherServiceImpl extends BaseServiceImpl<RedPacketVoucher, Long> implements IRedpacketVoucherService {

    @Autowired
    private IRedPacketVoucherRepository voucherRepository;
/*    @Autowired
    private IProductTypeRepository productTypeRepository;*/

    /**
     * 保存
     *
     * @param voucher
     * @param aBeginTime  活动开始时间字符串
     * @param aFinishTime 活动结束时间字符串
     * @return
     */
    @Override
    public String saveOne(RedPacketVoucher voucher, String aBeginTime, String aFinishTime, Integer[] productDaysArr) {
        if (voucher == null) {
            return null;
        }
        Long id = voucher.getId();
        Long currentTime = DateUtil.getSystemTimeSeconds();
        ShiroUser curUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        //如果是新建
        if (id == null) {
            voucher.setStatus(RedPacketInterest.STATE_INUSE);
            voucher.setCreated(currentTime);
            voucher.setCreateUser(curUser.getId());
        }
        //如果是编辑
        else {
            RedPacketVoucher sourceVoucher = voucherRepository.findOne(id);
            sourceVoucher.setName(voucher.getName());
            sourceVoucher.setUseDays(voucher.getUseDays());
            sourceVoucher.setFund(voucher.getFund());
            sourceVoucher.setLimitFund(voucher.getLimitFund());
            sourceVoucher.setUpdateUser(curUser.getId());
            sourceVoucher.setUpdated(currentTime);
            voucher = sourceVoucher;
        }
        voucher.setBeginTime(DateUtil.getSystemTimeDay(aBeginTime));
        voucher.setEndTime(DateUtil.getSystemTimeDay(aFinishTime));

        //产品天数
        if (productDaysArr != null && productDaysArr.length > 0) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < productDaysArr.length; i++) {
                sb.append(productDaysArr[i]);
                if (i < productDaysArr.length - 1) {
                    sb.append(",");
                }
            }
            voucher.setProductDays(sb.toString());
        }

        voucherRepository.save(voucher);
        return null;
    }

    /**
     * 停用
     *
     * @param id
     */
    @Override
    public void disable(Long id) {
        updateStatus(id, RedPacketInterest.STATE_DISABLED);
    }

    /**
     * 启用
     *
     * @param id
     */
    @Override
    public void enable(Long id) {
        updateStatus(id, RedPacketInterest.STATE_INUSE);
    }

    /**
     * 根据id改变该加息券的状态值
     *
     * @param id
     * @param status
     */
    @Override
    public void updateStatus(Long id, byte status) {
        voucherRepository.updateStatus(id, status);
    }

    /**
     * 根据使用状态查询加息券
     *
     * @param status
     * @return
     */
    @Override
    public List<RedPacketVoucher> findAll(byte status) {
        return voucherRepository.findAll(status);
    }
}
