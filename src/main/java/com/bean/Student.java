package com.bean;

public class Student {
    private int id;
    private int status;
    private int collegeMajor;
    private int selfInfoId;
    private int preferenceId;
    private int majorId;
    private int tutorId;
    private String studentNumber;  //通过学号和密码登录
    private String password;
    private String studentName;
    private StudentInfo studentInfo;
    private Preference preference;
    private Major major;
    private Tutor tutor;

    public Student() {
    }

    public Student(String studentNumber, String password) {
        this.studentNumber = studentNumber;
        this.password = password;
    }

    public Student(String studentNumber, String password, Major major) {
        this.studentNumber = studentNumber;
        this.password = password;
        this.major = major;
    }

    public Student(int id, int status, int collegeMajor, int selfInfoId, int preferenceId, int majorId, int tutorId, String studentNumber, String password, String studentName, StudentInfo studentInfo, Preference preference, Major major, Tutor tutor) {
        this.id = id;
        this.status = status;
        this.collegeMajor = collegeMajor;
        this.selfInfoId = selfInfoId;
        this.preferenceId = preferenceId;
        this.majorId = majorId;
        this.tutorId = tutorId;
        this.studentNumber = studentNumber;
        this.password = password;
        this.studentName = studentName;
        this.studentInfo = studentInfo;
        this.preference = preference;
        this.major = major;
        this.tutor = tutor;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", status=" + status +
                ", collegeMajor=" + collegeMajor +
                ", selfInfoId=" + selfInfoId +
                ", preferenceId=" + preferenceId +
                ", majorId=" + majorId +
                ", tutorId=" + tutorId +
                ", studentNumber='" + studentNumber + '\'' +
                ", password='" + password + '\'' +
                ", studentName='" + studentName + '\'' +
                ", studentInfo=" + studentInfo +
                ", preference=" + preference +
                ", major=" + major +
                ", tutor=" + tutor +
                '}';
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getSelfInfoId() {
        return selfInfoId;
    }

    public void setSelfInfoId(int selfInfoId) {
        this.selfInfoId = selfInfoId;
    }

    public int getPreferenceId() {
        return preferenceId;
    }

    public void setPreferenceId(int preferenceId) {
        this.preferenceId = preferenceId;
    }

    public int getMajorId() {
        return majorId;
    }

    public void setMajorId(int majorId) {
        this.majorId = majorId;
    }

    public int getTutorId() {
        return tutorId;
    }

    public void setTutorId(int tutorId) {
        this.tutorId = tutorId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCollegeMajor() {
        return collegeMajor;
    }

    public void setCollegeMajor(int collegeMajor) {
        this.collegeMajor = collegeMajor;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public StudentInfo getStudentInfo() {
        return studentInfo;
    }

    public void setStudentInfo(StudentInfo studentInfo) {
        this.studentInfo = studentInfo;
    }

    public Preference getPreference() {
        return preference;
    }

    public void setPreference(Preference preference) {
        this.preference = preference;
    }

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }
}
