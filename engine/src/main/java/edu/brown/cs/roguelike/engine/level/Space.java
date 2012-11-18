package edu.brown.cs.roguelike.engine.level;

/**
 * An open space that entities can occupy, namely hallways and rooms
 * @author jte
 *
 */
public interface Space {
	/**Connects the space to the hallway**/
	public void connectToHallway(Hallway h);
	
	/**Whether or not the connection needs a door (false for hallway, true for room)**/
	public boolean needDoor(); 
}
