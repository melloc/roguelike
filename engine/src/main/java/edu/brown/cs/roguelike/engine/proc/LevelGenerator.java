package edu.brown.cs.roguelike.engine.proc;

import cs195n.Vec2i;
import edu.brown.cs.roguelike.engine.level.Level;

/**
 * Interface for any level generator
 * @author jte
 *
 */
public interface LevelGenerator {
	
	/**Generates a level whose size is levelSize**/
	public Level generateLevel(Vec2i levelSize);
}
