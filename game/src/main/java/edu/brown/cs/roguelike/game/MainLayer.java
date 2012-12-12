package edu.brown.cs.roguelike.game;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.lanterna.input.Key;

import cs195n.Vec2i;
import edu.brown.cs.roguelike.engine.config.ConfigurationException;
import edu.brown.cs.roguelike.engine.entities.Action;
import edu.brown.cs.roguelike.engine.entities.Combatable;
import edu.brown.cs.roguelike.engine.entities.EntityActionManager;
import edu.brown.cs.roguelike.engine.entities.EntityManager;
import edu.brown.cs.roguelike.engine.entities.Event;
import edu.brown.cs.roguelike.engine.entities.events.ChaseMainCharacter;
import edu.brown.cs.roguelike.engine.entities.events.Move;
import edu.brown.cs.roguelike.engine.events.GameAction;
import edu.brown.cs.roguelike.engine.game.CumulativeTurnManager;
import edu.brown.cs.roguelike.engine.graphics.DefaultMainLayer;
import edu.brown.cs.roguelike.engine.graphics.LookLayer;
import edu.brown.cs.roguelike.engine.graphics.PotionLayer;
import edu.brown.cs.roguelike.engine.graphics.WeaponLayer;
import edu.brown.cs.roguelike.engine.level.Direction;
import edu.brown.cs.roguelike.engine.level.Level;
import edu.brown.cs.roguelike.engine.level.Room;
import edu.brown.cs.roguelike.engine.level.Tile;
import edu.brown.cs.roguelike.engine.level.TileType;
import edu.brown.cs.roguelike.engine.save.SaveLoadException;

/**
 * A simple demo layer that shows basic rendering of levels, saving/loading, and
 * procedural generation
 *
 * @author lelberty
 *
 */
public class MainLayer extends DefaultMainLayer<GUIApp> {
	
	public MainLayer(GUIApp guiApp, RogueGame game, Vec2i size, String startMessage) {
		super(guiApp, game, size, startMessage);
		if(game != null && game.getCurrentLevel() != null)
			checkReveal();

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
			case 'Q':
				return new GameAction(1, 4); // quit
			case 'h':
				return new GameAction(1, 5); // Move left
			case 'j':
				return new GameAction(1, 7); // Move down
			case 'k':
				return new GameAction(1, 6); // Move up
			case 'l':
				return new GameAction(1, 8); // Move right
			case 'i':
				return new GameAction(1, 9); // Open Inventory
			case 'q':
				return new GameAction(1, 10); // QuaffPotion
			case 'w':
				return new GameAction(1, 11); // Wield
			case 'o':
				return new GameAction(1, 12); // observe
			case '<':
				return new GameAction(1, 13); // up stairs
			case '>':
				return new GameAction(1, 14); // down stairs
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
		
		Level currentLevel = game.getCurrentLevel();
		CumulativeTurnManager tm = app.getTurnManager();
		
		EntityActionManager mainManager = null;
		
		Move moveAction;
		Combatable c = null;
		
		List<EntityActionManager> managers = null;
		
		// if there is a current level, 
		// then get the keyboard-controlled managers
		// (just the player)
		if (currentLevel != null) {
			managers = currentLevel.getManager().getEntity("keyboard");
			mainManager = managers.get(0);
			c = mainManager.getEntity();
		}
		
		if (managers == null)
			managers = new ArrayList<EntityActionManager>();
		
		if (action.getContextClassifier() != 1)
			throw new Error("Received an action for a non-gameplay context.");
		
		try {
			switch (action.getActionClassifier()) {
			case 0:
				break; // do nothing
			case 1: // generate new level
			{
				game.gotoLevel(currentLevel.getDepth()+1, app.getLevelGenerator());
				currentLevel = game.getCurrentLevel();
				EntityManager m = currentLevel.getManager();
				Action chaser = new ChaseMainCharacter(10, m);
				for (EntityActionManager monster : m.getEntity("monster")) {
					monster.on(Event.ATTACKED, chaser);
				}
				checkReveal();
			}
				break;
			case 2: // save current level
				app.getSaveManager().saveGame(this.game);
				break;
			case 3: // load saved level
			{
				game = app.getSaveManager().loadGame();
				currentLevel = game.getCurrentLevel();
				EntityManager m = currentLevel.getManager();
				Action chaser = new ChaseMainCharacter(10, m);
				for (EntityActionManager monster : m.getEntity("monster")) {
					monster.on(Event.ATTACKED, chaser);
				}
			}
				break;
			case 4: // quit
				app.shutdown();
				break;
			case 5:
				if (c != null) { 
					moveAction = new Move(10, c, Direction.LEFT);
					tm.takeTurnAndAnnounce(moveAction);
				}
//				for (EntityActionManager manager : managers)
//					manager.sendMove(Direction.LEFT);
				checkReveal();
				break;
			case 6:
				if (c != null) {
					moveAction = new Move(10, c, Direction.UP);
					tm.takeTurnAndAnnounce(moveAction);
				}
//				for (EntityActionManager manager : managers)
//					manager.sendMove(Direction.UP);
				checkReveal();
				break;
			case 7:
				if (c != null) {
					moveAction = new Move(10, c, Direction.DOWN);
					tm.takeTurnAndAnnounce(moveAction);
				}
//				for (EntityActionManager manager : managers)
//					manager.sendMove(Direction.DOWN);
				checkReveal();
				break;
			case 8:
				if (c != null) {
					moveAction = new Move(10, c, Direction.RIGHT);
					tm.takeTurnAndAnnounce(moveAction);					
				}
//				for  manager : managers)
//					manager.sendMove(Direction.RIGHT);
				checkReveal();
				break;
			case 9:
				app.getLayers().push(
						new InventoryLayer(app, size, currentLevel));
				break;
			case 10:
				app.getLayers().push(
						new InventoryLayer(app, size, currentLevel));
				app.getLayers().push(
						new PotionLayer<GUIApp>(tm, app, size, currentLevel));
				break;
			case 11:
				app.getLayers().push(
						new InventoryLayer(app, size, currentLevel));
				app.getLayers().push(
						new WeaponLayer<GUIApp>(tm, app, size, currentLevel));
				break;
			case 12:
				app.getLayers().push(
						new LookLayer<GUIApp>(app, size, currentLevel));
				break;
			case 13: //Up Stairs
				if(c != null) {
					if (c.getLocation().getType() == TileType.UP_STAIRS) {
						//TODO: Handle going to surface
						if(currentLevel.getDepth() != 1) {
							game.gotoLevel(currentLevel.getDepth()-1, app.getLevelGenerator());
							checkReveal();
						}
					}
				}
				break;
			case 14: //Up Stairs
				if(c != null) {
					if (c.getLocation().getType() == TileType.DOWN_STAIRS) {
							game.gotoLevel(currentLevel.getDepth()+1, app.getLevelGenerator());
							checkReveal();
					}
				}
				break;
			default:
				throw new Error(
						"This shouldn't happen and indicates an unhandled case. Received: "
								+ action.toString() + "...");
			}
		} catch (SaveLoadException e) {
			statusMsg = e.getMessage();
			currentLevel = null;
		} catch (ConfigurationException e) {
			statusMsg = e.getMessage();
			currentLevel = null;
		}
	}

	
	private void checkReveal() {
		Level currentLevel = game.getCurrentLevel();

		List<EntityActionManager> mainMangr = currentLevel.getManager().getEntity("main");
		if(mainMangr.size() < 1) 
			return;
		
		Tile playerLoc = mainMangr.get(0).getLocation();
		
		for(Room r : currentLevel.getRooms()) {
			if(r.containsTile(playerLoc)) {
				currentLevel.revealRoom(r);
			}
		}
		
		currentLevel.revealAround(playerLoc);
	}

	@Override
	public void tick(long nanosSincePreviousTick) {
		// Do nothing on tick
	}

}
