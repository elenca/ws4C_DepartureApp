package ch.fhnw.ws4c.mySimplecontrol;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * @author Dieter Holz
 */
public class MySimpleControl extends Region {
	private static final String FONTS_CSS = "fonts.css";
	private static final String STYLE_CSS = "style.css";

	private static final double PREFERRED_WIDTH  = 300;
	private static final double PREFERRED_HEIGHT = 300;

	private static final double ASPECT_RATIO = PREFERRED_WIDTH / PREFERRED_HEIGHT;

	private static final double MINIMUM_WIDTH  = 80;
	private static final double MINIMUM_HEIGHT = MINIMUM_WIDTH / ASPECT_RATIO;

	private static final double MAXIMUM_WIDTH  = 800;

	// all parts
	private Arc arcZ;
	//private Rectangle frame;

	private Pane drawingPane;

	// all properties
	private final StringProperty text = new SimpleStringProperty("Wow!");

	public MySimpleControl() {
		init();
		initializeParts();
		layoutParts();
		addEventHandlers();
		addValueChangedListeners();
		addBindings();
	}

	private void init() {
		addStyleSheets(this);
		getStyleClass().add(getStyleClassName());
	}

	private void initializeParts() {
		arcZ = new Arc();
		arcZ.setCenterX(50.0f);
		arcZ.setCenterY(50.0f);
		arcZ.setRadiusX(25.0f);
		arcZ.setRadiusY(25.0f);
		arcZ.setStartAngle(45.0f);
		arcZ.setLength(270.0f);
		arcZ.setType(ArcType.ROUND);

		relocateDisplay();

		// always needed
		drawingPane = new Pane();
		drawingPane.setMaxSize(PREFERRED_WIDTH, PREFERRED_HEIGHT);
		drawingPane.setMinSize(PREFERRED_WIDTH, PREFERRED_HEIGHT);
		drawingPane.setPrefSize(PREFERRED_WIDTH, PREFERRED_HEIGHT);
	}

	private void layoutParts() {
		drawingPane.getChildren().addAll(arcZ);
		getChildren().add(drawingPane);
	}

	private void addEventHandlers() {
	}

	private void addValueChangedListeners() {
		textProperty().addListener((observable, oldValue, newValue) -> {
			arcZ.setLength(Double.parseDouble(newValue));
			relocateDisplay();
		});

		// always needed
		widthProperty().addListener((observable, oldValue, newValue) -> resize());
		heightProperty().addListener((observable, oldValue, newValue) -> resize());
	}

	private void addBindings() {
	}

	private void resize() {
		Insets padding         = getPadding();
		double availableWidth  = getWidth() - padding.getLeft() - padding.getRight();
		double availableHeight = getHeight() - padding.getTop() - padding.getBottom();

		double width = Math.max(Math.min(Math.min(availableWidth, availableHeight * ASPECT_RATIO), MAXIMUM_WIDTH), MINIMUM_WIDTH);

		double scalingFactor = width / PREFERRED_WIDTH;

		if (availableWidth > 0 && availableHeight > 0) {
			drawingPane.relocate((getWidth() - PREFERRED_WIDTH) * 0.5, (getHeight() - PREFERRED_HEIGHT) * 0.5);
			drawingPane.setScaleX(scalingFactor);
			drawingPane.setScaleY(scalingFactor);
		}
	}

	private void relocateDisplay() {
		arcZ.setCenterX((PREFERRED_WIDTH - arcZ.getLayoutBounds().getWidth()) * 0.5);
	}

	// some useful helper-methods

	private void applyCss(Node node) {
		Group group = new Group(node);
		group.getStyleClass().add(getStyleClassName());
		addStyleSheets(group);
		new Scene(group);
		node.applyCss();
	}

	private void addStyleSheets(Parent parent) {
		String fonts = getClass().getResource(FONTS_CSS).toExternalForm();
		parent.getStylesheets().add(fonts);

		String stylesheet = getClass().getResource(STYLE_CSS).toExternalForm();
		parent.getStylesheets().add(stylesheet);
	}

	private String getStyleClassName() {
		String className = this.getClass().getSimpleName();

		return className.substring(0, 1).toLowerCase() + className.substring(1);
	}

	// compute sizes

	@Override
	protected double computeMinWidth(double height) {
		Insets padding = getPadding();
		double horizontalPadding = padding.getLeft() + padding.getRight();

		return MINIMUM_WIDTH + horizontalPadding;
	}

	@Override
	protected double computeMinHeight(double width) {
		Insets padding = getPadding();
		double verticalPadding   = padding.getTop() + padding.getBottom();

		return MINIMUM_HEIGHT + verticalPadding;
	}

	@Override
	protected double computePrefWidth(double height) {
		Insets padding = getPadding();
		double horizontalPadding = padding.getLeft() + padding.getRight();

		return PREFERRED_WIDTH + horizontalPadding;
	}

	@Override
	protected double computePrefHeight(double width) {
		Insets padding = getPadding();
		double verticalPadding   = padding.getTop() + padding.getBottom();

		return PREFERRED_HEIGHT + verticalPadding;
	}


	// getter and setter for all properties

	public String getText() {
		return text.get();
	}

	public StringProperty textProperty() {
		return text;
	}

	public void setText(String text) {
		this.text.set(text);
	}
}
