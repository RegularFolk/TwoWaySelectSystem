package com.bean;

public class TutorInfo {
    private int id;
    private int gender;
    private String title;
    private String educationBackground;
    private String researchField;
    private String lessons;
    private String selfIntroduction;

    public TutorInfo() {
    }

    public TutorInfo(int id, int gender, String title, String educationBackground, String researchField, String lessons, String selfIntroduction) {
        this.id = id;
        this.gender = gender;
        this.title = title;
        this.educationBackground = educationBackground;
        this.researchField = researchField;
        this.lessons = lessons;
        this.selfIntroduction = selfIntroduction;
    }

    @Override
    public String toString() {
        return "TutorInfo{" +
                "id=" + id +
                ", gender=" + gender +
                ", title='" + title + '\'' +
                ", educationBackground='" + educationBackground + '\'' +
                ", researchField='" + researchField + '\'' +
                ", lessons='" + lessons + '\'' +
                ", selfIntroduction='" + selfIntroduction + '\'' +
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEducationBackground() {
        return educationBackground;
    }

    public void setEducationBackground(String educationBackground) {
        this.educationBackground = educationBackground;
    }

    public String getResearchField() {
        return researchField;
    }

    public void setResearchField(String researchField) {
        this.researchField = researchField;
    }

    public String getLessons() {
        return lessons;
    }

    public void setLessons(String lessons) {
        this.lessons = lessons;
    }

    public String getSelfIntroduction() {
        return selfIntroduction;
    }

    public void setSelfIntroduction(String selfIntroduction) {
        this.selfIntroduction = selfIntroduction;
    }
}
