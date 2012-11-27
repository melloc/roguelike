package edu.brown.cs.roguelike.engine.entities;

import com.googlecode.lanterna.terminal.Terminal.Color;

import edu.brown.cs.roguelike.engine.save.Saveable;

public class Jewel extends Stackable implements Saveable {
	public Jewel() {
		this.character = '$';
		this.color = Color.CYAN;
	}
	
	public String getDescription() {
		return "A pretty jewel";
	}
	
	
	
}
