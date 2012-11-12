package edu.brown.cs.roguelike.engine.proc.cs195n;

public class InvalidLevelException extends Exception {
	private static final long serialVersionUID = 1L;
	public InvalidLevelException() {}
	public InvalidLevelException(String message, Throwable cause) {
		super(message, cause);
	}
	public InvalidLevelException(String message) {
		super(message);
	}
	public InvalidLevelException(Throwable cause) {
		super(cause);
	}
}
