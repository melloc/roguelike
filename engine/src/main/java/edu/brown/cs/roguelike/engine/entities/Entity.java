package edu.brown.cs.roguelike.engine.entities;

import com.googlecode.lanterna.terminal.Terminal.Color;

import edu.brown.cs.roguelike.engine.graphics.Drawable;

public abstract class Entity implements Drawable {
	protected char character;
	protected Color color;
	
	public char getCharacter() {return character;}
	public Color getColor() {return color;}
	
}
