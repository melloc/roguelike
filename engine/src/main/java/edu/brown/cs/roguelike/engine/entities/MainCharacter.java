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
	
	List<String> categories = null;
	{
		categories = new ArrayList<String>();
		categories.add("keyboard");
		categories.add("main");
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
