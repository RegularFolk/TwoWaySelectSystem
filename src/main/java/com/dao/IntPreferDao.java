package com.dao;

import java.util.List;

public interface IntPreferDao {
    List<Integer> getIdsByRoundAndTutorId(int round, int tutorId);
}
