/**
 * Creates an object representing a Wordle user, storing important user attributes.
 * 
 * Date 5/1/2023
 * 
 * @author Sean Eddy
 */

package src.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import org.hamcrest.core.Is;

import javafx.scene.chart.PieChart.Data;

@SuppressWarnings("serial")
public class WordleUser implements Serializable {
	private int gamesPlayed;
	private int winCount;
	private int lossCount;
	private double winRate;
	private int curStreak;
	private int maxStreak;
	private HashMap<ArrayList<Integer>, HashMap<Integer, Integer>> guessDistribution;
	private HashMap<ArrayList<Integer>, ArrayList<ArrayList<String>>> guessChoices;
	private String email;
	private String password;
	private LocalDate curDate;
	private boolean isDarkMode;
	private boolean isContrastMode;

	/**
	 * This will initialize the WordleUser object with an email and password.
	 * 
	 * @param email    is a string representing user email
	 * @param password is a string representing user password
	 */
	public WordleUser(String email, String password) {
		gamesPlayed = 0;
		winCount = 0;
		lossCount = 0;
		winRate = 0.0;
		curStreak = 0;
		maxStreak = 0;

		guessChoices = new HashMap<>();
		// initializes the guesses and their distributions
		initializeInfo(false);
		initializeInfo(true);
		// information needed for login system
		this.email = email;
		this.password = password;
		curDate = LocalDate.now();
		isDarkMode = false;
		isContrastMode = false;
	}

	/**
	 * This will return the user's email
	 * 
	 * @return user email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * This will return the user's password
	 * 
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * This will update the appropriate user attributes when a game is won or lost
	 * 
	 * @param won     is a boolean representing whether the game was won
	 * @param rowSize is an int representing the number of rows in the game
	 * @param colSize is an int representing the number of columns in the game
	 */
	public void reportGame(boolean won, int rowSize, int colSize) {
		gamesPlayed++;
		if (won) {
			winCount++;
			curStreak++;
			maxStreak = curStreak > maxStreak ? curStreak : maxStreak;
		} else {
			lossCount++;
			curStreak = 0;
		}
		winRate = (double) winCount / gamesPlayed;
		if (won) {
			// finds the map to update
			ArrayList<Integer> keyArrayList = new ArrayList<>(Arrays.asList(rowSize, colSize));
			HashMap<Integer, Integer> mapToUpdate = guessDistribution.get(keyArrayList);
			int guessSize = findSize(rowSize, colSize);
			mapToUpdate.put(guessSize, mapToUpdate.get(guessSize) + 1);
		}
	}

	/**
	 * This returns the number of games played by the user
	 * 
	 * @return number of games played
	 */
	public int getGamesPlayed() {
		return gamesPlayed;
	}

	/**
	 * This returns the number of games won by the user
	 * 
	 * @return number of won games
	 */
	public int getWinCount() {
		return winCount;
	}

	/**
	 * This returns the number of games lost by the user
	 * 
	 * @return number of games lost
	 */
	public int getLossCount() {
		return lossCount;
	}

	/**
	 * This returns the proportion of games won by the user
	 * 
	 * @return proportion of games won
	 */
	public double getWinRate() {
		return winRate;
	}

	/**
	 * This returns the current streak (number of games won in a row)
	 * 
	 * @return current streak
	 */
	public int getCurStreak() {
		return curStreak;
	}

	/**
	 * This returns the maximum streak( number of games won in a row)
	 * 
	 * @return maximum streak
	 */
	public int getMaxStreak() {
		return maxStreak;
	}

	/**
	 * This returns the user's guest distribution
	 * 
	 * @param rowSize is an int representing the number of rows in the game
	 * @param colSize is an int representing the number of columns in the game
	 * 
	 * @return guess distribution
	 */
	public HashMap<Integer, Integer> getGuessDistribution(int rowSize, int colSize) {
		// create the key to determine which wordle size to look at
		ArrayList<Integer> keyArrayList = new ArrayList<>(Arrays.asList(rowSize, colSize));
		return guessDistribution.get(keyArrayList);
	}

	/**
	 * This returns the user's guesses
	 * 
	 * @param rowSize is an int representing the number of rows in the game
	 * @param colSize is an int representing the number of columns in the game
	 * 
	 * @return guesses
	 */
	public ArrayList<ArrayList<String>> getGuesses(int rowSize, int colSize) {
		// create the key to determine which wordle size to look at
		ArrayList<Integer> keyArrayList = new ArrayList<>(Arrays.asList(rowSize, colSize));
		return guessChoices.get(keyArrayList);
	}

//	public void setInitalGuesses() {
//		curDate = LocalDate.now();
//		guessChoices = new HashMap<>();
//		// adds every possible pair of sizes
//		for (int col = 3; col <= 7; col++) {
//			for (int row = 3; row <= 7; row++) {
//				ArrayList<Integer> curPair = new ArrayList<>();
//				curPair.add(row);
//				curPair.add(col);
//				if (!guessChoices.containsKey(curPair)) {
//					ArrayList<ArrayList<String>> emptyPair = createEmptyGuess(col, row);
//					// adds to the hashmap of all guesses
//					guessChoices.put(curPair, emptyPair);
//				}
//			}
//		}
//	}

	/**
	 * This sets guesses with allGuesses
	 * 
	 * @param allGuesses is an ArrayList of all guesses
	 */
	public void setGuesses(ArrayList<ArrayList<String>> allGuesses) {
		// finds the correctly sized pair
		int rowSize = allGuesses.size();
		int colSize = allGuesses.get(0).size();
		// updates the value
		ArrayList<Integer> keyArrayList = new ArrayList<>(Arrays.asList(rowSize, colSize));
		guessChoices.put(keyArrayList, allGuesses);
	}

	/**
	 * This returns the current date
	 * 
	 * @return current date
	 */
	public LocalDate getDate() {
		return curDate;
	}

	/**
	 * This finds the size of the board
	 * 
	 * @param rowSize is an int representing the number of rows in the game
	 * @param colSize is an int representing the number of columns in the game
	 * 
	 * @return row size
	 */
	private int findSize(int rowSize, int colSize) {
		ArrayList<Integer> keyArrayList = new ArrayList<>(Arrays.asList(rowSize, colSize));
		for (int i = 0; i < rowSize; i++) {
			if (guessChoices.get(keyArrayList).get(i).get(0).equals(" ")) {
				return i;
			}
		}
		// took all the guesses
		return rowSize;
	}

	/**
	 * This creates an empty guess
	 * 
	 * @param numberRows is an int representing the number of rows in the game
	 * @param numberCols is an int representing the number of columns in the game
	 * @return
	 */
	private ArrayList<ArrayList<String>> createEmptyGuess(int numberCols, int numberRows) {
		ArrayList<ArrayList<String>> tempGuess = new ArrayList<>();
		for (int i = 0; i < numberRows; i++) {
			ArrayList<String> curGuess = new ArrayList<>();
			// add the empty letters
			for (int j = 0; j < numberCols; j++) {
				curGuess.add(" ");
			}
			tempGuess.add(curGuess);
		}
		return tempGuess;
	}

	/**
	 * This initializes date and guess objects
	 * 
	 * @param isGuesses is a boolean representing whether there are guesses
	 */
	public void initializeInfo(boolean isGuesses) {
		if (isGuesses) {
			curDate = LocalDate.now();
			guessChoices = new HashMap<>();
		} else {
			guessDistribution = new HashMap<>();
		}
		// adds every possible pair of sizes
		for (int col = 3; col <= 7; col++) {
			for (int row = 3; row <= 7; row++) {
				ArrayList<Integer> curPair = new ArrayList<>();
				curPair.add(row);
				curPair.add(col);
				// determines which map to add to
				if (isGuesses) {
					if (!guessChoices.containsKey(curPair)) {
						ArrayList<ArrayList<String>> emptyPair = createEmptyGuess(col, row);
						// adds to the hashmap of all guesses
						guessChoices.put(curPair, emptyPair);
					}
				} else {
					if (!guessDistribution.containsKey(curPair)) {
						HashMap<Integer, Integer> emptyPair = new HashMap<Integer, Integer>();
						for (int guessCount = 1; guessCount <= row; guessCount++) {
							emptyPair.put(guessCount, 0);
						}
						// adds to the hashmap to remember distribution of guesses
						guessDistribution.put(curPair, emptyPair);
					}
				}
			}
		}
	}

	/**
	 * This returns dark mode
	 * 
	 * @return the isDarkMode
	 */
	public boolean isDarkMode() {
		return isDarkMode;
	}

	/**
	 * This sets dark mode
	 * 
	 * @param isDarkMode the isDarkMode to set
	 */
	public void setDarkMode(boolean isDarkMode) {
		this.isDarkMode = isDarkMode;
	}

	/**
	 * This returns contrast mode
	 * 
	 * @return the isContrastMode
	 */
	public boolean isContrastMode() {
		return isContrastMode;
	}

	/**
	 * This sets contrast mode
	 * 
	 * @param isContrastMode the isContrastMode to set
	 */
	public void setContrastMode(boolean isContrastMode) {
		this.isContrastMode = isContrastMode;
	}

}