package ch.fhnw.ws4c.simplelistviewinwork;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ApplicationUI extends VBox {

	private final PresentationModel model;
	private Button button;
	private ListView<String> listView;

	public ApplicationUI(PresentationModel model) {
		this.model = model;
		initializeControls();
		layoutControls();
		addEventHandlers();
		addValueChangedListeners();
		addBindings();
	}

	private void initializeControls() {
		button = new Button("Add New Element");
		// ich muss der ListView den Observable und verwende mein "PresentationModel"
		listView = new ListView<>(model.getAllItems());
	}

	private void layoutControls() {
		setPadding(new Insets(10));
		setSpacing(10);

		button.setMaxWidth(Double.MAX_VALUE);
		setVgrow(listView, Priority.ALWAYS);

		getChildren().addAll(listView, button);
	}

	private void addEventHandlers() {
		// WAs soll beim Event passieren?
		// Überlasse dem PresentationModel was hinzugefügt wird!!
		button.setOnAction(event -> model.addItem());
	}

	private void addValueChangedListeners() {

	}

	private void addBindings() {
	}
}
