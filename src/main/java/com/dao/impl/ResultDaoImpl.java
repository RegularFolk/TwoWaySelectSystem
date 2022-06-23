package com.dao.impl;

import com.dao.BaseDao;
import com.dao.ResultDao;

import java.util.List;

public class ResultDaoImpl extends BaseDao<Integer> implements ResultDao {
    @Override
    public List<Integer> findStudentIdByEventId(Integer eventId) {
        String sql = "select student_id from result where event_id = ?";
        return getBeanList(Integer.class, sql, eventId);
    }

    @Override
    public void addResult(int eventId, int studentId, int tutorId) {
        String sql = "insert into result(event_id,student_id,tutor_id) values(?,?,?)";
        update(sql, eventId, studentId, tutorId);
    }

    @Override
    public void addTempResult(int tutorId, int studentId) {
        String sql = "insert into tutor_student(tutor_id,student_id) values(?,?)";
        update(sql, tutorId, studentId);
    }
}
