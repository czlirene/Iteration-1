package main;

<<<<<<< HEAD
import java.util.*;

import org.eclipse.jdt.core.dom.*;


/* List of visit(X nodes) that I don't need:
	- 
	- ArrayType, covered by Variables and Field

   List of visit(X node) that I don't know if I need:
	- AnnonymousClassDeclaration
=======
import java.math.BigInteger;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;

/**
 * The purpose of this visitor is to traverse through all the nodes and
 * increment declaration and reference counts of the desired Java type when it
 * comes across the relevant nodes.
 * 
 * However, we may not actually need to use a visitor at all if we can figure
 * out another way to do this.
 * 
 * NOTE: The following is all rough work/planning rather than actual code. I'm
 * not entirely sure how this is going to work out. Either the Type the visitor
 * is looking for is determined by the generic, or as an argument in the
 * constructor. If done through the constructor, it is received as a String (and
 * somehow we connect that with a class.)
 * 
 * There's also a flaw with how this currently works. While Java types typically
 * use their simple names (ex. "String", "Potato", "ArrayList"), they can also
 * be referred with their complete names (ex. "java.lang.String",
 * "package.foo.Potato", "java.util.ArrayList"), which may be practically used
 * if multiple classes in the same file have the same simple name. As a result,
 * we need to somehow consider Java files which include multiple classes of the
 * same name, and input type (the argument) with its package included.
 * 
 * @author Evan Quan
 * @since March 9, 2018
 *
>>>>>>> origin/master
 */

public class TypeVisitor extends ASTVisitor {
	private boolean DEBUG = true;

<<<<<<< HEAD
	private void debug(String msg){
		if (DEBUG) {
			System.out.println("DEBUG >> " + msg);
		}
	}

	private void debug(String var, String type){
		if (DEBUG) {
			System.out.println("DEBUG >> " + var + " : " + type);
		}
	}

	// Global variables
	private static List<String> types;
	private static Map<String, Integer> decCounter;
	private static Map<String, Integer> refCounter;

	// constructor
	public TypeVisitor(){
		// initialize list and counters to null 
		types = new ArrayList<String>();
		decCounter = new HashMap<String, Integer>();
		refCounter = new HashMap<String, Integer>();
	}

	/* HELPER FUNCTIONS */

	/**
		Check if the type currently exists in the list "types", 
			[true -> do nothing]
			[false -> add the type to "types",
					  initialize type in declaration counter
					  initialize type in reference counter]
	 */
	private static void addTypeToList(String type){
		if (!types.contains(type)){
			types.add(type);
			decCounter.put(type, 0);
			refCounter.put(type, 0);
		}
	}

	/**
		Increment declaration count for a given type
	 */	
	private static void incDecCount(String type){
		// Check if the type exists, then increment their associated value by 1
		// TODO: Redundant check? 
		if (decCounter.containsKey(type)){
			decCounter.put(type, decCounter.get(type)+1);
		}
=======
	private BigIncrementer declarationCount;
	private BigIncrementer referenceCount;
	private String type;

	/**
	 * Complete constructor
	 * 
	 * @param type
	 */
	public TypeVisitor(String type) {
		declarationCount = new BigIncrementer();
		referenceCount = new BigIncrementer();
		this.type = type;
>>>>>>> origin/master
	}

	/**
		Increment reference counter for a given type
	 */
<<<<<<< HEAD
	private static void incRefCount(String type){
		// Check if the type exists, then increment their associated value by 1
		// TODO: redundant check?
		if (refCounter.containsKey(type)){
			refCounter.put(type, refCounter.get(type)+1);
=======
	public void postVisit(AbstractTypeDeclaration node) {
		SimpleName name = node.getName();
		// Check that the node is of the desired type
		if (name.getIdentifier().equals(type)) {
			if (name.isDeclaration()) {
				// The node's name represents a name that is being defined, as opposed to one
				// being referenced
				declarationCount.increment();
			} else {
				// the name is one being referenced
				referenceCount.increment();
			}
>>>>>>> origin/master
		}
	}

	/* ----------------------- VISITOR METHODS ----------------------- */

	/**
		Assignment expression nodes.
		Status: WIP
		TODO: Check if this is only reference
	 */
<<<<<<< HEAD
	// public boolean visit(ExpressionStatement node){
	// 	ITypeBinding typeBind = node.resolveTypeBinding();
	// 	if (typeBind == null){
	// 		debug("fuck me", "");
	// 	} else {
	// 		String type = typeBind.getQualifiedName();
	// 		debug("", type);
	// 		addTypeToList(type);

	// 	}


	// 	return true;
	// }

	/** 
		ClassInstanceCreation; {expr} = new {type}
		Given how we're instantiating a new class, all of these will be considered as declarations.
		Counter: Declaration
	 */
	public boolean visit(ClassInstanceCreation node){
		ITypeBinding typeBind = node.resolveTypeBinding();
		String type = typeBind.getQualifiedName();

		/* Debug ONLY: Get the parent variable name if it exists */
		String parent = ((VariableDeclarationFragment) node.getParent()).getName().toString();
		debug(parent ,type);

		addTypeToList(type);
		incDecCount(type);

		return true;
=======
	public BigInteger getDeclarationCount() {
		return declarationCount.getBigInteger();
>>>>>>> origin/master
	}

	/**
		EnumDeclaration; enum ID {...}
		Counter: Declaration
	 */
<<<<<<< HEAD
	public boolean visit(EnumDeclaration node){
		ITypeBinding typeBind = node.resolveBinding();
		String type = typeBind.getQualifiedName();

		debug("enum", type);

		addTypeToList(type);
		incDecCount(type);

		return true;
=======
	public BigInteger getReferenceCount() {
		return referenceCount.getBigInteger();
>>>>>>> origin/master
	}

	/**
		Field declaration nodes
		ADDED: Support distinction between Primitive Data types (Reference Only) and others (TBD).
		CHANGED: these should ALL be references..
		TODO: Confirm Non-Primitive are REF/DECL.
	*/
	public boolean visit(FieldDeclaration node){
		ITypeBinding typeBind = node.getType().resolveBinding();
		String type = typeBind.getQualifiedName();
		String name = "";

		/* For debug purpose */
		Object o = node.fragments().get(0);
		if (o instanceof VariableDeclarationFragment){
			name = ((VariableDeclarationFragment) o).getName().toString();
			debug(name, type);
		}

		// Add current node's type to the list of types
		addTypeToList(type);
		// check if node is of primitive type
		if (node.getType().isPrimitiveType()){
			debug(name + " is PRIMITIVE TYPE");
			// increase reference count
			incRefCount(type);
		} else {
			// otherwise, increase reference count
			incRefCount(type);
		}
		return true;
	}

	/**
		Marker annotation nodes: @TypeName
		TODO: Check if AnnotationTypeDeclaration is still needed
		TODO: Cannot recognize full qualified name 
			e.g. @Test from org.junit.Test only appears as Test
	*/
	public boolean visit(MarkerAnnotation node){

		IAnnotationBinding annBind = node.resolveAnnotationBinding();
		ITypeBinding typeBind = annBind.getAnnotationType();
		String type = typeBind.getQualifiedName();
		debug("", type);

		addTypeToList(type);
		incRefCount(type);
		return true;
	}

	/**
		SingleVariableDeclaration; formal parameter lists, and catch clause variables
		ADDED: Distinction between Primitive type and others
		
		TODO: Confirm Non-primitive type COUNTER
	*/
	public boolean visit(SingleVariableDeclaration node){
		IVariableBinding varBind = node.resolveBinding();
		ITypeBinding typeBind = varBind.getType();
		String type = typeBind.getQualifiedName();
		debug(node.getName().toString(), type);

		// Add current node's type to the list of types
		addTypeToList(type);
		// check if node is of primitive type
		if (node.getType().isPrimitiveType()){
			debug(node.getName().toString() + " is PRIMITIVE TYPE");
			// increase reference count
			incRefCount(type);
		} else {
			// otherwise, increase declaration count
			incDecCount(type);
		}

		return true;
	}


	/**
		TypeDeclaration; union of class and interface declaration nodes
		Status: Done
		TODO: Confirm they are only DECLARATIONS
	*/
	public boolean visit(TypeDeclaration node){
		ITypeBinding typeBind = node.resolveBinding();
		String type = typeBind.getQualifiedName();

		debug(node.getName().toString(), type);
		
		addTypeToList(type);
		incDecCount(type);

		return true;
	}

	/**
		VariableDeclarationStatement; local variable declaration statement nodes.
		ADDED: Distinction between Primitive type and others.

		TODO: Confirm Non-primitive type only count as REFERENCES
	 */
	public boolean visit(VariableDeclarationStatement node){
		ITypeBinding typeBind = node.getType().resolveBinding();
		String type = typeBind.getQualifiedName();
		
		// debugging purpose, get the actual variable name
		// print out (varName, varType)
		Object o = node.fragments().get(0);
		String name = "";
		if (o instanceof VariableDeclarationFragment){
			name = ((VariableDeclarationFragment) o).getName().toString();
			debug(name, type);
		}

		// Add current node's type to the list of types
		addTypeToList(type);
		// check if node is of primitive type
		if (node.getType().isPrimitiveType()){
			debug(name + " is PRIMITIVE TYPE");
			// increase reference count
			incRefCount(type);
		} else {
			// otherwise, increase declaration count
			incDecCount(type);
		}

		return true;
	}


	public Map getDecCount(){
		return decCounter;
	}

	public Map getRefCount(){
		return refCounter;
	}

	public List getList(){
		return types;
	}
}


/* TODO: find out if we still need this method */
// 	public boolean visit(VariableDeclarationFragment node){
// 		IVariableBinding varBind = node.resolveBinding();
// 		ITypeBinding typeBind = varBind.getType();
// 		String type = typeBind.getQualifiedName();
// 		String name = node.getName().toString();
// 		debug(name, type);
// 		ChildPropertyDescriptor initializer = node.getInitializerProperty();

// 		if (initializer != null){
// 			System.out.println(initializer.toString());
// }
// 		return true;
// 	}