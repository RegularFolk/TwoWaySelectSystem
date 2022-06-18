package com.bean;

public class StudentInfo {
    private int id;
    private int gender;
    private String birthday;
    private String politics_status;
    private String phoneNumber;
    private String email;
    private String planning;
    private String honor;

    public StudentInfo() {
    }

    public StudentInfo(int id, int gender, String birthday, String politics_status, String phoneNumber, String email, String planning, String honor) {
        this.id = id;
        this.gender = gender;
        this.birthday = birthday;
        this.politics_status = politics_status;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.planning = planning;
        this.honor = honor;
    }

    @Override
    public String toString() {
        return "StudentInfo{" +
                "id=" + id +
                ", gender=" + gender +
                ", birthday='" + birthday + '\'' +
                ", politics_status='" + politics_status + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", planning='" + planning + '\'' +
                ", honor='" + honor + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPolitics_status() {
        return politics_status;
    }

    public void setPolitics_status(String politics_status) {
        this.politics_status = politics_status;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPlanning() {
        return planning;
    }

    public void setPlanning(String planning) {
        this.planning = planning;
    }

    public String getHonor() {
        return honor;
    }

    public void setHonor(String honor) {
        this.honor = honor;
    }
}
