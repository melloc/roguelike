package edu.brown.cs.roguelike.engine.level;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import cs195n.Vec2i;
import edu.brown.cs.roguelike.engine.save.Saveable;

public class Room implements Space, Saveable {
	
	/**
	 * Generated
	 */
	private static final long serialVersionUID = -760167898015853500L;
	
	protected List<Room> connectedRooms; //The rooms directly connected to this room

	public Vec2i min; //Min point of room, excluding walls
	public Vec2i max; //Max point of room, excluding walls
	
	public List<Room>  getConnectedRooms() {return this.connectedRooms;}
	public void addRoom(Room room) {connectedRooms.add(room);}
	
	
	
	public Room(Vec2i min, Vec2i max) {
		this.min = min;
		this.max = max;
		this.connectedRooms = new ArrayList<Room>();
	}
	
	public boolean containsTile(Tile t) {
		return (t.location.x >= min.x && t.location.x <= max.x && t.location.y >= min.y && t.location.y <= max.y);
	}
	
	@Override
	public void connectToHallway(Hallway h) {
		for(Room r : h.getRooms()) {
			this.addRoom(r);
			h.addRoom(this);
		}
	}

	
	@Override
	public boolean needDoor() {
		return true;
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
		Room other = (Room) obj;
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

}
