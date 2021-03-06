package com.example.hangman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class MainUI extends AppCompatActivity {

    private final static int NUMBER_OF_WRONG_GUESSES_ALLOWED = 6;

    private final static int NUMBER_OF_HINTS_ALLOWED = 2;

    private Score score;
    private int currentWrongGuesses = 0;
    private int currentCorrectGuesses = 0;
    private int competitiveCarryOverScore = 0;
    private int currentNumHintUsed = 0;

    private ArrayList<String> guessedLetters = new ArrayList<String>();
    private Display currentDisplay;

    private Set<String> uniqueLettersOfGuessedWord = new HashSet<String>();
    private ImageView hangmanImage;
    //private WebView gifImage;

    String currentGuessWord;
    String playerName;
    TextView currentScoreView;
    TextView wrongGuessesView;
    Button afterGameButton;
    String categoryChosen;
    WebView gifImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ui);

        Intent intent = getIntent();
        playerName = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        categoryChosen = intent.getStringExtra("CATEGORY_CHOSEN");

        TextView textView = findViewById(R.id.textView);
        textView.setText(playerName);

        WebView myWebView = (WebView) findViewById(R.id.webview);
        //WebView mywebview = findViewById(R.id.webview);
        myWebView.loadUrl("file:///android_asset/webpage.html");

        currentScoreView = findViewById(R.id.currentScore);
        wrongGuessesView = findViewById(R.id.currentWrongGuesses);
        afterGameButton = findViewById(R.id.newGameButton);;

        readImage(0, false);
        GuessWord gw = new GuessWord(categoryChosen, getApplicationContext());

        currentGuessWord = gw.selectWord();

        uniqueLettersOfGuessedWord = gw.getUniqueLettersOfSelectedWord();
        //NUMBER_OF_WRONG_GUESSES_ALLOWED = uniqueLettersOfGuessedWord.size();

        currentDisplay = new Display(currentGuessWord);
        TextView toGuess = findViewById(R.id.guessWordAndUnderlines);

        toGuess.setText(currentDisplay.displayLetterAndEmptyWordUnderlines(guessedLetters));

        score = new Score(categoryChosen.equals("competitive"));

        if (categoryChosen.equals("competitive")) {
            afterGameButton.setVisibility(View.GONE);
            competitiveCarryOverScore = intent.getIntExtra("prevScore", 0);

        } else {
            afterGameButton.setText("New Game");
        }

        TextView testView = findViewById(R.id.testTextView);
        testView.setText(currentGuessWord);

        currentScoreView.setText("" + competitiveCarryOverScore);

    }






    public void readImage(int numWrongGuesses, boolean wonGame)
    {
        String filename = null;
        //String imageFormat = ".png";
        String imageFormat = ".gif";

        if (numWrongGuesses == 0) {

            filename = "0.png";
        }
        else if (numWrongGuesses != NUMBER_OF_WRONG_GUESSES_ALLOWED && !wonGame) {
            filename = "NoGif" + imageFormat;
            //webview.loadUrl("file:///android_asset/your_html.html");

        } else if (wonGame) {
            filename = "WinnerGif.gif";
        } else {
            filename = "LoserGif.gif";
        }

        hangmanImage = findViewById(R.id.hangmanImage);
        try {
            InputStream currentImage = getAssets().open(filename);
            Drawable image = Drawable.createFromStream(currentImage, null);
            hangmanImage.setImageDrawable(image);
            currentImage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToNextGameOrHighScore(View v){
        if (categoryChosen.equals("competitive")){
            if (currentWrongGuesses == NUMBER_OF_WRONG_GUESSES_ALLOWED){
                Intent endGameIntent = new Intent(this, EndGameUI.class);
                endGameIntent.putExtra("score", competitiveCarryOverScore + score.scoreGame(currentGuessWord, currentWrongGuesses, guessedLetters));
                endGameIntent.putExtra("name", playerName);
                startActivity(endGameIntent);
            } else {
                Intent currentIntent = getIntent();
                currentIntent.putExtra("prevScore", competitiveCarryOverScore + score.scoreGame(currentGuessWord, currentWrongGuesses, guessedLetters));
                startActivity(currentIntent);
            }
        } else {
            Intent currentIntent = getIntent();
            finish();
            startActivity(currentIntent);
        }


    }

    public boolean gameIsWon(Set<String> uniqueLettersOfSelectedWord) {

        for (String uniqueLetters : uniqueLettersOfSelectedWord) {
            if (!guessedLetters.contains(uniqueLetters)) {
                return false;
            }
        }
        return true;
    }



    public void onClick(View v) {
        TextView toGuess = findViewById(R.id.guessWordAndUnderlines);

        if (currentWrongGuesses < NUMBER_OF_WRONG_GUESSES_ALLOWED){
            Button currentButton = findViewById(v.getId());
            CharSequence letterToAdd = currentButton.getText();
            if (currentButton.getText().equals("Hint")){
                System.out.println("HELLO MY FRIEND");
                Random rand = new Random();
                int chooseRand  = rand.nextInt(currentGuessWord.length());
                letterToAdd = currentGuessWord.substring(chooseRand, chooseRand + 1);

                while (guessedLetters.contains(letterToAdd)){
                    chooseRand  = rand.nextInt(currentGuessWord.length());
                    letterToAdd = currentGuessWord.substring(chooseRand, chooseRand + 1);
                }

                System.out.println("HELLO MY FRIEND:" + letterToAdd);
                currentNumHintUsed += 1;
            }
            guessedLetters.add((String) letterToAdd);
            TextView debug = findViewById(R.id.debugText);
            debug.setText(letterToAdd);
            toGuess.setText(currentDisplay.displayLetterAndEmptyWordUnderlines(guessedLetters));

            if (uniqueLettersOfGuessedWord.contains(letterToAdd)) {
                currentCorrectGuesses += 1;

                if (gameIsWon(uniqueLettersOfGuessedWord)) {
                    readImage(currentWrongGuesses, true);
                    if (categoryChosen.equals("competitive")){
                        afterGameButton.setVisibility(View.VISIBLE);
                        afterGameButton.setText("Next Game");
                    }

                }
            } else {
                currentWrongGuesses += 1;

                wrongGuessesView.setText("" + currentWrongGuesses);

                readImage(currentWrongGuesses, false);

                if (currentWrongGuesses == NUMBER_OF_WRONG_GUESSES_ALLOWED){
                    if (categoryChosen.equals("competitive")){
                        afterGameButton.setVisibility(View.VISIBLE);
                        afterGameButton.setText("Go to HighScore");
                    }
                }

            }

            if (currentButton.getText().equals("Hint")) {
                if (currentNumHintUsed == NUMBER_OF_HINTS_ALLOWED){
                    currentButton.setEnabled(false);

                }

            } else {
                currentButton.setEnabled(false);

            }


        } else {
            toGuess.setText(currentGuessWord);
        }
        int updatedScore = score.scoreGame(currentGuessWord, currentWrongGuesses, guessedLetters);
        currentScoreView.setText("" + (updatedScore + competitiveCarryOverScore));


    }

}
