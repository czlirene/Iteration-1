package main.FUCK;
import java.util.*;
import java.nio.file.NotDirectoryException;
import org.junit.*;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;

import main.JavaFileReader;
public class Test {
	
	/**
	 * MoreTest
	 * {@link MoreTest }
	 * @author slchan
	 *
	 */
	@interface ClassPreamble {
		   String author();
		   String date();
		   int currentRevision() default 1;
		   String lastModified() default "N/A";
		   String lastModifiedBy() default "N/A";
		   // Note use of array
		   String[] reviewers();
		}
	
	@ClassPreamble(author = "", date = "", reviewers = { "" })
	public static void main () {}
	
	@Deprecated
	public static void main2 () {}

//	public static int a, b, c;
//
//    public static String str = "package javaproject;" // package for all classes
//            + "class Dummy {" //
//            + "   void testSearch(String queryStr, String dateStr, SearchResources twitter1) {" //
//            + "      Query query = new Query(queryStr).until(dateStr);" //
//            + "      QueryResult queryResult = twitter1.search(query);" //
//            + "   }" //
//            + "}";

//	public class Foo2{};
//	public List<Boolean> a;A	
//    public void main(HashMap<Character, Character> args) {
//    	Foo<String,Integer> too = new Foo<String,Integer>();
//        ASTParser parser = ASTParser.newParser(AST.JLS4);
//        parser.setSource(str.toCharArray());
//        parser.setKind(ASTParser.K_COMPILATION_UNIT);
//        parser.setResolveBindings(true);
//
//        parser.setEnvironment( // apply classpath
//                new String[] { "\\" }, //
//                null, null, true);
//        parser.setUnitName("any_name");
//
//        final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
//
//        cu.accept(new ASTVisitor() {
//            public boolean visit(MethodDeclaration node) {
//                if (node.getName().getIdentifier().equals("testSearch")) {
//                    Block block = node.getBody();
//                    block.accept(new ASTVisitor() {
//                        public boolean visit(MethodInvocation node) {
//                            System.out.println("Name: " + node.getName());
//
//                            Expression expression = node.getExpression();
//                            if (expression != null) {
//                                System.out.println("Expr: " + expression.toString());
//                                ITypeBinding typeBinding = expression.resolveTypeBinding();
//                                if (typeBinding != null) {
//                                    System.out.println("Type: " + typeBinding.getName());
//                                }
//                            }
//                            IMethodBinding binding = node.resolveMethodBinding();
//                            if (binding != null) {
//                                ITypeBinding type = binding.getDeclaringClass();
//                                if (type != null) {
//                                    System.out.println("Decl: " + type.getName());
//                                }
//                            }
//
//                            return true;
//                        }
//                    });
//                }
//                return true;
//            }
//        });
//    	String s;
    }