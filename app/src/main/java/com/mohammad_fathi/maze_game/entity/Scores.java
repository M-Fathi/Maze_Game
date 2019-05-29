package com.mohammad_fathi.maze_game.entity;

public class Scores {

    private int scoreNumber;
    private int id;


    public Scores(int scoreNumber) {
        this.scoreNumber = scoreNumber;
    }

    public Scores(int scoreNumber, int id) {
        this.scoreNumber = scoreNumber;
        this.id = id;
    }

    public int getScoreNumber() {
        return scoreNumber;
    }

    public void setScoreNumber(int scoreNumber) {
        this.scoreNumber = scoreNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
