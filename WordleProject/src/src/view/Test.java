package src.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Test extends Application
{
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane pane = new BorderPane();
		Scene scene = new Scene(pane, 300, 300);
		
		Button button = new Button("hello");
		//button.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
		button.setFont(Font.loadFont("file:src/fonts/Helvetica Monospaced W06 Bold.ttf", 19));
		pane.setCenter(button);
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
