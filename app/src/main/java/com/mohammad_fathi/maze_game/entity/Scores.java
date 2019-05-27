package com.mohammad_fathi.maze_game.entity;

public class Scores {

    private String scoreNumber;
    private int id;


    public Scores(String scoreNumber) {
        this.scoreNumber = scoreNumber;
    }

    public Scores(String scoreNumber, int id) {
        this.scoreNumber = scoreNumber;
        this.id = id;
    }

    public String getScoreNumber() {
        return scoreNumber;
    }

    public void setScoreNumber(String scoreNumber) {
        this.scoreNumber = scoreNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
