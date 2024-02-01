/**
 * Creates a word chosen from the Dictionary class, with the along with the word
 * specific methods
 * 
 * Date: 4/30/2023
 * 
 * @author Connor Kippes, Luke Laurie, Sean Eddy, Zachary Hansen
 */
package src.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Word implements Serializable {
	private Dictionary wordDict;
	private LocalDate curDate;
	private HashMap<ArrayList<Integer>, ArrayList<String>> curWord;
	private static Word instance;
	private int colSize;

	// singleton design pattern
	public synchronized static Word getInstance(int colSize) {
		if (instance == null) {
			instance = new Word(colSize);
		} else {
			instance.setColSize(colSize);
		}
		// determine if new word is needed
		return instance;
	}

	// private constructor so other classes don't use it
	@SuppressWarnings("unchecked")
	private Word(int colSize) {
		this.colSize = colSize;
		curDate = LocalDate.now();
		wordDict = new Dictionary();
		readWord();
		readDate();
	}

	public String getCurDate() {
		return curDate.toString();
	}

	public ArrayList<String> getCurWord(int colSize, int rowSize) {
		// determines if a new word is needed
		readDate();
		updateTime();
		ArrayList<Integer> keyArrayList = new ArrayList<>(Arrays.asList(rowSize, colSize));
		return curWord.get(keyArrayList);
	}

	/**
	 * updates the random word and current times if the date if off
	 */
	public void updateTime() {
		if (checkNewDate()) {
			curDate = LocalDate.now();
			createWordMapping();
			saveDate();
			saveWord();
		}
	}

	/**
	 * This method checks if the time in either the word or the Dictionary object
	 * are off
	 * 
	 * @return a boolean operator
	 */
	public boolean checkNewDate() {
		LocalDate currentTime = LocalDate.now();
		return !curDate.equals(currentTime);
	}

	/**
	 * This method sets values for the attributes curDate and curWord
	 * @param theDate a LocatDate object
	 * @param theWord a HashMap of String ArrayLists
	 */
	public void setCustomValues(LocalDate theDate, HashMap<ArrayList<Integer>, ArrayList<String>> theWord) {
		curDate = theDate;
		curWord = theWord;
	}
	
	/**
	 * This method resets the class attribute currDate with the current time
	 */
	public void setNewDay() {
		curDate = LocalDate.now();
	}

	/**
	 * This method sets a new word for the in the dictionary
	 * @param newWord a String ArrayList object
	 */
	public void setNewWord(ArrayList<String> newWord) {
		createWordMapping(); 
		ArrayList<Integer> keyArrayList = new ArrayList<>(Arrays.asList(5, newWord.size()));
		curWord.put(keyArrayList, newWord);
	}
	
	/**
	 * This method sets a random new word for the in the dictionary
	 * @param newWord a String ArrayList object
	 */
	public void setRandomWord(int rowSize) {
		createWordMapping(); 
		ArrayList<Integer> keyArrayList = new ArrayList<>(Arrays.asList(rowSize, colSize));
		curWord.put(keyArrayList, wordDict.getRandomWord(colSize));
	}

	/**
	 * This method resets the class attribute currDate with yesterday's date
	 */
	public void setPastDate() {
		curDate = LocalDate.now().minusDays(1);
	}

	/**
	 * This method sets the the value for the column size class atrribute
	 * @param colSize an integer
	 */
	public void setColSize(int colSize) {
		if (colSize != this.colSize) {
			this.colSize = colSize;
		}
	}

	/**
	 * This method returns the column size of the word
	 * @return an integer
	 */
	public int getColSize() {
		return colSize;
	}

	/**
	 * This method saves the data in the Dictionary object to the serializable file
	 */
	
	public void saveDate() {
		try {
			FileOutputStream fileOut = new FileOutputStream("date.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(curDate);
			out.close();
			fileOut.close();
		} catch (Exception e) {
		}
	}

	/**
	 * This method reads the information from the date.ser serialized file
	 */
	public void readDate() {
		File file = new File("date.ser");
		if (file.exists()) {
			try {
				FileInputStream fileIn = new FileInputStream("date.ser");
				ObjectInputStream in = new ObjectInputStream(fileIn);
				curDate = (LocalDate) in.readObject();
				// checks if value in file
				if (curDate == null) {
					curDate = LocalDate.now();
					saveDate();
				}
				in.close();
				fileIn.close();
			} catch (Exception e) {
			}
		} else {
			// saves the file if not yet created
			curDate = LocalDate.now();
			saveDate();
		}
	}

	/**
	 * This method saves the HashMap of ArrayLists of Strings to the word.ser Serialized file
	 */
	public void saveWord() {
		try {
			FileOutputStream fileOut = new FileOutputStream("word.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(curWord);
			out.close();
			fileOut.close();
		} catch (Exception e) {
		}
	}
	
	
	/**
	 * This method retrieves the HashMap of ArrayLists of Strings from the word.ser Serialized file
	 */
	public void readWord() {
		File file = new File("word.ser");
		if (file.exists()) {
			try {
				FileInputStream fileIn = new FileInputStream("word.ser");
				ObjectInputStream in = new ObjectInputStream(fileIn);
				curWord = (HashMap<ArrayList<Integer>, ArrayList<String>>) in.readObject();
				// checks if value in file
				if (curWord == null) {
					// gets all the possible word sizes
					createWordMapping();
					saveWord();
				}
				in.close();
				fileIn.close();
			} catch (Exception e) {
			}
		} else {
			// gets all the possible word sizes
			createWordMapping();
			saveWord();
		}
	}

	/**
	 * This method returns the dictionary for the word
	 * @return a HashMap full of String ArrayLists
	 */
	public Dictionary getDict() {
		return wordDict;
	}

	/**
	 * This method creates a new word mapping
	 */
	private void createWordMapping() {
		curWord = new HashMap<>();
		// adds every possible pair of sizes
		for (int col = 3; col <= 7; col++) {
			for (int row = 3; row <= 7; row++) {
				ArrayList<Integer> curPair = new ArrayList<>();
				curPair.add(row);
				curPair.add(col);
				if (!curWord.containsKey(curPair)) {
					ArrayList<String> randomArrayListWord = wordDict.getRandomWord(col);
					// adds to the hashmap of all guesses
					curWord.put(curPair, randomArrayListWord);
				}
			}
		}
	}

}