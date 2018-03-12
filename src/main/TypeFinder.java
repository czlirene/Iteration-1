package main;

import java.io.IOException;
import java.nio.file.NotDirectoryException;
import java.util.*;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class TypeFinder {
	private static boolean DEBUG = true;

	private static void debug(String msg){
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
/* 		if (args.length != VALID_ARGUMENT_COUNT) {
			// Display usage, exit program.
			System.out.println("Usage: java TypeFinder <directory> <type>");
			System.exit(0);
		} */

		// directory = args[0];
		directory = "/home/slchan/eclipse-workspace/SENG300G1/src/main/FUCK/";
		// java_type = args[1];
		java_type = "no";

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
		initFinder(args);

		/* Create AST */

		ASTParser parser;

		// String file = 

		for (String file : java_files_as_string){
			debug(file);

			parser = ASTParser.newParser(AST.JLS8);
			parser.setSource(file.toCharArray());
			parser.setKind(ASTParser.K_COMPILATION_UNIT);
			parser.setResolveBindings(true);
			parser.setBindingsRecovery(true);
			// these are needed for binding to be resolved due to SOURCE is a char[]
			String[] srcPath = {"/home/slchan/eclipse-workspace/SENG300G1/src/"};
			String[] classPath = {"/home/slchan/eclipse-workspace/SENG300G1/bin/"};
			parser.setEnvironment(classPath, srcPath, null, true);
			// parser.setEnvironment(null, null, null, true);
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
		}

	}
}
