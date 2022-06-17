package com.bean;

public class Major {
    private int id;
    private String collegeName;
    private String major;

    public Major() {
    }

    public Major(int id, String collegeName, String major) {
        this.id = id;
        this.collegeName = collegeName;
        this.major = major;
    }

    @Override
    public String toString() {
        return "Major{" +
                "id=" + id +
                ", collegeName='" + collegeName + '\'' +
                ", major='" + major + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }
}
