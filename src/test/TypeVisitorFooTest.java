package test;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.junit.Test;

import main.TypeVisitor;

/**
 * JUnit 4 Tests for {@link TypeVisitor} class. Checks type declaration and
 * reference counts for Foo
 *
 * @author Evan Quan
 * @since 12 March 2018
 *
 */
public class TypeVisitorFooTest {

	private static final String type = "Foo";

	/**
	 * Configures ASTParser and visitor for source file
	 * 
	 * @param source
	 *            of CompilationUnit
	 */
	private static void configureParser(String source, int expectedDeclarationCount, int expectedReferenceCount) {
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setSource(source.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setResolveBindings(true);
		parser.setBindingsRecovery(true);
		// these are needed for binding to be resolved due to SOURCE is a char[]
		String[] srcPath = { TestSuite.SOURCE_DIR };
		String[] classPath = { TestSuite.BIN_DIR };
		parser.setEnvironment(classPath, srcPath, null, true);
		// parser.setEnvironment(null, null, null, true);
		// TODO: Fix up the name to be something other than name?
		parser.setUnitName("Name");

		// ensures nodes are being parsed properly
		Map<String, String> options = JavaCore.getOptions();
		options.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_1_8);
		options.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, JavaCore.VERSION_1_8);
		options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_8);
		parser.setCompilerOptions(options);

		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);

		TypeVisitor visitor = new TypeVisitor();
		cu.accept(visitor);

		int decl_count = 0;
		int ref_count = 0;
		try {
			decl_count = visitor.getDecCount().get(type);
		} catch (Exception e) {

		}
		try {
			ref_count = visitor.getRefCount().get(type);
		} catch (Exception e) {

		}

		assertEquals(expectedDeclarationCount, decl_count);
		assertEquals(expectedReferenceCount, ref_count);

	}

	@Test
	public void testOtherClassDeclaration_Dec_0_Ref_0() {
		configureParser("class Other {}", 0, 0);
	}

	@Test
	public void testClassDeclaration_Dec_1_Ref_0() {
		configureParser("class Foo {}", 1, 0);
	}

	@Test
	public void testInterfaceDeclaration_Dec_1_Ref_0() {
		configureParser("interface Foo {}", 1, 0);
	}

	@Test
	public void testEnumDeclaration_Dec_1_Ref_0() {
		configureParser("enum Foo {}", 1, 0);
	}

	@Test
	public void testAnnotationDeclaration_Dec_1_Ref_0() {
		configureParser("@interface Foo {}", 1, 0);
	}

	@Test
	public void testMethodReturn_Dec_1_Ref_1() {
		configureParser("public class Foo{ public Foo methodName() {}}", 1, 1);
	}

	@Test
	public void testDeclareVariable_Dec_1_Ref_1() {
		configureParser("public class Foo{ Foo foo;}", 1, 1);
	}

	@Test
	public void testSetVariable_Dec_1_Ref_0() {
		configureParser("public class Foo{ foo = anotherFoo;}", 1, 0);
	}

	@Test
	public void testDeclareAndInstantiateVariable_Dec_1_Ref_2() {
		configureParser("public class Foo{ Foo foo = new Foo();}", 1, 2);
	}

	@Test
	public void testInstantiateVariable_Dec_1_Ref_1() {
		configureParser("public class Other{ foo = new Foo();}", 1, 1);
	}

	/**
	 * NOTE: This may not be correct
	 */
	@Test
	public void testOtherParamterizedTypes_Dec_1_Ref_4() {
		configureParser("public class Foo{ Foo foo = new FooChild<String, Integer>();}", 1, 1);
	}

	@Test
	public void testParameterizedTypes_Dec_0_Ref_2() {
		configureParser("public class Other{ ArrayList<Foo> list = new ArrayList<Foo>()", 0, 2);
	}

	@Test
	public void testDoubleParameterizedTypes_Dec_0_Ref_4() {
		configureParser("public class Other{ HashMap<Foo, Foo> map = new ArrayList<Foo, Foo>()", 0, 4);
	}

	@Test
	public void testPackageFoo_Dec_0_Ref_0() {
		configureParser("package bar; public class Foo {}", 0, 0);
	}
}
