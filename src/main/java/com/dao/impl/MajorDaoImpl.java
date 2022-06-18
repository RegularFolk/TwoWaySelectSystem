package com.dao.impl;

import com.bean.Major;
import com.dao.BaseDao;
import com.dao.MajorDao;

public class MajorDaoImpl extends BaseDao<Major> implements MajorDao {
    @Override
    public Major findMajor(int id) {
        String sql = "select * from college_major where id = ?";
        return getBean(Major.class, sql, id);
    }
}
