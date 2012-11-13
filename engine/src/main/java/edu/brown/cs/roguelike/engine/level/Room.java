package edu.brown.cs.roguelike.engine.level;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cs195n.Vec2i;
import edu.brown.cs.roguelike.engine.save.IDManager;
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
	@Override
	public void connectToSpace(Space s) {s.connectToRoom(this);}
	
	@Override
	public void connectToRoom(Room r) {
		assert(false);
		//This shoud never happen, only hallways connect to rooms
	}
	
	@Override
	public void connectToHallway(Hallway h) {
		for(Room r : h.getRooms()) {
			this.addRoom(r);
			h.addRoom(this);
		}
	}
	
	/*** BEGIN Saveable ***/
	
	private long id;
	
	/**
	 * init block for assigning id
	 */
	{
		this.id = IDManager.getNext();
	}
	
	private void writeObject(ObjectOutputStream os) throws IOException {
		os.defaultWriteObject();
	}

	private void readObject(ObjectInputStream os) 
			throws IOException, ClassNotFoundException {
		os.defaultReadObject();
	}
	
	@Override
	public long getId() { return this.id; }

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
		Room other = (Room) obj;
		if (id == other.id) {  return true; }
		return false;
	}
	
	/*** END Saveable ***/
}
