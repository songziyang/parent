package com.ydzb.user.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.user.entity.ChannelManager;


public interface IChannelManagerService extends IBaseService<ChannelManager, Long> {

    public void saveAuthorization(Integer type, String[] limitinfos,String promptInfo) throws Exception;

    public void updateChannelManager(Long id) throws Exception;

}
