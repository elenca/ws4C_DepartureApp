package ch.fhnw.ws4c.module04.properties;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * @author Dieter Holz
 */
public class Adder {
	private final IntegerProperty summand_1 = new SimpleIntegerProperty(0);
	private final IntegerProperty summand_2 = new SimpleIntegerProperty(0);

	private final IntegerProperty result = new SimpleIntegerProperty();

	public Adder() {
		result.bind(summand_1.add(summand_2));
		//result.bind(Bindings.add(summand_1, summand_2));
	}

	public int getSummand_1() {
		return summand_1.get();
	}

	public IntegerProperty summand_1Property() {
		return summand_1;
	}

	public void setSummand_1(int summand_1) {
		this.summand_1.set(summand_1);
	}

	public int getSummand_2() {
		return summand_2.get();
	}

	public IntegerProperty summand_2Property() {
		return summand_2;
	}

	public void setSummand_2(int summand_2) {
		this.summand_2.set(summand_2);
	}

	public int getResult() {
		return result.get();
	}

	public IntegerProperty resultProperty() {
		return result;
	}
}
