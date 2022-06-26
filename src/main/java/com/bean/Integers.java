package com.bean;

import java.util.List;

public class Integers {
    Integer round;
    List<Integer> chosenIds;
    String method;

    @Override
    public String toString() {
        return "Integers{" +
                "round=" + round +
                ", chosenIds=" + chosenIds +
                ", method='" + method + '\'' +
                '}';
    }

    public Integer getRound() {
        return round;
    }

    public void setRound(Integer round) {
        this.round = round;
    }

    public Integers(Integer round, List<Integer> chosenIds, String method) {
        this.round = round;
        this.chosenIds = chosenIds;
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<Integer> getChosenIds() {
        return chosenIds;
    }

    public void setChosenIds(List<Integer> chosenIds) {
        this.chosenIds = chosenIds;
    }
}
