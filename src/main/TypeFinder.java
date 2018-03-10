package main;

import java.io.IOException;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class TypeFinder {
	private boolean DEBUG = true;

	private void debug(String msg){
		if (DEBUG) {
			System.out.println("DEBUG >> " + msg);
		}
	}

	/* GLOBAL VARIABLES */
	private static final int VALID_ARGUMENT_COUNT = 2;


	public static void main(String[] args) {
		/* Initialization process */
		// Check if user has inputed a valid number arguments.
		if (args.length != VALID_ARGUMENT_COUNT) {
			// Display usage, exit program.
			System.out.println("Usage: java TypeFinder <directory> <type>");
			System.exit(0);
		}
		
		// Initialize local variables
		String directory = args[0];
		String java_type = args[1];

		int dec_count = 0;
		int ref_count = 0;

		// Get all Java file contents from directory
		try {
			ArrayList<String> javaFiles = JavaFileReader.getAllJavaFilesToString(directory);
		} catch (NotDirectoryException e) {
			System.err.println("Error: Invalid directory");
			System.exit(0);
		} catch (Exception e) {
			// All other exceptions, debug 
			e.printStackTrace();
		}

		/* Create AST */
		ASTParser parser;

		for (String file : javaFiles){
			parser = ASTParser.newParser(AST.JLS9);
			parser.setSource(file);
			parser.setKind(ASTParser.K_COMPILATION_UNIT);
			parser.setResolveBindings(true);
			parser.setBindingsRecovery(true);

			
		}

	}
}
