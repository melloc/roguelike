package edu.brown.cs.roguelike.engine.level;

import com.googlecode.lanterna.terminal.Terminal.Color;

import edu.brown.cs.roguelike.engine.graphics.Drawable;

public enum TileType implements Drawable {
	WALL_HOR('-',Color.DEFAULT, false),
	WALL_VER('|',Color.DEFAULT, false),
	SOLID(' ',Color.DEFAULT, false),
	DOOR('+',Color.DEFAULT, true),
	UP_STAIRS('<',Color.DEFAULT, true),
	DOWN_STAIRS('>',Color.DEFAULT, true),
	FLOOR('.',Color.DEFAULT, true);

	protected char character;
	protected Color color;
	protected boolean passable;

	TileType(char character, Color color, boolean passable) {
		this.character = character;
		this.color = color;
		this.passable = passable;
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

	public boolean isPassable() {
		return passable;
	}
}
