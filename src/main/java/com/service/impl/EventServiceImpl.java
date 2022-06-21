package com.service.impl;

import com.bean.Event;
import com.bean.Tutor;
import com.dao.EventDao;
import com.dao.impl.EventDaoImpl;
import com.service.EventService;

public class EventServiceImpl implements EventService {
    EventDao eventDao = new EventDaoImpl();


    @Override
    public void addEventWithTutorId(Event event, Tutor tutor) {
        int eventId = eventDao.addEventWithTutorId(event, tutor.getId());
        int eventInfoId = eventDao.addInfo(event.getDescription(), tutor.getName());
        eventDao.setInfoId(eventId, eventInfoId);
    }
}
