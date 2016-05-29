package ch.fhnw.ws4c.colormemoryinwork;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.shape.Rectangle;

public class ColorMixerPane extends GridPane {
	private final CoolColorMemoryPM model;

	private Slider red;
	private Slider green;
	private Slider blue;

	private Label redValue;
	private Label blueValue;
	private Label greenValue;

	private Rectangle display;

	public ColorMixerPane(CoolColorMemoryPM model) {
		this.model = model;
		initializeControls();
		layoutControls();
		addEventHandlers();
		addValueChangedListeners();
		addBindings();
	}

	private void initializeControls() {
		red   = new Slider(0.0, 255, 0);
		green = new Slider(0.0, 255, 0);
		blue  = new Slider(0.0, 255, 0);

		redValue = new Label();
		greenValue = new Label();
		blueValue = new Label();

		display = new Rectangle();
		display.setHeight(100);
	}

	private void layoutControls() {
		setPadding(new Insets(10));
		setHgap(10);
		setVgap(10);

		ColumnConstraints sliderColumn = new ColumnConstraints();
		sliderColumn.setHgrow(Priority.ALWAYS);

		ColumnConstraints valueColumn = new ColumnConstraints(30);
		valueColumn.setHalignment(HPos.RIGHT);

		getColumnConstraints().addAll(sliderColumn, valueColumn);

		addRow(0, red  , redValue);
		addRow(1, green, greenValue);
		addRow(2, blue , blueValue);
		add(display, 0, 3, 2, 1);

		setPrefHeight(getPrefHeight() + 200);
	}

	private void addEventHandlers() {
	}


	private void addValueChangedListeners() {
	}

	private void addBindings() {
		red.valueProperty().bindBidirectional(model.redProperty());
		redValue.textProperty().bind(model.redProperty().asString());

		green.valueProperty().bindBidirectional(model.greenProperty());
		greenValue.textProperty().bind(model.greenProperty().asString());

		blue.valueProperty().bindBidirectional(model.blueProperty());
		blueValue.textProperty().bind(model.blueProperty().asString());

		display.fillProperty().bind(model.colorProperty());

		display.widthProperty().bind(widthProperty().subtract(20));
	}
}
