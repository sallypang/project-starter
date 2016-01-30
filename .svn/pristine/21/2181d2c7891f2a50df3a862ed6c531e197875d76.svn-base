package ast;

import java.util.List;

import visitor.Visitor;

public class FunctionInvocation extends Expression {
	public final String name;
	public final List<Expression> arguments;
	
	public FunctionInvocation(String name, List<Expression> arguments) {
		this.name = name;
		this.arguments = arguments;
	}
	
	@Override
	public <R> R accept(Visitor<R> v) {
		return v.visit(this);
	}

}
