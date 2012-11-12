package edu.brown.cs.roguelike.engine.level;

import java.util.List;

import cs195n.Vec2i;

public class Room implements Space {
	protected List<Room> connectedRooms; //The rooms directly connected to this room

	public Vec2i min; //Min point of room, excluding walls
	public Vec2i max; //Max point of room, excluding walls
	
	public List<Room>  getConnectedRooms() {return this.connectedRooms;}
	public void addRoom(Room room) {connectedRooms.add(room);}
	
	
	public Room(Vec2i min, Vec2i max) {
		this.min = min;
		this.max = max;
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
}
