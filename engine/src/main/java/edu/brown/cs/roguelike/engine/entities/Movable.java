package edu.brown.cs.roguelike.engine.entities;


import edu.brown.cs.roguelike.engine.level.Direction;
import edu.brown.cs.roguelike.engine.level.Mappable;
import edu.brown.cs.roguelike.engine.level.Tile;

public interface Movable extends Mappable {

	public void move(Direction dir);
	public void moveTo(Tile tile);

}
