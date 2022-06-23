package com.dao.impl;

import com.bean.EventInfo;
import com.dao.BaseDao;
import com.dao.EventInfoDao;

public class EventInfoDaoImpl extends BaseDao<EventInfo> implements EventInfoDao {
    @Override
    public EventInfo getEventInfoByInfoId(int eventInfoId) {
        String sql = "select * from event_info where id = ?";
        return getBean(EventInfo.class, sql, eventInfoId);
    }
}
