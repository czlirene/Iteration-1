package test;

import java.io.File;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

/**
 * All Test suites go here.
 */
@Suite.SuiteClasses({ BigIncrementerVaryingStartsTest.class, BigIncrementerDefaultTest.class, JavaFileReaderTest.class,
		TypeFinderTest.class, TypeVisitorFooTest.class, TypeVisitorPackageFooTest.class, TypeVisitorBuiltInTest.class,
		PassTest.class })

/**
 * Runs all test classes
 *
 * @author Evan Quan
 * @since 12 March 2018
 *
 */
public class TestSuite {
	/**
	 * Base directory is the root of the entire project (default package directory)
	 */
	public static final String BASEDIR = new File("").getAbsolutePath().concat("/");
	public static final String SOURCE_DIR = BASEDIR.concat("src/");
	public static final String TEST_DIR = SOURCE_DIR.concat("test/");
	public static final String BIN_DIR = BASEDIR.concat("bin/");
	/**
	 * test.testPackage package directory is contained in test package directory
	 */
	public static final String JAVA_FILE_READER_TEST_DIR = TEST_DIR.concat("javaFileReaderTestPackage/");
	public static final String TYPE_FINDER_TEST_DIR = TEST_DIR.concat("typeFinderTestPackage/");
	/**
	 * Line separator changes depending on operating system. JUnitTests dealing with
	 * file contents should consider this.
	 */
	public static String lineSeparator = System.getProperty("line.separator");
}
