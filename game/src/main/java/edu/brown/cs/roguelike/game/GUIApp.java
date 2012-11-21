package edu.brown.cs.roguelike.game;

import cs195n.Vec2i;
import edu.brown.cs.roguelike.engine.graphics.Application;

public class GUIApp extends Application {

	private final static Vec2i SCREEN_SIZE = new Vec2i(80,30);
	
	public GUIApp() {
		super("demo");
	}

	@Override
	protected boolean initialize(Vec2i screenSize) {
		this.layers.push(new MainLayer(this, SCREEN_SIZE));
		return true;
	}

}
