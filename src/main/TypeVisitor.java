package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.IAnnotationBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

/**
 * TypeVisitor.java
 *
 * A visitor for abstract syntax trees. For each different concrete AST node
 * type T, the visitor will locate the different java types present in the
 * source code, and count the number of declarations of references for each of
 * the java types present.
 *
 * @author Sze Lok Irene Chan
 * @version 2.7 +Fixed VariableDeclarationStatement; They are all considered
 *          references now
 * 
 * @TODO: Parametized Type: Increment references
 * @since 12 March 2018
 */
public class TypeVisitor extends ASTVisitor {

	/**
	 * Debug methods TODO: Remove these before submission
	 */
	private boolean DEBUG = true;

	private void debug(String msg) {
		if (DEBUG) {
			System.out.println("DEBUG >> " + msg);
		}
	}

	private void debug(String var, String type) {
		if (DEBUG) {
			System.out.println("DEBUG >> " + var + " : " + type);
		}
	}

	// Global variables
	private static ArrayList<String> types;
	private static HashMap<String, Integer> decCounter;
	private static HashMap<String, Integer> refCounter;

	/**
	 * constructor Intialize the list of types, and the HashMaps for the counters to
	 * null.
	 */
	public TypeVisitor() {
		// initialize list and counters to null
		types = new ArrayList<String>();
		decCounter = new HashMap<String, Integer>();
		refCounter = new HashMap<String, Integer>();
	}

	/*
	 * ============================== HELPER FUNCTIONS
	 * ==============================
	 */

	/**
	 * Checks if the passed type already exists within the types list. [false -> add
	 * type to list create entry <type, 0> in decCounter create entry <type, 0> in
	 * refCounter] [true -> do nothing]
	 *
	 * @param type
	 *            : String, java type
	 */
	private static void addTypeToList(String type) {
		if (!types.contains(type)) {
			types.add(type);
			decCounter.put(type, 0);
			refCounter.put(type, 0);
		}
	}

	/**
	 * Increment the counter value for a given type in decCounter.
	 *
	 * @param type:
	 *            String, java type
	 */
	private static void incDecCount(String type) {
		// Check if the type exists, then increment their associated value by 1
		if (decCounter.containsKey(type)) {
			decCounter.put(type, decCounter.get(type) + 1);
		}
	}

	/**
	 * Increment the counter value for a given type in refCounter.
	 *
	 * @param type
	 *            : String, java type
	 */
	private static void incRefCount(String type) {
		// Check if the type exists, then increment their associated value by 1
		if (refCounter.containsKey(type)) {
			refCounter.put(type, refCounter.get(type) + 1);
		}
	}

	/**
	 * Accessor method. Fetches the map of declaration counter.
	 *
	 * @return HashMap : decCounter
	 */
	public Map<String, Integer> getDecCount() {
		return decCounter;
	}

	/**
	 * Accessor method. Fetches the map of reference counter.
	 *
	 * @return HashMap : refCounter
	 */
	public Map<String, Integer> getRefCount() {
		return refCounter;
	}

	/**
	 * Accessor method. Fetches the list of types.
	 *
	 * @return ArrayList<String> : types
	 */
	public ArrayList<String> getList() {
		return types;
	}

	/*
	 * ============================== ASTVisitor FUNCTIONS
	 * ==============================
	 */
	/**
	 * Visits a Class instance creation expression AST node type. Determine the type
	 * of the Class instance being created, add it to types, and increment its
	 * type's counter value in refCounter.
	 * 
	 * CounterType: REFERENCE
	 * 
	 * @param node
	 *            : ClassInstanceCreation
	 * @return boolean : True to visit the children of this node
	 */
	@Override
	public boolean visit(ClassInstanceCreation node) {
		ITypeBinding typeBind = node.getType().resolveBinding();
		String type = typeBind.getQualifiedName();

		/* Debug ONLY: Get the parent variable name if it exists */
		debug("ClassInstanceCreation", type);

		addTypeToList(type);
		incRefCount(type);

		return true;
	}

	/**
	 * Visits a Enum declaration AST node type. Determine the type of the Enum
	 * identifier, add it to types, and increment its type's counter value in
	 * decCounter.
	 *
	 * CounterType: DECLARATION
	 *
	 * @param node
	 *            : EnumDeclaration
	 * @return boolean : True to visit the children of this node
	 */
	@Override
	public boolean visit(EnumDeclaration node) {
		ITypeBinding typeBind = node.resolveBinding();
		String type = typeBind.getQualifiedName();

		debug("Enum", type);

		addTypeToList(type);
		incDecCount(type);

		return true;
	}

	/**
	 * Visits a Field declaration node type. This type of node collects MULTIPLE
	 * VARIABLE DECL FRAGMENT into a single body declaration. They all share the
	 * same base type.
	 *
	 * Determine the type of the Field identifier, add it to types, and increment
	 * its type's counter value in refCounter based on the number of fragments.
	 *
	 * CounterType: REFERENCE
	 *
	 * @param node
	 *            : FieldDeclaration
	 * @return boolean : True to visit the children of this node
	 */
	@Override
	public boolean visit(FieldDeclaration node) {
		boolean isParameterized = node.getType().isParameterizedType();

		if (isParameterized) {
			ITypeBinding typeBind = node.getType().resolveBinding().getTypeDeclaration();
			String type = typeBind.getQualifiedName();

			addTypeToList(type);
			incRefCount(type);

			for (Object fragment : node.fragments()) {
				if (fragment instanceof VariableDeclarationFragment) {
					// debug only: get the name of the variable
					String name = ((VariableDeclarationFragment) fragment).getName().toString();
					debug(name, type);
				}
			}

			// inc count for all the arguments
			for (ITypeBinding paramBind : node.getType().resolveBinding().getTypeArguments()) {
				String paramType = paramBind.getQualifiedName();
				debug("param", paramType);
				addTypeToList(paramType);
				incRefCount(paramType);
			}
		} else {
			ITypeBinding typeBind = node.getType().resolveBinding();
			String type = typeBind.getQualifiedName();

			addTypeToList(type);

			// iterate through all the fragments, and increment the type counter
			for (Object fragment : node.fragments()) {
				if (fragment instanceof VariableDeclarationFragment) {
					// debug only: get the name of the variable
					String name = ((VariableDeclarationFragment) fragment).getName().toString();
					debug(name, type);
					incRefCount(type);
				}
			}
		}
		return true;
	}

	/**
	 * Visits a Marker annotation node type. Marker annotation "@<typeName>" is
	 * equivalent to normal annotation "@<typeName>()"
	 *
	 * Determine the type of annotation, add it to types, and increment its type's
	 * counter value in refCounter.
	 *
	 * CounterType: REFERENCE
	 *
	 * @param node
	 *            : MarkerAnnotation
	 * @return boolean : True to visit the children of this node
	 *
	 *         TODO: Cannot recognize full qualified names for IMPORTS. Works for
	 *         java.lang.* e.g. @Test from org.junit.Test appears as
	 *         <currentPackage>.Test
	 */
	@Override
	public boolean visit(MarkerAnnotation node) {
		IAnnotationBinding annBind = node.resolveAnnotationBinding();
		ITypeBinding typeBind = annBind.getAnnotationType();
		String type = typeBind.getQualifiedName();

		debug("Annotation", type);

		addTypeToList(type);
		incRefCount(type);
		return true;
	}

	/**
	 * Visits a Method declaration node type. Method declaration is a union of
	 * method declaration and constructor declaration. (void is not a type, any void
	 * methods will be ignored)
	 *
	 * Determine if the method is a constructor. [true -> true] [false -> get return
	 * type of method add type to types increment reference count return true]
	 *
	 * CounterType: REFERENCE
	 * 
	 * TODO: Get parameters
	 * 
	 * @param node
	 *            : MethodDeclaration
	 * @return boolean : True to visit the children of this node
	 */
	@Override
	public boolean visit(MethodDeclaration node) {
		boolean isConstructor = node.isConstructor();

		if (!isConstructor) {
			ITypeBinding typeBind = node.getReturnType2().resolveBinding();
			String type = typeBind.getQualifiedName();

			// ignore all void methods
			if (!type.equals("void")) {
				// debug only: print the method name, and its type
				debug(node.getName().toString(), type);

				addTypeToList(type);
				incRefCount(type);
			}
		}

		return true;
	}

	/**
	 * Visits a single variable declaration node type. These are used only in formal
	 * parameter lists, and catch clauses. They are not used for field declarations,
	 * and regular variable declaration statements
	 *
	 * Determine the type of variable, add it to types, and increment the counter
	 * value associated to the type in refCounter.
	 *
	 * CounterType: REFERENCE
	 *
	 * @param node
	 *            : SingleVariableDeclaration
	 * @return boolean : True to visit the children of this node
	 */
	@Override
	public boolean visit(SingleVariableDeclaration node) {
		boolean isParameterized = node.getType().isParameterizedType();

		// get parameterized variables
		if (isParameterized) {
			ITypeBinding typeBind = node.getType().resolveBinding().getTypeDeclaration();
			String type = typeBind.getQualifiedName();

			addTypeToList(type);
			incRefCount(type);

			for (Object fragment : node.fragments()) {
				if (fragment instanceof VariableDeclarationFragment) {
					// debug only: get the name of the variable
					String name = ((VariableDeclarationFragment) fragment).getName().toString();
					debug(name, type);
				}
			}

			// inc count for all the arguments
			for (ITypeBinding paramBind : node.getType().resolveBinding().getTypeArguments()) {
				String paramType = paramBind.getQualifiedName();
				debug("param", paramType);
				addTypeToList(paramType);
				incRefCount(paramType);
			}

		} else {
			IVariableBinding varBind = node.resolveBinding();
			ITypeBinding typeBind = varBind.getType();
			String type = typeBind.getQualifiedName();

			// debug only: print variable name, and its type
			debug(node.getName().toString(), type);

			addTypeToList(type);
			incRefCount(type);
		}

		return true;
	}

	/**
	 * Visits a type declaration node type. Type declaration node is the union of
	 * class declaration, and interface declaration.
	 *
	 * Determine the type of class, add it to types, and increment the declaration
	 * counter associated to the type.
	 *
	 * CounterType: DECLARATION
	 *
	 * @param node
	 *            : TypeDeclaration
	 * @return boolean : True to visit the children of this node
	 */
	@Override
	public boolean visit(TypeDeclaration node) {
		ITypeBinding typeBind = node.resolveBinding();
		String type = typeBind.getQualifiedName();

		debug(node.getName().toString(), type);

		addTypeToList(type);
		incDecCount(type);

		return true;
	}

	/**
	 * Visits a local variable declaration statement node type. This type of node
	 * contains several variable declaration fragments into a statement. They all
	 * have the same base type and modifier.
	 *
	 * Determine the type of variable, add it to types, and increment the
	 * declaration counter associated to the type depending on the number of
	 * fragments.
	 *
	 * Note: For any imported packages methods/classes, you must include the full
	 * qualified name in the code itself in order for this parser to bind it as the
	 * type
	 *
	 * CounterType: REFERENCE
	 *
	 * @param node
	 *            : VariableDeclarationStatement
	 * @return boolean : True to visit the children of this node
	 */
	@Override
	public boolean visit(VariableDeclarationStatement node) {
		boolean isParameterized = node.getType().isParameterizedType();

		// get parameterized variables
		if (isParameterized) {
			ITypeBinding typeBind = node.getType().resolveBinding().getTypeDeclaration();
			String type = typeBind.getQualifiedName();

			addTypeToList(type);
			incRefCount(type);

			for (Object fragment : node.fragments()) {
				if (fragment instanceof VariableDeclarationFragment) {
					// debug only: get the name of the variable
					String name = ((VariableDeclarationFragment) fragment).getName().toString();
					debug(name, type);
				}
			}

			// inc count for all the arguments
			for (ITypeBinding paramBind : node.getType().resolveBinding().getTypeArguments()) {
				String paramType = paramBind.getQualifiedName();
				debug("param", paramType);
				addTypeToList(paramType);
				incRefCount(paramType);
			}

		} else {
			// iterate through all the fragments, and increment the type counter
			for (Object fragment : node.fragments()) {
				if (fragment instanceof VariableDeclarationFragment) {
					// debug only: get the name of the variable
					String name = ((VariableDeclarationFragment) fragment).getName().toString();
					ITypeBinding typeBind = ((VariableDeclarationFragment) fragment).resolveBinding().getType();

					boolean isDeclaration = ((VariableDeclarationFragment) fragment).getName().isDeclaration();
					String type = typeBind.getQualifiedName();

					addTypeToList(type);
					debug(name, type);
					incRefCount(type);
				}
			}

		}

		return true;
	}

}

/* TODO: find out if we still need this method */
// public boolean visit(VariableDeclarationFragment node){
// IVariableBinding varBind = node.resolveBinding();
// ITypeBinding typeBind = varBind.getType();
// String type = typeBind.getQualifiedName();
// String name = node.getName().toString();
// debug(name, type);
// ChildPropertyDescriptor initializer = node.getInitializerProperty();

// if (initializer != null){
// System.out.println(initializer.toString());
// }
// return true;
// }

/**
 * Assignment expression nodes. Status: WIP TODO: Check if this is only
 * reference
 */
// public boolean visit(ExpressionStatement node){
// ITypeBinding typeBind = node.resolveTypeBinding();
// if (typeBind == null){
// debug("fuck me", "");
// } else {
// String type = typeBind.getQualifiedName();
// debug("", type);
// addTypeToList(type);

// }

// return true;
// }

// public boolean visit(MethodInvocation node){
// IMethodBinding methBind = node.resolveMethodBinding();

// if (methBind != null){
// ITypeBinding typeBind = methBind.getDeclaringClass();
// String type = typeBind.getQualifiedName();
// System.out.println("u fucked");
// System.out.println(type);
// // System.out.println(methBind.getKey());
// } else {
// System.out.println("null");
// }
// return true;
// }
