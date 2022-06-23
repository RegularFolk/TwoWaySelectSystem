package com.dao;

import com.bean.Student;
import com.bean.StudentInfo;

import java.util.List;

public interface StudentDao {
    Student findByNumber(String studentNumber);

    void add(Student student);

    void updatePassword(String password,int id);

    void updateStatus(int status,int id);

    Student findById(int id);

    List<Student> findByStatus(int status);

    List<Student> findByMajorId(int majorId);

    List<Student> findByTutorId(int tutorId);

    List<Student> findAll();

    Student findByPreferenceId(Integer preferenceId);

    void updateInfoId(int infoId, int id);

    void updatePreference(int id, int preferenceId);

    void initializeAllStatus();

    void updateTutorId(int studentId, int tutorId);
}
