package edu.brown.cs.roguelike.engine.graphics;

import java.util.List;

import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.screen.ScreenCharacterStyle;
import com.googlecode.lanterna.screen.ScreenWriter;

import cs195n.Vec2i;
import edu.brown.cs.roguelike.engine.entities.Combatable;
import edu.brown.cs.roguelike.engine.entities.EntityActionManager;
import edu.brown.cs.roguelike.engine.entities.MainCharacter;
import edu.brown.cs.roguelike.engine.events.GameAction;
import edu.brown.cs.roguelike.engine.game.Game;
import edu.brown.cs.roguelike.engine.level.Level;
import edu.brown.cs.roguelike.engine.level.Tile;

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
	
	protected Game game;

	protected String statusMsg;
	protected String name;
	
	protected Vec2i size;
	protected A app;

	public DefaultMainLayer(A app, Game game, 
			Vec2i screenSize, String startMessage) {
		this.game = game;
		this.app = app;
		this.size = screenSize;
		this.name = "A Roguelike";
		statusMsg = startMessage;
	}
	
	@Override
	public void updateSize(Vec2i newSize) {
		this.size = newSize;
	}

	@Override
	public void doDraw(Section sw) {

		Vec2i middle = size.sdiv(2);

		if (game == null || game.getCurrentLevel() == null) { // draw status message
			sw.drawString(middle.x - statusMsg.length() / 2, 0, statusMsg);
		} else { // otherwise draw level
			drawLevel(sw);
			drawStats(sw);
		}
	}

	private void drawStats(ScreenWriter sw) {
		
		Level currentLevel = game.getCurrentLevel();
		
		// Draw the divider
		String divider = "";
		for (int i = 0; i < size.x; i++) {
			divider += '_';
		}
		sw.drawString(0, ANNOUNCE_OFFSET + MAP_SIZE.y, divider);
		int statStart = ANNOUNCE_OFFSET + MAP_SIZE.y + 1;

		// CHECK FOR WIN/LOSE
		List<EntityActionManager> monsters = currentLevel.getManager()
				.getEntity("monster");
		List<EntityActionManager> main = currentLevel.getManager()
				.getEntity("main");

		if (monsters.isEmpty()) {
			sw.drawString(0, statStart, "You win!");
			return;
		} else if (main.isEmpty()) {
			sw.drawString(0, statStart, "You lost!");
			return;
		}

		MainCharacter player = (MainCharacter) main.get(0).getEntity();

		// DRAW THE STATS
		String line1 = "";
		line1 += "Robert the Rogue   ";
		line1 += "HP: ";
		line1 += player.getHP();
		line1 += "/";
		line1 += player.getStartHP();
		line1 += "   XP: ";
		line1 += player.getXP();
		line1 += "/";
		line1 += player.getNextLevelXP();
		line1 += "       ";
		sw.drawString(0, statStart, line1);
		
		String depthString = "Dungeon Level: " + currentLevel.getDepth();
		sw.drawString(size.x - depthString.length() -2, statStart, depthString);

		// LINE 2
		String line2 = "";
		line2 = "Level: " + player.getPlayerLevel();
		line2 += "  Attack: ";
		line2 += player.getStats().getAttack();
		line2 += "  Defense: ";
		line2 += String.valueOf(player.getStats().getDefense());
		line2 += "       ";
		sw.drawString(0, statStart + 1, line2);

	}

	private void drawLevel(Section sw) {
		game.getCurrentLevel().doDraw(sw.moveUpperLeft(new Vec2i(0, ANNOUNCE_OFFSET)));

		/*Tile t;
		for (int c = 0; c < tiles.length; c++) {
			for (int r = tiles[0].length - 1; r >= 0; r--) { // flip y
				t = tiles[c][r];

				sw.setForegroundColor(t.getColor());
				sw.drawString(c, r + ANNOUNCE_OFFSET,
						String.valueOf(t.getCharacter()),
						ScreenCharacterStyle.Bold);
			}
		}*/
	}

	public abstract GameAction getActionForKey(Key k);
	public abstract void propagateAction(GameAction action);
	public abstract void tick(long nanosSincePreviousTick);

}
