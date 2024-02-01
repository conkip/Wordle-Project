/**
 * This creates an object representing the login banner used in GUI to log in.
 * 
 * Date 5/1/2023
 * 
 * @author Sean Eddy
 */

package src.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import src.model.WordleUser;

public class LoginBannerDisplay extends HBox {
	private Font loginLabelFont = Font.loadFont("file:fonts/franklin-gothic-book.ttf", 12);
	private static Stage loginStage;

	/**
	 * This will initialize the LoginBannerDisplay object with the Wordle user and
	 * dark mode
	 * 
	 * @param wordleUser is a WordleUser representing the current Wordle user
	 * @param isDarkMode is a boolean representing whether dark mode is enabled
	 */
	public LoginBannerDisplay(WordleUser wordleUser, boolean isDarkMode) {

		this.setSpacing(6);
		this.setAlignment(Pos.CENTER_LEFT);

		Image loginImg = new Image("file:images/login.png");
		ImageView loginView = new ImageView(loginImg);
		loginView.setFitWidth(30);
		loginView.setFitHeight(30);
		this.getChildren().add(loginView);

		Button loginButton = new Button("Log In or create a free account to link your stats.");
		loginButton.setFont(loginLabelFont);
		loginButton.setUnderline(true);
		loginButton.setTextFill(isDarkMode ? Color.WHITE : Color.BLACK);
		loginButton.setStyle("-fx-background-color: " + (isDarkMode ? "#121213" : "white") + ";");
		this.getChildren().add(loginButton);

		loginButton.setOnAction((event) -> {
			loginStage = new Stage();
			LoginDisplay loginDisplay = new LoginDisplay(isDarkMode);
			Scene loginScene = new Scene(loginDisplay, 500, 500);
			loginStage.setScene(loginScene);
			loginStage.getIcons().add(new Image("file:images/login.png"));
			loginStage.setResizable(false);
			loginButton.setMouseTransparent(true);
			loginStage.showAndWait();
			loginButton.setMouseTransparent(false);
		});
	}

	/**
	 * This returns the stage
	 * 
	 * @return login stage
	 */
	public static Stage getStage() {
		return loginStage;
	}
}