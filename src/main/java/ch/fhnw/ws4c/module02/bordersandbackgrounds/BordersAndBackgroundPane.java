package ch.fhnw.ws4c.module02.bordersandbackgrounds;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;

/**
 * @author Dieter Holz
 */
public class BordersAndBackgroundPane extends HBox {
	private Region regionA;
	private Region regionB;
	private Region regionC;

	public BordersAndBackgroundPane() {
		initializeControls();
		layoutControls();
	}

	private void initializeControls() {
		regionA = new Region();
		regionA.getStyleClass().addAll("playground", "backgroundPlayground");

		regionB = new Region();
		regionB.getStyleClass().addAll("playground", "borderPlayground");

		regionC = new Region();
		regionC.getStyleClass().addAll("playground", "borderAndBackgroundPlayground");
	}

	private void layoutControls() {
		setHgrow(regionA, Priority.ALWAYS);
		setHgrow(regionB, Priority.ALWAYS);
		getChildren().addAll(regionA, regionB, regionC);
	}
}
