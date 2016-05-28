package ch.fhnw.ws4c.module02.gradients;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

/**
 * @author Dieter Holz
 */
public class GradientPane extends HBox {
	private Region regionA;
	private Region regionB;

	public GradientPane() {
		initializeControls();
		layoutControls();
	}

	private void initializeControls() {
		regionA = new Region();
		regionA.getStyleClass().addAll("playground", "linearGradientPlayground");

		regionB = new Region();
		regionB.getStyleClass().addAll("playground", "radialGradientPlayground");
	}

	private void layoutControls() {
		setHgrow(regionA, Priority.ALWAYS);
		setHgrow(regionB, Priority.ALWAYS);
		getChildren().addAll(regionA, regionB);
	}
}
