package edu.brown.cs.roguelike.engine.entities;

import com.googlecode.lanterna.terminal.Terminal.Color;

import edu.brown.cs.roguelike.engine.entities.events.ChangeStats;

public class Weapon extends Stackable{

	/**
	 * Generated
	 */
	private static final long serialVersionUID = -5817532170958350132L;

	protected Stats stats;
	private final Action wieldAction;
	private final Action unwieldAction;
	private String description;
	private DamageType damageType;

	
	public Weapon(Stats stats, String description, DamageType dt) {
		this.stats = stats;
		wieldAction = new ChangeStats(stats);
		unwieldAction = new ChangeStats(stats.invert());
		color = Color.WHITE;
		character = ')';
		type = ItemType.WEAPON;
		this.description = description;
		this.damageType = dt;
	}
	
	public Stats getStats() {
		return stats;
	}
	
	@Override
	public String getDescription() {
		return description;
	}

	public DamageType getDamageType() {return damageType;}
	
	public Action getWieldAction() {
		return wieldAction;
	}
	public Action getUnwieldAction() {
		return unwieldAction;
	}

}
