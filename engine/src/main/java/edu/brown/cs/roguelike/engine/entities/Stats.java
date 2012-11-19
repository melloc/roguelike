package edu.brown.cs.roguelike.engine.entities;

public class Stats {
	protected float hitChance; //Chance to hit [0-1]
	protected int   attack;	   //Attack power
	protected int	defense;   //Defense toughness
	
	public int getAttack() 	 {return attack;}
	public int getDefense()  {return defense;}
	public float getHitChance() {return hitChance;}
}
