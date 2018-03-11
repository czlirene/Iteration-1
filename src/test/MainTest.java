package test;

/**
 * Just some test stuff to be done with a main method
 * 
 * @author Evan Quan
 *
 */
public class MainTest {
	public static void main(String[] args) {
		// Get name with package
		System.out.println(new MainTest().getClass().getName());
		// Get just class name
		System.out.println(new MainTest().getClass().getSimpleName());
	}

}
