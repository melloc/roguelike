package edu.brown.cs.roguelike.engine.game;

import java.util.UUID;

import cs195n.Vec2i;

import edu.brown.cs.roguelike.engine.config.ConfigurationException;
import edu.brown.cs.roguelike.engine.level.Level;
import edu.brown.cs.roguelike.engine.proc.LevelGenerator;
import edu.brown.cs.roguelike.engine.save.Saveable;

public abstract class Game implements Saveable {
	
	/**
	 * Generated
	 */
	private static final long serialVersionUID = 4193150732888666463L;

	protected GameState gameState;
	
	protected Level currentLevel;
	protected Vec2i levelSize;
	protected final Vec2i MAP_SIZE;
    
    public Game(Vec2i mapSize) {
    	this.MAP_SIZE = mapSize;
    	this.init();
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
     * @param lg
     * @return
     * @throws ConfigurationException
     */
    public Level generateNewLevel(LevelGenerator lg) throws ConfigurationException {
    	this.currentLevel = lg.generateLevel(MAP_SIZE);
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
