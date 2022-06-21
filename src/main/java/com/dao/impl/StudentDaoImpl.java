package com.dao.impl;

import com.bean.Student;
import com.bean.StudentInfo;
import com.dao.BaseDao;
import com.dao.StudentDao;

import java.math.BigInteger;
import java.util.List;

public class StudentDaoImpl extends BaseDao<Student> implements StudentDao {
    @Override
    public Student findByNumber(String studentNumber) {
        String sql = "select * from student where student_number = ?";
        return getBean(Student.class, sql, studentNumber);
    }

    @Override
    public void add(Student student) {
        String sql = "insert into student (student_number,password,student_name) values(?,?,?)";
        update(sql, student.getStudentNumber(), student.getPassword(), student.getStudentName());
    }

    @Override
    public void updatePassword(String password, int id) {
        String sql = "update student set password = ? where id=?";
        update(sql, password, id);
    }

    @Override
    public void updateStatus(int status, int id) {
        String sql = "update student set status = ? where id=?";
        update(sql, status, id);
    }

    @Override
    public Student findById(int id) {
        String sql = "select * from student where id = ?";
        return getBean(Student.class, sql, id);
    }

    @Override
    public List<Student> findByStatus(int status) {
        String sql = "select * from student where status = ?";
        return getBeanList(Student.class, sql, status);
    }

    @Override
    public List<Student> findByMajorId(int majorId) {
        String sql = "select * from student where major_id = ?";
        return getBeanList(Student.class, sql, majorId);
    }

    @Override
    public List<Student> findByTutorId(int tutorId) {
        String sql = "select * from student where tutor_id = ?";
        return getBeanList(Student.class, sql, tutorId);
    }

    @Override
    public List<Student> findAll() {
        String sql = "select * from student";
        return getBeanList(Student.class, sql);
    }

    @Override
    public Student findByPreferenceId(Integer preferenceId) {
        String sql = "select * from student where preferences_id = ?";
        return getBean(Student.class, sql, preferenceId);
    }

    @Override
    public void updateInfoId(int infoId, int id) {
        String sql = "update student set self_info_id = ? where id = ?";
        update(sql, infoId, id);
    }

    @Override
    public void updatePreference(int id, int preferenceId) {
        String sql = "update student set preferences_id  = ? where id = ?";
        update(sql, preferenceId, id);
    }

}
