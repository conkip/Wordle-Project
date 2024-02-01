/**
 * Creates the display for toggling hardmode, darkmode, contrast theme, and
 * starting a new game when the settings menu button is clicked.
 * 
 * Date: 4/30/2023
 * 
 * @author Connor Kippes, Luke Laurie, Sean Eddy, Zachary Hansen
 */

package src.view;

import java.util.HashMap;
import java.util.Map;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class SettingsDisplay extends VBox {
	private Label settingsLabel = new Label("SETTINGS");
	private Label darkLabel = new Label("Dark Theme");
	private Label resetLabel = new Label("Reset Game");
	private Button darkButton = new Button();
	private Button resetButton = new Button();
	private Rectangle darkBack = new Rectangle();  
	private StackPane darkSwitch = new StackPane();
	private Label darkIndent = new Label("");
	private Label contrastIndent = new Label("");
	private Label hardIndent = new Label("");
	private Label resetIndent = new Label("");
	private Label contrastLabel = new Label("High Contrast Mode");
	private Label underContrastLabel = new Label("For improved color vision");
	private Button contrastButton = new Button();
	private Rectangle contrastBack = new Rectangle();
	private StackPane contrastSwitch = new StackPane();
	
	private BorderPane darkPane = new BorderPane();
	private BorderPane contrastPane = new BorderPane();
	private BorderPane hardPane = new BorderPane();
	private BorderPane resetPane = new BorderPane();
	private StackPane errorPane = new StackPane();

	private Label hardLabel = new Label("Hard Mode");
	private Label underHardLabel = new Label("Any revealed hints must be used in subsequent guesses");
	private Button hardButton = new Button();
	private Rectangle hardBack = new Rectangle();
	private StackPane hardSwitch = new StackPane();
	private WordleGUI gui;
	private boolean darkOn;
	private boolean contrastOn;
	private boolean hardOn;
	private String onColor;
	private String offColor;
	private Map<String, String> colors = new HashMap<String, String>();
	
	/**
	 * Constructor sets up all of the buttons, labels, formatting, and other things in the settings display
	 * 
	 * @param gui the main WordleGUI
	 */
	public SettingsDisplay(WordleGUI gui) {
		colors.put("Green", "#6aaa64");
		colors.put("Orange", "#f5793a");
		colors.put("Gray", "#878a8c");
		colors.put("dm Gray", "#565758");
		colors.put("Black", "#232323");
		colors.put("Under Gray", "#787c7e");
		colors.put("dm Under Gray", "#818384");
		
		
		this.setSpacing(10);
		this.setPadding(new Insets(25, 25, 25, 25));
		darkOn = gui.getDarkMode();
		contrastOn = gui.getContrastMode();
		hardOn = gui.getHardMode();
		onColor = (contrastOn ? colors.get("Orange") : colors.get("Green"));
		offColor = (darkOn ? colors.get("dm Gray") : colors.get("Gray"));
		updateDarkMode();
		
		Font labelFont = Font.font("Arial", 16.8);
		Font underLabelFont = Font.font("Arial", 11.2);
		Font indentFont = Font.font("Arial", 15);
		
		darkIndent.setFont(indentFont);
		contrastIndent.setFont(indentFont);
		hardIndent.setFont(indentFont);
		darkLabel.setFont(labelFont);
		resetLabel.setFont(labelFont);
		contrastLabel.setFont(labelFont);
		underContrastLabel.setFont(underLabelFont);
		hardLabel.setFont(labelFont);
		underHardLabel.setFont(underLabelFont);
		settingsLabel.setFont(Font.loadFont("file:fonts/Helvetica Monospaced W06 Bold.ttf", 15.9));
		
		this.getChildren().add(settingsLabel);
		this.setAlignment(Pos.TOP_CENTER);
		this.gui = gui;

		double buttonSize = 16;
		double backWidth = 35;
		double backHeight = 22;
		
		VBox darkLabelPane = new VBox();
		darkLabelPane.getChildren().add(darkLabel);
		darkLabelPane.getChildren().add(darkIndent);
		
		
		darkButton.setShape(new Circle(9));
		darkButton.setMaxSize(buttonSize, buttonSize);
		darkButton.setMinSize(buttonSize, buttonSize);
		darkButton.setStyle("-fx-background-color: #ffffff");
		darkButton.setPadding(new Insets(2, 2, 2, 2));
		darkButton.setFocusTraversable(false);

		darkBack.setWidth(backWidth);
		darkBack.setHeight(backHeight);
		darkBack.setArcHeight(darkBack.getHeight());
		darkBack.setArcWidth(darkBack.getHeight());
		darkBack.setFill(Color.valueOf((darkOn ? onColor : offColor)));

		darkSwitch.getChildren().addAll(darkBack, darkButton);
		darkSwitch.setAlignment(darkButton, (darkOn ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT));
		darkSwitch.setPrefSize(backWidth, backHeight);
		darkSwitch.setMargin(darkButton, new Insets(2, 2, 2.5, 2));

		darkPane.setLeft(darkLabelPane);
		darkPane.setRight(darkSwitch);
		
		VBox resetLabelPane = new VBox();
		resetLabelPane.getChildren().add(resetLabel);
		resetLabelPane.getChildren().add(resetIndent);
		resetButton.setText("New Game");
		resetButton.setFont(Font.font("Arial", 14));
		resetButton.setStyle(darkOn ? "-fx-text-fill: " + colors.get("Black") + " ; -fx-background-color: #ffffff" :
							   "-fx-text-fill: #ffffff" + " ; -fx-background-color: " + colors.get("Gray") );
		resetButton.setPadding(new Insets(2, 2, 2.5, 2));
		resetButton.setFocusTraversable(false);


		resetPane.setLeft(resetLabelPane);
		resetPane.setRight(resetButton);
		

		VBox contrastLabelPane = new VBox();
		contrastLabelPane.getChildren().add(contrastLabel);
		contrastLabelPane.getChildren().add(underContrastLabel);
		contrastLabelPane.getChildren().add(contrastIndent);

		contrastButton.setShape(new Circle(9));
		contrastButton.setMaxSize(buttonSize, buttonSize);
		contrastButton.setMinSize(buttonSize, buttonSize);
		contrastButton.setStyle("-fx-background-color: #ffffff");
		contrastButton.setFocusTraversable(false);

		contrastBack.setWidth(backWidth);
		contrastBack.setHeight(backHeight);
		contrastBack.setArcHeight(contrastBack.getHeight());
		contrastBack.setArcWidth(contrastBack.getHeight());
		contrastBack.setFill(Color.valueOf((contrastOn ? onColor : offColor)));

		contrastSwitch.getChildren().addAll(contrastBack, contrastButton);
		contrastSwitch.setAlignment(contrastButton, (contrastOn ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT));
		contrastSwitch.setPrefSize(backWidth, backHeight);
		contrastSwitch.setMargin(contrastButton, new Insets(2, 2, 2.5, 2));

		contrastPane.setLeft(contrastLabelPane);
		contrastPane.setRight(contrastSwitch);

		VBox hardLabelPane = new VBox();
		hardLabelPane.getChildren().add(hardLabel);
		hardLabelPane.getChildren().add(underHardLabel);
		hardLabelPane.getChildren().add(hardIndent);

		hardButton.setShape(new Circle(9));
		hardButton.setMaxSize(buttonSize, buttonSize);
		hardButton.setMinSize(buttonSize, buttonSize);
		hardButton.setStyle("-fx-background-color: #ffffff");
		hardButton.setPadding(new Insets(2, 2, 2, 2));
		hardButton.setFocusTraversable(false);

		hardBack.setWidth(backWidth);
		hardBack.setHeight(backHeight);
		hardBack.setArcHeight(hardBack.getHeight());
		hardBack.setArcWidth(hardBack.getHeight());
		hardBack.setFill(Color.valueOf((hardOn ? onColor : offColor)));

		hardSwitch.getChildren().addAll(hardBack, hardButton);
		hardSwitch.setAlignment(hardButton, (hardOn ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT));
		hardSwitch.setPrefSize(backWidth, backHeight);
		hardSwitch.setMargin(hardButton, new Insets(2, 2, 2.5, 2));
		
		hardPane.setLeft(hardLabelPane);
		hardPane.setRight(hardSwitch);
		
		errorPane.getChildren().add(hardPane);
		
		this.getChildren().addAll(errorPane, darkPane, contrastPane, resetPane);
		
		Border underLine = new Border(new BorderStroke(Color.valueOf(darkOn ? "#303031" : "#dee0e3"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
				new BorderWidths(0, 0, 1, 0)));
		hardPane.setBorder(underLine);
		darkPane.setBorder(underLine);
		contrastPane.setBorder(underLine);
		resetPane.setBorder(underLine);
		
		registerHandlers();
	}
	
	/**
	 * registers all of the button handlers
	 */
	private void registerHandlers() {
		darkSwitch.setOnMouseClicked(new DarkButtonHandler());
		darkButton.setOnMouseClicked(new DarkButtonHandler());

		contrastSwitch.setOnMouseClicked(new ContrastButtonHandler());
		contrastButton.setOnMouseClicked(new ContrastButtonHandler());

		hardSwitch.setOnMouseClicked(new HardButtonHandler());
		hardButton.setOnMouseClicked(new HardButtonHandler());
		
		resetButton.setOnMouseClicked(new ResetButtonHandler());
		
	}
	
	/**
	 * dark mode button handler class for toggling dark mode
	 * 
	 * @author Connor Kippes, Luke Laurie, Sean Eddy, Zachary Hansen
	 */
	private class DarkButtonHandler implements EventHandler<Event> {
		@Override
		public void handle(Event ae) {
			if (darkOn) {
				darkSwitch.setAlignment(darkButton, Pos.CENTER_LEFT);
				offColor = colors.get("Gray");
				darkBack.setFill(Color.valueOf(offColor));
				resetButton.setStyle("-fx-text-fill: #ffffff" + " ; -fx-background-color: " + colors.get("Gray"));

				if (!contrastOn) {
					contrastBack.setFill(Color.valueOf(offColor));
				}
				if (!hardOn) {
					hardBack.setFill(Color.valueOf(offColor));
				}
				darkOn = false;
				gui.setNonDarkMode();
				updateDarkMode();
			} else {
				darkSwitch.setAlignment(darkButton, Pos.CENTER_RIGHT);
				offColor = colors.get("dm Gray");
				darkBack.setFill(Color.valueOf(onColor));
				resetButton.setStyle("-fx-text-fill: " + colors.get("Black") + " ; -fx-background-color: #ffffff");

				if (!contrastOn) {
					contrastBack.setFill(Color.valueOf(offColor));
				}
				if (!hardOn) {
					hardBack.setFill(Color.valueOf(offColor));
				}
				darkOn = true;
				gui.setDarkMode();
				updateDarkMode();
			}
		}
	}
	
	/**
	 * DarkButton handler class for toggling dark mode
	 * 
	 * @author Connor Kippes, Luke Laurie, Sean Eddy, Zachary Hansen
	 */
	private void updateDarkMode() {
		this.setStyle("-fx-background-color: " + (darkOn ? "#121213" : "white") + ";");
		settingsLabel.setTextFill(darkOn ? Color.WHITE : Color.BLACK);
		resetLabel.setTextFill(darkOn ? Color.WHITE : Color.BLACK);
		darkLabel.setTextFill(darkOn ? Color.WHITE : Color.valueOf(colors.get("Black")));
		contrastLabel.setTextFill(darkOn ? Color.WHITE : Color.valueOf(colors.get("Black")));
		underContrastLabel.setTextFill(darkOn ? Color.valueOf(colors.get("dm Under Gray")) : Color.valueOf(colors.get("Under Gray")));
		hardLabel.setTextFill(darkOn ? Color.WHITE : Color.valueOf(colors.get("Black")));
		underHardLabel.setTextFill(darkOn ? Color.valueOf(colors.get("dm Under Gray")) : Color.valueOf(colors.get("Under Gray")));
		Border underLine = new Border(new BorderStroke(Color.valueOf(darkOn ? "#303031" : "#dee0e3"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
				new BorderWidths(0, 0, 1, 0)));
		hardPane.setBorder(underLine);
		darkPane.setBorder(underLine);
		contrastPane.setBorder(underLine);
		resetPane.setBorder(underLine);
	}
	
	/**
	 * contrast mode handler class for toggling contrast mode
	 * 
	 * @author Connor Kippes, Luke Laurie, Sean Eddy, Zachary Hansen
	 */
	private class ContrastButtonHandler implements EventHandler<Event> {
		@Override
		public void handle(Event ae) {
			if (contrastOn) {
				Map<String, String> keyboardSavedColorsMap = gui.getKeyboard().getSavedLetterColors();
				for (Map.Entry<String, String> entry : keyboardSavedColorsMap.entrySet()) {
					String key = entry.getKey();
					String value = entry.getValue();
					if (value.equals("Orange")) {
						keyboardSavedColorsMap.put(key, "Green");
					} else if (value.equals("Blue")) {
						keyboardSavedColorsMap.put(key, "Yellow");
					}
				}

				gui.setContrastMode(false);

				gui.getKeyboard().setNonContrastMode();
				gui.getBoard().setNonContrastMode();

				contrastSwitch.setAlignment(contrastButton, Pos.CENTER_LEFT);
				onColor = colors.get("Green");
				contrastBack.setFill(Color.valueOf(offColor));
				if (darkOn) {
					darkBack.setFill(Color.valueOf(onColor));
				}
				if (hardOn) {
					hardBack.setFill(Color.valueOf(onColor));
				}
				contrastOn = false;
			} else {
				Map<String, String> keyboardSavedColorsMap = gui.getKeyboard().getSavedLetterColors();
				for (Map.Entry<String, String> entry : keyboardSavedColorsMap.entrySet()) {
					String key = entry.getKey();
					String value = entry.getValue();
					if (value.equals("Green")) {
						keyboardSavedColorsMap.put(key, "Orange");
					} else if (value.equals("Yellow")) {
						keyboardSavedColorsMap.put(key, "Blue");
					}
				}

				gui.setContrastMode(true);

				gui.getKeyboard().setContrastMode();
				gui.getBoard().setContrastMode();
				contrastSwitch.setAlignment(contrastButton, Pos.CENTER_RIGHT);
				onColor = colors.get("Orange");
				contrastBack.setFill(Color.valueOf(onColor));
				if (darkOn) {
					darkBack.setFill(Color.valueOf(onColor));
				}
				if (hardOn) {
					hardBack.setFill(Color.valueOf(onColor));
				}
				contrastOn = true;
			}
		}
	}
	
	/**
	 * hard mode handler class for toggling hard mode
	 * 
	 * @author Connor Kippes, Luke Laurie, Sean Eddy, Zachary Hansen
	 */
	private class HardButtonHandler implements EventHandler<Event> {
		@Override
		public void handle(Event ae) {
			if (hardOn) {
				if (gui.getBoard().getCurRow() == 0) {
					gui.setHardMode(false);
					hardSwitch.setAlignment(hardButton, Pos.CENTER_LEFT);
					hardBack.setFill(Color.valueOf(offColor));
					hardOn = false;
				} else {
					gui.getBoard().popUpErrorMessage("Hard mode can only be enabled at the start of a round", errorPane);
				}
			} else {
				if (gui.getBoard().getCurRow() == 0) {
					gui.setHardMode(true);
					hardSwitch.setAlignment(hardButton, Pos.CENTER_RIGHT);
					hardBack.setFill(Color.valueOf(onColor));
					hardOn = true;
				} else {
					gui.getBoard().popUpErrorMessage("Hard mode can only be enabled at the start of a round", errorPane);
				}
			}
		}
	}
	
	/**
	 * reset button handler class for starting a new game
	 * 
	 * @author Connor Kippes, Luke Laurie, Sean Eddy, Zachary Hansen
	 */
	private class ResetButtonHandler implements EventHandler<Event>{
		@Override
		public void handle(Event ae) {
			gui.resetBoard();
			
		}
	}
}