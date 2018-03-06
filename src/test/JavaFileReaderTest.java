package test;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import main.JavaFileReader;

/**
 * JUnit 4 Test for JavaFileReader class
 * 
 * @author Evan Quan
 * @since March 5, 2018
 *
 */
public class JavaFileReaderTest {

	/**
	 * The expected string representation of ReadMeTestClass
	 */
	private static String TestClassString = "package test;\n\npublic class TestClass {\n\n}\n";

	/**
	 * Run before each test case
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() {
	}

	/**
	 * Check that that a Java file in the current directory (same directory as the
	 * JUnit tests) can be read and its contents can be converted to a string
	 * 
	 * @throws IOException
	 */
	@Test
	public void testGetFileToStringForCurrentDirectory() throws IOException {

		String currentFilePath = new File("").getAbsolutePath().concat("/src/test/TestClass.java");
		System.out.println(currentFilePath);
		String result = JavaFileReader.getFileToString(currentFilePath);

		assertEquals(TestClassString, result);
	}

	/**
	 * Check that trying to read from a non-existing file throws a
	 * FileNotFoundException
	 * 
	 * @throws IOException
	 */
	@Test(expected = FileNotFoundException.class)
	public void testGetNonExistenceFile() throws IOException {
		String invalidFilePath = "";
		String result = "";
		result = JavaFileReader.getFileToString(invalidFilePath);

		assertEquals(TestClassString, result);
	}

	/**
	 * Check if all the names of Java files of testPackage directory can be acquired
	 * 
	 * @throws NotDirectoryException
	 */
	@Test
	public void testGetJavaFileNames() throws NotDirectoryException {
		String testDirectory = new File("").getAbsolutePath().concat("/src/test/testPackage/");
		ArrayList<String> javaFileNames = JavaFileReader.getJavaFileNames(testDirectory);

		assertEquals("Apple.java", javaFileNames.get(0));
		assertEquals("Banana.java", javaFileNames.get(1));
		assertEquals("Bonobo.java", javaFileNames.get(2));

	}

	/**
	 * Check if a NotDirectoryException is thrown if an invalid directory argument
	 * is used
	 * 
	 * @throws NotDirectoryException
	 */
	@Test(expected = NotDirectoryException.class)
	public void testGetJavaFileNamesInvalidDirectory() throws NotDirectoryException {
		String invalidDirectory = "";
		ArrayList<String> javaFileNames = JavaFileReader.getJavaFileNames(invalidDirectory);

		assertEquals("Apple.java", javaFileNames.get(0));
		assertEquals("Banana.java", javaFileNames.get(1));
		assertEquals("Bonobo.java", javaFileNames.get(2));
	}

}
