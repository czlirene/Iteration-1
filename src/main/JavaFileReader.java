package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Detects Java files in a directory. Reads source code from Java files.
 * 
 * @author Evan Quan
 * @since March 5, 2018
 */
public class JavaFileReader {

	/**
	 * Cannot be instantiated
	 */
	private JavaFileReader() {
	}

	/**
	 * Reads the contents of a .java file and returns the contents as a string
	 * 
	 * @param path
	 *            of .java to read
	 * @return contents of .java file
	 * @throws IOException
	 *             if file not found
	 */
	public static String getFileToString(String file) throws IOException {
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		StringBuffer stringBuffer = new StringBuffer();
		String lineSeparator = System.getProperty("line.separator");

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

		// Sort names alphabetically, as they may not already be
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
}
