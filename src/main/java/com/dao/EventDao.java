package com.dao;

import com.bean.Event;

import java.util.List;

public interface EventDao {
    public int addEventWithTutorId(Event event, int tutorId);

    int addInfo(String description, String initiator);

    void setInfoId(int eventId, int eventInfoId);

    List<Event> getAllEvents();

    List<Event> getOngoingEvent();

    void setEventDisable(int id);
}
