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

package ch.fhnw.ws4c.module09.controllibrary.demo;

import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.converter.NumberStringConverter;

import ch.fhnw.ws4c.module09.controllibrary.NumberRangeControl;
import ch.fhnw.ws4c.module09.controllibrary.SkinType;

/**
 * @author Dieter Holz
 */
public class DemoPane extends BorderPane {
	private NumberRangeControl customControl;

	private TextField titleField;
	private TextField valueField;
	private Label     valueLabel;
	private TextField unitField;
	private TextField minValueField;
	private TextField maxValueField;
	private CheckBox  isAnimated;

	private ColorPicker colorPicker;

	public DemoPane() {
		initializeControls();
		layoutControls();
		addBindings();
	}

	private void initializeControls() {
		setPadding(new Insets(10));

		customControl = new NumberRangeControl(SkinType.LINEAR);

		titleField = new TextField();
		valueField = new TextField();
		valueLabel = new Label();
		unitField = new TextField();
		minValueField = new TextField();
		maxValueField = new TextField();
		isAnimated = new CheckBox();

		colorPicker = new ColorPicker();

	}

	private void layoutControls() {
		VBox box = new VBox(titleField, valueField, valueLabel, unitField, new Label("min, max Value:"), minValueField, maxValueField, new Label("animated:"), isAnimated, colorPicker);
		box.setSpacing(10);
		box.setPadding(new Insets(20));

		setCenter(customControl);
		setRight(new ScrollPane(box));
	}

	private void addBindings() {
		titleField.textProperty().bindBidirectional(customControl.titleProperty());
		unitField.textProperty().bindBidirectional(customControl.unitProperty());
		Bindings.bindBidirectional(valueField.textProperty(), customControl.valueProperty(), new NumberStringConverter());
		valueLabel.textProperty().bind(customControl.valueProperty().asString("%.2f"));

		Bindings.bindBidirectional(minValueField.textProperty(), customControl.minValueProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(maxValueField.textProperty(), customControl.maxValueProperty(), new NumberStringConverter());

		isAnimated.selectedProperty().bindBidirectional(customControl.animatedProperty());

		customControl.baseColorProperty().bind(colorPicker.valueProperty());
	}

}
