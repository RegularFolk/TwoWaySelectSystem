package com.dao;

import com.bean.StudentInfo;
import com.bean.Tutor;
import com.bean.TutorInfo;

import java.util.List;

public interface TutorInfoDao {
    TutorInfo findInfo(Tutor tutor);

    TutorInfo findInfoByInfoId(int infoId);

    TutorInfo findInfoByTutorId(int tutorId);

    List<TutorInfo> findAllInfo();

    int addInfo(TutorInfo tutorInfo);

    void updateInfo(int tutorInfoId, TutorInfo tutorInfo);
}
