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
 * reference counts for bar.Foo
 * 
 * @author Evan Quan
 * @since 12 March 2018
 *
 */
public class TypeVisitorPackageFooTest {

	/**
	 * Foo class of package bar
	 */
	private static final String type = "bar.Foo";

	/**
	 * Configures ASTParser and visitor for source file
	 * 
	 * @param source
	 * @param expectedDeclarationCount
	 * @param expectedReferenceCount
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
	public void testDefaultPackageDeclaration_Dec_0_Ref_0() {
		configureParser("class Foo {}", 0, 0);
	}

	@Test
	public void testDefaultPackageReference_Dec_0_Ref_0() {
		configureParser("class Other { Foo f; }", 0, 0);
	}

	@Test
	public void testOtherPackageDeclaration_Dec_0_Ref_0() {
		configureParser("package other; class Foo {}", 0, 0);
	}

	@Test
	public void testOtherPackageReference_Dec_0_Ref_0() {
		configureParser("package other; class Other { Foo f; }", 0, 0);
	}

	@Test
	public void testBarPackageDeclaration_Dec_1_Ref_0() {
		configureParser("package bar; class Foo {}", 1, 0);
	}

	@Test
	public void testBarPackageReference_Dec_0_Ref_1() {
		configureParser("package bar; class Other { Foo f; }", 0, 1);
	}
}
