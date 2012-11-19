package edu.brown.cs.roguelike.engine.entities;

import com.googlecode.lanterna.terminal.Terminal.Color;

import edu.brown.cs.roguelike.engine.config.MonsterTemplate;

public class Monster extends Combatable {
	public Monster(char c, Color color) {
		this.character = c;
		this.color = color;
	}
	
	public Monster(MonsterTemplate mt) {
		this.character = mt.character;
		this.color = mt.color;
		this.HP = mt.startHp;
		this.stats = new Stats(.75f,mt.attack,mt.defense); 
		//TODO: Hit chance varies between monsters
	}
	

	@Override
	protected void die() {
		// TODO Have the monster die and remove its references
	}

	@Override
	protected void onKillEntity(Combatable combatable) {
		// TODO Auto-generated method stub
		
	}
}
