package com.dao;

import com.bean.Event;

import java.util.List;

public interface EventDao {
    int addEventWithTutorId(Event event, int tutorId);

    int addInfo(String description, String initiator);

    void setInfoId(int eventId, int eventInfoId);

    List<Event> getAllEvents();

    List<Event> getAllEventsByStudentId(int id);

    List<Event> getOngoingEvent();

    void setEventFinished(int id);

    void setEventDisabled(int id);

    void enableEvent(Integer chosenEventId);
    Event getEventById(int id);
}
