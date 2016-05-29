package ch.fhnw.ws4c.module04.properties;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * @author Dieter Holz
 */
public class PropertyExample {

	private final IntegerProperty age = new SimpleIntegerProperty(0);

	public int getAge() {
		return age.get();
	}

	public void setAge(int age) {
		this.age.set(age);
	}

	public IntegerProperty ageProperty() {
		return age;
	}

}
