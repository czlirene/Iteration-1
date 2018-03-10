package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import main.BigIncrementer;

/**
 * JUnit 4 Test for BigIncrementer class with varying starting values
 * 
 * @author Evan Quan
 * @since March 9, 2018
 *
 */
public class BigIncrementerVaryingStartsTest {

	private static BigIncrementer in;
	private static String BILLION = "1000000000";
	private static String TRILLION = "1000000000000";
	private static String DECILLION = "1000000000000000000000000000000000";

	/**
	 * Check that an invalid starting value throws a NumberFormatException
	 */
	@Test(expected = NumberFormatException.class)
	public void testInvalidStartingValue() {
		in = new BigIncrementer("");
	}

	/**
	 * Check that incrementer starts at proper value
	 */
	@Test
	public void test_StartAt_BILLION_Increment_ZERO() {
		in = new BigIncrementer(BILLION);
		assertEquals(BILLION, in.toString());
	}

	/**
	 * Check that incrementer works for 10 increments starting at 1000000000
	 */
	@Test
	public void test_StartAt_TRILLION_Increment_ONE() {
		in = new BigIncrementer(TRILLION);
		String expected = "1000000000001";
		in.increment();
		assertEquals(expected, in.toString());
	}

	/**
	 * Check that incrementer works for 10 increments
	 */
	@Test
	public void test_StartAt_DECILLION_Increment_TEN() {
		in = new BigIncrementer(DECILLION);
		int count = 10;
		String expected = "1000000000000000000000000000000010";
		for (int i = 0; i < count; i++) {
			in.increment();
		}
		assertEquals(expected, in.toString());
	}

	/**
	 * Check that incrementer works for 1000000000 increments
	 */
	@Test
	public void test_StartAt_DECILLION_Increment_BILLION() {
		in = new BigIncrementer(DECILLION);
		int count = 1000000000;
		String expected = "1000000000000000000000001000000000";
		for (int i = 0; i < count; i++) {
			in.increment();
		}
		assertEquals(expected, in.toString());
	}

}
