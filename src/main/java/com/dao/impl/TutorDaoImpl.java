package com.dao.impl;

import com.bean.Tutor;
import com.constant.Constants;
import com.dao.BaseDao;
import com.dao.TutorDao;

import java.util.List;

public class TutorDaoImpl extends BaseDao<Tutor> implements TutorDao {
    @Override
    public Tutor findByNumber(String number) {
        String sql = "select * from tutor where number = ?";
        return getBean(Tutor.class, sql, number);
    }

    @Override
    public void add(Tutor tutor) {
        String sql = "insert into tutor (number,password,name) values(?,?,?)";
        update(sql, tutor.getNumber(), tutor.getPassword(), tutor.getName());
    }

    @Override
    public Tutor findById(Tutor tutor) {
        String sql = "select * from tutor where id = ?";
        return getBean(Tutor.class, sql, tutor.getId());
    }

    @Override
    public List<Tutor> findAll() {
        String sql = "select * from tutor";
        return getBeanList(Tutor.class, sql);
    }

    @Override
    public Tutor findById(int id) {
        String sql = "select * from tutor where id = ?";
        return getBean(Tutor.class, sql, id);
    }

    @Override
    public List<Tutor> findByMajorId(int majorId) {
        String sql = "select * from tutor where major_id = ?";
        return getBeanList(Tutor.class, sql, majorId);
    }

    @Override
    public void updateInfoId(int infoId, int id) {
        String sql = "update tutor set tutor_info_id = ? where id = ?";
        update(sql, infoId, id);
    }

    @Override
    public void updatePassword(String password, int id) {
        String sql = "update tutor set password = ? where id=?";
        update(sql, password, id);
    }

    @Override
    public void initializeTempResult() {
        String sql = "delete from tutor_student";
        update(sql);
    }

    @Override
    public void initializeAllStatus() {
        String sql = "update tutor set tutor.left = ?";
        update(sql, Constants.TUTOR_MAX_CAPACITY);
    }

    @Override
    public List<Tutor> findByLeft() {
        String sql = "select * from tutor where tutor.left > 0";
        return getBeanList(Tutor.class, sql);
    }

    @Override
    public void takeStudent(int tutorId, Integer chosenId, int round) {
        String sql = "insert into tutor_student (tutor_id,student_id,round) values(?,?,?)";
        update(sql, tutorId, chosenId, round);
    }

    @Override
    public void updateLeft(int tutorId, int newLeft) {
        String sql = "update tutor set left = ? where id = ?";
        update(sql, newLeft, tutorId);
    }

    @Override
    public void removeTaken(int tutorId, int round) {
        String sql = "delete from tutor_student where tutor_id = ? and round = ?";
        update(sql, tutorId, round);
    }
}
