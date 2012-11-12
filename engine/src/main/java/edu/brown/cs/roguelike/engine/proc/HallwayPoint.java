package edu.brown.cs.roguelike.engine.proc;

import cs195n.Vec2i;
import edu.brown.cs.roguelike.engine.level.Space;

/**Holder for a coordinate point and the Space the point belongs to**/

public class HallwayPoint {
	public final Space space;
	public final Vec2i point;
	
	public HallwayPoint(Vec2i point, Space space) {
		this.space = space;
		this.point = point;
	}
}
