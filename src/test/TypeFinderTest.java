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
 * @since 13 March 2018
 *
 */
public class TypeFinderTest {

	/**
	 * Contents of standard output
	 */
	private static ByteArrayOutputStream outContent;
	private static ByteArrayOutputStream errContent;

	/**
	 * Restore standard output and error to original streams
	 */
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
		outContent = new ByteArrayOutputStream();
		errContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
	}

	/**
	 * Check that inputting an invalid directory prompts the user with an Invalid
	 * Foo * Directory error message to screen
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
		String expected = TypeFinder.INVALID_ARGUMENT_ERROR_MESSAGE + TestSuite.lineSeparator;
		String results = errContent.toString();
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
		String expected = TypeFinder.INVALID_ARGUMENT_ERROR_MESSAGE + TestSuite.lineSeparator;
		String results = errContent.toString();
		assertEquals(expected, results);
	}

	/**
	 * Check that inputting 1 command line argument returns a prompt to the user
	 * explaining how to use the program
	 */
	@Test
	public void testOneArgument() {
		String[] args = { "" };
		TypeFinder.main(args);
		String expected = TypeFinder.INVALID_ARGUMENT_ERROR_MESSAGE + TestSuite.lineSeparator;
		String results = errContent.toString();
		assertEquals(expected, results);
	}

	/**
	 * Check that the correct number of declarations and references can be found
	 * from the test.testPackage directory TODO not working?
	 */
	@Test
	public void testTestPackageDirectory() {
		String[] args = { TestSuite.TYPE_FINDER_TEST_DIR, "test.typeFinderTestPackage.Foo" };
		int expectedDec = 1;
		int expectedRef = 11;
		String expectedOut = "test.typeFinderTestPackage.Foo. Declarations found: " + expectedDec
				+ "; references found: " + expectedRef + ".\n";
		String expectedErr = "";
		testOutput(args, expectedOut, expectedErr, expectedDec, expectedRef);
	}

	/**
	 * Check that standard output and standard error results are as expected
	 * 
	 * @param args
	 * @param expectedOut
	 * @param expectedErr
	 * @param expectedDec
	 * @param expectedRef
	 */
	private static void testOutput(String[] args, String expectedOut, String expectedErr, int expectedDec,
			int expectedRef) {
		TypeFinder.main(args);
		String outResults = outContent.toString();
		String errResults = errContent.toString();
		assertEquals(expectedErr, errResults);
		assertEquals(expectedOut, outResults);
	}

}
