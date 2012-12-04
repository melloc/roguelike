package edu.brown.cs.roguelike.engine.entities;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.lanterna.terminal.Terminal.Color;

import edu.brown.cs.roguelike.engine.config.MonsterTemplate;

public class Monster extends Combatable {

	/**
	 * Generated
	 */
	private static final long serialVersionUID = 78534419381534560L;
	
	List<String> categories = null;
	{
		categories = new ArrayList<String>();
		categories.add("monster");
	}

	private String name;

	public Monster(char c, Color color) {
		this.character = c;
		this.color = color;
	}
	
	public Monster(MonsterTemplate mt) {
		this.character = mt.character;
		this.color = mt.color;
		this.HP = mt.startHp;
		this.startHP = mt.startHp;
		this.stats = new Stats(.75f,mt.attack,mt.defense); 
		baseStats = stats;
		this.team = 0;
		this.name = mt.name;
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
	
}
