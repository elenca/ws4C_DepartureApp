package ch.fhnw.ws4c.module02.path;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Dieter Holz
 */
public class PathPlayground extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent rootPanel = new PathPane();

		Scene scene = new Scene(rootPanel);

		String fonts = getClass().getResource("fonts.css").toExternalForm();
		scene.getStylesheets().add(fonts);

		String stylesheet = getClass().getResource("style.css").toExternalForm();
		scene.getStylesheets().add(stylesheet);

		primaryStage.setTitle("Path Playground");
		primaryStage.setScene(scene);

		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
