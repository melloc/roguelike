package edu.brown.cs.roguelike.level.test;

import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.screen.ScreenCharacterStyle;

import cs195n.Vec2i;
import edu.brown.cs.roguelike.engine.config.ConfigurationException;
import edu.brown.cs.roguelike.engine.events.GameAction;
import edu.brown.cs.roguelike.engine.graphics.Layer;
import edu.brown.cs.roguelike.engine.graphics.Section;
import edu.brown.cs.roguelike.engine.level.Level;
import edu.brown.cs.roguelike.engine.level.Room;
import edu.brown.cs.roguelike.engine.level.Tile;
import edu.brown.cs.roguelike.engine.proc.BSPLevelGenerator;
import edu.brown.cs.roguelike.engine.proc.RandomGen;
import edu.brown.cs.roguelike.engine.save.SaveLoadException;
import edu.brown.cs.roguelike.engine.save.SaveManager;

/**
 * A simple demo layer that shows basic rendering of levels, saving/loading, and
 * procedural generation
 *
 * @author lelberty
 *
 */
public class PathTestLayer implements Layer {

	
	
    private Level currentLevel;
    private final Vec2i levelSize;
    private BSPLevelGenerator rg;
    private SaveManager sm;
    private String statusMsg;

    private Vec2i size;

    private PathFinderApplication app;

    public PathTestLayer(PathFinderApplication app, Vec2i size) {
        this.app = app;
        this.size = size;
        currentLevel = null;
        levelSize = size;
        rg = new BSPLevelGenerator();
        sm = new SaveManager("demoSave");
        statusMsg = "No level. Press 'N' to generate a level, or press "
                + "'S' to load the last saved level";
    }

    @Override
    public GameAction getActionForKey(Key k) {
        switch (k.getKind()) {
        case NormalKey: {
            switch (k.getCharacter()) {
            case 'N':
                return new GameAction(1, 1); // generate new level
            case 'S':
                return new GameAction(1, 2); // save current level
            case 'L':
                return new GameAction(1, 3); // load saved level
            case 'q':
                return new GameAction(1, 4); // quit
            case 'h':
                return new GameAction(1, 5); // Move left
            case 'j':
                return new GameAction(1, 7); // Move down
            case 'k':
                return new GameAction(1, 6); // Move up
            case 'l':
                return new GameAction(1, 8); // Move right
            default:
                return new GameAction(1, 0); // do nothing
            }
        }
        case ArrowLeft:
            return new GameAction(1, 5); // Move left
        case ArrowUp:
            return new GameAction(1, 6); // Move up
        case ArrowDown:
            return new GameAction(1, 7); // Move down
        case ArrowRight:
            return new GameAction(1, 8); // Move right
        default:
            return new GameAction(1, 0); // do nothing
        }
    }

    @Override
    public void propagateAction(GameAction action) {
        if (action.getContextClassifier() != 1)
            throw new Error("Received an action for a non-demo context.");
        try {
            switch (action.getActionClassifier()) {
            case 0:
                break; // do nothing
            case 1: // generate new level
                currentLevel = rg.generateLevel(levelSize,5);
                
                //TEST
                //Draw a path of monsters between two random rooms 
                RandomGen rand = new RandomGen(System.nanoTime());
                Room r1 = currentLevel.getRooms().get(0);
      
                
                
                break;
            case 2: // save current level
                sm.saveLevel(currentLevel);
                break;
            case 3: // load saved level
                currentLevel = sm.loadLevel();
                break;
            case 4: // quit
                app.shutdown();
                break;
            default:
                throw new Error("This shouldn't happen. Received a "
                        + action.getActionClassifier() + "...");
            }
        } catch (SaveLoadException e) {
            statusMsg = e.getMessage();
            currentLevel = null;
        } catch (ConfigurationException e) {
        	statusMsg = e.getMessage();
            currentLevel = null;
		}
    }

    @Override
    public void updateSize(Vec2i newSize) {
        this.size = newSize;
    }

    @Override
    public void doDraw(Section sw) {

        Vec2i middle = size.sdiv(2);

        if (currentLevel == null) { // draw status message
            sw.drawString(middle.x - statusMsg.length() / 2, 0, statusMsg);
        } else { // otherwise draw level

            Tile[][] tiles = currentLevel.tiles;

            Tile t;
            for (int c = 0; c < tiles.length; c++) {
                for (int r = tiles[0].length - 1; r >= 0; r--) { // flip y
                    t = tiles[c][r];

                    sw.setForegroundColor(t.getColor());
                    sw.drawString(c, r, String.valueOf(t.getCharacter()),
                            ScreenCharacterStyle.Bold);
                }
            }

        }
    }

    @Override
    public void tick(long nanosSincePreviousTick) {
        // Do nothing on tick
    }

}
