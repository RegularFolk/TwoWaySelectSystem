package com.service;

import com.bean.Student;
import com.bean.StudentInfo;

public interface StudentService {
    Student doLogin(Student student);

    void doRegister(Student student);

    Student updateStudentInfo(Student student, StudentInfo studentInfo);
}
