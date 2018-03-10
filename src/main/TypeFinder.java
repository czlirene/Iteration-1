package main;

import java.io.IOException;
import java.nio.file.NotDirectoryException;
import java.util.*;

import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class TypeFinder {
	private boolean DEBUG = true;

	private void debug(String msg){
		if (DEBUG) {
			System.out.println("DEBUG >> " + msg);
		}
	}

	/* GLOBAL VARIABLES */
	// 
	private static final int VALID_ARGUMENT_COUNT = 2;

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
//		PrimitiveType, SimpleType, AnnotationType,
		ASTParser parser;
		String file = "package main;" +
"public class MoreTest {" +
"		public int add(int a, int b){" +
"			int c = a+b;" +
"			String wtf = \"hey\";" +
"			System.out.println(wtf);" +
"			Integer x = 4;" +
"			return c;" +
"		}" +
"		public class Test {" +
"			public int add(int a, int b){" +
"				int t = a+b;" +
"				return t;" +
"			}" +
"		}" +
"		public static int d;" +
"}";
		

		


		// String file = "public class DoesItWork{\n"
		// 				+ "private class MaybeWorks{} \n"
		// 				+ "public void add(){ 1 + 1; }"
		// 				+ "public void add2(){ add() }"
		// 				+ "String a;"
		// 				+ "a = \"Hello\";"
		// 				+ "int b;"
		// 				+ "char c;"
		// 				+ "int d;"
		// 				+ "Time e;"
		// 				+ "enum Quark{ UP, DOWN}"
		// 				+ "}\n"
		// 				+ "interface PleaseWork{}\n";
		
		// String file = "package test;\n" + 
		// 		" \n" + 
		// 		"import java.util.ArrayList;\n" + 
		// 		" \n" + 
		// 		"public class Apple {\n" + 
		// 		"	public static void main(String[] args) {\n" + 
		// 		"		ArrayList<String> al = null;\n" + 
		// 		"		int j =0;\n" + 
		// 		"		System.out.println(j);\n" + 
		// 		"		System.out.println(al);\n" + 
		// 		"	}\n" + 
		// 		"}";
		
		// for (String file : javaFiles){
			parser = ASTParser.newParser(AST.JLS9);
			parser.setSource(file.toCharArray());
			parser.setKind(ASTParser.K_COMPILATION_UNIT);
			parser.setResolveBindings(true);
			parser.setBindingsRecovery(true);
			// these two are needed for binding to be resolved due to SOURCE is a char[]
			parser.setEnvironment(null, null, null, true);
			parser.setUnitName("Name");

			final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
			
			TypeVisitor visitor = new TypeVisitor();
			cu.accept(visitor);
			
			Map decCounter = visitor.getDecCount();
			System.out.println(decCounter.toString());
		// }

	}
}
