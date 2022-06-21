package com.service;

import com.bean.Student;
import com.bean.StudentInfo;

import java.util.List;

public interface StudentService {
    Student doLogin(Student student);

    void doRegister(Student student);

    Student updatePassword(String password,Student student);

    Student updateStudentInfo(Student student, StudentInfo studentInfo);

    List<Student> getStudentList();

    List<Student> getStudentListByMajorId(int majorId);

    List<Student> getStudentListByTutorId(int tutorId);

    List<Student> getStudentListByStatus(int status);

    Student getStudentById(int studentId);

    List<StudentInfo> getInfoList();

    StudentInfo getInfoByInfoId(int infoId);

    StudentInfo getInfoByStudentId(int studentId);

}
