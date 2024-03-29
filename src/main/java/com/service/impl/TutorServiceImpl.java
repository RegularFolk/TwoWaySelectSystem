package com.service.impl;

import com.bean.*;
import com.constant.Constants;
import com.dao.*;
import com.dao.impl.*;
import com.service.TutorService;
import com.utils.MD5Util;

import java.util.ArrayList;
import java.util.List;

public class TutorServiceImpl implements TutorService {

    TutorDao tutorDao = new TutorDaoImpl();

    TutorInfoDao tutorInfoDao = new TutorInfoDaoImpl();

    PreferenceDao preferenceDao = new PreferenceDaoImpl();

    StudentDao studentDao = new StudentDaoImpl();

    MajorDao majorDao = new MajorDaoImpl();

    ResultDao resultDao = new ResultDaoImpl();

    IntPreferDao intPreferDao = new IntPreferDaoImpl();

    @Override
    public Tutor doLogin(Tutor tutor) {
        Tutor findByNumber = tutorDao.findByNumber(tutor.getNumber());
        if (findByNumber != null) {
            String encode = MD5Util.encode(tutor.getPassword());
            if (findByNumber.getPassword().equals(encode)) {
                return findByNumber;
            } else {
                throw new RuntimeException(Constants.WRONG_PASSWORD);
            }
        }
        throw new RuntimeException(Constants.WRONG_NUMBER);
    }

    @Override
    public void doRegister(Tutor tutor) {
        //判断账号是否存在
        Tutor byTutorNumber = tutorDao.findByNumber(tutor.getNumber());
        if (byTutorNumber != null) {
            throw new RuntimeException(Constants.STUDENT_NUMBER_EXISTS);
        }
        //加密明文密码
        String encode = MD5Util.encode(tutor.getPassword());
        tutor.setPassword(encode);
        tutorDao.add(tutor);
    }


    @Override
    public Tutor updateTutorInfo(Tutor tutor, TutorInfo tutorInfoInfo) {
        int tutorInfoId = tutor.getTutorInfoId();
        if (tutorInfoId != 0) {
            tutorInfoDao.updateInfo(tutorInfoId, tutorInfoInfo);
        } else {
            int infoId = tutorInfoDao.addInfo(tutorInfoInfo);
            tutorDao.updateInfoId(infoId, tutor.getId());
            tutor.setTutorInfoId(infoId);
        }
        tutor.setTutorInfo(tutorInfoInfo);
        return tutor;
    }

    @Override
    public List<Tutor> getTutorList() {
        return tutorDao.findAll();
    }

    @Override
    public Tutor getTutorById(int id) {
        Tutor byId = tutorDao.findById(id);
        TutorInfo info = new TutorInfo();
        Major major = new Major();
        if (byId.getMajorId()!=0){
            major=majorDao.findMajor(byId.getMajorId());
        }
        if (byId.getTutorInfoId()!=0){
            info=tutorInfoDao.findInfoByTutorId(id);
        }
        byId.setMajor(major);
        byId.setTutorInfo(info);
        return byId;
    }

    @Override
    public List<Tutor> getTutorByMajorId(int majorId) {
        return tutorDao.findByMajorId(majorId);
    }

    @Override
    public List<TutorInfo> getInfoList() {
        return tutorInfoDao.findAllInfo();
    }

    @Override
    public TutorInfo getInfoByInfoId(int infoId) {
        return tutorInfoDao.findInfoByInfoId(infoId);
    }

    @Override
    public TutorInfo getInfoByTutorId(int tutorId) {
        return tutorInfoDao.findInfoByTutorId(tutorId);
    }

    @Override//导师查看选自己的学生，传入int类型第几志愿 int类型导师id, 最后返回List<Student>
    public List<Student> checkStudent(Integer preference, Integer tutorId) {
        List<Student> studentList = new ArrayList<>();
        Preference preferences = preferenceDao.findStudentIdsByPreference(preference, tutorId);
        List<Integer> preferenceIdList = preferences.getList();
        for (Integer integer : preferenceIdList) {
            Student byPreferenceId = studentDao.findByPreferenceId(integer);
            studentList.add(byPreferenceId);
        }
        return studentList;
    }

    @Override
    public void initialize() {
        tutorDao.initializeTempResult();
        tutorDao.initializeAllStatus();
    }

    @Override
    public void randomAllocation(List<Student> students) {
        List<Tutor> tutors = tutorDao.findByLeft();
        int cursor = 0;
        for (Tutor tutor : tutors) {
            for (int i = 0; i < tutor.getLeft() && cursor < students.size(); i++, cursor++) {
                int studentId = students.get(cursor).getId(), tutorId = tutor.getId();
                studentDao.updateStatus(Constants.STUDENT_STATUS_CHOSEN, studentId);
                studentDao.updateTutorId(studentId, tutorId);
                resultDao.addTempResult(tutorId, studentId);
            }
        }
    }

    @Override
    public void takeStudents(Tutor tutor, List<Integer> chosenIds, int round) {
        List<Integer> takenStudentIds = getTakenStudentIds(tutor, round); //获得之前提交的学生
        if (tutor.getLeft() + takenStudentIds.size() - chosenIds.size() < 0) {
            throw new RuntimeException(Constants.TOO_MANY_CHOSEN + "\n" + "最大提交数为:" + (tutor.getLeft() + takenStudentIds.size()));
        }
        tutorDao.removeTaken(tutor.getId(), round);//针对tutor_student临时表
        for (Integer takenStudentId : takenStudentIds) {
            studentDao.initializeSingle(takenStudentId);
        }
        for (Integer chosenId : chosenIds) {
            //存入缓存结果表
            tutorDao.takeStudent(tutor.getId(), chosenId, round);
            studentDao.updateStatus(Constants.STUDENT_STATUS_CHOSEN, chosenId);
            //在学生表中更新导师信息
            studentDao.updateTutorId(chosenId, tutor.getId());
        }//更新导师剩余名额信息
        tutorDao.updateLeft(tutor.getId(), tutor.getLeft() + takenStudentIds.size() - chosenIds.size());
    }

    @Override
    public List<Integer> getTakenStudentIds(Tutor tutor, int round) {//针对preference表
        List<IntBean> stuIds = intPreferDao.getTakenIds(round, tutor.getId());
        List<Integer> students = new ArrayList<>();
        for (IntBean stuId : stuIds) {
            students.add(stuId.getId());
        }
        return students;
    }

    @Override
    public Tutor updateTutor(Tutor updateTutor, Tutor tutor) {
        tutorDao.updateTutor(updateTutor);
        if (!tutor.getName().equals(updateTutor.getName())) {
            tutor.setName(updateTutor.getName());
        }
        if (!tutor.getAuthority().equals(updateTutor.getAuthority())) {
            tutor.setAuthority(updateTutor.getAuthority());
        }
        if (tutor.getMajorId() != updateTutor.getMajorId()) {
            tutor.setMajorId(updateTutor.getMajorId());
        }


        return tutor;

    }


    @Override
    public Tutor updatePassword(String password, Tutor tutor) {
        String encode = MD5Util.encode(password);
        tutorDao.updatePassword(encode, tutor.getId());
        tutor.setPassword(encode);
        return tutor;
    }
}
