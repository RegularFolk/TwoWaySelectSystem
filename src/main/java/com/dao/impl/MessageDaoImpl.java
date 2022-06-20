package com.dao.impl;

import com.bean.Message;
import com.dao.BaseDao;
import com.dao.MessageDao;

import java.util.List;

public class MessageDaoImpl extends BaseDao<Message> implements MessageDao {
    @Override
    public List<Message> findByReceiverId(int id) {
        String sql = "select * from message where receiver_id = ?";
        return getBeanList(Message.class, sql, id);
    }

    @Override
    public List<Message> findBySenderId(int id) {
        String sql = "select * from message where sender_id = ?";
        return getBeanList(Message.class, sql, id);
    }

    @Override
    public void sandById(int senderId, int receiverId, String text, String time) {
        String sql = "insert into message(sender_id,receiver_id,text,time) values(?,?,?,?)";
        update(sql, senderId, receiverId, text, time);
    }
}
