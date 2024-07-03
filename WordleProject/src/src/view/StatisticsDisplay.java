/**
 * This creates an object representing a Statistics display
 * 
 * Date 5/1/2023
 * 
 * @author Sean Eddy
 */

package src.view;

import java.util.Collections;
import java.util.HashMap;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import src.model.WordleUser;

public class StatisticsDisplay extends VBox {
	private Font mainLabelFont = Font.loadFont("file:src/fonts/Helvetica Monospaced W06 Bold.ttf", 14);
	private Font numLabelFont = Font.loadFont("file:src/fonts/franklin-gothic-book.ttf", 42);
	private Font underNumLabelFont = Font.loadFont("file:src/fonts/franklin-gothic-book.ttf", 12);
	private Font distNumLabelFont = Font.loadFont("file:src/fonts/Helvetica Monospaced W06 Bold.ttf", 12);
	private WordleGUI gui;
	private boolean isDarkMode;
	private boolean isContrastMode;
	private int colSize;
	private int rowSize;

	/**
	 * This will initialize the StatisticsDisplay object with relevant attributes
	 * 
	 * @param wordleUser is a WordleUser representing the Wordle user
	 * @param gui        is a WordleGUI representing the gui
	 * @param colSize    is an int representing the number of columns in the game
	 * @param rowSize    is an int representing the number of rows in the game
	 */
	public StatisticsDisplay(WordleUser wordleUser, WordleGUI gui, int colSize, int rowSize) {
		this.gui = gui;
		isDarkMode = gui.getDarkMode();
		isContrastMode = gui.getContrastMode();
		this.colSize = rowSize;
		this.rowSize = rowSize;

		this.setSpacing(30);
		this.setPadding(new Insets(75, 75, 75, 75));
		this.setStyle("-fx-background-color: " + (isDarkMode ? "#121213" : "white") + ";");

		VBox statsContainer = new VBox(0);

		Label statsLabel = new Label("STATISTICS");
		statsLabel.setFont(mainLabelFont);
		statsLabel.setTextFill(isDarkMode ? Color.WHITE : Color.BLACK);
		statsContainer.getChildren().add(statsLabel);

		HBox statsBox = new HBox(30);

		VBox gamesPlayedVBox = new VBox(0);
		Label gamesPlayedValLabel = new Label(Integer.toString(wordleUser.getGamesPlayed()));
		gamesPlayedValLabel.setFont(numLabelFont);
		gamesPlayedValLabel.setTextFill(isDarkMode ? Color.WHITE : Color.BLACK);
		gamesPlayedVBox.getChildren().add(gamesPlayedValLabel);
		Label gamesPlayedLabel = new Label("Played");
		gamesPlayedLabel.setAlignment(Pos.CENTER);
		gamesPlayedLabel.setFont(underNumLabelFont);
		gamesPlayedLabel.setTextFill(isDarkMode ? Color.WHITE : Color.BLACK);
		gamesPlayedVBox.getChildren().add(gamesPlayedLabel);
		statsBox.getChildren().add(gamesPlayedVBox);

		VBox winRateVBox = new VBox(0);
		Label winRateValLabel = new Label(Integer.toString((int) Math.round(wordleUser.getWinRate() * 100)));
		winRateValLabel.setFont(numLabelFont);
		winRateValLabel.setTextFill(isDarkMode ? Color.WHITE : Color.BLACK);
		winRateVBox.getChildren().add(winRateValLabel);
		Label winRateLabel = new Label("Win %");
		winRateLabel.setFont(underNumLabelFont);
		winRateLabel.setTextFill(isDarkMode ? Color.WHITE : Color.BLACK);
		winRateVBox.getChildren().add(winRateLabel);
		statsBox.getChildren().add(winRateVBox);

		VBox curStreakVBox = new VBox(0);
		Label curStreakValLabel = new Label(Integer.toString(wordleUser.getCurStreak()));
		curStreakValLabel.setFont(numLabelFont);
		curStreakValLabel.setTextFill(isDarkMode ? Color.WHITE : Color.BLACK);
		curStreakVBox.getChildren().add(curStreakValLabel);
		Label curStreakLabel = new Label("Current\nStreak");
		curStreakLabel.setFont(underNumLabelFont);
		curStreakLabel.setTextFill(isDarkMode ? Color.WHITE : Color.BLACK);
		curStreakVBox.getChildren().add(curStreakLabel);
		statsBox.getChildren().add(curStreakVBox);

		VBox maxStreakVBox = new VBox(0);
		Label maxStreakValLabel = new Label(Integer.toString(wordleUser.getMaxStreak()));
		maxStreakValLabel.setFont(numLabelFont);
		maxStreakValLabel.setTextFill(isDarkMode ? Color.WHITE : Color.BLACK);
		maxStreakVBox.getChildren().add(maxStreakValLabel);
		Label maxStreakLabel = new Label("Max Streak");
		maxStreakLabel.setFont(underNumLabelFont);
		maxStreakLabel.setTextFill(isDarkMode ? Color.WHITE : Color.BLACK);
		maxStreakVBox.getChildren().add(maxStreakLabel);
		statsBox.getChildren().add(maxStreakVBox);

		statsContainer.getChildren().add(statsBox);
		this.getChildren().add(statsContainer);

		VBox guessDistributionContainer = new VBox(10);

		Label guessDistributionLabel = new Label("GUESS DISTRIBUTION");
		guessDistributionLabel.setFont(mainLabelFont);
		guessDistributionLabel.setTextFill(isDarkMode ? Color.WHITE : Color.BLACK);
		guessDistributionContainer.getChildren().add(guessDistributionLabel);

		VBox guessDistributionsContainer = new VBox(5);

//		// initializing guess distribution to random values for testing
//		HashMap<Integer, Integer> guessDistribution = new HashMap<Integer, Integer>();
//		for (int guessCount = 1; guessCount <= 6; guessCount++) {
//			guessDistribution.put(guessCount, (int) (Math.random() * (6 + 1)));
//		}
		HashMap<Integer, Integer> guessDistribution = wordleUser.getGuessDistribution(rowSize, colSize);
		double maxDistCount = Collections.max(guessDistribution.values());
		guessDistribution.forEach((key, val) -> {
			HBox guessDistributionRow = new HBox(0);

			Label guessCountLabel = new Label(Integer.toString(key));
			guessCountLabel.setPadding(new Insets(4, 4, 4, 4));
			guessCountLabel.setTextFill(isDarkMode ? Color.WHITE : Color.BLACK);
			guessCountLabel.setFont(distNumLabelFont);
			guessDistributionRow.getChildren().add(guessCountLabel);

			BorderPane row = new BorderPane();

			Label distCountLabel = new Label(Integer.toString(val));
			distCountLabel.setPadding(new Insets(4, 4, 4, 4));
			distCountLabel.setFont(distNumLabelFont);
			distCountLabel.setTextFill(Color.WHITE);
			row.setRight(distCountLabel);

			row.setPrefWidth(10 + (maxDistCount > 0 ? ((val / (double) maxDistCount) * 340) : 0));
			row.setBackground(new Background(new BackgroundFill(((val == maxDistCount && maxDistCount != 0)
					? (isContrastMode ? Color.valueOf("f5793a") : Color.valueOf("6aaa64"))
					: Color.GRAY), CornerRadii.EMPTY, Insets.EMPTY)));
			guessDistributionRow.getChildren().add(row);

			guessDistributionsContainer.getChildren().add(guessDistributionRow);
		});

		guessDistributionContainer.getChildren().add(guessDistributionsContainer);

		this.getChildren().add(guessDistributionContainer);

		this.getChildren().add(new LoginBannerDisplay(wordleUser, isDarkMode));
	}
}