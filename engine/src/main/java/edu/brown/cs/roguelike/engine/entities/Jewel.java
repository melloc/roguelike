package edu.brown.cs.roguelike.engine.entities;

import com.googlecode.lanterna.terminal.Terminal.Color;

public class Jewel extends Stackable {
	public Jewel() {
		this.character = '$';
		this.color = Color.CYAN;
	}
	
	public String getDescription() {
		return "A pretty jewel";
	}
	
}
