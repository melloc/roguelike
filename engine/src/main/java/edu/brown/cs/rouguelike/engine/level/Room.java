package edu.brown.cs.rouguelike.engine.level;

import java.util.ArrayList;
import java.util.List;

import cs195n.Vec2i;

public class Room {
	protected List<Tile> tiles; //All the tiles in the room, including walls
	protected List<Room> connectedRooms; //The rooms directly connected to this room

	public List<Tile> getTiles() {return this.tiles;}
	public void setTiles(List<Tile> tiles) {this.tiles = tiles;}
	
	public List<Room>  getConnectedRooms() {return this.connectedRooms;}
	public void addRoom(Room room) {connectedRooms.add(room);}
	
	
	public Room(List<Tile> tiles) {
		this.tiles = tiles;
		connectedRooms = new ArrayList<Room>();
	}
}
