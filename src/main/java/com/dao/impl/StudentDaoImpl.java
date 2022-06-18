package com.dao.impl;

import com.bean.Student;
import com.dao.BaseDao;
import com.dao.StudentDao;

import java.util.List;

public class StudentDaoImpl extends BaseDao<Student> implements StudentDao {
    @Override
    public Student findByNumber(String studentNumber) {
        String sql1 = "select * from student where student_number = ?";
        return getBean(Student.class, sql1, studentNumber);
    }

    @Override
    public void add(Student student) {
        String sql = "insert into student (student_number,password,student_name) values(?,?,?)";
        update(sql, student.getStudentNumber(), student.getPassword(), student.getStudentName());
    }

    @Override
    public Student findById(int id) {
        String sql = "select * from student where id = ?";
        return getBean(Student.class, sql, id);
    }

    @Override
    public List<Student> findAll() {
        String sql = "select *from student";
        return getBeanList(Student.class, sql);
    }
}
