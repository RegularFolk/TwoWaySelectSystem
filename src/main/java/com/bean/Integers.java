package com.bean;

import java.util.List;

public class Integers {
    Integer round;
    List<Integer> chosenIds;

    @Override
    public String toString() {
        return "Integers{" +
                "round=" + round +
                ", chosenIds=" + chosenIds +
                '}';
    }

    public Integer getRound() {
        return round;
    }

    public void setRound(Integer round) {
        this.round = round;
    }

    public Integers(Integer round, List<Integer> chosenIds) {
        this.round = round;
        this.chosenIds = chosenIds;
    }

    public List<Integer> getChosenIds() {
        return chosenIds;
    }

    public void setChosenIds(List<Integer> chosenIds) {
        this.chosenIds = chosenIds;
    }
}
