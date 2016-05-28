package ch.fhnw.ws4c.simplelistviewinwork;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Dieter Holz
 */
public class PresentationModel {
	private final StringProperty windowTitle = new SimpleStringProperty("A very simple list");
	// Alles was dargestellt werden soll Initialisieren
	private final ObservableList<String> allItems = FXCollections.observableArrayList();

	public ObservableList<String> getAllItems(){
		return allItems;
	}
	public void addItem(){
		// Was soll hinzugefügt werden?
		allItems.add("item-" + allItems.size());
	}

	// all getters and setters

	public String getWindowTitle() {
		return windowTitle.get();
	}

	public StringProperty windowTitleProperty() {
		return windowTitle;
	}

	public void setWindowTitle(String windowTitle) {
		this.windowTitle.set(windowTitle);
	}



}
