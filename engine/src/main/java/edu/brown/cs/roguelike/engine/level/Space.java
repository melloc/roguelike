package edu.brown.cs.roguelike.engine.level;

/**
 * An open space that entities can occupy, namely hallways and rooms
 * @author jte
 *
 */
public interface Space {
	public void connectToSpace(Space s);
	public void connectToRoom(Room r);
	public void connectToHallway(Hallway h);
}
