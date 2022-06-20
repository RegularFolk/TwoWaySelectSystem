package com.dao;

import com.bean.Message;

import java.util.List;

public interface MessageDao {
    List<Message> findByReceiverId(int id);
    List<Message> findBySenderId(int id);
    void sandById(int senderId, int receiverId, String text, String time);


}
