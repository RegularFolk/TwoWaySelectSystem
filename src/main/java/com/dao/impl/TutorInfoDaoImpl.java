package com.dao.impl;

import com.bean.Tutor;
import com.bean.TutorInfo;
import com.dao.BaseDao;
import com.dao.TutorInfoDao;

import java.util.List;

public class TutorInfoDaoImpl extends BaseDao<TutorInfo> implements TutorInfoDao {
    @Override
    public TutorInfo findInfo(Tutor tutor) {
        String sql = "select * from tutor_info where id = ?";
        return getBean(TutorInfo.class, sql, tutor.getTutorInfoId());
    }

    @Override
    public TutorInfo findInfoByInfoId(int infoId) {
        String sql = "select * from tutor_info where id = ?";
        return getBean(TutorInfo.class, sql, infoId);
    }

    @Override
    public TutorInfo findInfoByTutorId(int tutorId) {
        String sql = "select tutor_info.* from tutor_info,tutor where tutor_info.id = tutor.tutor_info_id and tutor.id = ?";
        return getBean(TutorInfo.class, sql, tutorId);
    }

    @Override
    public List<TutorInfo> findAllInfo() {
        String sql = "select * from tutor_info ";
        return getBeanList(TutorInfo.class, sql);
    }

    @Override
    public int addInfo(TutorInfo tutorInfo) {
        String sql = "insert into tutor_info (gender, title, education_background, research_field, lessons, self_introduction) VALUES (?,?,?,?,?,?) ";
        return generatedKeyUpdate(sql, tutorInfo.getGender(), tutorInfo.getTitle(), tutorInfo.getEducationBackground(), tutorInfo.getResearchField(), tutorInfo.getLessons(), tutorInfo.getSelfIntroduction());
    }

    @Override
    public void updateInfo(int tutorInfoId, TutorInfo tutorInfo) {
        String sql = "update tutor_info set gender = ?, title=?, education_background = ?, research_field = ?, lessons = ?, self_introduction = ? where id=?";
        update(sql, tutorInfo.getGender(), tutorInfo.getTitle(), tutorInfo.getEducationBackground(), tutorInfo.getResearchField(), tutorInfo.getLessons(), tutorInfo.getSelfIntroduction(),tutorInfoId);
    }
}
