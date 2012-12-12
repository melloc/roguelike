package edu.brown.cs.roguelike.engine.entities;

import com.googlecode.lanterna.terminal.Terminal.Color;

public class Potion extends Stackable {
	
	/**
	 * Generated
	 */
	
	private static final long serialVersionUID = 290618014677296808L;
	
	
	public final Action quaffAction;
	
	
	public Potion(Color c, Action quaffAction) {
		this.quaffAction = quaffAction;
		character = '!';
		color = c;
		type = ItemType.POTION;
	}
	
	@Override
	public String getDescription() {
		return color +" potion";
	}

	public Action getQuaffAction() {
		return quaffAction;
	}

}
