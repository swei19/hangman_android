package com.example.hangman;

import java.util.ArrayList;
import java.util.HashSet;


/**
 * @author Calvin Chan, Denes Marton, Spencer Wei This class is responsible for
 *         scoring the game based on the number of tries and the letters of the word
 *         that the player had to guess
 */
public class Score {

    boolean isCompetitive;


    public Score(boolean isCompetitive) {
        this.isCompetitive = isCompetitive;
    }


    /**
     * This method determines the player's score based on the unique letters of the word and
     * number of wrong guesses. Creates a set of unique letters then loops through it to find
     * how many times that letter occurs in the word. It then calls on the calculateLetterScore()
     * method in this class to determine the number of points that letter is worth.
     *
     * @return the score that the player achieved
     */

    public int scoreGame (String guessWord, int numWrongGuesses, ArrayList<String> guessedLetters) {
        int scoreToReturn = 0;
        //HashMap<String, Integer> letterOccurences = new HashMap<String, Integer>();

        HashSet<String> uniqueLettersOfGuessWord = new HashSet<String>();
        for (String uniqueLetter: guessWord.split("")) {
            uniqueLettersOfGuessWord.add(uniqueLetter);
        }

        for (String uniqueLetter: uniqueLettersOfGuessWord) {
            int numOccurences = guessWord.length() - guessWord.replace(uniqueLetter, "").length();

            if (guessedLetters.contains(uniqueLetter)) {
                scoreToReturn += numOccurences * (calculateLetterScore(uniqueLetter));
            }
        }

        if (scoreToReturn - numWrongGuesses >= 0) {
            return scoreToReturn - numWrongGuesses;
        } else {
            return 0;
        }

    }

    /**
     * The method will provide a multiplier based on if a specific letter is in the guess word.
     * 	vowels (a,e,i,o,u),n,r,t,l,s are worth 1 point each
     * 	d,g are worth 2 points each
     * 	b,c,m,p are worth 3 pts each
     *  f,h,v,w,y are worth 4 points each
     * 	k is worth 5 pts
     * 	j,x are worth 8 pts each
     * 	q,z are worth 10 pts each
     *
     * @return points Multiplier for how many points a specific letter is worth
     */
    public int calculateLetterScore(String uniqueLetter) {
        uniqueLetter = uniqueLetter.toLowerCase();
        int points = 0;

        if (uniqueLetter.matches("[aieounrtls]")) {
            points = 1;
        }
        if (uniqueLetter.matches("[dg]")) {
            points = 2;
        }
        if (uniqueLetter.matches("[bcmp]")) {
            points = 3;
        }
        if (uniqueLetter.matches("[fhvwy]")) {
            points = 4;
        }
        if (uniqueLetter.matches("[k]")) {
            points = 5;
        }
        if (uniqueLetter.matches("[xj]")) {
            points = 8;
        }
        if (uniqueLetter.matches("[qz]")) {
            points = 10;
        }

        return points;
    }
}

