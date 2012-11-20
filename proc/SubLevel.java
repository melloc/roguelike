package jte.engine.proc;

import java.util.ArrayList;

import cs195n.Vec2i;

public class SubLevel {
	public Vec2i min;
	public Vec2i max;
	public int depth;
	
	public SubLevel(Vec2i min, Vec2i max) {
		this.min = min;
		this.max = max;
		depth = 0;
	}
	
	public ArrayList<Room> rooms = new ArrayList<Room>();
	public ArrayList<Hallway> hallways = new ArrayList<Hallway>();
	
}
