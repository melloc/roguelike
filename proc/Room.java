package jte.engine.proc;

import java.util.List;

public class Room {
	protected List<Tile> tiles; //All the tiles in the room, including walls
	protected List<Room> connectedRooms; //The rooms directly connected to this room

	public List<Tile> getTiles() {return this.tiles;}
	public void setTiles(List<Tile> tiles) {this.tiles = tiles;}
	
	public List<Room>  getConnectedRooms() {return this.connectedRooms;}
	public void addRoom(Room room) {connectedRooms.add(room);}
}
