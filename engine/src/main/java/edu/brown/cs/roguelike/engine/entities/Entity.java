package edu.brown.cs.roguelike.engine.entities;

import java.util.List;

import com.googlecode.lanterna.terminal.Terminal.Color;

import edu.brown.cs.roguelike.engine.graphics.Drawable;
import edu.brown.cs.roguelike.engine.level.Mappable;
import edu.brown.cs.roguelike.engine.save.IDManager;
import edu.brown.cs.roguelike.engine.save.Saveable;

public abstract class Entity implements Drawable, Mappable, Saveable {

	/**
	 * Generated
	 */
	private static final long serialVersionUID = 7459179832955737667L;
	
	protected char character;
	protected Color color;

	@Override
	public char getCharacter() {
		return character;
	}

	@Override
	public Color getColor() {
		return color;
	}

	public abstract List<String> getCategories();
	
	/*** BEGIN Saveable ***/
	
	private long id;
	
	/** initialize id **/
	{
		this.id = IDManager.getNext();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
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
		Entity other = (Entity) obj;
		if (id == other.id) return true;
		return false;
	}

	@Override
	public long getId() {
		return this.id;
	}
	
	/*** END Saveable ***/

}
