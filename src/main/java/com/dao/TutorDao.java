package com.dao;

import com.bean.Student;
import com.bean.Tutor;

import java.util.List;

public interface TutorDao {
    Tutor findByNumber(String number);

    void add(Tutor tutor);

    Tutor findById(Tutor tutor);

    List<Tutor> findAll();

    Tutor findById(int id);

    List<Tutor> findByMajorId(int majorId);

    void updateInfoId(int infoId, int id);

}
