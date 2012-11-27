package edu.brown.cs.roguelike.engine.graphics;

import java.util.List;

import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.screen.ScreenCharacterStyle;
import com.googlecode.lanterna.screen.ScreenWriter;

import cs195n.Vec2i;
import edu.brown.cs.roguelike.engine.entities.Combatable;
import edu.brown.cs.roguelike.engine.entities.EntityActionManager;
import edu.brown.cs.roguelike.engine.events.GameAction;
import edu.brown.cs.roguelike.engine.level.Level;
import edu.brown.cs.roguelike.engine.level.Tile;
import edu.brown.cs.roguelike.engine.proc.BSPLevelGenerator;
import edu.brown.cs.roguelike.engine.proc.LevelGenerator;
import edu.brown.cs.roguelike.engine.save.SaveManager;


/**
 * A default layer that displays levels. Games should extend this Layer
 * to display their levels. Game customization can happen in getActionForKey 
 * and propogateAction
 * 
 * @author lelberty
 *
 * @param <A> the main Application of the Roguelike
 */
public abstract class DefaultMainLayer<A extends Application> implements Layer {
	
	protected final static int ANNOUNCE_OFFSET = 1;
	protected final static Vec2i MAP_SIZE = new Vec2i(80, 24);
	protected final static int MAX_NAME_LENGTH = 30;

	protected Level currentLevel;
	protected LevelGenerator rg;
	protected SaveManager sm;
	protected String statusMsg;
	protected String name;

	protected Vec2i size;
	protected A app;

	public DefaultMainLayer(A app, Vec2i size, String name) {
		this.app = app;
		this.size = size;
		this.name = name;
		currentLevel = null;
		rg = new BSPLevelGenerator();
		sm = new SaveManager("demoSave");
		statusMsg = "No level. Press 'N' to generate a level, or press "
				+ "'S' to load the last saved level";
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
		// Draw the divider
		String divider = "";
		for (int i = 0; i < size.x; i++) {
			divider += '_';
		}
		sw.drawString(0, ANNOUNCE_OFFSET + MAP_SIZE.y, divider);
		int statStart = ANNOUNCE_OFFSET + MAP_SIZE.y + 1;

		// CHECK FOR WIN/LOSE
		List<EntityActionManager> monsters = this.currentLevel.getManager()
				.getEntity("monster");
		List<EntityActionManager> main = this.currentLevel.getManager()
				.getEntity("main");

		if (monsters.isEmpty()) {
			sw.drawString(0, statStart, "You win!");
			return;
		} else if (main.isEmpty()) {
			sw.drawString(0, statStart, "You lost!");
			return;
		}

		Combatable player = main.get(0).getEntity();

		// DRAW THE STATS
		String line1 = "";
		line1 += "Robert the Rogue   ";
		line1 += "HP: ";
		line1 += player.getHP();
		line1 += "/";
		line1 += player.getStartHP();
		sw.drawString(0, statStart, line1);

		// LINE 2
		String line2 = "";
		line2 += "Attack: ";
		line2 += player.getStats().getAttack();
		line2 += "  Defense: ";
		line2 += String.valueOf(player.getStats().getDefense());
		sw.drawString(0, statStart + 1, line2);

	}

	private void drawLevel(ScreenWriter sw) {
		Tile[][] tiles = currentLevel.tiles;

		Tile t;
		for (int c = 0; c < tiles.length; c++) {
			for (int r = tiles[0].length - 1; r >= 0; r--) { // flip y
				t = tiles[c][r];

				sw.setForegroundColor(t.getColor());
				sw.drawString(c, r + ANNOUNCE_OFFSET,
						String.valueOf(t.getCharacter()),
						ScreenCharacterStyle.Bold);
			}
		}
	}

	public abstract GameAction getActionForKey(Key k);
	public abstract void propagateAction(GameAction action);
	public abstract void tick(long nanosSincePreviousTick);

}
