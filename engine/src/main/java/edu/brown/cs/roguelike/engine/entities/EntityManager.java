package edu.brown.cs.roguelike.engine.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
		for (String category : entity.getCategories())
			addToCategory(category,manager);
		return manager;
	}

	public List<EntityActionManager> getEntity(String category) {
		return map.get(category);
	}


}
