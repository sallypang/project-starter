package ast;

/// Not an AST node, but used in FunctionDeclaration
public class Parameter {
	public final Type type;
	public final String name;
	
	public Parameter(Type type, String name) {
		this.type = type;
		this.name = name;
	}
}
