package main;

import java.util.*;

import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

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
 * @since March 8, 2018
 *
 */

public class TypeVisitor extends ASTVisitor {
	private boolean DEBUG = true;

	private void debug(String msg){
		if (DEBUG) {
			System.out.println("DEBUG >> " + msg);
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
		addTypeToList(type);
	// TODO: Figure out of this is a declaration or a reference
		return true;
	}

	public boolean visit(VariableDeclarationStatement node){
		ITypeBinding typeBind = node.getType().resolveBinding();
		String type = typeBind.getQualifiedName();
		Object o = node.fragments().get(0);
		if (o instanceof VariableDeclarationFragment){
			String s = ((VariableDeclarationFragment) o).getName().toString();
			System.out.println(s + " " + type);
		}
		// addTypeToList(type);
		return true;
	}


	public Map getDecCount(){
		return decCounter;
	}

}
