package edu.brown.cs.roguelike.engine.fsm.monster;

import java.util.List;

import cs195n.Vec2i;

import edu.brown.cs.roguelike.engine.entities.Action;
import edu.brown.cs.roguelike.engine.entities.EntityActionManager;
import edu.brown.cs.roguelike.engine.entities.Monster;
import edu.brown.cs.roguelike.engine.entities.events.Move;
import edu.brown.cs.roguelike.engine.entities.events.Wait;
import edu.brown.cs.roguelike.engine.fsm.State;
import edu.brown.cs.roguelike.engine.level.Direction;
import edu.brown.cs.roguelike.engine.level.Level;
import edu.brown.cs.roguelike.engine.level.Tile;
import edu.brown.cs.roguelike.engine.pathfinding.AStar;

public class Chasing extends State<MonsterInput> {
	
	/**
	 * Generated
	 */
	private static final long serialVersionUID = 7848469900206748905L;
	
	private final Monster me;
	private final int moveCost;

	public Chasing(Monster me, int moveCost) {
		this.me = me;
		this.moveCost = moveCost;
	}

	@Override
	public void onActive() {
		me.setNextAction(getAction());
	}

	@Override
	public void onEnter() {
		//me.setNextAction(getAction());
	}

	@Override
	public void onExit() {}
	
	private Action getAction() {
		
		Level l = me.getLevel();
		
		EntityActionManager player = 
				l.getManager().getEntity("main").get(0);
		
		Tile myTile = me.getLocation();
		
		Tile playerTile = player.getEntity().getLocation();
		
		AStar<Tile> pathfinder = new AStar<Tile>();
		
		List<Tile> path = pathfinder.computePath(myTile, playerTile);
		
		if (path.size() <= 1) return new Wait();
		
		Tile dest = path.get(1);
		
		Vec2i curLoc = myTile.getLocation();
		Vec2i destLoc = dest.getLocation();
		
		Direction dir;
		
		if (destLoc.x < curLoc.x) dir = Direction.LEFT;
		else if (destLoc.x > curLoc.x) dir = Direction.RIGHT;
		else if (destLoc.y < curLoc.y) dir = Direction.UP;
		else dir = Direction.DOWN;
		
		return new Move(moveCost, me, dir);
	}

}
