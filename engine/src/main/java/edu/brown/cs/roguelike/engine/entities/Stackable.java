package edu.brown.cs.roguelike.engine.entities;

import java.util.UUID;

import com.googlecode.lanterna.terminal.Terminal.Color;

import edu.brown.cs.roguelike.engine.graphics.Drawable;
import edu.brown.cs.roguelike.engine.save.Saveable;

/**
 * Anything that can be on a tile in a "pile"
 * Any number of stackables can be on a tile, along with an entity
 * 
 * @author Jake
 *
 */
public abstract class Stackable implements Drawable, Saveable {
	
	/**
	 * Generated 
	 */
	private static final long serialVersionUID = -4791198502696211551L;
	
	protected char character;
	protected Color color;
	
	public char getCharacter() {return character;}
	public Color getColor() {return color;}
	
	public abstract String getDescription();
	
	
	/*** BEGIN Saveable ***/

	private UUID id;

	protected ItemType type;
	protected Action action;
	
	/** initialize id **/
	{
		this.id = UUID.randomUUID();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stackable other = (Stackable) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (id.equals(other.id))
			// return true if ids are the same
			return true;
		return false;
	}

	@Override
	public UUID getId() {
		return this.id;
	}
	public ItemType getType() {
		return type;
	}
	

	/*** END Saveable ***/
}
