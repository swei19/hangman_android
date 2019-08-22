package com.example.hangman;

import java.util.ArrayList;

public class Display {

    String[] randomWordSelectedArr;

    /**
     * UserInterface Constructor
     */
    public Display(String randomWordSelected) {
        this.randomWordSelectedArr = randomWordSelected.trim().split("");
    }

    /**
     * The below method will display the letters that are guessed correctly and
     * those that are not yet guessed as underlines
     */

    public String displayLetterAndEmptyWordUnderlines(ArrayList<String> guessedLetters) {

        String stringToPrint = "";
        for (int i = 0; i < randomWordSelectedArr.length; i++) {

            if (guessedLetters.contains(randomWordSelectedArr[i])) {
                stringToPrint += " " + randomWordSelectedArr[i] + " ";

            } else {
                stringToPrint += " _ ";

            }
            ;
        }
        return stringToPrint;

    }

}