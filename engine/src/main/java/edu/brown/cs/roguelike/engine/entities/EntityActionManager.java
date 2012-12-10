package edu.brown.cs.roguelike.engine.entities;

import edu.brown.cs.roguelike.engine.level.Direction;
import edu.brown.cs.roguelike.engine.level.Tile;

public interface EntityActionManager {

	public Combatable getEntity();

	public Tile getLocation();
	public Stats getStats();
	
	public int getActionPoints();
	public void useActionPoints(int ap);
	public void addActionPoints(int ap);
	
	public void on(Event e, Action callback);
	public void call(Event e);
	
	public Action getNextAction();
	public void takeNextAction();
	public void setNextAction(Action nextAction);
	public boolean hasNextAction();

	public void sendMove(Direction dir);
	public void changeHP(int delta);
	public void toggleVisibility();
	public void changeStats(Stats delta);
	// public void modify(???);
	// public void timed(???);

}
