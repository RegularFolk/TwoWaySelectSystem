package com.service.impl;

import com.bean.Preference;
import com.bean.Student;
import com.bean.StudentInfo;
import com.bean.TutorInfo;
import com.constant.Constants;
import com.dao.PreferenceDao;
import com.dao.StudentDao;
import com.dao.StudentInfoDao;
import com.dao.TutorInfoDao;
import com.dao.impl.PreferenceDaoImpl;
import com.dao.impl.StudentDaoImpl;
import com.dao.impl.StudentInfoDaoImpl;
import com.dao.impl.TutorInfoDaoImpl;
import com.service.StudentService;
import com.utils.MD5Util;

import java.util.ArrayList;
import java.util.List;

public class StudentServiceImpl implements StudentService {

    StudentDao studentDao = new StudentDaoImpl();

    StudentInfoDao studentInfoDao = new StudentInfoDaoImpl();

    PreferenceDao preferenceDao = new PreferenceDaoImpl();

    TutorInfoDao tutorInfoDao = new TutorInfoDaoImpl();

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
    public Student updatePassword(String password, Student student) {
        //加密明文密码
        String encode = MD5Util.encode(password);
        studentDao.updatePassword(encode, student.getId());
        student.setPassword(password);
        return student;
    }

    @Override
    public Student updateStudentInfo(Student student, StudentInfo studentInfo) {
        //判断student是否已经存在info
        int selfInfoId = student.getSelfInfoId();
        if (selfInfoId != 0) {
            studentInfoDao.updateInfo(selfInfoId, studentInfo);
        } else {
            //添加的同时进行主键回填
            int infoId = studentInfoDao.addInfo(studentInfo);
            studentDao.updateInfoId(infoId, student.getId());
            student.setSelfInfoId(infoId);
        }
        student.setStudentInfo(studentInfo);
        return student;
    }

    @Override
    public List<Student> getStudentList() {
        List<Student> students = studentDao.findAll();
//        for (Student student:students){
//            StudentInfo info = studentInfoDao.findInfoByStudentId(student.getId());
//            student.setStudentInfo(info);
//        }
        return students;
    }

    @Override
    public List<Student> getStudentListByMajorId(int majorId) {
        List<Student> students = studentDao.findByMajorId(majorId);
//        for (Student student:students){
//            StudentInfo info = studentInfoDao.findInfoByStudentId(student.getId());
//            student.setStudentInfo(info);
//        }
        return students;
    }

    @Override
    public List<Student> getStudentListByTutorId(int tutorId) {
        return studentDao.findByTutorId(tutorId);
    }

    @Override
    public List<Student> getStudentListByStatus(int status) {
        return studentDao.findByStatus(status);
    }

    @Override
    public Student getStudentById(int id) {
        Student student = studentDao.findById(id);
        StudentInfo info = studentInfoDao.findInfoByStudentId(id);
        student.setStudentInfo(info);
        return student;
    }

    @Override
    public List<StudentInfo> getInfoList() {
        return studentInfoDao.findAllInfo();
    }

    @Override
    public StudentInfo getInfoByInfoId(int infoId) {
        return studentInfoDao.findInfoById(infoId);
    }

    @Override
    public StudentInfo getInfoByStudentId(int studentId) {
        return studentInfoDao.findInfoByStudentId(studentId);
    }

    @Override//查看学生志愿中的导师信息 需要传入Student, 最后返回List<TutorInfo>
    public List<TutorInfo> getTutorInfoListByStudent(Student student) {
        Integer preferenceId = student.getPreferencesId();
        Preference preference = preferenceDao.findTutorIdsByPreferenceId(preferenceId);
        List<Integer> tutorIdList = preference.getList();
        List<TutorInfo> tutorInfos = new ArrayList<>();
        for (Integer integer : tutorIdList) {
            TutorInfo tutorInfo = tutorInfoDao.findInfoByTutorId(integer);
            tutorInfos.add(tutorInfo);
        }
        return tutorInfos;
    }

    @Override //根据preferenceId和preference修改原本preference
    public void updatePreference(Preference preference, int id) {
        preferenceDao.update(preference, id);
    }

    @Override //判断学生是否已经有志愿
    public boolean hasPreference(int studentId) {
        Student student = studentDao.findById(studentId);
        return student.getPreferencesId() != 0;
    }

    @Override //给定studentId和preference，先在preference中添加，再把主键添加到id对应的student表中字段
    public int addPreference(Preference preference, int id) {
        int preferenceId = preferenceDao.addPreference(preference);
        studentDao.updatePreference(id, preferenceId);
        return preferenceId;
    }

    @Override
    public Preference getPreferenceByPreferenceId(int preferenceId) {
        return preferenceDao.findTutorIdsByPreferenceId(preferenceId);
    }

}
