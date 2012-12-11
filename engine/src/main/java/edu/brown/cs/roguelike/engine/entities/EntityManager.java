package edu.brown.cs.roguelike.engine.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import edu.brown.cs.roguelike.engine.entities.events.Remove;
import edu.brown.cs.roguelike.engine.save.Saveable;

public class EntityManager implements Saveable {

	/**
	 * Generated 
	 */
	private static final long serialVersionUID = 6538908714623761737L;
	
	/**
	 * Every EntityActionManager that truly exists.
	 */
	protected List<EntityActionManager> everything = new ArrayList<EntityActionManager>();
	
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


	/**
	 * Convenience method that creates an EntityActionManager for entity
	 * and adds it to map and everything
	 * 
	 * @param entity the Combatable to be registered
	 * @return the EntityActionManager created for entity and then added
	 */
	public EntityActionManager register(Combatable entity) {
		EntityActionManager manager = new LocalEntityActionManager(entity);
		manager.on(Event.DEATH, new Remove(entity, this));
		entity.setManager(manager);
		for (String category : entity.getCategories())
			addToCategory(category, manager);
		// add to everything
		everything.add(manager);
		return manager;
	}

	/**
	 * Register an EntityActionManager by adding it to map and to
	 * everything
	 * 
	 * @param manager EntityActionManager to add
	 * @param categories categories to add manager to
	 */
	public void register(EntityActionManager manager, List<String> categories) {
		for (String category : categories)
			addToCategory(category, manager);
		everything.add(manager);
	}

	/**
	 * Unregister an entity. Remove it from both map and everything.
	 * @param entity the {@link Combatable} to remove
	 */
	public void unregister(Combatable entity) {
		unregister(entity.getManager(), entity.getCategories());
		everything.remove(entity);
	}

	public void unregister(EntityActionManager manager, List<String> categories) {
		for (String category : categories)
			map.get(category).remove(manager);
	}

	/**
	 * @param category, String category of the EntityActionManagers to get
	 * @return a copy of the List<EntityActionManager> in map with the given
	 * category
	 */
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
	
	/** Gets the nth manager from the main character.
	 *  If the player does not exist, null is returned.
	 * @param n - the number of the player to get (starts at 0)
	 * @return - player #n
	 */
	public EntityActionManager getPlayer(int n) {
		List<EntityActionManager> mains = getEntity("main");
		if(n >= mains.size()) 
			return null;
		
		return mains.get(n);
	}
	
	/**
	 * Returns a boolean indicating whether or not an EntityActionManager
	 * really exists. True existence is determined by presence in the
	 * everything list, as the List returned by getEntity is just a copy.
	 * Rogue meets existentialism. Boom.
	 * 
	 * @return boolean indicating if an EntityActionManager really exists!
	 */
	public boolean reallyExists(EntityActionManager manager) {
		return everything.contains(manager);
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
