package edu.brown.cs.rouguelike.engine.level;

import java.util.List;


public class Level {
	public final Tile[][] tiles;
	private List<Room> rooms;
	private List<Hallway> hallways;
	
	public Level(Tile[][] tiles, List<Room> rooms, List<Hallway> hallways) {
		this.tiles = tiles;
		this.rooms = rooms;
		this.hallways = hallways;
	}
}
