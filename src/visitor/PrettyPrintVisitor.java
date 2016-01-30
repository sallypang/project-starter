package visitor;

import java.io.PrintWriter;
import java.util.List;

import ast.AST;
import ast.Assign;
import ast.BooleanType;
import ast.Conditional;
import ast.FunctionDeclaration;
import ast.IdentifierExp;
import ast.IntegerLiteral;
import ast.IntegerType;
import ast.LessThan;
import ast.Minus;
import ast.NodeList;
import ast.Not;
import ast.Param;
import ast.Plus;
import ast.Print;
import ast.Program;
import ast.Times;
import ast.UnknownType;
import util.IndentingWriter;



/**
 * This is an adaptation of the PrettyPrintVisitor from the textbook
 * online material, but updated to work with the "modernized" 
 * Visitor and our own versions of the AST classes.
 * <p>
 * This version is also cleaned up to actually produce *properly* indented
 * output.
 * 
 * @author kdvolder
 */
public class PrettyPrintVisitor implements Visitor<Void> {

	/**
	 * Where to send out.print output.
	 */
	private IndentingWriter out;
	
	public PrettyPrintVisitor(PrintWriter out) {
		this.out = new IndentingWriter(out);
	}
	
	///////////// Visitor methods /////////////////////////////////////////

	@Override
	public Void visit(Program n) {
		n.statements.accept(this);
		n.print.accept(this);
		return null;
	}

	@Override
	public Void visit(BooleanType n) {
		out.print("boolean");
		return null;
	}

	@Override
	public Void visit(UnknownType n) {
		out.print("unknown");
		return null;
	}

	@Override
	public Void visit(IntegerType n) {
		out.print("int");
		return null;
	}

  	@Override
	public Void visit(Conditional n) {
		out.print("( ");
		n.e1.accept(this);
		out.print(" ? ");
		n.e2.accept(this);
		out.print(" : ");
		n.e3.accept(this);
		out.print(" )");
		return null;
	}

	@Override
	public Void visit(Print n) {
		out.print("print ");
		n.exp.accept(this);
		out.println();
		return null;
	}

	@Override
	public Void visit(Assign n) {
		out.print(n.name + " = ");
		n.value.accept(this);
		out.println(";");
		return null;
	}

	@Override
	public Void visit(LessThan n) {
		out.print("(");
		n.e1.accept(this);
		out.print(" < ");
		n.e2.accept(this);
		out.print(")");
		return null;
	}

	@Override
	public Void visit(Plus n) {
		out.print("(");
		n.e1.accept(this);
		out.print(" + ");
		n.e2.accept(this);
		out.print(")");
		return null;
	}

	@Override
	public Void visit(Minus n) {
		out.print("(");
		n.e1.accept(this);
		out.print(" - ");
		n.e2.accept(this);
		out.print(")");
		return null;
	}

	@Override
	public Void visit(Times n) {
		out.print("(");
		n.e1.accept(this);
		out.print(" * ");
		n.e2.accept(this);
		out.print(")");
		return null;
	}

	@Override
	public Void visit(IntegerLiteral n) {
		out.print(""+n.value);
		return null;
	}

	@Override
	public Void visit(IdentifierExp n) {
		out.print(n.name);
		return null;
	}

	@Override
	public Void visit(Not n) {
		out.print("!");
		n.e.accept(this);
		return null;
	}
	
	@Override
	public Void visit(List<Param> n) {
		StringBuilder builder = new StringBuilder();
		for (Param elem : n) {
			if (builder.length() != 0) {
		        builder.append(",");
		    }
		    builder.append(elem.type + " " + elem.name);			
		}
		out.print(builder);
		return null;
	}
	
	@Override
	public Void visit(FunctionDeclaration n) {
		out.println("FunctionDeclaration");
		out.indent();
		n.type.accept(this);
		out.print(n.name);
		out.print("(");
		
		visit(n.formal);
		
		out.print(")");
		out.print("{");
		n.assign.accept(this);
		out.print("return");
		n.ret.accept(this);
		return null;
	}

	@Override
	public <T extends AST> Void visit(NodeList<T> nodes) {
		for (int i = 0; i < nodes.size(); i++) {
			nodes.elementAt(i).accept(this);
		}
		return null;
	}
}
