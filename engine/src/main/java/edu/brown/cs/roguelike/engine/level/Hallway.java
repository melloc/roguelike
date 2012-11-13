package edu.brown.cs.roguelike.engine.level;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cs195n.Vec2i;

/**
 * A hallway connecting two spaces.
 * @author jte
 *
 */
public class Hallway implements Space, Serializable {

	/**
	 * Generated
	 */
	private static final long serialVersionUID = 4238224467162924679L;
	
	public Hallway(Vec2i startTile, Vec2i endTile) {
		this.startTile = endTile;
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
