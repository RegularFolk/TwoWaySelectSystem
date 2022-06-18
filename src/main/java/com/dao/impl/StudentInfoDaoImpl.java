package com.dao.impl;

import com.bean.Student;
import com.bean.StudentInfo;
import com.dao.BaseDao;
import com.dao.StudentInfoDao;

public class StudentInfoDaoImpl extends BaseDao<StudentInfo> implements StudentInfoDao {
    @Override
    public StudentInfo findInfo(Student student) {
        String sql = "select * from student_info where id = ?";
        return getBean(StudentInfo.class, sql, student.getSelfInfoId());
    }
}
