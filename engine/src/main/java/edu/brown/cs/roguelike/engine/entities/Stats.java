package edu.brown.cs.roguelike.engine.entities;

/**The Stats of a Combatable Entity. Affects player combat**/
public class Stats {
	protected float hitChance; //Chance to hit [0-1]
	protected int   attack;	   //Attack power
	protected int	defense;   //Defense toughness

	public Stats(float hc, int atk, int def) {
		this.hitChance = hc;
		this.attack = atk;
		this.defense = def;
	}
	
	public int getAttack() 	 {return attack;}
	public int getDefense()  {return defense;}
	public float getHitChance() {return hitChance;}
}
