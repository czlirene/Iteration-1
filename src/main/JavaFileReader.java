package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Detects Java files in a directory. Reads source code from Java files.
 * Retrieves source code from Java files.
 *
 * @author Evan Quan
 * @since March 9, 2018
 */
public class JavaFileReader {

	/**
	 * Reads the contents of all Java files in a given directory and returns their
	 * contents as Strings
	 *
	 * @param directory
	 *            of interest
	 * @return contents of all Java files as Strings
	 * @throws DirectoryNotFoundException
	 *             if directory cannot be found
	 * @throws IOException
	 *             if a Java file in the directory is not able to be read.
	 *             Hypothetically, this exception should never run as Java files
	 *             that do not exist or cannot be accessed are ignored.
	 */
	public static ArrayList<String> getAllJavaFilesToString(String directory)
			throws DirectoryNotEmptyException, IOException {
		// Get all Java files
		ArrayList<String> javaFileNames = getJavaFileNames(directory);
		ArrayList<String> javaFilesToString = new ArrayList<String>();

		// Get the contents of every Java file
		String filePath;
		for (String javaFile : javaFileNames) {
			filePath = directory + "/" + javaFile;
			javaFilesToString.add(getFileContentsToString(filePath));
		}

		return javaFilesToString;
	}

	/**
	 * Reads the contents of a file with a given path and returns the contents of
	 * that file as a String.
	 *
	 * @param path
	 *            of file to read
	 * @return contents of file
	 * @throws IOException
	 *             if file is not able to be read
	 */
	public static String getFileContentsToString(String file) throws IOException {
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		StringBuffer stringBuffer = new StringBuffer();
		String lineSeparator = System.getProperty("line.separator");

		// Keep reading line by line (and ending with a line separator)
		// Until no more lines can be read
		// Each line is appended to the output stringBuffer
		try {
			String line = bufferedReader.readLine();
			while (line != null) {
				stringBuffer.append(line);
				stringBuffer.append(lineSeparator);
				line = bufferedReader.readLine();
			}
			return stringBuffer.toString();
		} finally {
			bufferedReader.close();
		}
	}

	/**
	 * Get the names of all the Java files in a given directory, with extension
	 *
	 * @param directory
	 *            path of interest
	 * @return all the Java file names in the directory, in alphabetical order
	 * @throws NotDirectoryException
	 *             if directory cannot be found
	 */
	public static ArrayList<String> getJavaFileNames(String directory) throws NotDirectoryException {
		ArrayList<String> javaFileNames = new ArrayList<String>();

		// Get all the files in the directory
		File[] files = new File(directory).listFiles();
		// If this pathname does not denote a directory, then listFiles() returns null.
		if (files == null) {
			throw new NotDirectoryException(directory);
		}

		for (File file : files) {
			// Only accept Java files
			if (file.isFile() && isJavaFile(file.getName())) {
				javaFileNames.add(file.getName());
			}
		}

		// Sort names alphabetically, as they may not already be.
		// NOTE for iteration 1: This is only necessary for testing
		// by making the order of file names predictable. Otherwise,
		// it has no meaningful effect on the results.
		Collections.sort(javaFileNames);
		return javaFileNames;
	}

	/**
	 * Checks if a file is a Java file
	 *
	 * @param fileName
	 *            name of file
	 * @return true if file is a Java file, else false
	 */
	public static boolean isJavaFile(String fileName) {
		return fileName.endsWith(".java");
	}

	/**
	 * Cannot be instantiated
	 * 
	 * NOTE: As it stands now, this class has no fields (only has methods), making
	 * instantiation unnecessary. If later down the road, fields are necessary,
	 * allow class to be instantiated and make methods no longer static as needed.
	 */
	private JavaFileReader() {
	}

}
