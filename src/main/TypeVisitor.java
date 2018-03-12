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
		ClassInstanceCreation; {expr} = new {type}
		Given how we're instantiating a new class, all of these will be considered as declarations.
		Counter: Declaration
	 */
	public boolean visit(ClassInstanceCreation node){
//		String type = node.getType().isQualifiedType();
		 ITypeBinding typeBind = node.getType().resolveBinding();
		// String type = typeBind.getPackage().getName();
		 String type = typeBind.getQualifiedName();
		// String type = typeBind.getBinaryName();

		/* Debug ONLY: Get the parent variable name if it exists */
		debug("ClassInstance" ,type);

		addTypeToList(type);
		incDecCount(type);

		return true;
	}

	/**
		EnumDeclaration; enum ID {...}
		Counter: Declaration
	 */
	public boolean visit(EnumDeclaration node){
		ITypeBinding typeBind = node.resolveBinding();
		String type = typeBind.getQualifiedName();

		debug("enum", type);

		addTypeToList(type);
		incDecCount(type);

		return true;
	}

	/**
		Field declaration nodes
		ADDED: Support distinction between Primitive Data types (Reference Only) and others (TBD).
			>> Revert: Disabled distinctions.
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
		incRefCount(type);
		// // check if node is of primitive type
		// if (node.getType().isPrimitiveType()){
		// 	debug(name + " is PRIMITIVE TYPE");
		// 	// increase reference count
		// 	incRefCount(type);
		// } else {
		// 	// otherwise, increase reference count
		// 	incRefCount(type);
		// }
		return true;
	}

	/**
		Marker annotation nodes: @TypeName
		TODO: Cannot recognize full qualified name; ONLY from imports, works for java.lang.*
			e.g. @Test from org.junit.Test only appears as package.Test
	*/
	public boolean visit(MarkerAnnotation node){
		IAnnotationBinding annBind = node.resolveAnnotationBinding();
		ITypeBinding typeBind = annBind.getAnnotationType();
		String type = typeBind.getQualifiedName();
		debug("Annotation", type);

		addTypeToList(type);
		incRefCount(type);
		return true;
	}

	public boolean visit(MethodDeclaration node){
		ITypeBinding typeBind = node.getReturnType2().resolveBinding();
		String type = typeBind.getQualifiedName();
		
		if (!type.equals("void")){
			debug(node.getName().toString(), type);
			addTypeToList(type);
			incRefCount(type);
		}

		return true;

	}

	/**
		SingleVariableDeclaration; formal parameter lists, and catch clause variables
		ADDED: Distinction between Primitive type and others
			>> Revert: Disabled distinctions.
		TODO: Confirm Non-primitive type COUNTER
	*/
	public boolean visit(SingleVariableDeclaration node){
		IVariableBinding varBind = node.resolveBinding();
		ITypeBinding typeBind = varBind.getType();
		String type = typeBind.getQualifiedName();
		debug(node.getName().toString(), type);

		// Add current node's type to the list of types
		addTypeToList(type);
		incRefCount(type);
		
/* 		// check if node is of primitive type
		if (node.getType().isPrimitiveType()){
			debug(node.getName().toString() + " is PRIMITIVE TYPE");
			// increase reference count
			incRefCount(type);
		} else {
			// otherwise, increase declaration count
			incDecCount(type);
		} */

		return true;
	}

	/**
		TypeDeclaration; union of class and interface declaration nodes
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
			>> Revert: Disabled distinction
		Note: For any imported packages, you must include the package qualified name in the code itself
			  in order for this parser to bind it as the type
		TODO: Confirm Non-primitive type only count as REFERENCES
	 */
	public boolean visit(VariableDeclarationStatement node){
		Object o = node.fragments().get(0);
		if (o instanceof VariableDeclarationFragment){
			// get the variable name, debug only
			String name = ((VariableDeclarationFragment) o).getName().toString();
			// get the type binding of the variable
			ITypeBinding typeBind = ((VariableDeclarationFragment) o).resolveBinding().getType();
			String type = typeBind.getQualifiedName();

			debug(name, type);
			addTypeToList(type);
			incRefCount(type);
		}

		// Add current node's type to the list of types
		// check if node is of primitive type
		/* if (node.getType().isPrimitiveType()){
			debug(name + " is PRIMITIVE TYPE");
			// increase reference count
			incRefCount(type);
		} else {
			// otherwise, increase declaration count
			incDecCount(type);
		} */

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

		// public boolean visit(MethodInvocation node){
	// 	IMethodBinding methBind = node.resolveMethodBinding();

	// 	if (methBind != null){
	// 	ITypeBinding typeBind = methBind.getDeclaringClass();
	// 	String type = typeBind.getQualifiedName();
	// 	System.out.println("u fucked");
	// 	System.out.println(type);
	// 	// System.out.println(methBind.getKey());
 	// 	} else {
	// 		 System.out.println("null");
	// 	 }
	// 	return true;
	// }