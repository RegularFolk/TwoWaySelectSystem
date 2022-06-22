package com.bean;

import java.util.List;

public class Event {
    private int id;
    private int eventInfoId;
    private int status;
    private String submitTime;
    private String round1;
    private String round2;
    private String round3;
    private String endTime;
    private String description;
    private List<Major> majors;
    private EventInfo eventInfo;

    public Event() {
    }

    public Event(int id, int eventInfoId, int status, String submitTime, String round1, String round2, String round3, String endTime, String description, List<Major> majors, EventInfo eventInfo) {
        this.id = id;
        this.eventInfoId = eventInfoId;
        this.status = status;
        this.submitTime = submitTime;
        this.round1 = round1;
        this.round2 = round2;
        this.round3 = round3;
        this.endTime = endTime;
        this.description = description;
        this.majors = majors;
        this.eventInfo = eventInfo;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", eventInfoId=" + eventInfoId +
                ", status=" + status +
                ", submitTime='" + submitTime + '\'' +
                ", round1='" + round1 + '\'' +
                ", round2='" + round2 + '\'' +
                ", round3='" + round3 + '\'' +
                ", endTime='" + endTime + '\'' +
                ", description='" + description + '\'' +
                ", majors=" + majors +
                ", eventInfo=" + eventInfo +
                '}';
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }

    public int getEventInfoId() {
        return eventInfoId;
    }

    public void setEventInfoId(int eventInfoId) {
        this.eventInfoId = eventInfoId;
    }

    public EventInfo getEventInfo() {
        return eventInfo;
    }

    public void setEventInfo(EventInfo eventInfo) {
        this.eventInfo = eventInfo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRound1() {
        return round1;
    }

    public void setRound1(String round1) {
        this.round1 = round1;
    }

    public String getRound2() {
        return round2;
    }

    public void setRound2(String round2) {
        this.round2 = round2;
    }

    public String getRound3() {
        return round3;
    }

    public void setRound3(String round3) {
        this.round3 = round3;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public List<Major> getMajors() {
        return majors;
    }

    public void setMajors(List<Major> majors) {
        this.majors = majors;
    }
}
