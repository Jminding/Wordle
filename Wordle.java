/*
 * File: Wordle.java
 * -----------------
 * Finished Version
 */

import edu.willamette.cs1.wordle.WordleDictionary;
import edu.willamette.cs1.wordle.WordleGWindow;
import java.util.*;

public class Wordle {

    public String correctWord = WordleDictionary.FIVE_LETTER_WORDS[(int) (Math.random() * WordleDictionary.FIVE_LETTER_WORDS.length)].toUpperCase();

    public void run() {
        gw = new WordleGWindow();
        gw.addEnterListener((s) -> enterAction(s));
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
                if (numTries == 1) gw.showMessage("Genius");
                else gw.showMessage("Impressive");
                gw.disableComponents();
            }
            
        } else {
            gw.showMessage("Not in word list");
        }
    }

    public void colorSquares(String s) {
        for (int i = 0; i < 5; i++) {
            if (s.charAt(i) == correctWord.charAt(i)) {
                gw.setSquareColor(gw.getCurrentRow(), i, gw.CORRECT_COLOR);
                gw.setKeyColor(s.substring(i, i + 1), gw.CORRECT_COLOR);
            }
            else if (correctWord.contains(s.substring(i, i + 1)) && correctWord.charAt(i) != s.charAt(i)) {
                gw.setSquareColor(gw.getCurrentRow(), i, gw.PRESENT_COLOR);
                gw.setKeyColor(s.substring(i, i + 1), gw.PRESENT_COLOR);
            }
            else {
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
