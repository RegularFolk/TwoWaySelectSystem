package com.dao.impl;

import com.bean.Preference;
import com.constant.Constants;
import com.dao.BaseDao;
import com.dao.PreferenceDao;

public class PreferenceDaoImpl extends BaseDao<Preference> implements PreferenceDao {
    @Override
    public Preference findStudentIdsByPreference(Integer preference, Integer tutorId) {
        String sql1 = "select id from preference where no_1 = ?";
        String sql2 = "select id from preference where no_2 = ?";
        String sql3 = "select id from preference where no_3 = ?";
        if (preference.equals(Constants.FIRST_PREFERENCE)) {
            return getBean(Preference.class, sql1, tutorId);
        } else if (preference.equals(Constants.SECOND_PREFERENCE)) {
            return getBean(Preference.class, sql2, tutorId);
        } else if (preference.equals(Constants.THIRD_PREFERENCE)) {
            return getBean(Preference.class, sql3, tutorId);
        } else {
            throw new RuntimeException(Constants.WRONG_PREFERENCE);
        }
    }

    @Override
    public Preference findTutorIdsByPreferenceId(Integer preferenceId) {
        String sql = "select * from preference where id = ?";
        return getBean(Preference.class, sql, preferenceId);
    }

    @Override
    public void update(Preference preference, int id) {
        String sql = "update preference set no_1 = ? , no_2 = ? , no_3 = ? where id = ?";
        update(sql, preference.getNo1(), preference.getNo2(), preference.getNo3(), id);
    }

    @Override
    public int addPreference(Preference preference) {
        String sql = "insert into preference(no_1,no_2,no_3) values (?,?,?)";
        return generatedKeyUpdate(sql, preference.getNo1(), preference.getNo2(), preference.getNo3());
    }
}
