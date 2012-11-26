package edu.brown.cs.roguelike.engine.entities;

import com.googlecode.lanterna.terminal.Terminal.Color;

import edu.brown.cs.roguelike.engine.graphics.Drawable;

/**
 * Anything that can be on a tile in a "pile"
 * Any number of stackables can be on a tile, along with an entity
 * 
 * @author Jake
 *
 */

public abstract class Stackable implements Drawable {
	protected char character;
	protected Color color;
	
	public char getCharacter() {return character;}
	public Color getColor() {return color;}
	
	public abstract String getDescription();
}
