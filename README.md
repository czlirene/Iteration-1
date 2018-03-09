SENG300 Group Iteration 1
-------------------------

Team Members
----
Evan Quan
- evan.quan@ucalgary.ca

Irene Chan
- slchan@ucalgary.ca

Patrick Gharib
- patrick.a.gharib@gmail.com

Tasks
-----
- Detect all Java files in a directory (DONE)
	- Having the user input the paths for each individual file is not enough because we need to be able to read from ALL java files in a directory
	- Remember to check for `.java` file ending
- Convert file contents to string (DONE)
	- Done 1 file at a time
	- Should probably save to an ArrayList<String>
- Configure AST parser to parse file contents
	- Multiple configurations needed?
- Get Type declaration and reference information
	- Acquired from the outputted AST (of whatever type)

Classes
-------
TypeFinder
- Main
- Gets user input
- Outputs results

JavaFileReader
- Gets files from input directory
- Converts file contents to Strings

ASTParser
- Parses file contents

ASTNode
- Get declaration and reference information from file contents

Do we need some sort of visitor?

Resources
---------
- [ASTParser Javadoc](https://help.eclipse.org/mars/index.jsp?topic=%2Forg.eclipse.jdt.doc.isv%2Freference%2Fapi%2Forg%2Feclipse%2Fjdt%2Fcore%2Fdom%2FASTParser.html)
- [Diagram Drawing Tool](https://draw.io)
	- When changes are made, export both the `.xml` and `.png` files to the report directory
