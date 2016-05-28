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

package ch.fhnw.ws4c.module05.led.demo;

import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import ch.fhnw.ws4c.module05.led.Led;

/**
 * @author Dieter Holz
 */
public class DemoPane extends BorderPane {
	private Led led;

	private CheckBox onBox;
	private CheckBox blinkingBox;
	private Slider   blinkRateSlider;

	public DemoPane() {
		initializeControls();
		layoutControls();
		addBindings();
	}

	private void initializeControls() {
		setPadding(new Insets(10));

		led = new Led();

		onBox = new CheckBox("on");
		blinkingBox = new CheckBox("blink");
		blinkRateSlider = new Slider(0.5, 2.0, 1);
		blinkRateSlider.setMinorTickCount(0);
		blinkRateSlider.setMajorTickUnit(0.5);
		blinkRateSlider.setShowTickMarks(true);
		blinkRateSlider.setShowTickLabels(true);
	}

	private void layoutControls() {
		setMargin(onBox, new Insets(0, 50, 10, 50));

		VBox pane = new VBox(onBox, blinkingBox, blinkRateSlider);
		pane.setPadding(new Insets(0, 50, 0, 50));
		pane.setSpacing(10);

		setCenter(led);
		setRight(pane);
	}

	private void addBindings() {
		onBox.selectedProperty().bindBidirectional(led.onProperty());
		blinkingBox.selectedProperty().bindBidirectional(led.blinkingProperty());
		led.blinkRateProperty().bind(blinkRateSlider.valueProperty().multiply(1_000_000_000L));
	}

}
