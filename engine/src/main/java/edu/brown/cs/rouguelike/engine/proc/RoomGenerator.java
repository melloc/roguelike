package edu.brown.cs.rouguelike.engine.proc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cs195n.Vec2i;
import edu.brown.cs.rouguelike.engine.level.Hallway;
import edu.brown.cs.rouguelike.engine.level.Level;
import edu.brown.cs.rouguelike.engine.level.Room;
import edu.brown.cs.rouguelike.engine.level.Tile;
import edu.brown.cs.rouguelike.engine.level.TileType;
import edu.brown.cs.rouguelike.engine.proc.Split;

public class RoomGenerator {
	private final int depthMax = 4;
	

	//-----------------------------------------------------------------------CONSTANTS-----------------------------------------------------------------------------------------------------------------
	/*
	private float roomMin; //min % the room occupies of sublevel
	private float roomMax; //max % the room occupies of sublevel
	private float roomBuffer; //% on each side of room must be from edge
	*/
	
	private final float splitMin = 0.1f; // min % to split at
	private final float splitMax = 0.9f; // max % to split at
	
	//---------------------------------------------------------------------END CONSTANTS------------------------------------------------------------------------------------------------------
	
	private ArrayList<Room> rooms;
	private ArrayList<Hallway> hallways;
	Tile[][] tiles;
	Random random; 
	
	/**
	 * Generates a full level whose size is levelSize
	 */
	public Level generateLevel(Vec2i levelSize) {
		random = new Random(System.nanoTime());
		tiles = new Tile[levelSize.x][levelSize.y];
		rooms = new ArrayList<Room>();
		hallways = new ArrayList<Hallway>();
		
		//Init with solid map
		fillWithWalls(tiles);
	
		SubLevel fullLevel = new SubLevel(new Vec2i(0,0), levelSize);
		splitAndBuild(fullLevel);
		
		return new Level(tiles,fullLevel.rooms,fullLevel.hallways);
	}

	/**
	 * Splits the sublevel recursively until it builds rooms, then connects the rooms together
	 * In the end, curr is populated with the current hallways and rooms
	 */
	
	private void splitAndBuild(SubLevel curr) {
		if(curr.depth == depthMax) {
			makeRoom(curr);
		}
		else{ 
		//Split into two more sub-levels, recur, then connect
			Split s = (random.nextInt(2) == 0) ? Split.VER : Split.HOR;
			
			if(s == Split.HOR){
				float width = (curr.max.x-curr.min.x);
				float splitVal = curr.min.x + width*splitMin+ random.nextInt(Math.round((width*(splitMax-splitMin))));
			}
			
			
		}
	}

	/**
	 * Makes a room inside the sublevel
	 * @param curr - the area to make the room in
	 */
	private void makeRoom(SubLevel curr) {
		int maxWidth = curr.max.x - curr.min.x;
		int maxHeight = curr.max.y - curr.min.y;
		
		//Randomly Select room coordinates
		int minX = random.nextInt(maxWidth);
		int maxX = minX + random.nextInt(maxWidth-minX);
		int minY = random.nextInt(maxHeight);
		int maxY = minY + random.nextInt(maxHeight-minY);
		
		Vec2i min = new Vec2i(minX,minY);
		Vec2i max = new Vec2i(minX,minY);
		
		//Find all tiles inside room and create room
		ArrayList<Tile> rTiles = new ArrayList<Tile>();
		for(int i = min.x; i <= max.x; i++) {
			for(int j = min.y; i <= max.y; i++) {
				rTiles.add(tiles[i][j]);
			}
		}
		
		//Paint room to tile array
		paintCellRectangle(min,max,true, TileType.FLOOR);
		
		Room r = new Room(rTiles);
		rooms.add(r);
		curr.rooms.add(r);
	}
	
	/**
	 * Puts a cell with the given attributes in the rectangle given by
	 * min,max inclusive.
	 */
	private void paintCellRectangle(Vec2i min, Vec2i max, boolean passable, TileType t) {
		for(int i = min.x; i <= max.x; i++) {
			for(int j = min.y; i <= max.y; i++) {
				tiles[i][j] = new Tile(t,passable);
			}
		}
	}

	private void fillWithWalls(Tile[][] tiles) {
		for(int i = 0; i < tiles.length; i++) {
			for(int j = 0; j<tiles[0].length; j++) {
				tiles[i][j] = new Tile(TileType.WALL,false);
			}
		}
	}

	
	
}
