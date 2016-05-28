package ch.fhnw.ws4c.module02.path;

import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

/**
 * @author Dieter Holz
 */
public class PathPane extends HBox {
	private Region regionA;
	private DropShadow dropShadow;


	public PathPane() {
		initializeControls();
		layoutControls();
	}

	private void initializeControls() {
		dropShadow = new DropShadow();
		dropShadow.setRadius(11.0);
		dropShadow.setOffsetX(5.0);
		dropShadow.setOffsetY(5.0);
		dropShadow.setColor(Color.color(0.4, 0.5, 0.5, 0.75));


		regionA = new Region();
		regionA.getStyleClass().addAll("playground", "pathPlayground");
	}

	private void layoutControls() {
		setHgrow(regionA, Priority.ALWAYS);
		getChildren().addAll(regionA);
	}
}
