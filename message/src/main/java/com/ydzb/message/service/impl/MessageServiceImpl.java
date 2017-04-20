package com.ydzb.message.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.message.repository.IMessageRepository;
import com.ydzb.message.service.IMessageService;
import com.ydzb.sms.entity.Message;
import com.ydzb.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MessageServiceImpl extends BaseServiceImpl<Message, Long>
        implements IMessageService {

    @Autowired
    private IMessageRepository messageRepository;


    @Override
    public String saveMessage(Message message) throws Exception {

        if (null == message.getUser().getId()) {
            User user = messageRepository.findUserByUsername(message.getUsername());
            if (null == user)
                return "用户名不存在！";
            message.setUser(user);
        }

        if (null == message.getId()) {
            message.setDeleted(0);
            message.setType(0);
            message.setStatus(0);
            message.setCreated(DateUtil.getSystemTimeSeconds());
        }
        messageRepository.save(message);
        return null;
    }

    public IMessageRepository getMessageRepository() {
        return messageRepository;
    }

    public void setMessageRepository(IMessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

}
