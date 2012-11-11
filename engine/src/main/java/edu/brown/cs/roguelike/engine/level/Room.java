package edu.brown.cs.roguelike.engine.level;

import java.util.ArrayList;
import java.util.List;

import cs195n.Vec2i;

public class Room {
	protected List<Room> connectedRooms; //The rooms directly connected to this room

	public Vec2i min; //Min point of room
	public Vec2i max; //Max point of room
	
	public List<Room>  getConnectedRooms() {return this.connectedRooms;}
	public void addRoom(Room room) {connectedRooms.add(room);}
	
	
	public Room(Vec2i min, Vec2i max) {
		this.min = min;
		this.max = max;
	}
}
