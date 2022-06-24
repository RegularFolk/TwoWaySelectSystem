package com.dao;

import com.bean.IntBean;

import java.util.List;

public interface IntPreferDao {
    List<IntBean> getIdsByRoundAndTutorId(int round, int tutorId);

    List<IntBean> getTakenIds(int round, int tutorId);
}
