package com.dao;

import com.bean.Event;

public interface EventDao {
    public int addEventWithTutorId(Event event, int tutorId);

    int addInfo(String description, String initiator);

    void setInfoId(int eventId, int eventInfoId);
}
