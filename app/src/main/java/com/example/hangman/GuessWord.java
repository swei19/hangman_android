package com.example.hangman;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

/**
 * @author Calvin Chan, Denes Marton, Spencer Wei The class below will be
 *         responsible for randomly selecting a word from a list of words based
 *         on a category chosen by a player The player will then need to guess
 *         this word
 */

public class GuessWord {
    private final static String[] possibleCategories = {"places", "countries", "food", "animals"};
    private final static int numPossibleCategories = possibleCategories.length;
    private ArrayList<String> candidateGuessWords;
    private Set<String> uniqueLettersOfSelectedWord;
    private String categoryChosen;

    public GuessWord(String categoryChosen, Context context) {
        String currentPathToTextFile;
        this.candidateGuessWords = new ArrayList<String>();
        this.uniqueLettersOfSelectedWord = new HashSet<String>();
        this.categoryChosen = categoryChosen;



        if (categoryChosen.equals("competitive")) {
            for (String category : possibleCategories) {
                initWordList(context, category);
            }
        } else {
            initWordList(context, categoryChosen);
        }
    }

    /**
     * Opens a .txt file containing words of the chosen category and loops through
     * each word in the .txt file to the 'wordsOfCategory' ArrayList
     */

    public void initWordList(Context context, String category) {


        Scanner s = new Scanner(context.getResources().openRawResource
                    (context.getResources().getIdentifier(category, "raw", context.getPackageName())));


        while (s.hasNextLine()) {
            candidateGuessWords.add(s.nextLine().toUpperCase());

        }


    }



    /**
     * Randomly chooses a word of a chosen category from the 'wordsOfCategory'
     * ArrayList and returns it
     *
     * @return a random word that the player will guess
     */
    public String selectWord() {
        Random random = new Random();
        int randomIndex = random.nextInt(candidateGuessWords.size());

        String selectedWord = candidateGuessWords.get(randomIndex);
        String[] selectedWordArr = selectedWord.split("");

        for (int i = 0; i < selectedWordArr.length; i++) {
            this.uniqueLettersOfSelectedWord.add(selectedWordArr[i]);
        }

        return selectedWord;
    }

    public static int getNumPossibleCategories() {
        return numPossibleCategories;
    }

    public static String[] getPossibleCategories() {
        return possibleCategories;
    }

    public Set<String> getUniqueLettersOfSelectedWord() {
        return uniqueLettersOfSelectedWord;
    }




}




