package com.service;

import com.bean.Student;

public interface StudentService {
    Student doLogin(Student student);

    void doRegister(Student student);
}
