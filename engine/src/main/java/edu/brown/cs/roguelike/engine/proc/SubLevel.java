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

			/*
			System.out.println("HOR");
			System.out.println(this.intersectMin.x + " " + other.intersectMax.x );
			System.out.println(this.intersectMax.x + " " + other.intersectMin.x );
			System.out.println(" ");
			 */

			if (!(this.intersectMin.x <= other.intersectMax.x && 
					this.intersectMax.x >= other.intersectMin.x)) 
			{
				return null;
			}


			smallestMax = Math.min(this.intersectMax.x, other.intersectMax.x);
			largestMin = Math.max(this.intersectMin.x, other.intersectMin.x);
		}
		else { //VER

			/*
			System.out.println("VER");
			System.out.println(this.intersectMin.y + " " + other.intersectMax.y );
			System.out.println(this.intersectMax.y + " " + other.intersectMin.y );
			System.out.println(" ");
			*/


			if (!(this.intersectMin.y <= other.intersectMax.y && 
					this.intersectMax.y >= other.intersectMin.y)) 
			{
				return null;
			}

			smallestMax = Math.min(this.intersectMax.y, other.intersectMax.y);
			largestMin = Math.max(this.intersectMin.y, other.intersectMin.y);
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
					if(h.startTile.x <= value && h.endTile.x >= value || h.startTile.x >= value && h.endTile.x <= value) {
						if(h.startTile.y > max_val) {
							max_val = h.startTile.y;
							bestSpace = h;
							bestPoint = new Vec2i(value,max_val);
						}
						else if(h.endTile.y > max_val) {
							max_val = h.endTile.y;
							bestSpace = h;
							bestPoint = new Vec2i(value,max_val);
						}
					}
				}
			}

			else{ //(minimum) 
				int min_val = Integer.MAX_VALUE;
				for(Room r : rooms) {
					if(r.max.x >= value && r.min.x <= value) {
						if(r.min.y < min_val) {
							min_val = r.min.y;
							bestSpace = r;
							bestPoint = new Vec2i(value,min_val);
						}
					}
				}
				for(Hallway h : hallways) {
					if(h.startTile.x <= value && h.endTile.x >= value || h.startTile.x >= value && h.endTile.x <= value) {
						if(h.startTile.y < min_val) {
							min_val = h.startTile.y;
							bestSpace = h;
							bestPoint = new Vec2i(value,min_val);
						}
						else if(h.endTile.y < min_val) {
							min_val = h.endTile.y;
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
					if(h.startTile.y <= value && h.endTile.y >= value || h.startTile.y >= value && h.endTile.y <= value) {
						if(h.startTile.x > max_val) {
							max_val = h.startTile.x;
							bestSpace = h;
							bestPoint = new Vec2i(max_val,value);
						}
						else if(h.endTile.x > max_val) {
							max_val = h.endTile.x;
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
						if(r.min.x < min_val) {
							min_val = r.min.x;
							bestSpace = r;
							bestPoint = new Vec2i(min_val,value);
						}
					}
				}
				for(Hallway h : hallways) {
					if(h.startTile.y <= value && h.endTile.y >= value || h.startTile.y >= value && h.endTile.y <= value) {
						if(h.startTile.x < min_val) {
							min_val = h.startTile.x;
							bestSpace = h;
							bestPoint = new Vec2i(min_val,value);
						}
						else if(h.endTile.x < min_val) {
							min_val = h.endTile.x;
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
