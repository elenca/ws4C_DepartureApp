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

package ch.fhnw.ws4c.module01.demotemplate;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * @author Dieter Holz
 */
public class DemoPane extends VBox {
	private Button button;
	private Button saveButton;
	private static final String SAVE = "\uf0c7";

	public DemoPane() {
		initializeControls();
		layoutControls();
	}

	private void initializeControls() {
		button = new Button("Hello World");

		saveButton = new Button(SAVE);
		saveButton.getStyleClass().add("icon");
	}

	private void layoutControls() {

		setPadding(new Insets(5));
		setSpacing(5);

		getChildren().addAll(button, saveButton);
	}
}
