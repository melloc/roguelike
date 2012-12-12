package edu.brown.cs.roguelike.engine.entities.events;

import java.util.UUID;

import edu.brown.cs.roguelike.engine.entities.Action;
import edu.brown.cs.roguelike.engine.entities.EntityActionManager;
import edu.brown.cs.roguelike.engine.entities.EntityManager;
import edu.brown.cs.roguelike.engine.game.Announcer;

public class ChangeHP extends Action {

	/**
	 * Generated
	 */
	private static final long serialVersionUID = -1008201197765156541L;

	EntityManager manager = null;
	
	private int delta;

	public ChangeHP(int delta) {
		super(0);
		this.delta = delta;
	}

	public void apply(EntityActionManager queue) {
		queue.changeHP(delta);
		
		if(delta > 0) 
			Announcer.announce("You feel healthier.");
		else 
			Announcer.announce("You feel weaker.");
	};
	
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
		ChangeHP other = (ChangeHP) obj;
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
