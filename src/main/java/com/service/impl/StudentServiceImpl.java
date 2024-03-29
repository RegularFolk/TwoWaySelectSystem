package com.service.impl;

import com.bean.*;
import com.constant.Constants;
import com.dao.*;
import com.dao.impl.*;
import com.service.StudentService;
import com.utils.MD5Util;

import java.util.ArrayList;
import java.util.List;

public class StudentServiceImpl implements StudentService {

    StudentDao studentDao = new StudentDaoImpl();

    StudentInfoDao studentInfoDao = new StudentInfoDaoImpl();

    PreferenceDao preferenceDao = new PreferenceDaoImpl();

    TutorInfoDao tutorInfoDao = new TutorInfoDaoImpl();

    IntPreferDao intPreferDao = new IntPreferDaoImpl();

    MajorDao majorDao=new MajorDaoImpl();

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
        for (Student student:students){
            StudentInfo info = studentInfoDao.findInfoByStudentId(student.getId());
            Major major=majorDao.findMajor(student.getMajorId());
            student.setStudentInfo(info);
            student.setMajor(major);
        }
        return students;
    }

    @Override
    public List<Student> getStudentListByMajorId(int majorId) {
        List<Student> students = studentDao.findByMajorId(majorId);
        for (Student student:students){

            StudentInfo info = studentInfoDao.findInfoByStudentId(student.getId());

            student.setStudentInfo(info);
        }
        return students;
    }

    @Override
    public List<Student> getStudentListByTutorId(int tutorId) {
        return studentDao.findByTutorId(tutorId);
    }

    @Override
    public List<Student> getNotChosen() {
        return studentDao.getNotChosen();
    }

    @Override
    public Student getStudentById(int id) {
        Student byId = studentDao.findById(id);
        StudentInfo info=new StudentInfo();
        Major major=new Major();
        if (byId.getMajorId()!=0){
            major=majorDao.findMajor(byId.getMajorId());
        }
        if (byId.getSelfInfoId()!=0){
            info=studentInfoDao.findInfoByStudentId(id);
        }
        byId.setMajor(major);
        byId.setStudentInfo(info);
        return byId;
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

    @Override
    public void setStatusToChose(int id) {
        studentDao.updateStatus(Constants.STUDENT_STATUS_CHOSE, id);
    }

    @Override
    public void initialize() {
        //初始化学生的status，preference_id,tutor_id
        studentDao.initialize();
        preferenceDao.initialize();
    }

    @Override
    public List<Student> getAvailableStudents(int round, int tutorId) {
        List<IntBean> preIds = intPreferDao.getIdsByRoundAndTutorId(round, tutorId);
        List<Student> candidates = new ArrayList<>();
        for (IntBean preId : preIds) {
            Student student = studentDao.findByPreferenceId(preId.getId());
            if (student.getTutorId() == tutorId || student.getTutorId() == 0) {
                candidates.add(student);
            }
        }
        return candidates;
    }


}
