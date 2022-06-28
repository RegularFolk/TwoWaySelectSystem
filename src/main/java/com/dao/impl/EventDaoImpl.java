package com.dao.impl;

import com.bean.Event;
import com.constant.Constants;
import com.dao.BaseDao;
import com.dao.EventDao;

import java.util.List;

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

    @Override
    public List<Event> getAllEvents() {
        String sql = "select * from event";
        return getBeanList(Event.class, sql);
    }

    @Override
    public List<Event> getOngoingEvent() {
        String sql = "select * from event where status = ?";
        return getBeanList(Event.class, sql, Constants.EVENT_ENABLED);
    }

    @Override
    public void setEventFinished(int id) {
        String sql = "update event set status = ? where id = ?";
        update(sql, Constants.EVENT_FINISHED, id);
    }

    @Override
    public void setEventDisabled(int id) {
        String sql = "update event set status = ? where id = ?";
        update(sql, Constants.EVENT_DISABLED, id);
    }

    @Override
    public void enableEvent(Integer chosenEventId) {
        String sql = "update event set status = ? where id = ?";
        update(sql, Constants.EVENT_ENABLED, chosenEventId);
    }

    @Override
    public Event getEventById(int id) {
        String sql = "select  * from event where id=?";
        return getBean(Event.class, sql, id);
    }
}
