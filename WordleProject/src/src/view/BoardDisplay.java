/**
 * This will create the board which is a container for all of 
 * the word class. This allows for the user to enter words and 
 * this class will then interact with the model, feeding it 
 * the input from the user
 * 
 * Date: 4/25/2023
 * 
 * @author Luke Laurie, Connor Kippes, Sean Eddy Zachary Hansen
 */
package src.view;

import java.io.File;
import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import src.model.Guess;
import src.model.Word;
import src.model.WordleUser;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class BoardDisplay extends VBox {
	private int curRow;
	private ArrayList<WordDisplay> guesses;
	private Guess wordComparison;
	private ArrayList<String> curGuessedWord;
	private boolean isWon;
	private boolean isHardMode = false;
	private boolean isDarkMode = false;
	private int colSize = 5;
	private int rowSize = 6;
	private WordleGUI gui;
	private WordleUser curUser;

	/**
	 * This will initialize the board with its correct values.
	 * 
	 * @param colSize is a int representing the size of the column.
	 * @param rowSize is a int representing the number of rows.
	 * @param gui     is the main gui for the game.
	 */
	public BoardDisplay(int colSize, int rowSize, WordleGUI gui) {
		this.colSize = colSize;
		this.rowSize = rowSize;
		this.gui = gui;
		curUser = gui.getUser();
		// set the spacing
		this.setSpacing(4.87);
		setUserData();
	}

	/**
	 * This will add a letter to its correct respective location on the board.
	 * 
	 * @param keyPressed      is a Keycode representing the key pressed by the user.
	 * @param buttonInput     is a String representing what key the user clicked.
	 * @param keyboardDisplay is the keyboard which the user can click letters on.
	 */
	public void addLetter(KeyCode keyPressed, String buttonInput, KeyboardDisplay keyboardDisplay) {
		// checks that all guesses have not been used and game continuing
		String gameCondition = gameOver();
		if (curRow < rowSize && gameCondition.equals("in progress")) {
			WordDisplay curWord = guesses.get(curRow);
			if (keyPressed == KeyCode.ENTER || buttonInput.equals("ENTER")) {
				int tempRow = curRow;
				boolean couldEnter = enterWord(curWord);
				// updates keyboardColors after word is done flipping and was a valid word
				if (couldEnter) {
					delay(2020, () -> updateKeyColors(keyboardDisplay, 0));
				}
			} else if (keyPressed == KeyCode.BACK_SPACE || buttonInput.equals("BACKSPACE")) {
				curWord.addLetter("backspace");
			} else if (!buttonInput.equals("")) {
				curWord.addLetter(buttonInput);
			} else if (Character.isLetter(keyPressed.getChar().charAt(0))) {
				boolean rowNotFull = true;
				if (curWord.getCurrentCol() == colSize - 1) {
					rowNotFull = false;
				}
				curWord.addLetter(keyPressed.toString());
				if (rowNotFull) {
					popOutLetter(curWord, 0);
				}
			}
		}
	}

	/**
	 * This updates the colors displayed on the keyboard.
	 * 
	 * @param keyboardDisplay is the keyboard which the user can click letters on.
	 * @param i               is a int representing if the keyboard should be
	 *                        updated or not.
	 */
	public void updateKeyColors(KeyboardDisplay keyboardDisplay, int i) {
		if (i == 0) {
			keyboardDisplay.updateKeyColors();
		}
	}

	/**
	 * This sets each color in the board to be in its dark mode.
	 */
	public void setDarkMode() {
		for (WordDisplay wordDisplay : guesses) {
			for (Letter letter : wordDisplay.getLetters()) {
				letter.setDarkMode();
			}
		}
		wordComparison.setDarkMode();
		isDarkMode = true;
	}

	/**
	 * This sets each color in the board to be in its normal mode.
	 */
	public void setNonDarkMode() {
		for (WordDisplay wordDisplay : guesses) {
			for (Letter letter : wordDisplay.getLetters()) {
				letter.setNonDarkMode();
			}
		}
		wordComparison.setNonDarkMode();
		isDarkMode = false;
	}

	/**
	 * This sets each color in the board to be in its contrast mode.
	 */
	public void setContrastMode() {
		for (WordDisplay wordDisplay : guesses) {
			for (Letter letter : wordDisplay.getLetters()) {
				letter.setContrastMode();
			}
		}
		wordComparison.setContrastMode();
	}

	/**
	 * This sets each color in the board to be in its not contrasted mode.
	 */
	public void setNonContrastMode() {
		for (WordDisplay wordDisplay : guesses) {
			for (Letter letter : wordDisplay.getLetters()) {
				letter.setNonContrastMode();
			}
		}
		wordComparison.setNonContrastMode();
	}

	/**
	 * This displays all of the correct values onto the board.
	 */
	public void displayChildren() {
		// clear the children
		this.getChildren().clear();
		// adds all the words
		for (WordDisplay curWord : guesses) {
			this.getChildren().add(curWord);
		}
	}

	/**
	 * This determines if a given word can be entered as a valid guess.
	 * 
	 * @param curWord is the word to try and enter as a guess.
	 */
	private boolean enterWord(WordDisplay curWord) {
		if (curWord.getCurrentCol() == colSize - 1) {
			// moves to next word
			if (curRow < rowSize) {
				return compareGuess(curWord, true);
			}
		} else {
			popUpErrorMessage("Not enough letters", gui.getMainCenterPane());

			shakeRow(curWord, 0);
		}
		return false;
	}

	/**
	 * This will get all of the colorings associated with a guess.
	 * 
	 * @param curWord       is the word to try and enter as a guess.
	 * @param shouldAnimate determines weather the current guesses was read in from
	 *                      the serialization.
	 */
	private boolean compareGuess(WordDisplay curWord, boolean shouldAnimate) {
		curGuessedWord = curWord.getWord();
		boolean canGuess = wordComparison.canGuess(curGuessedWord);
		// checks if word exists in the dictionary
		if (!canGuess) {
			popUpErrorMessage("Not in word list", gui.getMainCenterPane());

			shakeRow(curWord, 0);
			return false;
		}
		Guess tempGuess = new Guess(colSize);
		tempGuess.makeGuess(curGuessedWord, rowSize);
		if (isHardMode) {
			ArrayList<String> prevGuess = wordComparison.getCurGuess();
			ArrayList<String> prevGuessColors = wordComparison.getCurGuessColors();
			ArrayList<String> curGuessColors = tempGuess.getCurGuessColors();
			// gets the correct colorings of the guess
			if (prevGuess.size() != 0) {
				for (int i = 0; i < colSize; i++) {
					if (prevGuessColors.get(i).equals("Green") || prevGuessColors.get(i).equals("Orange")) {
						if (!curGuessColors.get(i).equals("Green") && !curGuessColors.get(i).equals("Orange")) {
							String orderNum = "";
							if (i == 0)
								orderNum = "1st";
							else if (i == 1)
								orderNum = "2nd";
							else if (i == 2)
								orderNum = "3rd";
							else
								orderNum = i + 1 + "th";
							popUpErrorMessage(orderNum + " letter must be " + prevGuess.get(i).toUpperCase(),
									gui.getMainCenterPane());
							shakeRow(curWord, 0);
							return false;
						}
					} else if (prevGuessColors.get(i).equals("Yellow") || prevGuessColors.get(i).equals("Blue")) {
						if (!curGuessedWord.contains(prevGuess.get(i))) {
							popUpErrorMessage("Guess must contain " + prevGuess.get(i).toUpperCase(),
									gui.getMainCenterPane());
							shakeRow(curWord, 0);
							return false;
						}
					}
				}
			}
		}
		ArrayList<String> placements = wordComparison.makeGuess(curGuessedWord, rowSize);
		isWon = true;
		flipLetter(0, curWord, placements, shouldAnimate);
		// checks if the game has been completed
		String gameCondition = gameOver();
		if (!gameCondition.equals("in progress") && shouldAnimate) {
			// reports game as a win or loss
			updateUserGuesses();
			if (gameCondition.equals("won")) {
				curUser.reportGame(true, rowSize, colSize);
			} else {
				curUser.reportGame(false, rowSize, colSize);
			}
		}
		return true;
	}

	/**
	 * This will run the animation that flips a letter.
	 * 
	 * @param i             is the int representing which letter to flip.
	 * @param curWord       is the word that should be animated.
	 * @param shouldAnimate determines weather the current guesses was read in from
	 *                      the serialization.
	 * @param shouldAnimate determines weather the current guesses was read in from
	 *                      the serialization.
	 */
	private void flipLetter(int i, WordDisplay curWord, ArrayList<String> placements, boolean animation) {
		Letter curLetter = curWord.getLetter(i);
		if (i == 0) {
			curRow++;
		}

		if (animation) {
			curLetter.getFlip().play();
		}

		curLetter.setKeyboardButtonStyle("White", placements.get(i));

		if (placements.get(i) != "Green") {
			isWon = false;
		}
		if (i == colSize - 1) {
			if (isWon) {
				curRow = rowSize;
				curRow++;
			}
		} else {
			// determines if animation needs to be delayed
			int delayTime = 0;
			if (animation) {
				delayTime = 390;
			}
			delay(delayTime, () -> flipLetter(i + 1, curWord, placements, animation));
		}
	}

	/**
	 * This will run the animation that pops the letter outwards.
	 * 
	 * @param curWord is the word that should be animated.
	 * @param i       is the int representing which letter to flip.
	 * 
	 */
	private void popOutLetter(WordDisplay curWord, int i) {
		Letter curLetter = curWord.getLetter(curWord.getCurrentCol());
		if (i == 0) {
			curLetter.setPrefSize(53, 53);
			delay(50, () -> popOutLetter(curWord, i + 1));
		} else {
			curLetter.setPrefSize(50, 50);
		}
	}

	/**
	 * This will run the animation that shakes all the letters in a word.
	 * 
	 * @param curWord is the word that should be animated.
	 * @param i       is the int representing which letter to flip.
	 * 
	 */
	private void shakeRow(WordDisplay curWord, int i) {
		if (i == 7) {
			curWord.setPadding(new Insets(0, 0, 0, 0));
		} else if (i % 2 == 0) {
			curWord.setPadding(new Insets(0, 7, 0, 0));
			delay(60, () -> shakeRow(curWord, i + 1));
		} else if (i % 2 == 1) {
			curWord.setPadding(new Insets(0, 0, 0, 7));
			delay(60, () -> shakeRow(curWord, i + 1));
		}
	}

	/**
	 * This will delay the time between animations.
	 * 
	 * @param millis is the time to delay between animations.
	 * @param continuation continues the sleeping thread.
	 */
	private static void delay(long millis, Runnable continuation) {
		Task<Void> sleeper = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				try {
					Thread.sleep(millis);
				} catch (InterruptedException e) {
				}
				return null;
			}
		};
		sleeper.setOnSucceeded(event -> continuation.run());
		new Thread(sleeper).start();
	}

	/**
	 * This will delay the time between animations.
	 * 
	 * @param msg is the text to display in the message.
	 * @param pane is what the message is displayed on.
	 */
	public void popUpErrorMessage(String msg, StackPane pane) {
		ErrorMessage errorMsg = new ErrorMessage(msg, isDarkMode);
		if (pane.getChildren().size() <= 1)
			;
		pane.getChildren().add(errorMsg);
		delay(1200, () -> removeErrorMessage(errorMsg, pane));
	}

	/**
	 * This will remove the current message.
	 * 
	 * @param errorMsg is what to remove.
	 * @param pane is what the message is displayed on.
	 */
	public void removeErrorMessage(ErrorMessage errorMsg, StackPane pane) {
		pane.getChildren().remove(errorMsg);
	}

	/**
	 * This will check if a game has been completed.
	 * 
	 * @return the current state of the game.
	 */
	public String gameOver() {
		
		// determines if won, lost or in progress
		if (curRow == rowSize) {
			Word wordClass = Word.getInstance(colSize);
			ArrayList<String> wordString = wordClass.getCurWord(colSize, WordleGUI.getRowSize());
			String word = "";
			for (String letter : wordString) {
				word += letter;
			}
			URI uri = new File("src/soundEffects/youLose.mp3").toURI();
			MediaPlayer player = new MediaPlayer(new Media(uri.toString()));
			player.play();
			ErrorMessage errorMsg = new ErrorMessage(word.toUpperCase(), isDarkMode);
			gui.getMainCenterPane().getChildren().add(errorMsg);
			errorMsg.setAlignment(Pos.TOP_CENTER);
			return "You lost, the correct word was " + word;
		} else {
			// checks if won or not
			ArrayList<String> comparisons = wordComparison.getCurGuessColors();
			if (comparisons.size() == 0) {
				return "in progress";
			}
			for (String guessColor : comparisons) {
				if (!guessColor.equals("Green") && !guessColor.equals("Orange")) {
					return "in progress";
				}
			}
			URI uri = new File("src/soundEffects/youWin.mp3").toURI();
			MediaPlayer player = new MediaPlayer(new Media(uri.toString()));
			player.play();
			popUpErrorMessage("Great", gui.getMainCenterPane());
			return "won";
		}
	}
	
	public boolean determineGameOver() {
		if (curRow == rowSize) {
			return true;
		} else {
			// checks if won or not
			ArrayList<String> comparisons = wordComparison.getCurGuessColors();
			if (comparisons.size() == 0) {
				return false;
			}
			for (String guessColor : comparisons) {
				if (!guessColor.equals("Green") && !guessColor.equals("Orange")) {
					return false;
				}
			}
			return true;
		}
	}

	/**
	 * Sets the game to hard mode.
	 * 
	 * @param isHardMode determines if hard mode or not.
	 */
	public void setHardMode(boolean isHardMode) {
		this.isHardMode = isHardMode;
	}

	/**
	 * Sets the correct column size for the board and all words.
	 * 
	 * @param colSize is the number of letters.
	 */
	public void setColSize(int colSize) {
		this.colSize = colSize;
		for (WordDisplay guess : guesses) {
			guess.setColSize(colSize);
		}
		wordComparison.setColSize(colSize);
	}

	/**
	 * Sets the correct row size for the board and all words.
	 * 
	 * @param rowSize is the number of words.
	 */
	public void setRowSize(int rowSize) {
		this.rowSize = rowSize;
	}

	/**
	 * Gets the current guess object.
	 * 
	 * @return the guess object.
	 */
	public Guess getGuessClass() {
		return wordComparison;
	}

	/**
	 * Gets the current row number.
	 * 
	 * @return the row number.
	 */
	public int getCurRow() {
		return curRow;
	}

	/**
	 * Initialized all the information from the board given data 
	 * from a wordleUser.
	 */
	public void setUserData() {
		wordComparison = new Guess(this.colSize);
		// determines if users guesses should be reset
		LocalDate currentTime = LocalDate.now();
		if (!currentTime.equals(curUser.getDate())) {
			curUser.initializeInfo(true);
		}
		ArrayList<ArrayList<String>> currentGuesses = curUser.getGuesses(rowSize, colSize);
		wordComparison.makeGuess(currentGuesses.get(0), rowSize);
		guesses = new ArrayList<>();
		curRow = 0;
		// sets the guesses to the initially guessed values
		for (int i = 0; i < rowSize; i++) {
			ArrayList<String> curWord = currentGuesses.get(i);
			// checks if word needs to be guessed
			if (!curWord.get(0).equals(" ")) {
				// enters the word
				WordDisplay inputtedWord = new WordDisplay(curWord);
				guesses.add(inputtedWord);
				compareGuess(inputtedWord, false);
			} else {
				WordDisplay inputtedWord = new WordDisplay(colSize);
				guesses.add(inputtedWord);
			}
		}
		displayChildren();
	}

	/**
	 * Updates all of the guesses obtained from the guesses 
	 * saved in the user.
	 */
	public void updateUserGuesses() {
		// converts the words into the needed format
		ArrayList<ArrayList<String>> wordsGuessed = new ArrayList<>();
		for (WordDisplay curWord : guesses) {
			wordsGuessed.add(curWord.getWord());
		}
		curUser.setGuesses(wordsGuessed);
	}
	
	/**
	 * Updates all of the information on the key board from 
	 * the words saved in the user object.
	 */
	public void updateKeyboard(KeyboardDisplay keyboard) {
		if (gui.getButton() != null && isDarkMode) {
			gui.setDarkMode();
		} else if (gui.getButton() != null && !isDarkMode) {
			gui.setNonDarkMode();
		}
		keyboard.resetColors(isDarkMode);
		for (WordDisplay curWord : guesses) {
			ArrayList<String> wordToGuess = curWord.getWord();
			if (!wordToGuess.get(0).equals(" ")) {
				// checks if word is needed to be guessed on keyboard
				wordComparison.makeGuess(wordToGuess, rowSize);
				updateKeyColors(keyboard, 0);
			}
		}
		// updates the colors
		if (gui.getContrastMode() && isDarkMode) {
			keyboard.setContrastMode();
			setDarkMode();
		} else if (!gui.getContrastMode() && isDarkMode) {
			keyboard.setDarkMode();
			setDarkMode();
		}
	}

	/**
	 * Sets the current user.
	 * 
	 * @param the object representing the wordleUser.
	 */
	public void setUser(WordleUser wordleUser) {
		curUser = wordleUser;
	}
	
	/**
	 * Sets the correct colors.
	 * 
	 * @param the object representing the wordleUser.
	 */
	public void setColors(boolean dark) {
		isDarkMode = dark;
	}
	
	/**
	 * Resets the row after a wrong guess.
	 */
	public void resetGuesses() {
		guesses = new ArrayList<>();
		for (int i = 0; i < rowSize; i++) {
			WordDisplay inputtedWord = new WordDisplay(colSize);
			guesses.add(inputtedWord);
		}
		curRow = 0;
		isWon = false;
		updateUserGuesses();
		displayChildren();
		gui.getMainCenterPane().getChildren().clear();
		gui.getMainCenterPane().getChildren().add(this);
	}
	
	public Guess getGuess()
	{
		return wordComparison;
	}
}