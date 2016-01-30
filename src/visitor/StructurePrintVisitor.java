package visitor;

import java.io.PrintWriter;

import ast.*;

import util.IndentingWriter;



/**
 * This prints the structure of an AST, showing its hierarchical relationships.
 * <p>
 * This version is also cleaned up to actually produce *properly* indented
 * output.
 * 
 * @author norm
 */
public class StructurePrintVisitor implements Visitor<Void> {

	/**
	 * Where to send out.print output.
	 */
	private IndentingWriter out;

	public StructurePrintVisitor(PrintWriter out) {
		this.out = new IndentingWriter(out);
	}

	///////////// Visitor methods /////////////////////////////////////////

	@Override
	public Void visit(Program n) {
		out.println("Program");
		out.indent();
		n.statements.accept(this);
		n.print.accept(this);
		out.outdent();
		return null;
	}

	@Override
	public Void visit(BooleanType n) {
		out.println("BooleanType");
		return null;
	}

	@Override
	public Void visit(IntegerType n) {
		out.println("IntegerType");
		return null;
	}

	@Override
	public Void visit(UnknownType n) {
		out.println("UnknownType");
		return null;
	}

	@Override
	public Void visit(Conditional n) {
		out.println("Conditional");
		out.indent();
		n.e1.accept(this);
		n.e2.accept(this);
		n.e3.accept(this);
		out.outdent();
		return null;
	}
	@Override
	public Void visit(Print n) {
		out.println("Print");
		out.indent();
		n.exp.accept(this);
		out.outdent();
		return null;
	}

	@Override
	public Void visit(Assign n) {
		out.println("Assign");
		out.indent();
		new IdentifierExp(n.name).accept(this);
		n.value.accept(this);
		out.outdent();
		return null;
	}

	@Override
	public Void visit(LessThan n) {
		out.println("LessThan");
		out.indent();
		n.e1.accept(this);
		n.e2.accept(this);
		out.outdent();
		return null;
	}

	@Override
	public Void visit(Plus n) {
		out.println("Plus");
		out.indent();
		n.e1.accept(this);
		n.e2.accept(this);
		out.outdent();
		return null;
	}

	@Override
	public Void visit(Minus n) {
		out.println("Minus");
		out.indent();
		n.e1.accept(this);
		n.e2.accept(this);
		out.outdent();
		return null;
	}

	@Override
	public Void visit(Times n) {
		out.println("Times");
		out.indent();
		n.e1.accept(this);
		n.e2.accept(this);
		out.outdent();
		return null;
	}

	@Override
	public Void visit(IntegerLiteral n) {
		out.println("IntegerLiteral "+n.value);
		return null;
	}

	@Override
	public Void visit(IdentifierExp n) {
		out.println("IdentifierExp " + n.name);
		return null;
	}

	@Override
	public Void visit(Not n) {
		out.println("Not");
		out.indent();
		n.e.accept(this);
		out.outdent();
		return null;
	}

	@Override
	public <T extends AST> Void visit(NodeList<T> nodes) {
		for (int i = 0; i < nodes.size(); i++) {
			nodes.elementAt(i).accept(this);
		}
		return null;
	}

	@Override
	public Void visit(FunctionDeclaration decl) {
		out.println("Function Declaration");
		out.indent();
		out.print("Returns: ");
		decl.returnType.accept(this);
		out.println("Identifier: " + decl.name);
		out.println("Parameters: ");
		out.indent();
		
		for(Parameter p : decl.parameters)
			out.println("Parameter: " + p.type + " " + p.name);
		
		out.outdent();
		out.println("Body:");
		out.indent();
		
		for(int i=0;i<decl.body.size();i++)
			decl.body.elementAt(i).accept(this);
		
		out.outdent();
		out.print("Return: ");
		decl.returnExpression.accept(this);
		
		out.outdent();
		return null;
	}

	@Override
	public Void visit(FunctionInvocation call) {
		out.print("Invoke: " + call.name);
		out.indent();
		for(Expression e : call.arguments)
			e.accept(this);
		out.outdent();
		return null;
	}

}
