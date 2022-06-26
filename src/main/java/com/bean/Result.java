package com.bean;

import java.util.List;

public class Result {
    private int eventId;
    private int studentId;
    private int tutorId;
    private List<Tutor> tutors;

    public Result() {
    }

    public Result(int eventId, int studentId, int tutorId, List<Tutor> tutors) {
        this.eventId = eventId;
        this.studentId = studentId;
        this.tutorId = tutorId;
        this.tutors = tutors;
    }

    public Result(int eventId) {
        this.eventId = eventId;
    }

    @Override
    public String toString() {
        return "Result{" +
                "eventId=" + eventId +
                ", studentId=" + studentId +
                ", tutorId=" + tutorId +
                ", tutors=" + tutors +
                '}';
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getTutorId() {
        return tutorId;
    }

    public void setTutorId(int tutorId) {
        this.tutorId = tutorId;
    }

    public List<Tutor> getTutors() {
        return tutors;
    }

    public void setTutors(List<Tutor> tutors) {
        this.tutors = tutors;
    }
}
