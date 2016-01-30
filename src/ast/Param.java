package ast;

// Dummy class to make Param listing more modular
public class Param {
	public final Type type;
	public final String name;
	
	public Param(Type type, String name) {
		this.type = type;
		this.name = name;
	}
}
