/*
 * File: Wordle.java
 * -----------------
 * Finished Version
 */

import edu.willamette.cs1.wordle.WordleDictionary;
import edu.willamette.cs1.wordle.WordleGWindow;
import java.util.*;

public class Wordle {

    final String correctWord = WordleDictionary.FIVE_LETTER_WORDS[(int) (Math.random() * WordleDictionary.FIVE_LETTER_WORDS.length)].toUpperCase();
    private HashMap<String, Integer> guessCharCount = new HashMap<>(); // the number of each character in the guess
    private HashMap<String, Integer> answerCharCount = new HashMap<>(); // the number of each character in the answer
    private HashMap<String, Integer> correctCharCount = new HashMap<>(); // the number of correct characters in the guess
    private HashMap<String, Integer> currChar = new HashMap<>(); // the number of the current character

    public void run() {
        gw = new WordleGWindow();
        gw.addEnterListener((s) -> enterAction(s));
        for (int i = 0; i < 5; i++) {
            if (!answerCharCount.containsKey(correctWord.substring(i, i + 1))) answerCharCount.put(correctWord.substring(i, i + 1), 1);
            else answerCharCount.put(correctWord.substring(i, i + 1), answerCharCount.get(correctWord.substring(i, i + 1)) + 1);
        }
    }

/*
 * Called when the user hits the RETURN key or clicks the ENTER button,
 * passing in the string of characters on the current row.
 */

    public void enterAction(String s) {
        // gw.showMessage("You have to implement this method.");
        /*
         * check if s is in the list of words
         * if it is in the list of words, color the squares and do
         * setCurrentRow(getRow() + 1)
         * else if it is not make the user try again and don't color the squares
         */
        int pos = Collections.binarySearch(Arrays.asList(WordleDictionary.FIVE_LETTER_WORDS), s.toLowerCase());
        guessCharCount.clear();
        currChar.clear();
        correctCharCount.clear();
        for (int i = 0; i < 5; i++) {
            if (!guessCharCount.containsKey(s.substring(i, i + 1))) guessCharCount.put(s.substring(i, i + 1), 1);
            else guessCharCount.put(s.substring(i, i + 1), guessCharCount.get(s.substring(i, i + 1)) + 1);
            if (s.charAt(i) == correctWord.charAt(i)) {
                if (!correctCharCount.containsKey(s.substring(i, i + 1))) correctCharCount.put(s.substring(i, i + 1), 1);
                else correctCharCount.put(s.substring(i, i + 1), correctCharCount.get(s.substring(i, i + 1)) + 1);
            }
        }
        if (pos >= 0) {
            numTries++;
            // gw.showMessage("Valid word!");
            if (!s.equals(correctWord)) {
                if (numTries == 6) {
                    colorSquares(s);
                    gw.showMessage(correctWord);
                    gw.disableComponents();
                } else {
                    colorSquares(s);
                    gw.setCurrentRow(gw.getCurrentRow() + 1);
                }
            } else {
                for (int i = 0; i < 5; i++) {
                    gw.setSquareColor(gw.getCurrentRow(), i, gw.CORRECT_COLOR);
                    gw.setKeyColor(s.substring(i, i + 1), gw.CORRECT_COLOR);
                }
                if (numTries <= 2) gw.showMessage("Genius");
                else gw.showMessage("Impressive");
                gw.disableComponents();
            }
            
        } else {
            gw.showMessage("Not in word list");
        }
    }

    public void colorSquares(String s) {
        for (int i = 0; i < 5; i++) {
            if (!currChar.containsKey(s.substring(i, i + 1))) currChar.put(s.substring(i, i + 1), 1);
            else currChar.put(s.substring(i, i + 1), currChar.get(s.substring(i, i + 1)) + 1);
            if (s.charAt(i) == correctWord.charAt(i)) {
                gw.setSquareColor(gw.getCurrentRow(), i, gw.CORRECT_COLOR);
                gw.setKeyColor(s.substring(i, i + 1), gw.CORRECT_COLOR);
            } else if (correctWord.contains(s.substring(i, i + 1)) && correctWord.charAt(i) != s.charAt(i)) {
                if (correctCharCount.containsKey(s.substring(i, i + 1)) && currChar.get(s.substring(i, i + 1)) + correctCharCount.get(s.substring(i, i + 1)) <= answerCharCount.get(s.substring(i, i + 1))) {
                    gw.setSquareColor(gw.getCurrentRow(), i, gw.PRESENT_COLOR);
                    if (!gw.getKeyColor(s.substring(i, i + 1)).equals(gw.CORRECT_COLOR))
                        gw.setKeyColor(s.substring(i, i + 1), gw.PRESENT_COLOR);
                } else if (guessCharCount.get(s.substring(i, i + 1)) <= answerCharCount.get(s.substring(i, i + 1))) {
                    if (!gw.getKeyColor(s.substring(i, i + 1)).equals(gw.CORRECT_COLOR))
                        gw.setKeyColor(s.substring(i, i + 1), gw.PRESENT_COLOR);
                    gw.setSquareColor(gw.getCurrentRow(), i, gw.PRESENT_COLOR);
                } else {
                    gw.setSquareColor(gw.getCurrentRow(), i, gw.MISSING_COLOR);
                }
            } else {
                gw.setSquareColor(gw.getCurrentRow(), i, gw.MISSING_COLOR);
                gw.setKeyColor(s.substring(i, i + 1), gw.MISSING_COLOR);
            }
        }
    }


/* Startup code */

    public static void main(String[] args) {
        new Wordle().run();
    }

/* Private instance variables */

    private WordleGWindow gw;
    private int numTries = 0;
}
