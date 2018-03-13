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
	 * Get string of expected TyepFinder output
	 * 
	 * @param java_type
	 * @param decl_count
	 * @param ref_count
	 * @return
	 */
	private static final String typeFinderResults(int java_type, int decl_count, int ref_count) {
		return java_type + ". Declarations found: " + decl_count + "; references found: " + ref_count + ".\n";
	}

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
		String expected = TypeFinder.INV_ARG_MSG + TestSuite.lineSeparator + TypeFinder.USAGE_MSG
				+ TestSuite.lineSeparator;
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
		String expected = TypeFinder.INV_ARG_MSG + TestSuite.lineSeparator + TypeFinder.USAGE_MSG
				+ TestSuite.lineSeparator;
		String results = errContent.toString();
		assertEquals(expected, results);
	}

	/**
	 * Check that the correct number of declarations and references can be found
	 * from the test.testPackage directory
	 */
	@Test
	public void testTestPackageDirectory() {
		String[] args = { TestSuite.TYPE_FINDER_TEST_DIR, "test.typeFinderTestPackage.Foo" };
		TypeFinder.main(args);
		int expectedDec = 1;
		int expectedRef = 11;
		String expectedOut = "Declarations found: " + expectedDec + "; references found: " + expectedRef + ".\n";
		String expectedErr = "";
		String outResults = outContent.toString();
		String errResults = errContent.toString();
		assertEquals(expectedErr, errResults);
		assertEquals(expectedOut, outResults);
	}

}
