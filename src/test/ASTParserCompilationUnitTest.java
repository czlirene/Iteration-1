package test;

import static org.junit.Assert.fail;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit 4 Tests for ASTParser configured to kind K_COMPILATION_UNIT and
 * CompilationUnit
 *
 * @author Evan Quan
 *
 */
public class ASTParserCompilationUnitTest {

	public static ASTParser parser;

	/**
	 * Run before each test case
	 */
	@Before
	public void setUp() {
		parser = ASTParser.newParser(AST.JLS8);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
