package main;

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
 */
public class TypeVisitor extends ASTVisitor {

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
	}

	/**
	 * Purpose: Increments declarationCount or referenceCount if the node is of the
	 * desired type.
	 * 
	 * Perhaps this is the method we want to use to increment the
	 * declaration/reference count since its overriding a more general type
	 * (ASTNode). Is this the right node type? Is there something more general that
	 * would work?
	 */
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
		}
	}

	/**
	 * 
	 * @return the number of declarations of input type
	 */
	public BigInteger getDeclarationCount() {
		return declarationCount.getBigInteger();
	}

	/**
	 * 
	 * @return the number of reference of input type
	 */
	public BigInteger getReferenceCount() {
		return referenceCount.getBigInteger();
	}

}
