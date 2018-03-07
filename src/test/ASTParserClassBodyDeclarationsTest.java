package test;

import static org.junit.Assert.fail;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit 4 Tests for ASTParser configured to kind K_CLASS_BODY_DECLARATIONS and
 * TypeDeclaration
 *
 * @author Evan Quan
 *
 */
public class ASTParserClassBodyDeclarationsTest {

	public static ASTParser parser;

	// These Strings can be used for testing (Currently unused)
	// <type>Dec<declarationCoun>Ref<referenceCount>
	String intDec1Ref1 = "public class Foo {\n int i = 1\n}";
	String doubleDec1Ref2 = "public class Foo {\n double d1 = 1.0; void foo() {d1 = 0.0} \n}";
	String doubleDec0Ref0 = "public class Foo {}";
	String stringDec1Ref0 = "public class Foo {}";

	/**
	 * Run before each test case
	 */
	@Before
	public void setUp() {
		parser = ASTParser.newParser(AST.JLS8);
		parser.setKind(ASTParser.K_CLASS_BODY_DECLARATIONS);
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
