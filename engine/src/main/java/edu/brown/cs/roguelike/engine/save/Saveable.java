package edu.brown.cs.roguelike.engine.save;

import java.io.Serializable;


/**
 * Any class that needs to be saved needs to implement this,
 * not Serializable. All implementing classes need to make
 * sure that getId returns a long that is globally unique
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
	@Override
	public int hashCode();
	
	
	/**
	 * Overriding equals should use id to determine
	 * equality
	 * 
	 * @param o
	 * @return boolean indicating object equality
	 */
	@Override
	public boolean equals(Object o);
}
