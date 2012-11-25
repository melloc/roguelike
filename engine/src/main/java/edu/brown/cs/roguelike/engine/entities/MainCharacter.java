package edu.brown.cs.roguelike.engine.entities;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.lanterna.terminal.Terminal.Color;

public class MainCharacter extends Combatable {

	List<String> categories = null;
	{
		categories = new ArrayList<String>();
		categories.add("keyboard");
		categories.add("main");
	}

	public MainCharacter() {
		this.character = '@';
		this.color = Color.DEFAULT;
		this.HP = 100;
		this.stats = new Stats(.75f,6,2);
		this.team = 1;
	}

	@Override
	protected void die() {
		this.location.setEntity(null);
		this.manager.call(Event.DEATH);
	}

	@Override
	protected void onKillEntity(Combatable combatable) {
		// TODO Auto-generated method stub
		
	}

	public List<String> getCategories() {
		return categories;
	}


}
