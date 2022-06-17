package com.service.impl;

import com.bean.Student;
import com.constant.Constants;
import com.dao.StudentDao;
import com.dao.impl.StudentDaoImpl;
import com.service.StudentService;
import com.utils.MD5Util;

public class StudentServiceImpl implements StudentService {
    StudentDao studentDao = new StudentDaoImpl();

    @Override
    public Student doLogin(Student student) {
        Student byStudentNumber = studentDao.findByNumber(student.getStudentNumber());
        if (byStudentNumber != null) {
            //用户名正确，效验密码
            String encode = MD5Util.encode(student.getPassword());
            if (encode.equals(byStudentNumber.getPassword())) {
                return byStudentNumber;
            } else {
                throw new RuntimeException(Constants.WRONG_PASSWORD);
            }
        }
        throw new RuntimeException(Constants.WRONG_NUMBER);
    }

    @Override
    public void doRegister(Student student) {
        //判断学号是否已经存在
        Student byStudentName = studentDao.findByNumber(student.getStudentNumber());
        if (byStudentName != null) {
            throw new RuntimeException(Constants.STUDENT_NUMBER_EXISTS);
        }
        //加密明文密码
        String encode = MD5Util.encode(student.getPassword());
        student.setPassword(encode);
        studentDao.add(student);
    }
}
