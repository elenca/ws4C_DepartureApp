package CustomControl.myFahrplanControll;

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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * @author Dieter Holz
 */
public class MyFahrplanControl extends Region {
	private static final String FONTS_CSS = "fonts.css";
	private static final String STYLE_CSS = "style.css";

	private static final double PREFERRED_WIDTH  = 250;
	private static final double PREFERRED_HEIGHT = 250;

	private static final double ASPECT_RATIO = PREFERRED_WIDTH / PREFERRED_HEIGHT;

	private static final double MINIMUM_WIDTH  = 25;
	private static final double MINIMUM_HEIGHT = MINIMUM_WIDTH / ASPECT_RATIO;

	private static final double MAXIMUM_WIDTH  = 800;

	// all parts
	/* private Rectangle 	background; */
	private Text      	display;
	private Region 		background;
	private Region		border;
	private Text 		gleis;

	private Pane drawingPane;

	// all properties
	private final StringProperty text = new SimpleStringProperty("5");


	public MyFahrplanControl() {
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
		/*
		background = new Rectangle(0.0, 0.0, PREFERRED_WIDTH, PREFERRED_HEIGHT);
		background.getStyleClass().add("background");
		*/

		background = new Region();
		background.getStyleClass().add("background");
		background.setLayoutX(0);
		background.setLayoutY(0);

		border = new Region();
		border.getStyleClass().add("border");
		border.setLayoutX(20);
		border.setLayoutY(21);

		gleis = new Text("Gleis");
		gleis.setX(45);
		gleis.setY(70);
		gleis.getStyleClass().add("gleis");
		applyCss(gleis);

		display = new Text(getText());
		display.setTextOrigin(VPos.CENTER);
		display.setTextAlignment(TextAlignment.CENTER);
		display.setY(170);
		display.getStyleClass().add("display");
		applyCss(display);

		relocateDisplay();

		// always needed
		drawingPane = new Pane();
		drawingPane.setMaxSize(PREFERRED_WIDTH, PREFERRED_HEIGHT);
		drawingPane.setMinSize(PREFERRED_WIDTH, PREFERRED_HEIGHT);
		drawingPane.setPrefSize(PREFERRED_WIDTH, PREFERRED_HEIGHT);
	}

	private void layoutParts() {
		drawingPane.getChildren().addAll(background, border, gleis, display);
		getChildren().add(drawingPane);
	}

	private void addEventHandlers() {
	}

	private void addValueChangedListeners() {
		textProperty().addListener((observable, oldValue, newValue) -> {
			display.setText(newValue);
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
		display.setX((PREFERRED_WIDTH - display.getLayoutBounds().getWidth()) * 0.5);
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
