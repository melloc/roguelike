package edu.brown.cs.roguelike.engine.level;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cs195n.Vec2i;

public class Room implements Space, Serializable {
	
	/**
	 * Generated
	 */
	private static final long serialVersionUID = -760167898015853500L;
	
	private static long gid = 0;

	protected List<Room> connectedRooms; //The rooms directly connected to this room

	public Vec2i min; //Min point of room, excluding walls
	public Vec2i max; //Max point of room, excluding walls
	
	public List<Room>  getConnectedRooms() {return this.connectedRooms;}
	public void addRoom(Room room) {connectedRooms.add(room);}
	
	private long id;
	
	/*** init block for assigning id
	 * 
	 * @author liam
	 */
	{
		this.id = Room.gid;
		Room.gid++;
	}
	
	
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
	
	/*** BEGIN hashCode and equals ***/

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((connectedRooms == null) ? 0 : connectedRooms.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((max == null) ? 0 : max.hashCode());
		result = prime * result + ((min == null) ? 0 : min.hashCode());
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
		// must be first check to prevent mutually recursive rooms
		// from running forever in eq checks
		if (id == other.id) {  return true; }
		if (max == null) {
			if (other.max != null)
				return false;
		} else if (!max.equals(other.max))
			return false;
		if (min == null) {
			if (other.min != null)
				return false;
		} else if (!min.equals(other.min))
			return false;
		if (connectedRooms == null) {
			if (other.connectedRooms != null)
				return false;
		} else if (!connectedRooms.equals(other.connectedRooms))
			return false;
		return true;
	}
	
	/*** END hashCode and equals ***/
	
	
	/*** BEGIN Serialization ***/

	/**
	 * Custom writeObject
	 * @param os the ObjectOutputStream
	 * @throws IOException 
	 */
	private void writeObject(ObjectOutputStream os) throws IOException {
		os.defaultWriteObject();
	}

	
	/**
	 * Custom readObject
	 * @param os the ObjectInputStream
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void readObject(ObjectInputStream os) 
			throws IOException, ClassNotFoundException {
		os.defaultReadObject();
	}
	
	/*** END Serialization ***/
}
