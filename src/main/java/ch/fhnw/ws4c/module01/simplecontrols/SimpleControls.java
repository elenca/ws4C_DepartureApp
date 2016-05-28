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

package ch.fhnw.ws4c.module01.simplecontrols;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class SimpleControls extends VBox {
	private Label     label;
	private Button    button;
	private TextField textField;
	private TextArea  textArea;

	public SimpleControls() {
		initializeControls();
		layoutControls();
	}

	private void initializeControls() {
		label     = new Label("ein Label");
		textField = new TextField("text field");
		textArea  = new TextArea("text area");
		button    = new Button("ein Button");
	}

	private void layoutControls() {
		setPadding(new Insets(5));
		setSpacing(5);

		setVgrow(textArea, Priority.ALWAYS);

		getChildren().addAll(label, textField, textArea, button);
	}

}
