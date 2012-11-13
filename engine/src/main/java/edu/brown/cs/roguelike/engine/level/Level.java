package edu.brown.cs.roguelike.engine.level;

import java.util.List;

import edu.brown.cs.roguelike.engine.save.IDManager;
import edu.brown.cs.roguelike.engine.save.Saveable;


public class Level implements Saveable {
	
	/**
	 * Generated
	 */
	private static final long serialVersionUID = 1187760172149150039L;
	
	public final Tile[][] tiles;
	private List<Room> rooms;
	private List<Hallway> hallways;
	
	public Level(Tile[][] tiles, List<Room> rooms, List<Hallway> hallways) {
		this.tiles = tiles;
		this.rooms = rooms;
		this.hallways = hallways;
	}
	
	public Tile[][] getTiles() { return this.tiles; }
	public List<Room> getRooms() { return this.rooms; }
	public List<Hallway> getHallways() { return this.hallways; }	
	
	/*** BEGIN Saveable ***/
	
	private long id;

	/** initialize id **/
	{
		this.id = IDManager.getNext();
	}
	
	@Override
	public long getId() {
		return this.id;
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
		Level other = (Level) obj;
		if (id == other.id) return true;
		return false;
	}
	
	/*** END Saveable ***/
}
