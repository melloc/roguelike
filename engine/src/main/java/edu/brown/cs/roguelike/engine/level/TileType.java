package edu.brown.cs.roguelike.engine.level;

import com.googlecode.lanterna.terminal.Terminal.Color;

import edu.brown.cs.roguelike.engine.graphics.Drawable;

public enum TileType implements Drawable {
	WALL_HOR('-',Color.DEFAULT, false, "A rough cave wall"),
	WALL_VER('|',Color.DEFAULT, false, "A rough cave wall"),
	SOLID(' ',Color.DEFAULT, false, null),
	DOOR('+',Color.DEFAULT, true, "A door"),
	UP_STAIRS('<',Color.DEFAULT, true, "Stairs leading upward"),
	DOWN_STAIRS('>',Color.DEFAULT, true, "Stairs leading downward"),
	FLOOR('.',Color.DEFAULT, true, null), 
	HIDDEN(' ', Color.DEFAULT, true, null);

	protected char character;
	protected Color color;
	protected boolean passable;
	protected String description;

	TileType(char character, Color color, boolean passable, String description) {
		this.character = character;
		this.color = color;
		this.passable = passable;
		this.description = description;
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

	public String getDescription() {
		return description;
	}
}
