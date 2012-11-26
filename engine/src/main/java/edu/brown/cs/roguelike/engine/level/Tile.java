package edu.brown.cs.roguelike.engine.level;

import java.util.LinkedList;
import java.util.UUID;

import com.googlecode.lanterna.terminal.Terminal.Color;

import cs195n.Vec2i;
import edu.brown.cs.roguelike.engine.entities.Entity;
import edu.brown.cs.roguelike.engine.entities.Stackable;
import edu.brown.cs.roguelike.engine.graphics.Drawable;
import edu.brown.cs.roguelike.engine.save.Saveable;

/**
 * A tile object to make up each physical level
 *
 * @author jte
 *
 */
public class Tile implements Saveable, Drawable {

	protected Vec2i location = null;
	protected Level level = null;

	/**
	 * Generated
	 */
	private static final long serialVersionUID = -4972149313921847289L;

	public Tile(TileType type) {
		this.type = type;
	}
	
	private LinkedList<Stackable> stackables = new LinkedList<Stackable>();

	/**
	 * @return the location
	 */
	public Vec2i getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(Vec2i location) {
		this.location = location;
	}

	/**
	 *
	 */
	public Direction dirTo(Tile other) {
		Vec2i otherLoc = other.getLocation();
		if (location.x < otherLoc.x)
			return Direction.RIGHT;
		else if (location.x > otherLoc.x)
			return Direction.LEFT;
		else if (location.y < otherLoc.y)
			return Direction.DOWN;
		else 
			return Direction.UP;
	}

	/**
	 * @return the level
	 */
	public Level getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(Level level) {
		this.level = level;
	}

	public LinkedList<Stackable> getStackables() {
		return stackables;
	}
	
	private Entity entity;

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		if (entity != null)
			entity.setLocation(this);
		this.entity = entity;
	}


	public boolean isPassable() {
		if (entity != null)
			return false;
		else
			return type.isPassable();
	}

	private TileType type;

	public TileType getType() {
		return type;
	}

	public void setType(TileType type) {
		this.type = type;
	}

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
		Tile other = (Tile) obj;
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


	public char getCharacter() {
		return getCurrent().getCharacter();
	}

	public Color getColor() {
		return getCurrent().getColor();
	}

	protected Drawable getCurrent() {
		if (this.entity != null)
			return entity;
		else if(stackables.size() > 0)
			return stackables.getFirst();
		else
			return getType();
	}

    @Override
    public String toString() {
        return "Tile" + location.toString();
    }

}
