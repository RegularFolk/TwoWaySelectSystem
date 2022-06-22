package com.dao.impl;

import com.bean.Event;
import com.dao.BaseDao;
import com.dao.EventDao;

public class EventDaoImpl extends BaseDao<Event> implements EventDao {
    @Override
    public int addEventWithTutorId(Event event, int tutorId) {
        String sql = "insert into event(submit_time,round1,round2,round3,end_time) values(?,?,?,?,?)";
        return generatedKeyUpdate(sql, event.getSubmitTime(), event.getRound1(), event.getRound2(), event.getRound3(), event.getEndTime());
    }

    @Override
    public int addInfo(String description, String initiator) {
        String sql = "insert into event_info(initiator,description) values(?,?)";
        return generatedKeyUpdate(sql, initiator, description);
    }

    @Override
    public void setInfoId(int eventId, int eventInfoId) {
        String sql = "update event set event_info_id = ? where id = ? ";
        update(sql, eventInfoId, eventId);
    }
}