package edu.brown.cs.roguelike.engine.proc;

import cs195n.Vec2i;
import edu.brown.cs.roguelike.engine.config.ConfigurationException;
import edu.brown.cs.roguelike.engine.level.Hallway;
import edu.brown.cs.roguelike.engine.level.Level;
import edu.brown.cs.roguelike.engine.level.Room;
import edu.brown.cs.roguelike.engine.level.Tile;
import edu.brown.cs.roguelike.engine.level.TileType;

/**
 * A level generator that uses BSP (binary space partioning) 
 * to place its rooms
 * 
 * @author jte
 *
 */
public class BSPLevelGenerator implements LevelGenerator{
	private final int depthMax = 3;


	//-----------------------------------------------------------------------CONSTANTS-----------------------------------------------------------------------------------------------------------------
	/*
	private float roomMin; //min % the room occupies of sublevel
	private float roomMax; //max % the room occupies of sublevel
	private float roomBuffer; //% on each side of room must be from edge
	 */

	private final float splitMin = 0.1f; // min % to split at
	private final float splitMax = 0.9f; // max % to split at

	private final int minWallThickness = 2; //Should be >=  2

	private int minRoomDim = 5;

	private int splitTries = 7;


	//---------------------------------------------------------------------END CONSTANTS------------------------------------------------------------------------------------------------------

	Tile[][] tiles;
	RandomGen rand;


	/**
	 * Generates a full level whose size is levelSize
	 * @throws ConfigurationException 
	 */
	public Level generateLevel(Vec2i levelSize) throws ConfigurationException {
		rand = new RandomGen(System.nanoTime());
		tiles = new Tile[levelSize.x][levelSize.y];

		//Init with solid map
		fillWithSolids(tiles);

		SubLevel fullLevel = new SubLevel(new Vec2i(0,0), levelSize,0);
		splitAndBuild(fullLevel);

		Level level = new Level(tiles,fullLevel.rooms,fullLevel.hallways);
		
		
		//TESTING PURPOSES: ALWAYS CONSTANT DEPTH
		level.setDepth(5); 
		//TESTING
		
		MonsterGenerator mg = new ProgressiveMonsterGenerator();
		mg.populateLevel(level);

		
		return level;
	}




	/**
	 * Splits the sublevel recursively until it builds rooms, then connects the rooms together
	 * In the end, curr is populated with the current hallways and rooms
	 */

	private void splitAndBuild(SubLevel curr) {
		boolean makeRoom = false;
		int splitAttempts = 0;

		if(curr.depth == depthMax) //Check to see if you should make a room
		{
			makeRoom = true;
		}
		if(makeRoom) {
			makeRoom(curr);
		}
		else{ 

			//Split into two more sub-levels, recur, then connect
			Split s = (rand.getRandom(2) == 0) ? Split.VER : Split.HOR;

			if(s == Split.HOR) {
				float height = (curr.max.y-curr.min.y);
				if(height < 4*minWallThickness + 2*minRoomDim) {
					s = Split.VER;
				}
			}
			else { //VER
				float width = (curr.max.x-curr.min.x);
				if(width < 4*minWallThickness + 2*minRoomDim) {
					s = Split.HOR;
				}
			}


			SubLevel s1,s2;


			if(s == Split.HOR){
				float height = (curr.max.y-curr.min.y);

				if(height < 4*minWallThickness + 2*minRoomDim) {
					makeRoom(curr);
					return;
				}

				int splitVal = Math.round(curr.min.y + height*splitMin+ rand.getRandom(Math.round((height*(splitMax-splitMin)))));

				while(splitVal - curr.min.y < 2*minWallThickness + minRoomDim || curr.max.y - splitVal -1 < 2*minWallThickness + minRoomDim ) {
					if(splitAttempts >= splitTries) {
						makeRoom(curr);
						return;
					}
					splitAttempts++;
					splitVal = Math.round(curr.min.y + height*splitMin+ rand.getRandom(Math.round((height*(splitMax-splitMin)))));
				}

				//S1 gets [0-splitVal], S2 gets [SplitVal+1,Max]
				s1 = new SubLevel(new Vec2i(curr.min.x,curr.min.y), new Vec2i(curr.max.x,splitVal), curr.depth+1);
				s2 = new SubLevel(new Vec2i(curr.min.x,splitVal+1), new Vec2i(curr.max.x,curr.max.y), curr.depth+1);
			}
			else { //VER
				float width = (curr.max.x-curr.min.x);

				if(width < 4*minWallThickness + 2*minRoomDim) {
					makeRoom(curr);
					return;
				}

				int maxVal = Math.round((width*(splitMax-splitMin)));
				int splitVal = Math.round(curr.min.x + width*splitMin+ rand.getRandom(maxVal));

				while(splitVal - curr.min.x < 2*minWallThickness + minRoomDim || curr.max.x - splitVal -1 < 2*minWallThickness + minRoomDim ) {
					if(splitAttempts >= splitTries) {
						makeRoom(curr);
						return;
					}
					splitAttempts++;
					maxVal = Math.round((width*(splitMax-splitMin)));
					splitVal = Math.round(curr.min.x + width*splitMin+ rand.getRandom(maxVal));
				}

				//S1 gets [0-splitVal], S2 gets [SplitVal+1,Max]
				s1 = new SubLevel(new Vec2i(curr.min.x,curr.min.y), new Vec2i(splitVal,curr.max.y), curr.depth+1);
				s2 = new SubLevel(new Vec2i(splitVal+1,curr.min.y), new Vec2i(curr.max.x,curr.max.y), curr.depth+1);
			}

			splitAndBuild(s1);
			splitAndBuild(s2);

			Range range = s1.overLap(s2, s);
			if(range != null)
			{
				int hpt;
				HallwayPoint hp1;
				HallwayPoint hp2;
				do{
					hpt = rand.getRandom(range.min,range.max);
					hp1 = s1.getHallwayPoint(s, true, hpt);
					hp2 = s2.getHallwayPoint(s, false, hpt);
				}
				while (!testHallway(hp1.point,hp2.point));

				Hallway new_hallway = new Hallway(hp1.point,hp2.point);

				paintHallway(hp1.point,hp2.point,true, TileType.FLOOR);

				hp1.space.connectToHallway(new_hallway);
				hp2.space.connectToHallway(new_hallway);

				if(hp1.space.needDoor())
					makeDoor(hp1.point,s,true);
				if(hp2.space.needDoor())
					makeDoor(hp2.point,s,false);

				curr.hallways.add(new_hallway);
			}
			else{
				//Make L-Shaped corridor
				if(s == Split.HOR) {

					HallwayPoint hp1;
					HallwayPoint hp2;
					Vec2i corner;
					do{
						int cx = rand.getRandom(s1.intersectMin.x, s1.intersectMax.x);
						int cy = rand.getRandom(s2.intersectMin.y, s2.intersectMax.y);
						corner = new Vec2i(cx,cy);

						hp1 = s1.getHallwayPoint(Split.HOR, true, cx);
						hp2 = s2.getHallwayPoint(Split.VER, (s2.intersectMin.x < s1.intersectMax.x), cy);
					}
					while (!testHallway(hp1.point,corner) && !testHallway(hp2.point,corner));




					Hallway new_hallway1 = new Hallway(hp1.point,corner);
					hp1.space.connectToHallway(new_hallway1);

					Hallway new_hallway2;

					if (s2.intersectMin.x > s1.intersectMax.x) 
						new_hallway2 = new Hallway(hp2.point,corner);
					else 
						new_hallway2 = new Hallway(corner,hp2.point);


					hp2.space.connectToHallway(new_hallway2);

					new_hallway1.connectToHallway(new_hallway2);

					//Paint in
					paintHallway(hp1.point,corner,true,TileType.FLOOR);
					paintHallway(hp2.point,corner,true,TileType.FLOOR);

					if(hp1.space.needDoor())
						makeDoor(hp1.point,s,true);
					if(hp2.space.needDoor())
						makeDoor(hp2.point,s,false);

					curr.hallways.add(new_hallway1);
					curr.hallways.add(new_hallway2);
				}
				else{ //VER

					HallwayPoint hp1;
					HallwayPoint hp2;
					Vec2i corner;

					do{
						int cx = s1.intersectMin.x + rand.getRandom(s1.intersectMax.x - s1.intersectMin.x);
						int cy = s2.intersectMin.y + rand.getRandom(s2.intersectMax.y - s2.intersectMin.y);
						corner = new Vec2i(cx,cy);

						hp1 = s1.getHallwayPoint(Split.HOR, (s1.intersectMin.y < s2.intersectMax.y), cx);
						hp2 = s2.getHallwayPoint(Split.VER, false, cy);
					}
					while (!testHallway(hp1.point,corner) && !testHallway(hp2.point,corner));

					Hallway new_hallway1;
					if (s1.intersectMin.y < s2.intersectMax.y)
						new_hallway1 = new Hallway(hp1.point,corner);
					else 
						new_hallway1 = new Hallway(corner,hp1.point);

					hp1.space.connectToHallway(new_hallway1);

					Hallway new_hallway2 = new Hallway(corner,hp2.point);
					hp2.space.connectToHallway(new_hallway2);

					new_hallway1.connectToHallway(new_hallway2);

					//Paint in
					paintHallway(hp1.point,corner,true,TileType.FLOOR);
					paintHallway(hp2.point,corner,true,TileType.FLOOR);

					if(hp1.space.needDoor())
						makeDoor(hp1.point,s,true);
					if(hp2.space.needDoor())
						makeDoor(hp2.point,s,false);
					
					curr.hallways.add(new_hallway1);
					curr.hallways.add(new_hallway2);
				}
			}
			//Combine into a single sublevel
			curr.rooms.addAll(s1.rooms);
			curr.rooms.addAll(s2.rooms);
			curr.hallways.addAll(s1.hallways);
			curr.hallways.addAll(s2.hallways);

			int maxIX = Math.max(s1.intersectMax.x, s2.intersectMax.x);
			int maxIY = Math.max(s1.intersectMax.y, s2.intersectMax.y);
			int minIX = Math.min(s1.intersectMin.x, s2.intersectMin.x);
			int minIY = Math.min(s1.intersectMin.y, s2.intersectMin.y);

			curr.intersectMax = new Vec2i(maxIX,maxIY);
			curr.intersectMin = new Vec2i(minIX,minIY);
		}
	}



	/**Makes a door at location, using the split and side to accurately place it in the wall**/
	private void makeDoor(Vec2i point, Split s, boolean b) {
		int xOff=0;
		int yOff=0;

		if(s == Split.HOR) 
			yOff = 1;
		else
			xOff = 1;
		if(!b) {
			xOff *= -1;
			yOff *= -1;
		}

		Tile t = tiles[point.x+xOff][point.y+yOff];
		t.setType(TileType.DOOR);
		t.setPassable(true);

	}


	/**
	 * Makes a room inside the sublevel
	 * @param curr - the area to make the room in
	 */
	private void makeRoom(SubLevel curr) {
		int maxWidth = curr.max.x - curr.min.x- 1*minWallThickness - 1;
		int maxHeight = curr.max.y - curr.min.y - 1*minWallThickness - 1;


		//Randomly Select room coordinates
		int minX = rand.getRandom(minWallThickness,maxWidth-minRoomDim);
		int maxX = rand.getRandom(minX+minRoomDim, maxWidth);

		int minY = rand.getRandom(minWallThickness,maxHeight-minRoomDim);
		int maxY = rand.getRandom(minY+minRoomDim, maxHeight);

		/*
		int minY = minWallThickness+rand.getRandom(maxHeightprivate void paintCellRectangle(Vec2i min, Vec2i max, boolean passable, TileType t) {
		for(int i = min.x; i <= max.x; i++) {
			for(int j = min.y; j <= max.y; j++) {
				Tile x = tiles[i][j];
				x.setPassable(passable);
				x.setType(t);
			}
		}-minRoomDim);
		int maxY =  minY + minRoomDim +  rand.getRandom(maxHeight-minY-minRoomDim);
		 */

		Vec2i min = curr.min.plus(minX, minY);
		Vec2i max = curr.min.plus(maxX, maxY);



		//Paint room to tile array
		paintCellRectangle(min,max,true, TileType.FLOOR);

		paintCellRectangle( curr.min.plus(minX-1,minY-1), curr.min.plus(minX-1,maxY+1),false, TileType.WALL_VER);
		paintCellRectangle( curr.min.plus(maxX+1,minY-1), curr.min.plus(maxX+1,maxY+1),false, TileType.WALL_VER);
		paintCellRectangle( curr.min.plus(minX-1,minY-1), curr.min.plus(maxX+1,minY-1),false, TileType.WALL_HOR);
		paintCellRectangle( curr.min.plus(minX-1,maxY+1), curr.min.plus(maxX+1,maxY+1),false, TileType.WALL_HOR);



		Room r = new Room(min,max);
		curr.rooms.add(r);

		//Hallways dont got to ends of rooms
		curr.intersectMin = min;
		curr.intersectMax = max;

		//Hallways dont got to ends of rooms
		//curr.intersectMin = min.plus(1,1);
		//curr.intersectMax = max.plus(-1,-1);
	}

	/**
	 * Puts a cell with the given attributes in the rectangle given by
	 * min,max inclusive.
	 */
	private void paintCellRectangle(Vec2i min, Vec2i max, boolean passable, TileType t) {
		for(int i = min.x; i <= max.x; i++) {
			for(int j = min.y; j <= max.y; j++) {
				Tile x = tiles[i][j];
				x.setPassable(passable);
				x.setType(t);
			}
		}
	}

	/**Tests to see if a hallway can be laid. returns true if there are no problems**/
	private boolean testHallway(Vec2i a, Vec2i b) {
		if(a.x > b.x || a.y > b.y) {
			testHallway(b,a);
		}

		if(a.x == b.x) {
			for(int i = a.x-1; i <= a.x+1; i++) {
				for(int j = a.y+2; j <= b.y-2; j++) {
					Tile x = tiles[i][j];
					if(x.getType() != TileType.SOLID) {
						return false;
					}
				}
			}
		}
		else{
			for(int y = a.y-1; y <= a.y+1; y++) {
				for(int x = a.x+2; x <= b.x-2; x++) {
					Tile t = tiles[x][y];
					if(t.getType() != TileType.SOLID) {
						return false;
					}
				}
			}
		}

		return true;
	}

	/**Paints a hallway onto the tile array**/
	private void paintHallway(Vec2i a, Vec2i b, boolean passable, TileType t) {
		if(a.x > b.x || a.y > b.y) {
			paintHallway(b,a,passable,t);
		}
		for(int i = a.x; i <= b.x; i++) {
			for(int j = a.y; j <= b.y; j++) {
				Tile x = tiles[i][j];
				x.setPassable(passable);
				x.setType(t);
			}
		}
	}

	/**Fills the tiles with all walls **/
	private void fillWithSolids(Tile[][] tiles) {
		for(int i = 0; i < tiles.length; i++) {
			for(int j = 0; j<tiles[0].length; j++) {
				tiles[i][j] = new Tile(TileType.SOLID,false);
			}
		}
	}



}
