package com.ydzb.account.service;


import com.ydzb.account.entity.WmRedPacketInterest;
import com.ydzb.account.entity.WmUser;

import java.util.List;

public interface IVipBirthdayService {


    public List<WmUser> findVipBirthdayByDate(String date);

    public List<WmUser> findBirthdayByDate(String date);

    public WmRedPacketInterest findWmRedPacketInterest();

    public int sendRedpacket(Long userId, Long redpacketId, String createName) throws Exception;


}
