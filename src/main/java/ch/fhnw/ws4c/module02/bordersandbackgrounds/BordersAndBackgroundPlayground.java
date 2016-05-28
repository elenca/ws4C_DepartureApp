package ch.fhnw.ws4c.module02.bordersandbackgrounds;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Dieter Holz
 */
public class BordersAndBackgroundPlayground extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent rootPanel = new BordersAndBackgroundPane();

		Scene scene = new Scene(rootPanel);

		String fonts = getClass().getResource("fonts.css").toExternalForm();
		scene.getStylesheets().add(fonts);

		String stylesheet = getClass().getResource("style.css").toExternalForm();
		scene.getStylesheets().add(stylesheet);

		primaryStage.setTitle("Borders-Background-Playground");
		primaryStage.setScene(scene);

		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
