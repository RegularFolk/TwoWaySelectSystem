package com.dao;

import com.bean.Student;
import com.bean.StudentInfo;

public interface StudentInfoDao {
    StudentInfo findInfo(Student student);
}
