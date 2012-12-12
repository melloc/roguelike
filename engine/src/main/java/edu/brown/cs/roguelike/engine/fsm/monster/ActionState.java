package edu.brown.cs.roguelike.engine.fsm.monster;

import edu.brown.cs.roguelike.engine.entities.Action;
import edu.brown.cs.roguelike.engine.fsm.Input;
import edu.brown.cs.roguelike.engine.fsm.State;

public abstract class ActionState<I extends Input> extends State<I> {

	/**
	 * Generated
	 */
	private static final long serialVersionUID = 5601174407092298219L;
	
	
	public abstract Action getAction();
	
	

}
