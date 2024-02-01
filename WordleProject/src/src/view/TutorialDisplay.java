/**
 * Creates the display that gives a brief tutorial for how to play wordle
 * 
 * Date: 4/30/2023
 * 
 * @author Connor Kippes, Luke Laurie, Sean Eddy, Zachary Hansen
 */

package src.view;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import src.model.WordleUser;

public class TutorialDisplay extends VBox {
	private boolean isDarkMode;
	private boolean isContrastMode;
	private int rowSize;
	private int colSize;

	//constructor for the TutorialDisplay
	public TutorialDisplay(WordleUser wordleUser, boolean isDarkMode, boolean isContrastMode, int rowSize,
			int colSize) {
		this.rowSize = rowSize;
		this.colSize = colSize;
		this.isDarkMode = isDarkMode;
		this.isContrastMode = isContrastMode;
		createDisplay();

		this.getChildren().add(new LoginBannerDisplay(wordleUser, isDarkMode));
	}
	
	/**
	 * Initializes all elements for the display
	 */
	public void createDisplay() {
		//Initializes the header in the display
		VBox statsContainer = new VBox(0);

		Label header = new Label("How To Play");
		header.setFont(Font.loadFont("file:fonts/KarnakPro-CondensedBlack.ttf", 30));
		if (isDarkMode) {
			header.setTextFill(Color.WHITE);
		}
		statsContainer.getChildren().add(header);
		Label moreHeader = new Label("Guess the Wordle in " + rowSize + " tries.");
		moreHeader.setFont(Font.loadFont("file:fonts/franklin-gothic-book.ttf", 20));
		if (isDarkMode) {
			moreHeader.setTextFill(Color.WHITE);
		}
		statsContainer.getChildren().add(moreHeader);
		this.getChildren().add(statsContainer);
		
		//Initializes the explanation in the display

		VBox explanation = new VBox(0);
		Label text = new Label("• Each guess must be a valid " + colSize + "-letter word.\n"
				+ "• The color of the tiles will change to show how close your guess\n" + "  was to the word.");
		text.setFont(new Font("Helvetica", 13));
		if (isDarkMode) {
			text.setTextFill(Color.WHITE);
		}
		explanation.getChildren().add(text);
		this.getChildren().add(explanation);
		
		//Initializes the example of a correct letter in the display
		VBox firstExample = new VBox(0);
		Label exampleText = new Label("Examples");
		exampleText.setFont(Font.loadFont("file:fonts/Helvetica Monospaced W06 Bold.ttf", 15));
		if (isDarkMode) {
			exampleText.setTextFill(Color.WHITE);
		}
		firstExample.getChildren().add(exampleText);
		HBox hbox = new HBox(6);
		Letter correctLetter = new Letter("W", false);
		correctLetter.setBoardButtonStyle("White", "Green", "Green");
		if (isContrastMode) {
			correctLetter.setContrastMode();
		}

		hbox.getChildren().add(correctLetter);
		Letter letter1 = new Letter("E", false), letter2 = new Letter("A", false);
		Letter letter3 = new Letter("R", false), letter4 = new Letter("Y", false);
		if (isDarkMode) {
			letter1.setDarkMode();
			letter2.setDarkMode();
			letter3.setDarkMode();
			letter4.setDarkMode();
		}
		hbox.getChildren().add(letter1);
		hbox.getChildren().add(letter2);
		hbox.getChildren().add(letter3);
		hbox.getChildren().add(letter4);
		firstExample.getChildren().add(hbox);
		HBox exampleExplanation = new HBox(2);
		Label exampleText2 = new Label("W");
		exampleText2.setFont(Font.loadFont("file:fonts/Helvetica Monospaced W06 Bold.ttf", 15));
		if (isDarkMode) {
			exampleText2.setTextFill(Color.WHITE);
		}
		exampleExplanation.getChildren().add(exampleText2);
		Label exampleText3 = new Label(" is in the word and in the correct spot.");
		exampleText3.setFont(new Font("Helvetica", 15));
		if (isDarkMode) {
			exampleText3.setTextFill(Color.WHITE);
		}
		exampleExplanation.getChildren().add(exampleText3);
		firstExample.getChildren().add(exampleExplanation);
		firstExample.setSpacing(8);
		this.getChildren().add(firstExample);

		//Initializes the example of a misplaced letter in the display
		VBox secondExample = new VBox(0);
		HBox hbox2 = new HBox(6);
		Letter letterWrongSpot = new Letter("I", false);
		letterWrongSpot.setBoardButtonStyle("White", "Yellow", "Yellow");
		if (isContrastMode) {
			letterWrongSpot.setContrastMode();
		}

		Letter letter5 = new Letter("P", false), letter6 = new Letter("L", false);
		Letter letter7 = new Letter("L", false), letter8 = new Letter("S", false);
		if (isDarkMode) {
			letter5.setDarkMode();
			letter6.setDarkMode();
			letter7.setDarkMode();
			letter8.setDarkMode();
		}
		hbox2.getChildren().add(letter5);
		hbox2.getChildren().add(letterWrongSpot);
		hbox2.getChildren().add(letter6);
		hbox2.getChildren().add(letter7);
		hbox2.getChildren().add(letter8);

		secondExample.getChildren().add(hbox2);
		HBox exampleExplanation2 = new HBox(2);
		Label exampleText4 = new Label("I");
		exampleText4.setFont(Font.loadFont("file:fonts/Helvetica Monospaced W06 Bold.ttf", 16));
		if (isDarkMode) {
			exampleText4.setTextFill(Color.WHITE);
		}
		exampleExplanation2.getChildren().add(exampleText4);
		Label exampleText5 = new Label(" is in the word but in the wrong spot.");
		exampleText5.setFont(new Font("Helvetica", 15));
		if (isDarkMode) {
			exampleText5.setTextFill(Color.WHITE);
		}
		exampleExplanation2.getChildren().add(exampleText5);
		secondExample.getChildren().add(exampleExplanation2);
		secondExample.setSpacing(8);
		this.getChildren().add(secondExample);

		VBox thirdExample = new VBox(0);
		HBox hbox3 = new HBox(6);
		Letter wrongLetter = new Letter("U", false);
		wrongLetter.setBoardButtonStyle("White", "Dark Gray", "Dark Gray");

		Letter letter9 = new Letter("V", false), letter10 = new Letter("A", false);
		Letter letter11 = new Letter("G", false), letter12 = new Letter("E", false);
		if (isDarkMode) {
			letter9.setDarkMode();
			letter10.setDarkMode();
			letter11.setDarkMode();
			letter12.setDarkMode();
			wrongLetter.setDarkMode();
		}
		hbox3.getChildren().add(letter9);
		hbox3.getChildren().add(letter10);
		hbox3.getChildren().add(letter11);
		hbox3.getChildren().add(wrongLetter);
		hbox3.getChildren().add(letter12);
		
		//Initializes the example of a wrong letter in the display

		thirdExample.getChildren().add(hbox3);
		HBox exampleExplanation3 = new HBox(2);
		Label exampleText6 = new Label("U");
		exampleText6.setFont(Font.loadFont("file:fonts/Helvetica Monospaced W06 Bold.ttf", 16));
		if (isDarkMode) {
			exampleText6.setTextFill(Color.WHITE);
		}
		exampleExplanation3.getChildren().add(exampleText6);
		Label exampleText7 = new Label(" is not in the word at any spot.");
		exampleText7.setFont(new Font("Helvetica", 15));
		if (isDarkMode) {
			exampleText7.setTextFill(Color.WHITE);
		}
		exampleExplanation3.getChildren().add(exampleText7);
		thirdExample.getChildren().add(exampleExplanation3);
		thirdExample.setSpacing(8);
		this.getChildren().add(thirdExample);

		this.setSpacing(17);
		this.setPadding(new Insets(25, 25, 25, 25));
		correctLetter.getFlip().setDuration(Duration.millis(1000));
		correctLetter.getFlip().play();
		letterWrongSpot.getFlip().setDuration(Duration.millis(1000));
		letterWrongSpot.getFlip().play();
		wrongLetter.getFlip().setDuration(Duration.millis(1000));
		wrongLetter.getFlip().play();
	}

	/**
	 * sets the display to dark mode and recreates the display
	 */
	public void setDarkMode() {
		isDarkMode = true;
		createDisplay();
	}

	/**
	 * sets the display to light mode and recreates the dsiplay
	 */
	public void setNonDarkMode() {
		isDarkMode = false;
		createDisplay();
	}
}