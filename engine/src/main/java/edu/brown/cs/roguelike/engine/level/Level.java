package edu.brown.cs.roguelike.engine.level;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import edu.brown.cs.roguelike.engine.entities.EntityActionManager;
import edu.brown.cs.roguelike.engine.entities.EntityManager;
import edu.brown.cs.roguelike.engine.entities.MainCharacter;
import edu.brown.cs.roguelike.engine.save.Saveable;


public class Level implements Saveable {

	/**
	 * Generated
	 */
	private static final long serialVersionUID = 1187760172149150039L;

	private static final int TIER_STEP_SIZE = 5;

	public final Tile[][] tiles;
	private List<Room> rooms;
	private List<Hallway> hallways;
	protected EntityManager manager = new EntityManager();
	private int depth; //The depth of the level
	public HashSet<Room> revealedRooms = new HashSet<Room>();
	
	public Tile upStairs;
	public Tile downStairs;
	
	public int getTier() {
		 return (1 + (this.depth/TIER_STEP_SIZE));
	}
	
	public int getTierDiff() {
		 return this.depth + 1 -  TIER_STEP_SIZE*(getTier()-1);
	}
	
	/**
	 * @return The entity manager for this {@link Level}.
	 */
	public EntityManager getManager() {
		return manager;
	}

	public void revealRoom(Room r) {
		if(revealedRooms.contains(r))
			return;
		
		for(int i = r.min.x-1; i<=r.max.x+1; i++) {
			for(int j = r.min.y-1; j<=r.max.y+1; j++) {
				tiles[i][j].setReveal(true);
			}
		}
		revealedRooms.add(r);
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
		Level other = (Level) obj;
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

	public void revealAround(Tile t) {
		for(int i = t.location.x-1; i<=t.location.x+1;i++) {
			for(int j = t.location.y-1; j<=t.location.y+1;j++) {
				tiles[i][j].setReveal(true);
			}
		}
	}

	/**Removes the player from the world and returns it
	 * Allows for world to be simulated while the player is not there.
	 * @return The main character of the level
	 */
	public MainCharacter removePlayer() {
		List<EntityActionManager> mains = manager.getEntity("main");
		if(mains.size() >0) {
			MainCharacter mc = (MainCharacter) mains.get(0).getEntity();
			mc.getLocation().setEntity(null);
			manager.unregister(mc);
			return mc;
		}
		else
			return null;
	}

	/**Places the character onto the map on the stairs indicated by the boolean argument
	 * @param mc The main character to place
	 *
	 * @param up: If true, place on the down stairs, else, the up stairs
	 */
	public void placeCharacter(MainCharacter mc, boolean up) {
		if(up)
			upStairs.setEntity(mc);
		else
			downStairs.setEntity(mc);
		
		LinkedList<String> attr = new LinkedList<String>();
		attr.add("main");
		attr.add("keyboard");
		manager.register(mc.getManager(),attr);
	}


}
