package com.ydzb.account.service.impl;

import com.ydzb.account.entity.WmRedPacketInterest;
import com.ydzb.account.entity.WmUser;
import com.ydzb.account.repository.VipBirthdayRepository;
import com.ydzb.account.service.IVipBirthdayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class VipBirthdayServiceImpl implements IVipBirthdayService {

    @Autowired
    private VipBirthdayRepository vipBirthdayRepository;

    @Override
    public List<WmUser> findVipBirthdayByDate(String date) {
        return vipBirthdayRepository.findVipBirthdayByDate(date);
    }

    @Override
    public List<WmUser> findBirthdayByDate(String date) {
        return vipBirthdayRepository.findBirthdayByDate(date);
    }

    @Override
    public int sendRedpacket(Long userId, Long redpacketId, String createName) throws Exception {
        return vipBirthdayRepository.sendRedpacket(userId, redpacketId, createName);
    }

    @Override
    public WmRedPacketInterest findWmRedPacketInterest() {
        return vipBirthdayRepository.findWmRedPacketInterest();
    }

    public VipBirthdayRepository getVipBirthdayRepository() {
        return vipBirthdayRepository;
    }

    public void setVipBirthdayRepository(VipBirthdayRepository vipBirthdayRepository) {
        this.vipBirthdayRepository = vipBirthdayRepository;
    }
}
