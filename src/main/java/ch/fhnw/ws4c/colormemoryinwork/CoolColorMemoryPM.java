package ch.fhnw.ws4c.colormemoryinwork;

import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;

/**
 * @author Dieter Holz
 */
public class CoolColorMemoryPM {
	private final StringProperty windowTitle = new SimpleStringProperty("Cool Colors");


	private final IntegerProperty red   = new SimpleIntegerProperty(0);
	private final IntegerProperty green = new SimpleIntegerProperty(0);
	private final IntegerProperty blue  = new SimpleIntegerProperty(0);

	private final ObjectProperty<Color> color = new SimpleObjectProperty<>();

	//Observable List erstellen
	private final ObservableList<Color> coolColors = FXCollections.observableArrayList();


	public CoolColorMemoryPM() {
		color.bind(Bindings.createObjectBinding(() -> Color.rgb(getRed(), getGreen(), getBlue()), red, green, blue));
	}


	// all getters and setters
	public void addColor(){
		coolColors.add(getColor());
	}

	public ObservableList<Color> getCoolColors() {
		return coolColors;
	}

	public int getRed() {
		return red.get();
	}

	public IntegerProperty redProperty() {
		return red;
	}

	public void setRed(int red) {
		this.red.set(red);
	}

	public int getGreen() {
		return green.get();
	}

	public IntegerProperty greenProperty() {
		return green;
	}

	public void setGreen(int green) {
		this.green.set(green);
	}

	public int getBlue() {
		return blue.get();
	}

	public IntegerProperty blueProperty() {
		return blue;
	}

	public void setBlue(int blue) {
		this.blue.set(blue);
	}

	public Color getColor() {
		return color.get();
	}

	public ObjectProperty<Color> colorProperty() {
		return color;
	}

	public void setColor(Color color) {
		this.color.set(color);
	}

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
