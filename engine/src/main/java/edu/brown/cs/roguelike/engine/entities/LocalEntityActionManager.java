package edu.brown.cs.roguelike.engine.entities;

import edu.brown.cs.roguelike.engine.level.Direction;
import edu.brown.cs.roguelike.engine.level.Tile;

public class LocalEntityActionManager implements EntityActionManager {
	
	Combatable entity = null;

	/**
	 *
	 */
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
		
	}

	@Override
	public void changeHP(int delta) {
		
	}

	@Override
	public void toggleVisibility() {

	}

	@Override
	public void on(Event e, Action a) {

	}

	/**
	 * @return the entity
	 */
	public Combatable getEntity() {
		return entity;
	}

}
