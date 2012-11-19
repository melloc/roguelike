package edu.brown.cs.roguelike.engine.entities;

import com.googlecode.lanterna.terminal.Terminal.Color;

public abstract class Entity {
	protected String character;
	protected Color color;
	
	public String getCharacter() {return character;}
	public Color getColor() {return color;}
	
}
