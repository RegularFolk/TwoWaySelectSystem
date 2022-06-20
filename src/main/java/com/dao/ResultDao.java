package com.dao;

import java.util.List;

public interface ResultDao {
    List<Integer> findStudentIdByEventId(Integer eventId);
}
