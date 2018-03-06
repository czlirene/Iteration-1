package main;

import java.util.Scanner;

/**
 * Takes a pathname to indicate a directory of interest and a string to indicate
 * a fully qualified name of a Java type. Counts the number of declarations of a
 * Java type and references of each occurrence of that type within that
 * directory.
 *
 *
 * @author Evan Quan
 * @since March 5, 2018
 *
 */
public class TypeFinder {

	/**
	 * Initiates program
	 *
	 * @param args
	 */
	public static void main(String[] args) {

		// Prompt user
		System.out.println("Type Finder");
		System.out.println(
				"Counts the number of declarations of a Java type and number of references of each occurrence of that type within a directory.\n");

		Scanner in = new Scanner(System.in);

		// Get input from user
		System.out.print("Absolute path of directory of interest\n(Start with \"./\" for relative path)\n> ");
		String directory = in.nextLine();

		System.out.print("\nJava Type\n> ");
		String type = in.next();

		int declarationCount = 0;
		int referenceCount = 0;
		// TODO HERE
		// Get all Java file contents
		// Instantiate ASTParser
		// Configure parser
		// FOR LOOP START
		// Have parser read all Java file contents and create needed ASTs
		// Extract declaration and reference information from ASTs
		// Increment declarationCount and referenceCount
		// FOR LOOP END
		// EZ Clap

		// Final output
		System.out.println("\n" + type + ". Declarations found: " + declarationCount + "; references found: "
				+ referenceCount + ".");

		in.close();
	}

}
