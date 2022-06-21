package com.bean;

import java.util.ArrayList;
import java.util.List;

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

    public Preference(List<Integer> tutorIds) {
        if (tutorIds.size() == 3) {
            this.no1 = tutorIds.get(0);
            this.no2 = tutorIds.get(1);
            this.no3 = tutorIds.get(2);
        } else if (tutorIds.size() == 2) {
            this.no1 = tutorIds.get(0);
            this.no2 = tutorIds.get(1);
        } else if (tutorIds.size() == 1) {
            this.no1 = tutorIds.get(0);
        } else if (tutorIds.size() != 0) {
            throw new RuntimeException("Preference初始化错误！");
        }
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

    public List<Integer> getList() {
        List<Integer> list = new ArrayList<>();
        if (this.no1 != 0) {
            list.add(this.no1);
        }
        if (this.no2 != 0) {
            list.add(this.no2);
        }
        if (this.no3 != 0) {
            list.add(this.no3);
        }
        return list;
    }
}
