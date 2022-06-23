package com.dao;

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

    void updatePassword(String password, int id);

    void initializeTempResult();

    void initializeAllStatus();

    List<Tutor> findByLeft();

    void takeStudent(int tutorId, Integer chosenId, int round);

    void updateLeft(int tutorId, int newLeft);

    void removeTaken(int tutorId, int round);
}
