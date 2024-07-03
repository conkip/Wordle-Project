/**
 * This is the main view class from which all other classes are called 
 * to create the GUI of the game Wordle
 * 
 * Date: 4/30/2023
 * 
 * @author Connor Kippes, Luke Laurie, Sean Eddy, Zachary Hansen
 */

package src.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Map;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import src.model.WordleUser;

public class WordleGUI extends Application {
	private Stage primaryStage;
	private Scene primaryScene;
	private String backgroundColor = "white";
	private BorderPane primaryPane;
	private StackPane mainCenterPane = new StackPane();
	private BorderPane menuPane;
	private static int colSize = 5;
	private static int rowSize = 6;
	private static BoardDisplay board;
	private static KeyboardDisplay keyboard;
	private Label title;
	private Button statsButton;
	private Button helpButton;
	private Button settingsButton;
	private static boolean isDarkMode = false;
	private static boolean isContrastMode = false;
	private boolean isHardMode = false;
	private ArrayList<Button> multiColButtons = new ArrayList<>();
	private ArrayList<Button> multiRowButtons = new ArrayList<>();
	private Region veil = new Region();

	private static WordleUser wordleUser;
	private static ArrayList<WordleUser> allUsers;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		veil.setStyle("-fx-background-color: rgba(0, 0, 0, .3)");
		veil.setVisible(false);
		// gets the starting user
		userStartup();
		new MultiWordle(this);
		updateUsers();
	}

	/**
	 * updates the user whenever application is closed
	 */
	private void updateUsers() {
		primaryStage.setOnCloseRequest(event -> {
			//setUpStage();
			board.updateUserGuesses();
			writeUser(wordleUser);
		});

	}

	/**
	 * decided whether to initialize the board for a specific WordleUser or the
	 * tempUser
	 */
	private void userStartup() {
		readUsers();
		// checks if no users exist yet
		if (allUsers == null) {
			allUsers = new ArrayList<>();
			wordleUser = new WordleUser("tempUser", "tempPassword");
			writeUser(wordleUser);
		} else {
			wordleUser = findUser("tempUser");
		}
		// Initialize the board
		board = new BoardDisplay(colSize, rowSize, this);
	}

	/**
	 * Where every element of the stage is initialized
	 */
	public void setUpStage() {
		this.primaryStage.setTitle("Wordle");

		// Instantiates the different Panes and Scenes to be used
		primaryPane = new BorderPane();
		primaryScene = new Scene(primaryPane, 700, 630);
		menuPane = new BorderPane();

		// This begins the header
		title = new Label("Wordle");
		title.setFont(Font.loadFont("file:src/fonts/KarnakPro-CondensedBlack.ttf", 40));

		// sets each button in the header
		statsButton = new Button();
		statsButton.setStyle("-fx-background-color: " + backgroundColor);
		Image statsImg = new Image("file:src/images/statistics.png");
		ImageView statsView = new ImageView(statsImg);
		statsView.setFitHeight(29);
		statsView.setFitWidth(29);
		statsButton.setGraphic(statsView);
		statsButton.setFocusTraversable(false);

		helpButton = new Button();
		helpButton.setStyle("-fx-background-color: " + backgroundColor);
		Image helpImg = new Image("file:src/images/help.png");
		ImageView helpView = new ImageView(helpImg);
		helpView.setFitHeight(29);
		helpView.setFitWidth(29);
		helpButton.setGraphic(helpView);
		helpButton.setFocusTraversable(false);

		settingsButton = new Button();
		settingsButton.setStyle("-fx-background-color: " + backgroundColor);
		Image settingImg = new Image("file:src/images/settings.png");
		ImageView settingView = new ImageView(settingImg);
		settingView.setFitHeight(29);
		settingView.setFitWidth(29);
		settingsButton.setGraphic(settingView);
		settingsButton.setFocusTraversable(false);

		// creates an invisible offset button for each real button to keep
		// the header balanced
		Button offsetButton1 = new Button();
		offsetButton1.setGraphic(helpView);
		offsetButton1.setVisible(false);

		Button offsetButton2 = new Button();
		offsetButton2.setGraphic(helpView);
		offsetButton2.setVisible(false);

		Button offsetButton3 = new Button();
		offsetButton3.setGraphic(helpView);
		offsetButton3.setVisible(false);

		// puts each Button and offset Button into an HBox
		HBox workingButtons = new HBox(0);
		workingButtons.getChildren().add(helpButton);
		workingButtons.getChildren().add(statsButton);
		workingButtons.getChildren().add(settingsButton);

		HBox offsetButtons = new HBox(0);
		offsetButtons.getChildren().add(offsetButton1);
		offsetButtons.getChildren().add(offsetButton2);
		offsetButtons.getChildren().add(offsetButton3);

		menuPane.setLeft(offsetButtons);
		menuPane.setCenter(title);
		menuPane.setRight(workingButtons);
		menuPane.setStyle("-fx-background-color: " + backgroundColor);
		menuPane.setPrefHeight(50);
		menuPane.setBorder(new Border(new BorderStroke(Color.valueOf("#dee0e3"), BorderStrokeStyle.SOLID,
				CornerRadii.EMPTY, new BorderWidths(0, 0, 1, 0))));

		board.setAlignment(Pos.CENTER);
		primaryPane.setPadding(new Insets(25, 25, 25, 25));

		mainCenterPane.getChildren().add(board);
		mainCenterPane.setAlignment(Pos.TOP_CENTER);

		primaryPane.setTop(menuPane);
		primaryPane.setCenter(mainCenterPane);
		primaryPane.setBottom(keyboard);
		primaryPane.setStyle("-fx-background-color: white;");

		eventHandlers();
		primaryStage.setScene(primaryScene);
		primaryStage.getIcons().add(new Image("file:src/images/wordle_logo_32x32.png"));
		primaryStage.show();
	}

	/**
	 * This method handles all event Handlers for the secondary displays
	 */
	private void eventHandlers() {
		// opens the Tutorial Display when pressed
		helpButton.setOnAction((event) -> {
			Stage helpStage = new Stage();
			helpStage.initOwner(primaryStage);
			helpStage.initModality(Modality.APPLICATION_MODAL);
			TutorialDisplay tutorialDisplay = new TutorialDisplay(wordleUser, isDarkMode, isContrastMode, rowSize,
					colSize);
			if (isDarkMode) {
				tutorialDisplay.setStyle("-fx-background-color: #121213;");
			} else {
				tutorialDisplay.setStyle("-fx-background-color: white;");
			}
			Scene helpScene = new Scene(tutorialDisplay, 500, 550);
			helpStage.setScene(helpScene);
			helpStage.getIcons().add(new Image("file:src/images/help.png"));
			helpStage.setResizable(false);
			settingsButton.setMouseTransparent(true);
			statsButton.setMouseTransparent(true);
			helpButton.setMouseTransparent(true);
			helpStage.showAndWait();
			settingsButton.setMouseTransparent(false);
			statsButton.setMouseTransparent(false);
			helpButton.setMouseTransparent(false);
		});

		// opens the Statistics Display when pressed
		statsButton.setOnAction((event) -> {
			Stage statsStage = new Stage();
			statsStage.initOwner(primaryStage);
			statsStage.initModality(Modality.APPLICATION_MODAL);
			StatisticsDisplay statsDisplay = new StatisticsDisplay(wordleUser, this, colSize, rowSize);
			Scene statsScene = new Scene(statsDisplay, 500, 500);
			statsStage.setScene(statsScene);
			statsStage.getIcons().add(new Image("file:src/images/statistics.png"));
			statsStage.setResizable(false);
			settingsButton.setMouseTransparent(true);
			statsButton.setMouseTransparent(true);
			helpButton.setMouseTransparent(true);
			statsStage.showAndWait();
			settingsButton.setMouseTransparent(false);
			statsButton.setMouseTransparent(false);
			helpButton.setMouseTransparent(false);
		});

		// opens the Settings Display when pressed
		settingsButton.setOnAction((event) -> {
			Stage settingsStage = new Stage();
			SettingsDisplay settingsDisplay = new SettingsDisplay(this);
			Scene settingsScene = new Scene(settingsDisplay, 500, 500);
			settingsStage.setScene(settingsScene);
			settingsStage.getIcons().add(new Image("file:src/images/settings.png"));
			settingsStage.setResizable(false);
			settingsStage.initOwner(primaryStage);
			settingsStage.initModality(Modality.APPLICATION_MODAL);
			veil.visibleProperty().bind(settingsStage.showingProperty());
			settingsButton.setMouseTransparent(true);
			statsButton.setMouseTransparent(true);
			helpButton.setMouseTransparent(true);
			settingsStage.showAndWait();
			settingsStage.close();
			settingsButton.setMouseTransparent(false);
			statsButton.setMouseTransparent(false);
			helpButton.setMouseTransparent(false);
		});

		// listens to button presses on the keyboard, this needs to be in this main GUI
		primaryScene.setOnKeyPressed((KeyEvent event) -> {
			if (event.getCode() == KeyCode.ENTER) {
				board.addLetter(event.getCode(), "", keyboard);
			} else {
				board.addLetter(event.getCode(), "", null);
			}
		});
	}

	/**
	 * sets the theme to dark mode for all displays
	 */
	public void setDarkMode() {
		inverseImage("src/images/help.png", helpButton);
		inverseImage("src/images/settings.png", settingsButton);
		inverseImage("src/images/statistics.png", statsButton);
		helpButton.setStyle("-fx-background-color: #121213");
		settingsButton.setStyle("-fx-background-color: #121213");
		statsButton.setStyle("-fx-background-color: #121213");

		Button backspaceButton = (Button) keyboard.getBackspaceButton();
		inverseImage("src/images/backspace.png", backspaceButton);

		Map<String, String> keyboardSavedColorsMap = keyboard.getSavedLetterColors();
		for (Map.Entry<String, String> entry : keyboardSavedColorsMap.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			if (value.equals("Dark Gray")) {
				keyboardSavedColorsMap.put(key, "dm Dark Gray");
			}
		}
		// sets the wordle users dark value
		wordleUser.setDarkMode(true);
		isDarkMode = true;
		keyboard.setDarkMode();
		board.setDarkMode();
		primaryPane.setStyle("-fx-background-color: #121213");
		menuPane.setStyle("-fx-background-color: #121213");
		title.setTextFill(Color.WHITE);
		menuPane.setBorder(new Border(new BorderStroke(Color.valueOf("#303031"), BorderStrokeStyle.SOLID,
				CornerRadii.EMPTY, new BorderWidths(0, 0, 1, 0))));

		if (mainCenterPane.getChildren().size() > 1)
			((ErrorMessage) mainCenterPane.getChildren().get(mainCenterPane.getChildren().size() - 1))
					.setDarkMode(false);
	}

	/**
	 * sets the theme to lightMode for all displays
	 */
	public void setNonDarkMode() {
		Image helpImg = new Image("file:src/images/help.png");
		ImageView helpView = new ImageView(helpImg);
		helpButton.setGraphic(helpView);
		helpView.setFitHeight(30);
		helpView.setFitWidth(30);

		Image settingsImg = new Image("file:src/images/settings.png");
		ImageView settingsView = new ImageView(settingsImg);
		settingsButton.setGraphic(settingsView);
		settingsView.setFitHeight(30);
		settingsView.setFitWidth(30);

		Image statsImg = new Image("file:src/images/statistics.png");
		ImageView statsView = new ImageView(statsImg);
		statsButton.setGraphic(statsView);
		statsView.setFitHeight(30);
		statsView.setFitWidth(30);

		helpButton.setStyle("-fx-background-color: #ffffff");
		settingsButton.setStyle("-fx-background-color: #ffffff");
		statsButton.setStyle("-fx-background-color: #ffffff");

		Image backspaceImg = new Image("file:src/images/backspace.png");
		ImageView backspaceView = new ImageView(backspaceImg);
		keyboard.getBackspaceButton().setGraphic(backspaceView);
		backspaceView.setFitHeight(30);
		backspaceView.setFitWidth(30);

		Map<String, String> keyboardSavedColorsMap = keyboard.getSavedLetterColors();
		for (Map.Entry<String, String> entry : keyboardSavedColorsMap.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			if (value.equals("dm Dark Gray")) {
				keyboardSavedColorsMap.put(key, "Dark Gray");
			}
		}

		// sets the wordle users dark value
		wordleUser.setDarkMode(false);
		isDarkMode = false;
		keyboard.setNonDarkMode();
		board.setNonDarkMode();
		primaryPane.setStyle("-fx-background-color: #ffffff");
		menuPane.setStyle("-fx-background-color: #ffffff");
		title.setTextFill(Color.valueOf("#121213"));
		menuPane.setBorder(new Border(new BorderStroke(Color.valueOf("#dee0e3"), BorderStrokeStyle.SOLID,
				CornerRadii.EMPTY, new BorderWidths(0, 0, 1, 0))));

		if (mainCenterPane.getChildren().size() > 1)
			((ErrorMessage) mainCenterPane.getChildren().get(mainCenterPane.getChildren().size() - 1))
					.setDarkMode(isDarkMode);
	}

	/**
	 * determines the contrast mode for all displays. It can turn it
	 * on or off depending on the parameter
	 * @param isContrastMode a boolean operator
	 */
	public void setContrastMode(boolean isContrastMode) {
		this.isContrastMode = isContrastMode;
		// sets the wordle users dark value
		wordleUser.setContrastMode(isContrastMode);
	}

	/**
	 * determines the difficulty mode for all displays. It can turn it
	 * on or off depending on the parameter
	 * @param isHardMode a boolean operator
	 */
	public void setHardMode(boolean isHardMode) {
		this.isHardMode = isHardMode;
		board.setHardMode(isHardMode);
	}

	/**
	 * This method inverts image color for display buttons to match with 
	 * the color them
	 * @param imageName a String of the image
	 * @param button the Button to change
	 */
	public void inverseImage(String imageName, Button button) {
		// code to invert image for dark mode
		try {
			InputStream stream = new FileInputStream(imageName);
			Image image = new Image(stream);
			PixelReader reader = image.getPixelReader();
			int w = (int) image.getWidth();
			int h = (int) image.getHeight();
			WritableImage wImage = new WritableImage(w, h);
			PixelWriter writer = wImage.getPixelWriter();
			for (int y = 0; y < h; y++) {
				for (int x = 0; x < w; x++) {
					Color color = reader.getColor(x, y);
					writer.setColor(x, y, color.invert());
				}
			}
			ImageView imageView = new ImageView();
			imageView.setImage(wImage);
			imageView.setFitHeight(30);
			imageView.setFitWidth(30);
			button.setGraphic(imageView);
		} catch (FileNotFoundException e) {
			System.out.println("Display button image file not found");
		}
	}

	/**
	 * This method sets the column size attribute
	 * @param colSize an integer
	 */
	public void setColSize(int colSize) {
		this.colSize = colSize;
	}

	/**
	 * This method sets the row size attribute
	 * @param sizeRow an intger
	 */
	public static void setRowSize(int sizeRow) {
		rowSize = sizeRow;
	}

	/**
	 * This method sets the board attribute
	 * @param board a BoardDisplay object
	 */
	public void setBoard(BoardDisplay board) {
		this.board = board;
	}

	/**
	 * This method sets the keyboard attribute
	 * @param keyboard a KeyboardDisplay object
	 */
	public void setKeyboard(KeyboardDisplay keyboard) {
		this.keyboard = keyboard;
		board.updateKeyboard(keyboard);
	}

	/**
	 * This method returns the current BoardDiplay
	 * @return a BoardDisplay object
	 */
	public BoardDisplay getBoard() {
		return board;
	}
	
	/**
	 * this method returns the current KeyBoard Display
	 * @return a KeyboardDisplay object
	 */
	public KeyboardDisplay getKeyboard() {
		return keyboard;
	}

	/**
	 * returns the columns for each button in the KeyBoard display
	 * @return an ArrayList of Buttons
	 */
	public ArrayList<Button> getMultiColButtons() {
		return multiColButtons;
	}

	/**
	 * returns the rows for each button in the KeyBoard display
	 * @return an ArrayList of Buttons
	 */
	public ArrayList<Button> getMultiRowButtons() {
		return multiRowButtons;
	}

	/**
	 * returns the main center pan, which contains the KeyBoardDisplay and the
	 * BoardDisplay
	 * @return a StackPane Object
	 */
	public StackPane getMainCenterPane() {
		return mainCenterPane;
	}

	/**
	 * returns whether or not the Gui is in dark mode or not
	 * @return a boolean operator
	 */
	public boolean getDarkMode() {
		return isDarkMode;
	}

	/**
	 * returns whether or not the Gui is in contrast mode or not
	 * @return a boolean operator
	 */
	public boolean getContrastMode() {
		return isContrastMode;
	}

	/**
	 * returns whether or not the Gui is in hard mode or not
	 * @return a boolean operator
	 */
	public boolean getHardMode() {
		return isHardMode;
	}

	/**
	 * returns the column size of the BoardDisplay
	 * @return an integer
	 */
	public int getColSize() {
		return colSize;
	}

	/**
	 * returns the row size of the BoardDsiplaay
	 * @return an integer
	 */
	public static int getRowSize() {
		return rowSize;
	}

	@SuppressWarnings("unchecked")
	/**
	 * This method determines the current user
	 */

	public void readUsers() {
		// first determine if the file exists
		File file = new File("users.ser");
		if (file.exists()) {
			try {
				FileInputStream rawBytes = new FileInputStream("users.ser");
				ObjectInputStream inFile = new ObjectInputStream(rawBytes);
				// checks all objects to see if email matches
				allUsers = (ArrayList<WordleUser>) inFile.readObject();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else
		{
			System.out.println("users.ser file not found");
		}
	}

	/**
	 * This method sets the current user
	 * @param userToSet a WordleUser object
	 */
	public static void setUser(WordleUser userToSet) {
		wordleUser = userToSet;
		isDarkMode = wordleUser.isDarkMode();
		isContrastMode = wordleUser.isContrastMode();
		// set the new needed values
		board.setUser(wordleUser);
		board.setColors(isDarkMode);
		board.setUserData();

		board.updateKeyboard(keyboard);
	}

	/**
	 * this method adds a new WordleUser to allusers
	 * @param userToAdd a WordleUser object
	 */
	public static void writeUser(WordleUser userToAdd) {
		if (userToAdd == null) {
			userToAdd = wordleUser;
		}
		if (board != null) {
			board.updateUserGuesses();
		}
		// removes user if already exists
		for (WordleUser curUser : allUsers) {
			// when found removes so can be updated and added back later
			if (curUser.getEmail().equals(userToAdd.getEmail())) {
				allUsers.remove(curUser);
				break;
			}
		}
		// adds the modified user
		allUsers.add(userToAdd);
		try {
			FileOutputStream bytesToDisk = new FileOutputStream("users.ser");
			ObjectOutputStream outFile = new ObjectOutputStream(bytesToDisk);
			// write all the users to the ser file.
			outFile.writeObject(allUsers);
			outFile.close();
		} catch (IOException ioe) {
			System.out.println("Writing objects failed");
		}
	}

	/**
	 * This method finds the WordleUser with a given email
	 * @param email a string
	 * @return a WordleUser object or null
	 */
	public static WordleUser findUser(String email) {
		for (WordleUser curUser : allUsers) {
			// when found removes so can be updated and added back later
			if (curUser.getEmail().equals(email)) {
				return curUser;
			}
		}
		return null;
	}

	/**
	 * returns gets the current WordleUser
	 * @return a WordleUser object
	 */
	public WordleUser getUser() {
		return wordleUser;
	}
	
	/**
	 * Thus method determines if the gui is in high contrast mode
	 * @return a boolean operator
	 */
	public static boolean isContrastMode() {
		return isContrastMode;
	}
	
	/**
	 * this method returns the Stats Button
	 * @return a Button Object
	 */
	public Button getButton() {
		return statsButton;
	}
	
	/**
	 * This method resets the BoardDisplay and KeyboardDisplay
	 */
	public void resetBoard() {
		board.resetGuesses();
		board.updateKeyboard(keyboard);
		board = new BoardDisplay(colSize, rowSize, this);
		board.getGuess().getWord().setRandomWord(rowSize);
		keyboard = new KeyboardDisplay(board, colSize);
		setUpStage();
	}

}