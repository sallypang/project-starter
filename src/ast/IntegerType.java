package ast;

import visitor.Visitor;

public class IntegerType extends Type {
	
	public final String type;

	public IntegerType() {
		this.type = "int";
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
