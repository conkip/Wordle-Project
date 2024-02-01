/**
 * This creates a letter button which is the core component and
 * is used as the board squares and keyboard buttons.
 * 
 * Date: 4/30/2023
 * 
 * @author Connor Kippes, Luke Laurie, Sean Eddy Zachary Hansen
 */

package src.view;

import java.util.HashMap;
import java.util.Map;

import javafx.animation.RotateTransition;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class Letter extends Button {
	private String textColor;
	private String borderColor;
	private String backgroundColor;
	private Map<String, String> colors = new HashMap<String, String>();
	private boolean isKeyboard;
	private RotateTransition flip;
	private boolean isDarkMode = false;

	/**
	 * Constructor initializes the color dictionary and sets the passed parameters
	 * 
	 * @param someText         the text of the button (usually a letter string)
	 * 
	 * @param isOnSomeKeyboard boolean true or false if the button is part of the
	 *                         keyboard display
	 */
	public Letter(String someText, Boolean isOnSomeKeyboard) {
		colors.put("Border Gray", "878a8c");
		colors.put("Green", "6aaa64");
		colors.put("dm Border Gray", "565758");
		colors.put("dm Dark Gray", "3a3a3c");
		colors.put("dm Gray", "818384");
		colors.put("Border Gray", "878a8c");
		colors.put("Dark Gray", "787c7f");
		colors.put("Gray", "d3d6da");
		colors.put("Yellow", "c9b458");
		colors.put("Orange", "f5793a");
		colors.put("Blue", "85c0f9");
		colors.put("Black", "121213");
		colors.put("White", "ffffff");
		isKeyboard = isOnSomeKeyboard;
		this.setText(someText);
		setView(isOnSomeKeyboard);
	}

	/**
	 * Sets the initial look and functionality based on if it is in the board or
	 * keyboard
	 * 
	 * @param isOnKeyboard boolean true or false if the button is part of the
	 *                     keyboard display
	 */
	private void setView(Boolean isOnKeyboard) {
		if (isOnKeyboard) {
			setKeyboardButtonStyle("Black", "Gray");
			textColor = "Black";
			backgroundColor = "Gray";
			this.setFont(Font.loadFont("file:fonts/Helvetica Monospaced W06 Bold.ttf", 19));

			this.setFocusTraversable(false);

			this.setPrefSize(44, 58);
		} else {
			setBoardButtonStyle("Black", "White", "Gray");
			textColor = "Black";
			backgroundColor = "White";
			borderColor = "Gray";
			this.setFont(Font.loadFont("file:fonts/Helvetica Monospaced W06 Bold.ttf", 16.5));

			// no buttons are selected or selectable
			this.setMouseTransparent(true);
			this.setFocusTraversable(false);

			this.setPrefSize(50, 50);

			flip = new RotateTransition(Duration.millis(500), this);
			flip.setAxis(Rotate.X_AXIS);
			flip.setFromAngle(-180);
			flip.setToAngle(-360);
		}
	}

	/**
	 * Sets the style (colors) of the keyboard button. Called by setView.
	 * 
	 * @param textColor       name of a color in the color dictionary to set the
	 *                        text to
	 * 
	 * @param backgroundColor name of a color in the color dictionary to set the
	 *                        background to
	 */
	public void setKeyboardButtonStyle(String textColor, String backgroundColor) {
		if (isDarkMode && backgroundColor == "Dark Gray") {
			backgroundColor = "dm Dark Gray";
		}
		if (WordleGUI.isContrastMode() && backgroundColor == "Green") {
			backgroundColor = "Orange";
		}
		if (WordleGUI.isContrastMode() && backgroundColor == "Yellow") {
			backgroundColor = "Blue";
		}
		this.setStyle(
				"-fx-text-fill: #" + colors.get(textColor) + "; -fx-background-color: #" + colors.get(backgroundColor));
		this.textColor = textColor;
		this.backgroundColor = backgroundColor;
	}

	/**
	 * Sets the style (colors) of the board squares. Called by setView.
	 * 
	 * @param textColor       name of a color in the color dictionary to set the
	 *                        text to
	 * 
	 * @param backgroundColor name of a color in the color dictionary to set the
	 *                        background to
	 * 
	 * @param borderColor     name of a color in the color dictionary to set the
	 *                        border to
	 */
	public void setBoardButtonStyle(String textColor, String backgroundColor, String borderColor) {
		this.setStyle(
				"-fx-text-fill: #" + colors.get(textColor) + "; -fx-background-color: #" + colors.get(backgroundColor)
						+ "; -fx-border-color: #" + colors.get(borderColor) + "; -fx-border-width: 2px");
		this.textColor = textColor;
		this.backgroundColor = backgroundColor;
		this.borderColor = borderColor;
	}

	/**
	 * sets the button to dark mode
	 */
	public void setDarkMode() {
		isDarkMode = true;
		if (isKeyboard) {
			if (backgroundColor.equals("Dark Gray")) {
				setKeyboardButtonStyle("White", "dm Dark Gray");
			} else if (backgroundColor.equals("Gray")) {
				setKeyboardButtonStyle("White", "dm Gray");
			}
		} else {
			if (backgroundColor.equals("White")) {
				if (borderColor.equals("Border Gray")) {
					setBoardButtonStyle("White", "Black", "dm Border Gray");
				} else {
					setBoardButtonStyle("White", "Black", "dm Dark Gray");
				}
			} else if (backgroundColor.equals("Dark Gray")) {
				setKeyboardButtonStyle("White", "dm Dark Gray");
			}
		}
	}

	/**
	 * sets the button off dark mode
	 */
	public void setNonDarkMode() {
		isDarkMode = false;
		if (isKeyboard) {
			if (backgroundColor.equals("dm Gray")) {
				setKeyboardButtonStyle("Black", "Gray");
			} else if (backgroundColor.equals("dm Dark Gray")) {
				setKeyboardButtonStyle("White", "Dark Gray");
			}
		} else {
			if (backgroundColor.equals("Black")) {
				if (this.getText().equals(" ")) {
					setBoardButtonStyle("Black", "White", "Gray");
				} else {
					setBoardButtonStyle("Black", "White", "Border Gray");
				}
			} else if (backgroundColor.equals("dm Dark Gray")) {
				setKeyboardButtonStyle("White", "Dark Gray");
			}
		}
	}

	/**
	 * sets the button to contrast theme
	 */
	public void setContrastMode() {
		if (isKeyboard) {
			if (backgroundColor.equals("Green")) {
				setKeyboardButtonStyle(textColor, "Orange");
			} else if (backgroundColor.equals("Yellow")) {
				setKeyboardButtonStyle(textColor, "Blue");
			}
		} else {
			if (backgroundColor.equals("Green")) {
				setBoardButtonStyle(textColor, "Orange", "Orange");
			} else if (backgroundColor.equals("Yellow")) {
				setBoardButtonStyle(textColor, "Blue", "Blue");
			}
		}
	}

	/**
	 * sets the button off contrast theme
	 */
	public void setNonContrastMode() {

		if (isKeyboard) {
			if (backgroundColor.equals("Orange")) {
				setKeyboardButtonStyle(textColor, "Green");
			} else if (backgroundColor.equals("Blue")) {
				setKeyboardButtonStyle(textColor, "Yellow");
			}
		} else {
			if (backgroundColor.equals("Orange")) {
				setBoardButtonStyle(textColor, "Green", "Green");
			} else if (backgroundColor.equals("Blue")) {
				setBoardButtonStyle(textColor, "Yellow", "Yellow");
			}
		}
	}

	/**
	 * returns if the letter button is in dark mode or not
	 * 
	 * @return true if the letter is in dark mode
	 */
	public boolean isDarkMode() {
		return isDarkMode;
	}

	/**
	 * returns the color of the letter button text
	 * 
	 * @return String name of the text color
	 */
	public String getTextColor() {
		return textColor;
	}

	/**
	 * returns the current border color of the letter color
	 * 
	 * @return String name of the border color
	 */
	public String getBorderColor() {
		return borderColor;
	}

	/**
	 * returns the current background color of the letter button
	 * 
	 * @return String name of the background color
	 */
	public String getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * returns the unique RotateTransition class that BoardDisplay uses to flip the
	 * letter
	 * 
	 * @return the RotateTransition initialization
	 */
	public RotateTransition getFlip() {
		return flip;
	}
}