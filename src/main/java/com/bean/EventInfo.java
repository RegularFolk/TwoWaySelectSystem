package com.bean;

public class EventInfo {
    int id;
    int majorId;
    String initiator;
    Major major;
    String description;

    public EventInfo() {
    }


    public EventInfo(int id, int majorId, String initiator, Major major, String description) {
        this.id = id;
        this.majorId = majorId;
        this.initiator = initiator;
        this.major = major;
        this.description = description;
    }


    @Override
    public String toString() {
        return "EventInfo{" +
                "id=" + id +
                ", majorId=" + majorId +
                ", initiator='" + initiator + '\'' +
                ", major=" + major +
                ", description='" + description + '\'' +
                '}';
    }

    public int getMajorId() {
        return majorId;
    }

    public void setMajorId(int majorId) {
        this.majorId = majorId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInitiator() {
        return initiator;
    }

    public void setInitiator(String initiator) {
        this.initiator = initiator;
    }

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
