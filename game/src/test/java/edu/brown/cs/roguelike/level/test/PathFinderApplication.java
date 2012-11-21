package edu.brown.cs.roguelike.level.test;

import cs195n.Vec2i;
import edu.brown.cs.roguelike.engine.graphics.Application;

public class PathFinderApplication extends Application {

	public PathFinderApplication() {
		super("demo");
	}

	@Override
	protected boolean initialize(Vec2i screenSize) {
		this.layers.push(new PathTestLayer(this, screenSize));
		return true;
	}

}
