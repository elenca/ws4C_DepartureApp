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

package ch.fhnw.ws4c.module05.switchcontrol.demo;

import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import ch.fhnw.ws4c.module05.switchcontrol.Switch;

/**
 * @author Dieter Holz
 */
public class DemoPane extends BorderPane {
	private Switch customControl;

	private CheckBox checkBox;

	public DemoPane() {
		initializeControls();
		layoutControls();
		addBindings();
	}

	private void initializeControls() {
		setPadding(new Insets(10));

		customControl = new Switch();

		checkBox = new CheckBox();
	}

	private void layoutControls() {
		setCenter(customControl);
		VBox box = new VBox(10, checkBox);
		box.setPadding(new Insets(10));
		setRight(box);
	}

	private void addBindings() {
		customControl.onProperty().bindBidirectional(checkBox.selectedProperty());
	}

}
