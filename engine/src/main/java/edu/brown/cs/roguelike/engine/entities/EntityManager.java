package edu.brown.cs.roguelike.engine.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.brown.cs.roguelike.engine.entities.events.RemoveOnDeath;

public class EntityManager {

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
		for (EntityActionManager manager : map.get(category)) 
			ret.add(manager);
		return ret;
	}


}
