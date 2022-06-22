package com.service;

import com.bean.Preference;
import com.bean.Student;
import com.bean.StudentInfo;
import com.bean.TutorInfo;

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

    List<TutorInfo> getTutorInfoListByStudent(Student studentId);

    void updatePreference(Preference preference, int id);

    boolean hasPreference(int studentId);

    int addPreference(Preference preference, int id);

    Preference getPreferenceByPreferenceId(int preferenceId);

    void setStatusToChose(int id);
}
