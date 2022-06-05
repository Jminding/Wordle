/*
 * File: Wordle.java
 * -----------------
 * Finished Version
 */

import edu.willamette.cs1.wordle.WordleDictionary;
import edu.willamette.cs1.wordle.WordleGWindow;

import java.awt.*;
import java.util.*;

public class Wordle {

    final String correctWord = WordleDictionary.FIVE_LETTER_WORDS[(int) (Math.random() * WordleDictionary.FIVE_LETTER_WORDS.length)].toUpperCase();
    private HashMap<String, Integer> answerCharCount = new HashMap<>(); // the number of each character in the answer
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
        currChar.clear();
        if (pos >= 0) {
            numTries++;
            if (!s.equals(correctWord)) {
                if (numTries == 6) {
                    colorSquares(s);
                    gw.showMessage(correctWord);
                    try { // Set the current row to outside the bounds so no more inputs can be made
                        gw.setCurrentRow(6);
                    } catch (Exception e) { // And don't make it throw an error

                    }
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
                try { // Set the current row to outside the bounds so no more inputs can be made
                    gw.setCurrentRow(6);
                } catch (Exception e) { // And don't make it throw an error

                }
            }
        } else if (s.length() < 5) {
            gw.showMessage("Not enough letters");
        } else {
            gw.showMessage("Not in word list");
        }
    }

    public void colorSquares(String s) {
        for (int i = 0; i < 5; i++) { // Color in all the ones that are correct first
            if (s.charAt(i) == correctWord.charAt(i)) {
                if (!currChar.containsKey(s.substring(i, i + 1))) currChar.put(s.substring(i, i + 1), 1);
                else currChar.put(s.substring(i, i + 1), currChar.get(s.substring(i, i + 1)) + 1);
                gw.setSquareColor(gw.getCurrentRow(), i, gw.CORRECT_COLOR);
                gw.setKeyColor(s.substring(i, i + 1), gw.CORRECT_COLOR);
            }
        }
        for (int i = 0; i < 5; i++) { // Then color in the ones that are not in the correct position
            if (correctWord.contains(s.substring(i, i + 1)) && correctWord.charAt(i) != s.charAt(i) && !gw.getSquareColor(gw.getCurrentRow(), i).equals(gw.CORRECT_COLOR)) {
                if (!currChar.containsKey(s.substring(i, i + 1))) currChar.put(s.substring(i, i + 1), 1);
                else currChar.put(s.substring(i, i + 1), currChar.get(s.substring(i, i + 1)) + 1);
                if (currChar.get(s.substring(i, i + 1)) <= answerCharCount.get(s.substring(i, i + 1))) {
                    gw.setSquareColor(gw.getCurrentRow(), i, gw.PRESENT_COLOR);
                    if (!gw.getKeyColor(s.substring(i, i + 1)).equals(gw.CORRECT_COLOR))
                        gw.setKeyColor(s.substring(i, i + 1), gw.PRESENT_COLOR);
                }
            }
        }
        for (int i = 0; i < 5; i++) { // Then color in any remaining ones as gray
            if (gw.getSquareColor(gw.getCurrentRow(), i).equals(Color.WHITE)) { // If it isn't colored in already then it's incorrect
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
