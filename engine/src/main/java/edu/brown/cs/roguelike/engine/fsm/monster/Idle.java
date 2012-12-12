package edu.brown.cs.roguelike.engine.fsm.monster;

import edu.brown.cs.roguelike.engine.entities.Action;
import edu.brown.cs.roguelike.engine.entities.Monster;
import edu.brown.cs.roguelike.engine.entities.events.Wait;
import edu.brown.cs.roguelike.engine.fsm.State;

public class Idle extends State<MonsterInput> {

	/**
	 * Generated
	 */
	private static final long serialVersionUID = 8648494326971032065L;
	
	private final Monster me;
	
	public Idle(Monster me) {
		this.me = me;
	}

	@Override
	public void onActive() {
		me.setNextAction(getAction());
	}

	@Override
	public void onEnter() {
		//me.setNextAction(getAction());
	}

	@Override
	public void onExit() {}
	
	private Action getAction() {
		return new Wait();
	}
	
}
