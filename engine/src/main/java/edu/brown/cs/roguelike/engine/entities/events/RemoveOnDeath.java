package edu.brown.cs.roguelike.engine.entities.events;

import edu.brown.cs.roguelike.engine.entities.Action;
import edu.brown.cs.roguelike.engine.entities.Combatable;
import edu.brown.cs.roguelike.engine.entities.EntityActionManager;
import edu.brown.cs.roguelike.engine.entities.EntityManager;

public class RemoveOnDeath implements Action {

	Combatable entity = null;
	EntityManager manager = null;

	public RemoveOnDeath(Combatable entity, EntityManager manager) {
		this.entity = entity;
		this.manager = manager;
	}

	public void apply(EntityActionManager queue) {
		this.manager.unregister(this.entity);
	}

}
