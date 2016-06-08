package sbbClockEM.controllibraryinwork;

import javafx.geometry.Insets;
import javafx.scene.control.SkinBase;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;

/**
 * @author Dieter Holz
 */
class PieSkin extends SkinBase<NumberRangeControl> { //vorher: <Control>, neu für <NumberRange...
	private static final String FONTS_CSS = "fonts.css";
	private static final String STYLE_CSS = "pieStyle.css";

	private static final double PREFERRED_WIDTH  = 180;
	private static final double PREFERRED_HEIGHT = 30;

	private static final double ASPECT_RATIO = PREFERRED_WIDTH / PREFERRED_HEIGHT;

	private static final double MINIMUM_WIDTH  = 120;
	private static final double MINIMUM_HEIGHT = MINIMUM_WIDTH / ASPECT_RATIO;

	private static final double MAXIMUM_WIDTH  = 300;
	private static final double MAXIMUM_HEIGHT = MAXIMUM_WIDTH / ASPECT_RATIO;

	// all parts
	private Circle border;
	private Arc pieSlice;
	private TextField textField;

	private Pane drawingPane;

	// animations

	PieSkin(NumberRangeControl control) {
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
		// Live Coding
		double center = PREFERRED_HEIGHT * 0.5;
		border = new Circle(center, center, center);
		border.getStyleClass().add("border");

		// Live Coding
		pieSlice = new Arc(center, center, center, center, 90, 0);
		pieSlice.getStyleClass().add("pieSlice");
		pieSlice.setType(ArcType.ROUND);

		// always needed
		drawingPane = new Pane();
		drawingPane.setMaxSize(PREFERRED_WIDTH, PREFERRED_HEIGHT);
		drawingPane.setMinSize(PREFERRED_WIDTH, PREFERRED_HEIGHT);
		drawingPane.setPrefSize(PREFERRED_WIDTH, PREFERRED_HEIGHT);
	}

	private void layoutParts() {
		drawingPane.getChildren().addAll(pieSlice, border);
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
		pieSlice.lengthProperty().bind(getSkinnable().angleProperty());
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
