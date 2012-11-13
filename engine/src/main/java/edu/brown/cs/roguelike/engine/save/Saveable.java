package edu.brown.cs.roguelike.engine.save;

import java.io.Serializable;


/**
 * Any class that needs to be saved needs to implement this,
 * not Serializable. All implementing classes need to make
 * sure that getId returns a long that is globally unique
 * 
 * Note that hashCode and equals should be overridden by
 * implementing classes. Having this methods in this interface
 * does not actually force implementing classes to override, but
 * they are here for a reminder.
 * 
 * 
 * @author lelberty
 *
 */
public interface Saveable extends Serializable {
	
	/**
	 * Must return a long that is globally unique
	 * @return long globally unique id
	 */
	public long getId();

	/**
	 * Hash based on this classes id
	 * 
	 * @return int hash code
	 */
	public abstract int hashCode();
	
	/**
	 * Overriding equals should use id to determine
	 * equality
	 * 
	 * @param o
	 * @return boolean indicating object equality
	 */
	public abstract boolean equals(Object o);
}
