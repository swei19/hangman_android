package com.example.hangman;

/**
 * The class below is responsible for comparing and sorting scores to allow
 * scores high enough to be entered into the highscore board
 *
 * @author Spencer
 *
 */

public class HighScore implements Comparable<Object> {
    private String playerName;
    private int score;

    public HighScore(String playerName, int score) {
        this.playerName = playerName;
        this.score = score;
    }

    public String getName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }

    /**
     * Comparator to sort high score by score in descending order
     */

    @Override
    public int compareTo(Object o) {
        HighScore currentScore = (HighScore) o;
        if (this.getScore() < currentScore.getScore()) {
            return 1;
        } else if (this.getScore() > currentScore.getScore()) {
            return -1;
        } else {
            return 0;
        }
    }
}