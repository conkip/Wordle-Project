/**
 * Creates the beginning display for choosing the length of word and number of guesses you want
 * 
 * Date: 4/30/2023
 * 
 * @author Connor Kippes, Luke Laurie, Sean Eddy, Zachary Hansen
 */
package src.view;

import java.util.ArrayList;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MultiWordle extends VBox {
	private ArrayList<Button> multiColButtons = new ArrayList<>();
	private ArrayList<Button> multiRowButtons = new ArrayList<>();
	private Stage multiWordleStage;
	private WordleGUI gui;
	int colSelcted = 5;
	int rowSelected = 6;
	private Font primaryFont = Font.loadFont("file:src/fonts/Helvetica Monospaced W06 Bold.ttf", 20);
	private Font secondaryFont = Font.loadFont("file:src/fonts/Helvetica Monospaced W06 Bold.ttf", 16);

	/**
	 * Constructor calls the setUpPane method and local WordleGUI instance variable
	 * 
	 * @param gui the WordleGUI
	 */
	public MultiWordle(WordleGUI gui) {
		this.gui = gui;
		setUpPane();
	}

	/**
	 * sets up all the buttons and format of the display
	 */
	public void setUpPane() {
		multiWordleStage = new Stage();
		HBox buttonPane = new HBox();
		VBox colButtonPane = new VBox();
		VBox rowButtonPane = new VBox();
		colButtonPane.setSpacing(25);
		rowButtonPane.setSpacing(25);
		buttonPane.setSpacing(25);
		this.setSpacing(25);

		for (int i = 3; i < 8; i++) {
			Button button = new Button(i + " Letter Wordle");
			button.setStyle("-fx-text-fill: #ffffff; -fx-background-color: #3a3a3c; -fx-background-radius: 10px");
			button.setFont(secondaryFont);
			button.setFocusTraversable(false);
			//button.setMaxSize(100, 50);
			//button.setMinSize(100, 50);
			multiColButtons.add(button);
			colButtonPane.getChildren().add(button);
		}

		for (int i = 3; i < 8; i++) {
			MultiWordleButtonHandler handler = new MultiWordleButtonHandler(-1, gui.getRowSize(), i, gui,
					multiColButtons.get(i - 3));
			multiColButtons.get(i - 3).setOnAction(handler);
		}

		for (int i = 3; i < 8; i++) {
			Button button;
			if (i == 3) {
				button = new Button(i + " Guesses");
			} else {
				button = new Button(i + " Many Guesses");
			}
			button.setStyle("-fx-text-fill: #ffffff; -fx-background-color: #3a3a3c; -fx-background-radius: 10px");
			button.setFont(secondaryFont);
			button.setFocusTraversable(false);
			multiRowButtons.add(button);
			rowButtonPane.getChildren().add(button);
		}

		for (int i = 3; i < 8; i++) {
			MultiWordleButtonHandler handler = new MultiWordleButtonHandler(gui.getColSize(), -1, i, gui,
					multiRowButtons.get(i - 3));
			multiRowButtons.get(i - 3).setOnAction(handler);
		}

		Button defaultWordleButton = new Button("Default Wordle");
		defaultWordleButton
				.setStyle("-fx-text-fill: #ffffff; -fx-background-color: #3a3a3c; -fx-background-radius: 10px");
		defaultWordleButton.setFont(primaryFont);
		defaultWordleButton.setFocusTraversable(false);
		defaultWordleButton.setOnAction((event) -> {
			setupInitial();
			if(gui.getMainCenterPane().getChildren().size() > 1)
			{
				gui.getMainCenterPane().getChildren().get(gui.getMainCenterPane().getChildren().size()-2).toFront();
			}
			multiWordleStage.close();
		});

		Button okButton = new Button("Ok");
		okButton.setStyle("-fx-text-fill: #ffffff; -fx-background-color: #3a3a3c; -fx-background-radius: 10px");
		okButton.setFont(Font.loadFont("file:src/fonts/Helvetica Monospaced W06 Bold.ttf", 30));
		okButton.setFocusTraversable(false);
		okButton.setOnAction((event) -> {
			setupInitial();
		});

		buttonPane.getChildren().addAll(colButtonPane, rowButtonPane);
		this.getChildren().addAll(defaultWordleButton, buttonPane, okButton);

		Scene scene = new Scene(this, 600, 600);
		this.setStyle("-fx-background-color: #818384");
		buttonPane.setAlignment(Pos.CENTER);
		this.setAlignment(Pos.CENTER);
		multiWordleStage.setScene(scene);
		multiWordleStage.show();
		multiWordleStage.setOnCloseRequest(event -> {
			setupInitial();
		});
	}

	/**
	 * Initializes the board and keyboard and calls setUpStage
	 */
	private void setupInitial() {
		gui.setBoard(new BoardDisplay(colSelcted, rowSelected, gui));
		gui.setKeyboard(new KeyboardDisplay(gui.getBoard(), colSelcted));
		gui.setColSize(colSelcted);
		gui.setRowSize(rowSelected);
		gui.setUpStage();
		// starts board at the correct color
		if (gui.getUser().isDarkMode()) {
			gui.setDarkMode();
		}
		if (gui.getUser().isContrastMode()) {
			gui.setContrastMode(true);
			gui.getKeyboard().setContrastMode();
			gui.getBoard().setContrastMode();
		}
		multiWordleStage.close();
	}

	/**
	 * button handler class for the row and col select buttons on the display to
	 * turn them green when selected
	 * 
	 * @author Connor Kippes, Luke Laurie, Sean Eddy, Zachary Hansen
	 */
	private class MultiWordleButtonHandler implements EventHandler<ActionEvent> {
		private int tempColSize;
		private int tempRowSize;
		private int buttonNum;
		private WordleGUI gui;

		public MultiWordleButtonHandler(int colSize, int rowSize, int buttonNum, WordleGUI gui, Button button) {
			this.buttonNum = buttonNum;
			this.tempColSize = colSize;
			this.tempRowSize = rowSize;
			this.gui = gui;
		}

		@Override
		public void handle(ActionEvent ae) {
			if (tempColSize == -1) {
				colSelcted = buttonNum;
				for (Button button : multiColButtons) {
					button.setStyle(
							"-fx-text-fill: #ffffff; -fx-background-color: #3a3a3c; -fx-background-radius: 10px");
					button.setFont(secondaryFont);
				}
				multiColButtons.get(buttonNum - 3).setStyle("-fx-text-fill: #ffffff; -fx-background-color: #6aaa64");
			} else if (tempRowSize == -1) {
				rowSelected = buttonNum;
				for (Button button : multiRowButtons) {
					button.setStyle(
							"-fx-text-fill: #ffffff; -fx-background-color: #3a3a3c; -fx-background-radius: 10px");
					button.setFont(secondaryFont);
				}
				multiRowButtons.get(buttonNum - 3).setStyle("-fx-text-fill: #ffffff; -fx-background-color: #6aaa64");
			}
		}
	}
}