package com.bean;

public class Preference {
    private int id;
    private int status;
    private int no1;
    private int no2;
    private int no3;
    Tutor tutor1;
    Tutor tutor2;
    Tutor tutor3;

    public Preference() {
    }

    public Preference(int id, int status, int no1, int no2, int no3, Tutor tutor1, Tutor tutor2, Tutor tutor3) {
        this.id = id;
        this.status = status;
        this.no1 = no1;
        this.no2 = no2;
        this.no3 = no3;
        this.tutor1 = tutor1;
        this.tutor2 = tutor2;
        this.tutor3 = tutor3;
    }

    @Override
    public String toString() {
        return "Preference{" +
                "id=" + id +
                ", status=" + status +
                ", no1=" + no1 +
                ", no2=" + no2 +
                ", no3=" + no3 +
                ", tutor1=" + tutor1 +
                ", tutor2=" + tutor2 +
                ", tutor3=" + tutor3 +
                '}';
    }

    public Tutor getTutor1() {
        return tutor1;
    }

    public void setTutor1(Tutor tutor1) {
        this.tutor1 = tutor1;
    }

    public Tutor getTutor2() {
        return tutor2;
    }

    public void setTutor2(Tutor tutor2) {
        this.tutor2 = tutor2;
    }

    public Tutor getTutor3() {
        return tutor3;
    }

    public void setTutor3(Tutor tutor3) {
        this.tutor3 = tutor3;
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

    public int getNo1() {
        return no1;
    }

    public void setNo1(int no1) {
        this.no1 = no1;
    }

    public int getNo2() {
        return no2;
    }

    public void setNo2(int no2) {
        this.no2 = no2;
    }

    public int getNo3() {
        return no3;
    }

    public void setNo3(int no3) {
        this.no3 = no3;
    }
}
