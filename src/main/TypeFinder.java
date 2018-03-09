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

	public static int DIRECTORY_PATH = 0;
	public static int JAVA_TYPE = 1;
	public static String INVALID_DIRECTORY_ERROR_MESSAGE = "Error: Invalid directory.";
	public static String YOU_DUN_GOOFED_UP_MESSAGE = "Error: This should never run.";

	/**
	 * Initiates program
	 *
	 * @param args
	 */
	public static void main(String[] args) {

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
		// included???

		// Instantiate visitor
		TypeVisitor visitor = new TypeVisitor(type);

		// TODO HERE
		// Instantiate ASTParser
		// Configure parser
		// FOR LOOP START
		// Have parser read all Java file contents and create needed ASTs
		// Extract declaration and reference information from ASTs
		// Potentially using visitor
		// Increment declarationCount and referenceCount
		// FOR LOOP END
		// EZ Clap

		int declarationCount = visitor.getDeclarationCount();
		int referenceCount = visitor.getReferenceCount();

		// Final output
		System.out.println(
				type + ". Declarations found: " + declarationCount + "; references found: " + referenceCount + ".");
	}

}
