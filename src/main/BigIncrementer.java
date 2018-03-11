package main;

import java.math.BigInteger;

/**
 * Increments decimals numbers from a starting value by 1. Holds values up to
 * Integer.MAX_VALUE digits in length.
 * 
 * @author Evan Quan
 * @since March 9, 2018
 *
 */
public class BigIncrementer {
	private static final BigInteger maxLong = new BigInteger(Long.toString(Long.MAX_VALUE));
	private long num;
	private BigInteger numBig;

	/**
	 * Complete constructor
	 * 
	 * @param startingValue
	 *            String representation of an Integer that the BigIncrementor starts
	 *            from
	 * @throws NumberFormatException
	 *             startingValue is not a valid representation of an Integer
	 */
	public BigIncrementer(String startingValue) throws NumberFormatException {
		num = 0;
		try {
			numBig = new BigInteger(startingValue);
		} catch (Exception e) {
			throw new NumberFormatException();
		}
	}

	/**
	 * Default constructor. Starting value is 0.
	 */
	public BigIncrementer() {
		this("0");
	}

	/**
	 * Increase value by 1
	 */
	public void increment() {
		checkUnderflow();
		num++;
	}

	/**
	 * Update numBig and reset num if num underflows
	 */
	private void checkUnderflow() {
		if (num == Long.MAX_VALUE) {
			numBig = numBig.add(maxLong);
			num = 0;
		}
	}

	/**
	 * 
	 * @return the BigInteger representation of the BigIncrementor value
	 */
	public BigInteger getBigInteger() {
		numBig = numBig.add(new BigInteger(Long.toString(num)));
		num = 0;
		return numBig;
	}

	/**
	 * String representation of decimal value of this incrementer
	 */
	@Override
	public String toString() {
		return getBigInteger().toString();
	}

}
