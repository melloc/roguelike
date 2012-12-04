package edu.brown.cs.roguelike.engine.entities;


import cs195n.Vec2i;
import edu.brown.cs.roguelike.engine.level.Direction;
import edu.brown.cs.roguelike.engine.level.Tile;

public interface EntityActionManager {

	public Combatable getEntity();

	public Tile getLocation();
	public Stats getStats();
	
	public void on(Event e, Action callback);
	public void call(Event e);

	public void sendMove(Direction dir);
	public void changeHP(int delta);
	public void toggleVisibility();
	public void changeStats(Stats delta);
	// public void modify(???);
	// public void timed(???);


}
