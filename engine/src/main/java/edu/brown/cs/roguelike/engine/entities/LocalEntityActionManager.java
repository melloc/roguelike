package edu.brown.cs.roguelike.engine.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

}
