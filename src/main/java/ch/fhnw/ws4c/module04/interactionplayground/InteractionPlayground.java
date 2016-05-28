/*
 *
 * Copyright (c) 2015 by FHNW
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package ch.fhnw.ws4c.module04.interactionplayground;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * @author Dieter Holz
 */
public class InteractionPlayground extends BorderPane {
	private final DoubleProperty doubleValue    = new SimpleDoubleProperty();
	private final StringProperty stringProperty = new SimpleStringProperty();

	private Circle    circle;
	private Text      text;
	private Rectangle rectangle;

	private Label     labelA;
	private Label     labelB;
	private TextField textFieldA;
	private TextField textFieldB;
	private Slider    sliderA;
	private Slider    sliderB;
	private Button    buttonA;
	private Button    buttonB;

	public InteractionPlayground() {
		initializeControls();
		layoutControls();
		addEventHandlers();
		addValueChangedListeners();
		addBindings();
	}

	private void initializeControls() {
		circle = new Circle(300, 300, 50, Color.DEEPSKYBLUE);
		circle.setStroke(Color.BLUE);

		text = new Text(50, 400, "Hi!");
		text.setFont(new Font(48));

		rectangle = new Rectangle(100, 200, Color.CORNFLOWERBLUE);
		rectangle.relocate(50, 50);

		labelA = new Label("hello");
		labelB = new Label("Brugg");

		textFieldA = new TextField();
		textFieldB = new TextField();

		sliderA = new Slider();
		sliderB = new Slider();

		buttonA = new Button("Click Me");
		buttonB = new Button("Click Me");
	}

	private void layoutControls() {
		setPadding(new Insets(10));

		Pane drawingPane = new Pane();
		drawingPane.setPrefHeight(500);
		drawingPane.setPrefWidth(500);
		drawingPane.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
		drawingPane.setBorder(new Border(new BorderStroke(Color.web("#0E53A7"), BorderStrokeStyle.SOLID, null, null)));

		drawingPane.getChildren().addAll(circle, text, rectangle);

		VBox box = new VBox(labelA, labelB, textFieldA, textFieldB, sliderA, sliderB, buttonA, buttonB);
		box.setSpacing(10);
		box.setPadding(new Insets(10));

		setCenter(drawingPane);
		setRight(box);
	}

	private void addEventHandlers() {
	}

	private void addValueChangedListeners() {
	}

	private void addBindings() {
	}

	public double getDoubleValue() {
		return doubleValue.get();
	}

	public DoubleProperty doubleValueProperty() {
		return doubleValue;
	}

	public void setDoubleValue(double doubleValue) {
		this.doubleValue.set(doubleValue);
	}

	public String getStringProperty() {
		return stringProperty.get();
	}

	public StringProperty stringPropertyProperty() {
		return stringProperty;
	}

	public void setStringProperty(String stringProperty) {
		this.stringProperty.set(stringProperty);
	}
}
