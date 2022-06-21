package com.service.impl;

import com.bean.Message;
import com.dao.MessageDao;
import com.dao.impl.MessageDaoImpl;
import com.service.MessageService;

import java.util.List;

public class MessageServiceImpl implements MessageService {

    MessageDao messageDao = new MessageDaoImpl();

    @Override
    public void sendMessageById(int senderId, int receiverId, String text, String time) {
        messageDao.sandById(senderId, receiverId, text, time);
    }

    @Override
    public List<Message> findByReceiverId(int id) {
        return messageDao.findByReceiverId(id);
    }

    @Override
    public List<Message> findBySenderId(int id) {
        return messageDao.findBySenderId(id);
    }
}
