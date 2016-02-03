package typechecker.implementation;

import ast.AST;
import ast.Assign;
import ast.BooleanType;
import ast.Conditional;
import ast.Expression;
import ast.FunctionDeclaration;
import ast.FunctionInvocation;
import ast.IdentifierExp;
import ast.IntegerLiteral;
import ast.IntegerType;
import ast.LessThan;
import ast.Minus;
import ast.NodeList;
import ast.Not;
import ast.Parameter;
import ast.Plus;
import ast.Print;
import ast.Program;
import ast.Times;
import ast.Type;
import ast.UnknownType;
import typechecker.ErrorReport;
import util.ImpTable;
import util.ImpTable.DuplicateException;
import visitor.DefaultVisitor;

/**
 * This visitor implements Phase 1 of the TypeChecker. It constructs the symboltable.
 * 
 * @author norm
 */
public class BuildSymbolTableVisitor extends DefaultVisitor<ImpTable<Type>> {
	
	private final ImpTable<Type> variables;
	private final ErrorReport errors;
	
	public BuildSymbolTableVisitor(ErrorReport errors) {
		variables = new ImpTable<Type>();
		this.errors = errors;
	}
	
	public BuildSymbolTableVisitor(ImpTable<Type> types, ErrorReport errors) {
		variables = types;
		this.errors = errors;
	}

	/////////////////// Phase 1 ///////////////////////////////////////////////////////
	// In our implementation, Phase 1 builds up a single symbol table containing all the
	// identifiers defined in an Expression program. 
	//
	// We also check for duplicate identifier definitions 

	@Override
	public ImpTable<Type> visit(Program n) {
		n.statements.accept(this);
		n.print.accept(this); // process all the "normal" classes.
		return variables;
	}
	
	@Override
	public <T extends AST> ImpTable<Type> visit(NodeList<T> ns) {
		for (int i = 0; i < ns.size(); i++)
			ns.elementAt(i).accept(this);
		return null;
	}

	@Override
	public ImpTable<Type> visit(Assign n) {
		n.value.accept(this);
		def(variables, n.name, new UnknownType());
		return null;
	}
	

	@Override
	public ImpTable<Type> visit(IdentifierExp n) {
		if (variables.lookup(n.name) == null)
			errors.undefinedId(n.name);
		return null;
	}
	
	@Override
	public ImpTable<Type> visit(BooleanType n) {
		return null;
	}

	@Override
	public ImpTable<Type> visit(IntegerType n) {
		return null;
	}

	@Override
	public ImpTable<Type> visit(Print n) {
		n.exp.accept(this);
		return null;
	}

	@Override
	public ImpTable<Type> visit(LessThan n) {
		n.e1.accept(this);
		n.e2.accept(this);
		return null;
	}

	@Override
	public ImpTable<Type> visit(Conditional n) {
		n.e1.accept(this);
		n.e2.accept(this);
		n.e3.accept(this);
		return null;
	}
	
	@Override
	public ImpTable<Type> visit(Plus n) {
		n.e1.accept(this);
		n.e2.accept(this);
		return null;
	}

	@Override
	public ImpTable<Type> visit(Minus n) {
		n.e1.accept(this);
		n.e2.accept(this);
		return null;
	}

	@Override
	public ImpTable<Type> visit(Times n) {
		n.e1.accept(this);
		n.e2.accept(this);
		return null;
	}

	@Override
	public ImpTable<Type> visit(IntegerLiteral n) {
		return null;
	}

	@Override
	public ImpTable<Type> visit(Not not) {
		not.e.accept(this);
		return null;
	}

	@Override
	public ImpTable<Type> visit(UnknownType n) {
		return null;
	}

	@Override
	// although this doesn't modify the variables list after executing, it 
	// ensures that the symbols in the body of the function can be resolved 
	public ImpTable<Type> visit(FunctionDeclaration decl) {
		ImpTable<Type> scope = new ImpTable<Type>();
		
		for(Parameter p : decl.parameters) {
			try {
				scope.put(p.name, p.type);
			}
			catch(DuplicateException ex) {
				errors.duplicateDefinition(p.name);
			}
		}
		
		BuildSymbolTableVisitor visitor = new BuildSymbolTableVisitor(scope, errors);

		decl.body.accept(visitor);
		decl.returnExpression.accept(visitor);
		
		return null;
	}
	
	@Override
	public ImpTable<Type> visit(FunctionInvocation call) {
		for(Expression e : call.arguments)
			e.accept(this);
		return null;
	}
	
	///////////////////// Helpers ///////////////////////////////////////////////
	
	/**
	 * Add an entry to a table, and check whether the name already existed.
	 * If the name already existed before, the new definition is ignored and
	 * an error is sent to the error report.
	 */
	private <V> void def(ImpTable<V> tab, String name, V value) {
		try {
			tab.put(name, value);
		} catch (DuplicateException e) {
			errors.duplicateDefinition(name);
		}
	}

}