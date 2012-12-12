package edu.brown.cs.roguelike.engine.game;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.UUID;

import cs195n.Vec2i;
import edu.brown.cs.roguelike.engine.config.ConfigurationException;
import edu.brown.cs.roguelike.engine.entities.MainCharacter;
import edu.brown.cs.roguelike.engine.level.Level;
import edu.brown.cs.roguelike.engine.proc.BSPLevelGenerator;
import edu.brown.cs.roguelike.engine.proc.LevelGenerator;
import edu.brown.cs.roguelike.engine.save.Saveable;

public abstract class Game implements Saveable {
	
	/**
	 * Generated
	 */
	private static final long serialVersionUID = 4193150732888666463L;

	protected GameState gameState;
	
	protected Hashtable<Integer,Level> levels = new Hashtable<Integer,Level>();
;
	protected Level currentLevel;
	protected Vec2i levelSize;
	protected final Vec2i MAP_SIZE;
    
    public Game(Vec2i mapSize) {
    	this.MAP_SIZE = mapSize;
    	this.init();
    }
    
    /**Initalizes the first level
     * @throws ConfigurationException **/
	public void createInitalLevel(BSPLevelGenerator lg) throws ConfigurationException {
		this.currentLevel = lg.generateLevel(MAP_SIZE, 1);
		levels.put(1, currentLevel);
		
		//TODO: Get character name
		MainCharacter mc = new MainCharacter("Robert the Rogue");
		
		currentLevel.getManager().register(mc);
	
		currentLevel.placeCharacter(mc, true);
	}
    
    /**
     * Moves the player to the level indicated by depth
     * @param depth - The depth the player is moving to
     * @param levelGen - The level generator to use if new levels are needed
     * @throws ConfigurationException - A configuration problem exists
     */
    public void gotoLevel(int depth, LevelGenerator levelGen) throws ConfigurationException {
    	if(levels.get(depth) == null) {
    		Level newLevel = levelGen.generateLevel(MAP_SIZE, depth);
    		
    		MainCharacter mc = currentLevel.removePlayer();
    		newLevel.placeCharacter(mc, currentLevel.getDepth() < depth);
    		levels.put(depth,newLevel);
    		currentLevel = newLevel;
    	}
    	else {
    		MainCharacter mc = currentLevel.removePlayer();
    		levels.get(depth).placeCharacter(mc, currentLevel.getDepth() < depth);
    		currentLevel = levels.get(depth);
    	}
    }
    
    private void init() {
    	this.gameState = GameState.INIT;
    }
        
    public void start() {
    	this.gameState = GameState.RUNNING;
    }
    
    public void win() { 
    	this.gameState = GameState.WIN;
    }
    
    public void loss() { 
    	this.gameState = GameState.LOSS;
    }
    
    public GameState getState() { 
    	return this.gameState;
    }
    
    public Level getCurrentLevel() { return this.currentLevel; }

    
    /**
     * Takes a LevelGenerator to prevent the need to serialize a local copy
     * 
     * @deprecated Use gotoLevel
     * 
     * @param lg
     * @return
     * @throws ConfigurationException
     */
    public Level generateNewLevel(LevelGenerator lg) throws ConfigurationException {
    	this.currentLevel = lg.generateLevel(MAP_SIZE, 3);
    	return currentLevel;
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
		Game other = (Game) obj;
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
