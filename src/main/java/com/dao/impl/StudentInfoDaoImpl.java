package com.dao.impl;

import com.bean.Student;
import com.bean.StudentInfo;
import com.dao.BaseDao;
import com.dao.StudentInfoDao;

import java.util.List;

public class StudentInfoDaoImpl extends BaseDao<StudentInfo> implements StudentInfoDao {
    @Override
    public StudentInfo findInfo(Student student) {
        String sql = "select * from student_info where id = ?";
        return getBean(StudentInfo.class, sql, student.getSelfInfoId());
    }

    @Override
    public StudentInfo findInfoById(int infoId) {
        String sql = "select * from student_info where id = ?";
        return getBean(StudentInfo.class, sql, infoId);
    }

    @Override
    public StudentInfo findInfoByStudentId(int studentId) {
        String sql = "select student_info.*,student.id from student,student_info where student.self_info_id=student_info.id and student.id = ?";
        return getBean(StudentInfo.class, sql, studentId);
    }

    @Override
    public List<StudentInfo> findAllInfo() {
        String sql = "select * from student_info";
        return getBeanList(StudentInfo.class, sql);
    }

    @Override
    public int addInfo(StudentInfo studentInfo) {
        String sql = "insert into student_info (gender, birthday, politics_status, phone_number, email, planning, honor) values(?,?,?,?,?,?,?)";
        return generatedKeyUpdate(sql, studentInfo.getGender(), studentInfo.getBirthday(), studentInfo.getPoliticsStatus(), studentInfo.getPhoneNumber(), studentInfo.getEmail(), studentInfo.getPlanning(), studentInfo.getHonor());
    }

    @Override
    public void updateInfo(int selfInfoId, StudentInfo studentInfo) {
        String sql = "update student_info set gender = ?, birthday = ?, politics_status = ?, phone_number = ?, email = ?, planning = ?, honor= ? where id = ?";
        update(sql, studentInfo.getGender(), studentInfo.getBirthday(), studentInfo.getPoliticsStatus(), studentInfo.getPhoneNumber(), studentInfo.getEmail(), studentInfo.getPlanning(), studentInfo.getHonor(), selfInfoId);
    }


}
