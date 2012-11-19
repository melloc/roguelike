package edu.brown.cs.roguelike.engine.entities;

import com.googlecode.lanterna.terminal.Terminal.Color;

public class Monster extends Combatable {
	public Monster(char c, Color color) {
		this.character = c;
		this.color = color;
	}

	@Override
	protected void die() {
		// TODO Have the monster die and remove its references
	}

	@Override
	protected void onKillEntity(Combatable combatable) {
		// TODO Auto-generated method stub
		
	}
}
