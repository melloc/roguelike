package jte.engine.proc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import jte.engine.proc.Split;
import cs195n.Vec2i;

public class RoomGenerator {
	private final int depthMax = 4;
	
	/*
	private float roomMin; //min % the room occupies of sublevel
	private float roomMax; //max % the room occupies of sublevel
	private float roomBuffer; //% on each side of room must be from edge
	*/
	
	private ArrayList<Room> rooms;
	Tile[][] tiles;
	Random random = new Random(System.nanoTime());

	/*Generates a full dungeon level*/
	public Level generateLevel(Vec2i levelSize) {
		tiles = new Tile[levelSize.x][levelSize.y];
		
		//Init with solid map
		fillWithWalls(tiles);
	
		SubLevel fullLevel = new SubLevel(new Vec2i(0,0), levelSize);
		splitAndBuild(fullLevel);
		return null;
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
			Split s = Split(random.nextInt(2)); //Retruns 0 for hor or 1 for ver
		}
	}

	/**
	 * Makes a room inside the sublevel
	 * @param curr - the area to make the room in
	 */
	private void makeRoom(SubLevel curr) {
		int maxWidth = curr.max.x - curr.min.x;
		int maxHeight = curr.max.y - curr.min.y;
		
		int minX = random.nextInt(maxWidth);
		int maxX = minX + random.nextInt(maxWidth-minX);
		int minY = random.nextInt(maxHeight);
		int maxY = minY + random.nextInt(maxHeight-minY);
		
		Vec2i min = new Vec2i(minX,minY);
		Vec2i max = new Vec2i(minX,minY);
		curr.rooms.add(new Room(min,max));
	}
	
	/**
	 * Puts a cell with the given attributes in the rectangle given by
	 * min,max inclusive.
	 * @return 
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
