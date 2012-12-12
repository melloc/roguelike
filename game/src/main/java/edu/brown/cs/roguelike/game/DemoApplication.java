package edu.brown.cs.roguelike.game;

import cs195n.Vec2i;
import edu.brown.cs.roguelike.engine.graphics.Application;

public class DemoApplication extends Application {

	public DemoApplication() {
		super("demo");
	}

	@Override
	protected boolean initialize(Vec2i screenSize) {
		this.layers.push(new DemoLayer(this, screenSize));
		return true;
	}

	@Override
	public Vec2i getSize() {
		return new Vec2i(80,30);
	}

	@Override
	public void deleteSaveFile() {
		// TODO Auto-generated method stub
		
	}
}
