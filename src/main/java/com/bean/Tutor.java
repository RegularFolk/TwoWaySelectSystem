package com.bean;

import java.util.List;

public class Tutor {
    private int id;
    private int majorId;
    private int tutorInfoId;
    private int left;
    private String authority;
    private String number;
    private String password;
    private String name;
    private String code;
    private Major major;
    private TutorInfo tutorInfo;
    private List<Student> students;

    public Tutor() {
    }

    public Tutor(String number, String password) {
        this.number = number;
        this.password = password;
    }

    public Tutor(String number, String password, String name) {
        this.number = number;
        this.password = password;
        this.name = name;
    }

    public Tutor(String number, String password, String name, Major major) {
        this.number = number;
        this.password = password;
        this.name = name;
        this.major = major;
    }

    public Tutor(int id, int majorId, int tutorInfoId, int left, String authority, String number, String password, String name, String code, Major major, TutorInfo tutorInfo, List<Student> students) {
        this.id = id;
        this.majorId = majorId;
        this.tutorInfoId = tutorInfoId;
        this.left = left;
        this.authority = authority;
        this.number = number;
        this.password = password;
        this.name = name;
        this.code = code;
        this.major = major;
        this.tutorInfo = tutorInfo;
        this.students = students;
    }

    @Override
    public String toString() {
        return "Tutor{" +
                "id=" + id +
                ", majorId=" + majorId +
                ", tutorInfoId=" + tutorInfoId +
                ", left=" + left +
                ", authority='" + authority + '\'' +
                ", number='" + number + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", major=" + major +
                ", tutorInfo=" + tutorInfo +
                ", students=" + students +
                '}';
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getMajorId() {
        return majorId;
    }

    public void setMajorId(int majorId) {
        this.majorId = majorId;
    }

    public int getTutorInfoId() {
        return tutorInfoId;
    }

    public void setTutorInfoId(int tutorInfoId) {
        this.tutorInfoId = tutorInfoId;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    public TutorInfo getTutorInfo() {
        return tutorInfo;
    }

    public void setTutorInfo(TutorInfo tutorInfo) {
        this.tutorInfo = tutorInfo;
    }
}
