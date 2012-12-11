package edu.brown.cs.roguelike.engine.game;

import java.util.List;

import edu.brown.cs.roguelike.engine.entities.Action;
import edu.brown.cs.roguelike.engine.entities.EntityActionManager;
import edu.brown.cs.roguelike.engine.entities.EntityManager;
import edu.brown.cs.roguelike.engine.entities.events.Wait;

/**
 * A TurnManager that gives every Entity a certain number of points
 * for each turn. When it's an Entity's turn, it will perform actions
 * and spend points so long as it can perform the action without 
 * going below a certain threshold of points (this may need configuration)
 * 
 * @author lelberty
 */
public class CumulativeTurnManager extends TurnManager {
	
	/**
	 * Generated
	 */
	private static final long serialVersionUID = 2226734907191258919L;
	
	private final int pointsPerTurn;
	
	// If the player has less than minPtsPerTurn, they won't be given the
	// chance to go again during that turn. This helps to alleviate 
	// player action lag. There may be a better way to do this
	private final int minPtsPerTurn;
	

	public CumulativeTurnManager(Game game, int pointsPerTurn) {
		super(game, pointsPerTurn);
		this.pointsPerTurn = pointsPerTurn;
		this.minPtsPerTurn = pointsPerTurn/2;
	}
	
	@Override
	public void takeTurn(Action playerAction) {
		
		Action nextAction = null;
		int cost;
		
		EntityManager em = game.getCurrentLevel().getManager();
		
		EntityActionManager mainMgr = em.getEntity("main").get(0);
		
		// if the player doesn't have an Action queued, then set one
		if (!mainMgr.hasNextAction()) { 
			mainMgr.setNextAction(playerAction);
		} // otherwise try to do the action that has been queued
		
		// Player's turn:
		nextAction = mainMgr.getNextAction();
		cost = (nextAction instanceof Wait) ? 
				pointsPerTurn : nextAction.getCost();
		
		// if the Player can take the action, then take it
		if (cost <= mainMgr.getActionPoints()) {
			mainMgr.takeNextAction();
			mainMgr.useActionPoints(cost);
		}
		
		// if there are more than minPtsPerTurn remaining, then return 
		// and wait for player's next input
		if (mainMgr.getActionPoints() > minPtsPerTurn) return;
		else { // continue and grant APs for this turn
			mainMgr.addActionPoints(pointsPerTurn);
		}

		// take care of monsters:
		
		List<EntityActionManager> monsterMgrs = em.getEntity("monster");

		for (EntityActionManager mgr: monsterMgrs) {

			do { // actions
				// check to make sure this monster still exists and hasn't been
				// concurrently removed
				if (em.reallyExists(mgr)) { 

					nextAction = mgr.getNextAction();
					cost = (nextAction instanceof Wait) ? 
							pointsPerTurn : nextAction.getCost();

					// if the monster can afford it, take the action
					if (cost <= mgr.getActionPoints()) {
						mgr.takeNextAction();
						mgr.useActionPoints(cost);
					}
				} 
			} 
			// until we have too few points to try again
			// TODO: consider making the lower bound minPtsPerTurn? 
			while (mgr.getActionPoints() > 0);
			
			// finally, grant points for this turn
			mgr.addActionPoints(pointsPerTurn);
		}
	}
}
