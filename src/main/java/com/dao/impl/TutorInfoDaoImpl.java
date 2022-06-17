package com.dao.impl;

import com.bean.Tutor;
import com.bean.TutorInfo;
import com.dao.BaseDao;
import com.dao.TutorInfoDao;

public class TutorInfoDaoImpl extends BaseDao<TutorInfo> implements TutorInfoDao {
    @Override
    public TutorInfo findInfo(Tutor tutor) {
        String sql = "select * from tutor_info where id = ?";
        return getBean(TutorInfo.class, sql, tutor.getTutorInfoId());
    }
}
