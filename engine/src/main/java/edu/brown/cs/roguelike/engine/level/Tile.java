package edu.brown.cs.roguelike.engine.level;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import com.googlecode.lanterna.terminal.Terminal.Color;

import cs195n.Vec2i;
import edu.brown.cs.roguelike.engine.entities.Entity;
import edu.brown.cs.roguelike.engine.entities.MainCharacter;
import edu.brown.cs.roguelike.engine.entities.Stackable;
import edu.brown.cs.roguelike.engine.graphics.Drawable;
import edu.brown.cs.roguelike.engine.pathfinding.AStarNode;
import edu.brown.cs.roguelike.engine.save.Saveable;

/**
 * A tile object to make up each physical level
 *
 * @author jte
 *
 */
public class Tile extends AStarNode<Tile> implements Saveable, Drawable {
	
	// The Space I belong to
	protected Space space;

	protected Vec2i location = null;
	protected Level level = null;
	protected boolean reveal = false;

	/**Allows you to reveal/hide a tile**/
	public void setReveal(boolean r) {
		this.reveal = r;
	}
	
	/**
	 * Generated
	 */
	private static final long serialVersionUID = -4972149313921847289L;

	public Tile(TileType type) {
		this.type = type;
	}
	
	public Tile(TileType type, Space space) {
		this.type = type;
		this.space = space;
	}
	
	private LinkedList<Stackable> stackables = new LinkedList<Stackable>();

	/**
	 * @return the location
	 */
	public Vec2i getLocation() {
		return location;
	}
	
	public Space getSpace() { return space; }
	public void setSpace(Space space) { this.space = space; }

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
	
    @Override
    public String toString() {
        return "Tile" + location.toString();
    }

	public boolean getReveal() {
		return this.reveal;
	}

	@Override
	protected int getHScore(Tile goal) {
		return 10*calcManhattan(goal);
	}
	
	@Override 
	public ArrayList<Tile> getNeighbors() {
		
		List<Tile> trueNeighbors = level.getNeighbors(this);
		
		ArrayList<Tile> passableNeighbors = new ArrayList<Tile>();
		
		for (Tile t : trueNeighbors) 
			if ( t.getType().passable ) passableNeighbors.add(t);
		
		return passableNeighbors;
	}
	
	// Convenience method to calculate the Manhattan distance
	protected int calcManhattan(Tile goal) {
		
		int rows = Math.abs(location.y-goal.location.y);
		int cols = Math.abs(location.x-goal.location.x);
		
		return rows + cols;
	}

	@Override
	public int distance(Tile neighbor) {
		int penalty = 0;
		
		Entity occupant = neighbor.getEntity();
		
		// Don't move into other Monsters, silly
		if (occupant != null && !(occupant instanceof MainCharacter)) penalty = 100; 
		
		return 10 + penalty;
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
		if(this.reveal == false && !(entity instanceof MainCharacter)) {
			return TileType.HIDDEN;
		}
		if (this.entity != null)
			return entity;
		else if(stackables.size() > 0)
			return stackables.getFirst();
		else
			return getType();
	}



}
