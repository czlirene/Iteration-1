package test;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;

import org.junit.Test;

import main.JavaFileReader;

/**
 * JUnit 4 Test for {@link JavaFileReader} class
 *
 * @author Evan Quan
 * @since 12 March 2018
 *
 */
public class JavaFileReaderTest {

	/**
	 * The expected string representation of TestClass
	 */
	private static final String TestClassString = "package test;" + TestSuite.lineSeparator + TestSuite.lineSeparator
			+ "public class TestClass {" + TestSuite.lineSeparator + TestSuite.lineSeparator + "}"
			+ TestSuite.lineSeparator;

	/**
	 * Check that a NotDirectoryException is thrown if an invalid directory is
	 * searched
	 *
	 * @throws NotDirectoryException
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
	@Test(expected = NotDirectoryException.class)
	public void testGetAllJavaFilesToStringForInvalidDirectory() throws NotDirectoryException, IOException {
		String invalidDirectory = "";
		ArrayList<String> results = JavaFileReader.getAllJavaFilesToString(invalidDirectory);
	}

	/**
	 * Check that all the contents of all Java files (and only Java files) can be
	 * retrieved as Strings from the javaFileReaderTestPackage directory
	 *
	 * @throws NotDirectoryException
	 * @throws IOException
	 */
	@Test
	public void testGetAllJavaFilesToStringForTestPackage() throws NotDirectoryException, IOException {
		ArrayList<String> results = JavaFileReader.getAllJavaFilesToString(TestSuite.JAVA_FILE_READER_TEST_DIR);
		String appleSource = "package test.javaFileReaderTestPackage;" + TestSuite.lineSeparator
				+ TestSuite.lineSeparator + "public class Apple {" + TestSuite.lineSeparator + TestSuite.lineSeparator
				+ "}" + TestSuite.lineSeparator;
		String bananaSource = "package test.javaFileReaderTestPackage;" + TestSuite.lineSeparator
				+ TestSuite.lineSeparator + "public class Banana {" + TestSuite.lineSeparator + TestSuite.lineSeparator
				+ "}" + TestSuite.lineSeparator;
		String zebraSource = "package test.javaFileReaderTestPackage;" + TestSuite.lineSeparator
				+ TestSuite.lineSeparator + "public class Zebra {" + TestSuite.lineSeparator + TestSuite.lineSeparator
				+ "}" + TestSuite.lineSeparator;
		assertEquals(appleSource, results.get(0));
		assertEquals(bananaSource, results.get(1));
		assertEquals(zebraSource, results.get(2));
	}

	/**
	 * Check that trying to read from an invalid file throws a FileNotFoundException
	 *
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
	@Test(expected = FileNotFoundException.class)
	public void testGetFileToStringForInvalidFilePath() throws IOException {
		String invalidFilePath = "";
		String result = JavaFileReader.getFileContentsToString(invalidFilePath);
	}

	/**
	 * Check that that a Java file in the current directory (same directory as the
	 * JUnit tests) can be read and its contents can be converted to a string
	 *
	 * @throws IOException
	 */
	@Test
	public void testGetFileToStringForRelativeDirectoryPath() throws IOException {
		String relativePath = TestSuite.TEST_DIR.concat("TestClass.java");
		String result = JavaFileReader.getFileContentsToString(relativePath);

		assertEquals(TestClassString, result);
	}

	/**
	 * Check if a NotDirectoryException is thrown if an invalid directory argument
	 * is used
	 *
	 * @throws NotDirectoryException
	 */
	@SuppressWarnings("unused")
	@Test(expected = NotDirectoryException.class)
	public void testGetJavaFileNamesForInvalidDirectory() throws NotDirectoryException {
		String invalidDirectory = "";
		ArrayList<String> javaFileNames = JavaFileReader.getJavaFileNames(invalidDirectory);
	}

	/**
	 * Check if all the names of Java files of testPackage directory can be acquired
	 *
	 * @throws NotDirectoryException
	 */
	@Test
	public void testGetJavaFileNamesForTestPackage() throws NotDirectoryException {
		ArrayList<String> javaFileNames = JavaFileReader.getJavaFileNames(TestSuite.JAVA_FILE_READER_TEST_DIR);

		assertEquals("Apple.java", javaFileNames.get(0));
		assertEquals("Banana.java", javaFileNames.get(1));
		assertEquals("Zebra.java", javaFileNames.get(2));

	}

}
