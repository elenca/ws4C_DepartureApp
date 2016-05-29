package ch.fhnw.ws4c.colormemoryinwork;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppStarter extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		CoolColorMemoryPM model = new CoolColorMemoryPM();

		Parent rootPanel = new ColorMemoryUI(model);

		Scene scene = new Scene(rootPanel);

		String stylesheet = getClass().getResource("style.css").toExternalForm();
		scene.getStylesheets().add(stylesheet);

		primaryStage.titleProperty().bind(model.windowTitleProperty());
		primaryStage.setScene(scene);

		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
