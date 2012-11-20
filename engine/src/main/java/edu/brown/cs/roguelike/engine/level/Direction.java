package edu.brown.cs.roguelike.engine.level;

import cs195n.Vec2i;

public enum Direction {
	LEFT(-1, 0), RIGHT(1, 0), UP(0, -1), DOWN(0, 1);

	protected Vec2i delta = null;

	Direction(int x, int y) {
		delta = new Vec2i(x, y);
	}

	/**
	 * @return the delta on the map to get to our new tile.
	 */
	public Vec2i getDelta() {
		return delta;
	}
}
