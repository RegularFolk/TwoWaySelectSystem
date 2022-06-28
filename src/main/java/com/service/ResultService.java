package com.service;

import com.bean.Result;

public interface ResultService {
    boolean hasFinalResult(int id);

    void updateResult(int eventId);

    Result getResultByEventId(int eventId);

    Result getResultByEventIdStudentId(int id, int id1);
}
