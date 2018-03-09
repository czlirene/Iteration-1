package main;

import java.io.IOException;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;

/**
 * Takes a pathname to indicate a directory of interest and a string to indicate
 * a fully qualified name of a Java type. Counts the number of declarations of a
 * Java type and references of each occurrence of that type within that
 * directory.
 *
 *
 * @author Evan Quan
 * @since March 8, 2018
 *
 */
public class TypeFinder {

	/**
	 * Command line argument index for the directory path of interest
	 */
	public static final int DIRECTORY_PATH = 0;
	/**
	 * Command line argument index for Java type of interest
	 */
	public static final int JAVA_TYPE = 1;
	/**
	 * The number of command line arguments the user needs to input in order
	 * for the program to propertly work.
	 */
	public static final int VALID_ARGUMENT_COUNT = 2;
	/**
	 * Error message when the user inputs a directory that TypeFinder cannot recognize.
	 * This may be because the directory does not exist, or is not accessible.
	 */
	public static final String INVALID_DIRECTORY_ERROR_MESSAGE = "Error: Invalid directory.";
	/**
	 * An IOException should never run (as opposed to a NotDirectoryException)
	 * because files that cannot be accessed or do not exist are not considered
	 * when looking for Java files in a directory anyways.
	 */
	public static final String YOU_DUN_GOOFED_UP_MESSAGE = "Error: This should never run.";
	/**
	 * Error message when the user inputs an incorrect number of command line arguments
	 * when running the program. Prompts the user on what the program does and
	 * how to use it properly.
	 */
	public static final String PROPER_USE_MESSAGE = "TypeFinder: finds the number of declarations and references of a Java type, for all Java files in a given directory.\nProper use:\njava TypeFinder <directory path> <Java type>";

	/**
	 * Initiates program
	 *
	 * @param args command line arguments
	 *		args[0] path of directory of interest
	 *		args[1] name of Java type to search for declarations and references
	 */
	public static void main(String[] args) {

		// Check if user has inputted a valid number arguments.
		if (args.length != VALID_ARGUMENT_COUNT) {
			System.out.println(PROPER_USE_MESSAGE);
			return; // End program
		}
		
		// Get input from command line arguments
		String directory = args[DIRECTORY_PATH];
		String type = args[JAVA_TYPE];

		// Get all Java file contents from directory
		try {
			ArrayList<String> javaFiles = JavaFileReader.getAllJavaFilesToString(directory);
		} catch (NotDirectoryException e) {
			System.err.println(INVALID_DIRECTORY_ERROR_MESSAGE);
			return; // End program
		} catch (IOException e) {
			// This should never run
			System.err.println(YOU_DUN_GOOFED_UP_MESSAGE);
		}

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
		int declarationCount = visitor.getDeclarationCount();
		int referenceCount = visitor.getReferenceCount();

		// Final output
		System.out.println(
				type + ". Declarations found: " + declarationCount + "; references found: " + referenceCount + ".");
	}
}
