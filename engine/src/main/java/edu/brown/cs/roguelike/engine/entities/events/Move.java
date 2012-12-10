package edu.brown.cs.roguelike.engine.entities.events;

import edu.brown.cs.roguelike.engine.entities.Action;
import edu.brown.cs.roguelike.engine.entities.Combatable;
import edu.brown.cs.roguelike.engine.entities.EntityActionManager;
import edu.brown.cs.roguelike.engine.level.Direction;

/**
 * Move a Combatable in a particular direction
 * 
 * @author lelberty
 *
 * @param <C> the class of the Combatable
 */
public class Move extends Action {

	/**
	 * Generated
	 */
	private static final long serialVersionUID = -7662560441748878059L;

	private Combatable c;
	private final Direction dir;
	
	public Move(int cost, Combatable combatable , Direction dir) {
		super(cost);
		this.c= combatable;
		this.dir = dir;
	}
	
	@Override
	public void apply(EntityActionManager queue) {
		c.move(dir);
	}
	
}
