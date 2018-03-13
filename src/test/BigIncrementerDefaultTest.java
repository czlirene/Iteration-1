package test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import main.BigIncrementer;

/**
 * JUnit 4 Tests for BigIncrementer class with default starting value
 * 
 * @author Evan Quan
 * @since March 9, 2018
 *
 */
public class BigIncrementerDefaultTest {

	private static BigIncrementer in;

	@Before
	public void setUp() {
		in = new BigIncrementer();
	}

	/**
	 * Check that incrementer starts at proper value
	 */
	@Test
	public void testNoincrement() {
		assertEquals("0", in.toString());
	}

	/**
	 * Check that incrementer works for 10 increments
	 */
	@Test
	public void testIncrement_ONE() {
		String expected = "1";
		in.increment();
		assertEquals(expected, in.toString());
	}
}
