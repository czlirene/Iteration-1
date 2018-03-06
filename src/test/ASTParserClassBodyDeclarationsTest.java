package test;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

/**
 * JUnit 4 Tests for ASTParser configured to kind K_CLASS_BODY_DECLARATIONS
 *
 * @author Evan Quan
 *
 */
public class ASTParserClassBodyDeclarationsTest {

	public static ASTParser parser;

	/**
	 * Run before each test case
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() {
		parser = ASTParser.newParser(AST.K_CLASS_BODY_DECLARATIONS);
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
