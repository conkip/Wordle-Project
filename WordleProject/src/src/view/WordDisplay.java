/**
 * This will create the WordDisplay which will display a single 
 * row on the board, where the user can type in letters to enter 
 * information into this row.
 * Author: Luke Laurie, Connor Kippes, Sean Eddy Zachary Hansen
 * Date: 4/25/2023
 */
package src.view;

import java.util.ArrayList;

import javafx.scene.input.KeyEvent;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import src.model.WordleUser;

public class WordDisplay extends HBox {
	private int currentCol;
	private ArrayList<Letter> letters;
	private int colSize = 5;

	/**
	 * This will initialize an empty word given its size.
	 * 
	 * @param colSize is a String representing the size of the column.
	 */
	public WordDisplay(int colSize) {
		this.colSize = colSize;
		initializeWord();
	}
	
	/**
	 * This will initialize the word given a predefined word.
	 * 
	 * @param finishedGuess is a already completed guess by the user.
	 */
	public WordDisplay(ArrayList<String> finishedGuess) {
		this.colSize = finishedGuess.size();
		initializeWord(); 
		for (int i = 0; i < colSize; i++) {
			String curLetter = finishedGuess.get(i); 
			if (!curLetter.equals(" ")) {
				addLetter(finishedGuess.get(i).toUpperCase());
			}
		}
	}
	
	/**
	 * Initializes the word to be empty and sets the correct spacing
	 */
	public void initializeWord() {
		currentCol = -1;
		// Initializes word to have five empty letters
		letters = new ArrayList<>();
		for (int i = 0; i < colSize; i++) {
			letters.add(new Letter(" ", false));
		}
		setWordChildren();
		// set correct spacing
		this.setSpacing(4.87);
		this.setAlignment(Pos.CENTER);
	}

	/**
	 * Finds the letter that was most recently typed by the user.
	 * 
	 * @return the letter found at the position.
	 */
	public Letter getCurrentLetter() {
		if (currentCol == -1) {
			return null;
		}
		return letters.get(currentCol);
	}

	/**
	 * Finds the letter at a specified index.
	 * 
	 * @param index is a int representing the location to search for.
	 * 
	 * @return the letter that was found.
	 */
	public Letter getLetter(int index) {
		for (int i = 0; i < colSize; i++) {
			// checks if correct letter
			if (i == index) {
				return letters.get(i);
			}
		}
		// not found
		return null;
	}

	/**
	 * @return the currentCol
	 */
	public int getCurrentCol() {
		return currentCol;
	}

	/**
	 * @return the letters
	 */
	public ArrayList<Letter> getLetters() {
		return letters;
	}

	/**
	 * Finds the letter at a specified index.
	 * 
	 * @param index is a int representing the location to search for.
	 * 
	 * @return the letter that was found.
	 */
	public ArrayList<String> getWord() {
		// converts all letters to a string, and adds to list
		ArrayList<String> word = new ArrayList<>();
		for (Letter curLetter : letters) {
			word.add(curLetter.getText().toLowerCase());
		}
		return word;
	}

	/**
	 * This will add a new lwtter to the word and update its styling.
	 * 
	 * @param letterInput is a string representing the letter to add.
	 * 
	 */
	public void addLetter(String letterInput) {
		// checks if letter should be removed
		if (letterInput == "backspace") {
			if (currentCol != -1) {
				Letter letter = letters.get(currentCol);
				letter.setText(" ");
				if (letter.isDarkMode()) {
					letter.setBoardButtonStyle(letter.getTextColor(), letter.getBackgroundColor(), "dm Dark Gray");
				} else {
					letter.setBoardButtonStyle(letter.getTextColor(), letter.getBackgroundColor(), "Gray");
				}
				currentCol--;
			}
		} else if (currentCol < colSize - 1 && letterInput.length() == 1) {
			// sets the value of the letter
			currentCol++;
			Letter letter = letters.get(currentCol);
			letter.setText(letterInput);
			if (letter.isDarkMode()) {
				letter.setBoardButtonStyle(letter.getTextColor(), letter.getBackgroundColor(), "dm Border Gray");
			} else {
				letter.setBoardButtonStyle(letter.getTextColor(), letter.getBackgroundColor(), "Border Gray");
			}
		}

		setWordChildren();
	}

	/**
	 * Sets the word to be the values in the letters array
	 */
	private void setWordChildren() {
		// clear the children
		this.getChildren().clear();
		for (Letter curLetter : letters) {
			this.getChildren().add(curLetter);
		}
	}

	/**
	 * Sets the column size.
	 * 
	 * @param colSize is a int representing the number of total letters.
	 * 
	 */
	public void setColSize(int colSize) {
		this.colSize = colSize;
	}
	
	/**
	 * This will take in a word and set its values to its correct respective 
	 * letter.
	 * 
	 * @param word is the string to be set as the letters.
	 * 
	 */
	public void setWord(String word) {
		// sets each letter in the word to its correct location
		for (int i = 0; i < word.length(); i++) {
			Letter letter = letters.get(i);
			letter.setText(Character.toString(word.charAt(i)));
			if (letter.isDarkMode()) {
				letter.setBoardButtonStyle(letter.getTextColor(), letter.getBackgroundColor(), "dm Border Gray");
			} else {
				letter.setBoardButtonStyle(letter.getTextColor(), letter.getBackgroundColor(), "Border Gray");
			}
		}
	}

}