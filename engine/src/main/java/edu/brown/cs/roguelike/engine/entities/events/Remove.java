package edu.brown.cs.roguelike.engine.entities.events;

import edu.brown.cs.roguelike.engine.entities.Action;
import edu.brown.cs.roguelike.engine.entities.Combatable;
import edu.brown.cs.roguelike.engine.entities.EntityActionManager;
import edu.brown.cs.roguelike.engine.entities.EntityManager;

public class Remove extends Action {
		
	/**
	 * Generated
	 */
	private static final long serialVersionUID = 2164260182197546382L;
	
	Combatable entity = null;
	EntityManager manager = null;

	public Remove(Combatable entity, EntityManager manager) {
		super(0);
		this.entity = entity;
		this.manager = manager;
	}

	public void apply(EntityActionManager queue) {
		this.manager.unregister(this.entity);
	}

}
