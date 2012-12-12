package edu.brown.cs.roguelike.engine.entities;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.lanterna.terminal.Terminal.Color;

import edu.brown.cs.roguelike.engine.config.MonsterTemplate;
import edu.brown.cs.roguelike.engine.entities.events.Wait;
import edu.brown.cs.roguelike.engine.fsm.FSM;
import edu.brown.cs.roguelike.engine.fsm.monster.Chasing;
import edu.brown.cs.roguelike.engine.fsm.monster.Idle;
import edu.brown.cs.roguelike.engine.fsm.monster.MonsterInput;
import edu.brown.cs.roguelike.engine.fsm.monster.PlayerInSpace;
import edu.brown.cs.roguelike.engine.fsm.monster.PlayerNotInSpace;
import edu.brown.cs.roguelike.engine.level.Level;

public class Monster extends Combatable {

	/**
	 * Generated
	 */
	private static final long serialVersionUID = 78534419381534560L;
	
	// The Level I'm in
	private Level level;
	private final int moveCost;
	private Action nextAction;
	
	// My brainz
	private FSM<MonsterInput> brainz;
	
	List<String> categories = null;
	{
		categories = new ArrayList<String>();
		categories.add("monster");
	}

	public Monster(char c, Color color, Level level) {
		this.character = c;
		this.color = color;
		nextAction = null;
		this.moveCost = 10;
		buildBrainz();
	}
	
	public Monster(MonsterTemplate mt, Level level) {
		this.level = level;
		this.character = mt.character;
		this.color = mt.color;
		this.HP = mt.startHp;
		this.startHP = mt.startHp;
		this.stats = new Stats(mt.attack,mt.defense); 
		baseStats = stats;
		this.team = 0;
		this.name = mt.name;
		this.moveCost = mt.moveCost;
		buildBrainz();
	}
	
	private void buildBrainz() {
		brainz = new FSM<MonsterInput>();
		
		Idle idleState = new Idle(this);
		Chasing chaseState = new Chasing(this, moveCost);
		
		PlayerInSpace chasePlayer = new PlayerInSpace(chaseState, this);
		// PlayerNotInSpace giveUp = new PlayerNotInSpace(idleState, this);
		
		idleState.addTransition(chasePlayer);
		// chaseState.addTransition(giveUp);
		
		brainz.setStartState(idleState);
	}

	@Override
	protected void die() {
		this.location.setEntity(null);
		this.manager.call(Event.DEATH);
	}

	@Override
	protected void onKillEntity(Combatable combatable) {
		// TODO Auto-generated method stub
		
	}

	public List<String> getCategories() {
		return categories;
	}

	@Override
	public String getDescription() {
		return name;
	}
	
	public Level getLevel() { return this.level; }
	
	public void setNextAction(Action nextAction) {
		this.nextAction = nextAction;
	}
	
	/**
	 * TODO: Make FSM responsible for generating next actions
	 */
	@Override
	protected Action generateNextAction() {
		brainz.update(new MonsterInput(level));
		return this.nextAction;
	}
}
