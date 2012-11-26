package edu.brown.cs.roguelike.engine.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.brown.cs.roguelike.engine.level.Direction;
import edu.brown.cs.roguelike.engine.level.Tile;
import edu.brown.cs.roguelike.engine.save.IDManager;
import edu.brown.cs.roguelike.engine.save.Saveable;

public class LocalEntityActionManager implements EntityActionManager, Saveable {
	
	/**
	 * Generated
	 */
	private static final long serialVersionUID = 7307174231891885328L;
	
	Combatable entity = null;

	public LocalEntityActionManager(Combatable entity) {
		this.entity = entity;
	}

	@Override
	public Tile getLocation() {
		return entity.getLocation();
	}

	@Override
	public Stats getStats() {
		return entity.getStats();
	}

	@Override
	public void sendMove(Direction dir) {
		entity.move(dir);
	}

	@Override
	public void changeHP(int delta) {
		entity.HP += delta;
	}

	@Override
	public void toggleVisibility() {

	}

	protected Map<Event,List<Action>> onEvents = new HashMap<Event,List<Action>>();

	@Override
	public void on(Event e, Action a) {
		List<Action> lst = onEvents.get(e);
		if (lst == null) {
			lst = new ArrayList<Action>();
			onEvents.put(e,lst);
		}
		lst.add(a);
	}

	public void call(Event e) {
		List<Action> lst = onEvents.get(e);
		if (lst != null)
			for (Action a : lst)
				a.apply(this);
	}

	/**
	 * @return the entity
	 */
	public Combatable getEntity() {
		return entity;
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
		LocalEntityActionManager other = (LocalEntityActionManager) obj;
		if (id == other.id) return true;
		return false;
	}

	@Override
	public long getId() {
		return this.id;
	}
	
	
	/*** END Saveable ***/

}
