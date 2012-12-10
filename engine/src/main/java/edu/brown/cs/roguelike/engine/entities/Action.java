package edu.brown.cs.roguelike.engine.entities;

import java.util.UUID;

import edu.brown.cs.roguelike.engine.save.Saveable;

public abstract class Action implements Saveable {
	
	/**
	 * Generated
	 */
	private static final long serialVersionUID = 786756738941365502L;
	
	/**
	 * The cost of taking this Action
	 */
	private int cost;
	
	public Action(int cost) {
		this.cost = cost;
	}
	
	public void setCost(int cost) {
		this.cost = cost;
	}
	
	public int getCost() { return this.cost; }

	public abstract void apply(EntityActionManager queue);
	
	/*** BEGIN Saveable ***/

	private UUID id;
	
	/** initialize id **/
	{
		this.id = UUID.randomUUID();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Action other = (Action) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (id.equals(other.id))
			// return true if ids are the same
			return true;
		return false;
	}

	@Override
	public UUID getId() {
		return this.id;
	}
	
	/*** END Saveable ***/
}
