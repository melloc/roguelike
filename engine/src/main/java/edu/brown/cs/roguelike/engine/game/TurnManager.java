package edu.brown.cs.roguelike.engine.game;

import java.util.UUID;

import edu.brown.cs.roguelike.engine.entities.Action;
import edu.brown.cs.roguelike.engine.entities.Entity;
import edu.brown.cs.roguelike.engine.entities.EntityActionManager;
import edu.brown.cs.roguelike.engine.save.Saveable;


/**
 * 
 * Responsible for managing turns.
 * 
 *  
 * @author lelberty
 *
 */
public abstract class TurnManager implements Saveable {
	
	/**
	 * Generated
	 */
	private static final long serialVersionUID = -8611255057971437450L;
	
	protected Game game;
	protected final int waitCost;
	
	public TurnManager(Game game, int waitCost) {
		this.game = game;
		this.waitCost = waitCost;
	}
	
	public int getWaitCost() { return this.waitCost; }

	/**
	 * Every turn starts with the player's Action
	 * @param playerAction
	 */
	public abstract void takeTurn(Action playerAction);
	
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
		TurnManager other = (TurnManager) obj;
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
