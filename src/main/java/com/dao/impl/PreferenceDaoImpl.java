package com.dao.impl;

import com.constant.Constants;
import com.dao.BaseDao;
import com.dao.PreferenceDao;

import java.util.List;

public class PreferenceDaoImpl extends BaseDao<Integer> implements PreferenceDao {
    @Override
    public List<Integer> findStudentIdsByPreference(Integer preference, Integer tutorId) {
        String sql1 = "select id from preference where no_1 = ?";
        String sql2 = "select id from preference where no_2 = ?";
        String sql3 = "select id from preference where no_3 = ?";
        if (preference.equals(Constants.FIRST_PREFERENCE)) {
            return getBeanList(Integer.class, sql1, tutorId);
        } else if (preference.equals(Constants.SECOND_PREFERENCE)) {
            return getBeanList(Integer.class, sql2, tutorId);
        } else if (preference.equals(Constants.THIRD_PREFERENCE)) {
            return getBeanList(Integer.class, sql3, tutorId);
        } else {
            throw new RuntimeException(Constants.WRONG_PREFERENCE);
        }
    }

    @Override
    public List<Integer> findTutorIdsByPreferenceId(Integer preferenceId) {
        String sql = "select no_1,no_2,no_3 from preference where id = ?";
        return getBeanList(Integer.class, sql, preferenceId);
    }
}
