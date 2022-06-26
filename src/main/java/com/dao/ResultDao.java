package com.dao;

import com.bean.IntBean;
import com.bean.Tutor;

import java.util.List;

public interface ResultDao {
    List<IntBean> findStudentIdByEventId(int eventId);

    void addResult(int eventId, int studentId, int tutorId);

    void addTempResult(int tutorId, int studentId);

    List<IntBean> findTutorByEventId(int eventId);

    List<IntBean> findStudentByEventIdTutor(int eventId,int tutorId);


}
