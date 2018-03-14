package test;

/**
 * Just some test stuff to be done with a main method
 *
 * @author Evan Quan
 *
 */
public class MainTest {
	public static void main(String[] args) throws IllegalArgumentException {

		Object[] a = new Object[12];
		Object[] b = new Object[0];

		System.out.println("a");
		for (Object i : a) {
			System.out.println(i);
		}

		System.out.println("b");
		for (Object i : b) {
			System.out.println(i);
		}

		// for (String s;;) {
		// System.out.println("hi");
		// }

	}
}
