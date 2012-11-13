package edu.brown.cs.roguelike.engine.save;

public class SaveLoadException extends Exception {

	/**
	 * Generated
	 */
	private static final long serialVersionUID = -723895557695580009L;

	public SaveLoadException(String msg) {
		super(msg);
	}
	
	public SaveLoadException(Throwable cause) {
		super(cause);
	}

}
