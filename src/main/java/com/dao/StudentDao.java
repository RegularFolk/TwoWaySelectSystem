package com.dao;

import com.bean.Student;

import java.util.List;

public interface StudentDao {
    Student findByNumber(String studentNumber);

    void add(Student student);

    Student findById(int id);

    List<Student> findAll();
}
