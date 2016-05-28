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

package ch.fhnw.ws4c.module06.statusiconsolution.demo;

import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.BorderPane;

import ch.fhnw.ws4c.module06.statusiconsolution.StatusIcon;

/**
 * @author Dieter Holz
 */
public class DemoPane extends BorderPane {
	private StatusIcon customControl;

	private ChoiceBox<StatusIcon.Status> statusField;

	public DemoPane() {
		initializeControls();
		layoutControls();
		addBindings();
	}

	private void initializeControls() {
		setPadding(new Insets(10));

		customControl = new StatusIcon();

		statusField = new ChoiceBox<>();
		statusField.getItems().addAll(StatusIcon.Status.values());
	}

	private void layoutControls() {
		setMargin(statusField, new Insets(0, 0, 10, 10));

		setCenter(customControl);
		setRight(statusField);
	}

	private void addBindings() {
		statusField.valueProperty().bindBidirectional(customControl.statusProperty());

	}

}
