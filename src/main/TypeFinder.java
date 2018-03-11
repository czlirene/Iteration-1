package main;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.NotDirectoryException;
import java.util.*;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class TypeFinder {
	private boolean DEBUG = true;

<<<<<<< HEAD
	private void debug(String msg){
		if (DEBUG) {
			System.out.println("DEBUG >> " + msg);
		}
	}

	/* GLOBAL VARIABLES */
	// 
	private static final int VALID_ARGUMENT_COUNT = 2;
=======
	/**
	 * Command line argument index for the directory path of interest
	 */
	public static final int DIRECTORY_PATH = 0;
	/**
	 * Command line argument index for Java type of interest
	 */
	public static final int JAVA_TYPE = 1;
	/**
	 * The number of command line arguments the user needs to input in order for the
	 * program to properly work.
	 */
	public static final int VALID_ARGUMENT_COUNT = 2;
	/**
	 * Error message when the user inputs a directory that TypeFinder cannot
	 * recognize. This may be because the directory does not exist, or is not
	 * accessible.
	 */
	public static final String INVALID_DIRECTORY_ERROR_MESSAGE = "Error: Invalid directory.";
	/**
	 * An IOException should never run (as opposed to a NotDirectoryException)
	 * because files that cannot be accessed or do not exist are not considered when
	 * looking for Java files in a directory anyways.
	 */
	public static final String YOU_DUN_GOOFED_UP_MESSAGE = "Error: This should never run.";
	/**
	 * Error message when the user inputs an incorrect number of command line
	 * arguments when running the program. Prompts the user on what the program does
	 * and how to use it properly.
	 */
	public static final String PROPER_USE_MESSAGE = "TypeFinder: finds the number of declarations and references of a Java type, for all Java files in a given directory.\nProper use:\njava TypeFinder <directory path> <Java type>";

	/**
	 * Initiates program
	 *
	 * @param args
	 *            command line arguments args[0] path of directory of interest
	 *            args[1] name of Java type to search for declarations and
	 *            references
	 */
	public static void main(String[] args) {
>>>>>>> origin/master

	private static String directory;
	private static String java_type;

	private static List<String> java_files_as_string = new ArrayList<String>();		// initialize it 

	private static void initFinder(String[] args){
		// Check if user has inputed a valid number arguments.
		if (args.length != VALID_ARGUMENT_COUNT) {
			// Display usage, exit program.
			System.out.println("Usage: java TypeFinder <directory> <type>");
			System.exit(0);
		}
<<<<<<< HEAD
=======

		// Get input from command line arguments
		String directory = args[DIRECTORY_PATH];
		String type = args[JAVA_TYPE];
>>>>>>> origin/master

		directory = args[0];
		java_type = args[1];

		try {
			java_files_as_string = JavaFileReader.getAllJavaFilesToString(directory);
		} catch (NotDirectoryException nde){
			System.err.println("Error: Invalid Directory");
			System.exit(0);
		} catch (Exception e){
			e.printStackTrace();
		}
	}


	public static void main(String[] args) {
		/* Initialization process */
		// initFinder(args);

		int dec_count = 0;
		int ref_count = 0;

		/* Create AST */

		ASTParser parser;
		/* String file = 
"import static org.junit.Assert.*;" +
"import static org.mockito.Mockito.*;" +
"import org.junit.After;" +
"import org.junit.Before;" +
"import org.junit.Test;" +
"import org.eclipse.jdt.core.ICompilationUnit;" +
"import org.eclipse.jdt.core.dom.AST;" +
"import org.eclipse.jdt.core.dom.ASTParser;" +
"import org.eclipse.jdt.core.dom.CompilationUnit;" +
"public class ASTParser_Tests {" +
"	" +
"	private ASTParser parser;" +
"	" +
"	@After" +
"	public void tearDown() throws Exception {" +
"		parser = null;" +
"	}" +
"	@Test" +
"	public void testCompilationUnitSource() {" +
"		parser = ASTParser.newParser(AST.JLS8);" +
"		parser.setSource(mock(ICompilationUnit.class));" +
"		assertNotNull(parser.createAST(null));" +
"		CompilationUnit cu = parser.createAST(null);" +
"		cu.accept(new ASTVisitor() {" +
"			public boolean visit(Assignment node) {" +
"				}" +
"			});" +
"	}" +
"}"; */
// 		String file = "package main;" +
// "import java.util.*;" +
// "public class MoreTest {" +
// "		" +
// "			enum Quark{ UP, DOWN};" +
// "		public static int add(int a, int b){" +
// "			int c = a+b;" +
// "			String wtf = \"hey\";" +
// "			System.out.println(wtf);" +
// "			Integer w = 4;" +
// "			Time shiballs;" +
// "			return c;" +
// "		}" +
// "		" +
// "		public boolean equals(int i, int j) {" +
// "			return i == j;" +
// "		}" +
// "		" +
// "		private class Test extends MoreTest{" +
// "			@Override" +
// "			public boolean equals(int newI, int newJ) {" +
// "				return false;" +
// "			}" +
// "			" +
// "			public int fck(int x, int y){" +
// "				int t = x+y;" +
// "				return t;" +
// "			}" +
// "		}" +
// "		public static void main (String args[]) {" +
// "			int fin = add(1,2);" +
// "		}" +
// "		public static int d;" +
// "		public List<String> hello;" +
// "		public Map<String, Integer> sup;" +
// "}";

		// String file =  "public class DoesItWork{\n"
// + "private class MaybeWorks{} \n"
// + "public void add(){ 1 + 1; }"
// + "public void add2(){ add() }"
// + "String a;"
// + "a = \"Hello\";"
// + "int b;"
// + "char c;"
// + "int d;"
// + "Time e;"
// + "enum Quark{UP, DOWN}"
// + "}\n";
// + "interface PleaseWork{}\n";
/* 		String file = "public class DoesItWork{\n"
						+ "private class MaybeWorks{} \n"
						+ "public void add(){ 1 + 1; }"
						+ "public void add2(){ add() }"
						+ "String j = new String (\"FOO\");"
						// + "DoesItWork k = new DoesItWork(AST.JLS9);"
						+ "String a;"
						+ "a = \"Hello\";"
						+ "int b;"
						+ "char c;"
						+ "int d;"
						+ "Time e;"
						 + "enum Quark{ UP, DOWN}"
						+ "}\n"
						+ "interface PleaseWork{}\n"; */
		
		String file = "package test;\n" + 
				" \n" + 
				"import java.util.ArrayList;\n" + 
				"import org.junit.Test;\n" + 
				" \n" + 
				"public class Apple {\n" + 
				"	@Test\n" + 
				"	public static void main(String[] args) {\n" + 
				"		ArrayList<String> al = null;\n" + 
				"		int j =0;\n" + 
				"		System.out.println(j);\n" + 
				"		System.out.println(al);\n" + 
				"	}\n" + 
				"}";
		
		// for (String file : javaFiles){
			parser = ASTParser.newParser(AST.JLS9);
			parser.setSource(file.toCharArray());
			parser.setKind(ASTParser.K_COMPILATION_UNIT);
			parser.setResolveBindings(true);
			parser.setBindingsRecovery(true);
			// these are needed for binding to be resolved due to SOURCE is a char[]
			parser.setEnvironment(null, null, null, true);
			// TODO: Fix up the name to be something other than name? 
			parser.setUnitName("Name");
			
			// ensures nodes are being parsed properly
			Map options = JavaCore.getOptions();
			options.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_1_8);
			options.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, JavaCore.VERSION_1_8);
			options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_8);
			parser.setCompilerOptions(options);

			final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
			
			TypeVisitor visitor = new TypeVisitor();
			cu.accept(visitor);
			
			System.out.println("========== COUNT ==========");
			List<String> keys = visitor.getList();
			Map<String, Integer> decCounter = visitor.getDecCount();
			Map<String, Integer> refCounter = visitor.getRefCount();

			for (String key : keys){
				System.out.println(key + ". Declarations found: " + decCounter.get(key) + "; References found: " + refCounter.get(key) );
			}
		// }

<<<<<<< HEAD
=======
		// NOTE: We may need to modify the type string here if the package is
		// included???. ie. Turn "java.lang.String" to "String" ???

		TypeVisitor visitor = new TypeVisitor(type);

		// TODO HERE
		// Instantiate ASTParser
		// Configure parser
		// FOR LOOP START
		// Have parser read all Java file contents and create needed ASTs
		// Extract declaration and reference information from ASTs
		// Potentially using visitor (TypeVisitor)
		// Increment declarationCount and referenceCount
		// FOR LOOP END
		// EZ Clap

		// Save results from all traversed Java files
		BigInteger declarationCount = visitor.getDeclarationCount();
		BigInteger referenceCount = visitor.getReferenceCount();

		// Final output
		System.out.println(
				type + ". Declarations found: " + declarationCount + "; references found: " + referenceCount + ".");
>>>>>>> origin/master
	}
}
