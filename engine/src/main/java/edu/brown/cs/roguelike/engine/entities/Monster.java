package edu.brown.cs.roguelike.engine.entities;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.lanterna.terminal.Terminal.Color;

import cs195n.Vec2i;

import edu.brown.cs.roguelike.engine.level.Tile;

public class Monster extends Combatable {

	List<String> categories = null;
	{
		categories = new ArrayList<String>();
		categories.add("monster");
	}

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

	public Tile getLocation() {
		return null;
	}

	public List<String> getCategories() {
		return categories;
	}
}
