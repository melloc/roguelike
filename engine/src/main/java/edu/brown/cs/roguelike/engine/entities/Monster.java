package edu.brown.cs.roguelike.engine.entities;

import com.googlecode.lanterna.terminal.Terminal.Color;

public class Monster extends Entity {
	public Monster(char c, Color color) {
		this.character = c;
		this.color = color;
	}
}
