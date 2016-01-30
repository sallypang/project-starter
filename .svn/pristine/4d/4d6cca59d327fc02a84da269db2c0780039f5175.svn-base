package ast;

import java.util.List;

import visitor.Visitor;

public class FunctionDeclaration extends Statement {
	public final Type returnType;
	public final String name;
	public final List<Parameter> parameters;
	public final NodeList<Assign> body;
	public final Expression returnExpression;
	
	public FunctionDeclaration(Type returnType, String name, List<Parameter> parameters, NodeList<Assign> body, Expression returnExpression) {
		this.returnType = returnType;
		this.name = name;
		this.parameters = parameters;
		this.body = body;
		this.returnExpression = returnExpression;
	}
	
	@Override
	public <R> R accept(Visitor<R> v) {
		return v.visit(this);
	}

}
