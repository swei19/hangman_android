package com.example.hangman;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class EndGameUI extends AppCompatActivity {

    final static String HIGH_SCORE_FILE_NAME = "highscores.txt";
    final static int NUM_HIGH_SCORES_TO_DISPLAY = 10;
    private static ArrayList<HighScore> scoreBoard;
    private HighScore currentScore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.end_game_ui);

        scoreBoard = new ArrayList<HighScore>();
        //this.currentScore = highScore;

        Intent intent = getIntent();
        String playerName = intent.getStringExtra("name");
        int scoreObtained = intent.getIntExtra("score",0);

        System.out.println("THE EVILNESS: " + playerName);
        System.out.println("THE EVILNESS2: " + intent.getExtras());

        HighScore playerHighScore = new HighScore(playerName, scoreObtained);
        currentScore = playerHighScore;

        initScoreList();
        setDisplayScore();
        writeToScoreTextFile();
    }

    public void initScoreList() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int numOfScores;

        if (preferences.getAll().size() < NUM_HIGH_SCORES_TO_DISPLAY){
            numOfScores = preferences.getAll().size();
        } else {
            numOfScores = NUM_HIGH_SCORES_TO_DISPLAY;
        }


        for (int i = 0; i < numOfScores; i++){

            String[] currentScoreEntry = preferences.getString("" + i, "").split(" ");


            String playerName;
            int playerScore;

            if (currentScoreEntry.length > 2) {


                playerName = TextUtils.join(" ", Arrays.copyOfRange(currentScoreEntry, 0, currentScoreEntry.length - 1));

                playerScore = Integer.parseInt(currentScoreEntry[currentScoreEntry.length - 1]);

            } else {
                playerName = currentScoreEntry[0];
                playerScore = Integer.parseInt(currentScoreEntry[1]);

            }

            HighScore highScore = new HighScore(playerName, playerScore);
            scoreBoard.add(highScore);
        }
        addToScoreAndSort();

        }


    /**
     * The below method adds the score of the current game to the scoreBoard
     * ArrayList and sorts it in descending order using the comparator in the
     * HighScore class
     *
     * @see HighScore#compareTo(Object)
     */
    public void addToScoreAndSort() {
        scoreBoard.add(currentScore);
        Collections.sort(scoreBoard);
    }

    /**
     * The below method creates a new JPanel which will contain the high scores. It
     * will loop through the scoreBoard data structure and for each score, create a
     * new JLabel which is then added to the JPanel.
     *
     * @return the panel containing the JLabels which contains the scores and will
     * be used to display the scores
     */

    public void setDisplayScore() {
        String highScoreString = "";

        String currentScoreText = "";
        int currentScoreNum = 1;


        for (int i = 0; i < scoreBoard.size(); i++) {
            HighScore currentHighScore = scoreBoard.get(i);
            TextView currentScoreView = findViewById(getResources().getIdentifier("score" + currentScoreNum, "id", getPackageName()));

            if (i != 9) {
                currentScoreText = "  " + (i + 1) + ". " + currentHighScore.getName() + ": " + currentHighScore.getScore();
            } else {
                currentScoreText = (i + 1) + ". " + currentHighScore.getName() + ": " + currentHighScore.getScore();

            }

            System.out.println(currentScoreText);

            currentScoreView.setText(currentScoreText);
            currentScoreNum += 1;
        }
    }


    public void writeToScoreTextFile () {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();

        for (int i = 0; i < scoreBoard.size(); i++) {
            HighScore currentHighScore = scoreBoard.get(i);
            editor.putString("" + i, currentHighScore.getName() + " " + currentHighScore.getScore());

            if (i == NUM_HIGH_SCORES_TO_DISPLAY - 1) {
                break;
            }
        }
        editor.commit();

    }


    /**
     * @return the scoreBoard that contains the current high scores
     */

    public static ArrayList<HighScore> getScoreBoard () {
        return scoreBoard;
    }

}


