package jte.engine.proc;

import java.util.ArrayList;
import java.util.List;

import cs195n.Vec2i;

public class Hallway {
	public Hallway(Vec2i startTile, Vec2i endTile) {
		this.startTile = endTile;
		this.endTile = endTile;
		rooms = new ArrayList<Room>();
	}
	
	public final Vec2i startTile;
	public final Vec2i endTile;
	
	private List<Room> rooms; //The rooms the hallway connects to
	public void addRoom(Room r) {rooms.add(r);}
	public List<Room> getRooms() {return this.rooms;}
}
