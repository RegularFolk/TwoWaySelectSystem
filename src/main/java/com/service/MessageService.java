package com.service;

import com.bean.Message;

import java.util.List;

public interface MessageService {
    void sendMessageById(int senderId, int receiverId, String text, String time);

    List<Message> findByReceiverId(int id);

    List<Message> findBySenderId(int id);

}
