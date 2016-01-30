package ast;

import java.util.List;

import visitor.Visitor;

public class FunctionDeclaration extends Statement {
	
	public final Type type;
	public final String name;
	public final List<Param> formal;
	public final NodeList<Statement> assign;
	public final Expression ret;
	
	public FunctionDeclaration(Type type, String name, 
			List<Param> formal, NodeList<Statement> assign, 
			Expression ret) {
		super();
		this.type = type;
		this.name = name;
		this.formal = formal;
		this.assign = assign;
		this.ret = ret;
	}

	@Override
	public <R> R accept(Visitor<R> v) {
		return v.visit(this);
	}

}