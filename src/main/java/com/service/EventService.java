package com.service;

import com.bean.Event;
import com.bean.Tutor;

import java.util.List;

public interface EventService {

    public void addEventWithTutorId(Event event, Tutor tutor);

    List<Event> getFullAllEvents();

    List<Event> getFullAllEventsByStudentId(int id);

    Event getOngoingEvent();

    void setEventFinished(int id);

    void setEventDisabled(int id);

    Event enableEvent(Integer chosenEventId);

    Event getEventById(int id);

    
}
