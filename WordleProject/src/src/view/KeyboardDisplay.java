/**
 * Creates the on screen button keyboard of wordle
 * 
 * Date: 4/30/2023
 * 
 * @author Connor Kippes, Luke Laurie, Sean Eddy, Zachary Hansen
 */

package src.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.RootPaneContainer;

import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import src.model.Guess;

public class KeyboardDisplay extends VBox {
	private String[][] letterStrings = { { "Q", "W", "E", "R", "T", "Y", "U", "I", "O" },
			{ "A", "S", "D", "F", "G", "H", "J", "K", "L", },
			{ "ENTER", "Z", "X", "C", "V", "B", "N", "M", "BACKSPACE" } };
	private Letter[][] letters = new Letter[3][9];
	// p is separate because it is the 10th letter in the first row
	private Letter p = new Letter("P", true);
	private BoardDisplay curBoard;
	private ImageView backspaceImageView;
	private Image backspaceImage = new Image("file:src/images/backspace.png");
	private Image inverseBackspaceImage = new Image("file:src/images/backspace.png");
	private Map<String, String> savedLetterColors = new HashMap<String, String>();
	private int colSize = 5;

	/**
	 * Constructor initializes some instance variables, sets the spacing, and calls
	 * the addButtons and registerHandlers methods
	 * 
	 * @param curBoard the current BoardDisplay class
	 * @param colSize  the current integer column size (word length)
	 */
	public KeyboardDisplay(BoardDisplay curBoard, int colSize) {
		this.colSize = colSize;
		this.curBoard = curBoard;
		this.setSpacing(6);

		addButtons();

		// increase width and text of backspace and enter buttons
		letters[2][0].setPrefWidth(70);
		letters[2][0].setFont(Font.loadFont("file:src/fonts/Helvetica Monospaced W06 Bold.ttf", 12));
		letters[2][8].setPrefWidth(55);
		registerHandlers();
	}

	/**
	 * Adds Letter buttons with the correct letters, an enter button, and a
	 * backspace button to the three HBoxes which are contained within the VBox of
	 * the keyboard
	 */
	public void addButtons() {
		for (int row = 0; row < 3; row++) {
			HBox hbox = new HBox(6);
			hbox.setAlignment(Pos.CENTER);
			for (int col = 0; col < 9; col++) {
				letters[row][col] = new Letter(letterStrings[row][col], true);
				if (letterStrings[row][col].equals("BACKSPACE")) {
					backspaceImageView = new ImageView(backspaceImage);
					backspaceImageView.setFitHeight(38);
					backspaceImageView.setPreserveRatio(true);
					letters[row][col] = new Letter("", true);
					letters[row][col].setGraphic(backspaceImageView);
				} else {
					letters[row][col] = new Letter(letterStrings[row][col], true);
				}
				hbox.getChildren().add(letters[row][col]);
			}
			this.getChildren().add(hbox);
		}

		// have to trick compiler because it thinks it's a Node
		((HBox) this.getChildren().get(0)).getChildren().add(p);
	}

	/**
	 * sets the keyboard to dark mode
	 */
	public void setDarkMode() {
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 9; col++) {
				letters[row][col].setDarkMode();
				if (letters[row][col].getGraphic() != null) {
					backspaceImageView = new ImageView(inverseBackspaceImage);
				}
			}
		}
		p.setDarkMode();
	}

	/**
	 * sets the keyboard off dark mode
	 */
	public void setNonDarkMode() {
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 9; col++) {
				letters[row][col].setNonDarkMode();
				if (letters[row][col].getGraphic() != null) {
					backspaceImageView = new ImageView(backspaceImage);
				}
			}
		}
		p.setNonDarkMode();
	}

	/**
	 * resets the keyboard (used for new game button)
	 */
	public void resetColors(boolean isDarkMode) {
		savedLetterColors = new HashMap<String, String>();
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 9; col++) {
				// swap to correct color later
				if (isDarkMode) {
					letters[row][col].setKeyboardButtonStyle("White", "dm Gray");
				} else {
					letters[row][col].setKeyboardButtonStyle("Black", "Gray");
				}
			}
		}
		if (isDarkMode) {
			p.setKeyboardButtonStyle("White", "dm Gray");
		} else {
			p.setKeyboardButtonStyle("Black", "Gray");
		}
	}

	/**
	 * sets the relevant keyboard buttons to contrast theme
	 */
	public void setContrastMode() {
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 9; col++) {
				letters[row][col].setContrastMode();
			}
		}
		p.setContrastMode();
	}

	/**
	 * sets the relevant keyboard buttons to non contrast theme
	 */
	public void setNonContrastMode() {
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 9; col++) {
				letters[row][col].setNonContrastMode();
			}
		}
		p.setNonContrastMode();
	}

	/**
	 * registers all of the buttons in the keyboard to the ButtonHandler
	 */
	private void registerHandlers() {
		ButtonHandler bh = new ButtonHandler(this);
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 9; col++) {
				letters[row][col].setOnAction(bh);
			}
		}
		p.setOnAction((event) -> {
			curBoard.addLetter(null, "P", null);
		});
	}

	/**
	 * This class handles when a button in the keyboard is clicked
	 * 
	 * @author Connor Kippes, Luke Laurie, Sean Eddy, Zachary Hansen
	 */
	private class ButtonHandler implements EventHandler<ActionEvent> {
		private KeyboardDisplay keyboardDisplay;

		// have to pass the outer class when clicking enter so pass it as a parameter
		public ButtonHandler(KeyboardDisplay keyboardDisplay) {
			this.keyboardDisplay = keyboardDisplay;
		}

		@Override
		public void handle(ActionEvent ae) {
			Button buttonClicked = (Button) ae.getSource();
			for (int row = 0; row < 3; row++) {
				for (int col = 0; col < 9; col++) {
					Letter curLetter = letters[row][col];
					if (curLetter == buttonClicked) {
						if (curLetter.getGraphic() != null) {
							curBoard.addLetter(null, "BACKSPACE", null);
						} else {
							if (curLetter.getText().equals("ENTER")) {
								curBoard.addLetter(null, curLetter.getText(), keyboardDisplay);
							} else {
								curBoard.addLetter(null, curLetter.getText(), null);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * updates the relevant keyboard button colors if a word is guessed correctly
	 */
	public void updateKeyColors() {
		Guess guessClass = curBoard.getGuessClass();
		ArrayList<String> guessedWord = guessClass.getCurGuess();
		ArrayList<String> curGuess = guessClass.getCurGuessColors();
		{
			if (curGuess != null) {
				for (int i = 0; i < colSize; i++) {
					if (p.getText().equalsIgnoreCase(guessedWord.get(i))) {
						// if statement so it doesn't overwrite a color with gray if multiple of the
						// letter in word
						if (savedLetterColors.containsKey(p.getText())) {
							if (savedLetterColors.containsKey(p.getText())) {
								if (savedLetterColors.get(p.getText()).equals("Green")
										|| savedLetterColors.get(p.getText()).equals("Orange")) {
									p.setKeyboardButtonStyle("White", savedLetterColors.get(p.getText()));
								} else if (curGuess.get(i).equals("Green") || curGuess.get(i).equals("Orange")) {
									p.setKeyboardButtonStyle("White", curGuess.get(i));
									savedLetterColors.put(p.getText(), curGuess.get(i));
								} else if (savedLetterColors.get(p.getText()).equals("Yellow")
										|| savedLetterColors.get(p.getText()).equals("Blue")) {
									p.setKeyboardButtonStyle("White", savedLetterColors.get(p.getText()));
								} else {
									p.setKeyboardButtonStyle("White", curGuess.get(i));
									savedLetterColors.put(p.getText(), curGuess.get(i));
								}
							} else {
								p.setKeyboardButtonStyle("White", curGuess.get(i));
								savedLetterColors.put(p.getText(), curGuess.get(i));
							}
						} else {
							savedLetterColors.put(p.getText(), curGuess.get(i));
							p.setKeyboardButtonStyle("White", curGuess.get(i));
						}
					} else {
						for (int row = 0; row < 3; row++) {
							for (int col = 0; col < 9; col++) {
								Letter curLetter = letters[row][col];
								if (curLetter.getText().equalsIgnoreCase(guessedWord.get(i))) {
									if (savedLetterColors.containsKey(curLetter.getText())) {
										if (savedLetterColors.get(curLetter.getText()).equals("Green")
												|| savedLetterColors.get(curLetter.getText()).equals("Orange")) {
											curLetter.setKeyboardButtonStyle("White",
													savedLetterColors.get(curLetter.getText()));
										} else if (curGuess.get(i).equals("Green")
												|| curGuess.get(i).equals("Orange")) {
											curLetter.setKeyboardButtonStyle("White", curGuess.get(i));
											savedLetterColors.put(curLetter.getText(), curGuess.get(i));
										} else if (savedLetterColors.get(curLetter.getText()).equals("Yellow")
												|| savedLetterColors.get(curLetter.getText()).equals("Blue")) {
											curLetter.setKeyboardButtonStyle("White",
													savedLetterColors.get(curLetter.getText()));
										} else {
											curLetter.setKeyboardButtonStyle("White", curGuess.get(i));
											savedLetterColors.put(curLetter.getText(), curGuess.get(i));
										}
									} else {
										curLetter.setKeyboardButtonStyle("White", curGuess.get(i));
										savedLetterColors.put(curLetter.getText(), curGuess.get(i));
									}
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * sets the column size (word length) for the keyboard display
	 * 
	 * @param colSize integer column size
	 */
	public void setColSize(int colSize) {
		this.colSize = colSize;
	}

	/**
	 * @return returns the backspace button
	 */
	public Letter getBackspaceButton() {
		return letters[2][8];
	}

	/**
	 * returns a hashmap of the current keybord button color of letters guessed so
	 * far
	 * 
	 * @return hashmap of letters mapped to colors
	 */
	public HashMap<String, String> getSavedLetterColors() {
		return (HashMap<String, String>) savedLetterColors;
	}
}