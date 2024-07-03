/**
 * This class is for guessing a word and relaying the information about the guess
 * 
 * Date: 4/30/2023
 * 
 * @author Connor Kippes, Luke Laurie, Sean Eddy Zachary Hansen
 */

package src.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import src.view.WordleGUI;

public class Guess implements Serializable{
	private ArrayList<String> curGuess = new ArrayList<>();
	ArrayList<String> tempGuess = new ArrayList<>();
	private ArrayList<String> curGuessColors = new ArrayList<>();
	private String guessColors[] = { "Green", "Yellow", "Dark Gray" };
	private int guessNumber = 0;
	private Word word;
	private int colSize = 5;
	
	/**
	 * This constructor initializes the local Word class and wordle game column size
	 * 
	 * @param colSize integer column size of the current wordle game (word length)
	 */
	public Guess(int colSize)
	{
		this.colSize = colSize;
		word = Word.getInstance(this.colSize);
	}

	/**
	 * This method atempts to make a guess with the inputed arrayList
	 * 
	 * @param an ArrayList of the attempted guess
	 * @return An Arraylist of the color values of the corresponding letter,
	 *         otherwise null
	 */
	public ArrayList<String> makeGuess(ArrayList<String> guess, int size) {
		if (canGuess(guess)) {
			//prevGuess = curGuess;
			curGuess = guess;
			ArrayList<String> wordAsList = word.getCurWord(colSize, size);
			String[] colorList = new String[colSize];
			ArrayList<String> correctLetters = new ArrayList<>();
			ArrayList<String> wrongPlaceLetters = new ArrayList<>();
			for (int i = 0; i < colSize; i++) {
				if (wordAsList.get(i).equalsIgnoreCase(curGuess.get(i))) {
					colorList[i] = guessColors[0];
					correctLetters.add(wordAsList.get(i));
				} else if (wordAsList.contains(curGuess.get(i))) {
					colorList[i] = guessColors[1];
					wrongPlaceLetters.add(wordAsList.get(i));
				} else {
					colorList[i] = guessColors[2];
				}
			}

			// map to keep track of number of times a wrong place letter was visited
			HashMap<String, Integer> counter = new HashMap<String, Integer>();
			// second loop to fix wrong place letters if there were too many
			for(int i = 0; i < colSize; i++)
			{
				if (colorList[i].equals(guessColors[1])) {
					int letterFreqInCorrectWord = Collections.frequency(wordAsList, curGuess.get(i));
					int letterFreqInGuessedWord = Collections.frequency(curGuess, curGuess.get(i));
					int freqDifference = letterFreqInGuessedWord - letterFreqInCorrectWord;
					int letterFreqInCorrectLetters = Collections.frequency(correctLetters, curGuess.get(i));
					if(freqDifference != 0)
					{
						if(!counter.containsKey(curGuess.get(i)))
						{
							counter.put(curGuess.get(i), 0);
						}
						if (letterFreqInCorrectWord <= (letterFreqInCorrectLetters + counter.get(curGuess.get(i)))) {
							colorList[i] = guessColors[2];
						}
						else
						{
							counter.put(curGuess.get(i), counter.get(curGuess.get(i)) + 1);
						}
					}
				}
			}
			
			guessNumber++;
			ArrayList<String> colorListAsArray = new ArrayList<String>(Arrays.asList(colorList));
			curGuessColors = colorListAsArray;
			return curGuessColors;
		}
		return null;
	}

	/**
	 * This method checks if the guess is valid (5 letters and contained in the
	 * dictionary)
	 * 
	 * @return True if the guess is valid
	 */
	public boolean canGuess(ArrayList<String> guess) {
		if (word.getDict().isValidWord(guess)) {
			return true;
		}
		return false;
	}
	
	/**
	 * This method changes the guess colors to contrast mode
	 */
	public void setDarkMode() {
		guessColors[2] = "dm Dark Gray";
	}

	/**
	 * This method changes the guess colors back from dark mode
	 */
	public void setNonDarkMode() {
		guessColors[2] = "Dark Gray";
	}

	/**
	 * This method changes the guess colors to contrast mode
	 */
	public void setContrastMode() {
		guessColors[0] = "Orange";
		guessColors[1] = "Blue";
	}
	
	/**
	 * This method changes the guess colors back from contrast mode
	 */
	public void setNonContrastMode() {
		guessColors[0] = "Green";
		guessColors[1] = "Yellow";
	}

	/**
	 * This method adds a letter to the current guess
	 * 
	 * @param String to be added to the guess
	 * 
	 * @return ArrayList of strings of letters of the current word guessed
	 */
	public void addLetter(String letter) {
		if (curGuess.size() < colSize) {
			curGuess.add(letter);
		}
	}

	/**
	 * This method adds a letter to the current guess
	 * 
	 * @return ArrayList of strings of letters of the current word guessed
	 */
	public void removeLetter() {
		if (curGuess.size() > 0) {
			curGuess.remove(curGuess.size() - 1);
		}
	}

	/**
	 * This the word to a custom word
	 * 
	 * @param Word to be swapped to.
	 * 
	 * @return ArrayList of strings of letters of the current word guessed
	 */
	public void setWord(Word curWord) {
		word = curWord;
	}
	
	/**
	 * This method sets the column size (word length)
	 * 
	 * @param colSize int column size
	 */
	public void setColSize(int colSize)
	{
		this.colSize = colSize;
	}

	/**
	 * This method returns the number of guesses
	 * 
	 * @return integer guess number
	 */
	public int getGuessNumber() {
		return guessNumber;
	}
	
	/**
	 * This method returns the current guess used for testing
	 * 
	 * @return ArrayList of strings of letters of the current word guessed
	 */
	public ArrayList<String> getCurGuess() {
		return curGuess;
	}
	
	/**
	 * This method returns the guess colors of the last word guessed
	 * 
	 * @return Array List of the current guess colors
	 */
	public ArrayList<String> getCurGuessColors()
	{
		return curGuessColors;
	}
	
	public Word getWord()
	{
		return word;
	}
}