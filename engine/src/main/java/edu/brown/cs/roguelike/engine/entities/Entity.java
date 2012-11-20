package edu.brown.cs.roguelike.engine.entities;

import java.util.List;

import com.googlecode.lanterna.terminal.Terminal.Color;

import edu.brown.cs.roguelike.engine.graphics.Drawable;
import edu.brown.cs.roguelike.engine.level.Mappable;

public abstract class Entity implements Drawable, Mappable {

	protected char character;
	protected Color color;

	@Override
	public char getCharacter() {
		return character;
	}

	@Override
	public Color getColor() {
		return color;
	}

	public abstract List<String> getCategories();

}
