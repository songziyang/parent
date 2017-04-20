package com.ydzb.user.service.impl;

import com.ydzb.admin.shiro.ShiroUser;
import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.user.entity.ChannelManager;
import com.ydzb.user.repository.IChannelManagerRepository;
import com.ydzb.user.service.IChannelManagerService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ChannelManagerServiceImpl extends BaseServiceImpl<ChannelManager, Long> implements IChannelManagerService {

    @Autowired
    private IChannelManagerRepository channelManagerRepository;

    @Override
    public void saveAuthorization(Integer type, String[] limitinfos,String promptInfo) throws Exception {
        Subject currentUser = SecurityUtils.getSubject();
        ShiroUser shiroUser = (ShiroUser) currentUser.getPrincipal();
        if (type != null && limitinfos != null && limitinfos.length > 0) {

            //更新以前记录
            ChannelManager channelManager = channelManagerRepository.findChannelManagerByType(type);
            if (channelManager != null) {
                channelManager.setStatus(2);
                channelManager.setUpdatedUser(shiroUser.getUsername());
                channelManagerRepository.save(channelManager);
            }

            //第三方渠道管理
            ChannelManager cm = new ChannelManager();
            cm.setType(type);
            cm.setStatus(1);
            cm.setCreated(DateUtil.getSystemTimeSeconds());
            cm.setCreatedUser(shiroUser.getUsername());
            cm.setPromptInfo(promptInfo);
            StringBuffer sb = new StringBuffer();
            for(String limitinfo : limitinfos){
                sb.append(limitinfo);
                sb.append(",");
            }
            cm.setLimitInfo(sb.toString());
            channelManagerRepository.save(cm);

        }
    }

    @Override
    public void updateChannelManager(Long id) throws Exception {
        Subject currentUser = SecurityUtils.getSubject();
        ShiroUser shiroUser = (ShiroUser) currentUser.getPrincipal();
        //更新以前记录
        ChannelManager channelManager = channelManagerRepository.findOne(id);
        if (channelManager != null) {
            channelManager.setStatus(2);
            channelManager.setUpdatedUser(shiroUser.getUsername());
            channelManagerRepository.save(channelManager);
        }
    }

    public IChannelManagerRepository getChannelManagerRepository() {
        return channelManagerRepository;
    }

    public void setChannelManagerRepository(IChannelManagerRepository channelManagerRepository) {
        this.channelManagerRepository = channelManagerRepository;
    }
}