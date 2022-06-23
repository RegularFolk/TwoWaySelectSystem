package com.service;

import com.bean.Student;
import com.bean.StudentInfo;
import com.bean.Tutor;
import com.bean.TutorInfo;

import java.util.List;

public interface TutorService {

    Tutor doLogin(Tutor tutor);

    void doRegister(Tutor tutor);

    Tutor updateTutorInfo(Tutor tutor, TutorInfo tutorInfoInfo);

    List<Tutor> getTutorList();

    Tutor getTutorById(int tutorId);

    List<Tutor> getTutorByMajorId(int majorId);

    List<TutorInfo> getInfoList();

    TutorInfo getInfoByInfoId(int infoId);

    TutorInfo getInfoByTutorId(int tutorId);

    Tutor updatePassword(String password, Tutor tutor);

    List<Student> checkStudent(Integer preference, Integer tutorId);

}
