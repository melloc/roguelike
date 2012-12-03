package edu.brown.cs.roguelike.engine.entities;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.googlecode.lanterna.terminal.Terminal.Color;

import edu.brown.cs.roguelike.engine.graphics.Drawable;
import edu.brown.cs.roguelike.engine.level.Mappable;
import edu.brown.cs.roguelike.engine.save.Saveable;

public abstract class Entity implements Drawable, Mappable, Saveable {

	/**
	 * Generated
	 */
	private static final long serialVersionUID = 7459179832955737667L;
	
	protected char character;
	protected Color color;
	protected Set<Stackable> inventory = new HashSet<Stackable>();

	@Override
	public char getCharacter() {
		return character;
	}

	@Override
	public Color getColor() {
		return color;
	}
	
	public abstract String getDescription();
	
	public Set<Stackable> getInventory() {return inventory;}
	public void addToInventory(Stackable item) {inventory.add(item);}
	public void removeFromInventory(Stackable item) {inventory.remove(item);}

	public abstract List<String> getCategories();
	
	/*** BEGIN Saveable ***/

	private UUID id;
	
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
		Entity other = (Entity) obj;
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

	/*** END Saveable ***/
	
	

}
