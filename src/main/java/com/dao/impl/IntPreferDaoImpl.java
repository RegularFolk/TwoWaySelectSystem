package com.dao.impl;

import com.bean.IntBean;
import com.constant.Constants;
import com.dao.BaseDao;
import com.dao.IntPreferDao;

import java.util.List;

public class IntPreferDaoImpl extends BaseDao<IntBean> implements IntPreferDao {
    @Override
    public List<IntBean> getIdsByRoundAndTutorId(int round, int tutorId) {
        String sql1 = "select id from preference where no_1 = ?";
        String sql2 = "select id from preference where no_2 = ?";
        String sql3 = "select id from preference where no_3 = ?";
        if (round == Constants.FIRST_PREFERENCE) {
            return getBeanList(IntBean.class, sql1, tutorId);
        } else if (round == Constants.SECOND_PREFERENCE) {
            return getBeanList(IntBean.class, sql2, tutorId);
        } else if (round == Constants.THIRD_PREFERENCE) {
            return getBeanList(IntBean.class, sql3, tutorId);
        } else {
            throw new RuntimeException(Constants.WRONG_PREFERENCE);
        }
    }

    @Override
    public List<IntBean> getTakenIds(int round, int tutorId) {
        String sql = "select student_id from tutor_student where tutor_id = ? and round = ?";
        return getBeanList(IntBean.class, sql, tutorId, round);
    }
}
