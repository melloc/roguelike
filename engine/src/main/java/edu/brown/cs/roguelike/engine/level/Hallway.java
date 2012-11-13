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

/**
 * A hallway connecting two spaces.
 * @author jte
 *
 */
public class Hallway implements Space, Saveable {

	/**
	 * Generated
	 */
	private static final long serialVersionUID = 4238224467162924679L;
	
	public Hallway(Vec2i startTile, Vec2i endTile) {
		this.startTile = startTile;
		this.endTile = endTile;
		rooms = new ArrayList<Room>();
	}
	
	public final Vec2i startTile;
	public final Vec2i endTile;
	
	private List<Room> rooms; //The rooms the hallway connects to
	public void addRoom(Room r) {
		for(Room room : rooms) {
			room.addRoom(r);
			}
		rooms.add(r);
		}
	public List<Room> getRooms() {return this.rooms;}
	
	@Override
	public void connectToSpace(Space s) {
		s.connectToHallway(this);
	}
	@Override
	public void connectToRoom(Room r) {
		r.connectToHallway(this);
		}
	@Override
	public void connectToHallway(Hallway h) {
		ArrayList<Room> other_rooms = new ArrayList<Room> (h.getRooms());
		for(Room r : rooms) {
			h.addRoom(r);
		}
		for(Room r : other_rooms){
			this.addRoom(r);
		}
	}
	
	
	/*** BEGIN Saveable ***/
	
	private long id;
	
	{ this.id = IDManager.getNext(); }

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
		Hallway other = (Hallway) obj;
		if (id == other.id)
			return true;
		return true;
	}
	
	/*** END Saveable ***/
	
}
