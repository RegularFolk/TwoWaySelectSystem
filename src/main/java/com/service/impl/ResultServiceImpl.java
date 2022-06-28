package com.service.impl;

import com.bean.*;
import com.dao.ResultDao;
import com.dao.StudentDao;
import com.dao.TutorDao;
import com.dao.impl.ResultDaoImpl;
import com.dao.impl.StudentDaoImpl;
import com.dao.impl.TutorDaoImpl;
import com.service.ResultService;

import java.util.List;

public class ResultServiceImpl implements ResultService {
    ResultDao resultDao = new ResultDaoImpl();
    StudentDao studentDao = new StudentDaoImpl();
    TutorDao tutorDao=new TutorDaoImpl();

    @Override
    public boolean hasFinalResult(int id) {
        List<IntBean> studentIdByEventId = resultDao.findStudentIdByEventId(id);
        return studentIdByEventId.size() > 0;

    }

    @Override
    public void updateResult(int eventId) {
        List<Student> all = studentDao.findAll();
        for (Student student : all) {
            resultDao.addResult(eventId, student.getId(), student.getTutorId());
        }
    }

    @Override
    public Result getResultByEventId(int eventId){
        Result result=new Result(eventId);
        List<IntBean> tutorList = resultDao.findTutorByEventId(eventId);
        List<Tutor> tutors = tutorDao.findTutorResult(tutorList);
        for (Tutor tutor:tutors){
            List<IntBean> studentList = resultDao.findStudentByEventIdTutor(eventId, tutor.getId());
            List<Student> students = studentDao.findStudentResult(studentList);
            tutor.setStudents(students);
        }
        result.setTutors(tutors);
        return result;
    }

    @Override
    public Result getResultByEventIdStudentId(int eventId, int studentId) {
        Result result=new Result(eventId);
        List<IntBean> tutorList = resultDao.findTutorByEventIdStudentId(eventId,studentId);
        List<Tutor> tutors = tutorDao.findTutorResult(tutorList);
        for (Tutor tutor:tutors){
            List<IntBean> studentList = resultDao.findStudentByEventIdTutor(eventId, tutor.getId());
            List<Student> students = studentDao.findStudentResult(studentList);
            tutor.setStudents(students);
        }
        result.setTutors(tutors);
        return result;
    }


}
