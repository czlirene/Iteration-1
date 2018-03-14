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
 * reference counts for Java built-in classes
 * 
 * @author Evan Quan
 * @since 12 March, 2018
 *
 */
public class TypeVisitorBuiltInTest {

	/**
	 * Configures ASTParser and visitor for source file
	 * 
	 * @param source
	 * @param type
	 * @param expectedDeclarationCount
	 * @param expectedReferenceCount
	 */
	private static void configureParser(String source, String type, int expectedDeclarationCount,
			int expectedReferenceCount) {
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

	/**
	 * Check that a reference to String does not default to local String class
	 */
	@Test
	public void testString_Dec_0_Ref_0() {
		configureParser("class Foo { String str; }", "String", 0, 0);
	}

	/**
	 * Check that a reference to String defaults to java.lang.String
	 */
	@Test
	public void testJavaLangString_Dec_0_Ref_1() {
		configureParser("class Foo { String str; }", "java.lang.String", 0, 1);
	}

	/**
	 * Check that a reference to Hash Map defaults to local HashMap with no imports
	 */
	@Test
	public void testHashMap_Dec_0_Ref_1() {
		configureParser("class Foo { HashMap map;}", "HashMap", 0, 1);
	}

	/**
	 * Check that a reference to HashMap does not default to local HashMap with
	 * java.util.HashMap import
	 */
	@Test
	public void testHashMapImported_Dec_0_Ref_0() {
		configureParser("import java.util.HashMap class Foo { HashMap map;}", "HashMap", 0, 0);
	}

	/**
	 * Check that a reference to HashMap defaults to java.util.HashMap with
	 * java.util.HashMap import
	 */
	@Test
	public void testJavaUtilHashMapImported_Dec_0_Ref_1() {
		configureParser("import java.util.HashMap class Foo { HashMap map;}", "java.util.HashMap", 0, 1);
	}

	/**
	 * Check that a reference to HashMap defaults to java.util.HashMap with
	 * java.util.* import
	 */
	@Test
	public void testJavaUtilAllImported_Dec_0_Ref_1() {
		configureParser("import java.util.* class Foo { HashMap map;}", "java.util.HashMap", 0, 1);
	}

	/**
	 * Check that a reference to HashMap does not default to local HashMap with
	 * java.util.* import
	 */
	@Test
	public void testJavaUtilAllImported_Dec_0_Ref_0() {
		configureParser("import java.util.* class Foo { HashMap map;}", "HashMap", 0, 0);
	}

	/**
	 * Check that a reference to HashMap<String,Integer> defaults to
	 * java.util.HashMap with java.util.HashMap import
	 */
	@Test
	public void testJavaUtilHashMapImportedParameterized_Dec_0_Ref_1() {
		configureParser("import java.util.HashMap class Foo { HashMap<String, Integer> map;}", "java.util.HashMap", 0,
				1);
	}

	/**
	 * Check that a reference to HashMap<String, Integer> defaults to
	 * java.util.HashMap with java.util.HashMap import
	 */
	@Test
	public void testJavaUtilHashMapImportedParameterizedAndDeclared_Dec_0_Ref_2() {
		configureParser(
				"import java.util.HashMap class Foo { HashMap<String, Integer> map = new HashMap<String, Integer>();}",
				"java.util.HashMap", 0, 2);
	}

	/**
	 * Check that a reference to String as a generic parameter of java.util.HashMap
	 * defaults to java.lang.String
	 */
	@Test
	public void testJavaLangStringParameterizedHashMap_Dec_0_Ref_1() {
		configureParser("import java.util.HashMap class Foo { HashMap<String, Integer> map;}", "java.lang.String", 0,
				1);
	}

	/**
	 * Check that a reference to String as a generic parameter of java.util.HashMap
	 * defaults to java.lang.String
	 */
	@Test
	public void testJavaLangStringParameterizedAndDeclaredHashMap_Dec_0_Ref_2() {
		configureParser(
				"import java.util.HashMap class Foo { HashMap<String, Integer> map = new HashMap<String, Integer>();}",
				"java.lang.String", 0, 2);
	}

	/**
	 * Check that a reference to String as both generic parameters of
	 * java.util.HashMap defaults to java.lang.String
	 */
	@Test
	public void testJavaLangStringParameterizedAndDeclaredHashMap_Dec_0_Ref_4() {
		configureParser(
				"import java.util.HashMap class Foo { HashMap<String, String> map = new HashMap<String, String>();}",
				"java.lang.String", 0, 4);
	}

	/**
	 * Check that a reference to String as a generic parameter of Foo defaults to
	 * java.lang.String
	 */
	@Test
	public void testJavaLangStringParameterizedFoo_Dec_0_Ref_1() {
		configureParser("class Other { Foo<String> foo;}", "java.lang.String", 0, 1);
	}

	/**
	 * Check that a reference to String as a generic parameter of Foo defaults to
	 * java.lang.String
	 */
	@Test
	public void testJavaLangStringParameterizedAndDeclaredFoo_Dec_0_Ref_2() {
		configureParser("class Other { Foo<String> foo = new Foo<String>();}", "java.lang.String", 0, 2);
	}

	/**
	 * Check if returning a static field of an array of String counts as a reference
	 */
	@Test
	public void testReturnStaticField_Dec_0_Ref_1() {
		configureParser("class Other { int length = String[].length;}", "java.lang.String", 0, 1);
	}

	/**
	 * Check that a reference to String as a generic parameter of Foo defaults to
	 * java.lang.String
	 */
	@Test
	public void testJavaLangStringParameterizedList_Dec_0_Ref_1() {
		configureParser("import java.util.List; class Other { List<String> list;}", "java.lang.String", 0, 1);
	}

	/**
	 * Check that a reference to String as a generic parameter of Foo defaults to
	 * java.lang.String
	 */
	@Test
	public void testJavaLangStringParameterizedAndDeclaredList_Dec_0_Ref_2() {
		configureParser("import java.util.List; class Other { List<String> list = new List<String>();}",
				"java.lang.String", 0, 2);
	}

	/**
	 * Check if initializing a variable of String within a for-loop counts as a
	 * reference
	 */
	@Test
	public void testForLoopInitialization_Dec_0_Ref_1() {
		configureParser("public class Other { public void method() { for (String s;;){}}}", "java.lang.String", 0, 1);
	}

	/**
	 * Check that creating a variable of an array of String counts as a reference
	 */
	@Test
	public void testArrayDeclareVariable_Dec_0_Ref_1() {
		configureParser("public class Other {String[] str;}", "java.lang.String", 0, 1);
	}

	@Test
	public void testArrayDeclarableVariableAndAllocate_Dec_0_Ref_2() {
		configureParser("public class Other {String[] str = new String[1];}", "java.lang.String", 0, 2);
	}

	@Test
	public void testArrayDeclarableVariableAndAllocate_Dec_0_Ref_1() {
		configureParser("public class Other {Bar[] bar = new String[1];}", "java.lang.String", 0, 1);
	}

}
