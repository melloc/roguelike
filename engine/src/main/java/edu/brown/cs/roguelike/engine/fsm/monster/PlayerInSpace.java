package edu.brown.cs.roguelike.engine.fsm.monster;

import edu.brown.cs.roguelike.engine.entities.EntityActionManager;
import edu.brown.cs.roguelike.engine.entities.Monster;
import edu.brown.cs.roguelike.engine.fsm.State;
import edu.brown.cs.roguelike.engine.fsm.Transition;
import edu.brown.cs.roguelike.engine.level.Level;
import edu.brown.cs.roguelike.engine.level.Space;

public class PlayerInSpace extends Transition<MonsterInput> {
	
	/**
	 * Generated
	 */
	private static final long serialVersionUID = -1305253493331919281L;
	
	private Monster me;

	public PlayerInSpace(State<MonsterInput> targetState, Monster me) {
		super(targetState);
		this.me = me;
	}

	@Override
	public boolean isReady(MonsterInput i) {
		
		Level l = i.getLevel();
		
		EntityActionManager player = 
				l.getManager().getEntity("main").get(0);
		
		Space mySpace = me.getLocation().getSpace();
		Space playerSpace = player.getEntity().getLocation().getSpace();
		
		return (mySpace.equals(playerSpace));
	}

}
