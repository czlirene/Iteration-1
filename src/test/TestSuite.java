package test;

import java.io.File;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

/**
 * NOTE: Put all test classes here in the form: ClassName.class
 */
@Suite.SuiteClasses({ TypeFinderTest.class, JavaFileReaderTest.class, PassTest.class })

/**
 * Runs all test classes
 *
 * @author Evan Quan
 *
 */
public class TestSuite {
	// Base directory is the test package directory
	public static String BASEDIR = new File("").getAbsolutePath().concat("/src/test/");
	public static String TEST_PACKAGE_DIR = BASEDIR.concat("testPackage/");
}
