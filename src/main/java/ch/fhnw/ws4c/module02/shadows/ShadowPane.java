package ch.fhnw.ws4c.module02.shadows;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

/**
 * @author Dieter Holz
 */
public class ShadowPane extends HBox {
	private Region regionA;
	private Region regionB;

	public ShadowPane() {
		initializeControls();
		layoutControls();
	}

	private void initializeControls() {
		regionA = new Region();
		regionA.getStyleClass().addAll("playground", "dropShadowPlayground");

		regionB = new Region();
		regionB.getStyleClass().addAll("playground", "innerShadowPlayground");
	}

	private void layoutControls() {
		setHgrow(regionA, Priority.ALWAYS);
		setHgrow(regionB, Priority.ALWAYS);
		getChildren().addAll(regionA, regionB);
	}
}
