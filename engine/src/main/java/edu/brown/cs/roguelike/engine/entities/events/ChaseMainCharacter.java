package edu.brown.cs.roguelike.engine.entities.events;

import java.util.List;

import edu.brown.cs.roguelike.engine.entities.Action;
import edu.brown.cs.roguelike.engine.entities.EntityActionManager;
import edu.brown.cs.roguelike.engine.entities.EntityManager;
import edu.brown.cs.roguelike.engine.level.Direction;
import edu.brown.cs.roguelike.engine.level.Pathfinder;
import edu.brown.cs.roguelike.engine.level.Tile;

public class ChaseMainCharacter implements Action {

	EntityManager manager = null;

	public ChaseMainCharacter(EntityManager manager) {
		this.manager = manager;
	}

	public void apply(EntityActionManager queue) {
		List<EntityActionManager> mains = this.manager.getEntity("main");
		if (mains.size() > 0) {
			Tile myLoc = queue.getLocation();
			Tile mainLoc = mains.get(0).getLocation();
			List<Tile> path = Pathfinder.findPath(myLoc, mainLoc, myLoc.getLevel());
			Direction dir = path.size() > 0 ? myLoc.dirTo(path.get(0)) : Direction.UP;
			queue.sendMove(dir);
		}
	};

}
