package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

/**
 * NOTE: Put all test classes here in the form: ClassName.class
 */
@Suite.SuiteClasses({ ASTParserCompilationUnitTest.class, ASTParserClassBodyDeclarationsTest.class,
		JavaFileReaderTest.class, PassTest.class })

/**
 * Runs all test classes
 *
 * @author Evan Quan
 *
 */
public class TestSuite {

}
