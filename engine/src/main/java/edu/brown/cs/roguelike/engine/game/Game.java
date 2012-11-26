package edu.brown.cs.roguelike.engine.game;

import edu.brown.cs.roguelike.engine.entities.Entity;
import edu.brown.cs.roguelike.engine.level.Level;

public class Game {

	protected Level currentLevel = null;
    protected Character character = null;
    protected Entity[][] entityLocations = null;

    public boolean isPassable(int row, int column) {
        return entityLocations[row][column] != null ||  currentLevel.tiles[row][column].isPassable();
    }
    


}
