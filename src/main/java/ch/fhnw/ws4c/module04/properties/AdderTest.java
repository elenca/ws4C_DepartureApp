package ch.fhnw.ws4c.module04.properties;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

// import static org.junit.Assert.*;

/**
 * @author Dieter Holz
 */
public class AdderTest {

	Adder sut;

	@Before
	public void setup() {
		sut = new Adder();
	}

	@Test
	public void testInit(){
		assertEquals(0, sut.getSummand_1());
		assertEquals(0, sut.getSummand_2());
		assertEquals(0, sut.getResult());
	}

	@Test
	public void testSetValue(){
		//when
		sut.setSummand_1(10);
		sut.setSummand_2(20);

		//then
		assertEquals(30, sut.getResult());
	}

}