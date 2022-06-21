package com.service;

import com.bean.Event;
import com.bean.Tutor;

public interface EventService {

    public void addEventWithTutorId(Event event, Tutor tutor);

}
