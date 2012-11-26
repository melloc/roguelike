package edu.brown.cs.roguelike.engine.entities.events;

import edu.brown.cs.roguelike.engine.entities.Action;
import edu.brown.cs.roguelike.engine.entities.Combatable;
import edu.brown.cs.roguelike.engine.entities.EntityActionManager;
import edu.brown.cs.roguelike.engine.entities.EntityManager;
import edu.brown.cs.roguelike.engine.entities.MainCharacter;
import edu.brown.cs.roguelike.engine.save.IDManager;
import edu.brown.cs.roguelike.engine.save.Saveable;

public class RemoveOnDeath implements Action, Saveable {

	/**
	 * Generated
	 */
	private static final long serialVersionUID = -7692501119493771815L;
	
	Combatable entity = null;
	EntityManager manager = null;

	public RemoveOnDeath(Combatable entity, EntityManager manager) {
		this.entity = entity;
		this.manager = manager;
	}

	public void apply(EntityActionManager queue) {
		this.manager.unregister(this.entity);
	}
	
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
		RemoveOnDeath other = (RemoveOnDeath) obj;
		if (id == other.id) return true;
		return false;
	}

	@Override
	public long getId() {
		return this.id;
	}
	
	/*** END Saveable ***/
			
	
}
