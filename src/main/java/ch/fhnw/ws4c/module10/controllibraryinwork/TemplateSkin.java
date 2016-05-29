package ch.fhnw.ws4c.module10.controllibraryinwork;

import javafx.geometry.Insets;
import javafx.scene.control.Control;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.Pane;

/**
 * @author Dieter Holz
 */
class TemplateSkin extends SkinBase<Control> {
	private static final String FONTS_CSS = "fonts.css";
	private static final String STYLE_CSS = "templateStyle.css";

	private static final double PREFERRED_WIDTH  = 250;
	private static final double PREFERRED_HEIGHT = 265;

	private static final double ASPECT_RATIO = PREFERRED_WIDTH / PREFERRED_HEIGHT;

	private static final double MINIMUM_WIDTH  = 80;
	private static final double MINIMUM_HEIGHT = MINIMUM_WIDTH / ASPECT_RATIO;

	private static final double MAXIMUM_WIDTH  = 800;
	private static final double MAXIMUM_HEIGHT = MAXIMUM_WIDTH / ASPECT_RATIO;

	// all parts

	private Pane drawingPane;

	// animations

	TemplateSkin(Control control) {
		super(control);
		init();
		initializeParts();
		layoutParts();
		addEventHandlers();
		addValueChangedListeners();
		addBindings();

	}

	private void init() {
		String fonts = getClass().getResource(FONTS_CSS).toExternalForm();
		getSkinnable().getStylesheets().add(fonts);

		String stylesheet = getClass().getResource(STYLE_CSS).toExternalForm();
		getSkinnable().getStylesheets().add(stylesheet);
	}

	private void initializeParts() {

		// always needed
		drawingPane = new Pane();
		drawingPane.setMaxSize(PREFERRED_WIDTH, PREFERRED_HEIGHT);
		drawingPane.setMinSize(PREFERRED_WIDTH, PREFERRED_HEIGHT);
		drawingPane.setPrefSize(PREFERRED_WIDTH, PREFERRED_HEIGHT);
	}

	private void layoutParts() {
		drawingPane.getChildren().addAll();
		getChildren().add(drawingPane);
	}

	private void addEventHandlers() {
	}

	private void addValueChangedListeners() {

		// always needed
		getSkinnable().widthProperty().addListener((observable, oldValue, newValue) -> resize());
		getSkinnable().heightProperty().addListener((observable, oldValue, newValue) -> resize());
	}

	private void addBindings() {
	}

	private void resize() {
		Insets padding         = getSkinnable().getPadding();
		double availableWidth  = getSkinnable().getWidth() - padding.getLeft() - padding.getRight();
		double availableHeight = getSkinnable().getHeight() - padding.getTop() - padding.getBottom();

		double width = Math.max(Math.min(Math.min(availableWidth, availableHeight * ASPECT_RATIO), MAXIMUM_WIDTH), MINIMUM_WIDTH);

		double scalingFactor = width / PREFERRED_WIDTH;

		if (availableWidth > 0 && availableHeight > 0) {
			drawingPane.relocate((getSkinnable().getWidth() - PREFERRED_WIDTH) * 0.5, (getSkinnable().getHeight() - PREFERRED_HEIGHT) * 0.5);
			drawingPane.setScaleX(scalingFactor);
			drawingPane.setScaleY(scalingFactor);
		}
	}

	// compute sizes

	@Override
	protected double computeMinWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
		double horizontalPadding = leftInset + rightInset;

		return MINIMUM_WIDTH + horizontalPadding;
	}

	@Override
	protected double computeMinHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
		double verticalPadding = topInset + bottomInset;

		return MINIMUM_HEIGHT + verticalPadding;
	}

	@Override
	protected double computePrefWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
		double horizontalPadding = leftInset + rightInset;

		return PREFERRED_WIDTH + horizontalPadding;
	}

	@Override
	protected double computePrefHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
		double verticalPadding = topInset + bottomInset;

		return PREFERRED_HEIGHT + verticalPadding;
	}

	@Override
	protected double computeMaxWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
		double horizontalPadding = leftInset + rightInset;

		return MAXIMUM_WIDTH + horizontalPadding;
	}

	@Override
	protected double computeMaxHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
		double verticalPadding = topInset + bottomInset;

		return MAXIMUM_HEIGHT + verticalPadding;
	}
}
