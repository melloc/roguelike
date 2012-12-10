package edu.brown.cs.roguelike.engine.entities.events;

import java.util.List;
import java.util.UUID;

import edu.brown.cs.roguelike.engine.entities.Action;
import edu.brown.cs.roguelike.engine.entities.EntityActionManager;
import edu.brown.cs.roguelike.engine.entities.EntityManager;
import edu.brown.cs.roguelike.engine.level.Direction;
import edu.brown.cs.roguelike.engine.level.Tile;
import edu.brown.cs.roguelike.engine.save.Saveable;

public class ChaseMainCharacter extends Action implements Saveable {

	/**
	 * Generated
	 */
	private static final long serialVersionUID = 644910751763675638L;
	
	EntityManager manager = null;

	public ChaseMainCharacter(int cost, EntityManager manager) {
		super(cost);
		this.manager = manager;
	}

	public void apply(EntityActionManager queue) {
		List<EntityActionManager> mains = this.manager.getEntity("main");
		if (mains.size() > 0) {
			Tile myLoc = queue.getLocation();
			Tile mainLoc = mains.get(0).getLocation();
			// Actually pathfind?
			//List<Tile> path = Pathfinder.findPath(myLoc, mainLoc, myLoc.getLevel());
			//Direction dir = path.size() > 0 ? myLoc.dirTo(mainLoc) : Direction.UP;
			Direction dir = myLoc.dirTo(mainLoc); 
			queue.sendMove(dir);
		}
	};
	
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
		ChaseMainCharacter other = (ChaseMainCharacter) obj;
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
