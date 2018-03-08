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
	public static String INVALID_DIRECTORY_ERROR_MESSAGE = "Invalid directory.";

	/**
	 * Initiates program
	 *
	 * @param args
	 */
	public static void main(String[] args) {

		// Get input from command line arguments
		String directory = args[DIRECTORY_PATH];
		String type = args[JAVA_TYPE];

		try {
			// Get all Java contents
			ArrayList<String> javaFiles = JavaFileReader.getAllJavaFilesToString(directory);
			// DEBUG Remove this
			for (String file : javaFiles) {
				System.out.println(file);
			}
		} catch (NotDirectoryException e) {
			System.out.println(INVALID_DIRECTORY_ERROR_MESSAGE);
			return; // End program
		} catch (IOException e) {
			// This should never run
			e.printStackTrace();
		}

		int declarationCount = 0;
		int referenceCount = 0;
		// TODO HERE
		// Instantiate ASTParser
		// Configure parser
		// FOR LOOP START
		// Have parser read all Java file contents and create needed ASTs
		// Extract declaration and reference information from ASTs
		// Increment declarationCount and referenceCount
		// FOR LOOP END
		// EZ Clap

		// Final output
		System.out.println(
				type + ". Declarations found: " + declarationCount + "; references found: " + referenceCount + ".");
	}

}
