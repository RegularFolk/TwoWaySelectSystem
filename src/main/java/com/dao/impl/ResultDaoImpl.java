package com.dao.impl;

import com.dao.BaseDao;
import com.dao.ResultDao;

import java.util.List;

public class ResultDaoImpl extends BaseDao<Integer> implements ResultDao {
    @Override
    public List<Integer> findStudentIdByEventId(Integer eventId) {
        String sql = "select student_id from result where event_id = ?";
        return getBeanList(Integer.class,sql,eventId);
    }
}
