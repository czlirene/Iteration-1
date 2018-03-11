package main;

import java.util.*;

import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

public class TypeVisitor extends ASTVisitor {
	private boolean DEBUG = true;

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
		Field declaration nodes
		Status: DONE
		TODO: Confirm they are only REFERENCES
	*/
	public boolean visit(FieldDeclaration node){
		ITypeBinding typeBind = node.getType().resolveBinding();
		String type = typeBind.getQualifiedName();

		/* For debug purpose */
		Object o = node.fragments().get(0);
		if (o instanceof VariableDeclarationFragment){
			String name = ((VariableDeclarationFragment) o).getName().toString();
			debug(name, type);
		}
		addTypeToList(type);
		incRefCount(type);

		return true;
	}

	/**
		Marker annotation nodes: @TypeName
		Status: WIP - Need to find out if FULL QUALIFIED NAME is doable
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
		Status: DONE
		TODO: Confirm they are only REFERENCE
	*/
	public boolean visit(SingleVariableDeclaration node){
		IVariableBinding varBind = node.resolveBinding();
		ITypeBinding typeBind = varBind.getType();
		String type = typeBind.getQualifiedName();
		debug(node.getName().toString(), type);

		addTypeToList(type);
		incRefCount(type);

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
		Status: DONE
		TODO: Confirm they only count as REFERENCES
	 */
	public boolean visit(VariableDeclarationStatement node){
		ITypeBinding typeBind = node.getType().resolveBinding();
		String type = typeBind.getQualifiedName();
		
		// debugging purpose, get the actual variable name
		// print out (varName, varType)
		Object o = node.fragments().get(0);
		if (o instanceof VariableDeclarationFragment){
			String name = ((VariableDeclarationFragment) o).getName().toString();
			debug(name, type);
		}

		addTypeToList(type);
		incRefCount(type);

		return true;
	}

	public Map getDecCount(){
		return decCounter;
	}

	public Map getRefCount(){
		return refCounter;
	}
}
