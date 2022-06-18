package com.dao;

import com.bean.Tutor;
import com.bean.TutorInfo;

public interface TutorInfoDao {
    TutorInfo findInfo(Tutor tutor);
}
