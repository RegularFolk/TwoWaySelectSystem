package com.dao;

import com.bean.Preference;
import com.bean.Student;

import java.util.List;

public interface PreferenceDao {
    /**
     * 根据志愿批次和导师id查找志愿id
     * @param preference 支援批次 从Constants里面选择
     * @param tutorId 导师id
     * @return 志愿id的集合
     */
    List<Integer> findStudentIdsByPreference(Integer preference,Integer tutorId);

    /**
     * 根据志愿id查询对应导师id
     * @param preferenceId 志愿id
     * @return 所有导师id
     */
    List<Integer> findTutorIdsByPreferenceId(Integer preferenceId);
}
