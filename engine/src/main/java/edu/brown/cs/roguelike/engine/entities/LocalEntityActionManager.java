package edu.brown.cs.roguelike.engine.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import edu.brown.cs.roguelike.engine.game.Announcer;
import edu.brown.cs.roguelike.engine.level.Direction;
import edu.brown.cs.roguelike.engine.level.Tile;
import edu.brown.cs.roguelike.engine.save.Saveable;

public class LocalEntityActionManager implements EntityActionManager, Saveable {
	
	/**
	 * Generated
	 */
	private static final long serialVersionUID = 7307174231891885328L;

	private static final int MAX_DROP_SIZE = 26;
	
	protected int actionPoints;
	
	protected Action nextAction;
	
	Combatable entity = null;

	public LocalEntityActionManager(Combatable entity) {
		this.entity = entity;
		this.actionPoints = 0;
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
	public void changeStats(Stats delta) {
		Stats s = entity.getStats();
		entity.setStats(new Stats (
				s.attack + delta.attack,
				s.defense + delta.defense
				));
	}
	
	@Override
	public void changeHP(int delta) {
		entity.changeHP(delta);
	}
	
	@Override
	public void drop(Stackable s) {
		if(entity.getLocation().getStackables().size() < MAX_DROP_SIZE) {
			entity.getInventory().remove(s);
			entity.getLocation().getStackables().add(s);
		}
		else {
			Announcer.announce("No room to drop here.");
		}
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
		LocalEntityActionManager other = (LocalEntityActionManager)obj;
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

	@Override
	public int getActionPoints() {
		return this.actionPoints;
	}

	@Override
	public void useActionPoints(int ap) {
		this.actionPoints -= ap;
	}
	
	@Override
	public void addActionPoints(int ap) {
		this.actionPoints += ap;
	}
	
	@Override
	final public Action getNextAction() {
		if (nextAction != null) { 
			return nextAction;
		} else {
			// ask the Entity for the next action to take
			this.nextAction = entity.generateNextAction();
			return nextAction;
		}
	}
	
	@Override
	final public void takeNextAction() {
		this.nextAction.apply(this); // take action
		// clear nextAction
		this.nextAction = null;
	}
	
	@Override
	final public void setNextAction(Action nextAction) {
		this.nextAction = nextAction;
	}
	
	@Override
	final public boolean hasNextAction() {
		return this.nextAction != null;
	}
	
}
