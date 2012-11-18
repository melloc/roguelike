package edu.brown.cs.roguelike.engine.game;

//import edu.brown.cs.roguelike.engine.game.Entity;
import edu.brown.cs.roguelike.engine.level.Level;
import static edu.brown.cs.roguelike.engine.level.Direction.*;

public class Game {

	protected Level currentLevel = null;
    protected Character character = null;
    protected Object[][] entityLocations = null;

    public boolean isPassable(int row, int column) {
        return entityLocations[row][column] != null ||  currentLevel.tiles[row][column].isPassable();
    }
    


}
