package edu.brown.cs.roguelike.engine.level;

import com.googlecode.lanterna.terminal.Terminal.Color;

import edu.brown.cs.roguelike.engine.graphics.Drawable;

public enum TileType implements Drawable {
	WALL_HOR('-',Color.DEFAULT),
	WALL_VER('|',Color.DEFAULT),
	SOLID(' ',Color.DEFAULT),
	DOOR('+',Color.DEFAULT),
	FLOOR('.',Color.DEFAULT);

	protected char character;
	protected Color color;

	TileType(char character, Color color) {
		this.character = character;
		this.color = color;
	}

	/**
	 * @return The character that represents this tile type 
	 */
	public char getCharacter() {
		return character;
	}

	/**
	 * @return The color that this tile has.
	 */
	public Color getColor() {
		return color;
	}
}
