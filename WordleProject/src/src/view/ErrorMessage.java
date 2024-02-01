/**
 * Used as an popup message when the user enters a wrong word, wins, loses, etc.
 * 
 * Date: 4/30/2023
 * 
 * @author Connor Kippes, Luke Laurie, Sean Eddy Zachary Hansen
 */

package src.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class ErrorMessage extends Button {
	private boolean isDarkMode = false;

	/**
	 * Constructor sets the text and darkmode status for the error message
	 * 
	 * @param text       String text of the error message
	 * 
	 * @param isDarkMode true or false if the game is in dark mode
	 */
	public ErrorMessage(String text, boolean isDarkMode) {
		this.setText(text);
		this.isDarkMode = isDarkMode;
		setUpButton();
	}

	/**
	 * sets up the font, size, and colors of the error message
	 */
	private void setUpButton() {
		this.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		this.setMinHeight(41);
		this.setMaxHeight(20);
		this.setTextAlignment(TextAlignment.CENTER);
		this.setStyle("-fx-text-fill: " + (isDarkMode ? "#121213" : "#ffffff") + "; -fx-background-color: "
				+ (isDarkMode ? "#ffffff" : "#121213"));
	}

	/**
	 * toggles dark mode for the error message
	 * 
	 * @param isDarkMode true or false if the game is in dark mode
	 */
	public void setDarkMode(boolean isDarkMode) {
		this.isDarkMode = isDarkMode;
		this.setStyle("-fx-text-fill: " + (isDarkMode ? "#121213" : "#ffffff") + "; -fx-background-color: "
				+ (isDarkMode ? "#ffffff" : "#121213"));
	}
}