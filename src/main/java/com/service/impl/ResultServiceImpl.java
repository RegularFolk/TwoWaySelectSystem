package com.service.impl;

import com.bean.Student;
import com.dao.ResultDao;
import com.dao.StudentDao;
import com.dao.impl.ResultDaoImpl;
import com.dao.impl.StudentDaoImpl;
import com.service.ResultService;

import java.util.List;

public class ResultServiceImpl implements ResultService {
    ResultDao resultDao = new ResultDaoImpl();
    StudentDao studentDao = new StudentDaoImpl();

    @Override
    public boolean hasFinalResult(int id) {
        List<Integer> studentIdByEventId = resultDao.findStudentIdByEventId(id);
        return studentIdByEventId.size() > 0;

    }

    @Override
    public void updateResult(int eventId) {
        List<Student> all = studentDao.findAll();
        for (Student student : all) {
            resultDao.addResult(eventId, student.getId(), student.getTutorId());
        }
    }
}
