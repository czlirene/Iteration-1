package test;

import java.util.HashMap;

/**
 * Just some test stuff to be done with a main method
 *
 * @author Evan Quan
 *
 */
public class MainTest {
	public static void main(String[] args) throws IllegalArgumentException {

		String ls = TestSuite.lineSeparator;
		// Get name with package
		System.out.println(new MainTest().getClass().getName());
		// Get just class name
		System.out.println(new MainTest().getClass().getSimpleName());

		HashMap<String, Integer> map1 = new HashMap<String, Integer>();
		HashMap<Double, String> map2 = new HashMap<Double, String>();
		boolean result = map1.getClass().equals(map2.getClass());
		System.out.println(result);

		System.out.println("/**" + ls + " * Here's a Javadoc comment" + ls + " */" + ls);
	}

}
