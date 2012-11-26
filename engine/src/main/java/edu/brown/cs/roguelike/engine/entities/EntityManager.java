package edu.brown.cs.roguelike.engine.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.brown.cs.roguelike.engine.entities.events.RemoveOnDeath;
import edu.brown.cs.roguelike.engine.save.IDManager;
import edu.brown.cs.roguelike.engine.save.Saveable;

public class EntityManager implements Saveable {

	/**
	 * Generated 
	 */
	private static final long serialVersionUID = 6538908714623761737L;
	
	protected HashMap<String,List<EntityActionManager>> map = new HashMap<String,List<EntityActionManager>>();

	public EntityManager() {

	}

	protected void addToCategory(String category, EntityActionManager manager) {
		List<EntityActionManager> managers = map.get(category);
		if (managers == null) {
			managers = new ArrayList<EntityActionManager>();
			map.put(category, managers);
		}
		managers.add(manager);
	}


	public EntityActionManager register(Combatable entity) {
		EntityActionManager manager = new LocalEntityActionManager(entity);
		manager.on(Event.DEATH, new RemoveOnDeath(entity, this));
		entity.setManager(manager);
		for (String category : entity.getCategories())
			addToCategory(category, manager);
		return manager;
	}

	public void register(EntityActionManager manager, List<String> categories) {
		for (String category : categories)
			addToCategory(category, manager);
	}

	public void unregister(Combatable entity) {
		unregister(entity.getManager(), entity.getCategories());
	}

	public void unregister(EntityActionManager manager, List<String> categories) {
		for (String category : categories)
			map.get(category).remove(manager);
	}

	public List<EntityActionManager> getEntity(String category) {
		List<EntityActionManager> ret = new ArrayList<EntityActionManager>();
		List<EntityActionManager> lst = map.get(category);
		if (lst == null) {
			lst = new ArrayList<EntityActionManager>();
			map.put(category, lst);
		}
		for (EntityActionManager manager : lst) 
			ret.add(manager);
		return ret;
	}
	
	/*** BEGIsN Saveable ***/
	
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
		EntityManager other = (EntityManager) obj;
		if (id == other.id) return true;
		return false;
	}

	@Override
	public long getId() {
		return this.id;
	}
	
	
	/*** END Saveable ***/
}
