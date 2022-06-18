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

    //zyq
    @Override
    public void update(StudentInfo studentInfo) {
        String sql="update student_info set  gender = ?,birthday = ?,politic_status= ? ,phone_number = ?,email = ?,   where id = ?";
    }
}
