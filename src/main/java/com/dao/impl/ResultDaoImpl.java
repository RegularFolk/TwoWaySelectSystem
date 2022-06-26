package com.dao.impl;

import com.bean.IntBean;
import com.bean.Result;
import com.bean.Tutor;
import com.dao.BaseDao;
import com.dao.ResultDao;

import java.util.List;

public class ResultDaoImpl extends BaseDao<IntBean> implements ResultDao {
    @Override
    public List<IntBean> findStudentIdByEventId(int eventId) {
        String sql = "select student_id as id from result where event_id = ?";
        return getBeanList(IntBean.class, sql, eventId);
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

    @Override
    public List<IntBean> findTutorByEventId(int eventId) {
        String sql = "select DISTINCT tutor_id as id from result where event_id = ?";
        return getBeanList(IntBean.class, sql, eventId);
    }

    @Override
    public List<IntBean> findStudentByEventIdTutor(int eventId, int tutorId) {
        String sql = "select DISTINCT student_id as id from result where event_id = ? and tutor_id=?";
        return getBeanList(IntBean.class, sql, eventId, tutorId);

    }

}
