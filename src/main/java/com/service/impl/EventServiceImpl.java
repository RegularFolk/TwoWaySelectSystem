package com.service.impl;

import com.bean.Event;
import com.bean.Tutor;
import com.constant.Constants;
import com.dao.EventDao;
import com.dao.EventInfoDao;
import com.dao.impl.EventDaoImpl;
import com.dao.impl.EventInfoDaoImpl;
import com.service.EventService;

import java.util.List;

public class EventServiceImpl implements EventService {
    EventDao eventDao = new EventDaoImpl();
    EventInfoDao eventInfoDao = new EventInfoDaoImpl();

    @Override
    public void addEventWithTutorId(Event event, Tutor tutor) {
        int eventId = eventDao.addEventWithTutorId(event, tutor.getId());
        int eventInfoId = eventDao.addInfo(event.getDescription(), tutor.getName());
        eventDao.setInfoId(eventId, eventInfoId);
    }

    @Override
    public List<Event> getFullAllEvents() {
        List<Event> events = eventDao.getAllEvents();
        for (Event event : events) {
            event.setEventInfo(eventInfoDao.getEventInfoByInfoId(event.getEventInfoId()));
        }
        return events;
    }

    @Override
    public Event getOngoingEvent() {
        List<Event> ongoingEvent = eventDao.getOngoingEvent();
        if (ongoingEvent.size() > 1) {
            throw new RuntimeException(Constants.MORE_THAN_ONE_ONGOING_EVENT);
        } else if (ongoingEvent.size() == 0) {
            return null;
        } else {
            return ongoingEvent.get(0);
        }
    }

    @Override
    public void setEventFinished(int id) {
        eventDao.setEventFinished(id);
    }

    @Override
    public void setEventDisabled(int id) {
        eventDao.setEventDisabled(id);
    }

    @Override
    public Event enableEvent(Integer chosenEventId) {
        eventDao.enableEvent(chosenEventId);
        return getOngoingEvent();
    }
}
