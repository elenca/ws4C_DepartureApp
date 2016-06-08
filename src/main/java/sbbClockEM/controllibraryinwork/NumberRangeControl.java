package sbbClockEM.controllibraryinwork;

import eu.hansolo.medusa.*;
import eu.hansolo.medusa.Clock;
import eu.hansolo.medusa.Clock.ClockSkinType;
import com.sun.javafx.css.converters.ColorConverter;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.css.*;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Dieter Holz
 */
public class NumberRangeControl extends Control {
	private static final Duration ANIMATION_DURATION = Duration.millis(400);

	// all properties
	private final StringProperty title    = new SimpleStringProperty("DESTINATION");
	private final StringProperty unit     = new SimpleStringProperty("DEPARTURE TIME");
	private final DoubleProperty minValue = new SimpleDoubleProperty(0);
	private final DoubleProperty maxValue = new SimpleDoubleProperty(60);
	private final DoubleProperty value    = new SimpleDoubleProperty();

	private final BooleanProperty animated      = new SimpleBooleanProperty(true);
	private final DoubleProperty  animatedValue = new SimpleDoubleProperty();

	private final DoubleProperty  percentage = new SimpleDoubleProperty();
	private final DoubleProperty  angle      = new SimpleDoubleProperty();
	private final BooleanProperty outOfRange = new SimpleBooleanProperty();

	// all CSS Styleable properties  (generisch, worauf wende ich das an)
	private final StyleableObjectProperty<Color> baseColor = new SimpleStyleableObjectProperty<Color>(StyleableProperties.BASE_COLOR,
	                                                                                                  this,
	                                                                                                  "baseColor") {
		@Override
		protected void invalidated() {
			setStyle(getCssMetaData().getProperty() + ": " + colorToCss(get()) + ";");
			applyCss();
		}
	};

	// Live Coding:
	private final StyleableObjectProperty<Color> outOfRangeColor = new SimpleStyleableObjectProperty<Color>(StyleableProperties.OUT_OF_RANGE_COLOR,
			this, "outOfRangeColor"){

		// Style soll auf neues UI Element angewendet werden
		@Override
		protected void invalidated() {
			setStyle(getCssMetaData().getProperty() + ": " + colorToCss(get()) + ";");
			applyCss();
		}
	};

	// animations
	private final Timeline timeline = new Timeline();
	private       Color    baseColorFromCSS;
	private final SkinType skinType;


	public NumberRangeControl(SkinType skinType) {
		this.skinType = skinType;
		getStyleClass().add(getStyleClassName());
		addValueChangedListeners();
		addBindings();
	}

	@Override
	protected Skin<?> createDefaultSkin() {
		switch (skinType) {
			case LINEAR:
				return new LinearSkin(this);
			case SLIM:
				return new SlimSkin(this);
			case PIE:
				return new PieSkin(this);
			case TITLE:
				return new TitleSkin(this);
			default:
				return new SlimSkin(this);
		}
	}

	private void addValueChangedListeners() {
		valueProperty().addListener((observable, oldValue, newValue) -> {
			if (isAnimated()) {
				timeline.stop();
				timeline.getKeyFrames().setAll(new KeyFrame(ANIMATION_DURATION,
				                                            new KeyValue(animatedValueProperty(), newValue, Interpolator.EASE_BOTH)));

				timeline.play();
			} else {
				setAnimatedValue(newValue.doubleValue());
			}
		});

		outOfRangeProperty().addListener((observable, oldValue, newValue) -> {
			if (baseColorFromCSS == null) {
				baseColorFromCSS = getBaseColor();
			}
			if (newValue) {
				setBaseColor(getOutOfRangeColor());  //Todo: Das sollte auch eine StyleableProperty sein!!
			} else {
				setBaseColor(baseColorFromCSS);
			}
		});
	}

	private void addBindings() {
		percentage.bind(Bindings.createDoubleBinding(() -> {
			double min = getMinValue();
			double max = getMaxValue();

			return Math.max(0.0, Math.min(100.0, (getAnimatedValue() - min) / ((max - min) / 100.0)));
		}, minValue, maxValue, animatedValue));

		angle.bind(percentage.multiply(-3.6));

		outOfRange.bind(Bindings.createBooleanBinding(() -> getValue() > getMaxValue() || getValue() < getMinValue(), value, minValue, maxValue));
	}

	private String getStyleClassName() {
		String className = this.getClass().getSimpleName();

		return className.substring(0, 1).toLowerCase() + className.substring(1);
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

	public double getValue() {
		return value.get();
	}

	public DoubleProperty valueProperty() {
		return value;
	}

	public void setValue(double value) {
		this.value.set(value);
	}

	public boolean isAnimated() {
		return animated.get();
	}

	public BooleanProperty animatedProperty() {
		return animated;
	}

	public void setAnimated(boolean animated) {
		this.animated.set(animated);
	}

	public double getAnimatedValue() {
		return animatedValue.get();
	}

	public DoubleProperty animatedValueProperty() {
		return animatedValue;
	}

	private void setAnimatedValue(double animatedValue) {
		this.animatedValue.set(animatedValue);
	}

	public double getPercentage() {
		return percentage.get();
	}

	public DoubleProperty percentageProperty() {
		return percentage;
	}

	public double getAngle() {
		return angle.get();
	}

	public DoubleProperty angleProperty() {
		return angle;
	}

	public boolean getOutOfRange() {
		return outOfRange.get();
	}

	public BooleanProperty outOfRangeProperty() {
		return outOfRange;
	}

	public Color getBaseColor() {
		return baseColor.get();
	}

	public StyleableObjectProperty<Color> baseColorProperty() {
		return baseColor;
	}

	public void setBaseColor(Color baseColor) {
		this.baseColor.set(baseColor);
	}

	public Color getOutOfRangeColor() {
		return outOfRangeColor.get();
	}

	public StyleableObjectProperty<Color> outOfRangeColorProperty() {
		return outOfRangeColor;
	}

	public void setOutOfRangeColor(Color outOfRangeColor) {
		this.outOfRangeColor.set(outOfRangeColor);
	}

	private static String colorToCss(final Color color) {
		return color.toString().replace("0x", "#");
	}

	/**
	 * contains all styleable CssMetaData needed
	 */
	private static class StyleableProperties {

		// Jede Property brauhct eine Konstante

		private static final CssMetaData<NumberRangeControl, Color> BASE_COLOR =
				new CssMetaData<NumberRangeControl, Color>("-base-color", ColorConverter.getInstance()) {

					@Override
					public boolean isSettable(NumberRangeControl control) {
						return control.baseColor != null && !control.baseColor.isBound();
					}

					@Override
					public StyleableProperty<Color> getStyleableProperty(NumberRangeControl control) {
						return control.baseColor;
					}
				};

		// Parameter im CSS File "-out-of-range-color", wie soll konvertiert werden
		private static final CssMetaData<NumberRangeControl, Color> OUT_OF_RANGE_COLOR =
				new CssMetaData<NumberRangeControl, Color>("-out-of-range-color", ColorConverter.getInstance()) {

					@Override
					public boolean isSettable(NumberRangeControl control) {
						return control.outOfRangeColor != null && !control.outOfRangeColor.isBound();
					}

					@Override
					public StyleableProperty<Color> getStyleableProperty(NumberRangeControl control) {
						return control.outOfRangeColor;
					}
				};

		// this will hold all the available styleables that are available for this class,
		// it will also contain the base classes StyleableProperties
		private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES;

		// In this region we will initialize the list of all StyleableProperties
		static {
			// Here we need to make sure to include all the StyleableProperties from the parent class,
			// otherwise we will loose the ability to style properties of the parent class
			final List<CssMetaData<? extends Styleable, ?>> styleables = new ArrayList<>(Control.getClassCssMetaData());
			Collections.addAll(styleables,

			                   // here all the cssmetadata of all StyleableProperties of this class will need to be added
			                   // in order to make them styleable
			                   BASE_COLOR,
							   OUT_OF_RANGE_COLOR
			                  );
			STYLEABLES = Collections.unmodifiableList(styleables);
		}
	}

	/**
	 * @return The CssMetaData associated with this class, which may include the CssMetaData of its super classes.
	 */
	public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
		return StyleableProperties.STYLEABLES;
	}

	/**
	 * This method should delegate to {@link #getClassCssMetaData()} so that a Node's CssMetaData can be accessed without the need for reflection.
	 *
	 * @return The CssMetaData associated with this node, which may include the CssMetaData of its super classes.
	 */
	@Override
	public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData() {
		return getClassCssMetaData();
	}

}
