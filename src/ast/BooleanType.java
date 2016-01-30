package ast;

import visitor.Visitor;

public class BooleanType extends Type {

	public final String type;

	public BooleanType() {
		this.type = "boolean";
	}
	@Override
	public <R> R accept(Visitor<R> v) {
		return v.visit(this);
	}

	@Override
	public boolean equals(Object other) {
		return this.getClass()==other.getClass();
	}
	
}
