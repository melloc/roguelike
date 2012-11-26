package edu.brown.cs.roguelike.engine.entities;

import edu.brown.cs.roguelike.engine.save.IDManager;
import edu.brown.cs.roguelike.engine.save.Saveable;

/**The Stats of a Combatable Entity. Affects player combat**/
public class Stats implements Saveable {
	
	/**
	 * Generated
	 */
	private static final long serialVersionUID = -3044267314709997781L;
	
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
	
	/*** BEGIN Saveable ***/
	
	private long id;
	
	/** initialize id **/
	{
		this.id = IDManager.getNext();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stats other = (Stats) obj;
		if (id == other.id) return true;
		return false;
	}

	@Override
	public long getId() {
		return this.id;
	}
	
	/*** END Saveable ***/

}
