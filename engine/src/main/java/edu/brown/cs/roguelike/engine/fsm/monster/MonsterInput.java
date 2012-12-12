package edu.brown.cs.roguelike.engine.fsm.monster;

import edu.brown.cs.roguelike.engine.fsm.Input;
import edu.brown.cs.roguelike.engine.level.Level;

public class MonsterInput implements Input {
	
	private Level level;

	public MonsterInput(Level level) {
		this.level = level;
	}
	
	public Level getLevel() { return this.level; }

}
