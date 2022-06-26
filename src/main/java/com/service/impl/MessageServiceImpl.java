package com.service.impl;

import com.bean.Message;
import com.bean.Student;
import com.dao.MessageDao;
import com.dao.StudentDao;
import com.dao.TutorDao;
import com.dao.impl.MessageDaoImpl;
import com.dao.impl.StudentDaoImpl;
import com.dao.impl.TutorDaoImpl;
import com.service.MessageService;

import java.util.List;

public class MessageServiceImpl implements MessageService {

    MessageDao messageDao = new MessageDaoImpl();
    TutorDao tutorDao = new TutorDaoImpl();
    StudentDao studentDao = new StudentDaoImpl();

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

    @Override
    public List<Message> getMessage(int senderId, int receiverId) {
        return messageDao.get(senderId, receiverId);
    }

    @Override
    public List<Message> getMessageList(int id) {
        List<Message> messages = messageDao.findMessageList(id);
        for (Message message : messages) {
            int receiverId = message.getReceiverId();
            int senderId = message.getSenderId();
            if (receiverId > 0 && senderId < 0) {
                message.setReceiverName(tutorDao.findById(receiverId).getName());
                message.setSenderName(studentDao.findById(-senderId).getStudentName());
            }
            if (receiverId < 0 && senderId > 0) {
                message.setReceiverName(studentDao.findById(-receiverId).getStudentName());
                message.setSenderName(tutorDao.findById(senderId).getName());
            }
        }
        return messages;
    }
}
