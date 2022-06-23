package com.dao.impl;

import com.bean.Preference;
import com.constant.Constants;
import com.dao.BaseDao;
import com.dao.IntPreferDao;

import java.util.List;

public class IntPreferDaoImpl extends BaseDao<Integer> implements IntPreferDao {
    @Override
    public List<Integer> getIdsByRoundAndTutorId(int round, int tutorId) {
        String sql1 = "select id from preference where no_1 = ?";
        String sql2 = "select id from preference where no_2 = ?";
        String sql3 = "select id from preference where no_3 = ?";
        if (round == Constants.FIRST_PREFERENCE) {
            return getBeanList(Integer.class, sql1, tutorId);
        } else if (round == Constants.SECOND_PREFERENCE) {
            return getBeanList(Integer.class, sql2, tutorId);
        } else if (round == Constants.THIRD_PREFERENCE) {
            return getBeanList(Integer.class, sql3, tutorId);
        } else {
            throw new RuntimeException(Constants.WRONG_PREFERENCE);
        }
    }
}
