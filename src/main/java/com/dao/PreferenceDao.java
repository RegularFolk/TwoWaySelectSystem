package com.dao;

import com.bean.Preference;

public interface PreferenceDao {
    /**
     * 根据志愿批次和导师id查找志愿id
     * @param preference 支援批次 从Constants里面选择
     * @param tutorId 导师id
     * @return 志愿id的集合
     */
    Preference findStudentIdsByPreference(Integer preference, Integer tutorId);

    /**
     * 根据志愿id查询对应导师id
     * @param preferenceId 志愿id
     * @return 所有导师id
     */
    Preference findTutorIdsByPreferenceId(Integer preferenceId);

    void update(Preference preference, int id);

    int addPreference(Preference preference);

    void initialize();
}
