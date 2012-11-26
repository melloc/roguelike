package edu.brown.cs.roguelike.engine.level;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import cs195n.Vec2i;
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
	public void connectToHallway(Hallway h) {
		ArrayList<Room> other_rooms = new ArrayList<Room> (h.getRooms());
		for(Room r : rooms) {
			h.addRoom(r);
		}
		for(Room r : other_rooms){
			this.addRoom(r);
		}
	}
	
	@Override
	public boolean needDoor() {
		return false;
	}
	
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
		Hallway other = (Hallway) obj;
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
