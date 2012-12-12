package edu.brown.cs.roguelike.engine.entities;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.lanterna.terminal.Terminal.Color;

import edu.brown.cs.roguelike.engine.game.Announcer;
import edu.brown.cs.roguelike.engine.level.Direction;

public class MainCharacter extends Combatable {

	/**
	 * Generated 
	 */
	private static final long serialVersionUID = -4240615722468534343L;
	private static final int MAX_INVENTORY_SIZE = 26;
	private static final float EXP_CURVE = 2.2f;
	
	List<String> categories = null;
	private int XP;
	private int nextLevelXP;
	private int playerLevel;
	
	{
		categories = new ArrayList<String>();
		categories.add("keyboard");
		categories.add("main");
	}

	public int getXP() {
		return XP;
	} 
	
	public int getNextLevelXP() {
		return nextLevelXP;
	}

	public int getPlayerLevel() {
		return playerLevel;
	}

	
	public MainCharacter(String name) {
		this.name = name;
		this.color = Color.DEFAULT;
		this.character = '@';
		this.HP = 100;
		this.startHP = this.HP;
		this.stats = new Stats(10,4);
		baseStats = stats;
		this.team = 1;
		this.XP = 0;
		nextLevelXP = 6;
		playerLevel = 1;
	}

	@Override
	protected void die() {
		this.location.setEntity(null);
		this.manager.call(Event.DEATH);
	}

	@Override
	protected void onKillEntity(Combatable combatable) {
		Announcer.announce("You defeated the " + combatable.getDescription());
		Monster m  = (Monster) combatable;
		this.XP += m.tier;
		if(XP >= nextLevelXP) {
			XP -=nextLevelXP;
			levelUp();
			Announcer.announce("Welcome to level " + playerLevel);
		}
	}

	
	private final int HP_GROWTH = 10;
	private final int ATTACK_GROWTH = 3;
	private final int DEFENSE_GROWTH = 1;

	private void levelUp() {
		playerLevel++;
		this.startHP += HP_GROWTH;
		this.HP = startHP;
		this.baseStats = new Stats(baseStats.attack+ATTACK_GROWTH, baseStats.defense+DEFENSE_GROWTH);
		this.stats = new Stats(stats.attack+ATTACK_GROWTH, stats.defense+DEFENSE_GROWTH);
		nextLevelXP =  (int) Math.ceil(nextLevelXP*EXP_CURVE);
	}

	public List<String> getCategories() {
		return categories;
	}
	
	@Override 
	public void move(Direction dir) {
		super.move(dir);
		
		while(inventory.size() < MAX_INVENTORY_SIZE && this.location.getStackables().size()>0) {
			Stackable s = this.location.getStackables().remove();
			this.inventory.add(s);
			Announcer.announce(s.getDescription());
		}
		if(this.getLocation().getStackables().size() > 0) {
			Announcer.announce("Not enough room in inventory.");
		}
		
	}

	@Override
	public String getDescription() {
		return "You";
	}
	
	/**
	 * Main Character's next action is dependent on user input, and thus cannot
	 * generate a next action
	 */
	@Override
	protected Action generateNextAction() { return null; }


	
}
