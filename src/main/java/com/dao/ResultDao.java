package com.dao;

import java.util.List;

public interface ResultDao {
    List<Integer> findStudentIdByEventId(Integer eventId);

    void addResult(int eventId, int studentId, int tutorId);

    void addTempResult(int tutorId, int studentId);
}
