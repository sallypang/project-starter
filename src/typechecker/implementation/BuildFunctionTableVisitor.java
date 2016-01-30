package typechecker.implementation;

import ast.AST;
import ast.Assign;
import ast.BooleanType;
import ast.Conditional;
import ast.FunctionDeclaration;
import ast.FunctionInvocation;
import ast.IdentifierExp;
import ast.IntegerLiteral;
import ast.IntegerType;
import ast.LessThan;
import ast.Minus;
import ast.NodeList;
import ast.Not;
import ast.Plus;
import ast.Print;
import ast.Program;
import ast.Times;
import ast.UnknownType;
import typechecker.ErrorReport;
import util.FunTable;
import visitor.Visitor;

public class BuildFunctionTableVisitor implements Visitor<FunTable<FunctionDeclaration>> {

	private FunTable<FunctionDeclaration> declarations;
	private ErrorReport errors;
	
	public BuildFunctionTableVisitor(ErrorReport errors){
		this.errors = errors;
		declarations = FunTable.<FunctionDeclaration>theEmpty();
	}
	
	@Override
	public <T extends AST> FunTable<FunctionDeclaration> visit(NodeList<T> ns) {
		for (int i = 0; i < ns.size(); i++)
			ns.elementAt(i).accept(this);
		return null;
	}

	@Override
	public FunTable<FunctionDeclaration> visit(Program n) {
		n.statements.accept(this);
		n.print.accept(this);
		return declarations;
	}

	@Override
	public FunTable<FunctionDeclaration> visit(Print n) {
		n.exp.accept(this);
		return null;
	}

	@Override
	public FunTable<FunctionDeclaration> visit(Assign n) {
		n.value.accept(this);
		return null;
	}

	@Override
	public FunTable<FunctionDeclaration> visit(FunctionDeclaration decl) {		
		// if this is a valid declaration, look at body to check everything is defined
		if(add(decl)) {
			decl.body.accept(this);
			decl.returnExpression.accept(this);
		}
		return null;
	}

	public boolean add(FunctionDeclaration decl) {
		if(declarations.lookup(decl.name) != null) {
			errors.duplicateDefinition(decl.name);
			return false;
		}
		
		declarations.merge(declarations.insert(decl.name, decl));	
		return true;
	}
	
	@Override
	public FunTable<FunctionDeclaration> visit(LessThan n) {
		n.e1.accept(this);
		n.e2.accept(this);
		return null;
	}

	@Override
	public FunTable<FunctionDeclaration> visit(Conditional n) {
		n.e1.accept(this);
		n.e2.accept(this);
		n.e3.accept(this);
		return null;
	}

	@Override
	public FunTable<FunctionDeclaration> visit(Plus n) {
		n.e1.accept(this);
		n.e2.accept(this);
		return null;
	}

	@Override
	public FunTable<FunctionDeclaration> visit(Minus n) {
		n.e1.accept(this);
		n.e2.accept(this);
		return null;
	}

	@Override
	public FunTable<FunctionDeclaration> visit(Times n) {
		n.e1.accept(this);
		n.e2.accept(this);
		return null;
	}

	@Override
	public FunTable<FunctionDeclaration> visit(IntegerLiteral n) {
		return null;
	}

	@Override
	public FunTable<FunctionDeclaration> visit(IdentifierExp n) {
		return null;
	}

	@Override
	public FunTable<FunctionDeclaration> visit(Not not) {
		not.e.accept(this);
		return null;
	}

	@Override
	public FunTable<FunctionDeclaration> visit(IntegerType n) {
		return null;
	}

	@Override
	public FunTable<FunctionDeclaration> visit(BooleanType n) {
		return null;
	}

	@Override
	public FunTable<FunctionDeclaration> visit(UnknownType n) {
		return null;
	}

	@Override
	public FunTable<FunctionDeclaration> visit(FunctionInvocation call) {
		if(declarations.lookup(call.name) == null)
			errors.undefinedId(call.name);
		return null;
	}

}
