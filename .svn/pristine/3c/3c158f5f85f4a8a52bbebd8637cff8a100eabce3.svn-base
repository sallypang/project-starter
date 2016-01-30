package test.parser;

import java.io.File;


import org.junit.Test;

import parser.Parser;

import ast.Program;
import junit.framework.Assert;
import util.SampleCode;


/**
 * The tests in this class correspond more or less to the work in Chapter 3.
 * <p>
 * These tests try to call your parser to parse Expression programs, but they do 
 * not check the AST produced by the parser. As such you should be able to get
 * these tests to pass without inserting any semantic actions into your
 * parser specification file.
 * <p>
 * The tests in this file are written in an order that can be followed if
 * you want to develop your parser incrementally, staring with the tests at the
 * top of the file, and working your way down.
 * 
 * @author kdvolder
 */
public class Test3Parse {
	
	/**
	 * All testing is supposed to go through calling this method to one of the
	 * accept methods, to see whether some input is accepted by the parser. The subclass 
	 * Test4Parse refines these tests by overriding this accept method to also verify the 
	 * parse tree structure.
	 */
	protected void accept(String input) throws Exception {
		System.out.println("parsing string: "+ input);
		Program p = Parser.parse(input);
		System.out.println("Parse tree:");
		System.out.println(p.dump());
	}
	
	protected void accept(File file) throws Exception {
		System.out.println("parsing file: "+file);
		Program p = Parser.parse(file);
		System.out.println("Parse tree:");
		System.out.println(p.dump());
	}
	
	protected void reject(String input) throws Exception {
		boolean good = false;
		try {
			System.out.println("parsing string: "+ input);
			Parser.parse(input);
			good = true;
		}
		catch(parser.jcc.ParseException e) {
			System.out.println("Expected parsing failure");
		}
		
		Assert.assertFalse(good);
	}
	
	//////////////////////////////////////////////////////////////////////////
	// First let's ensure we can parse the "simplest possible" program:

	@Test
	public void testSmallest() throws Exception {
		// The smallest program has one print statement with the smallest expression.
		accept( "print 1");
	}
	
	//////////////////////////////////////////////////////////////////////////
	// Next: let's work on making the expression parsing complete.
	
	// Note: some of the created test programs here are not correct. They use undeclared variables
	// and may have type errors... but they should pass by the parser. The parser doesn't
	// check for these kinds of errors.
	
	void acceptExpression(String exp) throws Exception {
		accept( "print " + exp);
	}
	
	void rejectExpression(String exp) throws Exception {
		reject("print " + exp);
	}
	
	@Test
	public void testIdentifier() throws Exception {
		acceptExpression("x");
		acceptExpression("y");
		acceptExpression("xy123");
		acceptExpression("x_y_123");
		acceptExpression("x_y_123");
	}
	
	@Test
	public void testThis() throws Exception {
		acceptExpression("this");
	}
	
	@Test
	public void testNot() throws Exception {
		acceptExpression("!x");
		acceptExpression("!!!!!!x");
	}

	@Test
	public void testParens() throws Exception {
		acceptExpression("(1)");
		acceptExpression("((((((1))))))");
	}

	@Test
	public void testMult() throws Exception {
		acceptExpression("10*9");
		acceptExpression("10*9*8");
		acceptExpression("foo*length");
		acceptExpression("10*9*8*7*x*y*foo");
	}
	
	@Test
	public void testAdd() throws Exception {
		acceptExpression("10+9");
		acceptExpression("10-9");
		acceptExpression("10+9+8");
		acceptExpression("10-9-8");
		acceptExpression("length+length");
		acceptExpression("length-length");
		acceptExpression("foo+foo");
		acceptExpression("foo+(foo)");
		acceptExpression("10+9+x*length-foo+array");
		acceptExpression("(a-b)*(a+b)");
	}

	@Test
	public void testComp() throws Exception {
		acceptExpression("10<9");
		acceptExpression("10+a*3<9-4+2");
		acceptExpression("length<1");
		acceptExpression("i<foo");
		acceptExpression("10<9");
		acceptExpression("10+a*3<9-4+2");
		acceptExpression("length<1");
		acceptExpression("i<foo");
	}
	
	@Test
	public void testConditional() throws Exception {
		acceptExpression("10<9?x:y");
		acceptExpression("10+a*3<9-4+2 ? 3 + 4 : 5 * 7");
		acceptExpression("1 ? 2 ? 3 ? 4 : 5 : 6 : 7");
		acceptExpression("1 ? 2 ? 3 : 4 ? 5 : 6 : 7 ? 8 : 9");
	}
	
	@Test
	public void testInvocation() throws Exception {
		acceptExpression("call()");		// no arguments
		acceptExpression("call(1)");	// single integer literal
		acceptExpression("call(id)");	// identifier
		acceptExpression("call(1, 2)");	// multi-argument
		acceptExpression("call(1, 2, 4)");
		acceptExpression("call (1)");
		acceptExpression("cal9 (1)");
		
		rejectExpression("(1, 2)");
		rejectExpression("9cal(1)");
		rejectExpression("call(1,2");
		rejectExpression("call1,2)");
		rejectExpression("1, 2");
		rejectExpression("call(1,2,)");
		rejectExpression("call(,1,2)");
		rejectExpression("call(,)");
		rejectExpression("call(");
		rejectExpression("call)");
	}

	/////////////////////////////////////////////////////////////////////////////////
	// Now let's work on making statement parsing complete.
	
	void acceptStatement(String statement) throws Exception {
		accept( statement + "\n" + "print 1");
	}
	
	void rejectStatement(String statement) throws Exception {
		reject( statement + "\n" + "print 1");
	}
	
	@Test 
	public void testAssign() throws Exception {
		acceptStatement("numbers = numbers + 1;");
		acceptStatement("foo = foo+1;");
	}
	
	@Test
	public void testDeclaration() throws Exception {
		acceptStatement("int add(int a, int b) { return a + b; }");
		acceptStatement("int add(int a, int b) {\nreturn a + b;\n}\n");
		acceptStatement("int add(int a) { return a; }");
		acceptStatement("int id(boolean a) { return a; }");
		acceptStatement("int add(int a, boolean b) { return a + b; }");
		acceptStatement("boolean add(int a, int b) { return a + b; }");
		acceptStatement("int add(int a, int b) { foo = foo+1; return a + b; }");
		acceptStatement("int fib(int n) { return (n < 2) ? 1 : (fib(n-1) + fib(n-2)); }");
		acceptStatement("int random(){ return 4; }");
		
		rejectStatement("foo a(){ return 1; }");
		rejectStatement("int a(, bool id){ return 1; }");
		rejectStatement("int 9a(){return 1;}");
		rejectStatement("a(){return 1;}");
		rejectStatement("int fun({return 1;}");
		rejectStatement("int fun){return 1;}");
		rejectStatement("int fun(){return 1;");
		rejectStatement("int fun()return 1;}");
		rejectStatement("int fun(int a)");
		rejectStatement("int fun(int a){}");
		rejectStatement("int fun(int a){ a = a + 5; }");
	}
	
	/////////////////////////////////////////////////////////////////////////////////
	// Finally, check whether the parser accepts all the sample code.
	@Test 
	public void testParseSampleCode() throws Exception {
		File[] files = SampleCode.sampleFiles("exp");
		for (File file : files) {
			accept(file);
		}
	}
	
}
