/**
 * This class creates a dictionary of usable words for the game Wordle
 * 
 * Date: 4/30/2023
 * 
 * @author Connor Kippes, Luke Laurie, Sean Eddy, Zachary Hansen
 */

package src.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

public class Dictionary implements Serializable {
	private HashMap<Integer, HashSet<ArrayList<String>>> possibleWords = new HashMap<>();
	private HashMap<Integer, HashSet<ArrayList<String>>> pickableWords = new HashMap<>();

	public Dictionary() {
		readFile("possible");
		readFile("pickable");
	}

	/**
	 * This function chooses a random word of a given in the HashMap
	 * @param WordSize an integer of the size of the word to return
	 * @return An ArrayList of Strings representing the word
	 */
	public ArrayList<String> getRandomWord(int WordSize) { 
		Random random = new Random();
		int size = pickableWords.get(WordSize).size();
		int item = random.nextInt(size);
		// return pickableWords.get(item);
		int i = 0;
		for (ArrayList<String> word : pickableWords.get(WordSize)) {
			if (i == item) {
				return word;
			}
			i++;
		}
		return null;
	}

	/**
	 * This method stores the information from the correct serialized file
	 * @param type a String denoting the correct file
	 */
	public void readFile(String type) {
		// reads in file for all the sizes
		for (int col = 3; col <= 7; col++) {
			// converts all words in file to hashset 
			HashSet<ArrayList<String>> fileReading = creatHashFromFile(col, type);
			if (type == "possible" && fileReading != null) {
				possibleWords.put(col, fileReading);
			} else if (fileReading != null) {
				pickableWords.put(col, fileReading);
			} 
		}
	}

	/**
	 * This method determines if a word is in the dictionary
	 * @param word an ArrayList of Strings of the word being determined
	 * @return a boolean operator
	 */
	public boolean isValidWord(ArrayList<String> word) {
		boolean wordExists = possibleWords.get(word.size()).contains(word);
		return wordExists;
	}

	/**
	 * This method opens a serialized file and stores the information
	 * @param colSize an integer to determine the correct serialized file
	 * @param type A String to determine the correct serialized file
	 * @return A HashSet of ArrayLists of Strings
	 */
	private HashSet<ArrayList<String>> creatHashFromFile(int colSize, String type) {
		HashSet<ArrayList<String>> wordsToRead = new HashSet<>();
		Scanner file;
		try {
			file = new Scanner(new File("src/text_files/" + type + "-" + colSize + "-letter-words.txt"));
			// reads each possible word from the file
			while (file.hasNextLine()) { 
				String word = file.nextLine();
				// creates array of each of the letters in the word
				ArrayList<String> letters = new ArrayList<>();
				for (int i = 0; i < word.length(); i++) {
					letters.add(word.substring(i, i + 1).toLowerCase());
				}
				wordsToRead.add(letters);
			}
			file.close();
			return wordsToRead;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(type + " " + colSize + " letter words file not found");
			return null;
		}
	}
}