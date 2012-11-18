package edu.brown.cs.roguelike.game;

import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.ScreenWriter;

import cs195n.Vec2i;
import edu.brown.cs.roguelike.engine.events.GameAction;
import edu.brown.cs.roguelike.engine.graphics.Layer;
import edu.brown.cs.roguelike.engine.level.Level;
import edu.brown.cs.roguelike.engine.level.Tile;
import edu.brown.cs.roguelike.engine.proc.BSPLevelGenerator;
import edu.brown.cs.roguelike.engine.save.SaveLoadException;
import edu.brown.cs.roguelike.engine.save.SaveManager;


/**
 * A simple demo layer that shows basic rendering of levels,
 * saving/loading, and procedural generation
 * 
 * @author lelberty
 *
 */
public class DemoLayer implements Layer {
	
	private Level currentLevel;
	private final Vec2i levelSize;
	private BSPLevelGenerator rg;
	private SaveManager sm;
	private String statusMsg;
	
	private Vec2i size;
	
	private DemoApplication app;
	
	public DemoLayer(DemoApplication app, Vec2i size) {
		this.app = app;
		this.size = size;
		currentLevel = null;
		levelSize = size;
		rg = new BSPLevelGenerator();
		sm = new SaveManager("demoSave");
		statusMsg = "No level. Press 'n' to generate a level, or press " +
				"'s' to load the last saved level";
	}

	@Override
	public GameAction getActionForKey(Key k) {
		switch (k.getCharacter()) {
		case 'n': return new GameAction(1, 1); // generate new level
		case 's': return new GameAction(1,2); // save current level
		case 'l': return new GameAction(1,3); // load saved level
		case 'q': return new GameAction(1,4); // quit
		default: return new GameAction(1, 0); // do nothing 
		}
	}

	@Override
	public void propagateAction(GameAction action) {
		if (action.getContextClassifier() != 1)
			throw new Error("Received an action for a non-demo context.");
		try {
			switch (action.getActionClassifier()) {
			case 0: break; // do nothing
			case 1: // generate new level
				currentLevel = rg.generateLevel(levelSize);
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
		}
		catch (SaveLoadException e) {
			statusMsg = e.getMessage();
			currentLevel = null;
		}
	}

	@Override
	public void updateSize(Vec2i newSize) {
		this.size = newSize;
	}

	@Override
	public void doDraw(Screen s) {
		
		ScreenWriter sw = new ScreenWriter(s);
		
		Vec2i middle = size.sdiv(2);
		
		if (currentLevel == null) { // draw status message
			sw.drawString(
					middle.x - statusMsg.length()/2,
					0,
					statusMsg);
		} else { // otherwise draw level
			
			Tile[][] tiles = currentLevel.tiles;
			
			Tile t;
			for (int c = 0; c < tiles.length; c++) {
				for (int r = tiles[0].length - 1; r >= 0; r--) { // flip y
					t = tiles[c][r];
					switch (t.getType()) {
					case FLOOR:
						sw.drawString(c, r, ".");
						break;
					case WALL_HOR:
						sw.drawString(c, r, "-");
						break;
					case WALL_VER:
						sw.drawString(c, r, "|");
						break;
					case SOLID:
						sw.drawString(c, r, " ");
						break;
					case DOOR:
						sw.drawString(c, r, "+");
						break;
					}
				}
			}

		}
	}

	@Override
	public void tick(long nanosSincePreviousTick) {
		// Do nothing on tick
	}

}
