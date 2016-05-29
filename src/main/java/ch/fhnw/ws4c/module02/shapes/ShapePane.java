package ch.fhnw.ws4c.module02.shapes;

import javafx.scene.layout.HBox;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

/**
 * @author Dieter Holz
 */
public class ShapePane extends HBox {
	private Line      line;
	private Rectangle rectangle;
	private Arc arc;

	public ShapePane() {
		initializeControls();
		layoutControls();
	}

	private void initializeControls() {
		line = new Line(0, 0, 100, 100);
		line.getStyleClass().add("linePlayground");

		rectangle = new Rectangle(200, 100);
		rectangle.getStyleClass().add("rectPlayground");

		arc = new Arc(100, 100, 50, 50, 90, 200);
		arc.getStyleClass().add("arcPlayground");

		

	}

	private void layoutControls() {
		getChildren().addAll(line, rectangle, arc);
	}
}
