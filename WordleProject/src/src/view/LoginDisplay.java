/**
 * Creates an object representing the login display
 * 
 * Date 5/1/2023
 * 
 * @author Sean Eddy
 */

package src.view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import src.model.WordleUser;

public class LoginDisplay extends VBox {
	private Font titleLabelFont = Font.loadFont("file:fonts/KarnakPro-CondensedBlack.ttf", 30);
	private Font subtitleLabelFont = Font.loadFont("file:fonts/Helvetica Monospaced W06 Bold.ttf", 14);
	private Font labelFont = Font.loadFont("file:fonts/Helvetica Monospaced W06 Bold.ttf", 12);

	private Label titleLabel;
	private TextArea emailTextArea;
	private PasswordField passwordField;
	private WordleUser curUser;

	/**
	 * This initializes a LoginDisplay object with current dark mode setting
	 * 
	 * @param isDarkMode is a boolean representing whether dark mode is enabled
	 */
	public LoginDisplay(boolean isDarkMode) {
		this.setSpacing(24);
		this.setPadding(new Insets(25, 25, 25, 25));
		this.setStyle("-fx-background-color: " + (isDarkMode ? "#121213" : "white") + ";");

		titleLabel = new Label("Log in or create a free account to save your stats.");
		titleLabel.setFont(titleLabelFont);
		titleLabel.setWrapText(true);
		titleLabel.setTextFill(isDarkMode ? Color.WHITE : Color.BLACK);
		this.getChildren().add(titleLabel);

		Label subtitleLabel = new Label(
				"Your Wordle stats will be linked to your account and will update wherever you play.");
		subtitleLabel.setFont(subtitleLabelFont);
		subtitleLabel.setWrapText(true);
		subtitleLabel.setTextFill(isDarkMode ? Color.WHITE : Color.BLACK);
		this.getChildren().add(subtitleLabel);

		VBox emailContainer = new VBox(4);

		Label emailLabel = new Label("Email Address");
		emailLabel.setFont(labelFont);
		emailLabel.setTextFill(isDarkMode ? Color.WHITE : Color.BLACK);
		emailContainer.getChildren().add(emailLabel);

		emailTextArea = new TextArea();
		emailTextArea.setFont(subtitleLabelFont);
		emailTextArea.setPrefWidth(450);
		emailTextArea.setPrefHeight(20);
		emailContainer.getChildren().add(emailTextArea);

		this.getChildren().add(emailContainer);

		Button loginButton = new Button("Continue");
		loginButton.setFont(subtitleLabelFont);
		loginButton.setTextFill(isDarkMode ? Color.BLACK : Color.WHITE);
		loginButton.setStyle("-fx-background-color: " + (isDarkMode ? "white" : "black") + ";");
		loginButton.setPrefWidth(450);
		loginButton.setPrefHeight(40);
		this.getChildren().add(loginButton);

		loginButton.setOnAction((event) -> {
			// checks if an email was entered
			String inputtedEmail = emailTextArea.getText();
			if (inputtedEmail.length() >= 1 && loginButton.getText() == "Continue") {
				curUser = WordleGUI.findUser(inputtedEmail);
				if (curUser != null) {
					titleLabel.setText("Enter your password to finish logging into your account.");
					loginButton.setText("Log In");
				} else {
					titleLabel.setText("Finish creating your free account.");
					loginButton.setText("Create Account");
				}
				emailTextArea.setEditable(false);
				this.getChildren().remove(loginButton);
				showPassword(isDarkMode);
				this.getChildren().add(loginButton);
			}
			// attempt to log in
			else if (loginButton.getText().equals("Log In")) {
				if (curUser.getPassword().equals(passwordField.getText())) {
					// save the current users info
					WordleGUI.writeUser(null);
					WordleGUI.setUser(curUser);
					// closes the panes
					LoginBannerDisplay.getStage().close();
				}
			}
			// create new account
			else if (loginButton.getText().equals("Create Account")) {
				WordleGUI.writeUser(null);
				curUser = new WordleUser(inputtedEmail, passwordField.getText());
				WordleGUI.setUser(curUser);
				WordleGUI.writeUser(curUser);
				// closes the panes
				LoginBannerDisplay.getStage().close();
			}
		});
	}

	/**
	 * This shows the password field
	 * 
	 * @param isDarkMode is a boolean representing whether dark mode is enabled
	 */
	private void showPassword(boolean isDarkMode) {
		VBox passwordContainer = new VBox(4);

		Label passwordLabel = new Label("Password");
		passwordLabel.setFont(labelFont);
		passwordLabel.setTextFill(isDarkMode ? Color.WHITE : Color.BLACK);
		passwordContainer.getChildren().add(passwordLabel);

		passwordField = new PasswordField();
		passwordField.setPrefWidth(450);
		passwordField.setPrefHeight(20);
		passwordContainer.getChildren().add(passwordField);
		// only allow for one password field
		if (this.getChildren().size() <= 3) {
			this.getChildren().add(passwordContainer);
		}
	}
}