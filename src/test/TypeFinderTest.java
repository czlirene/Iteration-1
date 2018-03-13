package test;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.TypeFinder;

/**
 * JUnit 4 Tests for {@link TypeFinder} class
 *
 * @author Evan Quan
 * @since 12 March 2018
 *
 */
public class TypeFinderTest {

	/**
	 * Contents of standard output
	 */
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

	@After
	public void restoreStream() {
		System.setOut(System.out);
		System.setErr(System.err);
	}

	/**
	 * outContent tracks standard output
	 */
	@Before
	public void setUpStream() {
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
	}

	/**
	 * Check that inputting an invalid directory prompts the user with an Invalid
	 * Directory error message to screen
	 */
	@Test
	public void testInvalidDirectory() {
		String invalidDirectory = "";
		String type = "String";
		String[] args = { invalidDirectory, type };
		TypeFinder.main(args);
		String expected = TypeFinder.INVALID_DIRECTORY_ERROR_MESSAGE + TestSuite.lineSeparator;
		String results = errContent.toString();
		assertEquals(expected, results);
	}

	/**
	 * Check that inputting no command line arguments returns a prompt to the user
	 * explaining how to use the program
	 */
	@Test
	public void testNoArguments() {
		String[] args = {};
		TypeFinder.main(args);
		String expected = TypeFinder.PROPER_USE_MESSAGE + TestSuite.lineSeparator;
		String results = outContent.toString();
		assertEquals(expected, results);
	}

	/**
	 * Check that inputting 3 arguments (more arguments than what is needed) returns
	 * a prompt to the user explaining how to use the program
	 */
	@Test
	public void testThreeArguments() {
		String[] args = { "", "", "" };
		TypeFinder.main(args);
		String expected = TypeFinder.PROPER_USE_MESSAGE + TestSuite.lineSeparator;
		String results = outContent.toString();
		assertEquals(expected, results);
	}

	/**
	 * Check that the correct number of declarations and references can be found
	 * from the test.testPackage directory
	 */
	// @Test
	// public void testTestPackageDirectory() {
	// // TODO
	// // String results = outContents.toString():
	// }

}
