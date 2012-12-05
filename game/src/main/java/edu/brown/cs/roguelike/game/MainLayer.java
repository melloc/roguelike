package edu.brown.cs.roguelike.game;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.lanterna.input.Key;

import cs195n.Vec2i;

import edu.brown.cs.roguelike.engine.entities.Action;
import edu.brown.cs.roguelike.engine.entities.EntityActionManager;
import edu.brown.cs.roguelike.engine.entities.EntityManager;
import edu.brown.cs.roguelike.engine.entities.Event;
import edu.brown.cs.roguelike.engine.entities.events.ChaseMainCharacter;
import edu.brown.cs.roguelike.engine.config.ConfigurationException;
import edu.brown.cs.roguelike.engine.events.GameAction;
import edu.brown.cs.roguelike.engine.graphics.DefaultMainLayer;
import edu.brown.cs.roguelike.engine.graphics.LookLayer;
import edu.brown.cs.roguelike.engine.graphics.PotionLayer;
import edu.brown.cs.roguelike.engine.graphics.WeaponLayer;
import edu.brown.cs.roguelike.engine.level.Direction;
import edu.brown.cs.roguelike.engine.level.Room;
import edu.brown.cs.roguelike.engine.level.Tile;
import edu.brown.cs.roguelike.engine.save.SaveLoadException;

/**
 * A simple demo layer that shows basic rendering of levels, saving/loading, and
 * procedural generation
 *
 * @author lelberty
 *
 */
public class MainLayer extends DefaultMainLayer<GUIApp> {
	
	public MainLayer(GUIApp guiApp, Vec2i size) {
		super(guiApp, size, "Robert The Rogue");
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
			managers = currentLevel.getManager().getEntity("keyboard");
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
				currentLevel = rg.generateLevel(MAP_SIZE);
				EntityManager m = currentLevel.getManager();
				Action chaser = new ChaseMainCharacter(m);
				for (EntityActionManager monster : m.getEntity("monster")) {
					monster.on(Event.ATTACKED, chaser);
				}
				checkReveal();
			}
				break;
			case 2: // save current level
				sm.saveLevel(currentLevel);
				break;
			case 3: // load saved level
			{
				currentLevel = sm.loadLevel();
				EntityManager m = currentLevel.getManager();
				Action chaser = new ChaseMainCharacter(m);
				for (EntityActionManager monster : m.getEntity("monster")) {
					monster.on(Event.ATTACKED, chaser);
				}
			}
				break;
			case 4: // quit
				app.shutdown();
				break;
			case 5:
				for (EntityActionManager manager : managers)
					manager.sendMove(Direction.LEFT);
				checkReveal();
				break;
			case 6:
				for (EntityActionManager manager : managers)
					manager.sendMove(Direction.UP);
				checkReveal();
				break;
			case 7:
				for (EntityActionManager manager : managers)
					manager.sendMove(Direction.DOWN);
				checkReveal();
				break;
			case 8:
				for (EntityActionManager manager : managers)
					manager.sendMove(Direction.RIGHT);
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
						new PotionLayer<GUIApp>(app, size, currentLevel));
				break;
			case 11:
				app.getLayers().push(
						new InventoryLayer(app, size, currentLevel));
				app.getLayers().push(
						new WeaponLayer<GUIApp>(app, size, currentLevel));
				break;
			case 12:
				app.getLayers().push(
						new LookLayer<GUIApp>(app, size, currentLevel));
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
