package test;

import java.io.File;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

/**
 * All Test suites go here.
 */
@Suite.SuiteClasses({ BigIncrementerVaryingStartsTest.class, BigIncrementerDefaultTest.class, TypeFinderTest.class,
		JavaFileReaderTest.class, PassTest.class })

/**
 * Runs all test classes
 *
 * @author Evan Quan
 * @since March 11, 2018
 *
 */
public class TestSuite {
	/**
	 * Base directory is the test package directory
	 */
	public static String BASEDIR = new File("").getAbsolutePath().concat("/src/test/");
	/**
	 * test.testPackage package directory is contained in test package directory
	 */
	public static String JAVA_FILE_READER_TEST_DIR = BASEDIR.concat("javaFileReaderTestPackage/");
	/**
	 * Line separator changes depending on operating system. JUnitTests dealing with
	 * file contents should consider this.
	 */
	public static String lineSeparator = System.getProperty("line.separator");
}
