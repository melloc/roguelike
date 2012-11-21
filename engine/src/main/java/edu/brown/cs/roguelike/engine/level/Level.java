package edu.brown.cs.roguelike.engine.level;

import java.util.LinkedList;
import java.util.List;

import edu.brown.cs.roguelike.engine.entities.EntityManager;
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
	protected EntityManager manager = new EntityManager();
	public int depth; //The depth of the level
	
	/**
	 * @return The entity manager for this {@link Level}.
	 */
	public EntityManager getManager() {
		return manager;
	}

	public Level(Tile[][] tiles, List<Room> rooms, List<Hallway> hallways) {
		this.tiles = tiles;
		for (Tile[] tiles2 : tiles)
			for (Tile tile : tiles2)
				tile.setLevel(this);
		this.rooms = rooms;
		this.hallways = hallways;
	}
	
	public Tile[][] getTiles() { return this.tiles; }
	public List<Room> getRooms() { return this.rooms; }
	public List<Hallway> getHallways() { return this.hallways; }	
	public int getDepth(){return depth;}
	
	public void setDepth(int depth) {this.depth = depth;}
	
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

	public List<Tile> getNeighbors(Tile current) {
		List<Tile> neighbors = new LinkedList<Tile>();
		
		if(current.getLocation().x != 0) {
			neighbors.add(tiles[current.getLocation().x-1][current.getLocation().y]);
		}
		if(current.getLocation().x != tiles.length) {
			neighbors.add(tiles[current.getLocation().x+1][current.getLocation().y]);
		}
		if(current.getLocation().y != 0) {
			neighbors.add(tiles[current.getLocation().x][current.getLocation().y-1]);
		}
		if(current.getLocation().y != tiles[0].length) {
			neighbors.add(tiles[current.getLocation().x][current.getLocation().y+1]);
		}
		return neighbors;
	}
	
	/*** END Saveable ***/
}
