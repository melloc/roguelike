package edu.brown.cs.roguelike.engine.entities;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.lanterna.terminal.Terminal.Color;

import edu.brown.cs.roguelike.engine.level.Direction;

public class MainCharacter extends Combatable {

	/**
	 * Generated 
	 */
	private static final long serialVersionUID = -4240615722468534343L;
	
	List<String> categories = null;
	{
		categories = new ArrayList<String>();
		categories.add("keyboard");
		categories.add("main");
	}

	public MainCharacter() {
		this.character = '@';
		this.color = Color.DEFAULT;
		this.HP = 100;
		this.startHP = this.HP;
		this.stats = new Stats(.75f,10,4);
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
		this.inventory.addAll(this.location.getStackables());
		this.location.getStackables().clear();
	}

	
}
