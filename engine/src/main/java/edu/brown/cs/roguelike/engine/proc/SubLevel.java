package edu.brown.cs.roguelike.engine.proc;

import java.util.ArrayList;

import cs195n.Vec2i;
import edu.brown.cs.roguelike.engine.level.Hallway;
import edu.brown.cs.roguelike.engine.level.Room;
import edu.brown.cs.roguelike.engine.level.Space;

public class SubLevel {
	public Vec2i min;
	public Vec2i max;
	public int depth;

	public Vec2i intersectMin;
	public Vec2i intersectMax;

	public SubLevel(Vec2i min, Vec2i max, int d) {
		this.min = min;
		this.max = max;
		this.depth = d;
	}

	public ArrayList<Room> rooms = new ArrayList<Room>();
	public ArrayList<Hallway> hallways = new ArrayList<Hallway>();

	/**
	 * Finds the overlap of this sublevel and another one
	 * 
	 * @param other - The other sublevel to overlap with
	 * @param s - the direction of the split between the sublevels
	 * @return - the range of values that the subregions overlap in
	 */
	public Range overLap(SubLevel other, Split s) {


		int smallestMax;
		int largestMin;
		if(s == Split.HOR) {

			if (!(this.intersectMin.x <= other.intersectMax.x && 
					this.intersectMax.x >= this.intersectMin.x)) 
			{
				return null;
			}


			smallestMax = Math.min(this.max.x, other.max.x);
			largestMin = Math.max(this.min.x, other.min.x);
		}
		else { //VER

			if (!(this.intersectMin.y <= other.intersectMax.y && 
					this.intersectMax.y >= this.intersectMin.y)) 
			{
				return null;
			}

			smallestMax = Math.min(this.max.y, other.max.y);
			largestMin = Math.max(this.min.y, other.min.y);
		}
		return new Range(largestMin,smallestMax);
	}

	/**
	 * Finds the point to connect a hallway to
	 * @param s - the split direction seperating the sublevels
	 * @param maximum - true if we want the room with max x/y, false otherwise
	 * @return - the point to connect a hallway to
	 */
	public HallwayPoint getHallwayPoint(Split s, boolean maximum, int value) {
		Space bestSpace = null;
		Vec2i bestPoint = null;
		
		if(s == Split.HOR) {
			//Find point with (value,f y)

			if(maximum) {
				int max_val = Integer.MIN_VALUE;
				for(Room r : rooms) {
					if(r.max.x >= value && r.min.x <= value) {
						if(r.max.y > max_val) {
							max_val = r.max.y;
							bestSpace = r;
							bestPoint = new Vec2i(value,max_val);
						}
					}
				}
				for(Hallway h : hallways) {
					if(h.startTile.x <= value && h.endTile.x >= value) {
						if(h.startTile.y > max_val) {
							max_val = h.startTile.y;
							bestSpace = h;
							bestPoint = new Vec2i(value,max_val);
						}
					}
				}
			}

			else{ //(minumum) 
				int min_val = Integer.MAX_VALUE;
				for(Room r : rooms) {
					if(r.max.x >= value && r.min.x <= value) {
						if(r.max.y < min_val) {
							min_val = r.max.y;
							bestSpace = r;
							bestPoint = new Vec2i(value,min_val);
						}
					}
				}
				for(Hallway h : hallways) {
					if(h.startTile.x <= value && h.endTile.x >= value) {
						if(h.startTile.y < min_val) {
							min_val = h.startTile.y;
							bestSpace = h;
							bestPoint = new Vec2i(value,min_val);
						}
					}
				}
			}
		}
		//-------------------------------VERTICAL --------------------------------------
		else { //VER
			if(maximum) {
				int max_val = Integer.MIN_VALUE;
				for(Room r : rooms) {
					if(r.max.y >= value && r.min.y <= value) {
						if(r.max.x > max_val) {
							max_val = r.max.x;
							bestSpace = r;
							bestPoint = new Vec2i(max_val,value);
						}
					}
				}
				for(Hallway h : hallways) {
					if(h.startTile.y <= value && h.endTile.y >= value) {
						if(h.startTile.x > max_val) {
							max_val = h.startTile.x;
							bestSpace = h;
							bestPoint = new Vec2i(max_val,value);
						}
					}
				}
			}

			else{ //(minumum) 
				int min_val = Integer.MAX_VALUE;
				for(Room r : rooms) {
					if(r.max.y >= value && r.min.y <= value) {
						if(r.max.x < min_val) {
							min_val = r.max.x;
							bestSpace = r;
							bestPoint = new Vec2i(min_val,value);
						}
					}
				}
				for(Hallway h : hallways) {
					if(h.startTile.y <= value && h.endTile.y >= value) {
						if(h.startTile.x < min_val) {
							min_val = h.startTile.x;
							bestSpace = h;
							bestPoint = new Vec2i(min_val,value);
						}
					}
				}
			}
		}
		
		return new HallwayPoint(bestPoint,bestSpace);

	}

}