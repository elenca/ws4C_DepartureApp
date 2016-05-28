package ch.fhnw.ws4c.module05.led;

import javafx.animation.AnimationTimer;
import javafx.beans.property.*;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * @author Dieter Holz
 */
public class Led extends Region {
	private static final String FONTS_CSS = "fonts.css";
	private static final String STYLE_CSS = "style.css";

	private static final double ARTBOARD_SIZE = 400;

	private static final double PREFERRED_WIDTH  = 24;
	private static final double PREFERRED_HEIGHT = PREFERRED_WIDTH;

	private static final double SIZE_FACTOR = PREFERRED_WIDTH / ARTBOARD_SIZE;

	private static final double ASPECT_RATIO = PREFERRED_WIDTH / PREFERRED_HEIGHT;

	private static final double MINIMUM_WIDTH  = 14;
	private static final double MINIMUM_HEIGHT = MINIMUM_WIDTH / ASPECT_RATIO;

	private static final double MAXIMUM_WIDTH = 800;

	//Todo: Redundant to css. Should be a StyleableProperty
	private static final Color LED_COLOR = Color.rgb(255, 0, 0);

	private static final InnerShadow INNER_SHADOW = new InnerShadow(BlurType.TWO_PASS_BOX, Color.rgb(0, 0, 0, 0.65), 8d * SIZE_FACTOR, 0d, 0d, 0d);
	private static final DropShadow  GLOW         = new DropShadow(BlurType.THREE_PASS_BOX, LED_COLOR, 90d * SIZE_FACTOR, 0d, 0d, 0d);

	{
		GLOW.setInput(INNER_SHADOW);
	}

	// all parts
	private Circle highlight;
	private Circle mainOn;
	private Circle mainOff;
	private Circle frame;

	private Pane drawingPane;

	// all properties
	private final BooleanProperty on        = new SimpleBooleanProperty();
	private final BooleanProperty blinking  = new SimpleBooleanProperty();
	private final LongProperty    blinkRate = new SimpleLongProperty(1_000_000_000L);


	private AnimationTimer timer = new AnimationTimer() {
		private long lastTimerCall;

		@Override
		public void handle(long now) {
			if (now > lastTimerCall + getBlinkRate()) {
				setOn(!getOn());
				lastTimerCall = now;
			}
		}
	};

	public Led() {
		init();
		initializeParts();
		layoutParts();
		addEventHandlers();
		addValueChangedListeners();
		addBindings();

		setOn(true);
		setBlinking(false);
	}

	private void init() {
		addStyleSheets(this);
		getStyleClass().add(getStyleClassName());
	}

	private void initializeParts() {
		double center = PREFERRED_WIDTH * 0.5;

		highlight = new Circle(center, center, 116 * SIZE_FACTOR);
		highlight.getStyleClass().addAll("highlight");

		mainOn = new Circle(center, center, 144 * SIZE_FACTOR);
		mainOn.getStyleClass().addAll("mainOn");
		mainOn.setEffect(GLOW);

		mainOff = new Circle(center, center, 144 * SIZE_FACTOR);
		mainOff.getStyleClass().addAll("mainOff");
		mainOff.setEffect(INNER_SHADOW);

		frame = new Circle(center, center, 200 * SIZE_FACTOR);
		frame.getStyleClass().addAll("frame");

		// always needed
		drawingPane = new Pane();
		drawingPane.setMaxSize(PREFERRED_WIDTH, PREFERRED_HEIGHT);
		drawingPane.setMinSize(PREFERRED_WIDTH, PREFERRED_HEIGHT);
		drawingPane.setPrefSize(PREFERRED_WIDTH, PREFERRED_HEIGHT);
	}

	private void layoutParts() {
		drawingPane.getChildren().addAll(frame, mainOff, mainOn, highlight);
		getChildren().add(drawingPane);
	}

	private void addEventHandlers() {
	}

	private void addValueChangedListeners() {
		onProperty().addListener((observable, oldValue, newValue) -> {
			mainOff.setVisible(!newValue);
			mainOn.setVisible(newValue);
		});

		blinkingProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue) {
				timer.start();
			} else {
				timer.stop();
			}
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
		Insets padding           = getPadding();
		double horizontalPadding = padding.getLeft() + padding.getRight();

		return MINIMUM_WIDTH + horizontalPadding;
	}

	@Override
	protected double computeMinHeight(double width) {
		Insets padding         = getPadding();
		double verticalPadding = padding.getTop() + padding.getBottom();

		return MINIMUM_HEIGHT + verticalPadding;
	}

	@Override
	protected double computePrefWidth(double height) {
		Insets padding           = getPadding();
		double horizontalPadding = padding.getLeft() + padding.getRight();

		return PREFERRED_WIDTH + horizontalPadding;
	}

	@Override
	protected double computePrefHeight(double width) {
		Insets padding         = getPadding();
		double verticalPadding = padding.getTop() + padding.getBottom();

		return PREFERRED_HEIGHT + verticalPadding;
	}

	// getter and setter for all properties

	public boolean getOn() {
		return on.get();
	}

	public BooleanProperty onProperty() {
		return on;
	}

	public void setOn(boolean on) {
		this.on.set(on);
	}

	public boolean getBlinking() {
		return blinking.get();
	}

	public BooleanProperty blinkingProperty() {
		return blinking;
	}

	public void setBlinking(boolean blinking) {
		this.blinking.set(blinking);
	}

	public long getBlinkRate() {
		return blinkRate.get();
	}

	public LongProperty blinkRateProperty() {
		return blinkRate;
	}

	public void setBlinkRate(long blinkRate) {
		this.blinkRate.set(blinkRate);
	}

}
