package main;

import java.util.*;

import org.eclipse.jdt.core.dom.*;


/* List of visit(X nodes) that I don't need:
	- 
	- ArrayType, covered by Variables and Field

   List of visit(X node) that I don't know if I need:
	- AnnonymousClassDeclaration
 */

public class TypeVisitor extends ASTVisitor {
	private boolean DEBUG = true;

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
	}

	/**
		Increment reference counter for a given type
	 */
	private static void incRefCount(String type){
		// Check if the type exists, then increment their associated value by 1
		// TODO: redundant check?
		if (refCounter.containsKey(type)){
			refCounter.put(type, refCounter.get(type)+1);
		}
	}

	/* ----------------------- VISITOR METHODS ----------------------- */

	/**
		Assignment expression nodes.
		Status: WIP
		TODO: Check if this is only reference
	 */
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
	}

	public boolean visit(EnumDeclaration node){
		ITypeBinding typeBind = node.resolveBinding();
		String type = typeBind.getQualifiedName();

		addTypeToList(type);
		incDecCount(type);

		return true;
	}

	/**
		Field declaration nodes
		ADDED: Support distinction between Primitive Data types (Reference Only) and others (TBD).

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
		Status: WIP - Need to find out if FULL QUALIFIED NAME is doable
		TODO: Check if AnnotationTypeDeclaration is still needed
		TODO: Cannot recognize full qualified name 
			e.g. @Test from org.junit.Test only appears as Test
		TODO: Determine if declaration or reference
	*/
	public boolean visit(MarkerAnnotation node){

		IAnnotationBinding annBind = node.resolveAnnotationBinding();
		ITypeBinding typeBind = annBind.getAnnotationType();
		String type = typeBind.getQualifiedName();
		debug("", type);

		addTypeToList(type);
		// incDecCount(type);
		// incRefCount(type);
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
