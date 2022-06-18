package com.service.impl;

import com.bean.Student;
import com.bean.StudentInfo;
import com.constant.Constants;
import com.dao.StudentDao;
import com.dao.StudentInfoDao;
import com.dao.impl.StudentDaoImpl;
import com.dao.impl.StudentInfoDaoImpl;
import com.service.StudentService;
import com.utils.MD5Util;

public class StudentServiceImpl implements StudentService {
    StudentDao studentDao = new StudentDaoImpl();

    StudentInfoDao studentInfoDao = new StudentInfoDaoImpl();

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

    @Override
    public Student updateStudentInfo(Student student, StudentInfo studentInfo) {
        //判断student是否已经存在info
        int selfInfoId = student.getSelfInfoId();
        if (selfInfoId != 0) {
            studentDao.updateInfo(selfInfoId, studentInfo);
        } else {
            //添加的同时进行主键回填
            int infoId = studentDao.addInfo(studentInfo);
            studentDao.updateInfoId(infoId, student.getId());
            student.setSelfInfoId(infoId);
        }
        student.setStudentInfo(studentInfo);
        return student;
    }


}
