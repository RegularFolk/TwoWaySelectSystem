package com.dao;

import com.bean.Student;
import com.bean.StudentInfo;

import java.util.List;

public interface StudentInfoDao {
    StudentInfo findInfo(Student student);

    StudentInfo findInfoById(int infoId);

    StudentInfo findInfoByStudentId(int studentId);

    List<StudentInfo> findAllInfo();

    int addInfo(StudentInfo studentInfo);

    void updateInfo(int selfInfoId, StudentInfo studentInfo);


}
