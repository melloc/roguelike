package edu.brown.cs.roguelike.game;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.ScreenCharacterStyle;
import com.googlecode.lanterna.screen.ScreenWriter;

import cs195n.Vec2i;
import edu.brown.cs.roguelike.engine.entities.EntityActionManager;
import edu.brown.cs.roguelike.engine.config.ConfigurationException;
import edu.brown.cs.roguelike.engine.events.GameAction;
import edu.brown.cs.roguelike.engine.graphics.Layer;
import edu.brown.cs.roguelike.engine.graphics.Section;
import edu.brown.cs.roguelike.engine.level.Direction;
import edu.brown.cs.roguelike.engine.level.Level;
import edu.brown.cs.roguelike.engine.level.Tile;
import edu.brown.cs.roguelike.engine.proc.BSPLevelGenerator;
import edu.brown.cs.roguelike.engine.proc.LevelGenerator;
import edu.brown.cs.roguelike.engine.save.SaveLoadException;
import edu.brown.cs.roguelike.engine.save.SaveManager;

/**
 * A simple demo layer that shows basic rendering of levels, saving/loading, and
 * procedural generation
 *
 * @author lelberty
 *
 */
public class MainLayer implements Layer {

	private final static int ANNOUNCE_OFFSET = 1;
	private final static Vec2i MAP_SIZE = new Vec2i(80,24);
	private final static int MAX_NAME_LENGTH = 30;

	private Level currentLevel;
	private LevelGenerator rg;
	private SaveManager sm;
	private String statusMsg;
	private String name = "Robert the Rogue";


	private Vec2i size;

	private GUIApp app;

	public MainLayer(GUIApp guiApp, Vec2i size) {
		this.app = guiApp;
		this.size = size;
		currentLevel = null;
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
		List<EntityActionManager> managers = null;
		if (currentLevel != null)
			managers =  currentLevel.getManager().getEntity("keyboard");
		if (managers == null)
			managers = new ArrayList<EntityActionManager>();
		if (action.getContextClassifier() != 1)
			throw new Error("Received an action for a non-gameplay context.");
		try {
			switch (action.getActionClassifier()) {
			case 0:
				break; // do nothing
			case 1: // generate new level
				currentLevel = rg.generateLevel(MAP_SIZE);
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
			case 5:
				for (EntityActionManager manager : managers)
					manager.sendMove(Direction.LEFT);
				break;
			case 6:
				for (EntityActionManager manager : managers)
					manager.sendMove(Direction.UP);
				break;
			case 7:
				for (EntityActionManager manager : managers)
					manager.sendMove(Direction.DOWN);
				break;
			case 8:
				for (EntityActionManager manager : managers)
					manager.sendMove(Direction.RIGHT);
				break;
			default:
				throw new Error("This shouldn't happen and indicates an unhandled case. Received: "
						+ action.toString()
						+ "...");
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
			drawLevel(sw);
			drawStats(sw);
		}
	}

	private void drawStats(ScreenWriter sw) {
		//Draw the divider
		String divider = "";
		for(int i = 0; i < size.x ; i++) {
			divider += '_';
		}
		sw.drawString(0, ANNOUNCE_OFFSET+MAP_SIZE.y, divider);
		int statStart = ANNOUNCE_OFFSET+MAP_SIZE.y+1;
		int xOffset = 0;
		//DRAW THE STATS
		sw.drawString(0, statStart, "Robert the Rogue");
		sw.drawString(name.length()+5, statStart, "HP: ");
		xOffset = name.length()+ 5 + "HP: ".length();
		
		sw.drawString(xOffset, statStart, "20/27");
		sw.drawString(0, statStart+1, "More stats will go here");

	}

	private void drawLevel(ScreenWriter sw) {
		Tile[][] tiles = currentLevel.tiles;

		Tile t;
		for (int c = 0; c < tiles.length; c++) {
			for (int r = tiles[0].length - 1; r >= 0; r--) { // flip y
				t = tiles[c][r];

				sw.setForegroundColor(t.getColor());
				sw.drawString(c, r+ANNOUNCE_OFFSET, String.valueOf(t.getCharacter()), ScreenCharacterStyle.Bold);
			}
		}
	}

	@Override
	public void tick(long nanosSincePreviousTick) {
		// Do nothing on tick
	}

}
