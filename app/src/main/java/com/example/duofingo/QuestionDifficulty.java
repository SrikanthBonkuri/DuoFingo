package com.example.duofingo;

public enum QuestionDifficulty {
    EASY("EASY"),
    MEDIUM("MEDIUM"),
    HARD("HARD");


    private final String difficulty;

    QuestionDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
}
