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

	private static List<String> types;
	private static Map<String, Integer> decCounter;
	private static Map<String, Integer> refCounter;

	// constructor, initialize lists and maps
	public TypeVisitor(){
		types = new ArrayList<String>();
		decCounter = new HashMap<String, Integer>();
		refCounter = new HashMap<String, Integer>();
	}

	private static void addTypeToList(String type){
		if (!types.contains(type)){
			types.add(type);
			decCounter.put(type, 0);
			refCounter.put(type, 0);
		}
	}

	private static void incDecCount(String type){
		// redundant check
		if (decCounter.containsKey(type)){
			decCounter.put(type, decCounter.get(type)+1);
		}
	}

	private static void incRefCount(String type){
		// redundant check
		if (refCounter.containsKey(type)){
			refCounter.put(type, refCounter.get(type)+1);
		}
	}

	// Get all the declarations of Classes and Interfaces
	public boolean visit(TypeDeclaration node){
		ITypeBinding typeBind = node.resolveBinding();
		String type = typeBind.getQualifiedName();
		debug(node.getName().toString(), type);
		addTypeToList(type);
		incDecCount(type);
		return true;
	}

//	// get all the declaration of METHODS
//	public boolean visit(MethodDeclaration node){
//		ITypeBinding typeBind = node.resolveBinding();
//		String type = typeBind.getQualifiedName();
//
//		return true;
//	}

	// get argument variables
	public boolean visit(SingleVariableDeclaration node){
		IVariableBinding varBind = node.resolveBinding();
		ITypeBinding typeBind = varBind.getType();
		String type = typeBind.getQualifiedName();
		debug(node.getName().toString(), type);
		addTypeToList(type);
		incRefCount(type);
		return true;
	}

	public boolean visit(VariableDeclarationStatement node){
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

	// get fields
	public boolean visit (FieldDeclaration node){
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

	public Map getDecCount(){
		return decCounter;
	}

	public Map getRefCount(){
		return refCounter;
	}
}
