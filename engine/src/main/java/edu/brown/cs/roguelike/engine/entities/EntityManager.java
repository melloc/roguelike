package edu.brown.cs.roguelike.engine.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import edu.brown.cs.roguelike.engine.entities.events.RemoveOnDeath;
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
		EntityManager other = (EntityManager) obj;
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
