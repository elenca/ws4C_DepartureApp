package ch.fhnw.ws4c.module06.slimvaluedisplaysolution;

import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
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
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

/**
 * @author Dieter Holz
 */
public class SlimValueDisplay extends Region {
	private static final String FONTS_CSS = "fonts.css";
	private static final String STYLE_CSS = "style.css";

	private static final double PREFERRED_WIDTH  = 250;
	private static final double PREFERRED_HEIGHT = 265;

	private static final double ASPECT_RATIO = PREFERRED_WIDTH / PREFERRED_HEIGHT;

	private static final double MINIMUM_WIDTH  = 80;
	private static final double MINIMUM_HEIGHT = MINIMUM_WIDTH / ASPECT_RATIO;

	private static final double MAXIMUM_WIDTH = 800;

	private static final Duration ANIMATION_DURATION = Duration.millis(400);
	private static final String   FORMAT             = "%.1f";
	private static final long     BLINK_RATE         = 500_000_000L;

	// all parts
	private Line   separator;
	private Text   titleLabel;
	private Text   valueLabel;
	private Text   unitLabel;
	private Circle barBackground;
	private Arc    bar;

	private Pane drawingPane;

	// all properties
	private final StringProperty title    = new SimpleStringProperty("TITLE");
	private final StringProperty unit     = new SimpleStringProperty("UNIT");
	private final DoubleProperty minValue = new SimpleDoubleProperty(0);
	private final DoubleProperty maxValue = new SimpleDoubleProperty(1000);
	private final DoubleProperty value    = new SimpleDoubleProperty();

	private final DoubleProperty animatedValue = new SimpleDoubleProperty();

	// animations
	private final Timeline timeline = new Timeline();

	private AnimationTimer timer = new AnimationTimer() {
		private long lastTimerCall;

		@Override
		public void handle(long now) {
			if (now > lastTimerCall + BLINK_RATE) {
				bar.setVisible(!bar.isVisible());
				lastTimerCall = now;
			}
		}
	};

	public SlimValueDisplay() {
		init();
		initializeParts();
		layoutParts();
		addEventHandlers();
		addValueChangedListeners();
		addBindings();
		setValue(200);
	}

	private void init() {
		addStyleSheets(this);
		getStyleClass().add(getStyleClassName());
	}

	private void initializeParts() {
		separator = new Line(25, 15, 225, 15);
		separator.getStyleClass().add("separator");
		separator.setStrokeLineCap(StrokeLineCap.ROUND);

		titleLabel = new Text(getTitle());
		titleLabel.getStyleClass().add("title");
		titleLabel.setTextOrigin(VPos.TOP);
		titleLabel.setTextAlignment(TextAlignment.CENTER);
		titleLabel.setY(19);

		valueLabel = new Text(String.format(FORMAT, getValue()));
		valueLabel.getStyleClass().add("value");
		valueLabel.setTextOrigin(VPos.CENTER);
		valueLabel.setTextAlignment(TextAlignment.CENTER);
		valueLabel.setY(150);

		unitLabel = new Text(getUnit());
		unitLabel.getStyleClass().add("unit");
		unitLabel.setTextOrigin(VPos.TOP);
		unitLabel.setTextAlignment(TextAlignment.CENTER);
		unitLabel.setY(188);

		relocateTexts();

		barBackground = new Circle(125, 150, 100);
		barBackground.getStyleClass().add("barBackground");

		bar = new Arc(125, 150, 100, 100, 90, 0);
		bar.getStyleClass().add("bar");
		bar.setType(ArcType.OPEN);

		// always needed
		drawingPane = new Pane();
		drawingPane.setMaxSize(PREFERRED_WIDTH, PREFERRED_HEIGHT);
		drawingPane.setMinSize(PREFERRED_WIDTH, PREFERRED_HEIGHT);
		drawingPane.setPrefSize(PREFERRED_WIDTH, PREFERRED_HEIGHT);
	}

	private void layoutParts() {
		drawingPane.getChildren().addAll(barBackground, bar, separator, titleLabel, valueLabel, unitLabel);
		getChildren().add(drawingPane);
	}

	private void addEventHandlers() {
	}

	private void addValueChangedListeners() {
		titleProperty().addListener((observable, oldValue, newValue) -> {
			titleLabel.setText(newValue);
			relocateTexts();
		});

		unitProperty().addListener((observable, oldValue, newValue) -> {
			unitLabel.setText(newValue);
			relocateTexts();
		});

		animatedValueProperty().addListener((observable, oldValue, newValue) -> {
			valueLabel.setText(String.format(FORMAT, newValue.doubleValue()));
			relocateTexts();
		});

		valueProperty().addListener((observable, oldValue, newValue) -> {
			checkBoundaries(newValue);

			timeline.stop();
			timeline.getKeyFrames().setAll(new KeyFrame(ANIMATION_DURATION,
			                                            new KeyValue(animatedValueProperty(), newValue, Interpolator.EASE_BOTH)));

			timeline.play();
		});

		animatedValueProperty().addListener((observable, oldValue, newValue) -> bar.setLength(getAngle(newValue)));

		minValueProperty().addListener((observable, oldValue, newValue) -> {
			checkBoundaries(getValue());
			bar.setLength(getAngle(getValue()));
		});

		maxValueProperty().addListener((observable, oldValue, newValue) -> {
			checkBoundaries(getValue());
			bar.setLength(getAngle(getValue()));
		});

		// always needed
		widthProperty().addListener((observable, oldValue, newValue) -> resize());
		heightProperty().addListener((observable, oldValue, newValue) -> resize());
	}

	private void checkBoundaries(Number newValue) {
		if (newValue.doubleValue() > getMaxValue() || newValue.doubleValue() < getMinValue()) {
			timer.start();
		} else {
			timer.stop();
			bar.setVisible(true);
		}
	}

	private void addBindings() {
		//	bar.lengthProperty().bind(Bindings.createDoubleBinding(() -> getDegree(getAnimatedValue()), animatedValueProperty()));
	}

	private double getAngle(Number value) {
		return -(3.6 * getPercentage(value));
	}

	private double getPercentage(Number newValue) {
		double min = getMinValue();
		double max = getMaxValue();
		return Math.max(1.0, Math.min(100.0, (newValue.doubleValue() - min) / ((max - min) / 100.0)));
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

	private void relocateTexts() {
		titleLabel.setX((PREFERRED_WIDTH - titleLabel.getLayoutBounds().getWidth()) * 0.5);
		valueLabel.setX((PREFERRED_WIDTH - valueLabel.getLayoutBounds().getWidth()) * 0.5);
		unitLabel.setX((PREFERRED_WIDTH - unitLabel.getLayoutBounds().getWidth()) * 0.5);
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

	public String getTitle() {
		return title.get();
	}

	public StringProperty titleProperty() {
		return title;
	}

	public void setTitle(String title) {
		this.title.set(title);
	}

	public double getValue() {
		return value.get();
	}

	public DoubleProperty valueProperty() {
		return value;
	}

	public void setValue(double value) {
		this.value.set(value);
	}

	public String getUnit() {
		return unit.get();
	}

	public StringProperty unitProperty() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit.set(unit);
	}

	public double getMinValue() {
		return minValue.get();
	}

	public DoubleProperty minValueProperty() {
		return minValue;
	}

	public void setMinValue(double minValue) {
		this.minValue.set(minValue);
	}

	public double getMaxValue() {
		return maxValue.get();
	}

	public DoubleProperty maxValueProperty() {
		return maxValue;
	}

	public void setMaxValue(double maxValue) {
		this.maxValue.set(maxValue);
	}

	public double getAnimatedValue() {
		return animatedValue.get();
	}

	public DoubleProperty animatedValueProperty() {
		return animatedValue;
	}

	public void setAnimatedValue(double animatedValue) {
		this.animatedValue.set(animatedValue);
	}
}

