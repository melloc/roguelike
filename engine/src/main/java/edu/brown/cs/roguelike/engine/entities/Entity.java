package edu.brown.cs.roguelike.engine.entities;

import java.util.List;

import com.googlecode.lanterna.terminal.Terminal.Color;

import edu.brown.cs.roguelike.engine.graphics.Drawable;
import edu.brown.cs.roguelike.engine.level.Tile;

public abstract class Entity implements Drawable, Mappable {

	protected char character;
	protected Color color;
	protected Tile location;

	@Override
	public char getCharacter() {
		return character;
	}

	@Override
	public Color getColor() {
		return color;
	}

	public abstract List<String> getCategories();

	/**
	 * @return the location
	 */
	@Override
	public Tile getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(Tile location) {
		this.location = location;
	}
	
}
