package com.service.impl;

import com.bean.Student;
import com.bean.Tutor;
import com.bean.TutorInfo;
import com.constant.Constants;
import com.dao.PreferenceDao;
import com.dao.StudentDao;
import com.dao.TutorDao;
import com.dao.TutorInfoDao;
import com.dao.impl.PreferenceDaoImpl;
import com.dao.impl.StudentDaoImpl;
import com.dao.impl.TutorDaoImpl;
import com.dao.impl.TutorInfoDaoImpl;
import com.service.TutorService;
import com.utils.MD5Util;

import java.util.ArrayList;
import java.util.List;

public class TutorServiceImpl implements TutorService {

    TutorDao tutorDao = new TutorDaoImpl();

    TutorInfoDao tutorInfoDao = new TutorInfoDaoImpl();

    PreferenceDao preferenceDao = new PreferenceDaoImpl();

    StudentDao studentDao = new StudentDaoImpl();

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
        return tutorDao.findById(id);
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
        List<Integer> preferenceIdList = preferenceDao.findStudentIdsByPreference(preference, tutorId);
        for (Integer integer : preferenceIdList) {
            Student byPreferenceId = studentDao.findByPreferenceId(integer);
            studentList.add(byPreferenceId);
        }
        return studentList;
    }
}
